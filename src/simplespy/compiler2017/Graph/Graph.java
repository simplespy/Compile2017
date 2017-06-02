package simplespy.compiler2017.Graph;

import simplespy.compiler2017.NodeFamily.VarDecNode;

import java.util.Set;

/**
 * Created by spy on 17/6/1.
 */
public class Graph {
    public class Node{
        public Set<VarDecNode> def;
        public Set<VarDecNode> use;
        public Set<Node> in;
        public Set<Node> out;
        public boolean isMove;
    }
}
