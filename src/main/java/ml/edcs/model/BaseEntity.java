package ml.edcs.model;

import java.util.Random;

public abstract class BaseEntity {
    private static final String sender = String.valueOf(new Random().nextInt());
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

    public Type getType() {
        return type;
    }

    void setType(Type type) {
        this.type = type;
    }
}
