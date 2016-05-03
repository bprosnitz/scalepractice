package com.example.bprosnitz.scalepractice;

public class Exercise {
    Pitch root;
    Scale scale;

    public Exercise(Scale scale) {
        this.root = Pitch.C2;
        this.scale = scale;
    }

    public Exercise(Pitch root, Scale scale) {
        this.root = root;
        this.scale = scale;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int base = scale.Notes[0];
        int i = 0;
        for (int note : scale.Notes) {
            if (i > 0) {
                sb.append(" ");
            }
            note = note - base;
            Pitch pitch = root.plus(note);
            sb.append(pitch.letter());
            i++;
        }
        return sb.toString();
    }
}
