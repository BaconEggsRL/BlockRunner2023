package tan.blockrunner;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by eas_c on 11/19/2016.
 */

public class Ground implements GameObject {

    private Rect rectangle;
    private int color;

    public Ground(Rect rectangle, int color){
        this.rectangle = rectangle;
        this.color = color;
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

    }
}
