package fr.ecn.facade.segmentdetection;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.util.Log;

import jjil.android.RgbImageAndroid;
import jjil.core.Gray8Image;
import jjil.core.Image;
import fr.ecn.facade.android.utils.ImageLoader;
import fr.ecn.facade.android.utils.ImageUtils;
import fr.ecn.facade.core.image.ByteImage;
import fr.ecn.facade.core.image.FloatImage;

/**
 * Class implementing the extraction of the main edges of an image.
 *
 * @author Leo COLLET, Cedric TELEGONE, Ecole Centrale Nantes
 * @version 1.0
 */

public class ImageSegment {

	/**
	 * Attributes
	 */
	protected Gray8Image baseImage;
	protected Map<Integer, List<Segment>> finalSegmentMap;
	
	/**
	 * Constructor using an Image object
	 * 
	 * @param image the image from which the segments will be extracted
	 */
	public ImageSegment(Image image){
		this.baseImage = ImageUtils.toGray8(image);
	}
	
	/**
	 * Constructor using a path to an image
	 * 
	 * @param path the path to the image from which the segments will be extracted
	 */
	public ImageSegment(String pathToImage){
		Bitmap bitmap = ImageLoader.loadResized(pathToImage, 600);

		Image image = RgbImageAndroid.toRgbImage(bitmap);

		this.baseImage = ImageUtils.toGray8(image);
	}

