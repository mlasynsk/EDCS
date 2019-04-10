package ml.edcs.model;


import java.util.Map;

public class Result extends BaseEntity {
    Map<String, Integer> votes;

    public Result() {
        super.setType(Type.RESULT);
    }

    public Map<String, Integer> getVotes() {
        return votes;
    }

    public void setVotes(Map<String, Integer> votes) {
        this.votes = votes;
    }
}
