package fr.ecn.facade.android;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import android.graphics.Bitmap;

import fr.ecn.common.android.image.BitmapConvertor;
import fr.ecn.common.android.image.BitmapLoader;
import fr.ecn.common.core.geometry.Point;
import fr.ecn.common.core.image.ColorImage;
import fr.ecn.common.core.imageinfos.Face;
import fr.ecn.common.core.imageinfos.ImageInfos;
import fr.ecn.facade.core.straightening.StraighteningFunction;

public class ResultController {
	
	public static class ResultCallable implements Callable<ResultController> {
		
		private ImageInfos imageInfos;

		/**
		 * @param imageInfos
		 */
		public ResultCallable(ImageInfos imageInfos) {
			super();
			this.imageInfos = imageInfos;
		}

		public ResultController call() throws Exception {
			return new ResultController(this.imageInfos);
		}
		
	}
	
	private Bitmap resultBitmap;

	public ResultController(ImageInfos imageInfos) {
		BitmapLoader.ResizedBitmap resizedBitmap = BitmapLoader.loadResized(imageInfos.getPath(), 1200);
		
		Bitmap sourceBitmap = resizedBitmap.bitmap;
		float scale = resizedBitmap.scale;
		
		ColorImage sourceImage = BitmapConvertor.bitmapToImage(sourceBitmap);
		sourceBitmap.recycle();
		
		//Get the first (and only) face
		Face face = imageInfos.getFaces().get(0);

		List<Point> resizedPoints = face.getPoints();
		List<Point> edgesPoints = new ArrayList<Point>(resizedPoints.size());

		for (Point point : resizedPoints) {
			edgesPoints.add(new Point(point.getX() * scale, point.getY() * scale));
		}
		
		//Get a vanishing point
		Point vanishingPoint = imageInfos.getVanishingPoints().get(0);
		
//		double width = Distance.distance(finalPoints[0], finalPoints[1]);
//		double height = Distance.distance(finalPoints[0], finalPoints[3]);
		
//		double ratio = height/width;
		
		ColorImage resultImage = new StraighteningFunction(edgesPoints, sourceImage, 40, vanishingPoint, 10).getResult();
		
		this.resultBitmap = BitmapConvertor.imageToBitmap(resultImage);
	}

	/**
	 * @return the resultBitmap
	 */
	public Bitmap getResultBitmap() {
		return resultBitmap;
	}
	
}
