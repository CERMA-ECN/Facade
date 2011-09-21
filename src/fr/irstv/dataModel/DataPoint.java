package fr.irstv.dataModel;

/**
 * class for datapoints. stores numerical data ; not very clever...
 * would require an adapter to existing data container
 * 
 * @author  gmoreau
 */
public class DataPoint {
	/**
	 * data associated to the DataPoint
	 */
	private double[] data;

	/**
	 * data dimension
	 */
	private int dim;
	
	/**
	 * constructor : requires dimension
	 * @param dim
	 */
	public DataPoint(int dim) {
		this.dim = dim;
		data = new double[dim];
	}
	
	/**
	 * copy constructor
	 * 
	 * @param copyMe DataPoint to be copied
	 */
	public DataPoint(DataPoint copyMe) {
		this.dim = copyMe.dim;
		data = new double[dim];
		for (int i=0 ; i<dim ; i++) {
			data[i] = copyMe.data[i];
		}
	}
	/**
	 * @return  the dim
	 * @uml.property  name="dim"
	 */
	public int getDim() {
		return dim;
	}
	/**
	 * i-th element datapoint accessor
	 * @param i
	 * @return
	 */
	public double get(int i) {
		return data[i];
	}
	
	/**
	 * mutatur on the i-th element of the datapoint
	 * @param i
	 * @param val
	 */
	public void set(int i,double val) {
		data[i] = val;
	}
	
	public String toString() {
		String s = new String();
		for (int i=0 ; i<dim ; i++) {
			if (i != 0) {
				s += "\t";
			}
			s += get(i);
		}
		return s;
	}

	public void initZero() {
		for (int i=0 ; i<dim ; i++) {
			set(i,0d);
		}
	}

	public void addCoord(DataPoint gp) {
		for (int i=0 ; i<dim ; i++) {
			set(i,get(i)+gp.get(i));
		}
	}

	public void scalDiv(double k) {
		for (int i=0 ; i<dim ; i++) {
			set(i,get(i)/k);
		}
	}

	public double[] getData() {
		return data;
	}
	
	/**
	 * Say if the point will be draw or not on the working area.
	 * @param area
	 * @return
	 */
	public boolean isPointVisible(WorkingArea area){
		boolean visible = true;
		//if the point is in the working area
		if(this.dim > 2){throw new DimensionException("methode usable only with dim 2 DataPoint");}
		if(area.getZeroPoint().get(0)+this.get(0)<0 || area.getZeroPoint().get(0)+this.get(0)>area.getXSize()){
			visible = false;
		}
		if(area.getZeroPoint().get(1)+this.get(1)<0 || area.getZeroPoint().get(1)+this.get(1)>area.getYSize()){
			visible = false;
		}
		return visible;
	}
}
