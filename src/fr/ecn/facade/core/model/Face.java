package fr.ecn.facade.core.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

import fr.ecn.common.core.geometry.Point;

public class Face implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Points of the face
	 */
	protected Point[] points;

	/**
	 * Create a face from an array of points
	 * 
	 * @param points
	 */
	public Face(Point[] points) {
		super();
		this.points = points;
	}

	/**
	 * Create a Face from 4 points.
	 * 
	 * The points will be ordered
	 * 
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 */
	public Face(Point p1, Point p2, Point p3, Point p4) {
		Point[] points = { p1, p2, p3, p4 };
		
		//We sort the points by X coordinate
		Arrays.sort(points, new Comparator<Point>() {
			public int compare(Point p1, Point p2) {
				return (int) (p1.getX() - p2.getX());
			}
		});

		// Ordering points
		Point topRightPoint;
		Point bottomRightPoint;
		Point topLeftPoint;
		Point bottomLeftPoint;

		{//The two left points are points[0] and points[1]
			// NOTE: le point est plus haut si ( en coordonnées image ) y est
			// plus petit!
			if (points[0].getY() < points[1].getY()) {
				topLeftPoint = points[0];
				bottomLeftPoint = points[1];
			} else {
				topLeftPoint = points[1];
				bottomLeftPoint = points[0];
			}
		}

		{//The two left points are points[2] and points[3]
			// NOTE: le point est plus haut si ( en coordonnées image ) y est
			// plus petit!
			if (points[2].getY() < points[3].getY()) {
				topRightPoint = points[2];
				bottomRightPoint = points[3];
			} else {
				topRightPoint = points[3];
				bottomRightPoint = points[2];
			}
		}
		
		Point[] orderedPoints = { bottomLeftPoint, bottomRightPoint, topRightPoint, topLeftPoint };
		
		this.points = orderedPoints;
	}

	/**
	 * Return all points
	 * 
	 * @return the points
	 */
	public Point[] getPoints() {
		return points;
	}
	
}
