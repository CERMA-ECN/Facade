/**
 *
 */
package fr.irstv.kmeans;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.StringTokenizer;


import fr.irstv.dataModel.DataPoint;

/**
 * @author  gmoreau  data corpus (DataPoint list)
 */
public class DataCorpus {
	/**
	 * list of data points
	 */
	protected LinkedList<DataPoint> corpus;

	/**
	 * @return  the corpus
	 * @uml.property  name="corpus"
	 */
	public LinkedList<DataPoint> getCorpus() {
		return corpus;
	}

	/**
	 * constructor : from a filename
	 * @param fileName
	 */
	public DataCorpus(String fileName) throws IOException {
		corpus = new LinkedList<DataPoint>();
		readCSV(fileName);
	}

	/**
	 * default constructor
	 */
	public DataCorpus() {
		corpus = new LinkedList<DataPoint>();
	}

	/**
	 * read the datafile : first line and first column are ignored
	 *
	 * @bug caution : double formats depend on the machine where
	 * the program runs. . may be found on PCs whereas Macs show ,
	 * sur PC et des , sur Mac
	 *
	 * @param url url of the CSV file
	 * @throws IOException
	 */
	public void readCSV(String url) throws IOException {
		URL urlDesc = new URL(url);
		URLConnection connection = urlDesc.openConnection();
		InputStream flux = connection.getInputStream();
		BufferedReader bf = new BufferedReader(new InputStreamReader(flux));
		String li = bf.readLine();
		int liCpt = 0;
		int dim = 0;
		while (li != null) {
			LinkedList<Double> ld = new LinkedList<Double>();
			liCpt++;
			// the whole work is done here
			String delim=";\n \t";
			StringTokenizer st = new StringTokenizer(li,delim);
			while (st.hasMoreTokens()) {
				String tk = st.nextToken();
				if (liCpt != 1) {
					try {
						double v = Double.parseDouble(tk);
						ld.add(v);
					}
					catch (NumberFormatException e) {
						System.out.println("file format error : "+url);
						System.out.println(e.getMessage());
					}
				}
				else {
					dim++;
				}
			}
			if (liCpt != 1) {
				DataPoint dp = new DataPoint(dim-1);
				int i=0;
				for (Double d : ld) {
					if (i != 0) {
						dp.set(i-1,d);
					}
					i++;
				}
				corpus.add(dp);
			}
			li = bf.readLine();
		}
	}


	/**
	 * only for display and debug
	 */
	public String toString() {
		String s = new String();
		for (DataPoint dp : corpus) {
			s += dp + "\n";
		}
		return s;
	}

	public static void main(String[] args) throws IOException {
		// try to read
		DataMk dc = new DataMk("DSCN3616.xml");
		System.out.println(dc);
		//System.out.println("nombre de points : "+dc.getCorpus().size());

	}
}
