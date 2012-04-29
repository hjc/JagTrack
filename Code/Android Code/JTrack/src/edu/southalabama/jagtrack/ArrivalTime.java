package edu.southalabama.jagtrack;

public class ArrivalTime
{
    /*Define Class Variables*/
    private int hours;
    private int minutes;
    private int seconds;
    /*End Define Class Variables*/

    public ArrivalTime(int hours, int minutes, int seconds)//Constructor
    {
        this.hours = hours;//Sets hours
        this.minutes = minutes;//Sets minutes
        this.seconds = seconds;//Sets seconds
    }

    /**
     * Returns the hours the JagTran will take
     * @return the hours the JagTran will take
     **/
    public int getHours()
    {
        return this.hours;
    }

    /**
     * Returns the minutes the JagTran will take
     * @return the minutes the JagTran will take
     **/
    public int getMinutes()
    {
        return this.minutes;
    }

    /**
     * Returns the seconds the JagTran will take
     * @return the seconds the JagTran will take
     **/
    public int getSeconds()
    {
        return this.seconds;
    }

    /**
     * Returns a String containing the hours, minutes, and seconds.
     * @return hours, minutes, seconds
     **/
    public String toString()
    {
        return hours + " hours " + minutes + " minutes " + seconds + " seconds";
    }

}
