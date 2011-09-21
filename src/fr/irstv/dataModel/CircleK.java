package fr.irstv.dataModel;


public class CircleK {

	/**
	 * @uml.property  name="radius"
	 */
	private double radius;

	/**
	 * @uml.property      name="center"
	 */
	private DataPoint center;

	/**
	 */
	public CircleK(DataPoint point){
		this.radius = calculateRadius(point);
		this.center = calculateCenter(point);
	
	}

	/**
	 * Getter of the property <tt>radius</tt>
	 * @return  Returns the radius.
	 * @uml.property  name="radius"
	 */
	public Double getRadius() {
		return radius;
	}

	/**
	 * Setter of the property <tt>radius</tt>
	 * @param radius  The radius to set.
	 * @uml.property  name="radius"
	 */
	public void setRadius(Double radius) {
		this.radius = radius;
	}

	/**
	 * Getter of the property <tt>center</tt>
	 * @return  Returns the center.
	 * @uml.property  name="center"
	 */
	public DataPoint getCenter() {
		return center;
	}

	/**
	 * Setter of the property <tt>center</tt>
	 * @param center  The center to set.
	 * @uml.property  name="center"
	 */
	public void setCenter(DataPoint center) {
		this.center = center;
	}

		
	/**
	 */
	private double calculateRadius(DataPoint point){
		
		double radius = 0;
		
		for(int i=0; i<point.getDim(); i++)
		{
			radius += Math.pow(point.get(i),2);
		}
		
		radius = Math.sqrt(radius)/2.0;
		
		return radius;
	
	}

	/**
	 */
	private DataPoint calculateCenter(DataPoint point){
		DataPoint center = new DataPoint(point);
	
		for(int i=0; i<point.getDim(); i++)
		{
			center.set(i, point.get(i)/2.0);
		}
		return center;
	}

	/**
	 * @uml.property  name="vanishingPoint"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="circleK:fr.irstv.dataModel.VanishingPoint"
	 */
	private VanishingPoint vanishingPoint = null;

	/**
	 * Getter of the property <tt>vanishingPoint</tt>
	 * @return  Returns the vanishingPoint.
	 * @uml.property  name="vanishingPoint"
	 */
	public VanishingPoint getVanishingPoint() {
		return vanishingPoint;
	}

	/**
	 * Setter of the property <tt>vanishingPoint</tt>
	 * @param vanishingPoint  The vanishingPoint to set.
	 * @uml.property  name="vanishingPoint"
	 */
	public void setVanishingPoint(VanishingPoint vanishingPoint) {
		this.vanishingPoint = vanishingPoint;
	}

}
