package com.gc.terminalview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.LinkedList;

/**
 * Created by Navas on 21/5/16.
 */
public class TerminalView extends ScrollView implements LineText.OnLineWritedListener {

    LinearLayout container;
    LinkedList<String> linesToWrite = new LinkedList<>();
    boolean animating = false;
    boolean showProgress = false;
    InitialLineText initialLineText;
    ProgressLineText progressLineText;
    float textSize = -1;

    LineText.OnLineWritedListener lineListener;

    public TerminalView(Context context) {
        super(context);
        initialConfiguration();
    }

    public TerminalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        int[] array = {android.R.attr.textSize};
        TypedArray values = getContext().obtainStyledAttributes(attrs, array);
        textSize = values.getDimension(0, -1);
        values.recycle();
        initialConfiguration();
    }

    private void initialConfiguration(){
        container = new LinearLayout(getContext());
        container.setOrientation(LinearLayout.VERTICAL);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        container.setLayoutParams(lp);
        addView(container);
        setFillViewport(true);
        setBackgroundColor(getContext().getResources().getColor(R.color.black));
        showInitialLine();
    }

    public void addLine(String line){
        hideInitialLine();
        hideProgress();
        if(animating) linesToWrite.addLast(line);
        else{
            hideProgress();
            animating = true;
            container.addView(new LineText(getContext(), line, this,textSize));
        }
    }

    public void addLine(String line, LineText.OnLineWritedListener listener){
        this.lineListener = listener;
        addLine(line);
    }

    public void addError(String error, LineText.OnLineWritedListener listener){
        this.lineListener = listener;
        addError(error);
    }

    public void addError(String error){
        hideInitialLine();
        hideProgress();
        LineText lineText = new LineText(getContext(), error, this,textSize);
        lineText.setTextColor(getContext().getResources().getColor(R.color.red));
        container.addView(lineText);
    }

    public void showProgress(){
        if(progressLineText == null) {
            if(animating) showProgress = true;
            else {
                hideInitialLine();
                showProgress = false;
                progressLineText = new ProgressLineText(getContext(), textSize);
                container.addView(progressLineText);
            }
        }else showProgress = false;
    }

    public void hideProgress(){
        if(progressLineText != null){
            progressLineText.stop();
            container.removeView(progressLineText);
            progressLineText = null;
        }
    }

    public void showInitialLine(){
        initialLineText = new InitialLineText(getContext(),textSize);
        container.addView(initialLineText);
    }

    public void hideInitialLine(){
        if(initialLineText != null){
            initialLineText.stop();
            container.removeView(initialLineText);
        }
    }

    public void clearConsole(){
        hideProgress();
        container.removeAllViews();
        showInitialLine();
    }





    @Override
    public void onLineWrited() {
        if(linesToWrite.size() == 0){
            animating = false;
            if(showProgress) showProgress();
            else {
                showInitialLine();
                if(lineListener != null){
                    lineListener.onLineWrited();
                    lineListener = null;
                }
            }
        } else container.addView(new LineText(getContext(), linesToWrite.removeFirst(), this,textSize));
    }
}
