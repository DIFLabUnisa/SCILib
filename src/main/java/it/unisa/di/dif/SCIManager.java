package it.unisa.di.dif;

import it.unisa.di.dif.filter.Filter;
import it.unisa.di.dif.filter.FilterFactory;
import it.unisa.di.dif.pattern.Image;
import it.unisa.di.dif.pattern.NoisePattern;
import it.unisa.di.dif.pattern.ReferencePattern;
import it.unisa.di.dif.pattern.ResidualNoise;
import it.unisa.di.dif.utils.AdaptationMethod;

import java.util.ArrayList;

public abstract class SCIManager {
    public static ReferencePattern extractReferencePattern(ArrayList<Image> images, Filter filter, AdaptationMethod method) {
        // TODO: To be implemented
        return null;
    }

    public static ReferencePattern extractReferencePattern(ArrayList<Image> images, Filter filter) {
        return extractReferencePattern(images, filter, AdaptationMethod.NOT_ADAPT);
    }

    public static ResidualNoise extractResidualNoise(Image image, Filter filter) {
        // TODO: To be implemented
        return null;
    }

    public static ReferencePattern extractReferencePattern(ArrayList<Image> images) {
        return extractReferencePattern(images, FilterFactory.getDefaultFilter(), AdaptationMethod.NOT_ADAPT);
    }

    public static ResidualNoise extractResidualNoise(Image image) {
        return extractResidualNoise(image, FilterFactory.getDefaultFilter());
    }

    public static float compare(ReferencePattern referencePattern, NoisePattern residualNoise, AdaptationMethod method) {
        // TODO: To be implemented
        return 0;
    }

    public static float compare(ReferencePattern referencePattern, NoisePattern residualNoise) {
        return compare(referencePattern, residualNoise, AdaptationMethod.NOT_ADAPT);
    }
}
