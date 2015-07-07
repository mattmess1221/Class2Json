package mnm.c2j.json;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

public class TypeJson extends BaseJson {

    private String type;
    private List<TypeJson> args;

    public TypeJson() {
    }

    public TypeJson(String type, TypeJson... args) {
        this.type = type;
        if (args.length > 0) {
            this.args = Arrays.asList(args);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void addArg(TypeJson t) {
        if (args == null)
            args = Lists.newArrayList();
        args.add(t);
    }
}
