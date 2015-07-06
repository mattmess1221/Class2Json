package mnm.c2j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.signature.SignatureVisitor;

public class ClassSignatureNode extends SignatureVisitor {

    public Map<String, TypeSignatureNode> typeArgs = new HashMap<>();
    public TypeSignatureNode superClass;
    public List<TypeSignatureNode> interfaces = new ArrayList<>();

    private TypeSignatureNode current;

    public ClassSignatureNode() {
        super(Opcodes.ASM5);
    }

    @Override
    public void visitFormalTypeParameter(String name) {
        current = new TypeSignatureNode();
        typeArgs.put(name, current);
    }

    @Override
    public void visitClassType(String name) {
        current.type = name;
    }

    @Override
    public void visitInnerClassType(String name) {
        current.type = name;
    }

    @Override
    public SignatureVisitor visitTypeArgument(char wildcard) {
        return current;
    }

    @Override
    public SignatureVisitor visitSuperclass() {
        return superClass = new TypeSignatureNode();
    }

    @Override
    public SignatureVisitor visitInterfaceBound() {
        TypeSignatureNode i = new TypeSignatureNode();
        interfaces.add(i);
        return i;
    }
}
