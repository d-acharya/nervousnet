package ch.ethz.coss.nervousnet.hub.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;

public class PositionSensorView extends View {
	
	public static final int SIZE = 300;
	public static final float TOP = 0.0f;
	public static final float LEFT = 0.0f;
	public static final float RIGHT = 1.0f;
	public static final float BOTTOM = 1.0f;
	public static final float CENTER = 0.5f;
	
	private Bitmap mBackground;
	private Paint mBackgroundPaint;
	
	String lon;
	String lat;
	
	float lonLabelStartX = 40;
	float lonLabelStartY = 50;
	float latLabelStartX = 40;
	float latLabelStartY = 	100;
	
	float lonValStartX = 250;
	float lonValStartY = 50;
	float latValStartX = 250;
	float latValStartY = 100;
	
	float textSize = 40;
	
	public PositionSensorView(final Context context, final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
		//readAttrs(context, attrs, defStyle);
		init();
	}
	
	public PositionSensorView(final Context context, final AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PositionSensorView(final Context context) {
		this(context, null, 0);
	}
	
	
	@Override
	protected void onDraw(final Canvas canvas) {
		drawBackground(canvas);
		final float scale = Math.min(getWidth(), getHeight());
		canvas.scale(scale, scale);
		canvas.translate((scale == getHeight()) ? ((getWidth() - scale) / 2) / scale : 0
				, (scale == getWidth()) ? ((getHeight() - scale) / 2) / scale : 0);
		drawText(canvas);
	}
	
	private void drawText(Canvas canvas){
		canvas.save();
		canvas.scale(1/1000, 1/1000);
		
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(textSize);
		paint.setLinearText(true);
		paint.setShadowLayer(100f,.05f,.05f,Color.argb(60,20, 250, 250));
		canvas.drawText("Longitude", lonLabelStartX , lonLabelStartY , paint);
		canvas.drawText("Latitude", latLabelStartX , latLabelStartY , paint);
		canvas.drawText(lon, lonValStartX , lonValStartY , paint);
		canvas.drawText(lat, latValStartX , latValStartY , paint);
		
		canvas.restore();
	}
	
	@Override
	protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
			drawBatteryBackground();
	}
	
	private void drawBatteryBackground() {
		if (null != mBackground) {
			mBackground.recycle();
		}
	}

	@TargetApi(11)
	public void init(){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
		initDrawingRects();
	}
	
	public void initDrawingRects() {
		
	}
	
	public void initDrawingTools(){
		mBackgroundPaint = new Paint();
		mBackgroundPaint.setFilterBitmap(true);
	}
	
	private void drawBackground(final Canvas canvas) {
        if (null != mBackground) {
				canvas.drawBitmap(mBackground, 0, 0, mBackgroundPaint);
			}
	}
	
	
	
	@Override
	protected void onRestoreInstanceState(final Parcelable state) {
		final Bundle bundle = (Bundle) state;
		final Parcelable superState = bundle.getParcelable("superState");
		super.onRestoreInstanceState(superState);

		//mChargingState = bundle.getBoolean("mChargingState");
		//mCurrentBatteryLevel = bundle.getFloat("mCurrentBatteryLevel");
		//mTargetBatteryLevel = bundle.getFloat("mTargetBatteryLevel");
	}
	
	@Override
	protected Parcelable onSaveInstanceState() {
		final Parcelable superState = super.onSaveInstanceState();

		final Bundle state = new Bundle();
		state.putParcelable("superState", superState);
		
		return state;
	}
	
	@Override
	protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {

		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		final int chosenWidth = chooseDimension(widthMode, widthSize);
		final int chosenHeight = chooseDimension(heightMode, heightSize);
		setMeasuredDimension(chosenWidth, chosenHeight);
	}
	
	private int chooseDimension(final int mode, final int size) {
		switch (mode) {
		case View.MeasureSpec.AT_MOST:
		case View.MeasureSpec.EXACTLY:
			return size;
		case View.MeasureSpec.UNSPECIFIED:
		default:
			return getDefaultDimension();
		}
	}
	
	private int getDefaultDimension() {
		return SIZE;
	}
	
	public void setPositionValues(String [] values){
		lat = values[0];
		lon = values[0];
		invalidate();
	}	
}

/*

float originalStrokeWidth = paint.getStrokeWidth();
float originalTextSize = paint.getTextSize();
final float magnifier = 1000f;

canvas.save();
canvas.scale(1f / magnifier, 1f / magnifier);

paint.setTextSize(originalTextSize );
paint.setStrokeWidth(originalStrokeWidth );

canvas.drawText(text, x , y , paint);
canvas.restore();

paint.setTextSize(originalTextSize);
paint.setStrokeWidth(originalStrokeWidth);
*/