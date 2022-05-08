package it.unisa.di.dif.pattern;

import java.security.SecureRandom;

public class ReferencePattern extends NoisePattern{
    public ReferencePattern() {
        super();
    }

    public ReferencePattern(ResidualNoise rn) {
        super();
        this.setRedChannel(rn.getRedChannel());
        this.setGreenChannel(rn.getGreenChannel());
        this.setBlueChannel(rn.getBlueChannel());
    }

    public ReferencePattern(int height, int width) {
        super(height, width);
    }

    public static ReferencePattern random(int height, int width) {
        SecureRandom random = new SecureRandom();

        ReferencePattern rp = new ReferencePattern();
        float[][] red = new float[height][width];
        float[][] green = new float[height][width];
        float[][] blue = new float[height][width];

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                red[i][j] = random.nextFloat();
                green[i][j] = random.nextFloat();
                blue[i][j] = random.nextFloat();
            }
        }

        rp.setRedChannel(new ColorChannel(red, ColorChannel.Channel.RED));
        rp.setGreenChannel(new ColorChannel(green, ColorChannel.Channel.GREEN));
        rp.setBlueChannel(new ColorChannel(blue, ColorChannel.Channel.BLUE));

        return rp;
    }

    @Override
    public String toString() {
        return "ReferencePattern [width=" + this.getWidth() + ", height=" + this.getHeight() + "]";
    }

    @Override
    public ReferencePattern getCroppedPattern(int width, int height) {
        ReferencePattern copy = new ReferencePattern();
        copy.setRedChannel(new ColorChannel(this.getRedChannel().getCentralCropping(width, height), ColorChannel.RED));
        copy.setGreenChannel(new ColorChannel(this.getGreenChannel().getCentralCropping(width, height), ColorChannel.GREEN));
        copy.setBlueChannel(new ColorChannel(this.getBlueChannel().getCentralCropping(width, height), ColorChannel.BLUE));
        return copy;
    }
}
