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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import models.Course;
import models.Schedule;
import models.Section;
import models.Semester;
import sql.DatabaseConnection;


import javax.swing.*;
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

    @FXML
    private Button submitButton;

    @FXML
    private Label numCourses;

    @FXML
    private Label numCredits;


    // List of all courses for current semester
    private FilteredList<String> courseList;

    private Course focusedCourse;

    static Semester currentSemester = new Semester("Spring 2021");

    private ArrayList<String> allCourse = new ArrayList<>();
    private ArrayList<Course> selectedCourses = new ArrayList<>();

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

        Schedule schedule = new Schedule("Spring 2021");
        loadHistory();


    }

    private void buttonSetup() {
        Arrays.asList(this.addCourseButton, this.removeCourseButton).forEach((button) -> {
            button.defaultButtonProperty().bind(button.focusedProperty());
        });
    }

    public void displayCoursesAndCredits() {
        String numCourse = String.valueOf(selectedCourses.size());
        numCourses.setText(numCourse + " Course(s)");
        int credit = 0;
        for (Course c:selectedCourses) {
            credit += c.getCredit();
        }
        String numCredit = String.valueOf(credit);
        numCredits.setText(numCredit + " Credit(s)");
    }


    public void loadHistory() {
        List<Section> sections = new ArrayList<>();
        try {
            String query = "SELECT DISTINCT(c.course_id), c.title, c.credit, c.description, c.color\n" +
                    "FROM course c\n" +
                    "JOIN schedule_1 cd\n" +
                    "WHERE (c.course_id = cd.course_id);";
            PreparedStatement pst = connectDB.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while(rs.next()) {
                String courseId = rs.getString("course_id");
                String title = rs.getString("title");
                int credit = rs.getInt("credit");
                String description = rs.getString("description");
                Course newCourse = new Course(courseId, title, description, credit);
                String displayedCourse = String.format("%s: %s\n%d Credit(s)", courseId, title, credit);
                this.selectedCoursesListView.getItems().add(displayedCourse);
                this.createNewTab(newCourse);
                selectedCourses.add(newCourse);
            }
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        displayCoursesAndCredits();
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
            for (Course c:selectedCourses) {
                if (courseId.equals(c.getCourseId())) {
                    return;
                }
            }

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
                    selectedCourses.add(newCourse);


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

            for (Course c:selectedCourses) {
                System.out.println(c);
            }
            selectedCourses.remove(ind);
            for (Course c:selectedCourses) {
                System.out.println(c);
            }

            // Clears the section listview if the focus is on the course to be removed.
            if (this.focusedCourse != null && this.focusedCourse.getCourseId().equalsIgnoreCase(courseToRemove)) {
                this.sectionListView.getItems().clear();
            }
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

    public LinkedHashMap <String, List<Section>> selectedSections;

    public LinkedHashMap<String, List<Section>> addSelectedSections(ActionEvent _event) {
        Section section;
        Tab tab;
        Course course;
        LinkedHashMap<String, List<Section>> sections = new LinkedHashMap<>();

        // iterate each tab
        for (int i=0; i<this.sectionTabPane.getTabs().size();i++) {
            tab = this.sectionTabPane.getTabs().get(i);
            course = selectedCourses.get(i);
            System.out.println(course.getCourseId());
            List<Section> selected = new ArrayList<>();
            VBox container = (VBox) ((ScrollPane) tab.getContent()).getContent();
            // iterate each section in a tab
            for (int j=0; j<container.getChildren().size(); j++) {
                VBox entry = (VBox) container.getChildren().get(j);
                CheckBox checkBox = (CheckBox) entry.getChildren().get(0);
                if (checkBox.isSelected()) {
                    selected.add(course.getSections().get(j));
                }
            }
            sections.put(course.getCourseId(), selected);
        }

        // display num of course and credit
        displayCoursesAndCredits();

        // Add to database




        try {
            Statement st = connectDB.createStatement();
            String query = "TRUNCATE TABLE schedule.schedule_1;";
            st.executeUpdate(query);

            for (Map.Entry<String, List<Section>> entry : sections.entrySet()) {
                for (Section s : entry.getValue()) {
                    String queryInsert = "INSERT INTO schedule.schedule_1 VALUES\n" +
                            "(NULL, ?, ?, ?, ?, ?)";
                    PreparedStatement pst = connectDB.prepareStatement(queryInsert);
                    pst.setString(1, entry.getKey());
                    pst.setString(2, s.getStartTime());
                    System.out.println(s.getStartTime());
                    pst.setString(3, s.getEndTime());
                    pst.setInt(4, s.getDay());
                    pst.setString(5, s.getDescription());
                    System.out.println(s.getDescription());
                    pst.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

        selectedSections = sections;
        courseDisplayer();
        weekViewMainEventDisplay ();
        test();
        return sections;
    }

    void courseDisplayer (){
        for (Map.Entry<String,List<Section>> entry : selectedSections.entrySet()){
            String id = entry.getKey();
            for (Section i : entry.getValue()){
                String Stime[] = i.getStartTime().split(":");
                String Etime[] = i.getEndTime().split(":");
                int day =  i.getDay()-1;
                addEvent (MonthView.tinder(day,1,2021),1,2021,Integer.parseInt(Stime[0]),Integer.parseInt(Stime[1]),23  ,6,2021,Integer.parseInt(Etime[0]),Integer.parseInt(Etime[1]),i.getDescription(),false,true,Color.AQUA);
            }
        }
    }
// --------------------------------------------------------------------------

    Date firstDate = new Date (16,5,2021,0,0) ; // denote the first day of the week;
    Date endDate = new Date (23,5,2021,0,0);
    Double Gwidth  = 143.0;
    Double Gheight =26.375;
    @FXML
    AnchorPane gp;
    @FXML
    GridPane grid;


    @FXML
    void test(){
        drawEvent(53.5,13,25,143,Color.RED,"ga`");
        displayEvent(1,11,00,13,15,Color.BLUE,"vjp");
    }





    @FXML
    void drawEvent(double x, double y, double height, double width, Color color,String title){
        Text displayTitle = new Text(title);
        StackPane SP = new StackPane();
        Rectangle rect = new Rectangle();

        SP.setPrefSize(width,height);
        SP.setLayoutX(x);
        SP.setLayoutY(y);
        SP.setAlignment(Pos.CENTER);
        rect.setX(x);
        rect.setY(y);
        rect.setHeight(height);
        rect.setWidth (width);
        rect.setFill(color);
        SP.getChildren().addAll(rect,displayTitle);
        gp.getChildren().add(SP);
    }

    Double [] gridPaneToPixel(int row ,int column){
        Double [] pix;
        double firstCollumnX = 53.5;
        double firstRowy     = 13 ;
        pix = new Double[]{firstCollumnX + (column - 1) * Gwidth, firstRowy + (row - 1) * Gheight};
        return pix ;
    }

    @FXML
       void displayEvent(int dayOfTheWeek, int Shour , int Sminute, int Ehour,int Eminute,Color color, String title){
//        Node Scell = getNodeByRowColumnIndex(dayOfTheWeek,Shour);
//        Node Ecell = getNodeByRowColumnIndex(dayOfTheWeek,Ehour);
        double startingY = gridPaneToPixel(Shour,dayOfTheWeek)[1] + Gheight*(Sminute)/60 ;
        double endingY = gridPaneToPixel(Ehour,dayOfTheWeek)[1] + (Gheight*Eminute)/60;
        if (Date.compareTime(new int []{Shour,Sminute},new int []{Ehour,Eminute}) == 1){
            displayEvent(dayOfTheWeek, Shour , Sminute, 25,00,color, title);
            if (dayOfTheWeek <=6)
                    displayEvent(dayOfTheWeek+1, 1, 00, Ehour,Eminute,color, title);
        }
        else
        drawEvent (gridPaneToPixel(Shour,dayOfTheWeek)[0],startingY,endingY-startingY,Gwidth,color,title);
    }

   static void addEvent (int Sdd,int Smm,int Syy,int Shour,int Sminute, int Edd,int Emm,int Eyy,int Ehour,int Eminute, String title, boolean Day, boolean Date, Color color){
        /**
         * @param Sdate : String
         *        starting date  and time of the event
         * @param Edate : String
         *        Ending date and time of the event
         * @param Syy  : int
         *             denote starting year
         * @param Smm  : int
         *             denote starting month
         * @param Sdd  :int
         *             dentoe starting day
         * @param Sdate : String
         *        starting date  and time of the event
         * @param Eyy  : int
         *             denote end year
         * @param Emm  : int
         *             denote end month
         * @param Edd  :int
         *             dentoe end day
         * BE NOTED : Format for Sdate and E date
         *            dd mm yy hour minute
         *  for example : 22/05/2021 2:03Pm  =>  22 5 2021 14 3
         * @param title : String
         *              Title of the event
         * @param day   : boolean
         *              check if the event recurr everyday
         * @param date  : boolean
         *              check if the event  recurr on this date every week
         *
         * Event parameters : Date SD,Date ED, String title,boolean Day,Boolean Date
         */
        Event eve = new Event (new Date(Sdd,Smm,Syy,Shour,Sminute), new Date(Edd,Emm,Eyy,Ehour,Eminute),title,Day,Date,color);
        if (!eve.isOverlapped())
            Event.EventManager.add (eve);
    }

    @FXML
    void weekViewMainEventDisplay (){
        Date crrDate = firstDate;
        for (int i = 0 ; i <=6;i++)
        {
            for (sample.Event eve : Event.EventManager){
                if (Date.isDuring(eve.startDate,eve.endDate,crrDate))
                  //condition to display
                  //int dayOfTheWeek, int Shour , int Sminute, int Ehour,int Eminute,Color color, String title
                      if  (eve.everyday){
                          displayEvent(i,eve.startDate.Stime[0],eve.startDate.Stime[1],eve.endDate.Stime[0],eve.endDate.Stime[1],eve.color,eve.title);
                          System.out.println ("1");
                      }
                      else if (eve.everyDate && MonthView.getDayOfTheWeek(eve.startDate.Sdate[0],eve.startDate.Sdate[1],eve.startDate.Sdate[2]) == i)
                      {
                          displayEvent(i,eve.startDate.Stime[0],eve.startDate.Stime[1],eve.endDate.Stime[0],eve.endDate.Stime[1],eve.color,eve.title);
                          System.out.println ("2");
                      }
                      else
                      if (Date.compareTime(eve.startDate.Sdate,crrDate.Sdate)==0)
                      {
                          displayEvent(i,eve.startDate.Stime[0],eve.startDate.Stime[1],eve.endDate.Stime[0],eve.endDate.Stime[1],eve.color,eve.title);
                          System.out.println ("3");
                      }

            }
            crrDate =  sample.MonthView.nextDate(crrDate);

        }
    }



}
