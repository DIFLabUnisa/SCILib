package it.unisa.di.dif.pattern;

import java.io.PrintStream;

public class ColorChannel {
    public static final Channel RED = Channel.RED;
    public static final Channel GREEN = Channel.GREEN;
    public static final Channel BLUE = Channel.BLUE;

    protected void print(PrintStream ps) {
        for (float[] row : data) {
            for (int j = 0; j < data[0].length; j++) {
                ps.print(row[j] + " ");
            }
        }
    }

    protected void printAsInt(PrintStream ps) {
        for (float[] row : data) {
            for (int j = 0; j < data[0].length; j++) {
                ps.print(((int)row[j]) + " ");
            }
            ps.println("\n");
        }
    }

    public enum Channel {
        RED, GREEN, BLUE
    }

    private float[][] data;
    private Channel channel;

    public ColorChannel(float[][] data, Channel channel) {
        this.data = data;
        this.channel = channel;
    }

    public float[][] getData() {
        return data;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setData(float[][] data) {
        this.data = data;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public float getValue(int x, int y) {
        return data[x][y];
    }

    public void setValue(int x, int y, float value) {
        data[x][y] = value;
    }

    public int getHeight() {
        return data.length;
    }

    public int getWidth() {
        return data[0].length;
    }

    public void clear() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                data[i][j] = 0;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ColorChannel) {
            ColorChannel other = (ColorChannel) obj;
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[0].length; j++) {
                    if(data[i][j] != other.data[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
}
