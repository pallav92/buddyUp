package com.yatra.buddyup.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * An ImageView class with a circle mask so that all images are drawn in a
 * circle instead of a square.
 */
public class CircularImageView extends ImageView {
    private static float circularImageBorder = 1f;

    private final Matrix matrix;
    private final RectF source;
    private final RectF destination;
    private final Paint bitmapPaint;
    private final Paint borderPaint;

    public CircularImageView(Context context) {
        this(context, null, 0);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        matrix = new Matrix();
        source = new RectF();
        destination = new RectF();

        bitmapPaint = new Paint();
        bitmapPaint.setAntiAlias(true);
        bitmapPaint.setFilterBitmap(true);
        bitmapPaint.setDither(true);

        borderPaint = new Paint();
        borderPaint.setColor(Color.TRANSPARENT);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(circularImageBorder);
        borderPaint.setAntiAlias(true);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Bitmap getBitmap(VectorDrawable vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    private Bitmap getBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof StateListDrawable) {
            return ((BitmapDrawable) drawable.getCurrent()).getBitmap();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && drawable instanceof VectorDrawable) {
                return getBitmap((VectorDrawable) drawable);
            }
        }
        return null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        Bitmap bitmap = getBitmap(drawable);
        if (bitmap == null) return;

        source.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
        destination.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(),
                getHeight() - getPaddingBottom());

        drawBitmapWithCircleOnCanvas(bitmap, canvas, source, destination);
    }

    /**
     * Given the source bitmap and a canvas, draws the bitmap through a circular
     * mask. Only draws a circle with diameter equal to the destination width.
     *
     * @param bitmap The source bitmap to draw.
     * @param canvas The canvas to draw it on.
     * @param source The source bound of the bitmap.
     * @param dest The destination bound on the canvas.
     */
    public void drawBitmapWithCircleOnCanvas(Bitmap bitmap, Canvas canvas,
                                             RectF source, RectF dest) {
        // Draw bitmap through shader first.
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        matrix.reset();

        // Fit bitmap to bounds.
        matrix.setRectToRect(source, dest, Matrix.ScaleToFit.FILL);

        shader.setLocalMatrix(matrix);
        bitmapPaint.setShader(shader);
        canvas.drawCircle(dest.centerX(), dest.centerY(), dest.width() / 2f,
                bitmapPaint);

        // Then draw the border.
        canvas.drawCircle(dest.centerX(), dest.centerY(),
                dest.width() / 2f - circularImageBorder / 2, borderPaint);
    }
}
