package mnm.c2j.asm;

import mnm.c2j.json.AnnotationInstanceJson;

public class AnnotationJsonVisitor extends BaseAnnotationVisitor {

    private AnnotationInstanceJson json;

    public AnnotationJsonVisitor(AnnotationInstanceJson json) {
        this.json = json;
    }

    @Override
    protected void add(String name, String type, Object value) {
        json.addArg(name, new AnnotationInstanceJson.Value(type, value));
    }

}
