package ml.edcs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Voting extends BaseEntity {
    private List<String> options;
    @JsonIgnore
    private Map<String, Vote> votes;

    public Voting() {
        super.setType(Type.CREATE);
    }

    public Voting(List<String> options) {
        this.options = options;
        votes = new HashMap<>();
        super.setType(Type.CREATE);
    }

    public Map<String, Vote> getVotes() {
        return votes;
    }

    public void setVotes(Map<String, Vote> votes) {
        this.votes = votes;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
