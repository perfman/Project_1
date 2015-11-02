package android.prj.fall.demoproject1ht;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {
    ImageView ivPlay, ivAnswer1, ivAnswer2, ivAnswer3, ivStop, ivPause, ivVolume;
    TextView tvCount, tvAnswer1, tvAnswer2, tvAnswer3, tvScore, tvCountRight;
    SeekBar seekBarVolume;
    AudioManager audioManager;
    MediaPlayer mp;
    String[] arrData = {"apple", "bridge", "bread", "exhausted", "spoon", "refrigerator", "pillow",
            "oven", "chopsticks", "passport", "luggage", "bride", "groom", "teddy",
            "motorcycle", "boat", "plane", "sheep", "crocodile", "eggs", "yogurt", "banana",
            "coconut", "grape", "mango", "honey", "pepper", "chocolate", "continent", "skyscraper",
            "tongue", "massage", "pool", "thief", "wheel", "escalator", "stair", "fortune_teller",
            "zodiac", "vampire", "ghost", "nail_clipper", "razor", "toothpaste", "great_wall",
            "eiffel_tower", "pyramid", "shampoo", "skydive", "temple"};
    List<String> listData = new ArrayList<>();
    int ident_1, ident_2, ident_3, ident_a = 0;
    String answer, audio;
    int r_rest1, r_rest2;
    int run = 0;
    int count = 0;
    int countPlay = 0;
    int countClickRightAnswer;
    int score = 0;
    int countRight = 0;
    boolean right = true; // Trigger count right answer "only recognize in the first time"
    Random r = new Random();
    Animation animClick, animRotate, animZoomText, animZoomLoad;
    static boolean saveGame = false;    // status load saved game or not

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        ivPlay = (ImageView) findViewById(R.id.imageViewPlay);
        ivAnswer1 = (ImageView) findViewById(R.id.imageViewAnswer1);
        ivAnswer2 = (ImageView) findViewById(R.id.imageViewAnswer2);
        ivAnswer3 = (ImageView) findViewById(R.id.imageViewAnswer3);
        ivStop = (ImageView) findViewById(R.id.imageViewStop);
        ivPause = (ImageView) findViewById(R.id.imageViewPause);
        ivVolume = (ImageView) findViewById(R.id.imageViewVolumeControl);
        seekBarVolume = (SeekBar) findViewById(R.id.seekBarVolumeControl);
        tvCount = (TextView) findViewById(R.id.textViewCount);
        tvScore = (TextView) findViewById(R.id.textViewScore);
        tvCountRight = (TextView) findViewById(R.id.textViewCountRight);
        tvAnswer1 = (TextView) findViewById(R.id.textViewAnswer1);
        tvAnswer2 = (TextView) findViewById(R.id.textViewAnswer2);
        tvAnswer3 = (TextView) findViewById(R.id.textViewAnswer3);

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        animClick = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.click);
        animRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        animZoomText = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_text);
        animZoomLoad = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_load);

        seekBarVolume.setVisibility(View.INVISIBLE);

        shuffleData();
        play();
        stopGame();
        controlVolume();
        toggleVolumeControl();
        saveGame();
        loadGame();
    }


    public void play() {
        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivPlay.startAnimation(animClick);
                if (run <= listData.size() - 1) {
                    ident_a = getResources().getIdentifier(listData.get(run), "raw", getPackageName());
                    mp = MediaPlayer.create(getApplicationContext(), ident_a);
                    if (countPlay == 0) {
                        clearText();
                        mp.start();
                        showImages();
                        right = true;
                    } else {
                        mp.start();
                    }
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                        }
                    });

                    countClickRightAnswer = 0;
                    countPlay++;
                    tvCount.setText(Integer.toString(count + 1) + "/" + listData.size());
                } else {
                    alertCompleteGame();
                }
            }
        });
    }

    public void showImages() {
        // Generate auto position for right answer
        int r_pos = r.nextInt(3);

        // Generate auto positions for rest answers
        r_rest1 = r.nextInt(listData.size());
        r_rest2 = r.nextInt(listData.size());
        while (r_rest1 == run) {
            r_rest1 = r.nextInt(listData.size());
        }
        while (r_rest2 == run || r_rest2 == r_rest1) {
            r_rest2 = r.nextInt(listData.size());
        }

        // Generate right image & wrong images
        switch (r_pos) {
            case 0:
                // set image for right answer
                ident_1 = getResources().getIdentifier(listData.get(run), "drawable", getPackageName());
                ivAnswer1.setImageResource(ident_1);
                ivAnswer1.startAnimation(animZoomLoad);
                // set image for rest answers
                ident_2 = getResources().getIdentifier(listData.get(r_rest1), "drawable", getPackageName());
                ivAnswer2.setImageResource(ident_2);
                ivAnswer2.startAnimation(animZoomLoad);
                ident_3 = getResources().getIdentifier(listData.get(r_rest2), "drawable", getPackageName());
                ivAnswer3.setImageResource(ident_3);
                ivAnswer3.startAnimation(animZoomLoad);
                break;
            case 1:
                // set image for right answer
                ident_2 = getResources().getIdentifier(listData.get(run), "drawable", getPackageName());
                ivAnswer2.setImageResource(ident_2);
                ivAnswer2.startAnimation(animZoomLoad);
                // set image for rest answers
                ident_1 = getResources().getIdentifier(listData.get(r_rest1), "drawable", getPackageName());
                ivAnswer1.setImageResource(ident_1);
                ivAnswer1.startAnimation(animZoomLoad);
                ident_3 = getResources().getIdentifier(listData.get(r_rest2), "drawable", getPackageName());
                ivAnswer3.setImageResource(ident_3);
                ivAnswer3.startAnimation(animZoomLoad);
                break;
            case 2:
                // set image for right answer
                ident_3 = getResources().getIdentifier(listData.get(run), "drawable", getPackageName());
                ivAnswer3.setImageResource(ident_3);
                ivAnswer3.startAnimation(animZoomLoad);
                // set image for rest answers
                ident_1 = getResources().getIdentifier(listData.get(r_rest1), "drawable", getPackageName());
                ivAnswer1.setImageResource(ident_1);
                ivAnswer1.startAnimation(animZoomLoad);
                ident_2 = getResources().getIdentifier(listData.get(r_rest2), "drawable", getPackageName());
                ivAnswer2.setImageResource(ident_2);
                ivAnswer2.startAnimation(animZoomLoad);
                break;
        }
    }

    public void clearText() {
        tvAnswer1.setText(null);
        tvAnswer2.setText(null);
        tvAnswer3.setText(null);
    }

    public void checkAnswer(View view) {
        switch (view.getId()) {
            case R.id.imageViewAnswer1:
                if (ident_a == 0) {
                    showToast("Please tap the headphone icon to hear the audio !");
                    break;
                } else {
                    // Get entry name image_1 & audio
                    answer = getResources().getResourceEntryName(ident_1);
                    audio = getResources().getResourceEntryName(ident_a);
                    // Comparison
                    if (answer.equalsIgnoreCase(audio)) {
                        ivAnswer1.startAnimation(animClick);
                        alertSound(1);
                        tvAnswer1.setText(answer);
                        tvAnswer1.startAnimation(animZoomText);

                        countPlay = 0;
                        if (countClickRightAnswer == 0) {
                            run++;
                            count++;
                            tvScore.setText(Integer.toString(++score));
                            if (right == true) {
                                tvCountRight.setText(Integer.toString(++countRight));
                                right = false;
                            }
                        }
                        play();
                        countClickRightAnswer++;
                    } else {
                        ivAnswer1.startAnimation(animRotate);
                        alertSound(0);
                        tvScore.setText(Integer.toString(--score));
                        right = false;
                    }
                    break;
                }
            case R.id.imageViewAnswer2:
                if (ident_a == 0) {
                    showToast("Please tap the headphone icon to hear the audio !");
                    break;
                } else {
                    ivAnswer2.startAnimation(animClick);
                    // Get entry name image_2 & audio
                    answer = getResources().getResourceEntryName(ident_2);
                    audio = getResources().getResourceEntryName(ident_a);
                    // Comparison
                    if (answer.equalsIgnoreCase(audio)) {
                        ivAnswer2.startAnimation(animClick);
                        alertSound(1);
                        tvAnswer2.setText(answer);
                        tvAnswer2.startAnimation(animZoomText);

                        countPlay = 0;
                        if (countClickRightAnswer == 0) {
                            count++;
                            run++;
                            tvScore.setText(Integer.toString(++score));
                            if (right == true) {
                                tvCountRight.setText(Integer.toString(++countRight));
                                right = false;
                            }
                        }
                        play();
                        countClickRightAnswer++;
                    } else {
                        ivAnswer2.startAnimation(animRotate);
                        alertSound(0);
                        tvScore.setText(Integer.toString(--score));
                        right = false;
                    }
                    break;
                }
            case R.id.imageViewAnswer3:
                if (ident_a == 0) {
                    showToast("Please tap the headphone icon to hear the audio !");
                    break;
                } else {
                    ivAnswer3.startAnimation(animClick);
                    // Get entry name image_3 & audio
                    answer = getResources().getResourceEntryName(ident_3);
                    audio = getResources().getResourceEntryName(ident_a);
                    // Comparison
                    if (answer.equalsIgnoreCase(audio)) {
                        ivAnswer3.startAnimation(animClick);
                        alertSound(1);
                        tvAnswer3.setText(answer);
                        tvAnswer3.startAnimation(animZoomText);

                        countPlay = 0;
                        if (countClickRightAnswer == 0) {
                            count++;
                            run++;
                            tvScore.setText(Integer.toString(++score));
                            if (right == true) {
                                tvCountRight.setText(Integer.toString(++countRight));
                                right = false;
                            }
                        }
                        play();
                        countClickRightAnswer++;
                    } else {
                        ivAnswer3.startAnimation(animRotate);
                        alertSound(0);
                        tvScore.setText(Integer.toString(--score));
                        right = false;
                    }
                    break;
                }
        }
    }

    public void shuffleData() {
        for (int i = 0; i < arrData.length; i++) {
            listData.add(arrData[i]);
        }
        Collections.shuffle(listData);
    }

    public void alertSound(int num) {
        MediaPlayer mpAlert;
        if (num == 0) {
            mpAlert = MediaPlayer.create(PlayActivity.this, R.raw.sys_wrong);
            mpAlert.setVolume(0.5f, 0.5f);
            mpAlert.start();
            mpAlert.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        }
        if (num == 1) {
            mpAlert = MediaPlayer.create(PlayActivity.this, R.raw.sys_right);
            mpAlert.setVolume(0.5f, 0.5f);
            mpAlert.start();
            mpAlert.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        }
        if (num == 2) {
            mpAlert = MediaPlayer.create(PlayActivity.this, R.raw.sys_congratulation);
            mpAlert.setVolume(0.5f, 0.5f);
            mpAlert.start();
            mpAlert.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        }
    }

    public void alertCompleteGame() {
        alertSound(2);
        Dialog dialog = new Dialog(this);
        dialog.setTitle("Complete Level");
        dialog.setContentView(R.layout.custom_dialog_complete_game);
        TextView scoreRef = (TextView) dialog.findViewById(R.id.tv_cus_Score);
        TextView countRightRef = (TextView) dialog.findViewById(R.id.tv_cus_CountRight);
        Button btnGoMainRef = (Button) dialog.findViewById(R.id.btn_cus_Main);
        Button btnReplayRef = (Button) dialog.findViewById(R.id.btn_cus_Replay);

        scoreRef.setText(Integer.toString(score));
        countRightRef.setText(Integer.toString(countRight));
        btnGoMainRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlayActivity.this, MainActivity.class));
            }
        });
        btnReplayRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlayActivity.this, PlayActivity.class));
            }
        });
        dialog.show();
    }

    public void showToast(String msg) {
        View layout = getLayoutInflater().inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.layout_cus_toast));
        Toast toast = new Toast(this);
        toast.setView(layout);
        toast.setDuration(Toast.LENGTH_SHORT);
        TextView txt = (TextView) layout.findViewById(R.id.tv_cus_toast);
        txt.setText(msg);
        toast.show();
    }

    public void stopGame() {
        ivStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
                builder.setTitle("Exit the lesson");
                builder.setMessage("Are you sure exit this lesson ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveGame = false;
                        startActivity(new Intent(PlayActivity.this, MainActivity.class));
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
            }
        });
    }

    public void controlVolume() {
        seekBarVolume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        seekBarVolume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
    }

    public void toggleVolumeControl() {
        ivVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seekBarVolume.isShown()) {
                    seekBarVolume.setVisibility(View.INVISIBLE);
                } else {
                    seekBarVolume.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void saveGame() {
        ivPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
                builder.setTitle("Save the lesson");
                builder.setMessage("Are you sure save this lesson?");
                builder.setNegativeButton("No", null);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharePref = getSharedPreferences("save_game", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharePref.edit();
                        JSONArray jsonArray = new JSONArray();
                        for (int i = 0; i < listData.size(); i++) {
                            jsonArray.put(listData.get(i));
                        }
                        editor.putString("list", jsonArray.toString());
                        editor.putInt("count", run);
                        editor.putInt("score", score);
                        editor.putInt("countRight", countRight);
                        editor.putInt("size",listData.size());
                        // save current time
                        SimpleDateFormat format = new SimpleDateFormat("hh:mm dd/MM/yyyy");
                        String currentTime = format.format(new Date());
                        editor.putString("time",currentTime);
                        if (editor.commit()) {
                            showToast("Game save successful !");
                        } else {
                            showToast("Game save failed !");
                        }
                    }
                });
                builder.show();
            }
        });
    }

    public void loadGame() {
        if(saveGame){
            SharedPreferences sharePref = getSharedPreferences("save_game",MODE_PRIVATE);
            // retrieve list
            String str = sharePref.getString("list",null);
            if(str!=null){
                try {
                    JSONArray json = new JSONArray(str);
                    List<String> listReturn = new ArrayList<>();
                    for(int i=0; i<json.length();i++){
                        listReturn.add(json.optString(i));
                    }
                    listData = listReturn;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            // retrieve count
            int countReturn = sharePref.getInt("count",-1);
            if(countReturn!=-1){
                run = countReturn;
                count = countReturn;
            }
            // retrieve score
            int scoreReturn = sharePref.getInt("score",-1);{
                if(scoreReturn!=-1){
                    score = scoreReturn;
                }
            }
            // retrieve countRight
            int countRightReturn = sharePref.getInt("countRight",-1);
            if(countRightReturn!=-1){
                countRight = countRightReturn;
            }
            // set status count / score / countRight
            tvCount.setText(Integer.toString(count+1)+"/"+listData.size());
            tvScore.setText(Integer.toString(score));
            tvCountRight.setText(Integer.toString(countRight));
        }
    }






















    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
