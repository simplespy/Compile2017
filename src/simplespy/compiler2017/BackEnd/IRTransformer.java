package simplespy.compiler2017.BackEnd;

import simplespy.compiler2017.Asm.*;
import simplespy.compiler2017.BackEnd.SIR.*;
import simplespy.compiler2017.FrontEnd.IRVisitor;
import simplespy.compiler2017.FrontEnd.Scope;
import simplespy.compiler2017.NodeFamily.*;
import simplespy.compiler2017.NodeFamily.IRNode.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by spy on 17/6/2.
 */
public class IRTransformer implements IRVisitor {
    public SIR sir = new SIR();
    private Map<String, StringLiteralNode> stringPool;
    public SIR getSir(){return sir;}
    private Function curfunc = null;
    VarDecNode argThis;

    @Override
    public void visit(IRRoot node) {
        sir.vars = node.vars;
        sir.typeTable = node.typeTable;
        sir.scope = node.scope;

        //read only string
        stringPool = node.typeTable.getStringMap();
        stringPool.keySet().stream().forEachOrdered(name -> {
            Label label = new Label();
            stringPool.get(name).setAddress(label.getSymbol());
            stringPool.get(name).setMemoryReference(new DirectMemoryReference(label.getSymbol()));
        });


        //global vars
        node.vars.stream().forEachOrdered(x ->{
            Symbol sym = new Symbol("var@"+x.getName());
            x.setAddress(sym);
            x.setMemoryReference(new DirectMemoryReference(sym));
            x.setOperand(new MemoryReference(sym));
        });


        // functions
        node.funcs.keySet().stream().forEachOrdered(x->{
            Function func = new Function(x);
            curfunc = func;
            curfunc.epilogue = new Label();

            FuncDefNode funcDefNode = node.funcs.get(x);
            int i = 0;
            if (funcDefNode.externClass != null){
                argThis = new VarDecNode(new BaseType(TypeNode.TYPENAME.INT, null));
                funcDefNode.parameters.add(0, argThis);
            }
            func.paras = funcDefNode.parameters;
             final int PARAM_START_WORD = 2;

            int numWords = PARAM_START_WORD;

            for (VarDecNode y : func.paras) {
                y.setOperand(new VirReg(curfunc));
                if (i < PARAS_REG.length){
                    func.mov(PARAS_REG[i], y.getOperand());
                    ++i;
                }
                else{
                    func.mov(new IndirectMemoryReference(numWords * 8, bp()), y.getOperand());
                    ++numWords;
                }
            }



            //allocate virReg
            createLocalVars(funcDefNode.scope);


            //global vars initialize
            if (x.equals("main")) node.vars.stream().filter(y->y.init != null).forEachOrdered(y->{
                visit(y.ir);
                if (y.getType().isString()){
                    curfunc.call(new Symbol("transtring"),new ArrayList<Operand>(){{add(y.ir.getResult());}}, y.getOperand());
                }
                else curfunc.mov(y.ir.getResult(),y.getOperand());
            });

            funcDefNode.ir.stream().forEachOrdered(this::visit);
            func.label(func.epilogue);
            sir.functionList.add(func);

        });


    }

    private void createLocalVars(Scope scope){
        for (Node vari : scope.getEntities().values()) {
            VarDecNode var = vari.getVardec();
            if (var == null) continue;
            if (var.operand != null) continue;
            var.setOperand(new VirReg(curfunc));
        }
        scope.getChildren().forEach(this::createLocalVars);
    }










    @Override
    public void visit(ExprStmt node) {
        visit(node.getExpr());
    }

    @Override
    public void visit(Bin node) {
        Expr left = node.getLeft();
        Expr right = node.getRight();
        visit(right);
        visit(left);
        Operand result = new VirReg(curfunc);
        node.setResult(result);

        Node entity = node.getEntityForce();
        if (entity != null && entity.getType().isString() || node.isString()){
            ArrayList<Operand> paras = new ArrayList<>();
            switch (node.getOp()){
                case ADD:
                   /* curfunc.mov(left.getResult(), di());
                    curfunc.mov(right.getResult(), si());*/
                    paras.add(left.getResult());
                    paras.add(right.getResult());
                    curfunc.call(new Symbol("String_add"), paras, result); break;
                default:
                    paras.add(right.getResult());
                    paras.add(left.getResult());
                    switch (node.getOp()) {
                        case EQ:
                            curfunc.call(new Symbol("String_eq"), paras, result);
                            break;
                        case NE:
                            curfunc.call(new Symbol("String_ne"), paras, result);
                            break;
                        case GT:
                            curfunc.call(new Symbol("String_gt"), paras, result);
                            break;
                        case GE:
                            curfunc.call(new Symbol("String_ge"), paras, result);
                            break;
                        case LT:
                            curfunc.call(new Symbol("String_lt"), paras, result);
                            break;
                        case LE:
                            curfunc.call(new Symbol("String_le"), paras, result);
                            break;
                    }
            }
        }

        else {
            curfunc.mov(left.getResult(), result);
            if ((node.getOp().equals(BinaryOpNode.BinaryOp.DIV) || node.getOp().equals(BinaryOpNode.BinaryOp.MOD)) && right.getResult() instanceof ImmediateValue){
                VirReg tmp = new VirReg(curfunc);
                curfunc.mov(right.getResult(), tmp);
                curfunc.bin(node.getOp(),result,tmp);
            }
            else curfunc.bin(node.getOp(), result, right.getResult());
        }
    }


