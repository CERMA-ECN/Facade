package fr.ecn.facade.core.straightening;

import java.util.ArrayList;
import java.util.List;

import fr.ecn.common.geometry.Distance;
import fr.ecn.common.geometry.Point;
import fr.ecn.common.image.ColorImage;

public class StraighteningFunction {

	private ArrayList<Point> beginPoints;
	private ArrayList<Point> endPoints;
	private Point horizontalVanishingPoint;
	
	/**
	 * Straightens the fronts delimited by edgesPoints to the given groundDistance/ratio.
	 * @param edgesPoints
	 * @param file path, to display / gets information
	 * @param groundDistance
	 * @param ratio height/width
	 * @param pixelPerMeter
	 */
	public StraighteningFunction(List<Point> edgesPoints, ColorImage image, int groundDistance, double ratio, int pixelPerMeter){
		this.beginPoints = new ArrayList<Point>(edgesPoints);
		this.endPoints = new ArrayList<Point>();
		
		computeEndPoints(groundDistance * pixelPerMeter, ratio);
		
		straightenFront(image);
	}

	/**
	 * Straightens the fronts delimited by edgesPoints to the given groundDistance and an estimated ratio.
	 * @param edgesPoints
	 * @param file path, to display / gets information
	 * @param groundDistance
	 * @param an horizontal vanishing point
	 * @param pixelPerMeter
	 */
	public StraighteningFunction(List<Point> edgesPoints, ColorImage image, int groundDistance, Point horizontalVanishingPoint, int pixelPerMeter){
		this.beginPoints = new ArrayList<Point>(edgesPoints);
		this.horizontalVanishingPoint = horizontalVanishingPoint;
		this.endPoints = new ArrayList<Point>();
		
		computeEndPoints(groundDistance* pixelPerMeter);
		
		straightenFront(image);
	}
	
	//TODO Correct this when good vanishing points
	/**
	 * Compute end points from a ground distance in pixels and deduces height/width ratio from the corresponding horizontal vanishing point.
	 * @param groundDistance
	 */
	private void computeEndPoints(double pixelGroundDistance){
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
	 * Compute end points from a ground distance and a given height/width ratio.
	 * @param groundDistance
	 * @param height/width ratio
	 * @param pixelPerMeter
	 */
	private void computeEndPoints(double pixelGroundDistance, double ratio){
		int width = (int) Math.ceil(pixelGroundDistance);
		int height = (int) Math.ceil(width * ratio);
		
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
	private void straightenFront(ColorImage image){
		Homography h = new Homography(beginPoints, endPoints);
		ImageStraightening i = new ImageStraightening(image);
		i.straightenUp(h);
	}
	
}
