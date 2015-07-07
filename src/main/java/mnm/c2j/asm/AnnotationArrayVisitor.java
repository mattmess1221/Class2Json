package mnm.c2j.asm;

import java.util.List;

import com.google.common.collect.Lists;

import mnm.c2j.json.AnnotationInstanceJson;

public class AnnotationArrayVisitor extends BaseAnnotationVisitor {

    private List<AnnotationInstanceJson.Value> list = Lists.newArrayList();

    @Override
    protected void add(String name, String type, Object value) {
        list.add(new AnnotationInstanceJson.Value(type, value));
    }

    public List<AnnotationInstanceJson.Value> getList() {
        return list;
    }
}
