package fr.irstv.kmeans;

import java.io.IOException;

import junit.framework.TestCase;

public class KMeansTestLecture extends TestCase {

	public void testReadCSV() {
		DataCorpus dc=null;
		try {
			dc = new DataCorpus("http://moreaug44.free.fr/mahzad/testsimple1.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("impossible de lire le fichier");
		}
		// test sur le nombre de lignes
		assertTrue("Taille du fichier",dc.getCorpus().size() == 5);
		if(dc.getCorpus().get(0).getDim() != 1) {
			fail();
		}
		if(dc.getCorpus().get(4).get(0) != 6) {
			fail();
		}
	}

}
