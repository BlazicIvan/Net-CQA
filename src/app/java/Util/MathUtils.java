package Util;

import java.math.BigDecimal;
import java.math.MathContext;

public class MathUtils {

    private static final int DECIMAL_PLACES = 2;


    public static double roundValue(double value) {
        return roundValue(value, DECIMAL_PLACES);
    }

    public static double roundValue(double value, int decimalPlaces) {
        BigDecimal decimalValue = new BigDecimal(value);
        return decimalValue.round(new MathContext(decimalPlaces)).doubleValue();
    }

    public static String getPercentageString(double value) {
        return (value * 100) + "%";
    }

}
