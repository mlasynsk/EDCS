package ml.edcs.model;

public abstract class BaseEntity {
    private String sender;
    private Type type;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Type getType() {
        return type;
    }

    void setType(Type type) {
        this.type = type;
    }
}
