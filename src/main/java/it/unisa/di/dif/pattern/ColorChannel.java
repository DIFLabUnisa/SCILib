package it.unisa.di.dif.pattern;

import it.unisa.di.dif.utils.Constant;

import java.io.PrintStream;
import java.util.Scanner;

public class ColorChannel {
    public static final Channel RED = Channel.RED;
    public static final Channel GREEN = Channel.GREEN;
    public static final Channel BLUE = Channel.BLUE;

    public enum Channel {
        RED, GREEN, BLUE
    }

    private float[][] data;
    private Channel channel;

    public ColorChannel(float[][] data, Channel channel) {
        this.data = data;
        this.channel = channel;
    }

    public ColorChannel(String channelStored) {
        Scanner sc = new Scanner(channelStored).useDelimiter("\n");
        int i = 0;
        while (sc.hasNextLine())
        {
            String line = sc.nextLine();
            Scanner scLine = new Scanner(line).useDelimiter(Constant.VALUE_SEPARATOR_FOR_NOISE_FILE);
            if (line.startsWith(String.valueOf(Constant.LINE_START_FOR_CHANNEL_IN_NOISE_FILE))) {
                String name = scLine.next().substring(1);
                setChannelFromName(name);
                int width = scLine.nextInt();
                int height = scLine.nextInt();
                data = new float[height][width];
            } else {
                int j = 0;
                while (scLine.hasNext()) {
                    float value = scLine.nextFloat();
                    if (data != null) {
                        data[i][j] = value;
                    } else {
                        throw new IllegalArgumentException("Invalid pattern file");
                    }
                }
                i+=1;
            }

        }
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

    public String getChannelName() {
        switch (channel) {
        case RED:
            return Constant.RED_CHANNEL_NAME;
        case GREEN:
            return Constant.GREEN_CHANNEL_NAME;
        case BLUE:
            return Constant.BLUE_CHANNEL_NAME;
        default:
            throw new IllegalArgumentException("Unknown channel");
        }
    }

    public void setChannelFromName(String channelName) {
        switch (channelName) {
            case Constant.RED_CHANNEL_NAME:
                channel = Channel.RED;
                break;
            case Constant.GREEN_CHANNEL_NAME:
                channel = Channel.GREEN;
                break;
            case Constant.BLUE_CHANNEL_NAME:
                channel = Channel.BLUE;
                break;
            default:
                throw new IllegalArgumentException("Unknown channel");
        }
    }

    protected void print(PrintStream ps) {
//        for (float[] row : data) {
//            for (int j = 0; j < data[0].length; j++) {
//                ps.print(row[j] + " ");
//            }
//            ps.println("\n");
//        }
        ps.println(this);
    }

    protected void printAsInt(PrintStream ps) {
//        for (float[] row : data) {
//            for (int j = 0; j < data[0].length; j++) {
//                ps.print(((int)row[j]) + " ");
//            }
//            ps.println("\n");
//        }
        ps.println(this.toString(true));
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

    @Override
    public String toString() {
        return toString(false);
    }

    public float[][] getCentralCropping(int width, int height) {
        if(width > data.length || height > data[0].length) {
            throw new IllegalArgumentException("Cropping size is larger than channel size");
        }
        int x = (data.length - height) / 2;
        int y = (data[0].length - width) / 2;
        int x_end = x + height;
        int y_end = y + width;
        float[][] cropped = new float[height][width];
        for(int i = x; i < x_end; i++) {
            if (y_end - y >= 0) System.arraycopy(data[i], y, cropped[i - x], y - y, y_end - y);
        }
        return cropped;
    }

    public String toString(boolean asInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(Constant.LINE_START_FOR_CHANNEL_IN_NOISE_FILE).append(this.getChannelName());
        sb.append(Constant.VALUE_SEPARATOR_FOR_NOISE_FILE).append(this.getWidth()).append(Constant.VALUE_SEPARATOR_FOR_NOISE_FILE).append(this.getHeight());
        for (float[] row : this.data) {
            for (float v : row) {
                if (asInt) {
                    sb.append((int) v);
                } else {
                    sb.append(v);
                }
                sb.append(Constant.VALUE_SEPARATOR_FOR_NOISE_FILE);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
