package fr.irstv.kmeans;


import java.util.ArrayList;

import fr.irstv.dataModel.DataPoint;
import fr.irstv.dataModel.MkDataPoint;


/**
 * data groups class: contains the groups of data that are created
 * by the kmeans algorithm
 * 
 * @author  gmoreau
 */public class DataGroup {
	 protected ArrayList<MkDataPoint> components; // points H
	 
	 protected DataPoint centroid; // milieu du segment origine-point de fuite
	 
	 protected DataDistance fctDistance;
	 
	 DataGroup(DataDistance fctDistance) {
		this.fctDistance = fctDistance;
		components = new ArrayList<MkDataPoint>();
	 }
	 
	 void add(MkDataPoint dp) {
		 components.add(dp);
	 }
	 
	 /**
	 * returns centroid without any recomputation
	 * @return
	 * @uml.property  name="centroid"
	 */
	 public DataPoint getCentroid() {
		 if (centroid == null) {
			 computeCentroid();
		 }
		 return centroid;
	 }
	 
	 /**
	  * computes centroid (with respect to the distance function) and returns
	  * @return
	  */
	 public DataPoint computeCentroid() {
		 centroid = fctDistance.centroid(components);
		 return centroid;
	 }

	/**
	 * @param centroid  the centroid to set
	 * @uml.property  name="centroide"
	 */
	public void setCentroid(DataPoint point) {
		centroid = point;
	}
	
	
	@Override
	public boolean equals(Object arg0) {
		if (!(arg0 instanceof DataGroup)) {
			return false;
		}
		DataGroup g1 = (DataGroup) arg0;
		// quick removal of obvious case
		if (g1.components.size() != components.size()) {
			return false;
		}
		for (DataPoint p : components) {
			if (! g1.components.contains(p)) {
				return false;
			}
		}
		// this components are into g1, size is the same
		return true;
	}

	public String toString() {
		String s = new String();
		s += "[";
		for (DataPoint dp : components) {
			s += dp.toString();
			s += "\n";
		}
		s += "]";
		return s;
	}

	public int getSize() {
		return components.size();
	}
	
	public ArrayList<MkDataPoint> getComponents() {
		return components;
	}

}
