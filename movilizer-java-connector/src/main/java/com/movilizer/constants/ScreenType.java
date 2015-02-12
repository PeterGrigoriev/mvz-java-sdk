package com.movilizer.constants;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public enum ScreenType {
    MESSAGE(0),
    IMAGE(1),
    SINGLE_SELECT(2),
    FULL_TEXT(3),
    MULTI_SELECT(4),
    TEXT_ITEM(5),
    MENU(6),
    IMAGE_SELECT(7),
    CALENDAR(8),
    TABLE_INPUT(9),
    CAPTURE_IMAGE(10),
    CAPTURE_SIGNATURE(11),
    WEB_VIEW(12),
    IMAGE_MENU(13),
    SINGLE_SELECT_LIST_VIEW(14),
    GOOGLE_MAPS(15),
    TABLE_MENU(16),
    CAPTURE_LOCATION(20),
    CAPTURE_RFID(21),
    CAPTURE_BARCODE_1D(22),
    CAPTURE_BARCODE_2D(23),
    CAPTURE_QR_CODE(24),
    PDF_VIEW(30),
    VIDEO_DISPLAY(31),
    HTML5(32),
    UPLOAD_IMAGE_INPUT(33),
    HTML5_FULL_SCREEN(34),
    INTERMOVELET_FLOW_CONTROL(40),
    EPSILON_FLOW_CONTROL(41);


    private final int intValue;

    public int getIntValue() {
        return intValue;
    }

    public static ScreenType fromIntValue(int value) {
        return intValueToScreenTypeMap.get(value);
    }

    private static final Map<Integer, ScreenType> intValueToScreenTypeMap = newHashMap();

    private ScreenType(int intValue) {
        registerType(intValue, this);
        this.intValue = intValue;
    }

    private static void registerType(int intValue, ScreenType screenType) {
        ScreenType existingType = intValueToScreenTypeMap.get(intValue);
        if (existingType != null) {
            throw new IllegalStateException("Screen type [" + intValue + "] is declared twice as [" + existingType.name() + "] and [" + screenType.name() + "]");
        }
        intValueToScreenTypeMap.put(intValue, screenType);
    }

    @Override
    public String toString() {
        return String.valueOf(intValue);
    }
}
