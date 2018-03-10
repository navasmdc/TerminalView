package com.gc.terminalview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Navas on 25/5/16.
 */
public class TerminalButton extends Button {

    public TerminalButton(Context context) {
        super(context);
        initializeView();
    }

    public TerminalButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }

    public void initializeView(){
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font.ttf"));
        setTextColor(getResources().getColorStateList(R.color.foreground_button));
        setBackgroundResource(R.drawable.background_button);
    }
}
