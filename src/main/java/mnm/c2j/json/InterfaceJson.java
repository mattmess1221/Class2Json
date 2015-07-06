package mnm.c2j.json;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class InterfaceJson extends BaseClassJson {

    @SerializedName("extends")
    private List<TypeJson> interfaces;
    private List<VariableJson> constants;

    public void addInterface(String i) {
        addInterface(new TypeJson(i));
    }

    public void addConstant(VariableJson c) {
        if (constants == null)
            constants = new ArrayList<>();
        constants.add(c);
    }

    public void addInterface(TypeJson i) {
        if (interfaces == null)
            interfaces = new ArrayList<>();
        interfaces.add(i);
    }
}
