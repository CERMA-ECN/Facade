package fr.irstv.dataModel;

public class MkDataPoint extends DataPoint {
	/**
	 * segment is stored here because in Mk case, the H point is the identifier
	 * that in used during classification
	 */
	Segment seg;
	
	public Segment getSeg() {
		return seg;
	}

	public void setSeg(Segment seg) {
		this.seg = seg;
	}

	public MkDataPoint() {
		super(2);
		// TODO Auto-generated constructor stub
	}

	public MkDataPoint(DataPoint copyMe,Segment s) {
		super(copyMe);
		seg = s;
	}

}
