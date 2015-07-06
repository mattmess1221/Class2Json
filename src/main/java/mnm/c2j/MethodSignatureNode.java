package mnm.c2j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.signature.SignatureVisitor;

import com.google.common.base.Joiner;

public class MethodSignatureNode extends SignatureVisitor {

    public TypeSignatureNode returnType;
    public List<TypeSignatureNode> parameters = new ArrayList<>();
    public List<TypeSignatureNode> exceptions = new ArrayList<>();
    public Map<String, TypeSignatureNode> typeVars = new HashMap<>();

    private TypeSignatureNode currentVar;

    public MethodSignatureNode() {
        super(Opcodes.ASM5);
    }

    @Override
    public void visitFormalTypeParameter(String name) {
        currentVar = new TypeSignatureNode();
        currentVar.type = name;
        typeVars.put(name, currentVar);
    }

    @Override
    public void visitClassType(String name) {
        currentVar.type = name;
    }

    @Override
    public void visitInnerClassType(String name) {
        currentVar.type = name;
    }

    @Override
    public SignatureVisitor visitTypeArgument(char wildcard) {
        TypeSignatureNode t = new TypeSignatureNode();
        currentVar.typeArgs.add(t);
        return t;
    }

    @Override
    public SignatureVisitor visitParameterType() {
        TypeSignatureNode p = new TypeSignatureNode();
        parameters.add(p);
        return p;
    }

    @Override
    public SignatureVisitor visitReturnType() {
        return returnType = new TypeSignatureNode();
    }

    @Override
    public SignatureVisitor visitExceptionType() {
        TypeSignatureNode e = new TypeSignatureNode();
        exceptions.add(e);
        return e;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (typeVars.size() > 0) {
            sb.append("<");
            boolean b = false;
            for (Entry<String, TypeSignatureNode> ent : typeVars.entrySet()) {
                if (b)
                    sb.append(", ");
                sb.append(ent.getKey());
                if (ent.getValue().type != null) {

                    sb.append(" extends ").append(ent.getValue());
                }
                b = true;
            }
            sb.append("> ");
        }
        sb.append("(");
        Joiner.on(", ").appendTo(sb, parameters);
        sb.append(")").append(returnType);
        return sb.toString();
    }
}
