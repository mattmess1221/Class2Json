package mnm.c2j.asm;

import java.util.Map.Entry;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.signature.SignatureReader;

import mnm.c2j.Kind;
import mnm.c2j.SyntheticClassException;
import mnm.c2j.json.AnnotationInstanceJson;
import mnm.c2j.json.BaseClassJson;
import mnm.c2j.json.CallableJson;
import mnm.c2j.json.ClassJson;
import mnm.c2j.json.EnumJson;
import mnm.c2j.json.InterfaceJson;
import mnm.c2j.json.MethodJson;
import mnm.c2j.json.TypeJson;
import mnm.c2j.json.VariableJson;

public class ClassJsonVisitor extends ClassVisitor {

    private String pkg;
    private Kind kind;
    private BaseClassJson json;

    public ClassJsonVisitor() {
        super(Opcodes.ASM5);
    }

    public String getPackage() {
        return pkg;
    }

    public BaseClassJson getJson() {
        return json;
    }

    public Kind getType() {
        return kind;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        if (isSynthetic(access))
            throw new SyntheticClassException(); // skip this class
        pkg = name.replace('/', '.').substring(0, name.lastIndexOf('/'));
        kind = Kind.getKind(access);
        json = kind.newJson();
        json.setName(name.substring(name.lastIndexOf('/') + 1));
        json.setAccess(access);
        if (kind == Kind.CLASS && superName != null) {
            ((ClassJson) json).setSuperClass(superName.replaceAll("[/$]", "."));
        }
        if (signature == null) {
            if (interfaces != null) {
                for (String i : interfaces) {
                    i = i.replaceAll("[/$]", ".");
                    if (kind == Kind.INTERFACE) {
                        ((InterfaceJson) json).addInterface(i);
                    } else if (kind == Kind.ENUM) {
                        ((EnumJson) json).addInterface(i);
                    } else if (kind == Kind.CLASS) {
                        ((ClassJson) json).addInterface(i);
                    }
                }
            }
        } else {
            SignatureReader sr = new SignatureReader(signature);
            ClassSignatureNode sn = new ClassSignatureNode();
            sr.accept(sn);

            if (kind == Kind.CLASS) {
                TypeJson superClass = new TypeJson();
                sn.superClass.accept(superClass);
                ((ClassJson) json).setSuperClass(superClass);
            }
            for (TypeSignatureNode n : sn.interfaces) {
                TypeJson interf = new TypeJson();
                n.accept(interf);
                if (kind == Kind.INTERFACE) {
                    ((InterfaceJson) json).addInterface(interf);
                } else if (kind == Kind.ENUM) {
                    ((EnumJson) json).addInterface(interf);
                }
            }
            for (Entry<String, TypeSignatureNode> e : sn.typeArgs.entrySet()) {
                TypeJson type = new TypeJson();
                e.getValue().accept(type);
                json.addTypeArg(e.getKey(), type);
            }
        }
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        AnnotationInstanceJson anno = new AnnotationInstanceJson();
        anno.setType(Type.getType(desc).getClassName());
        json.addAnnotated(anno);
        return new AnnotationJsonVisitor(anno);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        if (isSynthetic(access))
            return null;
        VariableJson f = new VariableJson();
        f.setName(name);
        f.setType(new TypeJson(Type.getType(desc).getClassName()));
        f.setAccess(access);
        if (signature != null) {
            SignatureReader sr = new SignatureReader(signature);
            TypeSignatureNode sn = new TypeSignatureNode();
            sr.acceptType(sn);
            TypeJson type = new TypeJson();
            sn.accept(type);
            f.setType(type);
        }
        addField(f);
        return new FieldJsonVisitor(f);
    }

