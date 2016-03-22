package xzh.com.listviewhover.view.chartline;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import xzh.com.listviewhover.R;
import xzh.com.listviewhover.utils.ScreenUtils;
import xzh.com.listviewhover.utils.Utils;

/**
 * 折线图
 */
public class LineChartView extends View {
    private int originX;// 圆点x坐标
    private int originY;// 圆点y坐标
    private int offsetX;// 偏移量x
    private int minX;// 在移动时，第一个点允许最小的x坐标
    private int maxX;// 在移动时，第一个点允许允许最大的x坐标
    private int lineColorOnAxis;// xy坐标轴颜色
    private int lineSizeOnAxis;// xy坐标轴大小
    private int textColorOnAxis;// xy坐标轴文字颜色
    private int textSizeOnAxis;// xy坐标轴文字大小
    private int linColorOnCoord;// 折线的颜色
    private int lineSizeOnCoord;// 折线的大小
    private int firstInterval;// 第一个间隔
    private int intervalY;// y轴坐标间的间隔
    private int interval;// 坐标间的间隔
    private int bgColor;// 背景颜色

    private float startX;// 第一次触摸的x坐标

    private int width;// 控件宽度
    private int height;// 控件高度

    private int minHeight;// 控件最小高度

    private int textHeightOnAxis;// 坐标轴文字高度
    private int textWidthOnAxis;// 坐标轴文字宽度

    private List<Coord> coords;// 坐标点集

    private Paint paintAxis;// 坐标轴上的点
    private Paint paintPointOnCoord;// 坐标上的点
    private Paint paintLineOnCoord;// 坐标上的线
    private Paint paintClear;// 黑板擦

    public LineChartView(Context context) {
        this(context, null);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 获取属性
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.LineChartView);
        lineColorOnAxis = ta.getColor(R.styleable.LineChartView_lineColorOnAxis, Color.parseColor("#7A7E85"));
        lineSizeOnAxis = (int) ta.getDimension(R.styleable.LineChartView_lineSizeOnAxis, Utils.dp2px(getContext(), 3));
        textColorOnAxis = ta.getColor(R.styleable.LineChartView_textColorOnAxis, Color.parseColor("#7A7E85"));
        textSizeOnAxis = (int) ta.getDimension(R.styleable.LineChartView_textSizeOnAxis, Utils.dp2px(getContext(), 17));
        linColorOnCoord = ta.getColor(R.styleable.LineChartView_linColorOnCoord, Color.parseColor("#0093DD"));
        lineSizeOnCoord = lineSizeOnAxis;
        bgColor = ta.getColor(R.styleable.LineChartView_bgColor, Color.WHITE);
        ta.recycle();

        // 初始化
        initPaint();// 初始化画笔
        coords = new ArrayList<>();

        interval = Utils.dp2px(getContext(), 60);
        firstInterval = interval / 2;
        intervalY = (int) (interval * 0.7);

        Rect bound = new Rect();
        paintAxis.getTextBounds("A", 0, 1, bound);
        textHeightOnAxis = bound.height();
        textWidthOnAxis = bound.width();

        minHeight = intervalY * 6 + textHeightOnAxis * 2;// 计算控件最小高度
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        paintAxis = new Paint();
        paintAxis.setColor(lineColorOnAxis);
        paintAxis.setStrokeWidth(lineSizeOnAxis);
        paintAxis.setStyle(Paint.Style.FILL);
        paintAxis.setAntiAlias(true);
        paintAxis.setTextSize(textSizeOnAxis);

        paintPointOnCoord = new Paint();
        paintPointOnCoord.setColor(linColorOnCoord);
        paintPointOnCoord.setStrokeWidth(lineSizeOnCoord);
        paintPointOnCoord.setStyle(Paint.Style.FILL);
        paintPointOnCoord.setAntiAlias(true);

        paintLineOnCoord = new Paint();
        paintLineOnCoord.setColor(linColorOnCoord);
        paintLineOnCoord.setStrokeWidth(lineSizeOnCoord);
        paintLineOnCoord.setStyle(Paint.Style.STROKE);
        paintLineOnCoord.setAntiAlias(true);

