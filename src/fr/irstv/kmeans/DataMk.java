package fr.irstv.kmeans;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fr.irstv.dataModel.DataPoint;
import fr.irstv.dataModel.MkDataPoint;
import fr.irstv.dataModel.Segment;

/**
 * specific class for reading MK points data files
 * see in XML file, no DTD or schema yet
 *
 * @author moreau
 *
 */
public class DataMk extends DataCorpus {

	/**
	 *@author Cedric Telegone & Leo Collet
	 *reading data from pg.data.group
	 *
	 *modified on March 7th 2011 by Elsa Arrou-Vignod & Florent Buisson
	 *(added MkCorpus in order not to lose the segment information)
	 *@param a pg.data.group 
	 */

	protected LinkedList<MkDataPoint> MkCorpus;
	
	public LinkedList<MkDataPoint> getMkCorpus() {
		return MkCorpus;
	}

	public DataMk(String fileName) throws IOException {
		super();
		MkCorpus = new LinkedList<MkDataPoint>();
//		readXmlMk(fileName);

		//readImageSegment(fileName);


		// TODO Auto-generated constructor stub
	}

	public DataMk(List<fr.ecn.common.core.geometry.Segment> segmentsList) {
		super();
		MkCorpus = new LinkedList<MkDataPoint>();
		
		for (fr.ecn.common.core.geometry.Segment segment : segmentsList) {
			DataPoint dp1 = new DataPoint(2);
			DataPoint dp2 = new DataPoint(2);

			dp1.set(0, segment.getP1().getX());
			dp1.set(1, segment.getP1().getY());
			dp2.set(0, segment.getP2().getX());
			dp2.set(1, segment.getP2().getY());
			Segment s = new Segment(dp1,dp2,null);
			MkDataPoint mkp = new MkDataPoint(s.getHPoint(),s);
			corpus.add(mkp);
			MkCorpus.add(mkp);
		}
		
	}

	/**
	 * reading datafile
	 *
	 * @author moreau
	 * @version 1
	 *
	 * @param fileName name of the file
	 *
	 */
//	public void readXmlMk(String url) {
//		Document document = null;
//		try {
//			/*
//			 *
//			 * lecture du fichier xml � partir d'une url
//			URL urlDesc = new URL(url);
//			URLConnection connection = urlDesc.openConnection();
//			InputStream flux = connection.getInputStream();
//			 */
//
//
//			//lecture du fichier xml en local
//			BufferedReader bf = new BufferedReader(new FileReader(url));
//			DOMParser parser = new DOMParser();
//			parser.parse(new InputSource(bf));
//			document = parser.getDocument();
//			NodeList nl = document.getElementsByTagName("Coordonnees");
//			System.out.println("--debug-- "+nl.getLength()+" segments in file");
//			for (int i=0 ; i<nl.getLength() ; i++) {
//				DataPoint dp1 = new DataPoint(2);
//				DataPoint dp2 = new DataPoint(2);
//				Node n = nl.item(i);
//				NamedNodeMap nnm = n.getAttributes();
//				dp1.set(0, Double.parseDouble(nnm.getNamedItem("Segments-xp1").getNodeValue()));
//				dp1.set(1, Double.parseDouble(nnm.getNamedItem("Segments-yp1").getNodeValue()));
//				dp2.set(0, Double.parseDouble(nnm.getNamedItem("Segments-xp2").getNodeValue()));
//				dp2.set(1, Double.parseDouble(nnm.getNamedItem("Segments-yp2").getNodeValue()));
//				Segment s = new Segment(dp1,dp2,null);
//				MkDataPoint mkp = new MkDataPoint(s.getHPoint(),s);
//				corpus.add(mkp);
//				MkCorpus.add(mkp);
//			}
//		} catch (Exception e) {
//			System.out.println("damn it!");
//			e.printStackTrace();
//		}
//	}

}
