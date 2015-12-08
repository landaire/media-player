package org.madhatters.mediaplayer.util;

/**
 * Created by Lander Brandt on 12/8/15.
 */
public class TimeUtilities {
    /**
     * Formats milliseconds in m:ss format
     * @param milliseconds
     * @return
     */
    public static String formatMilliseconds(Double milliseconds) {
        milliseconds /= 1000;
        return String.format("%01.0f:%02.0f", milliseconds / 60, milliseconds % 60);
    }
}
