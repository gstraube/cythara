package com.github.cythara;

class GuitarTuning implements Tuning {

    @Override
    public Note[] getNotes() {
        return GuitarString.values();
    }

    @Override
    public Note findNote(String name) {
        return GuitarString.valueOf(name);
    }
}
