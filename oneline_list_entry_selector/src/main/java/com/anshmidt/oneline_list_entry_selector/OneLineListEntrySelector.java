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
    private TextView entryView;
    private ImageButton downButton;
    private ImageButton upButton;
    private LinearLayout layout;

    private ArrayList list;
    private final int DEFAULT_INITIAL_ENTRY_NUMBER = 0;
    private int initialEntryNumber;
    private int currentEntryNumber;
    private float textSizeSp;
    private final int DEFAULT_TEXT_SIZE_SP = 13;
    private float buttonSizeDp;
    private int buttonPaddingDp;
    private final int DEFAULT_BUTTON_PADDING = 20;
    private final float DEFAULT_BUTTONS_SIZE_COEFF = 2.5f;

    private int defaultBackgroundColor;
    private int backgroundColor;
    private int defaultTextColor;
    private int textColor;
    private int buttonColor;
    private final float BUTTON_OPACITY_WHEN_DISABLED = 0.3f;
    private final float BUTTON_OPACITY_WHEN_ENABLED = 1.0f;

    private final int DEFAULT_ENTRY_VIEW_HORIZONTAL_MARGIN = 5;

    private boolean dynamicWidthEnabled;
    private final boolean DEFAULT_DYNAMIC_WIDTH_ENABLED = false;


    private Context context;
    private AttributeSet attrs;
    private int styleAttr;

    private OnValueChangeListener onValueChangeListener;

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

    public void setInitialEntryNumber(int initialEntryNumber) {
        this.initialEntryNumber = initialEntryNumber;
        setEntry(initialEntryNumber);
    }

    public void setList(ArrayList list) {
        this.list = list;
        setEntry(initialEntryNumber);
        if (!dynamicWidthEnabled) {
            setEntryViewWidth(((int) (getMaxStringWidthOfAllEntriesPx()
                    + 2 * spToPx(DEFAULT_ENTRY_VIEW_HORIZONTAL_MARGIN))) + 1);
        }
    }

    public void setEntry(int newEntryNumber) {
        String oldValue = getEntry();
        String valueToSet = list.get(newEntryNumber).toString();
        entryView.setText(valueToSet);
        currentEntryNumber = newEntryNumber;
        setButtonsAppearance(newEntryNumber);
        if (onValueChangeListener != null) {
            onValueChangeListener.onValueChange(this, oldValue, valueToSet);
        }
    }

    public String getEntry() {
        return entryView.getText().toString();
    }

    public void setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
        this.onValueChangeListener = onValueChangeListener;
    }

    public interface OnValueChangeListener {
        void onValueChange(OneLineListEntrySelector view, String oldValue, String newValue);
    }

    private void init() {
        rootView = inflate(context, R.layout.oneline_list_entry_selector, this);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OneLineListEntrySelector, styleAttr, 0);
        defaultBackgroundColor = ContextCompat.getColor(context, R.color.colorBackgroundDefault);
        defaultTextColor = ContextCompat.getColor(context, R.color.colorTextDefault);

        textSizeSp = pxToSp(a.getDimension(R.styleable.OneLineListEntrySelector_textSize, DEFAULT_TEXT_SIZE_SP));
        float defaultButtonSizeSp = spToPx(textSizeSp) * DEFAULT_BUTTONS_SIZE_COEFF;
        buttonSizeDp = pxToSp(a.getDimension(R.styleable.OneLineListEntrySelector_buttonSize, defaultButtonSizeSp));
        buttonPaddingDp = (int) pxToSp(a.getDimension(R.styleable.OneLineListEntrySelector_buttonPadding, spToPx(DEFAULT_BUTTON_PADDING)));

        backgroundColor = a.getColor(R.styleable.OneLineListEntrySelector_backgroundColor, defaultBackgroundColor);
        textColor = a.getColor(R.styleable.OneLineListEntrySelector_textColor, defaultTextColor);
        buttonColor = a.getColor(R.styleable.OneLineListEntrySelector_buttonColor, textColor);

        dynamicWidthEnabled = a.getBoolean(R.styleable.OneLineListEntrySelector_dynamicWidthEnabled, DEFAULT_DYNAMIC_WIDTH_ENABLED);
        a.recycle();

        entryView = rootView.findViewById(R.id.entry_view);
        downButton = rootView.findViewById(R.id.down_button);
        upButton = rootView.findViewById(R.id.up_button);
        layout = findViewById(R.id.layout);


        layout.setBackgroundColor(backgroundColor);

        entryView.setTextColor(textColor);
        upButton.setColorFilter(buttonColor);
        downButton.setColorFilter(buttonColor);

        entryView.setTextSize(textSizeSp);


        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) entryView.getLayoutParams();
        params.setMargins(DEFAULT_ENTRY_VIEW_HORIZONTAL_MARGIN, 0, DEFAULT_ENTRY_VIEW_HORIZONTAL_MARGIN, 0);
        entryView.setLayoutParams(params);

        setButtonSize(upButton, buttonSizeDp);
        setButtonSize(downButton, buttonSizeDp);

        upButton.setPadding(buttonPaddingDp, buttonPaddingDp, buttonPaddingDp, buttonPaddingDp);
        downButton.setPadding(buttonPaddingDp, buttonPaddingDp, buttonPaddingDp, buttonPaddingDp);

        currentEntryNumber = DEFAULT_INITIAL_ENTRY_NUMBER;

        downButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentEntryNumber(currentEntryNumber - 1);
                setEntry(currentEntryNumber);
            }
        });

        upButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentEntryNumber(currentEntryNumber + 1);
                setEntry(currentEntryNumber);
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

    private int getMaxStringWidthOfAllEntriesPx() {
        Paint paint = new Paint();
        float textSizePx = spToPx(textSizeSp);
        paint.setTypeface(Typeface.DEFAULT);// your preference here
        paint.setTextSize(textSizePx);// have this the same as your text size
        int stringWidth;
        int maxStringWidth = 0;
        for (Object entry : list) {
            stringWidth = (int) Math.ceil(paint.measureText(entry.toString()));
            if (stringWidth > maxStringWidth) {
                maxStringWidth = stringWidth;
            }
        }
        return maxStringWidth;
    }

    private void setEntryViewWidth(int newWidthPx) {
        entryView.getLayoutParams().width = newWidthPx;
    }

    private void setButtonSize(ImageButton button, float buttonSizeSp) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) button.getLayoutParams();
        int buttonSizePx = (int) spToPx(buttonSizeSp);
        params.height = buttonSizePx;
        params.width = buttonSizePx;
        button.setLayoutParams(params);
    }


    private void setButtonsAppearance(int currentEntryNumber) {
        if (currentEntryNumber == 0) {
            downButton.setAlpha(BUTTON_OPACITY_WHEN_DISABLED);
            upButton.setAlpha(BUTTON_OPACITY_WHEN_ENABLED);
        } else if (currentEntryNumber == list.size() - 1) {
            downButton.setAlpha(BUTTON_OPACITY_WHEN_ENABLED);
            upButton.setAlpha(BUTTON_OPACITY_WHEN_DISABLED);
        } else {
            downButton.setAlpha(BUTTON_OPACITY_WHEN_ENABLED);
            upButton.setAlpha(BUTTON_OPACITY_WHEN_ENABLED);
        }
    }


    private float spToPx(float sp) {
        return sp * getResources().getDisplayMetrics().scaledDensity;
    }

    private float pxToSp(float px) {
        return px / getResources().getDisplayMetrics().scaledDensity;
    }
 }
