package fr.ecn.facade.android;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;

import fr.ecn.facade.android.utils.ImageLoader;
import fr.ecn.facade.core.model.Face;
import fr.ecn.facade.core.model.ImageInfos;
import fr.ecn.facade.core.model.Point;

public class FacesSimpleController {
	
	protected Bitmap bitmap;
	
	// We explicitly need a LinkedList here because we need the capacity to
	// remove the last element of the list
	// In fact what we need is only something that implements the Deque and the
	// List interfaces
	protected LinkedList<Face> faces = new LinkedList<Face>();
	
	protected List<Point> points = null;

	public FacesSimpleController(ImageInfos imageInfos) {
		this.bitmap = ImageLoader.loadResized(imageInfos.getPath(), 600);
	}
	
	/**
	 * @return true if the controller isn't in face edition mode
	 */
	public boolean isIdle() {
		return this.points == null;
	}
	
	public void startFace() {
		this.points = new ArrayList<Point>(4);
	}

	public void addPoint(float x, float y) {
		this.points.add(new Point(x, y));
		
		if (this.points.size() == 4) {
			this.faces.add(new Face(this.points.get(0), this.points.get(1), this.points.get(2), this.points.get(3)));
			this.points = null;
		}
	}
	
	/**
	 * Cancel the current face
	 */
	public void cancelFace() {
		this.points = null;
	}
	
	/**
	 * Remove the last face added
	 */
	public void removeLastFace() {
		this.faces.removeLast();
	}

	/**
	 * @return the bitmap
	 */
	public Bitmap getBitmap() {
		return bitmap;
	}
	
	/**
	 * @return the faces
	 */
	public List<Face> getFaces() {
		return faces;
	}

	/**
	 * @return the points
	 */
	public List<Point> getPoints() {
		return points;
	}

}
