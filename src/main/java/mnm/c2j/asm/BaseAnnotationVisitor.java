package mnm.c2j.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import mnm.c2j.json.AnnotationInstanceJson;

public abstract class BaseAnnotationVisitor extends AnnotationVisitor {

    public BaseAnnotationVisitor() {
        super(Opcodes.ASM5);
    }

    protected abstract void add(String name, String type, Object value);

    @Override
    public void visit(String name, Object value) {
        add(name, value.getClass().getName(), value);
    }

    @Override
    public void visitEnum(String name, String desc, String value) {
        add(name, Type.getType(desc).getClassName(), value);
    }

    @Override
    public AnnotationVisitor visitArray(String name) {
        AnnotationArrayVisitor av = new AnnotationArrayVisitor();
        add(name, "array", av.getList());
        return av;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String name, String desc) {
        AnnotationInstanceJson anno = new AnnotationInstanceJson();
        add(name, Type.getType(desc).getClassName(), anno);
        return new AnnotationJsonVisitor(anno);
    }

}