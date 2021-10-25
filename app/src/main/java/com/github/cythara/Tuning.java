package com.github.cythara;

public interface Tuning {

    Note[] getNotes();

    Note findNote(String name);
    default int getNotesSize(){
        int counter=0;
        for(Note ignored :getNotes()){
            counter+=1;
        }
        return counter;
    }
}
