package tan.blockrunner;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.TextView;

public class SuperTextView extends TextView {

    private boolean ready;

    public SuperTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ready = false;
        addTextChangedListener(new CustomTextWatcher(this));
    }

    public SuperTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ready = false;
        addTextChangedListener(new CustomTextWatcher(this));
    }

    public SuperTextView(Context context) {
        super(context);
        ready = false;
        addTextChangedListener(new CustomTextWatcher(this));
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (ready && getText() != null && text != null) {
            String toChange = (String) text.toString();
            String myText = "" + getText();
            if (!myText.equals(toChange)) super.setText(text, type);
        } else super.setText(text, type);
    }


    public class CustomTextWatcher implements TextWatcher {

        private TextView element;
        private Integer[] rgb;

        public CustomTextWatcher(TextView element) {
            this.element = element;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            final int from = 255, to = 0;

            ValueAnimator anim = ValueAnimator.ofFloat(0, 1);
            anim.setDuration(500);

            rgb = parseColor(element.getCurrentTextColor());
            final int[] alpha  = new int[1];
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    alpha[0] = (int) (from + (to - from)*animation.getAnimatedFraction());
                    element.setTextColor(Color.argb(alpha[0], rgb[0], rgb[1], rgb[2]));
                }
            });

            anim.start();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //Log.e("Text","change");
        }

        @Override
        public void afterTextChanged(Editable s) {
            ready = true;
            final int from = 0, to = 255;

            ValueAnimator anim = ValueAnimator.ofFloat(0, 1);
            anim.setDuration(500);

            final int[] alpha  = new int[1];
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    alpha[0] = (int) (from + (to - from)*animation.getAnimatedFraction());
                    element.setTextColor(Color.argb(alpha[0], rgb[0], rgb[1], rgb[2]));
                }
            });

            anim.start();
        }

        public Integer[] parseColor(int value) {
            Integer[] rgb = new Integer[3];

            String hexColor = String.format("%06X", (0xFFFFFF & value));

            int color = (int)Long.parseLong(hexColor, 16);
            rgb[0] = (color >> 16) & 0xFF;
            rgb[1] = (color >> 8) & 0xFF;
            rgb[2] = (color >> 0) & 0xFF;

            return rgb;
        }
    }
}