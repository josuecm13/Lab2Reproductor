package labs.desarrollo.com.lab2reproductor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    SeekBar volumeSeekBar;
    SeekBar progressSeekBar;
    int songDuration;
    int prgrss;
    boolean isPlaying;
    ListView listView;
    TextView progressTextView;
    TextView progressLeftTextView;
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    int currentSong;

    final String[] songNames = {"Hotel California",
            "If You Leave Me Now",
            "Memories",
            "Piano",
            "Plasir d'Amour",
            "Spanish Flea",
            "Truth of Touch",
            "Unchained Melody",
            "Blue Tango",
            "Cavatina"};

    final int[] songIds = {R.raw.m1,
            R.raw.m2,
            R.raw.m3,
            R.raw.m4,
            R.raw.m5,
            R.raw.m6,
            R.raw.m7,
            R.raw.m8,
            R.raw.m9,
            R.raw.m10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentSong = 0;
        mediaPlayer = MediaPlayer.create(this, songIds[currentSong]);
        isPlaying = false;

        progressTextView = findViewById(R.id.text_view_progress);
        progressLeftTextView = findViewById(R.id.text_view_progress_left);


        //Listview
        listView = findViewById(R.id.list_view_songs);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songNames);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentSong = position;
                isPlaying = false;
                mediaPlayer.reset();
                mediaPlayer = MediaPlayer.create(getApplicationContext(), songIds[currentSong]);
                setSongInfo(songNames[position]);
                songDuration = mediaPlayer.getDuration();
                prgrss = mediaPlayer.getCurrentPosition();
                progressSeekBar.setMax(songDuration);
                progressSeekBar.setProgress(prgrss);
                playSong(view);
            }
        });

        //Volume
        volumeSeekBar = findViewById(R.id.seek_bar_volume);
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumeSeekBar.setMax(maxVolume);
        volumeSeekBar.setProgress(currentVolume);

        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        //Progress
        progressSeekBar = findViewById(R.id.seek_bar_progress);
        songDuration = mediaPlayer.getDuration();
        prgrss = mediaPlayer.getCurrentPosition();

        progressSeekBar.setMax(songDuration);
        progressSeekBar.setProgress(prgrss);

        progressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(progress);
                }
                int minutes = (progress/1000)/60;
                int seconds = (progress/1000)%60;

                int minutesLeft = ((songDuration - progress)/1000)/60;
                int secondsLeft = ((songDuration - progress)/1000)%60;

                progressTextView.setText(String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
                progressLeftTextView.setText(String.format("%02d", minutesLeft) + ":" + String.format("%02d", secondsLeft));


            }



            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                progressSeekBar.setProgress(mediaPlayer.getCurrentPosition());
            }}, 0, 1000
        );

    }

    public void previous_song(View view) {
        if (currentSong == 0)
            currentSong = 9;
        else
            currentSong = (--currentSong)%songIds.length;
        isPlaying = false;
        setSongInfo(songNames[currentSong]);
        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), songIds[currentSong]);
        songDuration = mediaPlayer.getDuration();
        prgrss = mediaPlayer.getCurrentPosition();
        progressSeekBar.setMax(songDuration);
        progressSeekBar.setProgress(prgrss);
        playSong(view);
    }

    public void playSong(View view) {
        //
        if(isPlaying){
            ((ImageView) findViewById(R.id.button_play_song)).setImageResource(R.drawable.ic_play_arrow_black_24dp);
            //pausa
            mediaPlayer.pause();
        }else{
            ((ImageView) findViewById(R.id.button_play_song)).setImageResource(R.drawable.ic_pause_black_24dp);
            //play
            mediaPlayer.start();
        }
        isPlaying = !isPlaying;
    }

    public void nextSong(View view) {
        currentSong = (++currentSong)%songIds.length;
        isPlaying = false;
        setSongInfo(songNames[currentSong]);
        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), songIds[currentSong]);
        songDuration = mediaPlayer.getDuration();
        prgrss = mediaPlayer.getCurrentPosition();
        progressSeekBar.setMax(songDuration);
        progressSeekBar.setProgress(prgrss);
        playSong(view);
    }


    public void setSongInfo(String songName){
        ((TextView) findViewById(R.id.text_view_song_name)).setText(songName);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }

}
