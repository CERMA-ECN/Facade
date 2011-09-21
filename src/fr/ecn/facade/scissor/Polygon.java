package fr.ecn.facade.scissor;

import java.io.Serializable;

import android.graphics.Rect;

/**
 * ScissorPolygon inherts from Polygon class. It provides additional basic
 * operations of a Polygon such as find the last point, find the separation
 * point between two Polygon.
 * 
 * @author LIU Xinchang
 * 
 */
public class Polygon implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This total number of endpoints.
	 * 
	 * @serial the number of endpoints, possibly less than the array sizes
	 */
	public int npoints;

	/**
	 * The array of X coordinates of endpoints. This should not be null.
	 * 
	 * @see #addPoint(int, int)
	 * @serial the x coordinates
	 */
	public int[] xpoints;

	/**
	 * The array of Y coordinates of endpoints. This should not be null.
	 * 
	 * @see #addPoint(int, int)
	 * @serial the y coordinates
	 */
	public int[] ypoints;

	/**
	 * The bounding box of this polygon. This is lazily created and cached, so
	 * it must be invalidated after changing points.
	 * 
	 * @see #getBounds()
	 * @serial the bounding box, or null
	 */
	protected Rect bounds;

	/** A big number, but not so big it can't survive a few float operations */
//	private static final double BIG_VALUE = java.lang.Double.MAX_VALUE / 10.0;

	/**
	 * Initializes an empty polygon.
	 */
	public Polygon() {
		// Leave room for growth.
		xpoints = new int[4];
		ypoints = new int[4];
	}

	/**
	 * Create a new polygon with the specified endpoints. The arrays are copied,
	 * so that future modifications to the parameters do not affect the polygon.
	 * 
	 * @param xpoints
	 *            the array of X coordinates for this polygon
	 * @param ypoints
	 *            the array of Y coordinates for this polygon
	 * @param npoints
	 *            the total number of endpoints in this polygon
	 * @throws NegativeArraySizeException
	 *             if npoints is negative
	 * @throws IndexOutOfBoundsException
	 *             if npoints exceeds either array
	 * @throws NullPointerException
	 *             if xpoints or ypoints is null
	 */
	public Polygon(int[] xpoints, int[] ypoints, int npoints) {
		this.xpoints = new int[npoints];
		this.ypoints = new int[npoints];
		System.arraycopy(xpoints, 0, this.xpoints, 0, npoints);
		System.arraycopy(ypoints, 0, this.ypoints, 0, npoints);
		this.npoints = npoints;
	}

	/**
	 * Adds the specified endpoint to the polygon. This updates the bounding
	 * box, if it has been created.
	 * 
	 * @param x
	 *            the X coordinate of the point to add
	 * @param y
	 *            the Y coordiante of the point to add
	 */
	public void addPoint(int x, int y) {
		if (npoints + 1 > xpoints.length) {
			int[] newx = new int[npoints + 10];//More place for grow
			System.arraycopy(xpoints, 0, newx, 0, npoints);
			xpoints = newx;
		}
		if (npoints + 1 > ypoints.length) {
			int[] newy = new int[npoints + 10];//More place for grow
			System.arraycopy(ypoints, 0, newy, 0, npoints);
			ypoints = newy;
		}
		xpoints[npoints] = x;
		ypoints[npoints] = y;
		npoints++;
		if (bounds != null) {
			if (npoints == 1) {
				bounds.left = x;
				bounds.top = y;
			} else {
				if (x < bounds.left) {
					bounds.left = x;
				} else if (x > bounds.right) {
					bounds.right = x;
				}
				if (y < bounds.top) {
					bounds.top = y;
				} else if (y > bounds.bottom) {
					bounds.bottom = y;
				}
			}
		}
	}

	/**
	 * Returns the bounding box of this polygon. This is the smallest rectangle
	 * with sides parallel to the X axis that will contain this polygon.
	 * 
	 * @return the bounding box for this polygon
	 * @see #getBounds2D()
	 * @since 1.1
	 */
	public Rect getBounds() {
		if (bounds == null) {
			if (npoints == 0)
				return bounds = new Rect();
			int i = npoints - 1;
			int minx = xpoints[i];
			int maxx = minx;
			int miny = ypoints[i];
			int maxy = miny;
			while (--i >= 0) {
				int x = xpoints[i];
				int y = ypoints[i];
				if (x < minx)
					minx = x;
				else if (x > maxx)
					maxx = x;
				if (y < miny)
					miny = y;
				else if (y > maxy)
					maxy = y;
			}
			bounds = new Rect(minx, miny, maxx, maxy);
		}
		return bounds;
	}

}