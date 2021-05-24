package sample;

public class Date {
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
    public int Sdate[];
    public int Stime[];
    public Date  (int dd, int mm ,int yy, int hour ,int minute){
        /**
         * Format for string Sdate
         * dd mm yy hour minute
         */

        this.Sdate = new int[]{dd, mm, yy};
        this.Stime = new int[]{hour, minute};
    }

    public static int compareDate (int th [],int [] o){
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

    public static int  compareTime (int th[], int [] o ){
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
            else if (th[i] > o[i] )
                return 1;
        return 0 ;
    }
    static boolean isDuring (Date start, Date end, Date ck){
        if (ck.Sdate[2] < start.Sdate[2] || ck.Sdate[2] > end.Sdate[2]) return false;
        if (ck.Sdate[1] < start.Sdate[1] || ck.Sdate[1] > end.Sdate[1]) return false;
        if (ck.Sdate[0] < start.Sdate[0] || ck.Sdate[1]  > end.Sdate[1]) return false ;
        return true;
    }
    
    @Override
    public String toString() {
        return String.format("%d/%d/%d", this.Sdate[0], this.Sdate[1], this.Sdate[2]);
    }
}
