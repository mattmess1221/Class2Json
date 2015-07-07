package mnm.c2j.json;

import java.util.List;

import com.google.common.collect.Lists;
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
            constructors = Lists.newArrayList();
        constructors.add(c);
    }

    public void addField(VariableJson f) {
        if (fields == null)
            fields = Lists.newArrayList();
        fields.add(f);
    }

    public void addInterface(TypeJson i) {
        if (interfaces == null)
            interfaces = Lists.newArrayList();
        interfaces.add(i);
    }

}
