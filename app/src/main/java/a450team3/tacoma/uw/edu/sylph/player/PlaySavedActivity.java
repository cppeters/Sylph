package a450team3.tacoma.uw.edu.sylph.player;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import a450team3.tacoma.uw.edu.sylph.R;

public class PlaySavedActivity extends Activity {
    private SeekBar musicSeekBar;
    public ArrayList<String> pathList;
    public MediaPlayer mediaPlayer;
    public int i;
    public final static String FILEPATH = Environment.getExternalStorageDirectory().toString() + "/Sylph/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        i = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_saved);
        pathList = new ArrayList<>();

        String name = Environment.getExternalStorageDirectory().getAbsolutePath();
        File direct = new File(name + "/Sylph/");
        if (!direct.exists()) {
            direct.mkdirs();
        }
        AssetManager mgr = getAssets();
        try {
            String list[] = mgr.list(FILEPATH);
            Log.e("FILES __________", String.valueOf(list.length));
            File directory = new File(FILEPATH);
            File[] files = directory.listFiles();
            Log.d("Files", "Size: " + files.length);
            for (int i = 0; i < files.length; i++) {
                Log.d("Files", "FileName:" + files[i].getName());
                pathList.add(files[i].getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!pathList.isEmpty()) {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(FILEPATH + pathList.get(0));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ImageButton next = (ImageButton) findViewById(R.id.media_ff);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i++;
                    if (i < pathList.size()) {
                        mediaPlayer.reset();
                        try {
                            Log.i("*******", pathList.get(i));
                            mediaPlayer.setDataSource(FILEPATH + String.valueOf(pathList.get(i)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            mediaPlayer.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        mediaPlayer.start();
                    } else {
                        i = 0;
                        mediaPlayer.reset();
                        try {
                            Log.i("*******", pathList.get(i));
                            mediaPlayer.setDataSource(FILEPATH + String.valueOf(pathList.get(i)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            mediaPlayer.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        mediaPlayer.start();
                    }
                    TextView title = (TextView) findViewById(R.id.songName);
                    title.setText(pathList.get(i));
                }
            });

            ImageButton stop = (ImageButton) findViewById(R.id.media_pause);
            stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mediaPlayer.isPlaying() == true) {
                        mediaPlayer.pause();
                    }
                }
            });
            ImageButton play = (ImageButton) findViewById(R.id.media_play);
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mediaPlayer.isPlaying() == false) {
                        mediaPlayer.start();
                    }
                }
            });
            TextView title = (TextView) findViewById(R.id.songName);
            title.setText(pathList.get(i));
            musicSeekBar = (SeekBar) findViewById(R.id.seekBar);
            musicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.seekTo(progress);
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
        else{
            Toast.makeText(this, "The List is Empty !!!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,SelectionActivity.class));
        }

    }



}
