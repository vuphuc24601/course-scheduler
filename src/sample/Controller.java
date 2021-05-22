package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import sql.DatabaseConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
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

        System.out.println(title);
        System.out.println("add event");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }




}
