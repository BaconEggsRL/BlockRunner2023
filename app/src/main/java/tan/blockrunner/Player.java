package tan.blockrunner;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Player implements GameObject {
    private Rect rectangle;
    private int color;
    public int vel;

    public Player(Rect rectangle, int color){
        this.rectangle = rectangle;
        this.color = color;
        vel = 0;
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

    public boolean playerCollide(Obstacles o){
        if (rectangle.intersects(rectangle, o.getRect())) {
            //if(rectangle.contains(o.getRect()))
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void update() {
        if(rectangle.bottom+vel > Constants.FLOOR) {
            vel = 0;
            rectangle.set(100,Constants.FLOOR - Constants.PLAYER_SIZE, 100 + Constants.PLAYER_SIZE
                    ,Constants.FLOOR);
        }

//        if(rectangle.bottom+vel <= Constants.FLOOR) {
//            vel += Constants.ACCEL;
//            rectangle.set(rectangle.left, rectangle.top + vel,
//                    rectangle.right, rectangle.bottom + vel);
//        }
//        else{
//            vel = Constants.ACCEL;
//            rectangle.set(100,Constants.FLOOR - Constants.PLAYER_SIZE, 100 + Constants.PLAYER_SIZE,Constants.FLOOR);
//        }
    }

    int stateChanges = 0;
    Boolean state = false;
    Boolean canJump = true;
    int maxJumps = 3;
    int numJumps = 0;
    int maxVel = -Constants.SCREEN_HEIGHT/15;
    int minVel = -Constants.SCREEN_HEIGHT/60;
    public void jumpAction (Boolean pressed) {
        if(pressed){
            if(state == false){
                state = true;
                stateChanges++;
            }
            if(canJump) {
                if(vel < maxVel || stateChanges > 3) {
                    canJump = false;
                    stateChanges = 0;
                } else {
                    //System.out.println("Jumping, can jump");
                    if(vel > minVel){
                        vel = minVel;
                    }
                    vel -= (Constants.ACCEL);
                    rectangle.set(rectangle.left, rectangle.top + vel,
                            rectangle.right, rectangle.bottom + vel);
                }
            } else {
                //System.out.println("Jumping, cannot jump");
                if(rectangle.bottom < Constants.FLOOR) {
                    vel += Constants.ACCEL;
                    rectangle.set(rectangle.left, rectangle.top + vel,
                            rectangle.right, rectangle.bottom + vel);
                } else if(rectangle.bottom == Constants.FLOOR) {
                    vel = 0;
                    canJump = true;
                }
            }
        } else {
            if(state == true){
                state = false;
                stateChanges++;
            }
            if(rectangle.bottom < Constants.FLOOR) {
                vel += Constants.ACCEL;
                rectangle.set(rectangle.left, rectangle.top + vel,
                        rectangle.right, rectangle.bottom + vel);
            } else if(rectangle.bottom == Constants.FLOOR) {
                vel = 0;
                canJump = true;
            }
        }
    }
}
