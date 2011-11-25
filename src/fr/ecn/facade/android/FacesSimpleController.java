package fr.ecn.facade.android;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;

import fr.ecn.common.android.image.BitmapLoader;
import fr.ecn.common.core.geometry.Point;
import fr.ecn.common.core.imageinfos.Face;
import fr.ecn.facade.core.model.ImageInfos;

public class FacesSimpleController {
	
	protected Bitmap bitmap;
	protected float scale;
	
	// We explicitly need a LinkedList here because we need the capacity to
	// remove the last element of the list
	// In fact what we need is only something that implements the Deque and the
	// List interfaces
	protected LinkedList<Face> faces = new LinkedList<Face>();
	
	protected List<Point> points = null;

	public FacesSimpleController(ImageInfos imageInfos) {
		BitmapLoader.ResizedBitmap resizedBitmap = BitmapLoader.loadResized(imageInfos.getPath(), 600);
		
		this.bitmap = resizedBitmap.bitmap;
		this.scale = resizedBitmap.scale;
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
			Face face = new Face();
			face.getPoints().addAll(this.points);
			
			this.faces.add(face);
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
	
	/**
	 * Return a list of faces scaled to the original size of the image
	 * 
	 * @return
	 */
	public List<Face> getFinalFaces() {
		List<Face> finalFaces = new LinkedList<Face>();
		
		for (Face face : this.faces) {
			Face finalFace = new Face();

			for (Point point : face.getPoints()) {
				finalFace.getPoints().add(new Point(point.getX() / scale, point.getY() / scale));
			}
			
			finalFaces.add(finalFace);
		}
		
		return finalFaces;
	}

}
