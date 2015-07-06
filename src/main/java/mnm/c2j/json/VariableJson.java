package mnm.c2j.json;

public class VariableJson extends NamedJson {

    private TypeJson type;

    public void setType(TypeJson t) {
        this.type = t;
    }

    public TypeJson getType() {
        return type;
    }

}
