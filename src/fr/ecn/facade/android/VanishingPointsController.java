package fr.ecn.facade.android;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import android.graphics.Bitmap;
import android.util.Log;

import jjil.android.RgbImageAndroid;
import jjil.core.Image;
import fr.ecn.common.geometry.Point;
import fr.ecn.facade.android.utils.ImageLoader;
import fr.ecn.facade.android.utils.ImageUtils;
import fr.ecn.facade.core.model.ImageInfos;
import fr.ecn.facade.segmentdetection.ImageSegment;
import fr.ecn.facade.segmentdetection.Segment;
import fr.irstv.dataModel.CircleKDistance;
import fr.irstv.kmeans.CleaningDataGroups;
import fr.irstv.kmeans.DataGroup;
import fr.irstv.kmeans.DataMk;
import fr.irstv.kmeans.RanSac;

public class VanishingPointsController {
	
	/**
	 * A callable that create a VansishingPointsController Object
	 * 
	 * @author jerome
	 *
	 */
	public static class VanishingPointsCallable implements Callable<VanishingPointsController> {
		
		protected ImageInfos imageInfos;
		
		/**
		 * @param imageInfos
		 */
		public VanishingPointsCallable(ImageInfos imageInfos) {
			super();
			this.imageInfos = imageInfos;
		}

		public VanishingPointsController call() throws Exception {
			return new VanishingPointsController(imageInfos);
		}
		
	}
	
	/**
	 * Bitmap version of the image (will be used for display)
	 */
	protected Bitmap bitmap;
	
	protected Map<Integer, List<Segment>> segments;
	protected DataGroup[] groups;
	
	protected boolean[] selectedGroups;

	public VanishingPointsController(ImageInfos imageInfos) {
		Bitmap bitmap = ImageLoader.loadResized(imageInfos.getPath(), 600);
		
		Image image = ImageUtils.toGray8(RgbImageAndroid.toRgbImage(bitmap));
		
		//We don't need the bitmap anymore
		bitmap.recycle();
		bitmap = null;
		
		this.computeSegments(image);
		
		this.computeGroups();
		
		this.bitmap = ImageUtils.toBitmap(image);
	}
	
	public void computeSegments(Image image) {
		Log.i("Ombre", "Starting segments computation");
		long time = System.nanoTime();
		
		ImageSegment is = new ImageSegment(image);
		is.getLargeConnectedEdges(false, 8);
		this.segments = is.getFinalSegmentMap();
		
		Log.i("Ombre", "Segments computation done in " + (System.nanoTime() - time));
	}
	
	public void computeGroups() {
		Log.i("Ombre", "Starting groups computation");
		long time = System.nanoTime();
		
		CircleKDistance fd = new CircleKDistance();

		DataMk dataSet = new DataMk(this.segments);
		
		// here we launch the real job
		RanSac r = new RanSac(6,dataSet,fd);
		// param init
		r.setMaxSample(25);
		r.setSigma(20d);
		r.setStopThreshold(0.05d);
		r.go();
		
		//Cleaning the groups
		this.groups = new CleaningDataGroups().clean(r.getGroups());
		
		//Create the selectedGroups array
		this.selectedGroups = new boolean[this.groups.length];
		for (int i=0; i< this.selectedGroups.length; i++) {
			this.selectedGroups[i] = true;
		}
		
		Log.i("Ombre", "Groups computation done in " + (System.nanoTime() - time));
	}

	/**
	 * @return the bitmap
	 */
	public Bitmap getBitmap() {
		return bitmap;
	}
	
	/**
	 * @return the groups
	 */
	public DataGroup[] getGroups() {
		return groups;
	}
	
	public void setGroupSelected(int groupId, boolean selected) {
		this.selectedGroups[groupId] = selected;
	}
	
	public boolean isGroupSelected(int groupId) {
		return this.selectedGroups[groupId];
	}

	public List<Point> getSelectedPoints() {
		List<Point> list = new ArrayList<Point>();
		
		for (int i=0; i<this.selectedGroups.length; i++) {
			if (this.selectedGroups[i]) {
				list.add(new Point(this.groups[i].getCentroid().get(0)*2, this.groups[i].getCentroid().get(1)*2));
			}
		}
		
		return list;
	}

}
