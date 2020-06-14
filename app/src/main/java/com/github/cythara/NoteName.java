package com.github.cythara;

public enum NoteName {

    C("C", "Do"),
    D("D", "Re"),
    E("E", "Mi"),
    F("F", "Fa"),
    G("G", "Sol"),
    A("A", "La"),
    B("B", "Si");

    private final String scientific;
    private final String sol;

    NoteName(String scientific, String sol) {
        this.scientific = scientific;
        this.sol = sol;
    }

    public String getScientific() {
        return scientific;
    }

    public String getSol() {
        return sol;
    }

    public static NoteName fromScientificName(String scientificName) {
        for (NoteName noteName : NoteName.values()) {
            if (noteName.getScientific().equalsIgnoreCase(scientificName)) {
                return noteName;
            }
        }

        throw new IllegalArgumentException("Could not convert from name: " + scientificName);
    }
}
