package fr.irstv.kmeans;

import java.util.List;

import fr.irstv.dataModel.DataPoint;


/**
 * distance between data elements
 * 
 * @author gmoreau
 *
 */
public interface DataDistance {
	/**
	 * the distance function that must be implemented
	 * @param p1 first point
	 * @param p2 second point
	 * @return distance between p1 and p2
	 */
	public double distance(DataPoint p1,DataPoint p2);
	
	/**
	 * centroid computation (with respect to the distance function 
	 * previously defined)
	 * 
	 * @param l list of points
	 * @return the centroid of the points
	 */
	public DataPoint centroid(List<? extends DataPoint> l);
}
