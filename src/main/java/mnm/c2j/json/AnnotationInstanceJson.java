package mnm.c2j.json;

import java.util.Map;

import org.objectweb.asm.Type;

import com.google.common.collect.Maps;
import com.google.common.primitives.Primitives;

public class AnnotationInstanceJson {

    // annotations cannot have generic types
    private String type;
    private Map<String, Value> args;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void addArg(String name, Value arg) {
        if (args == null)
            args = Maps.newHashMap();
        args.put(name, arg);
    }

    public static class Value {

        private String type;
        private Object value;

        public Value(String type, Object value) {
            if (value instanceof Type) {
                type = "java.lang.Class";
                value = ((Type) value).getClassName();
            } else if (Primitives.isWrapperType(value.getClass())) {
                type = Primitives.unwrap(value.getClass()).getCanonicalName();
            }
            this.type = type;
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public Object getValue() {
            return value;
        }
    }
}
