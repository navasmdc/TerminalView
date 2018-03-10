package com.gc.terminalview;

import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.gc.terminalviewdemo.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editText;
    TerminalView terminalView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edit_text);
        editText.setTypeface(Typeface.createFromAsset(getAssets(), "font.ttf"));
        terminalView =(TerminalView) findViewById(R.id.terminal_view);
        findViewById(R.id.btn_enter).setOnClickListener(this);
    }

    LineText.OnLineWritedListener onLineWritedListener = new LineText.OnLineWritedListener() {
        @Override
        public void onLineWrited() {
            terminalView.showProgress();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    terminalView.hideProgress();
                    terminalView.addError("The password is wrong", onErrorWritedListener);
                }
            }, 2000);
        }
    };

    LineText.OnLineWritedListener onErrorWritedListener = new LineText.OnLineWritedListener() {
        @Override
        public void onLineWrited() {
            editText.setEnabled(true);
        }
    };

    @Override
    public void onClick(View view) {
        String text = editText.getText().toString();
        if (!text.trim().isEmpty()) {
            editText.setEnabled(false);
            editText.setText("");
            terminalView.addLine(text, onLineWritedListener);
        }
    }
}
