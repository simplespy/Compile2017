package simplespy.compiler2017.Graph;

import simplespy.compiler2017.NodeFamily.IRNode.*;
import simplespy.compiler2017.NodeFamily.VarDecNode;

import java.util.List;

/**
 * Created by spy on 17/6/1.
 */
 public class FlowGraph extends Graph {
    public FlowGraph(List<Stmt> stmts){
        stmts.stream().forEachOrdered(s->{
            Node node = new Node();

        }
        );

    }
}
