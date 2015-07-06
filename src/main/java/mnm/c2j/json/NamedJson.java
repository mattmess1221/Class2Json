package mnm.c2j.json;

public abstract class NamedJson extends BaseJson {

    private String name;
    private int access;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }
}
