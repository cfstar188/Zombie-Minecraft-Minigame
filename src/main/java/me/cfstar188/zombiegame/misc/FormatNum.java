package me.cfstar188.zombiegame.misc;

public class FormatNum {

    public static double getDouble(Object obj) {
        if (obj != null)
            return ((Number) obj).doubleValue();
        return 0;
    }

}
