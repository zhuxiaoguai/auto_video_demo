package com.a733.video;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.SeekBar;
import android.widget.TextView;

public class MyWallPaper extends AppCompatActivity {
    private CardView cardView;
    private Animator animator;
    private SeekBar progressBar;
    private TextView square;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wall_paper);
//        VideoWallpaper videoWallpaper = new VideoWallpaper();
//        videoWallpaper.setToWallPaper(this, new File(Environment.getExternalStorageDirectory() + File.separator + "san.mp4").getPath());

        cardView = (CardView) findViewById(R.id.test);
        square = (TextView) findViewById(R.id.square);
//        cardView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                v.removeOnLayoutChangeListener(this);
                //进行自己的动画操作
//                if (square.getVisibility() == View.INVISIBLE) {
//                    square.setVisibility(View.VISIBLE);
//                    animator = ViewAnimationUtils.createCircularReveal(cardView, 50, 50, 0, dip2px(MyWallPaper.this, 400));
//
//                } else {
//                    square.setVisibility(View.INVISIBLE);
//                    animator = ViewAnimationUtils.createCircularReveal(cardView, 50, 50, dip2px(MyWallPaper.this, 400), 0);
//                }
//                animator.setDuration(1000);
            }
 //       });

//        progressBar = (SeekBar) findViewById(R.id.control);
//        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                textView.setRadius(progress);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
        // Log.e("----->", textView.getWidth() + "------" + textView.getHeight());


//    }

    public void start(View view) {
        if (square.getVisibility() == View.INVISIBLE) {
            animator = ViewAnimationUtils.createCircularReveal(cardView, 50, 50, 0, dip2px(MyWallPaper.this, 400));
            square.setVisibility(View.VISIBLE);

        } else {
            cardView.setCardBackgroundColor(Color.RED);
            animator = ViewAnimationUtils.createCircularReveal(cardView, 50, 50, dip2px(MyWallPaper.this, 400), 0);
            square.setVisibility(View.INVISIBLE);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    cardView.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        animator.setDuration(1000);
        animator.start();
    }

    public static int dip2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5);
    }

    public void reset(View view) {
        // cardView.setVisibility(View.VISIBLE);

        if (cardView.getVisibility() == View.INVISIBLE) {
            cardView.setVisibility(View.VISIBLE);
            animator = ViewAnimationUtils.createCircularReveal(cardView, 50, 50, 0, dip2px(MyWallPaper.this, 500));
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    cardView.setCardBackgroundColor(Color.RED);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        } else {

            animator = ViewAnimationUtils.createCircularReveal(cardView, 50, 50, dip2px(MyWallPaper.this, 500), 0);
            // square.setVisibility(View.INVISIBLE);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    cardView.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        animator.setDuration(500);
        animator.start();
    }

}
