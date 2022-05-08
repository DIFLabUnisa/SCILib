package it.unisa.di.dif.pattern;

import it.unisa.di.dif.utils.CHILogger;
import it.unisa.di.dif.utils.Constant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public abstract class GenericPattern implements Pattern {
    private ColorChannel red;
    private ColorChannel green;
    private ColorChannel blue;

    private final CHILogger logger = CHILogger.getInstance();
    private final Constant constant = Constant.getInstance();

    public GenericPattern(int height, int width) {
        float[][] red_data = new float[height][width];
        float[][] green_data = new float[height][width];
        float[][] blue_data = new float[height][width];
        this.red = new ColorChannel(red_data, ColorChannel.Channel.RED);
        this.green = new ColorChannel(green_data, ColorChannel.Channel.GREEN);
        this.blue = new ColorChannel(blue_data, ColorChannel.Channel.BLUE);
        this.clear();
    }

    public GenericPattern() {}

    @Override
    public ColorChannel getColorChannel(ColorChannel.Channel channel) {
        switch (channel) {
            case RED:
                return red;
            case GREEN:
                return green;
            case BLUE:
                return blue;
            default:
                throw new IllegalArgumentException("Invalid channel");
        }
    }

    @Override
    public ColorChannel getRedChannel() {
        return getColorChannel(ColorChannel.RED);
    }

    @Override
    public ColorChannel getGreenChannel() {
        return getColorChannel(ColorChannel.GREEN);
    }

    @Override
    public ColorChannel getBlueChannel() {
        return getColorChannel(ColorChannel.BLUE);
    }

    @Override
    public int getHeight() {
        return green.getHeight();
    }

    @Override
    public int getWidth() {
        return green.getWidth();
    }

    @Override
    public float getValue(int x, int y, ColorChannel.Channel channel) {
        switch (channel) {
            case RED:
                return red.getValue(x, y);
            case GREEN:
                return green.getValue(x, y);
            case BLUE:
                return blue.getValue(x, y);
            default:
                throw new IllegalArgumentException("Invalid channel");
        }
    }

    @Override
    public int getValueAsInteger(int x, int y, ColorChannel.Channel channel) {
        return (int) getValue(x, y, channel);
    }

    @Override
    public void clear() {
        if (red != null) {
            red.clear();
        }
        if (green != null) {
            green.clear();
        }
        if (blue != null) {
            blue.clear();
        }
    }

    @Override
    public String getChannelName(ColorChannel.Channel channel) {
        switch (channel) {
            case RED:
                return "Red";
            case GREEN:
                return "Green";
            case BLUE:
                return "Blue";
            default:
                throw new IllegalArgumentException("Invalid channel");
        }
    }

    @Override
    public void setHeight(int height) {}

    @Override
    public void setWidth(int width) {}

    @Override
    public void setChannel(ColorChannel value) {
        switch (value.getChannel()) {
            case RED:
                red = value;
                break;
            case GREEN:
                green = value;
                break;
            case BLUE:
                blue = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid channel");
        }
    }

    @Override
    public void setRedChannel(ColorChannel value)
    {
        if (value.getChannel() != ColorChannel.Channel.RED ){
            throw new IllegalArgumentException("Invalid channel");
        }
        setChannel(value);
    }

    @Override
    public void setGreenChannel(ColorChannel value) {
        if (value.getChannel() != ColorChannel.Channel.GREEN ){
            throw new IllegalArgumentException("Invalid channel");
        }
        setChannel(value);
    }

    @Override
    public void setBlueChannel(ColorChannel value) {
        if(value.getChannel() != ColorChannel.Channel.BLUE ){
            throw new IllegalArgumentException("Invalid channel");
        }
        setChannel(value);
    }

    @Override
    public void storeNoise(File f, boolean asInt) {
        if(f == null) {
            logger.log.warn("File is null");
            throw new IllegalArgumentException("File is null");
        }

        try (PrintStream ps = new PrintStream(f)) {

            ps.print(Constant.LINE_START_FOR_INFO_IN_NOISE_FILE +
                    getWidth() + Constant.VALUE_SEPARATOR_FOR_NOISE_FILE + getHeight() + "\n");

            if(asInt) {
                printChannelAsInt(ColorChannel.RED, ps);
                printChannelAsInt(ColorChannel.GREEN, ps);
                printChannelAsInt(ColorChannel.BLUE, ps);
            } else {
                printChannel(ColorChannel.RED, ps);
                printChannel(ColorChannel.GREEN, ps);
                printChannel(ColorChannel.BLUE, ps);
            }
        } catch (FileNotFoundException e) {
            if (constant.isWriteMessageLogOnConsole()) {
                e.printStackTrace();
            }
            logger.log.fatal("File not found: " + f.getAbsolutePath());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void storeAsInteger(File f) {
        storeNoise(f, true);
    }

    private void printChannel(ColorChannel.Channel channel, PrintStream ps) {
        switch (channel) {
            case RED:
                red.print(ps);
                break;
            case GREEN:
                green.print(ps);
                break;
            case BLUE:
                blue.print(ps);
                break;
            default:
                throw new IllegalArgumentException("Invalid channel");
        }
    }

    private void printChannelAsInt(ColorChannel.Channel channel, PrintStream ps) {
        switch (channel) {
            case RED:
                red.printAsInt(ps);
                break;
            case GREEN:
                green.printAsInt(ps);
                break;
            case BLUE:
                blue.printAsInt(ps);
                break;
            default:
                throw new IllegalArgumentException("Invalid channel");
        }
    }

    @Override
    public void storeAsFloat(File f) {
        storeNoise(f, false);
    }

    @Override
    public boolean equalsSize(Pattern other) {
        return other != null && this.getHeight() == other.getHeight() && this.getWidth() == other.getWidth();
    }

    @Override
    public boolean equalsChannel(Pattern other, ColorChannel.Channel channel) {
        return this.getColorChannel(channel).equals(other.getColorChannel(channel));
    }

    @Override
    public void crop(int width, int height) {
        if(width < 0 || height < 0) {
            throw new IllegalArgumentException("Width and height must be greater than 0");
        }
        red = new ColorChannel(red.getCentralCropping(width, height), ColorChannel.RED);
        green = new ColorChannel(green.getCentralCropping(width, height), ColorChannel.GREEN);
        blue = new ColorChannel(blue.getCentralCropping(width, height), ColorChannel.BLUE);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Pattern oth = (Pattern) obj;

        return equalsSize(oth) && equalsChannel(oth, ColorChannel.Channel.RED) && equalsChannel(oth, ColorChannel.Channel.GREEN) && equalsChannel(oth, ColorChannel.Channel.BLUE);
    }

    @Override
    public String toString() {
        return "GenericPattern [width=" + this.getWidth() + ", height=" + this.getHeight() + "]";
    }

    public void add(GenericPattern pattern) {
        if(!equalsSize(pattern)) {
            throw new IllegalArgumentException("Patterns must be of the same size");
        }

        for(ColorChannel.Channel channel : ColorChannel.Channel.values()) {
            for(int i = 0; i < this.getHeight(); i++) {
                for(int j = 0; j < this.getWidth(); j++) {
                    this.getColorChannel(channel).setValue(i, j,
                            this.getColorChannel(channel).getValue(i, j) + pattern.getColorChannel(channel).getValue(i, j));
                }
            }
        }
    }

    public void subtract(GenericPattern pattern) {
        if(!equalsSize(pattern)) {
            throw new IllegalArgumentException("Patterns must be of the same size");
        }

        for(ColorChannel.Channel channel : ColorChannel.Channel.values()) {
            for(int i = 0; i < this.getHeight(); i++) {
                for(int j = 0; j < this.getWidth(); j++) {
                    this.getColorChannel(channel).setValue(i, j,
                            this.getColorChannel(channel).getValue(i, j) - pattern.getColorChannel(channel).getValue(i, j));
                }
            }
        }
    }

    public void multiply(GenericPattern pattern) {
        if(!equalsSize(pattern)) {
            throw new IllegalArgumentException("Patterns must be of the same size");
        }

        for(ColorChannel.Channel channel : ColorChannel.Channel.values()) {
            for(int i = 0; i < this.getHeight(); i++) {
                for(int j = 0; j < this.getWidth(); j++) {
                    this.getColorChannel(channel).setValue(i, j,
                            this.getColorChannel(channel).getValue(i, j) * pattern.getColorChannel(channel).getValue(i, j));
                }
            }
        }
    }

    public void divide(GenericPattern pattern) {
        if(!equalsSize(pattern)) {
            throw new IllegalArgumentException("Patterns must be of the same size");
        }

        for(ColorChannel.Channel channel : ColorChannel.Channel.values()) {
            for(int i = 0; i < this.getHeight(); i++) {
                for(int j = 0; j < this.getWidth(); j++) {
                    float value = pattern.getColorChannel(channel).getValue(i, j) == 0 ? 0 :
                            this.getColorChannel(channel).getValue(i, j) / pattern.getColorChannel(channel).getValue(i, j);
                    this.getColorChannel(channel).setValue(i, j, value);
                }
            }
        }
    }

    public void divideByValue(float value) {
        if(value == 0) {
            throw new IllegalArgumentException("Value must be non-zero");
        }

        for(ColorChannel.Channel channel : ColorChannel.Channel.values()) {
            for(int i = 0; i < this.getHeight(); i++) {
                for(int j = 0; j < this.getWidth(); j++) {
                    float r = this.getColorChannel(channel).getValue(i, j) / value;
                    this.getColorChannel(channel).setValue(i, j, r);
                }
            }
        }
    }

    @Override
    public abstract GenericPattern getCroppedPattern(int width, int height);
}
