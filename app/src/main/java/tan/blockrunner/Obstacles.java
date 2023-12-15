package tan.blockrunner;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by TAN on 11/19/2016.
 */

public class Obstacles implements GameObject {
    private Rect rectangle;
    private int color;
    private int vel;

    public Obstacles(Rect rectangle, int color) {
        this.color = color;
        this.rectangle = rectangle;
        vel = Constants.SCREEN_WIDTH/50;
    }

    public Obstacles(int level) {
        color = Color.GREEN;
        rectangle = new Rect(Constants.SCREEN_WIDTH, Constants.FLOOR - (int)(Math.random()*Constants.PLAYER_SIZE/2+Constants.PLAYER_SIZE/5),
                Constants.SCREEN_WIDTH + (int)(Math.random()*Constants.PLAYER_SIZE/2+Constants.PLAYER_SIZE/5) , Constants.FLOOR);
        vel = (int)(Constants.SCREEN_WIDTH/55/1.5*Math.random()+Constants.SCREEN_WIDTH/50) + level;
    }

    public Obstacles(int level, int left) {
        color = Color.GREEN;
        rectangle = new Rect(left + Constants.SCREEN_WIDTH/2, Constants.FLOOR - (int)(Math.random()*Constants.PLAYER_SIZE/2+Constants.PLAYER_SIZE/5),
                Constants.SCREEN_WIDTH/2 +left + (int)(Math.random()*Constants.PLAYER_SIZE/2+Constants.PLAYER_SIZE/5) , Constants.FLOOR);
        vel = (int)(Constants.SCREEN_WIDTH/55/2*Math.random()+Constants.SCREEN_WIDTH/50) + level;
    }


    public Rect getRect(){
        return rectangle;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle,paint);
    }

    @Override
    public void update() {
        rectangle.set(rectangle.left - vel, rectangle.top,
                rectangle.right - vel, rectangle.bottom);
    }
}
