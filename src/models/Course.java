package models;

import sql.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class Course {
    String courseId;
    String title;
    String description;
    ArrayList<Section> sections;
    int credit;

    static DatabaseConnection connectNow = new DatabaseConnection();
    static Connection connectDB = connectNow.getConnection();

    public Course(String courseId, String title, String description, int credit) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.credit = credit;
        this.sections = new ArrayList<>();
        this.loadSections();
    }

    public String getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Section> getSections() {
        return sections;
    }

    public int getCredit() {
        return credit;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSections(ArrayList<Section> sections) {
        this.sections = sections;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public void loadSections() {
        try {

            String query = "SELECT c.course_id, c.title, c.credit, cd.start_time, cd.end_time, round(time_to_sec(timediff(end_time, start_time))/60) as duration, day, cd.description\n" +
                    "FROM course c\n" +
                    "JOIN course_date cd\n" +
                    "WHERE (c.course_id = cd.course_id) AND (c.course_id = ?)";
            PreparedStatement pst = connectDB.prepareStatement(query);
            pst.setString(1, this.courseId);
            ResultSet rs = pst.executeQuery();

            while(rs.next()) {
                String courseId = rs.getString("course_id");
                String title = rs.getString("title");
                int credit = rs.getInt("credit");
                String description = rs.getString("description");
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                int day = rs.getInt("day");
                int duration = rs.getInt("duration");

                Section newSection = new Section(description, startTime, endTime, day, duration);
                this.sections.add(newSection);
            }
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}
