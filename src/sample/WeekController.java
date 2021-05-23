package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Course;
import models.Semester;
import sql.DatabaseConnection;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;


public class WeekController implements Initializable {
    @FXML
    private GridPane scheduleGridPane;

    @FXML
    private TextField searchCourse;

    @FXML
    private ListView availableCourses;

    @FXML
    private Button addCourseButton;

    @FXML
    private ListView selectedCoursesListView, sectionListView;

    @FXML
    private Button removeCourseButton;

    @FXML
    private TabPane sectionTabPane;

    // List of all courses for current semester
    private FilteredList<String> courseList;

    private Course focusedCourse;

    static Semester currentSemester = new Semester("Spring 2021");

    private ArrayList<String> allCourse = new ArrayList<>();
    private HashSet<String> selectedCourses = new HashSet<>();

    static DatabaseConnection connectNow = new DatabaseConnection();
    static Connection connectDB = connectNow.getConnection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            Statement st = connectDB.createStatement();
            String query = "SELECT * FROM schedule.course;";
            ResultSet rs = st.executeQuery(query);

            while(rs.next()) {
                String courseId = rs.getString("course_id");
                String title = rs.getString("title");
                String course = String.format("%s: %s\n", courseId, title);
                System.out.println(course);
                allCourse.add(course);
            }
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

    }

    private void buttonSetup() {
        Arrays.asList(this.addCourseButton, this.removeCourseButton).forEach((button) -> {
            button.defaultButtonProperty().bind(button.focusedProperty());
        });
    }

    public void loadAllCourses() {
        ObservableList<String> options = FXCollections.observableArrayList(allCourse);
        this.courseList = new FilteredList<>(options, s -> true);
        this.availableCourses.setItems(this.courseList);

        searchCourse.textProperty().addListener(obs -> {
            String filter = this.searchCourse.getText().toLowerCase();
            // If user enter nothing then show all courses
            if (filter == null || filter.length() == 0) {
                this.courseList.setPredicate(s -> true);
            } else {
                this.courseList.setPredicate(s -> s.toLowerCase().contains(filter));
            }
        });
    }


    public void addSelectedCourse(ActionEvent _event) {
        if (this.availableCourses.getFocusModel().getFocusedItem() != null) {
            String courseId = this.availableCourses.getFocusModel().getFocusedItem().toString().split(":")[0];

            try {
                //Statement st = connectDB.createStatement();
                String query = "SELECT * FROM schedule.course WHERE course_id = ?";
                PreparedStatement pst = connectDB.prepareStatement(query);
                pst.setString(1, courseId);
                ResultSet rs = pst.executeQuery();


                while(rs.next()) {
                    //String courseId = rs.getString("course_id");
                    String title = rs.getString("title");
                    int credit = rs.getInt("credit");
                    String description = rs.getString("description");
                    Course newCourse = new Course(courseId, title, description, credit);
                    String displayedCourse = String.format("%s: %s\n%d Credit(s)", courseId, title, credit);
                    this.selectedCoursesListView.getItems().add(displayedCourse);
                    this.createNewTab(newCourse);


                }
            } catch (Exception e) {
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
            }
        }
    }

    public void removeSelectedCourse(ActionEvent _event) {

        if (this.selectedCoursesListView.getSelectionModel().getSelectedItem() != null) {
            int index = this.selectedCoursesListView.getSelectionModel().getSelectedIndex();
            this.sectionTabPane.getTabs().remove(index);
            String courseToRemove = ((String) this.selectedCoursesListView.getSelectionModel().getSelectedItem()).trim().split(":")[0];
            int ind = selectedCoursesListView.getSelectionModel().getSelectedIndex();
            selectedCoursesListView.getItems().remove(ind);
            System.out.println(courseToRemove);
            this.selectedCoursesListView.getItems().remove(courseToRemove);
            System.out.println(courseToRemove);

            // Clears the section listview if the focus is on the course to be removed.
            if (this.focusedCourse != null && this.focusedCourse.getCourseId().equalsIgnoreCase(courseToRemove)) {
                this.sectionListView.getItems().clear();
            }

            //WeekController.currentSemester.generateSchedules();
            //this.regenerateSchedules();
        }
    }

    public void loadCourseSections(ActionEvent _event) {
        System.out.println("ok");
        if (this.selectedCoursesListView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        String currentSelection = this.selectedCoursesListView.getSelectionModel().getSelectedItem().toString().split(":")[0];

        for (Course course : currentSemester.getAllCourses()) {
            if (course.getCourseId().equals(currentSelection)) {
                this.focusedCourse = course;
                System.out.println("ok");
                break;
            }
        }

        List<String> listCellLabels = new ArrayList<>();
        this.focusedCourse.getSections().forEach((section) -> {
            listCellLabels.add(section.toString());
        });

        this.sectionListView.setItems(FXCollections.observableList(listCellLabels));

    }

    public void loadSelectedCourses(Course course) {
        this.sectionTabPane.getTabs().clear();
        for (Course c : currentSemester.getAllCourses()) {
            if (c.getCourseId().equals(course.getCourseId())) {
                this.createNewTab(c);
            }
        }

    }

    public void createNewTab(Course course) {
        Tab tab = new Tab();
        tab.setText(course.getCourseId());
        VBox content = new VBox();
        ScrollPane pane = new ScrollPane();
        course.getSections().stream().map((section) -> {
            VBox sectionEntry = new VBox();
            sectionEntry.setPrefHeight(30);
            sectionEntry.setMinHeight(30);
            VBox.setVgrow(content, Priority.NEVER);
            sectionEntry.setAlignment(Pos.CENTER_LEFT);
            sectionEntry.setStyle("-fx-border-color: grey; -fx-border-width: 0 0 .5 0;");
            CheckBox checkBox = new CheckBox();
            checkBox.setSelected(true);
            VBox.setMargin(checkBox, new Insets(0, 0, 0, 10));
            checkBox.setText(section.toString());
            sectionEntry.getChildren().add(checkBox);
            return sectionEntry;
        }).forEachOrdered((sectionEntry) -> {
            content.getChildren().add(sectionEntry);
        });
        pane.setContent(content);
        tab.setContent(pane);
        this.sectionTabPane.getTabs().add(tab);
    }



}
