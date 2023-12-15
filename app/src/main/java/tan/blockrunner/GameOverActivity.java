package tan.blockrunner;

import android.content.Intent;
// import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {
    String score;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game_over);

        Intent getValues = getIntent();
        score = getValues.getExtras().getString("score")+"";
        time = getValues.getExtras().getString("time")+"";

        TextView tScore = (TextView) findViewById(R.id.SCORE);
        tScore.setText(score);
        TextView tTime = (TextView) findViewById(R.id.TIME);
        tTime.setText(time);
    }

    @Override
    public void onBackPressed(){
        toMenu(new View(this));
    }

    public void toMenu(View view)
    {
        Intent toMenu = new Intent(this, MainActivity.class);
        toMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        toMenu.putExtra("time", time);
        toMenu.putExtra("score", score);
        toMenu.putExtra("game", "true");
        finish();
        startActivity(toMenu);
    }
}
