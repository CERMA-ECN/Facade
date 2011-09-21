package fr.ecn.facade.scissor;

/**
 * 
 * Constants used for convolution  
 *  Including neighborhood system coordinates and weights
 *  
 * @author LIU
 *
 */

public final class Consts {
	/**
	 * Coordinates of 8 direct neighborhood
	 */
	final public int[] sys8X={-1,0,1,1,1,0,-1,-1};
	final public int[] sys8Y={1,1,1,0,-1,-1,-1,0};
	
	/**
	 * Weight vectors of 8 direct neighborhood
	 */
	final public int[] neighborWeightSys8X={-1,0,1,2,1,0,-1,-2};
	final public int[] neighborWeightSys8Y={1,2,1,0,-1,-2,-1,0};
	
	/**
	 * Distance of 8 direct neighborhood from origin 
	 */
	final public double[] dist8={1.414,1,1.414,1,1.414,1,1.414,1};
	
	/**
	 * Distance of 8 direct neighborhood from origin 
	 */
	final public double[] dist25={
		    0.3535534,    0.4472136,    0.5,    0.4472136,    0.3535534,  
		    0.4472136,    0.7071068,    1. ,    0.7071068,    0.4472136,  
		    0.5      ,    1.       ,    1. ,    1.       ,    0.5      ,  
		    0.4472136,    0.7071068,    1. ,    0.7071068,    0.4472136,  
		    0.3535534,    0.4472136,    0.5,    0.4472136,    0.3535534
	};
	
	/**
	 * Coordinates of origin and its 8 direct neighborhood 
	 */
	final public int[] sys9X={-1,0,1,-1,0,1,-1,0,1};
	final public int[] sys9Y={1,1,1,0,0,0,-1,-1,-1};

	/**
	 * Coordinates of origin and its 24 direct neighborhood 
	 */
	final public int[] sys25X={-2,-1,0,1,2,-2,-1,0,1,2,-2,-1,0,1,2,-2,-1,0,1,2,-2,-1,0,1,2};
	final public int[] sys25Y={2,2,2,2,2,1,1,1,1,1,0,0,0,0,0,-1,-1,-1,-1,-1,-2,-2,-2,-2,-2};
	
	/**
	 * Laplacien Weight for 9 neighborhood system
	 */
	final public int[] laplace9W1={0,-1,0,-1,4,-1,0,-1,0};
	final public int[] laplace9W2={-1,-1,-1,-1,8,-1,-1,-1,-1};
	
	/**
	 * Laplacien Weight for 25 neighborhood system
	 */	
	final public int[] laplace25W1={-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,24,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,};
	
	/**
	 * Sobel Weight for 9 neighborhood system for caculation of gradient
	 */	
	final public int[] sobel9W1={1,2,1,0,0,0,-1,-2,-1};
	final public int[] sobel9W2={-1,0,1,-2,0,2,-1,0,1};
	
	final public int[] sys81X={
			-4,-3,-2,-1,0,1,2,3,4,
			-4,-3,-2,-1,0,1,2,3,4,
			-4,-3,-2,-1,0,1,2,3,4,
			-4,-3,-2,-1,0,1,2,3,4,
			-4,-3,-2,-1,0,1,2,3,4,
			-4,-3,-2,-1,0,1,2,3,4,
			-4,-3,-2,-1,0,1,2,3,4,
			-4,-3,-2,-1,0,1,2,3,4,
			-4,-3,-2,-1,0,1,2,3,4,
	};
	final public int[] sys81Y={
			-4,-4,-4,-4,-4,-4,-4,-4,-4,
			-3,-3,-3,-3,-3,-3,-3,-3,-3,
			-2,-2,-2,-2,-2,-2,-2,-2,-2,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,
			0,0,0,0,0,0,0,0,0,
			1,1,1,1,1,1,1,1,1,
			2,2,2,2,2,2,2,2,2,
			3,3,3,3,3,3,3,3,3,
			4,4,4,4,4,4,4,4,4,
	};
	final public int[] laplace81W1={
			0,0, 3, 2, 2, 2, 3, 0, 0,
			0, 2, 3, 5, 5, 5, 3, 2, 0,
			3, 3, 5, 3, 0, 3, 5, 3, 3,
			2, 5, 3, -12, -23, -12, 3, 5, 2,
			2, 5, 0, -23, -44, -23, 0, 5, 2,
			2, 5, 3, -12, -23, -12, 3, 5, 2,
			3, 3, 5, 3, 0, 3, 5, 3, 3,
			0, 2, 3, 5, 5, 5, 3, 2, 0,
			0, 0, 3, 2, 2, 2, 3, 0, 0
	};
}
