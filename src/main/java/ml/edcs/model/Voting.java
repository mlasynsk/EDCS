package ml.edcs.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Voting extends BaseEntity {


    private Date startTime;
    private Date resultTime;
    private List<String> options;

    public Voting() {
        super.setType(Type.OPEN_VOTING);
    }

    public Voting(List<String> options) {
        this.options = options;
        super.setType(Type.OPEN_VOTING);
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setDefaultTime() {
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.SECOND, 5);
        this.startTime = calendar.getTime();
        calendar.add(Calendar.SECOND, 10);
        this.resultTime = calendar.getTime();
    }

    public Date getResultTime() {
        return resultTime;
    }

    public void setResultTime(Date resultTime) {
        this.resultTime = resultTime;
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
