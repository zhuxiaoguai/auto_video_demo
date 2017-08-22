package a733.shareapk.com.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Selection;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.EditText;
import android.widget.RelativeLayout;

import a733.shareapk.com.R;

/**
 * Created by Administrator on 2017/8/21.
 */

public class NumEditTextView extends EditText {
    private EditText editText;
    private int length;
    private int width;
    private int height;
    private RectF rectF;
    private Paint mPaint;
    private Context context;
    private InputMethodManager imm;

    public NumEditTextView(final Context context) {
        this(context, null);

    }

    public NumEditTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NumEditTextView, defStyleAttr, 0);
        length = ta.getInteger(R.styleable.NumEditTextView_max_length, 4);
        if (length < 4) {
            length = 4;
        }
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w_mode = MeasureSpec.getMode(widthMeasureSpec);

        int h_mode = MeasureSpec.getMode(heightMeasureSpec);

        if (w_mode == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            width = length * dpTopx(50) + (getPaddingRight() == 0 ? dpTopx(5) : getPaddingRight()) + (getPaddingLeft() == 0 ? dpTopx(5) : getPaddingLeft()) + (length - 1) * dpTopx(18);
        }

        if (h_mode == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            height = dpTopx(50) + (getPaddingBottom() == 0 ? dpTopx(5) : getPaddingBottom()) + (getPaddingTop() == 0 ? dpTopx(5) : getPaddingTop());
        }

        setMeasuredDimension(width, height);

        rectF = new RectF();


        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < length; i++) {
            rectF.left = (getPaddingLeft() == 0 ? dpTopx(5) : getPaddingLeft()) + i * dpTopx(50) + i * dpTopx(18);
            rectF.top = getPaddingTop() == 0 ? dpTopx(5) : getPaddingTop();
            rectF.right = rectF.left + dpTopx(50);
            rectF.bottom = rectF.top + dpTopx(50);
            canvas.drawRect(rectF, mPaint);
        }

        rectF.left = 0;
        rectF.top = 0;
        rectF.right = width;
        rectF.bottom = height;
        canvas.drawRect(rectF, mPaint);
    }

    private int dpTopx(int dp) {
        float density = this.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                Log.e("cnm", "----------");
                if (imm == null)
                    imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
        }
        return true;
    }


}
