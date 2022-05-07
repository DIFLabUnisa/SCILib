package it.unisa.di.dif;

import it.unisa.di.dif.filter.Filter;
import it.unisa.di.dif.filter.FilterFactory;
import it.unisa.di.dif.pattern.*;
import it.unisa.di.dif.utils.AdaptationMethod;

import java.io.IOException;
import java.util.ArrayList;

public abstract class SCIManager {
    public static ReferencePattern extractReferencePattern(ArrayList<Image> images, Filter filter, AdaptationMethod method) {
        if(method == AdaptationMethod.RESIZE) {
            throw new UnsupportedOperationException("Resize method is not supported yet");
        }

        if(method == AdaptationMethod.NOT_ADAPT) {
            Image old = null;
            for(Image image : images) {
                if(old == null || old.equalsSize(image)) {
                    old = image;
                } else {
                    throw new UnsupportedOperationException("Images must have the same size when selected NOT_ADAPT method");
                }
            }
        }

        if(method == AdaptationMethod.CROP) {
            Image old = null;
            int height = Integer.MAX_VALUE;
            int width = Integer.MAX_VALUE;
            for(Image image : images) {
                if(old == null) {
                    old = image;
                    continue;
                }
                if(!old.equalsSize(image)) {
                    height = Math.min(Math.min(old.getHeight(), image.getHeight()), height);
                    width = Math.min(Math.min(old.getWidth(), image.getWidth()), width);
                }
            }
            int finalWidth = width;
            int finalHeight = height;
            images = images.stream()
                    .map(image -> image.getCroppedPattern(finalWidth, finalHeight))
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        }
        //TODO: Complete the implementation using calcolaReferencePattern_2
        return null;
    }

    public static ReferencePattern extractReferencePattern(ArrayList<Image> images, Filter filter) {
        return extractReferencePattern(images, filter, AdaptationMethod.NOT_ADAPT);
    }

    public static ResidualNoise extractResidualNoise(Image image, Filter filter) {
        Image filteredImage = new Image();
        for (ColorChannel.Channel channel : ColorChannel.Channel.values()) {
            ColorChannel ch = image.getColorChannel(channel);
            if (ch == null) {
                continue;
            }

            Filter f = FilterFactory.getDefaultFilter();
            if(filter == null) {
                throw new IllegalArgumentException("Error: filter is null");
            }

            ColorChannel chFiltered = f.getFiltered(ch);
            filteredImage.setChannel(chFiltered);
        }
        return ResidualNoise.getInstanceBySubtraction(image, filteredImage);
    }

    public static ReferencePattern extractReferencePattern(ArrayList<Image> images) {
        return extractReferencePattern(images, FilterFactory.getDefaultFilter(), AdaptationMethod.NOT_ADAPT);
    }

    public static ResidualNoise extractResidualNoise(Image image) {
        return extractResidualNoise(image, FilterFactory.getDefaultFilter());
    }

    public static ResidualNoise extractResidualNoise(String path) throws IOException {
        return extractResidualNoise(new Image(path), FilterFactory.getDefaultFilter());
    }

    public static ResidualNoise extractResidualNoise(String path, Filter filter) throws IOException {
        return extractResidualNoise(new Image(path));
    }

    public static float compare(ReferencePattern referencePattern, NoisePattern residualNoise, AdaptationMethod method) {
        // TODO: To be implemented
        return 0;
    }

    public static float compare(ReferencePattern referencePattern, NoisePattern residualNoise) {
        return compare(referencePattern, residualNoise, AdaptationMethod.NOT_ADAPT);
    }
}
