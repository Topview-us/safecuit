package com.gdut.safecuit.organization.common.util;

public class OrganizationConstant {
    private static final int NAME_MIN_LENGTH = 1;
    private static final int NAME_MAX_LENGTH = 100;

    private static final int ADDRESS_MIN_LENGTH = 1;
    private static final int ADDRESS_MAX_LENGTH = 255;

    private static final int EMAIL_MIN_LENGTH = 3;
    private static final int EMAIL_MAX_LENGTH = 100;

    private static final int PHONE_MIN_LENGTH = 6;
    private static final int PHONE_MAX_LENGTH = 30;

    private static final int DESCRIPTION_MIN_LENGTH = 0;
    private static final int DESCRIPTION_MAX_LENGTH = 255;

    public static int getNameMinLength() {
        return NAME_MIN_LENGTH;
    }

    public static int getNameMaxLength() {
        return NAME_MAX_LENGTH;
    }

    public static int getAddressMinLength() {
        return ADDRESS_MIN_LENGTH;
    }

    public static int getAddressMaxLength() {
        return ADDRESS_MAX_LENGTH;
    }

    public static int getEmailMinLength() {
        return EMAIL_MIN_LENGTH;
    }

    public static int getEmailMaxLength() {
        return EMAIL_MAX_LENGTH;
    }

    public static int getPhoneMinLength() {
        return PHONE_MIN_LENGTH;
    }

    public static int getPhoneMaxLength() {
        return PHONE_MAX_LENGTH;
    }

    public static int getDescriptionMinLength() {
        return DESCRIPTION_MIN_LENGTH;
    }

    public static int getDescriptionMaxLength() {
        return DESCRIPTION_MAX_LENGTH;
    }
}

