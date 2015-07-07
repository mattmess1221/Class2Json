package mnm.c2j.json;

import com.google.gson.annotations.SerializedName;

public class MethodJson extends CallableJson {

    @SerializedName("return")
    private String returnType;
    @SerializedName("default")
    private AnnotationInstanceJson.Value defaultValue;

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public void setDefaultValue(AnnotationInstanceJson.Value defaultValue) {
        this.defaultValue = defaultValue;
    }
}
