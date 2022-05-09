package it.unisa.di.dif.pattern;

public class ResidualNoise extends NoisePattern{
    public ResidualNoise() {
        super();
    }

    public ResidualNoise(int height, int width) {
        super(height, width);
    }

    public static ResidualNoise getInstanceBySize(int height, int width) {
        ResidualNoise r = new ResidualNoise();
        float[][] red_data = new float[height][width];
        float[][] green_data = new float[height][width];
        float[][] blue_data = new float[height][width];
        r.setChannel(new ColorChannel(red_data, ColorChannel.Channel.RED));
        r.setChannel(new ColorChannel(green_data, ColorChannel.Channel.GREEN));
        r.setChannel(new ColorChannel(blue_data, ColorChannel.Channel.BLUE));
        r.clear();
        return r;
    }

    public static ResidualNoise getInstanceBySubtraction(GenericPattern a, GenericPattern b){
        return ResidualNoise.getInstantByOperation(a, b, "-");
    }

    public static ResidualNoise getInstanceByAddition(GenericPattern a, GenericPattern b) {
        return ResidualNoise.getInstantByOperation(a, b, "+");
    }

    public static ResidualNoise getInstanceByMultiplication(GenericPattern a, GenericPattern b) {
        return ResidualNoise.getInstantByOperation(a, b, "*");
    }

    public static ResidualNoise getInstanceByDivision(GenericPattern a, GenericPattern b) {
        return ResidualNoise.getInstantByOperation(a, b, "/");
    }

    private static ResidualNoise getInstantByOperation(GenericPattern a, GenericPattern b, String op){
        if(a.equalsSize(b)){
            ResidualNoise r = new ResidualNoise();
            for(ColorChannel.Channel channel : ColorChannel.Channel.values()){
                r.setChannel(a.getColorChannel(channel));
            }
            switch (op) {
                case "+":
                    r.add(b);
                    break;
                case "-":
                    r.subtract(b);
                    break;
                case "*":
                    r.multiply(b);
                    break;
                case "/":
                    r.divide(b);
                    break;
                default:
                    throw new IllegalArgumentException("Operation not supported");
            }
            return r;
        } else {
            throw new IllegalArgumentException("Patterns must be of the same size");
        }
    }

    public static ResidualNoise divideByInteger(GenericPattern a, int b) {
        if(b == 0){
            throw new IllegalArgumentException("Divisor cannot be zero");
        }
        ResidualNoise r = new ResidualNoise();
        float[][] red_data = new float[a.getHeight()][a.getWidth()];
        float[][] green_data = new float[a.getHeight()][a.getWidth()];
        float[][] blue_data = new float[a.getHeight()][a.getWidth()];

        for(int i = 0; i < a.getHeight(); i++){
            for(int j = 0; j < a.getWidth(); j++){
                red_data[i][j] = a.getValue(i, j, ColorChannel.Channel.RED) * b;
                green_data[i][j] = a.getValue(i, j, ColorChannel.Channel.GREEN) * b;
                blue_data[i][j] = a.getValue(i, j, ColorChannel.Channel.BLUE) * b;
            }
        }

        r.setChannel(new ColorChannel(red_data, ColorChannel.Channel.RED));
        r.setChannel(new ColorChannel(green_data, ColorChannel.Channel.GREEN));
        r.setChannel(new ColorChannel(blue_data, ColorChannel.Channel.BLUE));
        return r;
    }

    @Override
    public String toString() {
        return "ResidualNoise [width=" + this.getWidth() + ", height=" + this.getHeight() + "]";
    }

    @Override
    public ResidualNoise getCroppedPattern(int width, int height) {
        ResidualNoise copy = new ResidualNoise();
        for (ColorChannel.Channel channel : ColorChannel.Channel.values()) {
            copy.setChannel(new ColorChannel(this.getRedChannel().getCentralCropping(width, height), channel));
        }
        return copy;
    }
}
