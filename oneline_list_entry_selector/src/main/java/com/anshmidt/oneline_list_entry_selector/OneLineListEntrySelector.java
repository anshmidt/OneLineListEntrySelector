package com.anshmidt.oneline_list_entry_selector;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by Ilya Anshmidt on 30.09.2018.
 */

public class OneLineListEntrySelector extends RelativeLayout {

    private View rootView;
    private TextView valueView;
    private ImageButton upButton, downButton;
    private LinearLayout layout;

    private ArrayList<String> list;
    private final int DEFAULT_INITIAL_ENTRY_NUMBER = 0;
    private int initialEntryNumber;
    private int currentEntryNumber;
    private int maxStringWidthOfAllEntries;
    private float textSizeSp;

    private int defaultBackgroundColor;
    private int defaultTextColor;
    final private int defaultTextSizeSp = 13;
    private int DEFAULT_VALUE_VIEW_HORIZONTAL_MARGIN = 0;

    private Context context;
    private AttributeSet attrs;
    private int styleAttr;

    private static final String LOG_TAG = OneLineListEntrySelector.class.getSimpleName();


    public OneLineListEntrySelector(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public OneLineListEntrySelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        init();
    }

    public OneLineListEntrySelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.attrs = attrs;
        this.styleAttr = defStyleAttr;
        init();
    }

    private void init() {
        rootView = inflate(context, R.layout.oneline_list_entry_selector, this);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OneLineListEntrySelector, styleAttr, 0);
        defaultBackgroundColor = ContextCompat.getColor(context, R.color.colorBackgroundDefault);
        defaultTextColor = ContextCompat.getColor(context, R.color.colorTextDefault);

        textSizeSp = pxToSp(a.getDimension(R.styleable.OneLineListEntrySelector_textSize, defaultTextSizeSp));
        int backgroundColor = a.getColor(R.styleable.OneLineListEntrySelector_backgroundColor, defaultBackgroundColor);
        int textColor = a.getColor(R.styleable.OneLineListEntrySelector_textColor, defaultTextColor);
        a.recycle();

        valueView = (TextView) rootView.findViewById(R.id.value_view);
        downButton = (ImageButton) rootView.findViewById(R.id.down_button);
        upButton = (ImageButton) rootView.findViewById(R.id.up_button);
        layout = (LinearLayout) findViewById(R.id.layout);

        layout.setBackgroundColor(backgroundColor);
        valueView.setTextColor(textColor);
        upButton.setColorFilter(textColor);
        downButton.setColorFilter(textColor);
        valueView.setTextSize(textSizeSp);

//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) upButton.getLayoutParams();
//        params.width = 300;
//        params.height = 300;
//        upButton.setLayoutParams(params);
//        downButton.setLayoutParams(params);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) valueView.getLayoutParams();
        params.setMargins(DEFAULT_VALUE_VIEW_HORIZONTAL_MARGIN, 0, DEFAULT_VALUE_VIEW_HORIZONTAL_MARGIN, 0);
        valueView.setLayoutParams(params);

        downButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentEntryNumber(currentEntryNumber - 1);
                setValue(currentEntryNumber);
            }
        });

        upButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentEntryNumber(currentEntryNumber + 1);
                setValue(currentEntryNumber);
            }
        });
    }

    private void setCurrentEntryNumber(int newEntryNumber) {
        if (newEntryNumber < 0) {
            return;
        }
        if (newEntryNumber >= list.size()) {
            return;
        }
        currentEntryNumber = newEntryNumber;
    }

    public void setInitialEntryNumber(int initialEntryNumber) {
        this.initialEntryNumber = initialEntryNumber;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
        setInitialEntryNumber(DEFAULT_INITIAL_ENTRY_NUMBER);
        setValue(initialEntryNumber);
        setValueViewWidth(((int) (getMaxStringWidthOfAllEntriesPx() + 2*spToPx(DEFAULT_VALUE_VIEW_HORIZONTAL_MARGIN)))+1);
    }

    public void setValue(int index) {
        String valueToSet = list.get(index);
        valueView.setText(valueToSet);
    }

    public String getValue() {
        return valueView.getText().toString();
    }

    private int getMaxStringWidthOfAllEntriesPx() {
        Paint paint = new Paint();
        float textSizePx = spToPx(textSizeSp);
        paint.setTypeface(Typeface.DEFAULT);// your preference here
        paint.setTextSize(textSizePx);// have this the same as your text size
        int stringWidth;
        int maxStringWidth = 0;
        for (String entry : list) {
            stringWidth = (int) Math.ceil(paint.measureText(entry));
            if (stringWidth > maxStringWidth) {
                maxStringWidth = stringWidth;
            }
        }
        return maxStringWidth;
    }

    private void setValueViewWidth(int newWidthPx) {
        valueView.getLayoutParams().width = newWidthPx;
    }

    private float spToPx(float sp) {
        return sp * getResources().getDisplayMetrics().scaledDensity;
    }

    private float pxToSp(float px) {
        return px / getResources().getDisplayMetrics().scaledDensity;
    }
 }
