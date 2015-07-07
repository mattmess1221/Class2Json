package mnm.c2j;

import org.objectweb.asm.Opcodes;

import mnm.c2j.json.AnnotationJson;
import mnm.c2j.json.BaseClassJson;
import mnm.c2j.json.ClassJson;
import mnm.c2j.json.EnumJson;
import mnm.c2j.json.InterfaceJson;

public enum Kind {
    ENUM(Opcodes.ACC_ENUM, EnumJson.class),
    ANNOTATION(Opcodes.ACC_ANNOTATION, AnnotationJson.class),
    INTERFACE(Opcodes.ACC_INTERFACE, InterfaceJson.class),
    CLASS(0, ClassJson.class),;

    private int flag;
    private Class<? extends BaseClassJson> jsonClass;

    private Kind(int flag, Class<? extends BaseClassJson> json) {
        this.flag = flag;
        this.jsonClass = json;
    }

    public static Kind getKind(int access) {
        for (Kind k : values()) {
            if ((access & k.flag) == k.flag)
                return k;
        }
        return CLASS;
    }

    public BaseClassJson newJson() {
        try {
            return jsonClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
