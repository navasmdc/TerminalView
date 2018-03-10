# TerminalView
Curious view that simulates a terminal 

### Use

In order to use this view you have to add this lines to your xml file

```

<com.gc.terminalview.TerminalView
        android:id="@+id/terminal_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:textSize="7dp"/>


```

> With `android:textSize` you can change the text size of the lines.

### Add line

Add a line to the TerminalView
> This function play a keyboard sound

![Add Line](images/add_line.gif)

```

terminalView.addLine(text);
// or with listener
terminalView.addLine(text,onLineWritedListener);


```

### Add error line

Add an error line to the TerminalView

> This function play a keyboard sound

![Add Line](images/add_error.gif)

```

terminalView.addError(error);
// or with listener
terminalView.addError(text,onLineWritedListener);


```

### Progress

Display a progress indicator

![Add Line](images/progress.gif)

```

// Display the progress indicator
terminalView.showProgress();
// Hide the progress indicator
terminalView.hideProgress();


```