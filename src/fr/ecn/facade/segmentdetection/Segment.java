package fr.ecn.facade.segmentdetection;

import java.util.Vector;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import fr.irstv.dataModel.DataPoint;
import fr.irstv.dataModel.MkDataPoint;

/**
 * Class implementing the segment data structure.
 *
 * @author Leo COLLET, Cedric TELEGONE, Ecole Centrale Nantes
 * @version 1.0
 */

public class Segment {

	/**
	 * Attributes
	 */
	protected Vector<Point> points;
	protected Point startPoint;
	protected Point endPoint;

	/**
	 * Public constructors
	 */
	public Segment(){
		this.points = new Vector<Point>();
		this.endPoint = new Point();
		this.startPoint = new Point();
	}
	public Segment(Vector<Point> v){
		this.points = v;
		this.endPoint = new Point();
		this.startPoint = new Point();
	}
	public Segment(MkDataPoint dp) {
		DataPoint beginPoint = dp.getSeg().getBeginPoint();
		DataPoint endPoint = dp.getSeg().getEndPoint();
		setStartPoint(new Point((int) beginPoint.get(0),(int) beginPoint.get(1)));
		setEndPoint(new Point((int) endPoint.get(0),(int) endPoint.get(1)));
	}

	/**
	 * Add a point into the point vector
	 * For each new point, start point and end point are re-calculated using a linear regression method.
	 * 
	 * @param 	p	the point to be added in the segment
	 */
	public void addPoint(Point p){
		this.points.add(p);
	}

	/**
	 * Compute start point and end point
	 * 
	 * @return	A double value that represents the ratio between two eigenvalues. That value is used as a criteria of relevance of a segment.
	 */
	public double computeStartEndPoints(){

		double meanX = 0;
		double meanY = 0;
		int Xmin = this.points.get(0).getX();
		int Xmax = this.points.get(0).getX();
		int Ymin = this.points.get(0).getY();
		int Ymax = this.points.get(0).getY();

		for (int i=0; i<this.points.size(); i++){
			meanX = meanX + this.points.get(i).getX();
			meanY = meanY + this.points.get(i).getY();
			if (this.points.get(i).getX() < Xmin){
				Xmin = this.points.get(i).getX();
			} else if (this.points.get(i).getX() > Xmax){
				Xmax = this.points.get(i).getX();
			}
			if (this.points.get(i).getY() < Ymin){
				Ymin = this.points.get(i).getY();
			} else if (this.points.get(i).getY() > Ymax){
				Ymax = this.points.get(i).getY();
			}
		}
		meanX = meanX / this.points.size();
		meanY = meanY / this.points.size();

		double[][] M = new double[2][2];
		for (int i=0; i<this.points.size(); i++){
			M[0][0] = M[0][0] + (this.points.get(i).getX() - meanX)*(this.points.get(i).getX() - meanX);
			M[1][1] = M[1][1] + (this.points.get(i).getY() - meanY)*(this.points.get(i).getY() - meanY);
			M[0][1] = M[0][1] + (this.points.get(i).getX() - meanX)*(this.points.get(i).getY() - meanY);
			M[1][0] = M[1][0] + (this.points.get(i).getX() - meanX)*(this.points.get(i).getY() - meanY);
		}
		Matrix MM = new Matrix(M);
		EigenvalueDecomposition evd = new EigenvalueDecomposition(MM);
		Matrix lambda = evd.getD();
		Matrix v = evd.getV();

		double theta = Math.atan2(v.get(1, 1), v.get(0, 1));
		double conf;
		if (lambda.get(0,0) > 0){
			conf = lambda.get(1, 1) / lambda.get(0, 0);
		} else {
			conf = 100000;
		}
		if (conf >= 400){
			double r = Math.sqrt((Xmax - Xmin)*(Xmax - Xmin) + (Ymax - Ymin)*(Ymax - Ymin));
			this.startPoint.setX((int) (meanX - Math.cos(theta)*r/2));
			this.startPoint.setY((int) (meanY - Math.sin(theta)*r/2));
			this.endPoint.setX((int) (meanX + Math.cos(theta)*r/2));
			this.endPoint.setY((int) (meanY + Math.sin(theta)*r/2));
		}
		return conf;
	}

	/**
	 * toString() method
	 * @return	a String representing a segment as a list of points: (x1,y1) (x2,y2)... without notion of order.
	 */
	public String toString(){
		String s = new String();
		for (int i=0; i<this.points.size(); i++){
			s = s + this.points.get(i).toString() + "\t";
		}
		return s;
	}

	/**
	 * Get points value
	 * @return	points
	 */
	public Vector<Point> getPoints() {
		return points;
	}
	/**
	 * Set points value
	 * @param points
	 */
	public void setPoints(Vector<Point> points) {
		this.points = points;
	}
	/**
	 * Get startPoint value
	 * @return	startPoint
	 */
	public Point getStartPoint() {
		return startPoint;
	}
	/**
	 * Set startPoint value
	 * @param startPoint
	 */
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}
	/**
	 * Get endPoint value
	 * @return	endPoint
	 */
	public Point getEndPoint() {
		return endPoint;
	}
	/**
	 * Set endPoint value
	 * @param endPoint
	 */
	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	/**
	 * 
	 * @author Elsa Arrou-Vignod, Florent Buisson
	 */
	public double[] computeParams() {
		double[] ab = new double[3];
		double a, b, vertical;
		a = 0; b = 0; vertical = 0;
		if ((endPoint.x - startPoint.x) != 0) {
			a = (endPoint.y - startPoint.y)/(endPoint.x - startPoint.x);
			b = endPoint.y - endPoint.x*a;
		} else {
			a = 1;
			b = endPoint.x;
			vertical = 1;
		}
		ab[0] = a; ab[1] = b; ab[2] = vertical;
		return ab;
	}

}
