package tree;

import java.util.HashMap;

public class Node {
    private final String name;
    private final HashMap<String, Node> children = new HashMap<>();

    public Node (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Node> getChildren() {
        return children;
    }
}
