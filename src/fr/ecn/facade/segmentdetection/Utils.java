package fr.ecn.facade.segmentdetection;

import jjil.core.Error;
import jjil.core.Gray8Image;
import fr.ecn.facade.core.image.filters.ByteConvolve;
import fr.ecn.facade.core.image.ByteImage;

/**
 * Class implementing the useful tools for ImageSegment class.
 *
 * @author Leo COLLET, Cedric TELEGONE, Ecole Centrale Nantes
 * @version 1.0
 */

public class Utils {

	/**
	 * X and Y gradients computation
	 *
	 * @param 	image	the ImagePlus object which gradients must be computed
	 * @return			an ImagePlus array of length 2 containing the X and Y gradient images of image
	 */
	public static ByteImage[] getGradients(Gray8Image image){
		/**
		 *  Initialize gradient matrixes
		 */
		ByteImage gradXimage = null;
		ByteImage gradYimage = null;
		/**
		 *  Initialize normalized convolution kernels
		 */
		//float coeff = (float) 0.5;
		float coeff = (float) 0.3333;
		/*float[] kerX = {0, 0, 0, -coeff, 0, coeff, 0, 0, 0};
		float[] kerY = {0, -coeff, 0, 0, 0, 0, 0, coeff, 0};*/
		
		float[] kerX = {-coeff, 0, coeff, -coeff, 0, coeff, -coeff, 0, coeff};
		ByteConvolve convolverX = new ByteConvolve(kerX, 3, 3);
		try {
			convolverX.push(image);
			gradXimage = (ByteImage) convolverX.getFront();
		} catch (Error e) {
			// Shouldn't append
			e.printStackTrace();
		}
		
		float[] kerY = {-coeff, -coeff, -coeff, 0, 0, 0, coeff, coeff, coeff};
		ByteConvolve convolverY = new ByteConvolve(kerY, 3, 3);
		try {
			convolverY.push(image);
			gradYimage = (ByteImage) convolverY.getFront();
		} catch (Error e) {
			// Shouldn't append
			e.printStackTrace();
		}
		
		ByteImage[] result = {
				gradXimage,
				gradYimage,
		};
		//gradXimage.show();
		//gradYimage.show();
		return result;
	}

	/**
	 * Calculate the linear regression coefficients in a Segment context, viewed as a set of points.
	 * Based upon a classical linear regression method.
	 * From Introduction aux Probabilites et a la Statistique; BRILLOUET-BELLUOT, Nicole; Ecole Centrale Nantes; 2008
	 *
	 * @param 		s	the Segment to be processed
	 * @return			a Point array containing start and end points of Segment s
	 */
	public static void linRegSegment(Segment s){

		/**
		 * Linear regression coefficients
		 * a, b
		 * y = a*x + b
		 */
		double a, b;

		/**
		 * 'a' and 'b' coefficients are computed this way:
		 * s = {(Xi,Yi)}, i=1..n
		 * X = mean(Xi); Y = mean(Yi)
		 * num = sum[(Xi - X)*(Yi-Y)], i=1..n
		 * den = sum[(Xi - X)^2], i=1..n
		 * a = num / den
		 * b = Y - a*X
		 */
		double num 	= 0;
		double den 	= 0;
		double X	= 0;
		double Y	= 0;

		/**
		 * To get integer values for start point and end point, we keep in memory Xmin and Xmax.
		 * Startpoint will be (Xmin, a*Xmin + b)
		 * Endpoint will be (Xmax, a*Xmax + b)
		 */
		int Xmin = s.getPoints().get(0).getX();
		int Xmax = s.getPoints().get(0).getX();

		/**
		 * Calculate X and Y
		 */
		for (int i=0; i<s.getPoints().size(); i++){
			X = X + s.getPoints().get(i).getX();
			Y = Y + s.getPoints().get(i).getY();
			if (s.getPoints().get(i).getX() < Xmin){
				Xmin = s.getPoints().get(i).getX();
			} else if (s.getPoints().get(i).getX() > Xmax){
				Xmax = s.getPoints().get(i).getX();
			}
		}
		X = X / s.getPoints().size();
		Y = Y / s.getPoints().size();

		/**
		 * Calculate num and den
		 */
		for (int i=0; i<s.getPoints().size(); i++){
			num = num + (s.getPoints().get(i).getX() - X) * (s.getPoints().get(i).getY() - Y);
			den = den + (s.getPoints().get(i).getX() - X) * (s.getPoints().get(i).getX() - X);
		}

		/**
		 * Calculate a and b
		 */
		a = num / den;
		b = Y - (a * X);

		/**
		 * Calculate and modify start and end points
		 */
		Double Ymin = new Double(a*Xmin + b);
		Double Ymax = new Double(a*Xmax + b);
		s.setStartPoint(new Point(Xmin, Ymin.intValue()));
		s.setEndPoint(new Point(Xmax, Ymax.intValue()));
	}
}
