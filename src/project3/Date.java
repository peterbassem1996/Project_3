package project3;

import java.util.Calendar;

/**
 *Class for date that allows to check if it is a valid date
 *@author Peter Dawoud
 *@author Maharshi Patel
 */

public class Date implements Comparable<Date> {

    private static final int QUADRENNIAL = 4;
    private static final int CENTENNIAL = 100;
    private static final int QUADRICENTENNIAL = 400;
    private static final int NUMBER_OF_MONTHS = 12;
    private static final int LARGE_MONTH_DAYS = 31;
    private static final int SMALL_MONTH_DAYS = 30;
    private static final int FEB_LEAP_DAYS = 29;
    private static final int FEB_NON_LEAP_DAYS = 28;
    private static final int DAYS_TO_HOURS = 24;
    private static final int HOURS_TO_MINS = 60;
    private static final int MINS_TO_SECS = 60;
    private static final int SECS_TO_MS = 1000;
    private static final int MONTH_VARIANCE = 1;
    private static final int BASE_YEAR = 1900;
    private static final int LOCATION_OF_MONTH = 0;
    private static final int LOCATION_OF_DAY = 1;
    private static final int LOCATION_OF_YEAR = 2;
    private int year;
    private int month;
    private int day;

    /**
     * Parameterized Constructor that accepts date as a string and tries to set it
     * up
     * 
     * @param date Date represented as a string
     */
    public Date(String date) {
        String[] compositionOfDate = date.split("/");

        /*
         * Tries to take the first element of the array and parse it in to int and add
         * it to month. When it fails it sets it to zero.
         */
        try {
            this.month = Integer.parseInt(compositionOfDate[LOCATION_OF_MONTH]);
        } catch (Exception notNumber) {
            this.month = 0;
        }

        /*
         * Tries to take the first element of the array and parse it in to int and add
         * it to day. When it fails it sets it to zero.
         */
        try {
            this.day = Integer.parseInt(compositionOfDate[LOCATION_OF_DAY]);
        } catch (Exception notNumber) {
            this.day = 0;
        }

        /*
         * Tries to take the first element of the array and parse it in to int and add
         * it to year. When it fails it sets it to zero.
         */
        try {
            this.year = Integer.parseInt(compositionOfDate[LOCATION_OF_YEAR]);
        } catch (Exception notNumber) {
            this.year = 0;
        }
    }

    /**
     * Checking if the year is a leap year
     * 
     * @param year The year to check
     * @return true if it is a leap year, false otherwise
     */
    private static boolean isLeap(int year) {
        // Checks if the year is divisible by 4 and 100 or by 4 and 400
        return ((year % QUADRENNIAL == 0 && year % CENTENNIAL != 0)
                || (year % QUADRENNIAL == 0 && year % CENTENNIAL != 0 && year % QUADRICENTENNIAL == 0));
    }

    /**
     * Getter method for year
     * 
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * Getter method for month
     * 
     * @return month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Getter method for day
     * 
     * @return day
     */
    public int getDay() {
        return day;
    }

    /**
     * Check if the date is valid
     * 
     * @return true if the date is valid, false otherwise
     */
    public boolean isValid() {
        // checking if year is less than 1900
        if (this.year < BASE_YEAR) {
            return false;
        }

        // checking if month is in the correct range
        if (this.month > NUMBER_OF_MONTHS || this.month < 1) {
            return false;
        }

        // checking minimum days
        if (this.day < 1) {
            return false;
        }

        int monthRealNum = this.month - MONTH_VARIANCE;
        // checking number of days for large months
        if ((monthRealNum == Calendar.JANUARY || monthRealNum == Calendar.MARCH || monthRealNum == Calendar.MAY
                || monthRealNum == Calendar.JULY || monthRealNum == Calendar.AUGUST || monthRealNum == Calendar.OCTOBER
                || monthRealNum == Calendar.DECEMBER) && this.day > LARGE_MONTH_DAYS) {
            return false;
        }

        // checking number of days for large months
        if ((monthRealNum == Calendar.APRIL || monthRealNum == Calendar.JUNE || monthRealNum == Calendar.SEPTEMBER
                || monthRealNum == Calendar.NOVEMBER) && this.day > SMALL_MONTH_DAYS) {
            return false;
        }

        // checking number of days for february
        if (monthRealNum == Calendar.FEBRUARY) {
            if (isLeap(this.year)) {
                if (this.day > FEB_LEAP_DAYS) {
                    return false;
                }
            } else {
                if (this.day > FEB_NON_LEAP_DAYS) {
                    return false;
                }
            }
        }

        // checking if the date is in the future
        Calendar today = Calendar.getInstance();
        Calendar givenDay = Calendar.getInstance();
        givenDay.set(this.year, this.month - MONTH_VARIANCE, this.day);
        int differenceBetweenTwoDates = (int) ((givenDay.getTimeInMillis() - today.getTimeInMillis())
                / (DAYS_TO_HOURS * HOURS_TO_MINS * MINS_TO_SECS * SECS_TO_MS));

        if (differenceBetweenTwoDates > 0) {
            return false;
        }

        return true;
    }

    /**
     * toString() to print a date object
     * 
     * @return string representation of the date object
     */
    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }

    /**
     * CompareTo to check the date
     * 
     * @param date The date that is need to be compared
     * @return -1 if the current date is before, 0 if the current date is the same
     *         and 1 if the current date is after the compared date
     */
    @Override
    public int compareTo(Date date) {
        Calendar thisDay = Calendar.getInstance();
        Calendar givenDay = Calendar.getInstance();
        thisDay.set(this.year, this.month - MONTH_VARIANCE, this.day);
        givenDay.set(date.year, date.month - MONTH_VARIANCE, date.day);
        int differenceBetweenTwoDates = (int) ((thisDay.getTimeInMillis() - givenDay.getTimeInMillis())
                / (DAYS_TO_HOURS * HOURS_TO_MINS * MINS_TO_SECS * SECS_TO_MS));

        return differenceBetweenTwoDates;
    }

    /**
     * Equals method to check if two methods are equal
     * 
     * @param object The object that is need to be compared
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof Date) {
            Date input = (Date) object;
            return (this.day == input.getDay() && this.month == input.getMonth() && this.year == input.getYear());
        }
        return false;
    }
}
