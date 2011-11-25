package fr.ecn.facade.android;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import fr.ecn.common.core.geometry.Point;
import fr.ecn.common.core.imageinfos.Face;
import fr.ecn.facade.android.utils.Drawing;

public class FacesSimpleDrawable extends Drawable {

	protected FacesSimpleController controller;

	/**
	 * @param controller
	 */
	public FacesSimpleDrawable(FacesSimpleController controller) {
		super();
		this.controller = controller;
	}

	@Override
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.YELLOW);
		paint.setStyle(Paint.Style.STROKE);

		for (Face face : this.controller.getFaces()) {
			Drawing.drawFace(face, canvas, paint);
		}

		paint.setColor(Color.RED);
		
		List<Point> points = this.controller.getPoints();

		if (points != null) {
			if (points.size() == 1) {
				canvas.drawPoint((int) points.get(0).getX(), (int) points
						.get(0).getY(), paint);
			} else {
				for (int i = 1; i < points.size(); i++) {
					canvas.drawLine((int) points.get(i - 1).getX(),
							(int) points.get(i - 1).getY(), (int) points.get(i)
									.getX(), (int) points.get(i).getY(), paint);
				}
			}
		}
	}

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAlpha(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColorFilter(ColorFilter arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see android.graphics.drawable.Drawable#getIntrinsicHeight()
	 */
	@Override
	public int getIntrinsicHeight() {
		return this.controller.getBitmap().getHeight();
	}

	/* (non-Javadoc)
	 * @see android.graphics.drawable.Drawable#getIntrinsicWidth()
	 */
	@Override
	public int getIntrinsicWidth() {
		// TODO Auto-generated method stub
		return this.controller.getBitmap().getWidth();
	}

}
