package fr.irstv.dataModel;


//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.LinkedList;

//import javax.media.jai.PlanarImage;

/**
 */
public class WorkingArea {

	/**
	 * @uml.property  name="size"
	 */
	private int[] size = new int[2];

	/**
	 * @uml.property   name="zeroPoint"
	 */
	private DataPoint zeroPoint = new DataPoint(2);

	/**
	 * @uml.property   name="imageModel"
	 * @uml.associationEnd   multiplicity="(1 1)" inverse="workingArea:fr.irstv.dataModel.ImageModel"
	 */
//	private PlanarImage imageModel = null;

	/**
	 */
//	public WorkingArea(PlanarImage image, LinkedList<VanishingPoint> vanishingPointList){
//		this.imageModel = image;
//		this.size = calculateWorkingAreaSize(image, vanishingPointList);
//		this.zeroPoint = calculateZeroPoint(calculateWorkingAreaCoordinates(image, vanishingPointList));
//	}

	public WorkingArea(){

	}

	/**
	 * Getter of the property <tt>size</tt>
	 * @return  Returns the size.
	 * @uml.property  name="size"
	 */
	public int[] getSize() {
		return size;
	}

	/**
	 * Setter of the property <tt>size</tt>
	 * @param size  The size to set.
	 * @uml.property  name="size"
	 */
	public void setSize(int[] size) {
		this.size = size;
	}

	/**
	 * Getter of the property <tt>zeroPoint</tt>
	 * @return  Returns the zeroPoint.
	 * @uml.property  name="zeroPoint"
	 */
	public DataPoint getZeroPoint() {
		return zeroPoint;
	}

	/**
	 * Setter of the property <tt>zeroPoint</tt>
	 * @param zeroPoint  The zeroPoint to set.
	 * @uml.property  name="zeroPoint"
	 */
	public void setZeroPoint(DataPoint zeroPoint) {
		this.zeroPoint = zeroPoint;
	}

	/**
	 * Getter of the property <tt>imageModel</tt>
	 * @return  Returns the imageModel.
	 * @uml.property  name="imageModel"
	 */
//	public PlanarImage getPlanarImage() {
//		return imageModel;
//	}

	/**
	 * Setter of the property <tt>imageModel</tt>
	 * @param imageModel  The imageModel to set.
	 * @uml.property  name="imageModel"
	 */
//	public void setPlanarImage(PlanarImage imageModel) {
//		this.imageModel = imageModel;
//	}

