package fr.ecn.facade.android;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;

import jjil.android.RgbImageAndroid;
import fr.ecn.facade.android.utils.ImageLoader;
import fr.ecn.facade.core.model.Face;
import fr.ecn.facade.core.model.ImageInfos;
import fr.ecn.facade.core.model.Point;
import fr.ecn.facade.core.utils.FaceExctractor;
import fr.ecn.facade.scissor.Polygon;
import fr.ecn.facade.scissor.SCISSOR_STATE;
import fr.ecn.facade.scissor.Scissor;
import fr.ecn.facade.scissor.ScissorLine;

public class FacesController {

	protected Bitmap bitmap;

	protected Scissor scissor;
	
	// We explicitly need a LinkedList here because we need the capacity to
	// remove the last element of the list
	// In fact what we need is only something that implements the Deque and the
	// List interfaces
	protected LinkedList<Face> faces = new LinkedList<Face>();
	
	protected ScissorLine currentLine;

	/**
	 * @param imageInfos
	 */
	public FacesController(ImageInfos imageInfos) {
		super();

		this.bitmap = ImageLoader.loadResized(imageInfos.getPath(), 600);

		this.scissor = new Scissor(RgbImageAndroid.toRgbImage(bitmap));
	}
	
	/**
	 * @return true if the controller isn't in face edition mode
	 */
	public boolean isIdle() {
		return this.currentLine == null;
	}

	/**
	 * Tell this controller to generate a new scissor line.
	 */
	public void startFace() {
		if (this.currentLine != null) {
			if (this.currentLine.getState() != SCISSOR_STATE.HOLD) {
				this.currentLine.endScissor();
			}
			
			Face face = this.convertLineToFace(this.currentLine);
			
			if (face != null) {
				this.faces.add(face);
			}
		}

		this.currentLine = new ScissorLine(this.scissor);

		this.currentLine.setActive();
	}
	
	/**
	 * End the current face
	 */
	public void endFace() {
		if (this.currentLine.getState() != SCISSOR_STATE.HOLD) {
			this.currentLine.endScissor();
		}
		
		this.faces.add(this.convertLineToFace(this.currentLine));
		
		this.currentLine = null;
	}
	
	/**
	 * Cancel the current face
	 */
	public void cancelFace() {
		this.currentLine = null;
	}
	
	/**
	 * Remove the last face added
	 */
	public void removeLastFace() {
		this.faces.removeLast();
	}
	
	/**
	 * Convert a scissorLine to a Face object
	 * 
	 * @param line
	 * @return
	 */
	protected Face convertLineToFace(ScissorLine line) {
		//Get all points from scissorLine
		List<Point> points = new LinkedList<Point>();
		for (Polygon polygon : line.getScissorLine()) {
			for (int i=0; i<polygon.npoints; i++) {
				points.add(new Point(polygon.xpoints[i], polygon.ypoints[i]));
			}
		}
		
		//Not enouth points to make a face
		if (points.size() < 10) {
			return null;
		}
		
		return new FaceExctractor().exctractFace(points);
	}

	/**
	 * Tell current scissor line to add a new key point.
	 * 
	 * @param x
	 *            int x coordinate
	 * @param y
	 *            int y coordinate
	 */
	public void addNewKeyPoint(int x, int y) {
		if (this.currentLine != null)
			this.currentLine.addNewKeyPoint(x, y);
	}

	/**
	 * Tell current scissor line that the cursor is moving to (x,y).
	 * 
	 * @param x
	 *            int x coordinate
	 * @param y
	 *            int y coordinate
	 */
	public void setMovePoint(int x, int y) {
		if (this.currentLine != null)
			this.currentLine.setMovePoint(x, y);
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
}