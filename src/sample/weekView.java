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
    @FXML
    AnchorPane gp;
    @FXML
    GridPane grid;

    @FXML
    Pane testP;
    @FXML
    void test(){
        displayEvent(1,1,20,2,30,Color.RED);
    }
    @FXML
    public Node getNodeByRowColumnIndex (final int row, final int column) {
        Node result = null;
   //     ObservableList<Node> childrens = grid.getChildren();

        for (Node node : grid.getChildren()) {
            System.out.println(node);
            if(grid.getRowIndex(node) == row && grid.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }


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
    @FXML
    void displayEvent(int dayOfTheWeek, int Shour , int Sminute, int Ehour,int Eminute,Color color){
        Node Scell = getNodeByRowColumnIndex(dayOfTheWeek,Shour);
        Node Ecell = getNodeByRowColumnIndex(dayOfTheWeek,Ehour);
        double startingY = Scell.getTranslateY()+ Scell.getScaleY()*((60- Sminute)*100)/60;
        double endingy = Ecell.getTranslateY() - Ecell.getScaleY() *(Eminute)*100/60;
        drawEvent (Scell.getTranslateX(),startingY,startingY-endingy,200,color);
    }


}
