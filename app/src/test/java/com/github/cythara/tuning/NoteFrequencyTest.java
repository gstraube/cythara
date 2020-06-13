package com.github.cythara.tuning;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NoteFrequencyTest {

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

                String note = noteWithOctave.substring(0, 1);
                String octave = noteWithOctave.substring(1);
                if (noteWithOctave.contains("#")) {
                    note = noteWithOctave.substring(0, 2);
                    octave = noteWithOctave.substring(2);
                }

                double expectedFrequency = Double.parseDouble(frequency);
                double actualFrequency = NoteFrequency.getFrequency(note, Integer.parseInt(octave));
                Assert.assertEquals(expectedFrequency, actualFrequency, 0.01);

            }
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }

    }

}