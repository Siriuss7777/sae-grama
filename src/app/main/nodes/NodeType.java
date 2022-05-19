package app.main.nodes;

import java.util.Locale;

public enum NodeType {
    VILLE("V"),
    RESTAURANT("R"),
    LOISIRS("L");

    String type;

    NodeType(String type){
        this.type = type;
    }

    public String getType(){ return this.type; }

    public static NodeType getTypeWithVal(String s){
        s = s.toUpperCase(Locale.ROOT);
        for(NodeType nodeType: NodeType.values()){
            if (nodeType.getType().equals(s)) {
                return nodeType;
            }
        }
        return null;
    }

    public String toString(){
        return this.type;
    }
}
