package com.github.cythara;

public enum Language {

    FRENCH("fr"),
    ITALIAN("it"),
    SPANISH("es"),
    ROMANIAN("ro"),
    PORTUGUESE("pt"),
    BOSNIAN("bs"),
    CROATIAN("hr"),
    SERBO_CROATIAN("sh"),
    SERBIAN("sr"),
    RUSSIAN("ru"),
    UKRAINIAN("uk"),
    BULGARIAN("bg"),
    GREEK("el"),
    ALBANIAN("sq"),
    MACEDONIAN("mk"),
    MONGOLIAN("mn"),
    PERSIAN("fa"),
    TURKISH("tr"),
    HEBREW("he");

    private String code;

    Language(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
