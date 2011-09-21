package fr.ecn.facade.android;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import fr.irstv.dataModel.DataPoint;
import fr.irstv.dataModel.MkDataPoint;
import fr.irstv.kmeans.DataGroup;

public class VanishingPointsDrawable extends Drawable {
	
	public static final int[] colorMap = {
		Color.RED,
		Color.BLUE,
		Color.GREEN,
		Color.rgb(255, 200, 0),
		Color.YELLOW,
		Color.MAGENTA,
		Color.CYAN,
		Color.WHITE,
	};
	
	protected VanishingPointsController controller;

	/**
	 * @param controller
	 */
	public VanishingPointsDrawable(VanishingPointsController controller) {
		super();
		this.controller = controller;
	}

	@Override
	public void draw(Canvas canvas) {		
		DataGroup[] groups = this.controller.getGroups();
		for (int i=0; i<groups.length; i++) {
			if (!this.controller.isGroupSelected(i))
				continue;
			
			DataGroup group = groups[i];
			
			Paint paint = new Paint();
			paint.setColor(colorMap[i]);
			
			for (MkDataPoint dp : group.getComponents()) {
				DataPoint beginPoint = dp.getSeg().getBeginPoint();
				DataPoint endPoint = dp.getSeg().getEndPoint();
				
				canvas.drawLine((int)beginPoint.get(0), (int)beginPoint.get(1), (int)endPoint.get(0), (int)endPoint.get(1), paint);
			}
			
			DataPoint vp = group.computeCentroid();
			
			paint.setStyle(Paint.Style.FILL);
			
			canvas.drawCircle((float)vp.get(0)*2, (float)vp.get(1)*2, 5f, paint);
		}
	}

	/* (non-Javadoc)
	 * @see android.graphics.drawable.Drawable#getIntrinsicHeight()
	 */
	@Override
	public int getIntrinsicHeight() {
		return this.controller.bitmap.getHeight();
	}

	/* (non-Javadoc)
	 * @see android.graphics.drawable.Drawable#getIntrinsicWidth()
	 */
	@Override
	public int getIntrinsicWidth() {
		return this.controller.bitmap.getWidth();
	}

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// TODO Auto-generated method stub

	}

}
