package com.example.bprosnitz.scalepractice;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.TimeSignature;

import java.util.ArrayList;

public class Scale {
    int[] Notes;

    public Scale(int[] notes) {
        this.Notes = notes;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean first = false;
        for (int note : Notes) {
            if (!first) {
                sb.append(" ");
            }
            sb.append(note);
            first = true;
        }
        return sb.toString();
    }

    public MidiFile ToMIDI(Pitch root, int bpm) {
        // 1. Create some MidiTracks
        MidiTrack tempoTrack = new MidiTrack();
        MidiTrack noteTrack = new MidiTrack();

        // 2. Add events to the tracks
        // 2a. Track 0 is typically the tempo map
        TimeSignature ts = new TimeSignature();
        ts.setTimeSignature(4, 4, TimeSignature.DEFAULT_METER, TimeSignature.DEFAULT_DIVISION);

        Tempo t = new Tempo();
        t.setBpm(bpm);

        tempoTrack.insertEvent(ts);
        tempoTrack.insertEvent(t);

        // 2b. Track 1 will have some notes in it
        int i = 0;
        for(int interval : Notes)
        {
            int channel = 0, pitch = root.plus(interval).midiIndex(), velocity = 100;
            NoteOn on = new NoteOn(i * 480, channel, pitch, velocity);
            NoteOff off = new NoteOff(i * 480 + 120, channel, pitch, 0);

            noteTrack.insertEvent(on);
            noteTrack.insertEvent(off);

            // There is also a utility function for notes that you should use
            // instead of the above.
            noteTrack.insertNote(channel, pitch + 2, velocity, i * 480, 120);
            i++;
        }

        // It's best not to manually insert EndOfTrack events; MidiTrack will
        // call closeTrack() on itself before writing itself to a file

        // 3. Create a MidiFile with the tracks we created
        ArrayList<MidiTrack> tracks = new ArrayList<MidiTrack>();
        tracks.add(tempoTrack);
        tracks.add(noteTrack);

        return new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);
    }
}
