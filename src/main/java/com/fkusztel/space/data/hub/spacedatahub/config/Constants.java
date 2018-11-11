package com.fkusztel.space.data.hub.spacedatahub.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Filip.Kusztelak
 */
public class Constants {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ImageType {

        public static final String PANCHROMATIC = "Panchromatic";
        public static final String MULTISPECTRAL = "Multispectral";
        public static final String HYPERPECTRAL = "Hyperspectral";
    }
}
