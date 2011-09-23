package fr.ecn.facade.android;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import android.graphics.Bitmap;

import fr.ecn.android.image.BitmapConvertor;
import fr.ecn.common.geometry.Point;
import fr.ecn.common.image.ColorImage;
import fr.ecn.facade.android.utils.ImageLoader;
import fr.ecn.facade.core.model.Face;
import fr.ecn.facade.core.model.ImageInfos;
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
		Bitmap sourceBitmap = ImageLoader.loadResized(imageInfos.getPath(), 600);
		
		ColorImage sourceImage = BitmapConvertor.bitmapToImage(sourceBitmap);
		sourceBitmap.recycle();
		
		//Get the first (and only) face
		Face face = imageInfos.getFaces().get(0);
		List<Point> edgesPoints = Arrays.asList(face.getPoints());
		
		ColorImage resultImage = new StraighteningFunction(edgesPoints, sourceImage, 40, 2, 10).getResult();
		
		this.resultBitmap = BitmapConvertor.imageToBitmap(resultImage);
	}

	/**
	 * @return the resultBitmap
	 */
	public Bitmap getResultBitmap() {
		return resultBitmap;
	}
	
}
