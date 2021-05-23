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
    
     Date firstDate ; // denote the first day of the week;
    Date endDate;
    int Gwidth  = 143;
    int Gheight =25;
    @FXML
    AnchorPane gp;
    @FXML
    GridPane grid;


    @FXML
    void test(){
        drawEvent(53.5,13,25,143,Color.RED,"ga`");
        displayEvent(1,5,30,7,45,Color.BLUE,"vjp");
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
    void drawEvent(double x, double y, double height, double width, Color color,String title){
        Text displayTitle = new Text(title);

        StackPane SP = new StackPane();
        Rectangle rect = new Rectangle();

        SP.setPrefSize(width,height);
        SP.setLayoutX(x);
        SP.setLayoutY(y);
        SP.setAlignment(Pos.CENTER_LEFT);
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
        drawEvent (gridPaneToPixel(Shour,dayOfTheWeek)[0],startingY,endingY-startingY,Gwidth,color,title);
    }
    
//    static void addEvent (int Sdd,int Smm,int Syy,int Shour,int Sminute, int Edd,int Emm,int Eyy,int Ehour,int Eminute, String title, boolean Day, boolean Date, Color color){
//        /**
//         * @param Sdate : String
//         *        starting date  and time of the event
//         * @param Edate : String
//         *        Ending date and time of the event
//         * @param Syy  : int
//         *             denote starting year
//         * @param Smm  : int
//         *             denote starting month
//         * @param Sdd  :int
//         *             dentoe starting day
//         * @param Sdate : String
//         *        starting date  and time of the event
//         * @param Eyy  : int
//         *             denote end year
//         * @param Emm  : int
//         *             denote end month
//         * @param Edd  :int
//         *             dentoe end day
//         * BE NOTED : Format for Sdate and E date
//         *            dd mm yy hour minute
//         *  for example : 22/05/2021 2:03Pm  =>  22 5 2021 14 3
//         * @param title : String
//         *              Title of the event
//         * @param day   : boolean
//         *              check if the event recurr everyday
//         * @param date  : boolean
//         *              check if the event  recurr on this date every week
//         *
//         * Event parameters : Date SD,Date ED, String title,boolean Day,Boolean Date
//         */
//        Event eve = new Event (new Date(Sdd,Smm,Syy,Shour,Sminute), new Date(Edd,Emm,Eyy,Ehour,Eminute),title,Day,Date,color);
//        if (!eve.isOverlapped())
//            EventManager.add (eve);
//
//        if (sample.Date.compareTime (new int [] {Shour,Sminute},new int []{Ehour,Eminute}) == 1){
//            displayEvent(Sdd, Smm,Syy ,)
//        }
//    }




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
