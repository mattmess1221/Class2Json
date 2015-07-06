package mnm.c2j.json;

import java.util.ArrayList;
import java.util.List;

public class PackageJson extends BaseJson {

    private List<ClassJson> classes;
    private List<InterfaceJson> interfaces;
    private List<AnnotationJson> annotations;
    private List<EnumJson> enums;

    public void addClass(ClassJson c) {
        if (classes == null)
            classes = new ArrayList<>();
        classes.add(c);
    }

    public void addInterface(InterfaceJson i) {
        if (interfaces == null)
            interfaces = new ArrayList<>();
        interfaces.add(i);
    }

    public void addAnnotation(AnnotationJson a) {
        if (annotations == null)
            annotations = new ArrayList<>();
        annotations.add(a);
    }

    public void addEnum(EnumJson e) {
        if (enums == null)
            enums = new ArrayList<>();
        enums.add(e);
    }
}
