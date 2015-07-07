package mnm.c2j.asm;

import mnm.c2j.json.AnnotationInstanceJson;
import mnm.c2j.json.MethodJson;

public class AnnotationValueVisitor extends BaseAnnotationVisitor {

    private MethodJson json;

    public AnnotationValueVisitor(MethodJson json) {
        this.json = json;
    }

    @Override
    protected void add(String name, String type, Object value) {
        json.setDefaultValue(new AnnotationInstanceJson.Value(type, value));
    }
}