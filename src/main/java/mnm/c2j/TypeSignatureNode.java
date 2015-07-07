package mnm.c2j;

import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.signature.SignatureVisitor;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import mnm.c2j.json.TypeJson;

public class TypeSignatureNode extends SignatureVisitor {

    public String type;
    public List<TypeSignatureNode> typeArgs = Lists.newArrayList();

    public TypeSignatureNode() {
        super(Opcodes.ASM5);
    }

    @Override
    public void visitTypeVariable(String name) {
        this.type = name;
    }

    @Override
    public void visitBaseType(char descriptor) {
        this.type = descriptor + "";
    }

    @Override
    public SignatureVisitor visitArrayType() {
        TypeSignatureNode t = new TypeSignatureNode();
        this.typeArgs.add(t);
        return t;
    }

    @Override
    public void visitClassType(String name) {
        type = name;
    }

    @Override
    public void visitInnerClassType(String name) {
        type = name;
    }

    @Override
    public SignatureVisitor visitTypeArgument(char wildcard) {
        TypeSignatureNode t = new TypeSignatureNode();
        this.typeArgs.add(t);
        return t;
    }

    @Override
    public String toString() {
        String type = Type.getType(this.type).getClassName();
        if (type == null)
            type = this.type;
        StringBuilder sb = new StringBuilder(type);
        if (!typeArgs.isEmpty()) {
            sb.append("<");
            Joiner.on(", ").appendTo(sb, typeArgs);
            sb.append(">");
        }
        return sb.toString();
    }

    public void accept(TypeJson json) {
        json.setType(type);
        for (TypeSignatureNode n : typeArgs) {
            TypeJson arg = new TypeJson();
            json.addArg(arg);
            n.accept(arg);
        }
    }
}
