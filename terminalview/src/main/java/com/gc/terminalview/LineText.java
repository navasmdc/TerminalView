package com.gc.terminalview;

import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.widget.TextView;

/**
 * Created by Navas on 21/5/16.
 */
class LineText extends TextView{

    String text;
    MediaPlayer mp;
    int keyPosition = 0;
    boolean animating = true;
    String fullBlock = new String(Character.toChars(0x2588));
    OnLineWritedListener onLineWritedListener;

    public LineText(Context context, String text,OnLineWritedListener onLineWritedListener, float textSize) {
        super(context);
        if(textSize != -1) setTextSize(textSize);
        this.text = "> "+text;
        initView();
        this.onLineWritedListener = onLineWritedListener;
    }

    private void initView(){
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font.ttf"));
        setTextColor(getContext().getResources().getColor(R.color.green));
        post(postRunnable);
    }

    Runnable postRunnable = new Runnable() {
        @Override
        public void run() {
            new AnimationWrite().execute();
        }
    };

    private void playSound(){
        mp = MediaPlayer.create(getContext(), R.raw.keysound1);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(animating) {
                    LineText.this.mp = MediaPlayer.create(getContext(), R.raw.keysound1);
                    LineText.this.mp.setOnCompletionListener(this);
                    LineText.this.mp.start();
                }
            }
        });
        mp.start();
    }

    private class AnimationWrite extends AsyncTask<Void,Integer,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            for(int i = 0; i < text.length(); i++){
                try {
                    Thread.sleep(12);
                } catch (InterruptedException e) {}
                publishProgress(i);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if(values[0] == 0) playSound();
            setText(getText().toString() + text.toCharArray()[values[0]]);
            if(values[0] % 2 == 0)
                setText(getText().toString().replace(fullBlock,""));
            else
                setText(getText().toString()+fullBlock);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            animating = false;
            mp.stop();
            setText(getText().toString().replace(fullBlock,""));
            if(onLineWritedListener != null) onLineWritedListener.onLineWrited();
        }
    }


    public interface OnLineWritedListener{
        void onLineWrited();
    }


}
