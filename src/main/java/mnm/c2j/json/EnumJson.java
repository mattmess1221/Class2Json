package mnm.c2j.json;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class EnumJson extends BaseClassJson {

    @SerializedName("implements")
    private List<TypeJson> interfaces;
    private List<CallableJson> constructors;
    private List<VariableJson> fields;

    public void addInterface(String i) {
        addInterface(new TypeJson(i));
    }

    public void addConstructor(CallableJson c) {
        if (constructors == null)
            constructors = new ArrayList<>();
        constructors.add(c);
    }

    public void addField(VariableJson f) {
        if (fields == null)
            fields = new ArrayList<>();
        fields.add(f);
    }

    public void addInterface(TypeJson i) {
        if (interfaces == null)
            interfaces = new ArrayList<>();
        interfaces.add(i);
    }

}
