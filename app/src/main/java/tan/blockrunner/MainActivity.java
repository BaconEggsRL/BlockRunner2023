package tan.blockrunner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
// import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Call parent class constructor
        super.onCreate(savedInstanceState);
        //Make app fullscreen
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Remove title
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);







        Intent getValue = getIntent();
        try {
            if(getValue.hasExtra("score")) {
                int score = Integer.parseInt(getValue.getStringExtra("score"));
                int time = Integer.parseInt(getValue.getStringExtra("time"));
                boolean game = Boolean.parseBoolean(getValue.getStringExtra("game"));

                setFile(score, time, game);
            }
        }
        catch (NumberFormatException e){
            System.out.println("Error in File");
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    private void setFile(int score, int time, boolean game) {
        String strScore = "0";
        String strHighTime = "0";
        String strGamePlayed = "0";
        String strTotalTime = "0";
        File stat = new File(this.getFilesDir(), "statFile.txt");
        if(stat.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(stat));

                strScore =(br.readLine());
                strHighTime = (br.readLine());
                strGamePlayed = (br.readLine());
                strTotalTime = (br.readLine());

                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                FileOutputStream out = openFileOutput("statFile.txt", Context.MODE_PRIVATE);
                out.write("0\n0\n0\n0".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int fileScore = Integer.parseInt(strScore);
        int fileHighTime = Integer.parseInt(strHighTime);
        int fileGamePlayed = Integer.parseInt(strGamePlayed);
        int fileTotalTime = Integer.parseInt(strTotalTime);

        if(game)
            fileGamePlayed++;
        if(score>fileScore)
            fileScore = score;
        if (time>fileHighTime)
            fileHighTime = time;
        fileTotalTime += time;

        try {
            stat.delete();
            FileOutputStream out = openFileOutput("statFile.txt", Context.MODE_PRIVATE);
            out.write((fileScore+"\n" + fileHighTime + "\n" + fileGamePlayed + "\n" + fileTotalTime).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newGame(View view){
        Intent startNewGame = new Intent(this,GameActivity.class);
        startActivity(startNewGame);
    }
    public void stat(View view){
        Intent stat = new Intent(this, StatPanel.class);
        startActivity(stat);
    }
}
