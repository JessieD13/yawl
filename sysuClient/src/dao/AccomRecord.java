package dao;

/**
 * Created by apple on 2018/3/19.
 */
public class AccomRecord implements java.io.Serializable{
    private Integer id;
    private String userId;
    private String specName;
    private String taskName;
    private String averageExeTime; //平均执行时间
    private Integer completedTimes;

    public AccomRecord() {}

    public AccomRecord(String userid, String specname, String taskname, String avertime, Integer completedtimes) {
        this.userId = userid;
        this.specName = specname;
        this.taskName = taskname;
        this.averageExeTime = avertime;
        this.completedTimes = completedtimes;
    }

    public Integer getId() {
        return this.id;
    }
    public void setId(Integer _id) {
        this.id = _id;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userid) {
        this.userId = userid;
    }
    public String getSpecName() {
        return this.specName;
    }
    public void setSpecName(String specname) {
        this.specName = specname;
    }
    public String getTaskName() {
        return this.taskName;
    }
    public void setTaskName(String taskname) {
        this.taskName = taskname;
    }
    public String getAverageExeTime() {
        return this.averageExeTime;
    }
    public void setAverageExeTime(String avertime) {
        this.averageExeTime = avertime;
    }
    public Integer getCompletedTimes() {
        return this.completedTimes;
    }
    public void setCompletedTimes(int times) {
        this.completedTimes = times;
    }
}
