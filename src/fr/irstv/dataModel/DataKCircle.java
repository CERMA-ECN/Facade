package fr.irstv.dataModel;

public class DataKCircle extends DataPoint {

	/**
	 * radius
	 */
	private double radius;
	
	/**
	 * constructor (only including dimension)
	 * @param dim
	 */
	public DataKCircle(int dim) {
		super(dim);
	}
	
	/**
	 * copy constructor
	 * @param copyMe the circle to be copied
	 */
	public DataKCircle(DataKCircle copyMe) {
		super(copyMe);
		radius = copyMe.radius;
	}

	public double getRadius() {
		return radius;
	}
	
	public void setRadius(double r) {
		radius = r;
	}
}
