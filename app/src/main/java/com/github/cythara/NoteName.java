package com.github.cythara;

public enum NoteName {

    C("C", "Do"),
    D("D", "Re"),
    E("E", "Mi"),
    F("F", "Fa"),
    G("G", "Sol"),
    A("A", "La"),
    B("B", "Si");

    private final String standard;
    private final String sol;

    NoteName(String standard, String sol) {
        this.standard = standard;
        this.sol = sol;
    }

    public String getStandard() {
        return standard;
    }

    public String getSol() {
        return sol;
    }
}
