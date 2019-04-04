package ml.edcs.model;

import java.io.Serializable;
import java.util.Date;

public class Vote extends BaseEntity implements Serializable {

    private Date receiveTime;
    private String option;

    public Vote() {
        super.setType(Type.VOTE);
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
