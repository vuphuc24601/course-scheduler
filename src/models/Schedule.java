package models;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    String name;
    List<Section> sections;
    List<Course> courses;
    int totalCredits;

    public Schedule(String name) {
        this.name = name;
        this.sections = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits() {
        for (Course c: courses) {
            totalCredits += c.getCredit();
        }
    }
}
