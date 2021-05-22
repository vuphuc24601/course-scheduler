package sample;

import javafx.scene.paint.Color;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.PriorityQueue;

class Date {
    /**
     *  use to format a date with
     *  dd/mm/yy and time: hh/mm
     * @params Sdate : Integer
     *              formart the date
     *              The format storing is as follow:
     *              Sdate  = {dd/mm/yy/hh/mm}
     *              for example : 18:15 on 21/05/2021 would be formated
     *              Sdate = {21,5,2021,18,15}
     *
     * @param Stime : Integer
     *              denote  time
     *              format  {hh,mm}
     *  */
    int Sdate[];
    int Stime[];
    Date  (String Sdate){
     String temp []  = Sdate.split(" ");
        /**
         * Format for string Sdate
         * dd mm yy hour minute
         */

     this.Sdate = new int[10];

     for (int i = 0 ; i <temp.length-2; i++)
         this.Sdate [i] = Integer.parseInt(temp[i]);
     for (int i = temp.length-2; i < temp.length ; i++)
         Stime[i- temp.length+3] = Integer.parseInt(temp[i]);
    }

    static int compareDate (int th [],int [] o){
        /**
         * Compare two date array
         * th: integer
         *      represent "Our" date
         * o: integer
         *      represent the other date
         */
        for (int i = 2 ; i >= 0 ; i-- )
            if (th[i] > o[i]) return 1 ;
            else
                if (th[i] < o[i]) return -1;
        return 0;
    }

    static int  compareTime (int th[], int [] o ){
        /**
         * Compare two time array
         * th: integer
         *      represent "Our" date
         * o: integer
         *      represent the other date
         */
        for (int i = 0 ; i < 2; i++)
            if (th[i] < o[i])
                return -1;
            else if (th[i] < o[i] )
                return 1;
            return 0 ;
    }
}
class Event{
    /**
     * this class is meant for describing an event
     * formart for event date  : ex: for 18:45 on 23/06/2021
     * Date formart : 18 45 23 06 2021
     * @param startDate : Date
     *                  contain time and date (following the format) of starting date
     * @param endDate : Date
     *                containt time and date of end date
     * @param title  : String
     *               title of the event
     * @param everyday: boolean
     *               if the event occur everyday
     * @param everyDate:Boolean
     *               if the event occur every week on this Date
     */
    Date startDate ;
    Date endDate;
    String title;
    boolean everyday, everyDate ;
    Color color;
    public static ArrayList<Event> EventManager;    
    {
        EventManager = new ArrayList<>();
    }

    Event (Date SD,Date ED, String title,boolean Day,Boolean Date, Color color){

        this.startDate = SD;
        this.endDate = ED;
        this.title = title;
        this.everyday  = Day;
        this.everyDate = Date;
    }
    Boolean isOverlapped (){
        for (Event eve : EventManager ){
            if (Date.compareDate(startDate.Sdate,eve.startDate.Sdate) == 0 ){
                if (Date.compareTime(startDate.Stime,eve.startDate.Stime) == 1 && Date.compareTime(startDate.Stime,eve.endDate.Stime) == -1) return true ;
                if (Date.compareTime(startDate.Stime,eve.startDate.Stime) == -1 && Date.compareTime(endDate.Stime,eve.startDate.Stime) == 1) return true ;
            }
        }
        return false ;
    }
}