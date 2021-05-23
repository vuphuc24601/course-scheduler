package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static sample.monthView.crrYear;

public class weekView   {
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


}
