package mnm.c2j.json;

import java.util.List;

import com.google.common.collect.Lists;

public class PackageJson extends BaseJson {

    private List<ClassJson> classes;
    private List<InterfaceJson> interfaces;
    private List<AnnotationJson> annotations;
    private List<EnumJson> enums;

    public void addClass(ClassJson c) {
        if (classes == null)
            classes = Lists.newArrayList();
        classes.add(c);
    }

    public void addInterface(InterfaceJson i) {
        if (interfaces == null)
            interfaces = Lists.newArrayList();
        interfaces.add(i);
    }

    public void addAnnotation(AnnotationJson a) {
        if (annotations == null)
            annotations = Lists.newArrayList();
        annotations.add(a);
    }

    public void addEnum(EnumJson e) {
        if (enums == null)
            enums = Lists.newArrayList();
        enums.add(e);
    }
}
