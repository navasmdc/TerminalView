package com.gc.terminalview;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

/**
 * Created by Navas on 24/5/16.
 */
class InitialLineText extends TextView {

    String fullBlock = new String(Character.toChars(0x2588));
    boolean animating = true;
    boolean showFullBlock = true;

    public InitialLineText(Context context, float textSize) {
        super(context);
        if(textSize != -1) setTextSize(textSize);
        post(new Runnable() {
            @Override
            public void run() {
                setText("> ");
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
                if (showFullBlock) setText(getText().toString() + fullBlock);
                else setText(getText().toString().replace(fullBlock, ""));
            }catch (Exception ex){animating = false; }
            return false;
        }
    });

    Thread animationThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while(animating) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                showFullBlock = !showFullBlock;
                handler.sendEmptyMessage(0);
            }
        }
    });

    public void stop(){
        animating = false;
    }



}