    private Register[] PARAS_REG = {di(), si(), dx(), cx(), r8(), r9()};


    @Override
    public void visit(Call node) {
        FuncDefNode entity = (FuncDefNode) node.getEntityForce();
        String funcName = entity.getName();
        if (entity.externClass != null) funcName = entity.externClass.name + '_' + funcName;
        else if (funcName.equals("size") && entity.equals(sir.scope.array.get(funcName))){
            funcName = "Array_size";
        }
        List<Operand> paras = new ArrayList<>();

        if (node.argThis != null){
            visit(node.argThis);
            paras.add(node.argThis.getResult());
        }
        else if (entity.externClass != null){
            paras.add(curfunc.paras.get(0).getOperand());
        }

        for (Expr arg: node.getArgs()) {
            visit(arg);
            paras.add(arg.getResult());
        }


        VirReg result = new VirReg(curfunc);
        curfunc.call(new Symbol(funcName), paras, result);
        node.setResult(result);
    }

    @Override
    public void visit(CJump node) {
        Operand condition;
        if (node.getCond() == null){
            condition = new VirReg(curfunc);
            curfunc.mov(new ImmediateValue(1), condition);
        }
        else {
            visit(node.getCond());
            condition = node.getCond().getResult();
        }
        curfunc.branch(condition, node.then, node.otherwise);

    }

    @Override
    public void visit(Expr node) {
        if (node == null) return;
        node.accept(this);
    }

    @Override
    public void visit(Int node) {node.setResult(new ImmediateValue(node.getValue()));}



    @Override
    public void visit(Jump node) { curfunc.jmp(node.getLabel());}

    @Override
    public void visit(LabelStmt node) { curfunc.label(node.label);}

    @Override
    public void visit(Return node) {
        if (node.getExpr() != null) {
            visit(node.getExpr());
            curfunc.mov(node.getExpr().getResult(), ax());
        }
        curfunc.jmp(curfunc.epilogue);
    }

    @Override
    public void visit(Str node) { node.setResult(stringPool.get(node.getValue()).getAddress());}

    @Override
    public void visit(Uni node) {
        visit(node.getExpr());
        VirReg result = new VirReg(curfunc);
        node.setResult(result);
        curfunc.mov(node.getExpr().getResult(), result);
        curfunc.uni(node.getOp(),result);
    }

    @Override
    public void visit(Var node) {
        node.setResult(node.entity.getVardec().getOperand());
        if (node.getResult() == null && node.getEntityForce().getVardec().externClass != null){
            VirReg addr = new VirReg(curfunc);
            curfunc.mov(argThis.getOperand(), addr);
            curfunc.bin(BinaryOpNode.BinaryOp.ADD, addr, new ImmediateValue(node.getEntityForce().getVardec().externClass.getOffset(node.getEntityForce().getVardec())*8));
            node.setResult(new MemoryReference(addr));
        }
    }

    @Override
    public void visit(VarDecNode node) {

    }

    @Override
    public void visit(Assign node) {
        if (node.getRhs() == null) return;
        Expr left = node.getLhs();
        Expr right = node.getRhs();
        visit(right);
        visit(left);
        if (node.getLhs().getEntityForce().getType().isString()){
            curfunc.call(new Symbol("transtring"),new ArrayList<Operand>(){{add(right.getResult());}}, left.getResult());
        }

        else curfunc.mov(right.getResult(), left.getResult());
    }

    @Override
    public void visit(FuncDefNode node) {

    }

    @Override
    public void visit(Stmt node) {
        if (node == null) return;
        node.accept(this);
    }

