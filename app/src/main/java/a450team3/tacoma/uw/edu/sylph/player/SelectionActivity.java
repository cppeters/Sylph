package a450team3.tacoma.uw.edu.sylph.player;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import a450team3.tacoma.uw.edu.sylph.R;
public class SelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        Button playVideo = (Button)findViewById(R.id.play_video_youtube);
        Button convert = (Button)findViewById(R.id.convert_video);
        Button playAudio = (Button)findViewById(R.id.play_local) ;

        playAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),PlaySavedActivity.class));
            }
        });

        playVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),YoutubePlayerActivity.class));
            }
        });

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),ConvertActivity.class));
            }
        });
    }
}
