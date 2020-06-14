package com.github.cythara.tuning;

import com.github.cythara.Note;
import com.github.cythara.NoteName;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NoteFrequencyCalculatorTest {

    @Test
    public void TestCalc() {

        InputStream resourceAsStream = getClass().getResourceAsStream("note_frequencies.csv");
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(resourceAsStream))) {
            while (reader.ready()) {
                String line = reader.readLine();
                String[] components = line.split(",");
                String noteWithOctave = components[0].split("/")[0];
                String frequency = components[1];

                String noteName = noteWithOctave.substring(0, 1);
                String octave = noteWithOctave.substring(1);
                String sign = "";
                if (noteWithOctave.contains("#")) {
                    noteName = noteWithOctave.substring(0, 1);
                    octave = noteWithOctave.substring(2);
                    sign = "#";
                }

                String finalNoteName = noteName;
                String finalOctave = octave;
                String finalSign = sign;
                Note note = new Note() {
                    @Override
                    public NoteName getName() {
                        return NoteName.fromScientificName(finalNoteName);
                    }

                    @Override
                    public int getOctave() {
                        return Integer.parseInt(finalOctave);
                    }

                    @Override
                    public String getSign() {
                        return finalSign;
                    }
                };

                NoteFrequencyCalculator noteFrequencyCalculator =
                        new NoteFrequencyCalculator(440);
                double expectedFrequency = Double.parseDouble(frequency);
                double actualFrequency = noteFrequencyCalculator.getFrequency(note);
                Assert.assertEquals(expectedFrequency, actualFrequency, 0.01);

            }
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }

    }

}