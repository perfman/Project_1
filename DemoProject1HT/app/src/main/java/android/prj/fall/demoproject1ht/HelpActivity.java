package android.prj.fall.demoproject1ht;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {
    ImageView ivPlay, ivAnswer1, ivAnswer2, ivAnswer3, ivStop, ivPause, ivVolume;
    TextView tvAnswer1, tvAnswer2, tvAnswer3, tvScore, tvCountRight, tvNext, tvGuide;
    SeekBar seekBarVolume;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ivPlay = (ImageView) findViewById(R.id.imageViewPlay2);
        ivAnswer1 = (ImageView) findViewById(R.id.imageViewAnswer12);
        ivAnswer2 = (ImageView) findViewById(R.id.imageViewAnswer22);
        ivAnswer3 = (ImageView) findViewById(R.id.imageViewAnswer32);
        ivStop = (ImageView) findViewById(R.id.imageViewStop2);
        ivPause = (ImageView) findViewById(R.id.imageViewPause2);
        ivVolume = (ImageView) findViewById(R.id.imageViewVolumeControl2);
        seekBarVolume = (SeekBar) findViewById(R.id.seekBarVolumeControl2);
        tvScore = (TextView) findViewById(R.id.textViewScore2);
        tvCountRight = (TextView) findViewById(R.id.textViewCountRight2);
        tvAnswer1 = (TextView) findViewById(R.id.textViewAnswer12);
        tvAnswer2 = (TextView) findViewById(R.id.textViewAnswer22);
        tvAnswer3 = (TextView) findViewById(R.id.textViewAnswer32);
        tvNext = (TextView) findViewById(R.id.tvNext_GuideBoard);
        tvGuide = (TextView) findViewById(R.id.tvGuide_GuideBoard);

        runGuide();
    }

    public void runGuide(){
        ivPlay.setVisibility(View.VISIBLE);
        tvGuide.setText("Click the headphone icon to hear the audio");

        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (count){
                    case 0:
                        ivAnswer2.setVisibility(View.VISIBLE);
                        tvAnswer2.setVisibility(View.VISIBLE);
                        tvAnswer2.setText("mango");
                        tvGuide.setText("Choose the answer matches with audio");
                        ivPlay.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        tvScore.setVisibility(View.VISIBLE);
                        ivAnswer2.setVisibility(View.INVISIBLE);
                        tvAnswer2.setVisibility(View.INVISIBLE);
                        tvAnswer2.setText(null);
                        tvGuide.setText("Show the score");
                        break;
                    case 2:
                        tvCountRight.setVisibility(View.VISIBLE);
                        tvScore.setVisibility(View.INVISIBLE);
                        tvGuide.setText("Count the right answers");
                        break;
                    case 3:
                        ivStop.setVisibility(View.VISIBLE);
                        tvCountRight.setVisibility(View.INVISIBLE);
                        tvGuide.setText("Stop the lesson");
                        break;
                    case 4:
                        ivPause.setVisibility(View.VISIBLE);
                        ivStop.setVisibility(View.INVISIBLE);
                        tvGuide.setText("Save the lesson");
                        break;
                    case 5:
                        ivVolume.setVisibility(View.VISIBLE);
                        ivPause.setVisibility(View.INVISIBLE);
                        tvGuide.setText("Control the volume");
                        tvNext.setText("Got it");
                        break;
                    case 6:
                        startActivity(new Intent(HelpActivity.this,MainActivity.class));
                        break;
                }
                count++;
            }
        });
    }
}
