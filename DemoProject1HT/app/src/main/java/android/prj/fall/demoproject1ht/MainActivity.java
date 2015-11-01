package android.prj.fall.demoproject1ht;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnPlay, btnLoad;
    Animation animClick;
    MediaPlayer mpClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPlay = (Button)findViewById(R.id.buttonPlay);
        btnLoad = (Button)findViewById(R.id.buttonLoad);

        animClick = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.click);

        playGame();
        loadGame();
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

    public void loadGame(){
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLoad.startAnimation(animClick);
                playSoundClick();

                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setTitle("Load Game");
                dialog.setContentView(R.layout.custom_dialog_load_game);
                TextView info = (TextView)dialog.findViewById(R.id.tv_cus_InfoSavedGame);
                Button btnCancel = (Button)dialog.findViewById(R.id.btn_cus_CancelLoadGame);
                Button btnOK = (Button)dialog.findViewById(R.id.btn_cus_OKLoadGame);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PlayActivity.saveGame = true;
                        startActivity(new Intent(MainActivity.this, PlayActivity.class));
                    }
                });
                dialog.show();
            }
        });
    }
}
