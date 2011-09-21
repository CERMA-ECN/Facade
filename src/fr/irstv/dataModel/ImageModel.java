package fr.irstv.dataModel;

//import java.awt.Component;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;
import java.util.Observable;
//import java.util.ResourceBundle;

//import javax.media.jai.JAI;
//import javax.media.jai.PlanarImage;

//import org.w3c.dom.Document;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import org.xml.sax.InputSource;
//import org.xml.sax.SAXException;

//import com.sun.org.apache.xerces.internal.parsers.DOMParser;

//import fr.irstv.view.ImageFilter;
//import fr.irstv.view.XMLFileNotFoundExceptionDialog;
//import fr.irstv.view.XMLFileNotReadException;

/**
 * Class containing all the image informations.
 */
public class ImageModel extends Observable{

	/**
	 * @uml.property  name="imageName"
	 */
	private String imageName;

	/**
	 * @uml.property   name="vanishingPointList"
	 */
	private LinkedList<VanishingPoint> vanishingPointList;

	/**
	 * @uml.property   name="segmentList"
	 */
	private LinkedList<Segment> segmentList;
	
	private LinkedList<DataPoint> hPointList;

	/**
	 * @uml.property   name="workingArea"
	 * @uml.associationEnd   inverse="imageModel:fr.irstv.dataModel.WorkingArea"
	 */
	private WorkingArea workingArea;


	/**
	 * @uml.property   name="planarImage"
	 */
//	private PlanarImage planarImage;


	/**
	 * @uml.property   name="epsilon"
	 */
	private float epsilon;


	/**
	 * @uml.property   name="imageURL"
	 */
	private URI imageURI;


	public ImageModel(){
		this.epsilon = 0 ;
		this.imageName = new String();
//		this.planarImage = null;
		this.segmentList = new LinkedList<Segment>();
		this.vanishingPointList = new LinkedList<VanishingPoint>();
		this.hPointList = new LinkedList<DataPoint>();
		this.workingArea = new WorkingArea();
		this.imageURI = null;
		setChanged();
		notifyObservers();
	}


	/**
	 * Getter of the property <tt>imageName</tt>
	 * @return  Returns the imageName.
	 * @uml.property  name="imageName"
	 */
	public String getImageName() {
		return imageName;
	}

	/**
	 * Getter of the property <tt>vanishingPointList</tt>
	 * @return  Returns the vanishingPointList.
	 * @uml.property  name="vanishingPointList"
	 */
	public LinkedList<VanishingPoint> getVanishingPointList() {
		return vanishingPointList;
	}

	/**
	 * Getter of the property <tt>segmentList</tt>
	 * @return  Returns the segmentList.
	 * @uml.property  name="segmentList"
	 */
	public LinkedList<Segment> getSegmentList() {
		return segmentList;
	}

	/**
	 * Getter of the property <tt>workingArea</tt>
	 * @return  Returns the workingArea.
	 * @uml.property  name="workingArea"
	 */
	public WorkingArea getWorkingArea() {
		return workingArea;
	}

	/**
	 * Getter of the property <tt>planarImage</tt>
	 * @return  Returns the planarImage.
	 * @uml.property  name="planarImage"
	 */
//	public PlanarImage getPlanarImage() {
//		return planarImage;
//	}


	/**
	 * Getter of the property <tt>epsilon</tt>
	 * @return  Returns the epsilon.
	 * @uml.property  name="epsilon"
	 */
	public float getEpsilon() {
		return epsilon;
	}

	/**
	 * Reinitilaze the model.
	 */
	public void reinitializeModel(){
		this.epsilon = 0 ;
		this.imageName = new String();
//		this.planarImage = null;
		this.segmentList = new LinkedList<Segment>();
		this.vanishingPointList = new LinkedList<VanishingPoint>();
		this.workingArea = new WorkingArea();
		this.imageURI = null;
		setChanged();
		notifyObservers();
	}


	public void setEpsilon(float epsilon) {
		this.epsilon = epsilon;
		setChanged();
		notifyObservers();
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
		setChanged();
		notifyObservers();
	}

//	public void setPlanarImage(PlanarImage planarImage) {
//		this.planarImage = planarImage;
//		setChanged();
//		notifyObservers();
//	}

	public void setSegmentList(LinkedList<Segment> segmentList) {
		this.segmentList = segmentList;
		setChanged();
		notifyObservers();
	}

	public void setVanishingPointList(LinkedList<VanishingPoint> vanishingPointList) {
		this.vanishingPointList = vanishingPointList;
		setChanged();
		notifyObservers();
	}

	public void setWorkingArea(WorkingArea workingArea) {
		this.workingArea = workingArea;
		setChanged();
		notifyObservers();
	}

//	public void setWorkingArea(PlanarImage image, LinkedList<VanishingPoint> vanishingPointList) {
//		this.workingArea = new WorkingArea(image, vanishingPointList);
//		setChanged();
//		notifyObservers();
//	}
	/**
	 * Getter of the property <tt>imageURL</tt>
	 * @return  Returns the imageURL.
	 * @uml.property  name="imageURL"
	 */
	public URI getImageURI() {
		return imageURI;
	}


	/**
	 * Setter of the property <tt>imageURL</tt>
	 * @param imageURL  The imageURL to set.
	 * @uml.property  name="imageURL"
	 */
	public void setImageURI(URI imageURI) {
		this.imageURI = imageURI;
	}


