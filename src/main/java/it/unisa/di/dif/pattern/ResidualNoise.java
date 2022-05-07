package it.unisa.di.dif.pattern;

public class ResidualNoise extends NoisePattern{
    public static ResidualNoise getInstanceBySubtraction(GenericPattern a, GenericPattern b){
        ResidualNoise r = null;
        float[][] red_data = null;
        float[][] green_data = null;
        float[][] blue_data = null;

        if(a.equalsSize(b)){
            r = new ResidualNoise();
            red_data = new float[a.getHeight()][a.getWidth()];
            green_data = new float[a.getHeight()][a.getWidth()];
            blue_data = new float[a.getHeight()][a.getWidth()];

            for(int i = 0; i < a.getHeight(); i++){
                for(int j = 0; j < a.getWidth(); j++){
                    red_data[i][j] = a.getValue(i, j, ColorChannel.Channel.RED) - b.getValue(i, j, ColorChannel.Channel.RED);
                    green_data[i][j] = a.getValue(i, j, ColorChannel.Channel.GREEN) - b.getValue(i, j, ColorChannel.Channel.GREEN);
                    blue_data[i][j] = a.getValue(i, j, ColorChannel.Channel.BLUE) - b.getValue(i, j, ColorChannel.Channel.BLUE);
                }
            }

            r.setChannel(new ColorChannel(red_data, ColorChannel.Channel.RED));
            r.setChannel(new ColorChannel(green_data, ColorChannel.Channel.GREEN));
            r.setChannel(new ColorChannel(blue_data, ColorChannel.Channel.BLUE));
        }

        return r;
    }

    @Override
    public String toString() {
        return "ResidualNoise [width=" + this.getWidth() + ", height=" + this.getHeight() + "]";
    }

    @Override
    public ResidualNoise getCroppedPattern(int width, int height) {
        ResidualNoise copy = new ResidualNoise();
        copy.setRedChannel(new ColorChannel(this.getRedChannel().getCentralCropping(width, height), ColorChannel.RED));
        copy.setGreenChannel(new ColorChannel(this.getGreenChannel().getCentralCropping(width, height), ColorChannel.GREEN));
        copy.setBlueChannel(new ColorChannel(this.getBlueChannel().getCentralCropping(width, height), ColorChannel.BLUE));
        return copy;
    }
}
