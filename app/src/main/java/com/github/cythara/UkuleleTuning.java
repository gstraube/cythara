package com.github.cythara;

class UkuleleTuning implements Tuning {

    @Override
    public Note[] getNotes() {
        return UkuleleString.values();
    }

    @Override
    public Note findNote(String name) {
        return UkuleleString.valueOf(name);
    }
}
