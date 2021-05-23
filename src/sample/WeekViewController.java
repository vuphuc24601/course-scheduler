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

        Date firstDate ; // denote the first day of the week;
    Date endDate;
    int Gwidth= 100;
    int Gheight =63;
    @FXML
    AnchorPane gp;
    @FXML
    GridPane grid;


    @FXML
    void test(){
        //drawEvent(99,30,63,100,Color.RED);
        displayEvent(1,1,30,2,30,Color.BLUE);
    }

//    public Node getNodeByRowColumnIndex (final int row, final int column) {
//        Node result = null;
//   //     ObservableList<Node> childrens = grid.getChildren();
//
//        for (Node node : grid.getChildren()) {
//
//            if(grid.getRowIndex(node) == row && grid.getColumnIndex(node) == column) {
//                result = node;
//                break;
//            }
//        }
//
//        return result;
//    }



    @FXML
    void drawEvent(double x, double y, double height, double width, Color color){
        Rectangle rect = new Rectangle();
        rect.setX(x);

        rect.setY(y);
        rect.setHeight(height);
        rect.setWidth (width);
        rect.setFill(color);
        gp.getChildren().add(rect);
    }
    Double [] gridPaneToPixel(int row ,int column){
        Double [] pix;
        double firstCollumnX = 99;
        double firstRowy     = 30 ;
        pix = new Double[]{firstCollumnX + (column - 1) * Gwidth, firstRowy + (row - 1) * Gheight};
        return pix ;
    }

    @FXML
    void displayEvent(int dayOfTheWeek, int Shour , int Sminute, int Ehour,int Eminute,Color color){
//        Node Scell = getNodeByRowColumnIndex(dayOfTheWeek,Shour);
//        Node Ecell = getNodeByRowColumnIndex(dayOfTheWeek,Ehour);
        double startingY = gridPaneToPixel(Shour,dayOfTheWeek)[1] + Gheight*(Sminute)/60 ;
        double endingY = gridPaneToPixel(Ehour,dayOfTheWeek)[1] + (Gheight*Eminute)/60;
        drawEvent (gridPaneToPixel(dayOfTheWeek,Shour)[0],startingY,endingY-startingY,Gwidth,color);
    }

    public void add_course() {
        String start_date;
        String end_date;
        //String query = "SELECT * FROM "
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
