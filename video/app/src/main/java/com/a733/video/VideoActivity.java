package com.a733.video;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

public class VideoActivity extends AppCompatActivity {
    private TextureView textureView;
    private MediaPlayer mediaPlayer;
    private Surface sf;
    private GestureDetector gestureDetector;
    private int videoX = 0;
    private int videoY = 0;
    private WindowManager windowManager;
    private int s_h = 0;
    private int s_w = 0;
    private MyRecyclerView recyclerView;
    private MyAdapter myAdapter;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public static MyBroadcast broadcast;
    private FrameLayout frameLayout;
    private LinearLayoutManager linearLayoutManager;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        broadcast = new MyBroadcast();

        preferences = this.getSharedPreferences("datas", Context.MODE_PRIVATE);
        editor = preferences.edit();
        frameLayout = (FrameLayout) findViewById(R.id.video_container);
        recyclerView = (MyRecyclerView) findViewById(R.id.rlv);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    //判断是当前layoutManager是否为LinearLayoutManager
                    // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                    if (layoutManager instanceof LinearLayoutManager) {
                        LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                        //获取第一个可见view的位置
                        int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                        //int lastItemPosition = linearManager.findLastVisibleItemPosition();
                        if (firstItemPosition > 3) {
                            if (!myAdapter.isPlaying()) {
                                if (preferences.getInt("player_num", 0) == (firstItemPosition + 1) || textureView != null) {
                                    return;
                                }
                                myAdapter.setD_position(firstItemPosition + 1);
                                myAdapter.notifyDataSetChanged();
                                editor.putInt("player_num", firstItemPosition + 1).commit();
                            }
                        }

                    }
                }


            }


        });


        windowManager = this.getWindowManager();
        s_h = windowManager.getDefaultDisplay().getHeight();
        s_w = windowManager.getDefaultDisplay().getWidth();


        uri = Uri.parse(Environment.getExternalStorageDirectory() + File.separator + "san.mp4");


        gestureDetector = new GestureDetector(this, simpleOnGestureListener);

        myAdapter = new MyAdapter(this);
        recyclerView.setAdapter(myAdapter);
    }

    private float mOffsetScX;
    private float moffsetScY;
    GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            int[] location = new int[2];
            frameLayout.getLocationOnScreen(location);
            videoX = location[0];
            videoY = location[1];
            if (e2.getX() < videoX || e2.getX() > (videoX + frameLayout.getWidth()) || e2.getY() < videoY || e2.getY() > (videoY + frameLayout.getHeight())) {

                return false;
            }
            if (videoX < 0 | (videoX + frameLayout.getWidth()) > s_w | videoY < getStatusBarHeight() | (videoY + frameLayout.getHeight()) > s_h) {
                if (mOffsetScX > 0) {
                    mOffsetScX = mOffsetScX - 1;
                } else {
                    mOffsetScX = mOffsetScX + 1;
                }

                if (moffsetScY > 0) {
                    moffsetScY = moffsetScY - 1;
                } else {
                    moffsetScY = moffsetScY + 1;
                }
                frameLayout.setTranslationX(mOffsetScX);
                frameLayout.setTranslationY(moffsetScY);
                return false;
            } else {
                mOffsetScX += -distanceX;
                moffsetScY += -distanceY;
                frameLayout.setTranslationX(mOffsetScX);
                frameLayout.setTranslationY(moffsetScY);
            }

            return true;
        }

    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public class MyBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            if ("com.a733.video.change_view".equals(intent.getAction())) {

                textureView = new TextureView(context);
                frameLayout.addView(textureView);

                final TextView textView = new TextView(context);
                textView.setTextColor(Color.WHITE);
                textView.setText("-");
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(30);
                frameLayout.addView(textView);

                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.width = (int) dpToPix(context, 40);
                layoutParams.height = (int) dpToPix(context, 40);
                layoutParams.gravity = Gravity.RIGHT;
                textView.setLayoutParams(layoutParams);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerView.setInter(true);
                        frameLayout.removeAllViews();
                        textureView = null;
                    }
                });

                textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
                    @Override
                    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                        sf = new Surface(surface);
                        try {
                            mediaPlayer = new MediaPlayer();
                            mediaPlayer.setSurface(sf);
                            mediaPlayer.setDataSource(context, uri);
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                            recyclerView.setInter(false);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

                    }

                    @Override
                    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                        return false;
                    }

                    @Override
                    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

                    }
                });

            }
        }
    }

    private double dpToPix(Context context, int dp) {
        float density = this.getResources().getDisplayMetrics().density;
        return (density * dp + 0.5);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.putInt("player_num", 0).commit();
        preferences = null;
        editor = null;
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}
