package fr.ecn.facade.core.utils;

import java.util.List;

import fr.ecn.common.core.geometry.Point;
import fr.ecn.facade.core.model.Face;

public class FaceExctractor {
	
	/**
	 * Exctract a face form a list of points
	 * 
	 * @param points
	 * @return
	 */
	public Face exctractFace(List<Point> points) {
		// inits
		double distance;
		double maxDis;
		
		Point p1 = null, p2 = null, p3 = null, p4 = null;
		
		Point center = this.barycenter(points);
		
		// corner #1 is farthest point from barycenter
		maxDis = 0;
		for (Point p : points) {
			distance = this.distance(p, center);
			if (distance > maxDis) {
				maxDis = distance;
				p1 = p;
			}
		}
		
		// corner #2 is farthest point from #1
		maxDis = 0;
		for (Point p : points) {
			distance = this.distance(p, p1);
			if (distance >= maxDis) {
				maxDis = distance;
				p2 = p;
			}
		}
		
		// corner 3 is farthest point from [#1 #2]
		p3 = this.farthestPoint(points, p1, p2);
		if (p3 == null) {
			p3 = this.farthestPoint(points, p2, p1);
		}
		
		// corner 4 is farthest point from [#1 #3] or [#2 #3] that isn't in the triangle
		p4 = this.farthestPoint(points, p1, p3);
		if (p4 == null) {
			p4 = this.farthestPoint(points, p3, p1);
		}
		if (this.belongsToTriangle(p1, p2, p3, p4)) {
			p4 = this.farthestPoint(points, p2, p3);
			if (p4 == null) {
				p4 = this.farthestPoint(points, p3, p2);
			}
		}
		
		return new Face(p1, p2, p3, p4);
	}

	private Point barycenter(List<Point> points) {
		double moyX = 0, moyY = 0;
		
		for(Point p : points) {
			moyX += p.getX();
			moyY += p.getY();
		}
		
		moyX /= points.size();
		moyY /= points.size();
		
		return new Point(moyX, moyY);
	}
	
	private double distance(Point p1, Point p2) {
		return Math.pow(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2), 0.5);
	}

	/**
	 * is toBeTested in the half-plane on the left from [p0p1]
	 * @param p0
	 * @param p1
	 * @param toBeTested
	 * @return boolean
	 */
	public boolean isInHalfPlane(Point p0, Point p1, Point toBeTested) {
		double a, b, zero, sens;

		zero = 1; sens = p1.getX()-p0.getX();

		// Straight line (p0p1) parameters
		a = (p1.getY()-p0.getY())/(p1.getX()-p0.getX());
		b = p0.getY()-a*p0.getX();
		if (p1.getX()-p0.getX() == 0) {
			a = -1;
			b = p0.getX();
			zero = 0;
			sens = p0.getY()-p1.getY();
		}

		double x = toBeTested.getX(); double y = toBeTested.getY();
		double test = (zero*y-a*x-b)*sens;
		if (test < 0) {
			return true;
		}
		return false;
	}

	public boolean belongsToTriangle(Point p0, Point p1, Point p2, Point toBeTested) {

		// to which planes does toBeTested belong ?
		boolean test0 = isInHalfPlane(p0, p1, toBeTested);
		boolean test1 = isInHalfPlane(p1, p2, toBeTested);
		boolean test2 = isInHalfPlane(p2, p0, toBeTested);

		// do the triangle corners belong to the same half planes ?
		boolean testP0 = isInHalfPlane(p0, p1, p2);
		boolean testP1 = isInHalfPlane(p1, p2, p0);
		boolean testP2 = isInHalfPlane(p2, p0, p1);

		// let's group the tests to lighten the "if"
		boolean oui0 = (test0==true&&testP0==true);
		boolean oui1 = (test1==true&&testP1==true);
		boolean oui2 = (test2==true&&testP2==true);

		boolean non0 = (test0==false&&testP0==false);
		boolean non1 = (test1==false&&testP1==false);
		boolean non2 = (test2==false&&testP2==false);

		if ((oui0&&oui1&&oui2)||(non0&&non1&&non2)) {
			return true;
		}
		return false;
	}

	/**
	 * find the farthest point from a given segment [p1p2] on the right
	 * @param fop
	 * @param p1
	 * @param p2
	 * @return farthestPoint
	 */
	public Point farthestPoint(List<Point> fop, Point p1, Point p2) {
		Point result = null;
		double distanceMax = 10;

		// determining the straight line parameters
		double m, zero, p;
		// case 1 : the line is vertical
		if (p2.getX()-p1.getX() == 0) {
			m = 1;
			zero = 0;
			p = p2.getX();
			// case 2 : the line isn't vertical
		} else {
			m = (p2.getY()-p1.getY())/(p2.getX()-p1.getX());
			zero = 1;
			p = p2.getY() - m * p2.getX();
		}
		double norme = Math.pow(1 + Math.pow(m, 2), 0.5);

		for (int i=0;i<fop.size();i++) {
			double distance = Math.abs(m*fop.get(i).getX()-zero*fop.get(i).getY()+p)/norme;

			// we do a half-plane test to distinguish the points on the left from those on the right
			boolean demiPlan = isInHalfPlane(p1, p2, fop.get(i));
			if ((distance >= distanceMax)&&(demiPlan)) {
				distanceMax = distance;
				result = fop.get(i);
			}
		}
		return result;
	}
}
