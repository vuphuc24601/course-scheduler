package models;

import java.util.ArrayList;

public class Schedule {
    String name;
    List<Section> sections;
    List<Course> courses;
    int totalCredits;

    public Schedule(String name) {
        this.name = name;
        this.sections = new ArrayList<>();
    }



}