        // 黑板擦
        paintClear = new Paint();
        paintClear.setStyle(Paint.Style.FILL);
        paintClear.setColor(bgColor);
        paintClear.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("testview", "onMeasure");

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = widthSize;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            // 判断给定高度是否小于最小高度
            height = minHeight < heightSize ? heightSize : minHeight;
        } else {
            //直接设置为最小高度
            height = minHeight;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.i("testview", "onLayout");

        if (changed) {
            width = getWidth();
            height = getHeight();

            originX = textWidthOnAxis * 2;
            originY = height - textHeightOnAxis * 2;

            offsetX = firstInterval + originX;

            minX = width - originX - (coords.size() - 1) * interval;
            maxX = offsetX;

            setBackgroundColor(bgColor);
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("testview", "onDraw");

        drawPointOnX(canvas);// 绘制x轴上的点
        drawAxis(canvas);// 绘制坐标轴
        drawPointOnY(canvas);// 绘制Y轴上的点
    }


    /**
     * 绘制Y轴上的点
     *
     * @param canvas 画布
     */
    private void drawPointOnY(Canvas canvas) {
        for (int i = 1; i <= 5; i++) {
            canvas.drawCircle(originX, originY - (i * intervalY), lineSizeOnCoord * 2, paintAxis);
        }
        paintAxis.setColor(textColorOnAxis);
        paintAxis.setTextSize(textSizeOnAxis);

        String[] dataY = new String[]{"E", "D", "C", "B", "A"};
        for (int poi = 0; poi < 5; poi++) {
            canvas.drawText(dataY[poi], originX - textWidthOnAxis - lineSizeOnCoord * 3,
                    originY - (intervalY * (poi + 1)) + textHeightOnAxis / 2, paintAxis);
        }
    }

    /**
     * 绘制坐标轴
     *
     * @param canvas 画布
     */
    private void drawAxis(Canvas canvas) {
        canvas.drawLine(originX, (float) (originY - 5.8 * intervalY), originX, originY, paintAxis);
        canvas.drawLine(originX - lineSizeOnAxis / 2, originY, width, originY, paintAxis);
    }

    /**
     * 绘制x轴上的点
     *
     * @param canvas 画布
     */
    private void drawPointOnX(Canvas canvas) {
        Paint xContentPaint = new Paint(paintAxis);
        xContentPaint.setTextSize(Utils.dp2px(getContext(), 14));
        Path path = new Path();
        int x, y;
        for (int i = 0; i < (coords.size() - 1); i++) {
            x = i * interval + offsetX;
            y = originY - coords.get(i).getY() * intervalY;
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
            canvas.drawCircle(x, y, lineSizeOnCoord * 2, paintPointOnCoord);// 绘制坐标点
            canvas.drawCircle(x, originY, lineSizeOnCoord * 2, paintAxis);// 绘制X轴上的点

            Rect bound = new Rect();

            xContentPaint.getTextBounds(coords.get(i).getTitle(), 0,
                    coords.get(i).getTitle().length(), bound);
            // 绘制x轴的文字
            canvas.drawText(coords.get(i).getTitle(), x - bound.width() / 2,
                    originY + bound.height() + lineSizeOnAxis * 2, xContentPaint);
            // 绘制坐标描述信息
            drawDesOnCoord(canvas, i, x, y);
        }
        canvas.drawPath(path, paintLineOnCoord);// 绘制坐标线

        // 将折线超出x轴坐标的部分截取掉
        RectF rectF = new RectF(0, 0, originX - lineSizeOnAxis / 2, height);
        canvas.drawRect(rectF, paintClear);
    }

    private void drawDesOnCoord(Canvas canvas, int poi, int x, int y) {
        Rect bound = new Rect();
        String str = String.valueOf(coords.get(poi).getValue());
        paintAxis.getTextBounds(str, 0, str.length(), bound);
        if (poi == 0) {//第一个
            //判断显示位置
            if (coords.get(0).getY() - coords.get(1).getY() == 0) {
                //显示正上方
                canvas.drawText(str, x - bound.width() / 2, y - bound.height(), paintAxis);
            } else {
                if (coords.get(0).getY() > coords.get(1).getY()) {
                    // 显示正上方
                    canvas.drawText(str, x - bound.width() / 2, y - bound.height(), paintAxis);
                } else {
                    // 显示正下方
                    canvas.drawText(str, x - bound.width() / 2, y + bound.height() * 2, paintAxis);
                }
            }
        } else if (poi == (coords.size() - 1) - 1) {//最后一个
            int previous = poi - 1;
            //判断显示位置
            if (coords.get(previous).getY() - coords.get(poi).getY() == 0) {
                //显示正上方
                canvas.drawText(str, x - bound.width() / 2, y - bound.height(), paintAxis);
            } else {
                if (coords.get(previous).getY() > coords.get(poi).getY()) {
                    // 显示正下方
                    canvas.drawText(str, x - bound.width() / 2, y + bound.height() * 2, paintAxis);
                } else {
                    // 显示正上方
                    canvas.drawText(str, x - bound.width() / 2, y - bound.height(), paintAxis);
                }
            }
        } else {
            int previous = poi - 1;
            int behind = poi + 1;

            boolean b1 = coords.get(previous).getY() > coords.get(poi).getY();
            boolean b2 = coords.get(behind).getY() > coords.get(poi).getY();

            //判断显示位置
            if (b1 & b2) {
                //显示正下方
                canvas.drawText(str, x - bound.width() / 2, y + bound.height() * 2, paintAxis);
            } else if (!b1 & !b2) {
                // 显示正上方
                canvas.drawText(str, x - bound.width() / 2, y - bound.height(), paintAxis);
            } else if (b1 & !b2) {
                // 显示右上方
                canvas.drawText(str, x, y - bound.height(), paintAxis);
            } else {
                // 显示左上方
                canvas.drawText(str, x - bound.width(), y - bound.height(), paintAxis);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //判断是否需要滑动
        if (interval * (coords.size() - 1) <= width - originX)
            return false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX() - startX;// 得到移动的距离
                startX = event.getX();
                float buf = offsetX + moveX;
                if (buf > maxX) {
                    offsetX = maxX;
                } else if (buf < minX) {
                    offsetX = minX;
                } else {
                    offsetX = (int) buf;
                }
                invalidate();
                break;
        }
        return true;
    }

    /**
     * 坐标点 Coordinate
     */
    public static class Coord {
        private int y;// y轴坐标
        private int value;// 值
        private String title;// 标题

        public Coord(int value, String title) {
            this.y = setScale(value);
            this.value = value;
            this.title = title;
        }

        private int setScale(int value) {
            int scale;
            if (100 >= value & value >= 90) {
                scale = 5;
            } else if (value >= 80) {
                scale = 4;
            } else if (value >= 70) {
                scale = 3;
            } else if (value >= 60) {
                scale = 2;
            } else {
                scale = 1;
            }
            return scale;
        }

        public int getY() {
            return y;
        }

        public String getTitle() {
            return title;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 设置坐标点
     *
     * @param coords 坐标点对象
     */
    public void setCoords(List<Coord> coords) {
        this.coords = coords;
    }
}