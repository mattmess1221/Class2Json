package mnm.c2j.json;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ClassJson extends BaseClassJson {

    @SerializedName("extends")
    private TypeJson superClass;
    @SerializedName("implements")
    private List<TypeJson> interfaces;
    private List<VariableJson> fields;
    private List<CallableJson> constructors = new ArrayList<>();

    public void setSuperClass(String superClass) {
        setSuperClass(new TypeJson(superClass));
    }

    public void setSuperClass(TypeJson superClass) {
        this.superClass = superClass;
    }

    public void addInterface(String i) {
        addInterface(new TypeJson(i));
    }

    public void addInterface(TypeJson i) {
        if (interfaces == null)
            interfaces = new ArrayList<>();
        interfaces.add(i);
    }

    public void addField(VariableJson f) {
        if (fields == null) {
            fields = new ArrayList<>();
        }
        fields.add(f);
    }

    public void addConstructor(CallableJson c) {
        constructors.add(c);
    }

}
