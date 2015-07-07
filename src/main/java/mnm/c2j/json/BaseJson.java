package mnm.c2j.json;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

public abstract class BaseJson {

    @SerializedName("annotated")
    private List<String> annotatedWith;

    public void addAnnotated(String anno) {
        if (annotatedWith == null)
            annotatedWith = Lists.newArrayList();
        annotatedWith.add(anno);
    }

}
