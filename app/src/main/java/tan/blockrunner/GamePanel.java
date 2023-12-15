package tan.blockrunner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private Obstacles obstacle2;
    private MainThread thread;

    private Player player;
    private Ground ground;
    private Obstacles obstacle;
    private int score = 0;
    private int time = 0;
    private long startTime = 0;
    private long endTime = 0;
    private int level = 0;
    private TextView textView;
    private TextView textView2;
    private LinearLayout linearLayout;
    boolean pressed = false;


    public GamePanel(Context context){
        super(context);
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        player = new Player(new Rect(100,Constants.FLOOR - Constants.PLAYER_SIZE, 100 + Constants.PLAYER_SIZE
                ,Constants.FLOOR), Color.rgb(255,0,0));
        ground = new Ground(new Rect(0,Constants.FLOOR, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT), Color.rgb(0,0,0));
        obstacle = new Obstacles(new Rect((int) (Constants.SCREEN_WIDTH*1.05),Constants.FLOOR-Constants.PLAYER_SIZE/2,
                (int) (Constants.SCREEN_WIDTH*1.05) +Constants.PLAYER_SIZE/2,Constants.FLOOR), Color.rgb(0,255,0));
        obstacle2 = new Obstacles(new Rect((int)(1.5*Constants.SCREEN_WIDTH),(int) (Constants.FLOOR-Constants.PLAYER_SIZE/2*1.2),
                (int)(1.5*Constants.SCREEN_WIDTH)+Constants.PLAYER_SIZE/2,Constants.FLOOR), Color.rgb(0,255,0));
        startTime = System.currentTimeMillis();

        linearLayout = new LinearLayout(this.getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        textView2 = new TextView(this.getContext());
        textView2.setText("Score: 0");
        textView2.setVisibility(View.VISIBLE);
        linearLayout.addView(textView2);

        textView = new TextView(this.getContext());
        textView.setText("Time: 0");
        textView.setVisibility(View.VISIBLE);
        linearLayout.addView(textView);

        /*

        TextView txtView = new TextView(this.getContext());
        txtView.setText("Tap the screen to jump!");
        txtView.setVisibility(View.VISIBLE);
        linearLayout.addView(txtView);

        View viewToAnimate = findViewById(txtView.getId());
        Animation out = AnimationUtils.makeOutAnimation(this.getContext(), true);
        viewToAnimate.startAnimation(out);
        viewToAnimate.setVisibility(View.INVISIBLE);
        */

        /*
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f , 1.0f ) ;
        AlphaAnimation fadeOut = new AlphaAnimation( 1.0f , 0.0f ) ;
        txtView.startAnimation(fadeIn);
        txtView.startAnimation(fadeOut);
        fadeIn.setDuration(1200);
        fadeIn.setFillAfter(true);
        fadeOut.setDuration(1200);
        fadeOut.setFillAfter(true);
        fadeOut.setStartOffset(4200+fadeIn.getStartOffset());
        */


        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry)
        {
            try{thread.setRunning(false);
                thread.join();

            }catch(InterruptedException e){e.printStackTrace();}
            retry = false;
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
//                if(player.getRect().bottom >= Constants.FLOOR* 0.98 && !pressed) {
                    pressed = true;
//                    player.getRect().set(player.getRect().left, player.getRect().top - 5,
//                            player.getRect().right, player.getRect().bottom - 5);
//                    player.vel = Constants.ACCEL - (int) (Constants.SCREEN_HEIGHT / 7.5);
//                }
                break;
            case MotionEvent.ACTION_UP:
                pressed = false;
                break;
        }
        return true;

    }
    public void update() {
        endTime = System.currentTimeMillis();
        time = (int) ((endTime-startTime)/1000);
        level = time / 3 * Constants.SCREEN_WIDTH/50/20;
        if(player.playerCollide(obstacle) || player.playerCollide(obstacle2)) {
            thread.setRunning(false);
            thread.interrupt();
            Intent GameOver = new Intent(this.getContext(),GameOverActivity.class);
            GameOver.putExtra("time", time+"");
            GameOver.putExtra("score", score+"");
            this.getContext().startActivity(GameOver);
        }
        player.update();
        player.jumpAction(pressed);
        ground.update();
        obstacle.update();
        obstacle2.update();
        textView.setText("Time: " + time);
        textView2.setText("Score: " + score);

        if(obstacle.getRect().right < 0){
            obstacle = new Obstacles(level);
            score ++;
        }
        if(obstacle2.getRect().right<0 && obstacle2.getRect().top != 0){
            obstacle2 = new Obstacles(new Rect(-1,0,-1,0), Color.WHITE);
            score ++;
            score ++;
        }
        if(Math.random()<0.3 && obstacle.getRect().right > Constants.SCREEN_WIDTH && obstacle2.getRect().right<0){
            obstacle2 = new Obstacles(level, obstacle.getRect().left);
        }

    }
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        player.draw(canvas);
        ground.draw(canvas);
        obstacle.draw(canvas);
        obstacle2.draw(canvas);

        linearLayout.measure(canvas.getWidth(), canvas.getHeight());
        linearLayout.layout(0, 0, canvas.getWidth(), canvas.getHeight());

        linearLayout.draw(canvas);

    }
}