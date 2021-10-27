package com.github.cythara;

import static java.util.Arrays.binarySearch;

import com.github.cythara.tuning.ChromaticTuning;
import com.github.cythara.tuning.NoteFrequencyCalculator;

import java.util.Arrays;
import java.util.List;

class PitchComparator {
    private static final Tuning chromaticTuning = new ChromaticTuning();
    private static final int numberOfNotes=chromaticTuning.getNotes().length;
    private static final int referencePitch = MainActivity.getReferencePitch();
    private static final NoteFrequencyCalculator noteFrequencyCalculator =
            new NoteFrequencyCalculator(referencePitch);
    private static final int [] noteValues = new int[numberOfNotes];
    private static Object[][]notes = new Object[numberOfNotes][2];
    private static Object[][]searchedNotes;
    static int newNumberOfNotes;
    static void fillSearchedNotesArr(){
        newNumberOfNotes=MainActivity.getCurrentTuning().getNotes().length;
        searchedNotes=new Object[newNumberOfNotes][2];
        Note[]tuningNotes=MainActivity.getCurrentTuning().getNotes();
        int noteCounter=0;
        for (Note note:tuningNotes){
            for(int i=0;i<2;i++){
                if (i==0){
                    searchedNotes[noteCounter][i]=note;
                }else{
                    searchedNotes[noteCounter][i]=noteFrequencyCalculator.getPosition(note);
                }
            }
            noteCounter++;
        }
        Arrays.sort(searchedNotes, (a, b) -> {
            return Integer.compare((int) a[1],(int)  b[1]);
        });
    }
    static void fillChromaticNotesArr(){
        notes = new Object[numberOfNotes][2];
        int noteCounter=0;
        for (Note note:chromaticTuning.getNotes()){
            for(int i=0;i<2;i++){
                if (i==0){
                    notes[noteCounter][i]=note;
                }else{
                    notes[noteCounter][i]=noteFrequencyCalculator.getFrequency(note);
                }
            }
            noteCounter++;
        }
        Arrays.sort(notes, (a, b) -> {
            return Double.compare((double) a[1],(double)  b[1]);
        });
        for (int i=0;i< numberOfNotes;i++) {
            noteValues[i]=(int)Math.round((log2((double)notes[i][1]))*12);


            /* TEST to show that the next Value==Value+1(important for binarySearch)

            int noteValue=0;
            if (i>0)
            {
                if (noteValues[i]!=noteValues[i-1]+1) {
                    System.out.println(noteValues[i]+"-!-");
                }else{
                    System.out.println("i");
                }
            }else{
                System.out.println(i);
            }
             */
        }
    }
    static PitchDifference retrieveNote(float pitch) {

        int index = binarySearch(noteValues, (int) Math.round(log2(pitch) * 12));
        Object[][] centDifference = new Object[3][2];
        if (index > 0) {
            for (int i = -1; i < 2; i++) {
                centDifference[i+1][1] = 1200.0*(log2((double) pitch)-log2((double) notes[index+i][1]));
                centDifference[i+1][0] = notes[index+i][0];
            }
            Arrays.sort(centDifference, (a, b) -> Double.compare(Math.abs((double) a[1]), Math.abs((double) b[1])));

            return calculateDifference((Note) centDifference[0][0], (double)centDifference[0][1],(double)pitch);
        }
        return new PitchDifference((Note) centDifference[0][0], (double)(2024*2048));
    }
    private static PitchDifference calculateDifference(Note givenNote, double givenCentDifference,double pitch) {
        if(Arrays.equals(MainActivity.getCurrentTuning().getNotes(), chromaticTuning.getNotes())){
            return new PitchDifference(givenNote,givenCentDifference);
        }
        int givenPosition=noteFrequencyCalculator.getPosition(givenNote);
        int oldNoteDifference=1024;
        int noteCounter;
        for(noteCounter=0;noteCounter<newNumberOfNotes;noteCounter++){
            int newNoteDifference=givenPosition-(int)searchedNotes[noteCounter][1];
            if (Math.abs(newNoteDifference)<Math.abs(oldNoteDifference)) {
                oldNoteDifference = newNoteDifference;
            }else{
                break;
            }
        }
        noteCounter--;
        double centDifference=1200*(
                log2(pitch)-log2(noteFrequencyCalculator.getFrequency((Note)searchedNotes[noteCounter][0])));

        if (centDifference>60){
            centDifference=60;
        }else if(centDifference<-60){
            centDifference=-60;
        }
        return new PitchDifference((Note)searchedNotes[noteCounter][0],centDifference);
    }

    private static double log2(double number) {
        return Math.log(number) / Math.log(2);
    }
}