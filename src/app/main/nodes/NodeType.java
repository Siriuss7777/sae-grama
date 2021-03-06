package app.main.nodes;

import java.util.Locale;

/**
 * @author Bastien Le Gall
 */

public enum NodeType {
    CITY("V"),
    RESTAURANT("R"),
    LEISURE("L");

    String type;

    NodeType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public static NodeType getTypeWithVal(String s) {
        s = s.toUpperCase(Locale.ROOT);
        for (NodeType nodeType : NodeType.values()) {
            if (nodeType.getType().equals(s)) {
                return nodeType;
            }
        }
        return null;
    }

    public String toString() {
        return this.type;
    }
}
