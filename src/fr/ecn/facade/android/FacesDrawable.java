/**
 * 
 */
package fr.ecn.facade.android;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import fr.ecn.facade.android.utils.Drawing;
import fr.ecn.facade.core.model.Face;

/**
 * @author jerome
 *
 */
public class FacesDrawable extends Drawable {
	
	protected FacesController controller;

	/**
	 * @param cissorController
	 */
	public FacesDrawable(FacesController scissorController) {
		super();
		this.controller = scissorController;
	}

	/* (non-Javadoc)
	 * @see android.graphics.drawable.Drawable#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.YELLOW);
		paint.setStyle(Paint.Style.STROKE);
		
		for (Face face : this.controller.faces) {
			Drawing.drawFace(face, canvas, paint);
		}
		
		paint.setColor(Color.RED);
		
		if (this.controller.currentLine != null) {
			this.controller.currentLine.draw(canvas);
		}
	}

	/* (non-Javadoc)
	 * @see android.graphics.drawable.Drawable#getOpacity()
	 */
	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.graphics.drawable.Drawable#setAlpha(int)
	 */
	@Override
	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see android.graphics.drawable.Drawable#setColorFilter(android.graphics.ColorFilter)
	 */
	@Override
	public void setColorFilter(ColorFilter cf) {
		// TODO Auto-generated method stub

	}

}
