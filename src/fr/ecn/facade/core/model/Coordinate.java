/**
 * 
 */
package fr.ecn.facade.core.model;

import java.io.Serializable;

/**
 * @author jerome
 *
 */
public class Coordinate implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected double degrees;
	protected double minutes;
	protected double seconds;
	
	protected String ref;
	
	/**
	 * @param degrees
	 * @param minutes
	 * @param seconds
	 * @param ref
	 */
	public Coordinate(double degrees, double minutes, double seconds, String ref) {
		super();
		this.degrees = degrees;
		this.minutes = minutes;
		this.seconds = seconds;
		this.ref = ref;
	}

	/**
	 * @return the ref
	 */
	public String getRef() {
		return ref;
	}

	/**
	 * @param ref the ref to set
	 */
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	/**
	 * Return the decimal degrees representation of this coordinate.
	 * 
	 * @return
	 */
	public double getDecimalDegrees() {
		double degrees = this.degrees + this.minutes/60 + this.seconds/3600;
		if (this.ref.equals("W") || this.ref.equals("S")) {
			degrees = -degrees;
		}
		return degrees;
	}
	
	/**
	 * Return the DMS representation of this coordinate as a string
	 * 
	 * @return
	 */
	public String getDMSString() {
		return (int)this.degrees + "°" + (int)this.minutes + "'" + this.seconds + "\"";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getDMSString() + ref;
	}
	
	/**
	 * Parse the specified string as a Coordinate object.
	 * 
	 * @param coordinate the string representation of the coordinate.
	 * @param reference the reference for this coordinate
	 * @return the Coordinate object represented by this string
	 */
	public static Coordinate fromString(String coordinate, String reference) {
		String[] coordinateParts = coordinate.split("[°'\"]");
		
		int length = coordinateParts.length;
		
		if (coordinateParts[length-1].equals("")) {
			length -= 1;
		}
		
		switch (length) {
		case 1:
			return new Coordinate(Double.parseDouble(coordinateParts[0]), 0, 0, reference);
		case 2:
			return new Coordinate(Double.parseDouble(coordinateParts[0]), Double.parseDouble(coordinateParts[1]), 0, reference);
		case 3:
			return new Coordinate(Double.parseDouble(coordinateParts[0]), Double.parseDouble(coordinateParts[1]), Double.parseDouble(coordinateParts[2]), reference);
		default:
			throw new IllegalArgumentException("Invalid coordinate String");
		}
	}
	
	public static Coordinate fromDouble(double coordinate, String reference) {
		double degrees = (int) coordinate;
		coordinate -= degrees;
		
		coordinate *= 60;
		double minutes = (int) coordinate;
		coordinate -= minutes;
		
		coordinate *= 60;
		double seconds = coordinate;
		
		return new Coordinate(degrees, minutes, seconds, reference);
	}
}
