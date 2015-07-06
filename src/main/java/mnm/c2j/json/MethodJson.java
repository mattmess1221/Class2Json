package mnm.c2j.json;

import com.google.gson.annotations.SerializedName;

public class MethodJson extends CallableJson {

    @SerializedName("return")
    private String returnType;

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
}
