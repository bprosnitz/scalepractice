package com.example.bprosnitz.scalepractice;

public enum Pitch {
    C0, CSharp0, D0, DSharp0, E0, F0, FSharp0, G0, GSharp0, A0, Asharp0, B0,
    C1, CSharp1, D1, DSharp1, E1, F1, FSharp1, G1, GSharp1, A1, Asharp1, B1,
    C2, CSharp2, D2, DSharp2, E2, F2, FSharp2, G2, GSharp2, A2, Asharp2, B2,
    C3, CSharp3, D3, DSharp3, E3, F3, FSharp3, G3, GSharp3, A3, Asharp3, B3,
    C4, CSharp4, D4, DSharp4, E4, F4, FSharp4, G4, GSharp4, A4, Asharp4, B4,
    C5, CSharp5, D5, DSharp5, E5, F5, FSharp5, G5, GSharp5, A5, Asharp5, B5,
    C6, CSharp6, D6, DSharp6, E6, F6, FSharp6, G6, GSharp6, A6, Asharp6, B6;

    public int midiIndex() {
        return this.ordinal() - Pitch.A0.ordinal() + 21;
    }

    public int minus(Pitch other) {
        return this.ordinal() - other.ordinal();
    }

    public Pitch plus(int zeroBasedInterval) {
        for (Pitch pitch : Pitch.values()) {
            if (zeroBasedInterval == pitch.minus(this)) {
                return pitch;
            }
        }
        throw new RuntimeException("not found");
    }

    private String noteIndex(int x) {
        switch (x) {
            case 0:
                return "C";
            case 1:
                return "C#";
            case 2:
                return "D";
            case 3:
                return "D#";
            case 4:
                return "E";
            case 5:
                return "F";
            case 6:
                return "F#";
            case 7:
                return "G";
            case 8:
                return "G#";
            case 9:
                return "A";
            case 10:
                return "A#";
            case 11:
                return "B";
        }
        return "ERR";
    }

    public String toString() {
        return noteIndex(this.ordinal() % 12) + (this.ordinal() / 12);
    }

    public String letter() {
        return noteIndex(this.ordinal() % 12);
    }
}