	/**
	 * return true if there is no data in the ImageModel.
	 * @return boolean
	 */
	public boolean isEmpty(){
		if(this.epsilon == 0 &&
				this.imageName.length() == 0 &&
//				this.planarImage == null &&
				this.segmentList.size() ==0 &&
				this.vanishingPointList.size() == 0 &&
				this.workingArea.getXSize() == 0 &&
				this.workingArea.getYSize() == 0 &&
				this.imageURI == null){
			return true;
		}else{
			return false;
		}
	}


	/**
	 * @throws IOException 
	 * @throws SAXException 
	 */
//	public void readXMLResultFile(File xmlFile, ResourceBundle stringValues) throws SAXException, IOException{
//
//		Document document = null;
//		FileReader fr = new FileReader(xmlFile);
//		BufferedReader bf = new BufferedReader(fr);
//		DOMParser parser = new DOMParser();
//		parser.parse(new InputSource(bf));
//		document = parser.getDocument();
//
//
//
//		this.epsilon = Float.parseFloat(document.getElementsByTagName("Epsilon").item(0).getAttributes().getNamedItem("Epsilon").getNodeValue());
//
//		NodeList vanishingPointNodeList = document.getElementsByTagName("PointDeFuite");
//		Node vanishingPointNode;
//		NodeList vanishingPointChildNodeList;
//		NodeList vanishingPointSegmentsNodeList;
//
//
//		// get the vanishing points in the xml file 
//		for(int i=0; i<vanishingPointNodeList.getLength(); i++){
//			//get the current vanishing point
//			vanishingPointNode = vanishingPointNodeList.item(i);
//
//			//get the vanishing point child node in the xml file
//			vanishingPointChildNodeList = vanishingPointNode.getChildNodes();
//
//			//create the vanishing point in the vanishing point list
//			VanishingPoint vanishingDataPoint = new VanishingPoint(2);
//
//			//the segmentList to be read
//			LinkedList<Segment> segmentVanishingPointList = new LinkedList<Segment>();
//
//			// set the value of the class attributes
//			for (int j = 0; j < vanishingPointChildNodeList.getLength(); j++) {
//				//set the x and y value of the vanishing point
//				if (vanishingPointChildNodeList.item(j).getNodeName().equalsIgnoreCase("Xpdf")) {
//					vanishingDataPoint.set(0, Double.parseDouble(vanishingPointChildNodeList.item(j).getAttributes().getNamedItem("X-du-Point-de-Fuite").getNodeValue()));				}
//				if (vanishingPointChildNodeList.item(j).getNodeName().equalsIgnoreCase("Ypdf")) {
//					vanishingDataPoint.set(1, Double.parseDouble(vanishingPointChildNodeList.item(j).getAttributes().getNamedItem("Y-du-Point-de-Fuite").getNodeValue()));					 					
//				}
//				//managde the segments list
//				if (vanishingPointChildNodeList.item(j).getNodeName().equalsIgnoreCase("LES-SEGMENTS")) {
//
//					//the segment to be read
//					Segment segment;
//					//the segment node list
//					vanishingPointSegmentsNodeList = vanishingPointChildNodeList.item(j).getChildNodes();
//
//					for (int k = 0; k < vanishingPointSegmentsNodeList.getLength(); k++) {
//						if (vanishingPointSegmentsNodeList.item(k).getNodeName().equalsIgnoreCase("Coordonnees")) {
//							DataPoint begin = new DataPoint(2);
//							DataPoint end = new DataPoint(2);
//							begin.set(0, Double.parseDouble(vanishingPointSegmentsNodeList.item(k).getAttributes().getNamedItem("Segments-xp1").getNodeValue()));
//							begin.set(1, Double.parseDouble(vanishingPointSegmentsNodeList.item(k).getAttributes().getNamedItem("Segments-yp1").getNodeValue()));
//							end.set(0, Double.parseDouble(vanishingPointSegmentsNodeList.item(k).getAttributes().getNamedItem("Segments-xp2").getNodeValue()));
//							end.set(1, Double.parseDouble(vanishingPointSegmentsNodeList.item(k).getAttributes().getNamedItem("Segments-yp2").getNodeValue()));
//
//							segment = new Segment(begin, end, vanishingDataPoint);
//						
//							segmentVanishingPointList.add(segment);
//						}
//					}
//				}
//
//				vanishingDataPoint.setSegmentList(segmentVanishingPointList);
//				//add a vanishing point in the vanishing point list
//			}
//			this.vanishingPointList.add(vanishingDataPoint);	
//
//			this.segmentList.addAll(segmentVanishingPointList);
//			
//
//		}
//
//		for(Segment segment : this.segmentList){
//			//System.out.println(segment.getBeginPoint().get(0));
//			this.hPointList.add(segment.getHPoint());
//		}
//
//		setChanged();
//		notifyObservers();
//
//	}

//	public void processOpenFile(File file, Component component, ResourceBundle stringValues){
//		String fileName = ImageFilter.getFileNameWithoutExtension(file.getName());
//		try {
//
//			this.setImageURI(file.toURI());
//			this.setImageName(fileName);
//			File xmlFile = new File(ImageFilter.getFileNameWithoutExtension(file.getPath())+".xml");
//			this.readXMLResultFile(xmlFile, stringValues);
//			this.setPlanarImage(JAI.create("fileload", file.getPath()));
//			this.setWorkingArea(this.planarImage, this.vanishingPointList);
//		} catch (IOException e) {
//			new XMLFileNotFoundExceptionDialog(fileName, component, stringValues);
//			e.printStackTrace();
//		}catch (SAXException esax){
//			new XMLFileNotReadException(fileName, component, stringValues);
//			esax.printStackTrace();
//		}
//	}



}
