package fr.ecn.facade.core.utils;

import java.io.File;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.GpsDescriptor;
import com.drew.metadata.exif.GpsDirectory;

import fr.ecn.common.core.imageinfos.Coordinate;
import fr.ecn.common.core.imageinfos.ImageInfos;

/**
 * Helper class for reading Exif data from a JPEG file
 *
 */
public class ExifReader {
	
	protected ImageInfos image;
	
	protected GpsDescriptor gpsDescriptor;
	
	/**
	 * Creates a new ExifReader for an image
	 * 
	 * @param image The image for witch we want to read the exif
	 * @throws JpegProcessingException
	 */
	public ExifReader(ImageInfos image) throws JpegProcessingException {
		this.image = image;
		
		//Create a GpsDescriptor
		Metadata metadata = JpegMetadataReader.readMetadata(new File(this.image.getPath()));
		
		Directory gpsDirectory = metadata.getDirectory(GpsDirectory.class);
		
		this.gpsDescriptor = new GpsDescriptor(gpsDirectory);
	}
	
	/**
	 * Convert GPS coordinates from hour format (xx"xx'xx.xx) to the decimal one xx.xx
	 * 
	 * @param degree
	 * @return
	 */
	public double convertHourToDecimal(String degree) {
		// Select the degrees, the minutes and the seconds and put it in an array
		String[] strArray=degree.split("[\"']");
		// Sum the terms, converting it into decimal
		return Double.parseDouble(strArray[0])+Double.parseDouble(strArray[1])/60+Double.parseDouble(strArray[2])/3600;
	}
	
	/**
	 * @return the absolute longitude (can be negative) from the Exif of a JPEG picture
	 * @throws ExifReaderException 
	 */
	public double getLongitude() throws ExifReaderException {
		// Get the longitude data, in degrees, minutes and seconds and convert it into decimal
		Double longitude = this.convertHourToDecimal(this.getLongitudeDescription());
		
		// All the numbers will be positive, so get the longitude reference, and if it's oriented west "W", return the opposite
		if (getLongitudeRef().equals("W")) {
			longitude =- longitude;
		}
		return longitude;
	}
	
	/**
	 * @return the longitude orientation (W or E)
	 * @throws ExifReaderException 
	 */
	public String getLongitudeRef() throws ExifReaderException {
		try {
			return this.gpsDescriptor.getDescription(GpsDirectory.TAG_GPS_LONGITUDE_REF);
		} catch (MetadataException e) {
			throw new ExifReaderException("Unknown longitude ref", e);
		}
	}
	
	/**
	 * @return the longitude as a string
	 * @throws ExifReaderException
	 */
	public String getLongitudeDescription() throws ExifReaderException {
		String longitudeDescription;
		try {
			longitudeDescription = this.gpsDescriptor.getGpsLongitudeDescription();
		} catch (MetadataException e) {
			throw new ExifReaderException("Unknown longitude", e);
		}
		
		if (longitudeDescription == null) {
			throw new ExifReaderException("Unknown longitude");
		}
		
		return longitudeDescription;
	}
	
	/**
	 * @return the absolute latitude (can be negative) from the Exif of a JPEG picture
	 * @throws ExifReaderException 
	 */
	public double getLatitude() throws ExifReaderException {
		// Get the latitude data, in degrees, minutes and seconds and convert it into decimal
		double latitude = this.convertHourToDecimal(this.getLatitudeDescription());
		// All the numbers will be positive, so get the latitude reference, and if it's oriented south "S", return the opposite
		if (getLatitudeRef().equals("S")) {
			latitude =- latitude;
		}
		return latitude;
	}
	
	/**
	 * @return the latitude orientation (N or S)
	 * @throws ExifReaderException 
	 */
	public String getLatitudeRef() throws ExifReaderException {
		try {
			return this.gpsDescriptor.getDescription(GpsDirectory.TAG_GPS_LATITUDE_REF);
		} catch (MetadataException e) {
			throw new ExifReaderException("Unknown latitude ref", e);
		}
	}
	
	/**
	 * @return the latitude as a string
	 * @throws ExifReaderException
	 */
	public String getLatitudeDescription() throws ExifReaderException {
		String latitudeDescription;
		try {
			latitudeDescription = this.gpsDescriptor.getGpsLatitudeDescription();
		} catch (MetadataException e) {
			throw new ExifReaderException("Unknown latitude", e);
		}
		
		if (latitudeDescription == null) {
			throw new ExifReaderException("Unknown latitude");
		}
		
		return latitudeDescription;
	}
	
	/**
	 * Get the latitude and longitude from the Exif and save them in the ImageInfos object.
	 * 
	 * @param image
	 */
	public static void readExif(ImageInfos image) {
		try {
			ExifReader reader = new ExifReader(image);
			
			try {
				image.setLatitude(Coordinate.fromString(reader.getLatitudeDescription(), reader.getLatitudeRef()));
			} catch (ExifReaderException e) {
				// Latitude can't be read
				e.printStackTrace();
			}
			
			try {
				image.setLongitude(Coordinate.fromString(reader.getLongitudeDescription(), reader.getLongitudeRef()));
			} catch (ExifReaderException e) {
				// Logitude can't be read
				e.printStackTrace();
			}
		} catch (JpegProcessingException e) {
			//Exif can't be read so just log the exception
			e.printStackTrace();
		}
	}
}