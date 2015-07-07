package mnm.c2j.asm;

import java.util.List;
import java.util.Map;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.signature.SignatureVisitor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ClassSignatureNode extends SignatureVisitor {

    public Map<String, TypeSignatureNode> typeArgs = Maps.newHashMap();
    public TypeSignatureNode superClass;
    public List<TypeSignatureNode> interfaces = Lists.newArrayList();

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
        current.type = name.replaceAll("[/$]", ".");
    }

    @Override
    public void visitInnerClassType(String name) {
        current.type = name.replaceAll("[/$]", ".");
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
