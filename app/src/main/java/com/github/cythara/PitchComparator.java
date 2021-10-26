package com.github.cythara;

import static java.util.Arrays.binarySearch;

import com.github.cythara.tuning.NoteFrequencyCalculator;

import java.util.Arrays;
import java.util.Comparator;

class PitchComparator {
    private static Tuning tuning = MainActivity.getCurrentTuning();
    private static int [] noteValues = new int[tuning.getNotesSize()];
    private static Object[][]notes = new Object[tuning.getNotesSize()][2];
    static void fillNotesArr(){
        notes = new Object[tuning.getNotesSize()][2];
        int referencePitch = MainActivity.getReferencePitch();
        NoteFrequencyCalculator noteFrequencyCalculator =
                new NoteFrequencyCalculator(referencePitch);
        int noteCounter=0;
        for (Note note:tuning.getNotes()){
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
        for (int i=0;i< tuning.getNotesSize();i++) {
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
                centDifference[i+1][1] = 1200.0*(log2((double) notes[index+i][1])-log2((double) pitch));
                centDifference[i+1][0] = notes[index+i][0];
            }
            Arrays.sort(centDifference, (a, b) -> Double.compare(Math.abs((double) a[1]), Math.abs((double) b[1])));
            return new PitchDifference((Note) centDifference[0][0], (double)centDifference[0][1]);
        }
        return new PitchDifference((Note) centDifference[0][0], (double)(2024*2048));
    }

    private static double log2(double number) {
        return Math.log(number) / Math.log(2);
    }
}