	/**
	 * Calculate the working area min and max coordinates in the image reference.
	 * @param imageModel
	 * @return int[]
	 */
//	private int[] calculateWorkingAreaCoordinates(PlanarImage imageModel, LinkedList<VanishingPoint> vanishingPointList){
//		int[] area = new int[4];
//		int[] tempArea = new int[4];
//
//		area[0] = 0;//xmin
//		area[1] = imageModel.getWidth();//xmax
//		area[2] = 0;//ymin
//		area[3] = imageModel.getHeight();//ymax
//
//		int XWAsize = this.size[0];
//		int YWAsize = this.size[1];
//
//		for(VanishingPoint point : vanishingPointList){
//			if(point.getDim()>2) throw new DimensionException("Point dimension should be 2");
//
//			if(point.get(0)<area[0]){
//				tempArea[0] = (int)Math.floor(point.get(0));
//			}else{
//				tempArea[0] = area[0];
//			}
//			if(point.get(0)>area[1]){
//				tempArea[1] = (int)Math.floor(point.get(0));
//			}else{
//				tempArea[1] = area[1];
//			}
//			if(point.get(1)<area[2]){
//				tempArea[2] = (int)Math.floor(point.get(1));
//			}else{
//				tempArea[2] = area[2];
//			}
//			if(point.get(1)>area[3]){
//				tempArea[3] = (int)Math.floor(point.get(1));
//			}else{
//				tempArea[3] = area[3];
//			}
//			if(tempArea[1]-tempArea[0]<XWAsize){
//				area[0] = tempArea[0];
//				area[1] = tempArea[1];
//			}
//			if(tempArea[3]-tempArea[2]<YWAsize){
//				area[2] = tempArea[2];
//				area[3] = tempArea[3];
//			}
//
//		}
//
//
//		return area;
//	}


//	private int[] calculateWorkingAreaMaxCoordinates(PlanarImage imageModel, LinkedList<VanishingPoint> vanishingPointList){
//		int[] area = new int[4];
//
//		area[0] = 0;//xmin
//		area[1] = imageModel.getWidth();//xmax
//		area[2] = 0;//ymin
//		area[3] = imageModel.getHeight();//ymax
//
//		for(VanishingPoint point : vanishingPointList){
//			if(point.getDim()>2) throw new DimensionException("Point dimension should be 2");
//
//			if(point.get(0)<area[0]){
//				area[0] = (int)Math.floor(point.get(0));
//			}
//			if(point.get(0)>area[1]){
//				area[1] = (int)Math.floor(point.get(0));
//			}
//			if(point.get(1)<area[2]){
//				area[2] = (int)Math.floor(point.get(1));
//			}
//			if(point.get(1)>area[3]){
//				area[3] = (int)Math.floor(point.get(1));
//			}
//		}
//
//		return area;
//	}
	/**
	 * Gize the size of the working area int a tab of to int (W then H).
	 * @param imageModel
	 * @return int[]
	 */
//	private int[] calculateWorkingAreaSize(PlanarImage imageModel, LinkedList<VanishingPoint> vanishingPointList){
//		int[] size = new int[2];
//		int[] area = calculateWorkingAreaMaxCoordinates(imageModel, vanishingPointList);
//
//		size[0] = area[1] - area[0];
//		size[1] = area[3] - area[2]; 
//
//		//System.out.println("WA calculated : "+size[0]+"x"+size[1]);
//
//		size = setWorkingAreaParamSize(size);
//
//		//System.out.println("WA param size : "+size[0]+"x"+size[1]);
//
//		
//		return size;
//
//	}


	/**
	 * Give the coordinates of the (0,0) point from the image reference in the working area reference.
	 */
	private DataPoint calculateZeroPoint(int[] area){
		DataPoint zero = new DataPoint(2);
		zero.set(0, -area[0]);
		zero.set(1, -area[2]);

		return zero;
	}


	public int getXSize(){
		return size[0];
	}

	public int getYSize(){
		return size[1];
	}

//	private int[] setWorkingAreaParamSize(int[] calculatedSize){
//		boolean setSize = false;
//
//		int[] paramSize = new int[2];
//
//		File file = new File("WorkingArea.param");
//
//		try {
//			FileReader fileReader = new FileReader(file);
//			BufferedReader bufferReader = new BufferedReader(fileReader);
//
//			String line;
//			//read the comment
//			line = bufferReader.readLine();
//			//read the Xmax value
//			line = bufferReader.readLine();
//			String[] elemX = line.split(" +");
//			//read the Ymax value
//			line = bufferReader.readLine();
//			String[] elemY = line.split(" +");
//
//			if(elemX[2] != null && elemY[2] != null){
//				paramSize[0]=Integer.parseInt(elemX[2]);
//				paramSize[1]=Integer.parseInt(elemY[2]);
//				setSize = true;
//			}
//
//		} catch (FileNotFoundException e) {
//			System.out.println("Working Area param file can't be found");
//		} catch (IOException e) {
//			System.out.println("Working Area param file can't be read");
//		} 
//
//		if(setSize){
//			//the working area is at least as big as the image
//			if(paramSize[0]<imageModel.getWidth()){
//				paramSize[0] = imageModel.getWidth();
//			}
//			if(paramSize[1]<imageModel.getHeight()){
//				paramSize[1] = imageModel.getHeight();
//			}
//			//if the calculated size is smaller than the param size, the smaller size is choosen
//			//the size is set a little bigger to be able to paint the vanishing point
//			if(calculatedSize[0]<paramSize[0]){
//				paramSize[0] = calculatedSize[0];
//			}
//			if(calculatedSize[1]+10<paramSize[1]){
//				paramSize[1] = calculatedSize[1];
//			}
//		}else{
//			paramSize[0] = calculatedSize[0];
//			paramSize[1] = calculatedSize[1];
//		}
//
//		return paramSize;
//	}
}
