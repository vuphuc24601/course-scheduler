package models;

public class Section{
    /**
     *
     * @params startTime : String hh:mm:ss 14:30:00
     *
     * */
    public String description;
    String startTime;
    String endTime;
    int day;
    int duration;
    String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};


    public Section(String description, String startTime, String endTime, int day, int duration) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
        this.duration = duration;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%s | %s %s - %s", description, days[this.getDay()],startTime.substring(0, 5), endTime.substring(0, 5));
    }
}
