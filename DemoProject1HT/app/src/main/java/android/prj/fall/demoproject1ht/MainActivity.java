package android.prj.fall.demoproject1ht;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnPlay, btnLoad, btnHelp;
    Animation animClick;
    MediaPlayer mpClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPlay = (Button) findViewById(R.id.buttonPlay);
        btnLoad = (Button) findViewById(R.id.buttonLoad);
        btnHelp = (Button) findViewById(R.id.buttonHelp);

        animClick = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.click);

        playGame();
        loadGame();
        showHelp();
    }

    public void playGame() {
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPlay.startAnimation(animClick);
                playSoundClick();
                startActivity(new Intent(getApplicationContext(), PlayActivity.class));
            }
        });
    }

    public void playSoundClick() {
        mpClick = MediaPlayer.create(this, R.raw.sys_click);
        mpClick.start();
        mpClick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mpClick.release();
            }
        });
    }

    public void loadGame() {
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLoad.startAnimation(animClick);
                playSoundClick();
                if(getInfoSaveGame("checkShare").equals("no")){
                    PlayActivity.showToast(MainActivity.this,"You're not save any game yet !");
                }else{
                    final Dialog dialog = new Dialog(MainActivity.this);
                    dialog.setTitle("Load Game");
                    dialog.setContentView(R.layout.custom_dialog_load_game);
                    TextView time = (TextView) dialog.findViewById(R.id.tv_cus_InfoSavedGame_Time);
                    TextView position = (TextView) dialog.findViewById(R.id.tv_cus_InfoSavedGame_Position);
                    time.setText(getInfoSaveGame("time"));
                    position.setText(getInfoSaveGame("position"));

                    Button btnCancel = (Button) dialog.findViewById(R.id.btn_cus_CancelLoadGame);
                    Button btnOK = (Button) dialog.findViewById(R.id.btn_cus_OKLoadGame);
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
            }
        });
    }

    public String getInfoSaveGame(String type) {
        SharedPreferences share = getSharedPreferences("save_game", MODE_PRIVATE);
        if (type.equals("time")) {
            String time = share.getString("time", "");
            return time;
        } else if (type.equals("position")) {
            int count = share.getInt("count", 0);
            int size = share.getInt("size", 0);
            return Integer.toString(count + 1) + "/" + Integer.toString(size);

        } else if(type.equals("checkShare")){
            if(!share.contains("list")){
                return "no";
            }
        }
        return "";
    }

    public void showHelp(){
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSoundClick();
                startActivity(new Intent(MainActivity.this,HelpActivity.class));
            }
        });
    }
}
