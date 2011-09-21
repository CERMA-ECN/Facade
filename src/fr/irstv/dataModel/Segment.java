package fr.irstv.dataModel;

import android.graphics.Color;

public class Segment {

	/**
	 * @uml.property  name="beginPoint"
	 */
	private DataPoint beginPoint;
	
	/**
	 * @uml.property  name="endPoint"
	 */
	private DataPoint endPoint;
	
	/**
	 * @uml.property  name="vanishingPoint"
	 */
	private VanishingPoint vanishingPoint;
	
	/**
	 * @uml.property  name="hPoint"
	 */
	private DataPoint hpoint;
	
	/**
	 * @uml.property  name="color"
	 */
	private int color = Color.rgb(0, 0, 0);
	
	/**
	 * Constructeur d'un segment.
	 */
	public Segment(DataPoint begin, DataPoint end, VanishingPoint vanishingPoint){
		this.beginPoint = begin;
		this.endPoint = end;
		this.vanishingPoint = vanishingPoint;
		this.hpoint = calculateHPoint();
	
	}
	
	/**
	 * Constructeur d'un segment, avec la couleur en plus.
	 */
	public Segment(DataPoint begin, DataPoint end, VanishingPoint vanishingPoint, int color){
		this.beginPoint = begin;
		this.endPoint = end;
		this.vanishingPoint = vanishingPoint;
		this.hpoint = calculateHPoint();
		this.color = color;
	
	}
	
	public Segment(Segment seg) {
		beginPoint = new DataPoint(seg.beginPoint);
		endPoint = new DataPoint(seg.endPoint);
		vanishingPoint = seg.vanishingPoint;
		hpoint = calculateHPoint();
	}

	/**
	 * Getter of the property <tt>beginPoint</tt>
	 * @return  Returns the beginPoint.
	 * @uml.property  name="beginPoint"
	 */
	public DataPoint getBeginPoint() {
		return beginPoint;
	}

	/**
	 * Setter of the property <tt>beginPoint</tt>
	 * @param beginPoint  The beginPoint to set.
	 * @uml.property  name="beginPoint"
	 */
	public void setBeginPoint(DataPoint beginPoint) {
		this.beginPoint = beginPoint;
	}


	/**
	 * Getter of the property <tt>endPoint</tt>
	 * @return  Returns the endPoint.
	 * @uml.property  name="endPoint"
	 */
	public DataPoint getEndPoint() {
		return endPoint;
	}

	/**
	 * Setter of the property <tt>endPoint</tt>
	 * @param endPoint  The endPoint to set.
	 * @uml.property  name="endPoint"
	 */
	public void setEndPoint(DataPoint endPoint) {
		this.endPoint = endPoint;
	}


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


	/**
	 * Getter of the property <tt>hPoint</tt>
	 * @return  Returns the point.
	 * @uml.property  name="hPoint"
	 */
	public DataPoint getHPoint() {
		return hpoint;
	}

	/**
	 * Setter of the property <tt>hPoint</tt>
	 * @param hPoint  The point to set.
	 * @uml.property  name="hPoint"
	 */
	public void setHPoint(DataPoint point) {
		this.hpoint = point;
	}



	/**
	 * Getter of the property <tt>color</tt>
	 * @return  Returns the color.
	 * @uml.property  name="color"
	 */
	public int getColor() {
		return color;
	}

	/**
	 * Setter of the property <tt>color</tt>
	 * @param color  The color to set.
	 * @uml.property  name="color"
	 */
	public void setColor(int color) {
		this.color = color;
	}

		
	/**
	 * Calculate H point with the segment orientation.
	 */
	private DataPoint calculateHPoint() throws DimensionException{
		if((this.beginPoint.getDim() != 2) || (this.endPoint.getDim() != 2)){
			throw new DimensionException("Point dimention sould be 2");
		}
		DataPoint hPoint = new DataPoint(2);
		double xb = beginPoint.get(0);
		double yb = beginPoint.get(1);
		double xe = endPoint.get(0);
		double ye = endPoint.get(1);
		
		
		double xh = (ye-yb)*(xb*ye-yb*xe)/((xe-xb)*(xe-xb)+(ye-yb)*(ye-yb)); 
		double yh = -(xe-xb)*xh/(ye-yb);	
		
		hPoint.set(0, xh);
		hPoint.set(1, yh);
		
		return hPoint;
	}


	/**
	 * @uml.property  name="imageModel"
	 */
	private ImageModel imageModel;

	/**
	 * Getter of the property <tt>imageModel</tt>
	 * @return  Returns the imageModel.
	 * @uml.property  name="imageModel"
	 */
	public ImageModel getImageModel() {
		return imageModel;
	}

	/**
	 * Setter of the property <tt>imageModel</tt>
	 * @param imageModel  The imageModel to set.
	 * @uml.property  name="imageModel"
	 */
	public void setImageModel(ImageModel imageModel) {
		this.imageModel = imageModel;
	}

		


}
