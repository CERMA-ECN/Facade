package fr.ecn.facade.core.model;

import java.io.Serializable;
import java.util.List;

import fr.ecn.common.geometry.Point;

public class ImageInfos implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String path = null;

	protected Coordinate latitude  = null;
	protected Coordinate longitude = null;
	
	protected Double orientation = null;
	
	protected List<Point> vanishingPoints = null;
	
	protected Double yHorizon = null;
	
	protected List<Face> faces = null;
	
	/**
	 * 
	 */
	public ImageInfos() {
		super();
	}

	public ImageInfos(String path) {
		super();
		this.path = path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
	
	/**
	 * @return the latitude
	 */
	public Coordinate getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Coordinate latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public Coordinate getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Coordinate longitude) {
		this.longitude = longitude;
	}

	public Double getOrientation() {
		return orientation;
	}
	
	public void setOrientation(Double orientation) {
		this.orientation = orientation;
	}

	/**
	 * @return the vanishingPoints
	 */
	public List<Point> getVanishingPoints() {
		return vanishingPoints;
	}

	/**
	 * @param vanishingPoints the vanishingPoints to set
	 */
	public void setVanishingPoints(List<Point> vanishingPoints) {
		this.vanishingPoints = vanishingPoints;
	}
	
	/**
	 * @return the yHorizon
	 */
	public Double getYHorizon() {
		return yHorizon;
	}

	/**
	 * @param yHorizon the yHorizon to set
	 */
	public void setYHorizon(Double yHorizon) {
		this.yHorizon = yHorizon;
	}

	/**
	 * @return the faces
	 */
	public List<Face> getFaces() {
		return faces;
	}

	/**
	 * @param faces the faces to set
	 */
	public void setFaces(List<Face> faces) {
		this.faces = faces;
	}

	@Override
	public String toString() {
		return "ImageInfos [path=" + path + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", orientation=" + orientation + "]";
	}
}
