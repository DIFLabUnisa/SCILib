package it.unisa.di.dif;

import it.unisa.di.dif.pattern.Image;
import it.unisa.di.dif.pattern.NoisePattern;
import it.unisa.di.dif.pattern.ReferencePattern;
import it.unisa.di.dif.pattern.ResidualNoise;

import java.util.ArrayList;

public abstract class SCIManager {
    public static ReferencePattern extractReferencePattern(ArrayList<Image> images) {
        // TODO: To be implemented
        return null;
    }

    public static ResidualNoise extractResidualNoise(Image image) {
        // TODO: To be implemented
        return null;
    }

    public static float compare(ReferencePattern referencePattern, NoisePattern residualNoise) {
        // TODO: To be implemented
        return 0;
    }
}
