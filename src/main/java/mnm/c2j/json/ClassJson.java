package mnm.c2j.json;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

public class ClassJson extends BaseClassJson {

    @SerializedName("extends")
    private TypeJson superClass;
    @SerializedName("implements")
    private List<TypeJson> interfaces;
    private List<VariableJson> fields;
    private List<CallableJson> constructors;

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
            interfaces = Lists.newArrayList();
        interfaces.add(i);
    }

    public void addField(VariableJson f) {
        if (fields == null) {
            fields = Lists.newArrayList();
        }
        fields.add(f);
    }

    public void addConstructor(CallableJson c) {
        if (constructors == null) {
            constructors = Lists.newArrayList();
        }
        constructors.add(c);
    }

}
