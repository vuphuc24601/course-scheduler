package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.lang.reflect.Array;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;


public class monthView {
    int crrMonth;
    int crrYear;
    static int currentWeek,currentYear,currentMonth;
    static final int latestLeapYear = 2020;  // for calculating number of leap years
    static final int numberOfDayOfMonths[]={0,31,28,31,30,31,30,31,31,30,31,30,31}; ;
    static final String [] MonthName = {"","January","Febuary","March","April","May","June","July","August","September","October","November","December"};
    static final String [] datesOfTheWeek = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    @FXML
    Label[][] dateFormat ; // for testing purpose
    // 5 row 7 column
    int row, column;
    @FXML
    AnchorPane anchorRoot;


    // labels
    public ArrayList<Label> labelList ; // main displayer
    public Label monthLabel, yearLabel;
    public void initialize() {
        displayCurrentMonth();
    }
    public int monthLimit;

    {
        this.crrMonth = 5;
        this.crrYear = 2021;
        dateFormat  = new Label[5][9]; // [row][column], the calendar is 7x5
        anchorRoot = new AnchorPane();
        for (int i = 0 ; i <=4; i++)
            for (int j = 0 ; j <=8; j++){
                dateFormat[i][j] = new Label();
                dateFormat[i][j].setText("CC");
            }

        row = 6;
        column  = 7;

    }

    // for controlling the calendar
    void setCrrMonth(int month){
        crrMonth = month;
    }
    @FXML
    void clearMonth(){
        for (Label i : labelList){
            i.setText("");
        }
    }
    @FXML
    void nextMonth (){
        crrMonth++ ;
        if (crrMonth > 12 ) {
            crrMonth = 1 ;
            crrYear += 1;
            yearLabel.setText(String.valueOf(crrYear));
        }
        clearMonth();
        monthLabel.setText(MonthName[crrMonth]);
        displayCurrentMonth();
    }
    @FXML
    void prevMonth (){
        crrMonth -- ;
        if (crrMonth <= 0 ) {
            crrMonth  = 12;
            crrYear -= 1;
            yearLabel.setText(String.valueOf(crrYear));
        }
        clearMonth();
        monthLabel.setText(MonthName[crrMonth]);
        displayCurrentMonth();
    }

    @FXML
    void displayCurrentMonth (){
        /**
         * display the month view mode
         * Be noted  date would be displayed by labelList
         */
        int crrDate =1;
        monthLimit = numberOfDayOfMonths[crrMonth];
        if (crrMonth == 2 ) monthLimit += isLeapYear(crrYear);
        for (int i = 0 ; i<= 5;i++){
            //Check for Sunday, as transfer into new week
            if (getDayOfTheWeek(crrDate,crrMonth,crrYear) == 0){
                labelList.get(getDayOfTheWeek(crrDate,crrMonth,crrYear)+7*i).setText(String.valueOf(crrDate));
                crrDate++;
            }
            // display
             while (getDayOfTheWeek(crrDate,crrMonth,crrYear) != 0 && crrDate <= monthLimit){
                    labelList.get(getDayOfTheWeek(crrDate,crrMonth,crrYear)+7*i).setText(String.valueOf(crrDate));
                    crrDate++;
             }
        }
//        int crrDayDisplayer  = 1;
//        // do first row
//        for (int i = getDayOfTheWeek(1,currentMonth,currentYear); i <=6 ;  i++){
//            dateFormat[0][i].setText (String.valueOf(crrDayDisplayer));
//            crrDayDisplayer ++ ;
//        }
//        for (int i= 1 ; i <row ; i ++ )
//            for (int j=0; j <column ; j++){
//                dateFormat[i][j].setText (String.valueOf(crrDayDisplayer));
//                crrDayDisplayer ++;
//            }
    }

    // behind the scene
    static int isLeapYear(int year){

        /**
         * @param year : int
         *  return : check if year is a leap year
         *             1 = yes
         *             0 = no
         */
        if ((latestLeapYear - year ) % 4 == 0)
            return 1;
        return 0;
    }

    static int numberOfLeapYear (int yearFrom , int yearTo){
        /**
         * @param  yearFrom :Interger
         *                  denote the starting point of the interval
         * @param  yearTo  : Interger
         *                 denote the end point of the interval
         * return: the number of leap year in this interval
         */
        int anchor = -1 ; // closest leap year to yearTo
        for (int i = yearTo; i>= yearTo-4 ; i -- )
            if (isLeapYear(i) == 1)
                anchor = i;
        if (anchor == - 1) return  0 ;
        return  (int) Math.abs(anchor - yearFrom)/4 ;
    }

    static int sumOfMonth(int monthFrom, int monthTo, int  year){
        /**
         * @param monthFrom : Interger
         *                   starting month
         * @param monthTo : Interger
         *                ending months
         * @param year : Interger
         *             to determine whether it is a leap year with 1 extra day
         * return :the total number of day from monthFrom to monthTo
         */
        int res =0;
        for (int i= monthFrom; i <= monthTo; i++) {
            res += numberOfDayOfMonths[i];
            if (i == 2)
                res +=isLeapYear(year) ;
        }
        return res ;
    }
    static int getDayOfTheWeek (int day, int month, int year){
        /**
         * @param day,month, year : all interger
         *                  denote a particular date dd/mm/yy
         * return the day of the week of that day
         *                   0 = Sunday - 6 = Saturday
         */
        // return the day of the week of the input date
        // date calculated by years apart
        // 1/1/2017 is Sunday choose that as an anchor to calculate

        int numberOfDayApart =  Math.abs(2017 -  year)*365 + numberOfLeapYear(Math.min(2017,year),Math.max(2017,year)) ;

        if (2017 <= year) {
            // if the year consider is later than 2017
            numberOfDayApart += sumOfMonth(1,month-1,year);
            numberOfDayApart += day ;
            if (2017 == year ) numberOfDayApart -=1;
        }
        else    // if year is less than 2017
            numberOfDayApart = sumOfMonth(month+1,12,year) + numberOfDayOfMonths[month] - day +1;

        return  numberOfDayApart%7;
    }
    int  tinder (int day){
        /**
         * Return the first Date in the current month match with day of the week
         * @param day : Interger
         *            0 = Sunday ->  6 = Saturday
         *            denote the day of the  week
         */
        for (int i = 1; i <= monthLimit ; i++)
            if (getDayOfTheWeek(i,crrMonth,crrYear) == day)
                return i;
        return 0;
    }

    // resolve the event stuff
    void addEvent (String Sdate, String Edate, String title, boolean Day, boolean Date, Color color){
        /**
         * @param Sdate : String
         *        starting date  and time of the event
         * @param Edate : String
         *        Ending date and time of the event
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
         * Date  parameters : String Sdate;
         */
        Event eve = new Event (new Date(Sdate), new Date(Edate),title,Day,Date,color);
        if (!eve.isOverlapped())
            Event.EventManager.add (eve);
    }
 
}

