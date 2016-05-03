package com.example.bprosnitz.scalepractice;

import android.database.DataSetObserver;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.leff.midi.MidiFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int interval(Pitch lower, Pitch upper) {
        return upper.ordinal() - lower.ordinal();
    }

    private int cnote(Pitch note) {
        return interval(Pitch.C0, note);
    }

    int scale = 0;

    private List<Exercise> exercises = Arrays.asList(
            // ascending and descending
            new Exercise(new Scale(new int[]{0, cnote(Pitch.G0), 0})),
            new Exercise(new Scale(new int[]{0, cnote(Pitch.E0), cnote(Pitch.G0), cnote(Pitch.E0), 0})),
            // there is also a version of the below one where the note is held
            new Exercise(new Scale(new int[]{0, cnote(Pitch.E0), cnote(Pitch.F0), cnote(Pitch.G0), cnote(Pitch.F0), cnote(Pitch.E0), 0})),
            new Exercise(new Scale(new int[]{0, cnote(Pitch.E0), cnote(Pitch.F0), cnote(Pitch.G0), cnote(Pitch.G0), cnote(Pitch.G0), cnote(Pitch.G0), cnote(Pitch.F0), cnote(Pitch.E0), 0})),
            new Exercise(new Scale(new int[]{0, cnote(Pitch.D0), cnote(Pitch.E0), cnote(Pitch.F0), cnote(Pitch.G0), cnote(Pitch.F0), cnote(Pitch.E0), cnote(Pitch.D0), 0})),
            new Exercise(new Scale(new int[]{0, cnote(Pitch.E0), cnote(Pitch.G0), cnote(Pitch.C1), cnote(Pitch.E1), cnote(Pitch.G1), cnote(Pitch.F1), cnote(Pitch.D1), cnote(Pitch.B0), cnote(Pitch.G0), cnote(Pitch.F0), cnote(Pitch.D0), 0})),

            // descending
            new Exercise(new Scale(new int[]{interval(Pitch.F0, Pitch.C1), interval(Pitch.F0, Pitch.A0), 0})),
            new Exercise(new Scale(new int[]{interval(Pitch.G0, Pitch.D1), interval(Pitch.G0, Pitch.C1),interval(Pitch.G0, Pitch.B0),interval(Pitch.G0, Pitch.A0), 0}))
            );
    private List<Pitch> roots = Arrays.asList(Pitch.C2, Pitch.CSharp2, Pitch.D2, Pitch.DSharp2, Pitch.E2, Pitch.F2, Pitch.FSharp2, Pitch.G2, Pitch.GSharp2, Pitch.A2, Pitch.Asharp2, Pitch.B2,
            Pitch.C3, Pitch.CSharp3, Pitch.D3, Pitch.DSharp3, Pitch.E3, Pitch.F3, Pitch.FSharp3, Pitch.G3, Pitch.GSharp3, Pitch.A3, Pitch.Asharp3, Pitch.B3,
            Pitch.C4, Pitch.CSharp4, Pitch.D4, Pitch.DSharp4, Pitch.E4, Pitch.F4, Pitch.FSharp4, Pitch.G4, Pitch.GSharp4, Pitch.A4, Pitch.Asharp4, Pitch.B4,
            Pitch.C5, Pitch.CSharp5, Pitch.D5, Pitch.DSharp5, Pitch.E5, Pitch.F5, Pitch.FSharp5, Pitch.G5, Pitch.GSharp5, Pitch.A5, Pitch.Asharp5, Pitch.B5);
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView scaleList = (ListView)findViewById(R.id.scaleList);
        scaleList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        scaleList.setAdapter(new ArrayAdapter<Exercise>(this, R.layout.exercise_item, exercises));
        scaleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("TAG", "Scale"+position);
                scale = position;
                view.setSelected(true);
            }
        });
        ListView rootList = (ListView)findViewById(R.id.rootList);
        rootList.setChoiceMode(ListView.CHOICE_MODE_NONE);
        rootList.setAdapter(new ArrayAdapter<Pitch>(this, R.layout.exercise_item, roots));
        rootList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("TAG", "Root"+position);
                writeScale(roots.get(position), exercises.get(scale).scale);
                MediaPlayer player = MediaPlayer.create(MainActivity.this, Uri.parse("/data/data/com.example.bprosnitz.scalepractice/lib/x/exampleout.mid"));
                player.start();
            }
        });
    }

    private void writeScale(Pitch root, Scale scale) {
        MidiFile midi = scale.ToMIDI(root, 60);
        new File("/data/data/com.example.bprosnitz.scalepractice/lib/x").mkdirs();
        File output = new File("/data/data/com.example.bprosnitz.scalepractice/lib/x/exampleout.mid");
        try
        {
            midi.writeToFile(output);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
