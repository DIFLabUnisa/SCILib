package it.unisa.di.dif.pattern;

import it.unisa.di.dif.utils.CHILogger;
import it.unisa.di.dif.utils.Constant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public abstract class NoisePattern extends GenericPattern {
    public NoisePattern() {
        super();
    }

    public NoisePattern(int height, int width) {
        super(height, width);
    }

    @Override
    public abstract NoisePattern getCroppedPattern(int width, int height);

    @Override
    public String toString() {
        return "NoisePattern [width=" + this.getWidth() + ", height=" + this.getHeight() + "]";
    }
}
