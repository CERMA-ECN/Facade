package fr.ecn.facade.core.straightening;

import fr.ecn.common.image.ColorImage;

import Jama.Matrix;

public class ImageStraightening {
	
	protected ColorImage source;
	protected ColorImage result;
	
	public ImageStraightening(ColorImage image) {
		this.source = image;
	}
	/**
	 * Computes and displays the result of the homography h on the image attribute.
	 * @param h, the homography
	 */
	public void straightenUp(Homography h) {
		// inits
		Matrix revHomography = h.reverseSquareHomography;
		int width = (int) Math.abs(Math.ceil(h.endPoints.get(3).getX()-h.endPoints.get(0).getX()));
		int height = (int) Math.abs(Math.ceil(h.endPoints.get(1).getY()-h.endPoints.get(0).getY()));
		
		result = new ColorImage(width, height);
		
		// Pixels vectors
		Matrix X = new Matrix(3,1);
		Matrix Y = new Matrix(3,1);
		X.set(2, 0, 1);
		
		for (int i=0;i<width;i++) {
			for (int j=0;j<height;j++) {
				// Pixels' coordinates
				X.set(0, 0, i);
				X.set(1, 0, j);
				// reverse side transformation
				Y = revHomography.times(X);
				int pixel = source.getPixel((int) Math.floor(Y.get(0, 0)/Y.get(2, 0)),((int) Math.floor(Y.get(1, 0)/Y.get(2, 0))));
				result.setPixel(i, j, pixel);
			}
		}
	}

}
