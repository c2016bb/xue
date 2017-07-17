package com.taoxue.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.taoxue.R;

/**
 * 自定义的View，实现ListView A~Z快速索引效果
 *
 * @author Folyd
 */
public class SlideBar extends View {
    private Paint paint = new Paint();
    private OnTouchLetterChangeListenner listenner;
    // 是否画出背景
    private boolean showBg = false;

    private float textSize;
    private int textColor;
    private int textColorSel;
    private int bgColorSel;
    private int bgColor;
    // 选中的项
    private int choose = -1;
    // 准备好的A~Z的字母数组
    public static String[] letters = {"#", "A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};

    // 构造方法
    public SlideBar(Context context) {
        super(context);
    }

    public SlideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlideBar);
            textSize = typedArray.getDimension(R.styleable.SlideBar_sb_textSize, textSize);
            textColor = typedArray.getColor(R.styleable.SlideBar_sb_textColor, Color.GRAY);
            textColorSel = typedArray.getColor(R.styleable.SlideBar_sb_textColorSel, getResources().getColor(R.color.colorPrimary));
            bgColor = typedArray.getColor(R.styleable.SlideBar_sb_bgColor, Color.TRANSPARENT);
            bgColorSel = typedArray.getColor(R.styleable.SlideBar_sb_bgColorSel, Color.TRANSPARENT);
            typedArray.recycle();
        }
        if (isInEditMode()) {
            bgColor = Color.parseColor("#EEEEEE");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取宽和高
        int width = getWidth();
        int height = getHeight() - 30;
        // 每个字母的高度
        int singleHeight = height / letters.length;
        if (showBg) {
            // 画出背景
            canvas.drawColor(bgColorSel);
        } else {
            canvas.drawColor(bgColor);
        }
        // 画字母
        for (int i = 0; i < letters.length; i++) {
            paint.setColor(textColor);
            // 设置字体格式
            paint.setAntiAlias(true);
            paint.setTextSize(textSize);
            // 如果这一项被选中，则换一种颜色画
            if (i == choose) {
                paint.setColor(textColorSel);
                paint.setFakeBoldText(true);
            }
            // 要画的字母的x,y坐标
            float posX = width / 2 - paint.measureText(letters[i]) / 2;
            float posY = i * singleHeight + singleHeight;
            // 画出字母
            canvas.drawText(letters[i], posX, posY, paint);
            // 重新设置画笔
            paint.reset();
        }
    }

    /**
     * 处理SlideBar的状态
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final float y = event.getY();
        // 算出点击的字母的索引
        final int index = (int) (y / getHeight() * letters.length);
        // 保存上次点击的字母的索引到oldChoose
        final int oldChoose = choose;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                showBg = true;
                if (oldChoose != index && listenner != null && index > 0
                        && index < letters.length) {
                    choose = index;
                    listenner.onTouchLetterChange(event, letters[index]);
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (oldChoose != index && listenner != null && index > 0
                        && index < letters.length) {
                    choose = index;
                    listenner.onTouchLetterChange(event, letters[index]);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            default:
                showBg = false;
                choose = -1;
                if (listenner != null && index > 0 && index < letters.length)
                    listenner.onTouchLetterChange(event, letters[index]);
                invalidate();
                break;
        }
        return true;
    }

    /**
     * 回调方法，注册监听器
     *
     * @param listenner
     */
    public void setOnTouchLetterChangeListenner(
            OnTouchLetterChangeListenner listenner) {
        this.listenner = listenner;
    }

    /**
     * SlideBar 的监听器接口
     *
     * @author Folyd
     */
    public interface OnTouchLetterChangeListenner {

        void onTouchLetterChange(MotionEvent event, String s);
    }

}