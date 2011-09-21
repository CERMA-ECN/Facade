
package fr.ecn.facade.scissor;

import jjil.core.Gray8Image;

/**
 * Class ByteEdge provides some basic 8-bit graphic operations like convolution operation, absolute value of a graph array and so on.
 *   
 * @author LIU
 *
 */
public class ByteEdge{
    final protected Consts csts = new Consts();
    
    protected int[] maskX;
    protected int[] maskY;    	
    protected int maskSys;
    protected int[] weight;
    protected int width,height;
    protected byte[] pixels;
    
    public ByteEdge(Gray8Image ip){
    	width=ip.getWidth();
    	height=ip.getHeight();
    	pixels = ip.getData();
    } 
    public ByteEdge(int w,int h, byte[] p){
    	width=w;
    	height=h;
    	pixels=p;
    	
    } 
    /**
     * Convolution algorithm for a given mask 
     * @param arr	Value of each pixel in a graph 
     * @return		Convolution result
     */
    public int[] convolvAlgo(byte arr[]) 
    {
    	int i,j,k;
    	int v;
    	// convolution result array
    	int[] pix=new int[width*height];
		for (i=0;i<width;i++)
			for(j=0;j<height;j++)
			{
				v=0;
				for (k=0;k<maskSys;k++)
					v+=getPixelArr(i+maskX[k],j+maskY[k],arr)*weight[k];
				pix[j*width+i]=v;
			}		
		return pix;
    }
    
    /**
     * Thresholding
     * @param arr
     * @param n
     */
    public void bicolor(int[] arr,int n)
    {
    	int i;
    	for (i=0; i<arr.length;i++)
    	{
    		if (arr[i]<n && arr[i]>(-n))
    			arr[i]=0;
    		else
    			arr[i]=255;
    	}
    
    }
    /**
     * Absolute value regulated between 0 and 255
     * @param arr Graphic array to be modified
     */
    public void abs(int[] arr){
    	int i;
    	for (i=0; i<arr.length;i++)
    	{
    		if (arr[i]<0)
    			arr[i]=-arr[i];
    		if (arr[i]>255)
    			arr[i]=255;
    	}
    }
    /**
     * Absolute value regulated from [-n n] to [0 255]
     * @param arr	Graphic array to be modified
     */
	public void abs2(int[] arr){
    	int i;
		int n=100;
    	for (i=0; i<arr.length;i++)
    	{
    		if (arr[i]<-n)
    			arr[i]=0;
    		else if (arr[i]>n)
    			arr[i]=255;
			else
				arr[i]=255*(arr[i]+n)/2/n;
    	}
    }
	/**
	 * Invert the pixel value
	 * @param arr	Graphic array to be inverted
	 */
	
	public void inverse(int[] arr){
    	int i;
    	for (i=0; i<arr.length;i++)
  			arr[i]=255-arr[i];
    }
	/**
	 * Normalize and invert the pixel value to [0 255] with a scale 
	 * @param arr	Graphic array to be operated
	 * @param seed	Scale number
	 * @return		Result graphic array, 255 * ( 1 - arrValue / seed )
	 */
	public int[] inverNormalize(double[] arr, double seed)
	{	
		int[] temp=new int[arr.length];
    	for (int i=0; i<arr.length;i++)
  			temp[i]=(int)(255*(1-arr[i]/seed));
    	return temp;
	}
	/**
	 * Given coordinates, get element value from given graph array
	 * @param x 	X-coordinate
	 * @param y		Y-coordinate
	 * @param arr	Graph array
	 * @return		Value at point (x,y)
	 */
    public int getPixelArr(int x,int y,byte[] arr)
    {
    	if (x<0) x=0;
    	if (y<0) y=0;
    	if (x>=width) x=width-1;
    	if (y>=height) y=height-1;
    	return 0xFF&arr[y*width+x];
    }
    /**
     * Given coordinates, get element value from given graph array
     * @param x 	X-coordinate
	 * @param y		Y-coordinate
	 * @param arr	Graph array
	 * @return		Value at point (x,y)
     */
    public int getPixelArr(int x,int y,int[] arr)
    {
    	if (x<0) x=0;
    	if (y<0) y=0;
    	if (x>=width) x=width-1;
    	if (y>=height) y=height-1;
    	return arr[y*width+x];
    }
    /**
     * Convert an array to a 8-bit graph with absolute value operation
     * @param arr int pixels array
     * @return	Gray8Image with pixels value from input array 
     */
    public Gray8Image showArrayInGraph(int[] arr)
    {
    	Gray8Image bp=new Gray8Image(this.width,this.height);
    	byte[] imageData = bp.getData();
    	for (int i=0;i<arr.length;i++)
    		if (arr[i]<-255)
    			imageData[i] = (byte) 255;
    		else if (arr[i]<0)
    			imageData[i] = (byte) -arr[i];
    		else if (arr[i]>255)
    			imageData[i] = (byte) 255;
    		else 
    			imageData[i] = (byte) arr[i]; 
    	
    	return bp;
    }
    /**
     * Convert an array to a 8-bit graph with a scale to 0-255, negative values are ignore.
     * @param arr int pixels array
     * @return	Gray8Image with pixels value from input array 
     */
    public Gray8Image showArrayInGraph2(int[] arr)
    {
    	int max=0;
    	for (int i=0;i<arr.length;i++)
    		max=Math.max(max, arr[i]);
    	Gray8Image bp=new Gray8Image(this.width,this.height);
    	byte[] imageData = bp.getData();
    	for (int i=0;i<arr.length;i++)
    		imageData[i] = (byte) (255*arr[i]/max);
    	return bp;
    }
    /**
     * Convert an array to a 8-bit graph with a scale to 0-255, negative values are ignore.
     * @param arr double pixels array
     * @return	Gray8Image with pixels value from input array 
     */
	public Gray8Image showArrayInGraph2(double[] arr)
    {
    	double max=0;
    	for (int i=0;i<arr.length;i++)
    		max=Math.max(max, arr[i]);
    	Gray8Image bp=new Gray8Image(this.width,this.height);
    	byte[] imageData = bp.getData();
    	for (int i=0;i<arr.length;i++)
    		imageData[i] = (byte) (255*arr[i]/max);
    	return bp;
    }
    
}
