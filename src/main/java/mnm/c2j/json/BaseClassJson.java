package mnm.c2j.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseClassJson extends NamedJson {

    private List<MethodJson> methods;
    private String outerClass;
    private List<String> innerClasses;
    private Map<String, TypeJson> typeArgs;

    public void addMethod(MethodJson m) {
        if (methods == null)
            methods = new ArrayList<>();
        methods.add(m);
    }

    public void addInnerClass(String c) {
        if (innerClasses == null)
            innerClasses = new ArrayList<>();
        innerClasses.add(c);
    }

    public void addTypeArg(String name, TypeJson t) {
        if (typeArgs == null) {
            typeArgs = new HashMap<>();
        }
        typeArgs.put(name, t);
    }

    public void setOuterClass(String className) {
        outerClass = className;
    }

    public String getOuterClass() {
        return outerClass;
    }

}
