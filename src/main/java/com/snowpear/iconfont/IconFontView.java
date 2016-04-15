package com.snowpear.iconfont;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Created by wangjinpeng on 16/2/1.
 * IconFontView 为了IconFont设置的View，必须设定宽高为固定值，
 * xml使用时必须引用 xmlns:iconfont_attr="http://schemas.android.com/apk/res-auto"
 * 必须引用
 * iconfont_attr:font -- 字体内容
 * iconfont_attr:fontAsset  -- 字体引用的ttf文件路径，目前只能放在assets资源目录下
 * iconfont_attr:fontColor  -- 字体颜色，不传则为黑色
 */
public class IconFontView extends View {

    public IconFontView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public IconFontView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public IconFontView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public IconFontView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }


    private TextPaint mTextPaint;
    private String mIconfontText;
    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        mIconfontText = "";
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.density = getResources().getDisplayMetrics().density;
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        if(attrs != null) {
            ColorStateList textColor = null;
            String fontAsset = null;

            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.iconfont_attr);

            int n = a.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.iconfont_attr_font) {
                    mIconfontText = a.getText(attr).toString();
                } else if (attr == R.styleable.iconfont_attr_fontColor) {
                    textColor = a.getColorStateList(attr);
                } else if (attr == R.styleable.iconfont_attr_fontAsset) {
                    fontAsset = a.getString(attr);
                }
            }
            a.recycle();

            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), fontAsset);
            if (typeface != null) {
                mTextPaint.setTypeface(typeface);
            }
            if (textColor != null) {
                mTextPaint.setColor(textColor.getDefaultColor());
            } else {
                mTextPaint.setColor(Color.BLACK);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int padLeft = getPaddingLeft();
        int padRight = getPaddingLeft();
        int padTop = getPaddingLeft();
        int padBottom = getPaddingLeft();

        int padingHor = padLeft > padRight ? padLeft : padRight;
        int padingVer = padTop > padBottom ? padTop : padBottom;

        int fontWidth = width - padingHor * 2;
        int fontHeight = height - padingVer * 2;
        //始终画在中间
        int fontSize = fontWidth > fontHeight ? fontHeight : fontWidth;
        mTextPaint.setTextSize(fontSize);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        int x = width / 2;
        int y = (int) (height / 2 + Math.abs(fontMetrics.ascent) / 2 - fontMetrics.leading * 3 / 4);
        canvas.drawText(mIconfontText, x, y, mTextPaint);
    }

    int width = 0;
    int height = 0;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
    }

    public void setFontAsset(String assetPath){
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), assetPath);
        if(typeface != null){
            mTextPaint.setTypeface(typeface);
        }
        invalidate();
    }

    public void setFontColor(int color){
        mTextPaint.setColor(color);
        invalidate();
    }

    public void setFont(String font){
        mIconfontText = StringEscapeUtils.unescapeXml(font);
        invalidate();
    }

    public void setFont(String font, String assetPath, int color){
        mIconfontText = StringEscapeUtils.unescapeXml(font);
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), assetPath);
        if(typeface != null){
            mTextPaint.setTypeface(typeface);
        }
        mTextPaint.setColor(color);
        invalidate();
    }
}
