package fr.ecn.facade.android.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import fr.ecn.common.core.geometry.Point;
import fr.ecn.facade.core.model.Face;

/**
 * @author jerome
 * 
 * A class that provide methods to draw Objects into an android canvas
 */
public class Drawing {
	
	/**
	 * Draw a given Face in a given Canvas using a given Paint
	 * 
	 * @param face the face to be drawn
	 * @param canvas the canvas to draw into
	 * @param paint the paint to use to draw
	 */
	public static void drawFace(Face face, Canvas canvas, Paint paint) {
		Point[] points = face.getPoints();
		
		Path path = new Path();
		path.moveTo((float) points[points.length-1].getX(), (float) points[points.length-1].getY());

		for (int i = 0; i < points.length; i++) {
			Point p = points[i];
			
			path.lineTo((float) p.getX(), (float) p.getY());
		}
		
		canvas.drawPath(path, paint);
	}
}
