package fr.ecn.facade.segmentdetection;
import java.util.Stack;

/**
* LabelImage.java
* Find connected components in a matrix (that will be used in a segment finding method for an image).
*
* @author JANKOWSKI, Mariusz (Department of Electrical Engineering, University of Southern Maine, USA)
* @author KUSKA, Jens-Peer (Department for Computer Graphics and Image Processing, University of Leipzig, Germany)
* @version Created on April 16, 2004, 1:59 PM
*/

public class LabelImage {

	/**
	 * Attributes
	 */
	static int [][] label ;
	static Stack<int[]> stack ;

	/**
	 * Creates a new instance of LabelImage
	 */
	public LabelImage() {

	}

	/**
	 * Method finding connected components
	 *
	 * @param 	img				a (n x r) matrix where connected components must be labelled
	 * @param 	connectivity	if equals to 4, 4-connected elements computed. In ANY other case, 8-connected elements.
	 * @return					a ConnectedObjects object containing img labels
	 */
	public static ConnectedObjects labelImage(int[][] img, int connectivity) {
		int nrow = img.length ;
		int ncol = img[0].length;
		int lab = 1 ;
		int [] pos ;
		stack = new Stack<int[]>() ;
		label = new int[nrow][ncol] ;

		for (int r = 1; r < nrow; r++)
		for (int c = 1; c < ncol; c++) {
			if (img[r][c] == 0) continue ;
			if (label[r][c] > 0) continue ;
			/* encountered unlabeled foreground pixel at position r, c */
			/* push the position on the stack and assign label */
			stack.push(new int [] {r, c}) ;
			label[r][c] = lab ;
			/* start the float fill */
			while ( !stack.isEmpty()) {
				pos = stack.pop() ;
				int i = pos[0]; int j = pos[1];
				if (img[i-1][j] == 1 && label[i-1][j] == 0) {
					stack.push( new int[] {i-1,j} );
					label[i-1][j] = lab ;
				}
				if (img[i][j-1] == 1 && label[i][j-1] == 0) {
					stack.push( new int[] {i,j-1} );
					label[i][j-1] = lab ;
				}
				if (img[i][j+1] == 1 && label[i][j+1] == 0) {
					stack.push( new int[] {i,j+1} );
					label[i][j+1] = lab ;
				}
				if (img[i+1][j] == 1 && label[i+1][j] == 0) {
					stack.push( new int[] {i+1,j} );
					label[i+1][j] = lab ;
				}
				if (connectivity != 4){
					if (img[i-1][j-1] == 1 && label[i-1][j-1] == 0) {
						stack.push( new int[] {i-1,j-1} );
						label[i-1][j-1] = lab ;
					}
					if (img[i-1][j+1] == 1 && label[i-1][j+1] == 0) {
						stack.push( new int[] {i-1,j+1} );
						label[i-1][j+1] = lab ;
					}
					if (img[i+1][j-1] == 1 && label[i+1][j-1] == 0) {
						stack.push( new int[] {i+1,j-1} );
						label[i+1][j-1] = lab ;
					}
					if (img[i+1][j+1] == 1 && label[i+1][j+1] == 0) {
						stack.push( new int[] {i+1,j+1} );
						label[i+1][j+1] = lab ;
					}
				}
			}
			lab++;
		}
		return new ConnectedObjects(label, lab) ;
	}
}