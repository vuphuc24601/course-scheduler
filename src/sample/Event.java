package sample;

import java.util.ArrayList;

public class Event {
    String title;
    String description;
    String startTime;
    String endTime;
    String startDate;
    String endDate;
    String color;
    ArrayList<Integer> recur;


    Event(String title, String description, String startTime, String endTime,
          String startDate, String endDate, ArrayList<Integer> recur) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.recur = recur;
    }
}

class Course extends Event {
    ArrayList<String> instructors;
    int credit;
    String course_id;

    Course(String title, String description, String startTime, String endTime,
           String startDate, String endDate, ArrayList<Integer> recur,
           ArrayList<String> instructors, int credit, String course_id) {
        super(title, description, startTime, endTime, startDate, endDate, recur);
        this.instructors = instructors;
        this.credit = credit;
        this.course_id = course_id;
    }

}
