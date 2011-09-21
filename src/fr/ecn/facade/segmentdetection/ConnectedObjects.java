package fr.ecn.facade.segmentdetection;

/**
 * Class implementing the connected objects in a matrix.
 *
 * @author Leo COLLET, Cedric TELEGONE, Ecole Centrale Nantes
 * @version 1.0
 */

public class ConnectedObjects {
	
	/**
	 * Atributes
	 */
	protected int num_labels;
	protected int[][] matrix;
	
	/**
	 * Public constructor
	 * 
	 * @param 	m		matrix containing elements with value 0 in the background and p(i,j)>0 in the foreground
	 * @param 	num		the number of connected objects in the matrix
	 */
	public ConnectedObjects(int[][] m, int num){
		this.matrix = m;
		this.num_labels = num;
	}

	public String toString(){
		String s = new String();
		s += "ConnectedObjects\n";
		s += "Value of num_labels : " + num_labels + "\n";
		s += "Value of matrix : " + matrix.toString() + "\n";
		s += "Size of matrix : " + matrix.length + " x " + matrix[0].length;
		return s;
	}
	
	/**
	 * Get number of labels
	 * 
	 * @return	the number of labels in the input matrix
	 */
	public int getNum_labels() {
		return num_labels;
	}
	/**
	 * Get matrix of labelled objects
	 * 
	 * @return	a 2-dim integer array containing 0 on background points and a label for each point in a connected component
	 */
	public int[][] getMatrix() {
		return matrix;
	}
	/**
	 * Get an element of the matrix of labelled objects
	 * @param 	i		vertical position
	 * @param 	j		horizontal position
	 * @return			value at this.matrix[i][j]
	 */
	public int getMatrix(int i, int j){
		return this.matrix[i][j];
	}
	
}
