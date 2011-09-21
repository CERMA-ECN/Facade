/**
 *
 */
package fr.irstv.dataModel;

import java.util.LinkedList;
import java.util.List;

import Jama.Matrix;
import fr.irstv.kmeans.EuclidianDistance;

/**
 * @author moreau
 *
 */
public class CircleKDistance extends EuclidianDistance {
	
	/**
	 * computation of the centroid is done here; in fact it is
	 * the computation of the new circle
	 *
	 * @see fr.irstv.kmeans.DataDistance#centroid(java.util.List)
	 */
	public DataPoint centroid(List<? extends DataPoint> l) {
		// for debug
		LinkedList<DataPoint> listH = new LinkedList<DataPoint>();

		// B matrix creation
		Matrix B = new Matrix(l.size(),4);
		for (int i=0 ; i<l.size() ; i++) {
			DataPoint h = l.get(i);
			listH.add(h);
			double xh = h.get(0);
			double yh = h.get(1);
			B.set(i, 0, xh*xh+yh*yh);
			B.set(i, 1, xh);
			B.set(i, 2, yh);
			B.set(i, 3, 1d);
		}

		// compute SVD of B matrix
		if (l.size() < 3) {
			System.out.println("probably not enough points for B");
		}
		
		Matrix V = new SingularValueDecomposition(B).getV();

		double a = V.get(0,3);
		double b1 = V.get(1,3);
		double b2 = V.get(2,3);
		double c = V.get(3,3);
		double z1 = -b1/2d/a;
		double z2 = -b2/2d/a;
		double r = Math.sqrt(z1*z1+z2*z2-c/a);

		DataKCircle cx = new DataKCircle(2);
		cx.set(0,z1);
		cx.set(1,z2);
		cx.setRadius(r);

		return cx;
	}

	/**
	 * the distance is always seen as the distance to the circle. Do not forget
	 * that here the second point is always the center of the circle. This does
	 * not procide us with the distance. The circle K is derived class from
	 * the DataPoint notion. It is a virtual DataPoint that contains more
	 * information, i.e. the radius of the circle

	 * @see fr.irstv.kmeans.DataDistance#distance(fr.irstv.dataModel.DataPoint, fr.irstv.dataModel.DataPoint)
	 */
	public double distance(DataPoint p1, DataPoint p2) {
		// is p2 a CircleK
		double d;
		try {
			DataKCircle c = (DataKCircle) p2;
			// distance to the circle = distance to the center - radius
			d = Math.abs(super.distance(p1, p2)-c.getRadius());
		}
		catch (ClassCastException e) {
			e.printStackTrace();
			return -1d;
		}
		return d;
	}
}
