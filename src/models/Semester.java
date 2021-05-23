package models;

import sql.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class Semester {
    String name;
    List<Course> allCourses;
    LinkedHashMap<String, List<Section>> selectedSections;

    static DatabaseConnection connectNow = new DatabaseConnection();
    static Connection connectDB = connectNow.getConnection();

    public Semester(String name) {
        this.name = name;
        this.allCourses = new ArrayList<>();
        this.selectedSections = new LinkedHashMap<>();
        this.loadAllCourses();
        //this.loadAllSections();
    }

    public void loadAllCourses() {
        try {
            Statement st = connectDB.createStatement();
            String query = "SELECT * FROM schedule.course;";
            ResultSet rs = st.executeQuery(query);

            while(rs.next()) {
                String courseId = rs.getString("course_id");
                String title = rs.getString("title");
                int credit = rs.getInt("credit");
                String description = rs.getString("description");
                Course newCourse = new Course(courseId, title, description, credit);
                allCourses.add(newCourse);
            }
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getAllCourses() {
        return allCourses;
    }

    public void setAllCourses(List<Course> allCourses) {
        this.allCourses = allCourses;
    }

    public LinkedHashMap<String, List<Section>> getSelectedSections() {
        return selectedSections;
    }

    public void setSelectedSections(LinkedHashMap<String, List<Section>> selectedSections) {
        this.selectedSections = selectedSections;
    }

    public void loadAllSections() {
        try {
            Statement st = connectDB.createStatement();
            String query = "SELECT c.course_id, c.title, c.credit, cd.start_time, cd.end_time, round(time_to_sec(timediff(end_time, start_time))/60) as duration, day, cd.description\n" +
                    "FROM course c\n" +
                    "JOIN course_date cd\n" +
                    "WHERE c.course_id = cd.course_id";
            ResultSet rs = st.executeQuery(query);

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
                if (selectedSections.containsKey(courseId)) {
                    selectedSections.get(courseId).add(newSection);
                } else {
                    selectedSections.put(courseId, Arrays.asList(newSection));
                }
            }
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

    }
}
