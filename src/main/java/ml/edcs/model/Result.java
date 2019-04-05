package ml.edcs.model;


import java.util.List;

public class Result extends BaseEntity{
    public Result() {
        super.setType(Type.RESULT);
    }

    List<Vote> votes;

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
}
