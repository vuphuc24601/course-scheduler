package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import sql.DatabaseConnection;

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class WeekViewController implements Initializable {
    static DatabaseConnection connectNow = new DatabaseConnection();
    static Connection connectDB = connectNow.getConnection();

    private final ObservableList<Event> course_list = FXCollections.observableArrayList();
    @FXML
    private TextField add_course;

    @FXML
    private TableColumn<Event, Pane> courses;

    public void add_course() {
        String start_date;
        String end_date;
        //String query = "SELECT * FROM "
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
