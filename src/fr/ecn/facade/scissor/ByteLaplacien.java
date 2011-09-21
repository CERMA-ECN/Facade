package fr.ecn.facade.scissor;

import jjil.core.Gray8Image;

/**
 * Calculate the gradient of a 8 bit gray graph, using Laplacian kernel<br>
 * &emsp&emsp&emsp&emsp&emsp&nbsp|-1 -1 -1 -1 -1|<br>
 * |-1  -1 -1|&emsp&nbsp&nbsp |-1 -1 -1 -1 -1|<br>
 * |-1&nbsp 8 -1|&nbsp or&nbsp |-1 -1 24 -1 -1| or 9*9 kernel for convolution<br>
 * |-1 -1 -1|&emsp&nbsp&nbsp |-1 -1 -1 -1 -1|<br>
 * &emsp&emsp&emsp&emsp&emsp&nbsp|-1 -1 -1 -1 -1|<br>
 * </pre>
 * @author LIU Xinchang
 *
 */
public class ByteLaplacien extends ByteEdge {

	private int[] lapla;
	public ByteLaplacien(Gray8Image ip) {
		super(ip);
		int maskChoice=2;
		if (maskChoice==0)
		{
			//using laplacian mask of 3*3
			maskSys=9;
			maskX=csts.sys9X;
			maskY=csts.sys9Y;
			weight=csts.laplace9W2;
		}
		else if(maskChoice==1)
		{
			//using laplacian mask of 5*5
			maskSys=25;
			maskX=csts.sys25X;
			maskY=csts.sys25Y;
			weight=csts.laplace25W1;
		}else
		{
			//using laplacian mask of 9*9
			maskSys=81;
			maskX=csts.sys81X;
			maskY=csts.sys81Y;
			weight=csts.laplace81W1;
			lapla=this.convolvAlgo(this.pixels);
		}
	}
	/**
	 * 
	 * @return An int array of the result after laplacian convolution 
	 */
	public int[] getLapla()
	{
		return lapla.clone();
	}
	/**
	 * 
	 * @return An int array of the result of zero-crossings after laplacian convolution 
	 */	
	public int[] getLaplaZeroCro()
	{
		return this.zeroCross();
	}
	public int[] getLaplaZeroCro(byte[] mask)
	{
		return this.zeroCross(mask);
	}
	/**
	 * Find zero-crossings which represent the edge points with default parameter
	 * @return A black white graph array where black points indicate the zero-crossings 
	 */
	public int[] zeroCross(){
		int n=10;
		int m=9;
		return zeroCross(n,m);		
	}
	/**
	 * Find zero-crossings which represent the edge points with default parameter
	 * @param n	Minimum difference between neighbor elements 
	 * @param m Minimum number of element of positive sign or negative sign
	 * @return A black white graph array where black points indicate the zero-crossings 
	 */
	public int[] zeroCross(int n,int m){
		int[] temp=new int[lapla.length];

		for (int i=0;i<this.width;i++)
			for (int j=0;j<this.height;j++)
			{
				double xp=0;
				double xn=0;
				for (int k=0;k<25;k++)
				{
					int kk=this.getPixelArr(i+csts.sys25X[k], j+csts.sys25Y[k], lapla);
					if (kk>n)
					{
						xp+=1;
					}
					else if (kk<-n)
					{
						xn+=1;
					}
					
				}
				if (xp>m & xn>m)
					temp[j*this.width+i]=1;
				else
					temp[j*this.width+i]=100;
			}
		return temp;
	}
	/**
	 * Find zero-crossings which represent the edge points with default parameter
	 * @return A black white graph array where black points indicate the zero-crossings 
	 */
	public int[] zeroCross(byte[] mask){
		int n=10;
		int m=8;
		return zeroCross(n,m,mask);		
	}
	/**
	 * Find zero-crossings which represent the edge points with default parameter
	 * @param n	Minimum difference between neighbor elements 
	 * @param m Minimum number of element of positive sign or negative sign
	 * @return A black white graph array where black points indicate the zero-crossings 
	 */
	public int[] zeroCross(int n,int m,byte[] mask){
		int[] temp=new int[lapla.length];

		for (int i=0;i<this.width;i++)
			for (int j=0;j<this.height;j++)
			{
				if (mask[j*this.width+i]!=0)
				{        
					double xp=0;
					double xn=0;
					for (int k=0;k<25;k++)
					{
						int kk=this.getPixelArr(i+csts.sys25X[k], j+csts.sys25Y[k], lapla);
						if (kk>n)
						{
							xp+=1;
						}
						else if (kk<-n)
						{
							xn+=1;
						}
						
					}
					if (xp>m & xn>m)
						temp[j*this.width+i]=2;
					else
						temp[j*this.width+i]=100;
				}else
					temp[j*this.width+i]=100;
			}
		return temp;
	}
}