	/**
	 * Method computing relevant edges in an grayscale image.
	 *
	 * @param	ALLOW_NOISE_REDUCTION	A boolean value set to TRUE if a noise reduction is necessary.
	 * @param	num_dir					Number of computed directions
	 */
	public void getLargeConnectedEdges(boolean ALLOW_NOISE_REDUCTION, int num_dir){

		int w = this.baseImage.getWidth();
		int h = this.baseImage.getHeight();
		Gray8Image imageProcessed = this.baseImage;
		double minLength = 0.025*Math.sqrt(w*w + h*h);

		/*if (ALLOW_NOISE_REDUCTION){
			/**
			 *  Get the processed image 2D matrix.
			 */
		/*
		int[][] imageArray = this.baseImage.getProcessor().getIntArray();
			/**
			 *  Calculate the gaussian smoothed image.
			 */
		/*
			int[][] imageSmoothed = GaussianSmooth.smooth(imageArray, w, h, 7, 1.5);
			for (int i=0; i<w; i++){
				for (int j=0; j<h; j++){
					imageProcessed.getProcessor().putPixel(i, j, imageSmoothed[i][j]);
				}
			}
		}
		*/

		/**
		 *  Calculate the image X and Y gradients.
		 */
		Log.i("Ombre", "Starting gradients computation");
		long time = System.nanoTime();
		
		ByteImage[] gradients = Utils.getGradients(imageProcessed);
		
		Log.i("Ombre", "Gradients computation done in " + (System.nanoTime() - time));

		/**
		 *  Calculate the Canny Deriche filtered image
		 */
		float alpha = (float) 0.5;
		
		Log.i("Ombre", "Starting cannyDeriche computation");
		time = System.nanoTime();
		
		FloatImage imageCanny = new CannyDericheFilter().processDeriche(this.baseImage, alpha);
		
		Log.i("Ombre", "CannyDeriche computation done in " + (System.nanoTime() - time));
		
		int wCanny = imageCanny.getWidth();
		int hCanny = imageCanny.getHeight();
		/**
		 *  Eliminate generated horizontal and vertical border edges, thickness eHMax and eVMax pixels
		 *  TODO Automatic detection of relevant thickness elimination?
		 */
		int eHMax = 4;
		for (int i=0; i<wCanny; i++){
			for (int e=0; e<eHMax; e++){
				imageCanny.setPixel(i, e, 0);
				imageCanny.setPixel(i, hCanny-1-e, 0);
			}
		}
		int eVMax = 10;
		for (int j=0; j<hCanny; j++){
			for (int e=0; e<eVMax; e++){
				imageCanny.setPixel(e, j, 0);
				imageCanny.setPixel(wCanny-1-e, j, 0);
			}
		}

		/**
		 * Cross-compare X and Y gradients to Canny-Deriche filtered image.
		 * Select, in ipGradX and ipGradY, the pixels (x,y) where ipCanny(x,y)>0.
		 * Return two arrays selectX and selectY.
		 * Compute Arctan(selectY / selectX). In case selectX[i] equals to 0, set Arctan value to a previously defined MAX_ANGLE_VALUE equal to Pi/2 - epsilon.
		 *
		 */
		final double EPSILON = 0.0000000001;
		final double MAX_ANGLE_VALUE = Math.PI/2 - EPSILON;
		
		List<Point>[] direction = (List<Point>[]) new List[num_dir];
		for (int i=0; i<num_dir; i++) {
			direction[i] = new LinkedList<Point>();
		}
		
		for (int i = 0; i < wCanny; i++) {
			for (int j = 0; j < hCanny; j++) {
				if (imageCanny.getPixel(i, j) > 0) {
					int vX = (int) gradients[0].getPixel(i, j);
					int vY = (int) gradients[1].getPixel(i, j);

					int angle;
					double a;
					if (vX != 0) {
						a = Math.atan(vY / vX);
					} else {
						a = MAX_ANGLE_VALUE;
					}
					/**
					 * Calculate angle value in [|0;num_dir - 1|]
					 */
					angle = (int) Math.floor((a / Math.PI + 0.5) * num_dir);
					/**
					 * Add point indexes(k), which is on an edge of direction k, into direction hashmap.
					 */
					direction[angle].add(new Point(i, j));// i = horizontal coordinate ; j = vertical coordinate
				}
			}
		}
		
		//We have no longer uses of gradients so let's let the gc collect them, same for cannyDeriche.
		gradients  = null;
		imageCanny = null;

		/**
		 * Initialize final segment map
		 */
		this.finalSegmentMap = new HashMap<Integer, List<Segment>>(num_dir);
		for (int key=0; key<num_dir; key++){
			this.finalSegmentMap.put(key, new LinkedList<Segment>());
		}

		/**
		 * Compute relevant information for each direction in [|0, num_dir-1|]
		 */
		int[][] dir_im;
		for (int k=0; k<num_dir; k++) {
			dir_im = new int[this.baseImage.getHeight()][this.baseImage.getWidth()];

			/**
			 * For direction k, find points on k-oriented edges
			 * Put 1 in matrix dir_im where a point is on a k-oriented edge
			 */
			for (Point p : direction[k]) {
				dir_im[p.getY()][p.getX()] = 1;
			}

			/**
			 * Find 8-connected components in matrix dir_im
			 */
			ConnectedObjects c = LabelImage.labelImage(dir_im,num_dir); // Get the connected points
			
			/**
			 * Initialize classified segments array
			 */
			Segment[] classifiedSegments = new Segment[c.getNum_labels()];
			for (int id=0; id<classifiedSegments.length; id++){
				classifiedSegments[id] = new Segment();
			}
			/**
			 * Get the number of pixels in each edge
			 */
			for (Point p : direction[k]) {
				int id = c.getMatrix(p.getY(), p.getX());
				classifiedSegments[id].addPoint(p);
			}
			
			/**
			 * Add eligible edges to finalSegmentMap
			 */
			for (int id = 0; id < classifiedSegments.length; id++) {
				if (classifiedSegments[id].getPoints().size() >= minLength
						&& classifiedSegments[id].computeStartEndPoints() >= 500) {
					this.finalSegmentMap.get(k).add(classifiedSegments[id]);
				}
			}
		}

	}

	/**
	 * Get baseImage value
	 * @return	ImagePlus this.baseImage
	 */
	public Image getBaseImage() {
		return baseImage;
	}
	
	/**
	 * Get finalSegmentMap value
	 * @return	HashMap<Integer,Vector<Segment>> this.finalSegmentMap
	 */
	public Map<Integer, List<Segment>> getFinalSegmentMap() {
		return finalSegmentMap;
	}



}
