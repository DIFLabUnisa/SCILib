package it.unisa.di.dif.pattern;

import it.unisa.di.dif.utils.CHILogger;
import it.unisa.di.dif.utils.Constant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Pattern implements IPattern{
    private ColorChannel red;
    private ColorChannel green;
    private ColorChannel blue;

    private final CHILogger logger = CHILogger.getInstance();
    private final Constant constant = Constant.getInstance();

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
    public void setChannel(ColorChannel value, ColorChannel.Channel channel) {
        switch (channel) {
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
    public void setRedChannel(ColorChannel value) {
        setChannel(value, ColorChannel.RED);
    }

    @Override
    public void setGreenChannel(ColorChannel value) {
        setChannel(value, ColorChannel.GREEN);
    }

    @Override
    public void setBlueChannel(ColorChannel value) {
        setChannel(value, ColorChannel.BLUE);
    }

    @Override
    public void storeAsInteger(File f) {
        if(f == null) {
            logger.log.warn("File is null");
            throw new IllegalArgumentException("File is null");
        }

        try (PrintStream ps = new PrintStream(f)) {

            ps.print(getWidth() + " " + getHeight() + "\n\n");

            printChannelAsInt(ColorChannel.RED, ps);
            printChannelAsInt(ColorChannel.GREEN, ps);
            printChannelAsInt(ColorChannel.BLUE, ps);
        } catch (FileNotFoundException e) {
            if (constant.isWriteMessageLogOnConsole()) {
                e.printStackTrace();
            }
            logger.log.fatal("File not found: " + f.getAbsolutePath());
            throw new RuntimeException(e);
        }
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
        if(f == null) {
            logger.log.warn("File is null");
            throw new IllegalArgumentException("File is null");
        }

        try (PrintStream ps = new PrintStream(f)) {

            ps.print(getWidth() + " " + getHeight() + "\n\n");

            printChannel(ColorChannel.RED, ps);
            printChannel(ColorChannel.GREEN, ps);
            printChannel(ColorChannel.BLUE, ps);
        } catch (FileNotFoundException e) {
            if (constant.isWriteMessageLogOnConsole()) {
                e.printStackTrace();
            }
            logger.log.fatal("File not found: " + f.getAbsolutePath());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equalsSize(IPattern other) {
        if (other == null || this.getHeight() != other.getHeight() || this.getWidth() != other.getWidth()) {
            return false;
        }

        return true;
    }

    @Override
    public boolean equalsChannel(IPattern other, ColorChannel.Channel channel) {
        return this.getColorChannel(channel).equals(other.getColorChannel(channel));
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }

        IPattern oth = (IPattern) obj;

        return equalsSize(oth) && equalsChannel(oth, ColorChannel.Channel.RED) && equalsChannel(oth, ColorChannel.Channel.GREEN) && equalsChannel(oth, ColorChannel.Channel.BLUE);
    }
}
