package me.cfstar188.zombiegame.misc;

public class FormatTime {

    // formats an hoursDouble into a String of the form "X hours Y minutes"
    public static String formatHours(double hoursDouble) {

        // extract hours and minutes
        int hours = (int) hoursDouble;
        int minutes = (int) Math.round((hoursDouble - hours) * 60);

        // special case: both hours and minutes are 0
        if (hours == 0 && minutes == 0)
            return "1 minute";

        // convert minutes to hours if it reaches or exceeds 60
        if (minutes == 60) {
            hours += 1;
            minutes = 0;
        }

        // construct hour and minute parts
        String hourPart = hours > 0 ? (hours == 1 ? "1 hour" : hours + " hours") : "";
        String minutePart = minutes > 0 ? (minutes == 1 ? "1 minute" : minutes + " minutes") : "";

        // combine parts
        return hourPart + (hourPart.isEmpty() || minutePart.isEmpty() ? "" : " ") + minutePart;

    }

}
