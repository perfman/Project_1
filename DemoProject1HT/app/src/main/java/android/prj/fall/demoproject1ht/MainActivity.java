package android.prj.fall.demoproject1ht;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnPlay;
    Animation animClick;
    MediaPlayer mpClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPlay = (Button)findViewById(R.id.buttonPlay);

        animClick = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.click);

        playGame();
    }

    public void playGame(){
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPlay.startAnimation(animClick);
                playSoundClick();
                startActivity(new Intent(getApplicationContext(), PlayActivity.class));
            }
        });
    }

    public void playSoundClick(){
        mpClick = MediaPlayer.create(this,R.raw.sys_click);
        mpClick.start();
        mpClick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mpClick.release();
            }
        });
    }
}
