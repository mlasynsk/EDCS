package ml.edcs.model;

import java.util.List;

public class Voting extends BaseEntity {
    private List<String> options;

    public Voting() {
        super.setType(Type.CREATE);
    }

    public Voting(List<String> options) {
        this.options = options;
        super.setType(Type.CREATE);
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    @Override
    public boolean equals(Object o) {
        String name = getName();
        if (name != null) {
            return name.equals(o.toString());
        } else return false;
    }

    @Override
    public int hashCode() {
        String name = getName();
        return name.hashCode();
    }
}
