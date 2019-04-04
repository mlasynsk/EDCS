package ml.edcs.model;

public enum Type {
    CREATE("create"), REGISTER("register"), VOTE("vote"), RESULT("result");
    private String type;

    Type(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
