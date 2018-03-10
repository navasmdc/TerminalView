package com.gc.terminalview;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

/**
 * Created by Navas on 24/5/16.
 */
class ProgressLineText extends TextView {

    boolean animating = true;
    String[] progressCharacters = {"\\","|","/","-","\\","|","/","-"};
    int keyPosition = 0;

    public ProgressLineText(Context context, float textSize) {
        super(context);
        if(textSize != -1) setTextSize(textSize);
        post(new Runnable() {
            @Override
            public void run() {
                setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font.ttf"));
                setTextColor(getContext().getResources().getColor(R.color.green));
                animationThread.start();
            }
        });
    }


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            try {
                setText("> "+progressCharacters[keyPosition]);
            }catch (Exception ex){animating = false; }
            return false;
        }
    });

    Thread animationThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while(animating) {
                try {
                    Thread.sleep(200);
                    keyPosition++;
                    if(keyPosition == progressCharacters.length) keyPosition = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        }
    });

    public void stop(){
        animating = false;
    }


}
