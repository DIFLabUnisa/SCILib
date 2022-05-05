package it.unisa.di.dif.pattern;

import java.io.File;

public interface IPattern {
    public ColorChannel getColorChannel(ColorChannel.Channel channel);
    public ColorChannel getRedChannel();
    public ColorChannel getGreenChannel();
    public ColorChannel getBlueChannel();
    public int getHeight();
    public int getWidth();
    public float getValue(int x, int y, ColorChannel.Channel channel);
    public int getValueAsInteger(int x, int y, ColorChannel.Channel channel);
    public void clear();
    public String getChannelName(ColorChannel.Channel channel);
    public void setHeight(int height);
    public void setWidth(int width);
    public void setChannel(ColorChannel value, ColorChannel.Channel channel);
    public void setRedChannel(ColorChannel value);
    public void setGreenChannel(ColorChannel value);
    public void setBlueChannel(ColorChannel value);
    public void storeAsInteger(File f);
    public void storeAsFloat(File f);
    public boolean equalsSize(IPattern other);
    public boolean equalsChannel(IPattern other);

}
