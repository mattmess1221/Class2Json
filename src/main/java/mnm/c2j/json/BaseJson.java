package mnm.c2j.json;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public abstract class BaseJson {

    @SerializedName("annotated")
    private List<String> annotatedWith;

    public void addAnnotated(String anno) {
        if (annotatedWith == null)
            annotatedWith = new ArrayList<>();
        annotatedWith.add(anno);
    }

}
