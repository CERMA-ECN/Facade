package fr.irstv.kmeans;

import java.util.LinkedList;

import fr.irstv.dataModel.DataKCircle;
import fr.irstv.dataModel.DataPoint;
import fr.irstv.dataModel.MkDataPoint;

/**
 * Implementation class for the RanSac algorithm
 * 
 * Ref: M.A. Fischler and R.C. Bolles. Random sample consensus: a paradigm
 * for model fitting with applications to image analysis and automated
 * cartography. Communications of the ACM, Vol. 24(6) 381-395, 1981.
 * 
 * @author Guillaume Moreau
 * 
 * modified on March 7th 2011 by Elsa Arrou-Vignod & Florent Buisson
 * (DataPoint changed into MkDataPoint)
 */
public class RanSac {
	/**
	 * max number of samples
	 */
	private int maxSample;

	/**
	 * tolerance for "point belongs to circle" relation ship
	 */
	private double sigma;

	/**
	 * data corpus
	 */
	protected DataMk dc;

	/**
	 * distance function
	 */
	DataDistance fctDist;

	/**
	 * stop threshold for model fitting
	 */
	private double stopThreshold;

	/**
	 * data groups
	 * @uml.property  name="groups"
	 * @uml.associationEnd  multiplicity="(0 -1)"
	 */
	DataGroup[] groups;

	/**
	 * constructor
	 *
	 * @param dc data corpus (points to be classified)
	 */
	public RanSac(int ngroups, DataMk dc, DataDistance fctDist) {
		this.dc = dc;
		this.fctDist = fctDist;
		// we build the groups
		groups = new DataGroup[ngroups];
		for (int i=0 ; i<ngroups ; i++) {
			groups[i] = new DataGroup(fctDist);
		}
	}

	/**
	 * the function that does the real work
	 *
	 */
	public void go() {
		// number of points to classify
		int n = dc.getCorpus().size();
		// remaining points to classify
		LinkedList<MkDataPoint> remainingPoints = new LinkedList<MkDataPoint>(dc.getMkCorpus());

		// group counter
		int groupCount = 0;
		// main loop
		while(remainingPoints.size() > (double)n * stopThreshold && groupCount<groups.length) {
			int possibleFitNumber = 1;
			LinkedList<MkDataPoint> bestPossibleFit=null;
//			LinkedList<DataPoint> kBest = null;
			
			for (int s=0 ; s<maxSample ; s++) {
				LinkedList<MkDataPoint> possibleFit = new LinkedList<MkDataPoint>();
				
				int k1,k2,k3;
				
				do {
					k1 = (int) (Math.random()*remainingPoints.size());
					k2 = (int) (Math.random()*remainingPoints.size());
					k3 = (int) (Math.random()*remainingPoints.size());
				} while ((k1 == k2) || (k1 == k3) || (k2 == k3));
				
				LinkedList<DataPoint> k = new LinkedList<DataPoint>();
				k.add(remainingPoints.get(k1));
				k.add(remainingPoints.get(k2));
				k.add(remainingPoints.get(k3));
				
				DataKCircle z = (DataKCircle)fctDist.centroid(k);
				
				// how many points are close to this circle?
				for (MkDataPoint point : remainingPoints) {
					double distance = fctDist.distance(point, z);
					if (distance < sigma) {
						possibleFit.add(point);
					}
				}
				
				if (possibleFit.size() > possibleFitNumber) {
					possibleFitNumber = possibleFit.size();
					bestPossibleFit = possibleFit;
//					kBest = k;
				}
			} // for (maxSample)
			// we have a candidate best fit
			if (bestPossibleFit.size() > 3) {
				// we can fetch the best associated circle
//				fctDist.centroid(kBest);
//				fctDist.centroid(bestPossibleFit);
				for (MkDataPoint pointH:bestPossibleFit) {
					groups[groupCount].add(pointH);
					remainingPoints.remove(pointH);
				}
				groupCount++;
			}
		} // while points are remaining
	} // go method

	public int getMaxSample() {
		return maxSample;
	}

	public void setMaxSample(int maxSample) {
		this.maxSample = maxSample;
	}

	public double getSigma() {
		return sigma;
	}

	public void setSigma(double sigma) {
		this.sigma = sigma;
	}

	public double getStopThreshold() {
		return stopThreshold;
	}

	public void setStopThreshold(double stopThreshold) {
		this.stopThreshold = stopThreshold;
	}

	public DataCorpus getDc() {
		return dc;
	}

	public DataGroup[] getGroups() {
		return groups;
	}
} // class
