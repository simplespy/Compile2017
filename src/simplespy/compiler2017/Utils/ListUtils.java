package simplespy.compiler2017.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by spy on 5/20/17.
 */
abstract public class ListUtils {
    static public <T>List<T> reverse(List<T> list){
        List<T> result = new ArrayList<T>(list.size());
        ListIterator<T> it = list.listIterator(list.size());
        while (it.hasPrevious()) {
            result.add(it.previous());
        }
        return result;
    }
}
