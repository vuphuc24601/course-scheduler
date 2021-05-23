package models;

import sql.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LoadData {

    public void load() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        Statement st = connectDB.createStatement();
        String query = "SELECT c.course_id, c.title, c.credit, cd.day, cd.description, cd.start_time, cd.end_time, timediff(cd.end_time, cd.start_time) AS duration\n" +
                "FROM course_date cd\n" +
                "JOIN course c ON c.course_id = cd.course_id ";
        ResultSet rs = st.executeQuery(query);

        while(rs.next()) {
            String courseId = rs.getString("course_id");
            String startTime = rs.getString("start_time");
            String endTime = rs.getString("end_time");
            String title = rs.getString("title");
            int credit = rs.getInt("credit");
            int day = rs.getInt("day");
            String duration = rs.getString("duration");

            System.out.println(courseId);
        }
    }

    public static void main(String[] args) throws SQLException {
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();

            Statement st = connectDB.createStatement();
            String query = "SELECT c.course_id, c.title, c.credit, cd.day, cd.description, cd.start_time, cd.end_time, timediff(cd.end_time, cd.start_time) AS duration\n" +
                    "FROM course_date cd\n" +
                    "JOIN course c ON c.course_id = cd.course_id ";
            ResultSet rs = st.executeQuery(query);

            while(rs.next()) {
                String courseId = rs.getString("course_id");
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                String title = rs.getString("title");
                int credit = rs.getInt("credit");
                int day = rs.getInt("day");
                String duration = rs.getString("duration");

                System.out.println(courseId);
            }
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}