    @Override
    public void visit(Addr node) {
        node.setResult(node.entity.getVardec().getOperand());
        if (node.getResult() == null && node.getEntityForce().getVardec().externClass != null){
            VirReg addr = new VirReg(curfunc);
            curfunc.mov(argThis.getOperand(), addr);
            curfunc.bin(BinaryOpNode.BinaryOp.ADD, addr, new ImmediateValue(node.getEntityForce().getVardec().externClass.getOffset(node.getEntityForce().getVardec())*8));
            node.setResult(new MemoryReference(addr));
        }
    }

    @Override
    public void visit(Mem node) {
        visit(node.expr);
        node.setResult(new MemoryReference(node.expr.getResult()));


    }

    @Override
    public void visit(Malloc node) {
        if (node.dimList.size() == 0) {
            if (node.getEntity().getType().getBaseType() instanceof ClassType) {
                node.setResult(ClassAllocate(node));
            }
            else throw new Error("Malloc");
        }
        else node.setResult(HeapAllocate(node.dimList, node));


    }
    Operand HeapAllocate(List<Expr> dimList, Malloc node) {
        Symbol malloc = new Symbol("malloc");
        VirReg result = new VirReg(curfunc);
        VirReg size = new VirReg(curfunc);


        Expr dimSize = dimList.get(0);
        visit(dimSize);
        curfunc.mov(dimSize.getResult(), size);

        curfunc.bin(BinaryOpNode.BinaryOp.ADD, size, new ImmediateValue(1));
        curfunc.bin(BinaryOpNode.BinaryOp.MUL, size, new ImmediateValue(8));
        curfunc.call(malloc, new ArrayList<Operand>(){{add(size);}}, result);

        curfunc.mov(dimSize.getResult(), new MemoryReference(result));//save size info
        curfunc.bin(BinaryOpNode.BinaryOp.ADD, result, new ImmediateValue(8));
        curfunc.mov(dimSize.getResult(), size);
        curfunc.comment(";end of the first step of malloc");



        int type = 0;
        if (dimList.size() == 1 && node.getEntity().getType().getBaseType() instanceof ClassType) type = 1;

        if (dimList.size() > 1) type = 2;
        if (type != 0) {
            curfunc.mov(dimSize.getResult(), size);
            Label thenlabel = new Label();
            Label elselabel = new Label();

            curfunc.label(thenlabel);
            curfunc.bin(BinaryOpNode.BinaryOp.SUB, size, new ImmediateValue(1));
            VirReg newResult = new VirReg(curfunc);
            curfunc.mov(size, newResult);
            curfunc.bin(BinaryOpNode.BinaryOp.MUL,newResult, new ImmediateValue(8));
            curfunc.bin(BinaryOpNode.BinaryOp.ADD,newResult, result);
            if (type == 1) curfunc.mov(ClassAllocate(node),new MemoryReference(newResult));
            else curfunc.mov(HeapAllocate(dimList.subList(1,dimList.size()),node),new MemoryReference(newResult));
            VirReg condition = new VirReg(curfunc);
            curfunc.mov(new ImmediateValue(0), condition);
            curfunc.bin(BinaryOpNode.BinaryOp.NE, condition, size);
            curfunc.branch(condition, thenlabel, elselabel);
            curfunc.label(elselabel);
        }
        return result;

    }
    Operand ClassAllocate(Malloc node) {
        Symbol malloc = new Symbol("malloc");
        VirReg result = new VirReg(curfunc);
        ClassDefNode cls = sir.typeTable.getClassDefNode(node.getEntity().getType().getBaseType().toString());
        curfunc.call(malloc, new ArrayList<Operand>(){{add(new ImmediateValue(node.baseSize));}}, result);

        if (cls.constructor != null) {
            curfunc.call(new Symbol(cls.name + '_' + cls.name), new ArrayList<Operand>(){{add(result);}});
        }
        return result;
    }


        @Override
    public void visit(This node) {
        node.setResult(curfunc.paras.get(0).getOperand());
    }



    private Register ax(){
        return new PhiReg(Register.RegisterClass.AX);
    }
    private Register cx(){return new PhiReg(Register.RegisterClass.CX);}
    private Register dx(){return new PhiReg(Register.RegisterClass.DX);}
    private Register di(){return new PhiReg(Register.RegisterClass.DI);}
    private Register si(){return new PhiReg(Register.RegisterClass.SI);}
    private Register r8(){return new PhiReg(Register.RegisterClass.R8);}
    private Register r9(){return new PhiReg(Register.RegisterClass.R9);}
    private Register bp(){return new PhiReg(Register.RegisterClass.BP);}


}
