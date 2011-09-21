
package fr.ecn.facade.scissor;

import jjil.core.Gray8Image;
/**
 * Calculate the gradient of a 8 bit gray graph, using Sobel kernel<br>
 * |-1 0 1|&emsp&emsp&nbsp |-1 -2 -1|<br>
 * |-2 0 2|&nbsp and &nbsp| 0&nbsp 0&nbsp 0 | for convolution<br>
 * |-1 0 1|&emsp&emsp&nbsp | 1&nbsp 2&nbsp 1| <br>
 * @author LIU
 *
 */
public class ByteGradient extends ByteEdge {
	private int[] gradX;
	private int[] gradY;
	/**
	 * Norm of gradient vector
	 */
	private double[] gradN;
	private double gradNMax;
	//private int[] gradVecX;
	//private int[] gradVecY;
	/**
	 * Tangent of gradient vector  
	 */
	//private int[] gradAng;
	public ByteGradient(Gray8Image ip) {
		super(ip);
		//Take the mask
		maskSys=9;
		maskX=csts.sys9X;
		maskY=csts.sys9Y;
		//Sobel horizontal mask 
		weight=csts.sobel9W1;
		gradY=this.convolvAlgo(this.pixels);
		//Sobel vertical mask 
		weight=csts.sobel9W2;
		gradX=this.convolvAlgo(this.pixels);
		
		int i;
		gradN=new double[width * height];
		gradNMax=0;
		//Calculate the norm of gradient
		for (i=0;i<width * height;i++)
		{
			gradN[i]= Math.sqrt(Math.pow(gradX[i], 2.0)+Math.pow(gradY[i], 2.0));
			gradNMax=Math.max(gradN[i], gradNMax);
		}
		
	}
	/**
	 * 
	 * @return An int array of the x component of gradient vector
	 */
	public int[] getGradX()
	{
		return gradX;
	}
	 /** 
	 * @return An int array of the y component of gradient vector
	 */
	public int[] getGradY()
	{
		return gradY;
	}
	/**
	 * 
	 * @return The norm of gradient vector
	 */
	public double[] getGradN()
	{
		return gradN;
	}
	public double getGradNMax()
	{
		return gradNMax;
	}
	
//	public void getGradVec(int[] x,int[] y)
//	{
//
//	}

//	/**
//	 *@return A 8 bit graph with the value of x component of gradient vector  
//	 */
//    public Gray8Image getIpX(){
//		this.abs(gradX);
//		this.setPixelArr(gradX);
//		return this;
//	}
//    /**
//     * 
//     * @return A 8 bit graph with the value of y component of gradient vector
//     */
//    public Gray8Image getIpY(){
//		this.abs(gradY);
//		this.setPixelArr(gradY);
//		return this;
//	}
    /**
     * @return A 8 bit graph with the value of the norm of gradient vector
     */
   
	public Gray8Image getIpN(){
		return showArrayInGraph2(gradN);
	}
}
