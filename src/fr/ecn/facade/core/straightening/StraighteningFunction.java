package fr.ecn.facade.core.straightening;

import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;

import fr.ecn.common.core.geometry.Distance;
import fr.ecn.common.core.geometry.Point;
import fr.ecn.common.core.image.ColorImage;

public class StraighteningFunction {

	private ArrayList<Point> beginPoints;
	private ArrayList<Point> endPoints;
	
	protected ColorImage result;
	
	/**
	 * Straightens the fronts delimited by edgesPoints to the given groundDistance/ratio.
	 * @param edgesPoints
	 * @param file path, to display / gets information
	 * @param groundDistance
	 * @param ratio height/width
	 * @param pixelPerMeter
	 */
	public StraighteningFunction(List<Point> edgesPoints, ColorImage image, int groundDistance, double ratio, int pixelPerMeter) {
		this.beginPoints = new ArrayList<Point>(edgesPoints);
		this.endPoints = new ArrayList<Point>();
		
		computeEndPoints(groundDistance * pixelPerMeter, ratio);
		
		this.result = this.straightenImage(image, beginPoints, endPoints);
	}

	/**
	 * Straightens the fronts delimited by edgesPoints to the given groundDistance and an estimated ratio.
	 * @param edgesPoints
	 * @param file path, to display / gets information
	 * @param groundDistance
	 * @param an horizontal vanishing point
	 * @param pixelPerMeter
	 */
	public StraighteningFunction(List<Point> edgesPoints, ColorImage image, int groundDistance, Point horizontalVanishingPoint, int pixelPerMeter) {
		this.beginPoints = new ArrayList<Point>(edgesPoints);
		this.endPoints = new ArrayList<Point>();
		
		computeEndPoints(groundDistance* pixelPerMeter, horizontalVanishingPoint);
		
		this.result = this.straightenImage(image, beginPoints, endPoints);
	}
	
	//TODO Correct this when good vanishing points
	/**
	 * Compute end points from a ground distance in pixels and deduces height/width ratio from the corresponding horizontal vanishing point.
	 * @param groundDistance
	 */
	private void computeEndPoints(double pixelGroundDistance, Point horizontalVanishingPoint) {
		//This algorithm is under the hypothesis vertical vanishing point is very further to image than others.
		this.beginPoints = Homography.sortPoints(this.beginPoints);
		int index = horizontalVanishingPoint.getX() > beginPoints.get(0).getX() ? 1 : 2;
		double CA = Distance.distance(beginPoints.get(index), horizontalVanishingPoint);
		double CN = Distance.distance(beginPoints.get(index), beginPoints.get(index == 2 ? 1 : 2));
		double v = CA * pixelGroundDistance / CN;
		System.out.println("v = " + v + ", CN = " + CN +", CA = " + CA);
		double ratio = Math.abs(CN / (CN - v));
		double width = pixelGroundDistance;
		double height = width / ratio;
		System.out.println("Width = " + width + ", height = " + height);

		this.createEndPoints(width, height);
	}
	
	/**
	 * Compute end points from a ground distance and a given height/width ratio.
	 * @param groundDistance
	 * @param height/width ratio
	 * @param pixelPerMeter
	 */
	private void computeEndPoints(double pixelGroundDistance, double ratio) {
		double width = pixelGroundDistance;
		double height = width * ratio;
		
		this.createEndPoints(width, height);
	}
	
	private void createEndPoints(double width, double height) {
		Point upLeft = new Point(width, 0);
		Point upRight = new Point(width, height);
		Point downRight = new Point(0, height);
		Point downLeft = new Point(0, 0);
		
		endPoints.add(upLeft);
		endPoints.add(upRight);
		endPoints.add(downRight);
		endPoints.add(downLeft);
		
	}
	
	/**
	 * Computes the point to point homography from begin and end points
	 * @param file
	 */
	private ColorImage straightenImage(ColorImage source, List<Point> beginPoints, List<Point> endPoints) {
		Homography h = new Homography(beginPoints, endPoints);
		
		// inits
		Matrix revHomography = h.reverseSquareHomography;
		int width = (int) Math.abs(Math.ceil(h.endPoints.get(3).getX()-h.endPoints.get(0).getX()));
		int height = (int) Math.abs(Math.ceil(h.endPoints.get(1).getY()-h.endPoints.get(0).getY()));
		
		ColorImage result = new ColorImage(width, height);
		
		// Pixels vectors
		Matrix X = new Matrix(3,1);
		Matrix Y = new Matrix(3,1);
		X.set(2, 0, 1);
		
		for (int i=0;i<width;i++) {
			for (int j=0;j<height;j++) {
				// Pixels' coordinates
				X.set(0, 0, i);
				X.set(1, 0, j);
				// reverse side transformation
				Y = revHomography.times(X);
				
				int x = (int) (Y.get(0, 0)/Y.get(2, 0));
				int y = (int) (Y.get(1, 0)/Y.get(2, 0));
				
				if (x > source.getWidth() || x < 0 || y > source.getHeight() || y < 0) {
					continue;
				}
				
				int pixel = source.getPixel(x, y);
				result.setPixel(i, j, pixel);
			}
		}
		
		return result;
	}

	/**
	 * @return the result
	 */
	public ColorImage getResult() {
		return result;
	}
	
}
