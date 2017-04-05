package simplespy.compiler2017.Exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spy on 17/3/31.
 */
public class CompilationError {
    public static List<Exception> exceptions;

    public static void initialize(){
        exceptions = new ArrayList<>();
    }

    public static void printExceptions(){
        exceptions.stream().forEach(x->System.err.println(x.getMessage()));
    }

    public void add(Exception e){
        exceptions.add(e);
    }
    public static Exception top(){
        return exceptions.get(exceptions.size()-1);
    }
}