    private void addField(VariableJson f) {
        switch (kind) {
        case CLASS:
            ((ClassJson) json).addField(f);
            break;
        case INTERFACE:
            ((InterfaceJson) json).addConstant(f);
            break;
        case ENUM:
            ((EnumJson) json).addField(f);
            break;
        default:
            break;
        }
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        // ignore synthetics
        if (isSynthetic(access))
            return null;

        CallableJson mtd;
        if ("<init>".equals(name)) {
            mtd = new CallableJson();
            addConstructor(mtd);
        } else if (!"<clinit>".equals(name)) {
            mtd = new MethodJson();
            ((MethodJson) mtd).setReturnType(Type.getReturnType(desc).getClassName());
            mtd.setName(name);
            json.addMethod((MethodJson) mtd);
        } else {
            return null;
        }
        mtd.setAccess(access);
        if (exceptions != null)
            for (String e : exceptions) {
                mtd.addException(new TypeJson(e.replaceAll("[/$]", ".")));
            }
        if (signature == null) {
            for (Type o : Type.getArgumentTypes(desc)) {
                mtd.addArgument(o.getClassName());
            }
        } else {
            MethodSignatureNode msn = new MethodSignatureNode();
            SignatureReader sr = new SignatureReader(signature);
            sr.accept(msn);

            for (Entry<String, TypeSignatureNode> entry : msn.typeVars.entrySet()) {
                TypeJson t = new TypeJson();
                entry.getValue().accept(t);
                mtd.addTypeVar(entry.getKey(), t);
            }

            for (int i = 0; i < msn.parameters.size(); i++) {
                VariableJson v = new VariableJson();
                TypeJson j = new TypeJson();
                msn.parameters.get(i).accept(j);
                v.setType(j);
                mtd.addArgument(v);
            }
        }

        return new MethodJsonVisitor(mtd);
    }

    private void addConstructor(CallableJson c) {
        switch (kind) {
        case CLASS:
            ((ClassJson) json).addConstructor(c);
            break;
        case ENUM:
            ((EnumJson) json).addConstructor(c);
            break;
        default:
            break;
        }
    }

    @Override
    public void visitOuterClass(String owner, String name, String desc) {
        json.setOuterClass(owner);
    }

    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        String canName = pkg.replace('.', '/') + '/' + json.getName();
        if (outerName != null && !isSynthetic(access) && canName.equals(outerName)) {
            json.addInnerClass(innerName);
        }
    }

    private static boolean isSynthetic(int access) {
        return (access & Opcodes.ACC_SYNTHETIC) == Opcodes.ACC_SYNTHETIC;
    }

    private static boolean isStatic(int access) {
        return (access & Opcodes.ACC_STATIC) == Opcodes.ACC_STATIC;
    }

    private class FieldJsonVisitor extends FieldVisitor {

        private VariableJson json;

        public FieldJsonVisitor(VariableJson json) {
            super(Opcodes.ASM5);
            this.json = json;
        }

        @Override
        public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
            AnnotationInstanceJson anno = new AnnotationInstanceJson();
            anno.setType(Type.getType(desc).getClassName());
            json.addAnnotated(anno);
            return new AnnotationJsonVisitor(anno);
        }
    }

    private class MethodJsonVisitor extends MethodVisitor {

        private CallableJson json;

        public MethodJsonVisitor(CallableJson json) {
            super(Opcodes.ASM5);
            this.json = json;
        }

        @Override
        public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
            AnnotationInstanceJson anno = new AnnotationInstanceJson();
            anno.setType(Type.getType(desc).getClassName());
            json.addAnnotated(anno);
            return new AnnotationJsonVisitor(anno);
        }

        @Override
        public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
            // Workaround for visitParameter not working
            int id = isStatic(json.getAccess()) ? index : index - 1;
            if (id >= 0 && id < json.getParameterCount()) {
                json.getArgument(id).setName(name);
            }
        }

        @Override
        public void visitParameter(String name, int access) {
            // FIXME: Doesn't get called
            // VariableJson param = json.getArgument(index);
            // param.setAccess(access);
            // param.setName(name);
            // index++;
        }

        @Override
        public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
            AnnotationInstanceJson anno = new AnnotationInstanceJson();
            anno.setType(Type.getType(desc).getClassName());
            json.getArgument(parameter).addAnnotated(anno);
            return new AnnotationJsonVisitor(anno);
        }

        @Override
        public AnnotationVisitor visitAnnotationDefault() {
            return new AnnotationValueVisitor((MethodJson) json);
        }
    }

}
