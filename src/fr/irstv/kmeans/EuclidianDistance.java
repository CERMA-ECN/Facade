package fr.irstv.kmeans;


import java.util.List;

import fr.irstv.dataModel.DataPoint;

public class EuclidianDistance implements DataDistance {

	public double distance(DataPoint p1, DataPoint p2) {
		int i;
		double d=0d;
		for (i=0 ; i<p1.getDim() ; i++) {
			double dx = p1.get(i)-p2.get(i);
			d += dx*dx;
		}
		return Math.sqrt(d);
	}
	
	public DataPoint centroid(List<? extends DataPoint> l) {
		DataPoint g = new DataPoint(l.get(0).getDim());
		g.initZero();
		
		for (DataPoint gp : l) {
			g.addCoord(gp);
		}
		g.scalDiv((double)l.size());
		return g;
	}
}
