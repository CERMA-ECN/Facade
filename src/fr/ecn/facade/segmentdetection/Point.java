package fr.ecn.facade.segmentdetection;
/**
 * Class implementing the point data.
 *
 * @author Leo COLLET, Cedric TELEGONE, Ecole Centrale Nantes
 * @version 1.0
 */

public class Point {

	/**
	 * Attributes
	 */
	protected int x;
	protected int y;
	
	/**
	 * Constructors
	 */
	public Point(){
		this.x = 0;
		this.y = 0;
	}
	public Point(int a, int b){
		this.x = a;
		this.y = b;
	}
	
	/**
	 * toString() method.
	 * @return		a String that displays a point like (x,y)
	 */
	public String toString(){
		String s = new String();
		s = "(" + this.x + "," + this.y + ")";
		return s;
	}
	
	/**
	 * Get x value
	 * @return	x
	 */
	public int getX() {
		return x;
	}
	/**
	 * Set x value
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * Get y value
	 * @return	y
	 */
	public int getY() {
		return y;
	}
	/**
	 * Set y value
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	
}
