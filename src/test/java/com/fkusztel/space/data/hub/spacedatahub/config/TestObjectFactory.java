package com.fkusztel.space.data.hub.spacedatahub.config;

import com.fkusztel.space.data.hub.spacedatahub.entity.ImageType;
import com.fkusztel.space.data.hub.spacedatahub.entity.Mission;
import com.fkusztel.space.data.hub.spacedatahub.entity.Product;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * @author Filip.Kusztelak
 */
public class TestObjectFactory {

    public static class MissionCreate {

        public static final String MISSION_NAME = "missionName";
        public static final String START_DATE = "2018-09-10";
        public static final String END_DATE = "2018-01-19";
        public static final ImageType PROPER_IMAGE_TYPE = ImageType.PANCHROMATIC;

        public static final String DIFFERENT_MISSION_NAME = "differentMissionName";
        private static final String DIFFERENT_START_DATE = "2018-01-01";
        private static final String DIFFERENT_END_DATE = "2018-12-12";
        private static final ImageType DIFFERENT_PROPER_IMAGE_TYPE = ImageType.PANCHROMATIC;

        private MissionCreate() {

        }
    }

    public static class NewMission {

        public static Mission mission = Mission.builder()
                .name(TestObjectFactory.MissionCreate.MISSION_NAME)
                .imageType(TestObjectFactory.MissionCreate.PROPER_IMAGE_TYPE)
                .startDate(LocalDate.parse(TestObjectFactory.MissionCreate.START_DATE))
                .endDate(LocalDate.parse(TestObjectFactory.MissionCreate.END_DATE))
                .build();

        public static Mission differentMission = Mission.builder()
                .name(TestObjectFactory.MissionCreate.DIFFERENT_MISSION_NAME)
                .imageType(TestObjectFactory.MissionCreate.DIFFERENT_PROPER_IMAGE_TYPE)
                .startDate(LocalDate.parse(TestObjectFactory.MissionCreate.DIFFERENT_START_DATE))
                .endDate(LocalDate.parse(TestObjectFactory.MissionCreate.DIFFERENT_END_DATE))
                .build();

        private NewMission() {

        }
    }

    public static class ProductList{

        public static Product productFirst = Product.builder()
                .acquisitionDate(LocalDate.parse(Dates.FIRST_DATE))
                .build();

        private static Product productSecond = Product.builder()
                .acquisitionDate(LocalDate.parse(Dates.SECOND_DATE))
                .build();

        private static Product productThird = Product.builder()
                .acquisitionDate(LocalDate.parse(Dates.THIRD_DATE))
                .build();

        private static Product productForth = Product.builder()
                .acquisitionDate(LocalDate.parse(Dates.FOURTH_DATE))
                .build();

        public static Product productFifth = Product.builder()
                .acquisitionDate(LocalDate.parse(Dates.FIFTH_DATE))
                .build();

        public static List<Product> datesAll = Arrays.asList(
                productFirst,
                productSecond,
                productThird,
                productForth,
                productFifth
        );

        public static List<Product> datesLower = Arrays.asList(
                productFirst,
                productSecond
        );

        public static List<Product> datesGreater = Arrays.asList(
                productForth,
                productFifth
        );

        public static List<Product> datesBetween = Arrays.asList(
                productSecond,
                productThird,
                productForth
        );

        private ProductList() {

        }
    }

    public static class Dates {

        public static final String FIRST_DATE = "2018-01-19";
        public static final String SECOND_DATE = "2018-03-22";
        public static final String THIRD_DATE = "2018-05-02";
        public static final String FOURTH_DATE = "2018-07-11";
        public static final String FIFTH_DATE = "2018-09-10";

        private Dates() {

        }
    }

    public static class NewProduct{

        public static Product productPurchased = Product.builder()
                .purchased(true)
                .build();

        public static Product productNotPurchased = Product.builder()
                .purchased(false)
                .build();

        private NewProduct() {

        }
    }
}
