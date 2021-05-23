package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import sql.DatabaseConnection;

import java.net.URL;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.sql.Connection;

public class Controller implements Initializable {

    @FXML
    TextField title_id;
    @FXML
    TextField description_id;
    @FXML
    HBox dayCheckBox;

    @FXML
    private ComboBox<String> start_hour;

    @FXML
    private ComboBox<String> start_min;

    @FXML
    private ComboBox<String> start_period;

    @FXML
    private ComboBox<String> end_hour;

    @FXML
    private ComboBox<String> end_min;

    @FXML
    private ComboBox<String> end_period;

    @FXML
    private DatePicker start_date;

    @FXML
    private DatePicker end_date;

    ArrayList<String> days = new ArrayList<String>();
    String startTime;
    String endTime;
    String startDate;
    String endDate;

    static DatabaseConnection connectNow = new DatabaseConnection();
    static Connection connectDB = connectNow.getConnection();

    public void addEvent() {
        String title = title_id.getText();
        String description = description_id.getText();

        ObservableList<Node> children = dayCheckBox.getChildrenUnmodifiable();
        for (Node child: children) {
            if (child instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) child;
                if (checkBox.isSelected()) {
                    System.out.println(checkBox.getText());
                }
            }
        }

        System.out.println(start_hour.getValue());


        if (start_date.getValue() != null && end_date.getValue() != null) {
            String start_date_str = start_date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String end_date_str = end_date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            System.out.println(start_date_str);
            System.out.println(end_date_str);
        }


        System.out.println(title);
        System.out.println("add event");




    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        start_hour.getItems().addAll(
                "01",
                "02",
                "03",
                "04",
                "06",
                "07",
                "08",
                "09",
                "10",
                "11",
                "12"
        );

        start_min.getItems().addAll(
                "00",
                "05",
                "10",
                "15",
                "20",
                "25",
                "30",
                "35",
                "40",
                "45",
                "50",
                "55"
        );

        start_period.getItems().addAll(
                "am",
                "pm"
        );

        end_hour.getItems().addAll(
                "01",
                "02",
                "03",
                "04",
                "06",
                "07",
                "08",
                "09",
                "10",
                "11",
                "12"
        );

        end_min.getItems().addAll(
                "00",
                "05",
                "10",
                "15",
                "20",
                "25",
                "30",
                "35",
                "40",
                "45",
                "50",
                "55"
        );

        end_period.getItems().addAll(
                "am",
                "pm"
        );

        try {
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
