package mnm.c2j.json;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public abstract class BaseClassJson extends NamedJson {

    private List<MethodJson> methods;
    private String outerClass;
    private List<String> innerClasses;
    private Map<String, TypeJson> typeArgs;

    public void addMethod(MethodJson m) {
        if (methods == null)
            methods = Lists.newArrayList();
        methods.add(m);
    }

    public void addInnerClass(String c) {
        if (innerClasses == null)
            innerClasses = Lists.newArrayList();
        innerClasses.add(c);
    }

    public void addTypeArg(String name, TypeJson t) {
        if (typeArgs == null) {
            typeArgs = Maps.newHashMap();
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
