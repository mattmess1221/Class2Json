package mnm.c2j.json;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.annotations.SerializedName;

public class CallableJson extends NamedJson {

    private List<VariableJson> args;
    private Map<String, TypeJson> typeVariables;
    @SerializedName("throws")
    private List<TypeJson> exceptions;

    public void addArgument(VariableJson v) {
        if (args == null)
            args = Lists.newArrayList();
        if (v.getName() == null)
            v.setName("arg" + args.size());
        args.add(v);
    }

    public void addArgument(String t) {
        VariableJson v = new VariableJson();
        v.setType(new TypeJson(t));
        addArgument(v);
    }

    public VariableJson getArgument(int i) {
        return args.get(i);
    }

    public void addTypeVar(String name, TypeJson t) {
        if (typeVariables == null)
            typeVariables = Maps.newHashMap();
        typeVariables.put(name, t);
    }

    public void addException(TypeJson e) {
        if (exceptions == null)
            exceptions = Lists.newArrayList();
        exceptions.add(e);
    }

}
