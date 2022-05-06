package it.unisa.di.dif.correlation;

import it.unisa.di.dif.pattern.ColorChannel;
import it.unisa.di.dif.pattern.Pattern;

import java.util.ArrayList;
import java.util.OptionalDouble;

public class Pearson {
    private double red_correlation = Double.NaN;
    private double green_correlation = Double.NaN;
    private double blue_correlation = Double.NaN;

    public Pearson(double red_correlation, double green_correlation, double blue_correlation) {
        this.red_correlation = red_correlation;
        this.green_correlation = green_correlation;
        this.blue_correlation = blue_correlation;
    }


    public Pearson(Pattern p1, Pattern p2) {
        if(p1 == null || p2 == null) {
            throw new IllegalArgumentException("Patterns are null");
        }

        if(!p1.equalsSize(p2)) {
            throw new IllegalArgumentException("Patterns are not of the same size");
        }

        red_correlation = computeCorrelation(p1.getColorChannel(ColorChannel.Channel.RED), p2.getColorChannel(ColorChannel.Channel.RED));
        green_correlation = computeCorrelation(p1.getColorChannel(ColorChannel.Channel.GREEN), p2.getColorChannel(ColorChannel.Channel.GREEN));
        blue_correlation = computeCorrelation(p1.getColorChannel(ColorChannel.Channel.BLUE), p2.getColorChannel(ColorChannel.Channel.BLUE));
    }

    public double getRed_correlation() {
        if (Double.isNaN(red_correlation)) {
            throw new IllegalStateException("red_correlation is not set");
        }
        return red_correlation;
    }

    public void setRed_correlation(double red_correlation) {
        this.red_correlation = red_correlation;
    }

    public double getGreen_correlation() {
        if (Double.isNaN(green_correlation)) {
            throw new IllegalStateException("green_correlation is not set");
        }
        return green_correlation;
    }

    public void setGreen_correlation(double green_correlation) {
        this.green_correlation = green_correlation;
    }

    public double getBlue_correlation() {
        if (Double.isNaN(blue_correlation)) {
            throw new IllegalStateException("blue_correlation is not set");
        }
        return blue_correlation;
    }

    public void setBlue_correlation(double blue_correlation) {
        this.blue_correlation = blue_correlation;
    }

    private double computeCorrelation(ColorChannel p1, ColorChannel p2) {
        double mean_p1 = mean(p1);
        double mean_p2 = mean(p2);

        double sum = 0;
        double sum_p1 = 0;
        double sum_p2 = 0;

        for(int i = 0; i < p1.getHeight(); i++) {
            for(int j = 0; j < p1.getWidth(); j++) {
                sum += (p1.getValue(i, j) - mean_p1) * (p2.getValue(i, j) - mean_p2);
                sum_p1 += (p1.getValue(i, j) - mean_p1) * (p1.getValue(i, j) - mean_p1);
                sum_p2 += (p2.getValue(i, j) - mean_p2) * (p2.getValue(i, j) - mean_p2);
            }
        }

        double den = Math.sqrt(sum_p1) * Math.sqrt(sum_p2);
        return sum / den;
    }

    private double mean(ColorChannel p) {
        if (p == null||p.getHeight()==0) {
            throw new IllegalArgumentException("Pattern is null or empty");
        }

        double sum = 0;

        ArrayList<Double> averages = new ArrayList<>();

        for(int j=0; j<p.getWidth(); j++) {
            for(int i=0; i<p.getHeight(); i++) {
                sum += p.getValue(i,j);
            }
            averages.add(sum/p.getHeight());
            sum = 0;
        }
        OptionalDouble average =  averages.stream().mapToDouble(a -> a).average();

        return average.isPresent() ? average.getAsDouble() : Double.NaN;
    }

    @Override
    public String toString() {
        return "Pearson{" +
                "red_correlation=" + red_correlation +
                ", green_correlation=" + green_correlation +
                ", blue_correlation=" + blue_correlation +
                '}';
    }
}
