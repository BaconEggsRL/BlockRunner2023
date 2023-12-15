package tan.blockrunner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
// import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class StatPanel extends AppCompatActivity {
    String score;
    String highTime;
    String gamePlayed;
    String totalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_stat_panel);

        TextView tvscore = (TextView) findViewById(R.id.HighScore);
        TextView tvhighTime = (TextView) findViewById(R.id.HighestTime);
        TextView tvgamePlayed = (TextView) findViewById(R.id.GamesPlayed);
        TextView tvtotalTime = (TextView) findViewById(R.id.TotalTime);
        tvscore.setText("0");
        tvhighTime.setText("0");
        tvgamePlayed.setText("0");
        tvtotalTime.setText("0");

        File stat = new File(this.getFilesDir(), "statFile.txt");
        if(stat.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(stat));

                tvscore.setText(br.readLine());
                tvhighTime.setText(br.readLine());
                tvgamePlayed.setText(br.readLine());
                tvtotalTime.setText(br.readLine());

                br.close();
                stat.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                FileOutputStream out = openFileOutput("statFile.txt", Context.MODE_PRIVATE);
                out.write("0\n0\n0\n0".getBytes());
                tvscore.setText("0");
                tvhighTime.setText("0");
                tvgamePlayed.setText("0");
                tvtotalTime.setText("0");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        score = tvscore.getText().toString();
        highTime = tvhighTime.getText().toString();
        gamePlayed = tvgamePlayed.getText().toString();
        totalTime = tvtotalTime.getText().toString();
    }

    public void toMenu(View view){
        Intent toMenu = new Intent(this, MainActivity.class);
        finish();
        startActivity(toMenu);
    }
    @Override
    protected void onStop() {
        super.onStop();
        try {
            FileOutputStream out = openFileOutput("statFile.txt", Context.MODE_PRIVATE);
            out.write((score+"\n"+highTime+"\n"+gamePlayed+"\n"+totalTime).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
