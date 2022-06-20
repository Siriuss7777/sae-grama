package app.main.nodes;

import java.util.Locale;

/**
 * @author Bastien Le Gall
 */

public enum LinkType {
    DEPARTMENTAL("D"),
    NATIONAL("N"),
    HIGHWAY("A");


    String type;

    LinkType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public static LinkType getTypeWithVal(String s) {
        s = s.toUpperCase(Locale.ROOT);
        for (LinkType linkType : LinkType.values()) {
            if (linkType.getType().equals(s)) {
                return linkType;
            }
        }
        return null;
    }

    public String toString() {
        return this.type;
    }

}
