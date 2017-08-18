package com.a733.video;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


/**
 * Created by Administrator on 2017/8/16.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHoder> {
    private final Uri uri;
    private Context context;
    private MediaPlayer mediaPlayer;
    private int d_position = 999;
    private boolean isPlaying = false;
    private ImageView imageView;
    private TextureView textureView;
    private VideoActivity.MyBroadcast broadcast;

    public MyAdapter(Context context) {
        this.context = context;
        uri = Uri.parse(Environment.getExternalStorageDirectory() + File.separator + "san.mp4");
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }


    public void setD_position(int d_position) {
        this.d_position = d_position;
    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_item, null);
        MyHoder myHoder = new MyHoder(view);
        return myHoder;
    }


    @Override
    public void onBindViewHolder(final MyHoder holder, int position) {
        if (d_position == position) {
            textureView = new TextureView(context);
            holder.frameLayout.addView(textureView);
            TextView textView = new TextView(context);
            textView.setText("+");
            textView.setTextSize(28);
            textView.setTextColor(Color.WHITE);
            textView.setGravity(Gravity.CENTER);
            holder.frameLayout.addView(textView);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.width = 180;
            layoutParams.height = 180;
            layoutParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
            layoutParams.rightMargin = 20;
//            layoutParams.bottomMargin = 20;
            textView.setLayoutParams(layoutParams);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mediaPlayer != null) {
                        mediaPlayer.release();
                        broadcast = VideoActivity.broadcast;
                        IntentFilter intentFilter = new IntentFilter("com.a733.video.change_view");
                        context.registerReceiver(broadcast, intentFilter);
                        Intent intent = new Intent("com.a733.video.change_view");
                        context.sendBroadcast(intent);

                        holder.frameLayout.removeAllViews();
                        isPlaying = false;
                        imageView = new ImageView(context);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        holder.frameLayout.addView(imageView);

                        Glide.with(context)
                                .load(Environment.getExternalStorageDirectory().getPath() + File.separator + "san.png")
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imageView);
                    }
                }
            });
            textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
                @Override
                public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                    Surface sf = new Surface(surface);
                    try {
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(context, uri);
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.setSurface(sf);
                    try {
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        isPlaying = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

                }

                @Override
                public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                    mediaPlayer.release();
                    return true;
                }

                @Override
                public void onSurfaceTextureUpdated(SurfaceTexture surface) {

                }
            });
//            ijkVideoView = new IjkVideoView(context);
//            ijkVideoView.setVideoPath(uri.getPath());
//            ijkVideoView.toggleAspectRatio();
//            holder.frameLayout.addView(ijkVideoView);
//
//            ijkVideoView.setMediaController(new IMediaController() {
//                @Override
//                public void hide() {
//
//                }
//
//                @Override
//                public boolean isShowing() {
//                    return false;
//                }
//
//                @Override
//                public void setAnchorView(View view) {
//
//                }
//
//                @Override
//                public void setEnabled(boolean enabled) {
//
//                }
//
//                @Override
//                public void setMediaPlayer(MediaController.MediaPlayerControl player) {
//
//                    player.start();
//                }
//
//                @Override
//                public void show(int timeout) {
//
//                }
//
//                @Override
//                public void show() {
//
//                }
//
//                @Override
//                public void showOnce(View view) {
//
//                }
//            });
            d_position = 999;
        } else {
            if (mediaPlayer != null) {

                mediaPlayer.release();
                Log.e("--------->", "------->");
                //d_position = 999;
            } else {
                Log.e("--------->", "=======");
            }
            // }
            holder.frameLayout.removeAllViews();
            isPlaying = false;
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            holder.frameLayout.addView(imageView);

            Glide.with(context)
                    .load(Environment.getExternalStorageDirectory().getPath() + File.separator + "san.png")
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }


    @Override
    public int getItemCount() {
        return 40;
    }

    public class MyHoder extends RecyclerView.ViewHolder {
        // public ImageView imageView;
        private FrameLayout frameLayout;
        // private IjkVideoView ijkVideoView;

        public MyHoder(View itemView) {
            super(itemView);
            //  imageView = (ImageView) itemView.findViewById(R.id.video_image);
            frameLayout = (FrameLayout) itemView.findViewById(R.id.container);
            //  ijkVideoView = (IjkVideoView) itemView.findViewById(R.id.video_item);
        }
    }

    private Bitmap changeSize(Bitmap bitmap, View view) {
        int d_width = view.getWidth();
        int d_height = view.getHeight();

        int s_width = bitmap.getWidth();
        int s_height = bitmap.getHeight();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] datas = baos.toByteArray();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;
        if (s_width > d_width | s_height > s_width) {
            int w_size = Math.round(s_width / d_width);
            int h_size = Math.round(s_height / d_height);
            inSampleSize = w_size < h_size ? w_size : h_size;
        }
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeByteArray(datas, 0, baos.size(), options);
    }
}
