package a733.shareapk.com.widget;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import a733.shareapk.com.R;

/**
 * Created by Administrator on 2017/8/22.
 */

public class InputEditTextView extends RelativeLayout {
    private EditText editText;
    private TextView textView;
    private LayoutParams layoutParams;
    private List<TextView> tvs = new ArrayList<>();
    private int num = 3;

    public InputEditTextView(Context context) {
        this(context, null);
    }

    public InputEditTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        editText = new EditText(context);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setBackgroundResource(R.drawable.edit_bg);
        editText.setTextSize(0);
        editText.setCursorVisible(false);
        this.addView(editText);
        layoutParams = (LayoutParams) editText.getLayoutParams();
        layoutParams.height = dpTopx(50) + (getPaddingBottom() == 0 ? dpTopx(5) : getPaddingBottom()) + (getPaddingTop() == 0 ? dpTopx(5) : getPaddingTop());
        layoutParams.width = 4 * dpTopx(50) + (getPaddingRight() == 0 ? dpTopx(5) : getPaddingRight()) + (getPaddingLeft() == 0 ? dpTopx(5) : getPaddingLeft()) + 3 * dpTopx(18);

        for (int i = 0; i < 4; i++) {
            textView = new TextView(context);
            textView.setBackgroundResource(R.drawable.text_bg);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(20);
            this.addView(textView);
            layoutParams = (LayoutParams) textView.getLayoutParams();
            layoutParams.height = dpTopx(50);
            layoutParams.width = dpTopx(50);
            //  layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            textView.setLayoutParams(layoutParams);
            textView.setX((getPaddingLeft() == 0 ? dpTopx(5) : getPaddingLeft()) + i * dpTopx(50) + i * dpTopx(18));
            textView.setY((getPaddingTop() == 0 ? dpTopx(5) : getPaddingTop()));
            tvs.add(textView);
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Log.e("cnm", s + "======" + start + "=====" + before + "=====" + count);
                if (before == 0) {
                    if (start <= 3) {
                        textView = (TextView) getChildAt(start + 1);
                        textView.setText(s.charAt(start) + "");
                    } else {
                        textView = (TextView) getChildAt(num + 1);
                        textView.setText(s.charAt(start) + "");
                        if (num != 3) {
                            num++;
                        }
                    }

                } else {
                    if (start > 3) {
                        textView = (TextView) getChildAt(num + 1);
                        textView.setText("");
                        if (num != 0) {
                            num--;
                        }
                    } else {
                        textView = (TextView) getChildAt(start + 1);
                        textView.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private int dpTopx(int dp) {
        float density = this.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }
}
