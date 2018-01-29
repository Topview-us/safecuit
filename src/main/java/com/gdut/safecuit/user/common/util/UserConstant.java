package com.gdut.safecuit.user.common.util;

public class UserConstant {
    private static final Integer USERNAME_MIN_LENGTH = 4;
    private static final Integer USERNAME_MAX_LENGTH = 20;

    private static final Integer REAL_NAME_MIN_LENGTH = 1;
    private static final Integer REAL_NAME_MAX_LENGTH = 100;

    private static final Integer PASSWORD_MIN_LENGTH = 6;
    private static final Integer PASSWORD_MAX_LENGTH = 36;

    private static final Integer DESCRIPTION_MAX_LENGTH = 6;

    public static Integer getUsernameMinLength() {
        return USERNAME_MIN_LENGTH;
    }

    public static Integer getUsernameMaxLength() {
        return USERNAME_MAX_LENGTH;
    }

    public static Integer getRealNameMinLength() {
        return REAL_NAME_MIN_LENGTH;
    }

    public static Integer getRealNameMaxLength() {
        return REAL_NAME_MAX_LENGTH;
    }

    public static Integer getPasswordMinLength() {
        return PASSWORD_MIN_LENGTH;
    }

    public static Integer getPasswordMaxLength() {
        return PASSWORD_MAX_LENGTH;
    }

    public static Integer getDescriptionMaxLength() {
        return DESCRIPTION_MAX_LENGTH;
    }
}
