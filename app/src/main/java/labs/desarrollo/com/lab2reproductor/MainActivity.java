package labs.desarrollo.com.lab2reproductor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

//TODO: implementar metodos
//TODO: AudioManager, MediaManager, Runnables, SetOnSeekBarChange
//TODO: ArrayAdapter, onItemClicked

public class MainActivity extends AppCompatActivity {

    SeekBar volumeSeekBar;
    SeekBar progressSeekBar;
    String songName;
    String progress;
    String progressLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        volumeSeekBar = findViewById(R.id.seek_bar_volume);
        progressSeekBar = findViewById(R.id.seek_bar_progress);

        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void previous_song(View view) {
        //
    }

    public void playSong(View view) {
        //
    }

    public void nextSong(View view) {
        //
    }
}
