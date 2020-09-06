package Util;

import java.util.ArrayList;
import java.util.List;

public class AverageCalculator {

    private final List<Double> elements;

    public AverageCalculator() {
        elements = new ArrayList<>();
    }

    public void addElement(double element) {
        elements.add(element);
    }

    public double getAverage() {
        double sum = 0;
        for (Double element : elements) {
            sum += element;
        }

        return sum/(double)elements.size();
    }
}
