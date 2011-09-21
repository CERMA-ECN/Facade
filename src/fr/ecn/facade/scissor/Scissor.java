package fr.ecn.facade.scissor;

import fr.ecn.facade.android.utils.ImageUtils;
import fr.ecn.facade.core.image.filters.Gray83x3Average;
import jjil.algorithm.Gray8Threshold;
import jjil.core.Error;
import jjil.core.Gray8Image;
import jjil.core.Image;
/**
 * Scissor algorithm using Dijkstra algorithm to calculate the lowest cost path. 
 * The weight of each pixel is obtained by Laplacian and Gradient value of the graph.
 * 
 * @author LIU Xinchang 
 *
 */
public class Scissor extends Dijkstra {

	private Gray8Image ip;
	int length; //length of pixels array of imported graph
	int[] arrLapla;
	double[] arrGradN;
	int[] arrIntGradN;
	int[] arrGradX;
	int[] arrGradY;
	int wd;//weight of gradient direction
	/**
	 * Constructor of Scissor
	 * @param ip A 8-bit graph
	 */
	public Scissor(Gray8Image ip) {
		super();
		this.ip=ip;
		length=ip.getWidth()*ip.getHeight();
		
		Gray83x3Average smooth = new Gray83x3Average();
		
		//Calculation of Laplacian and gradient of the graph
		//ByteGradient grad=new ByteGradient(ip.duplicate());
		Gray8Image tempIp = null;
		
		try {
			smooth.push(ip);
			tempIp = (Gray8Image) smooth.getFront();
		} catch (Error e) {
			//This shouldn't happens
		}
		
		{
			ByteGradient grad=new ByteGradient(tempIp);
			arrGradN=grad.getGradN();
			arrGradX=grad.getGradX();
			arrGradY=grad.getGradY();
			arrIntGradN=grad.inverNormalize(arrGradN, grad.getGradNMax());
		}
		
		ByteLaplacien lapla=new ByteLaplacien(tempIp);
		
		try {
			smooth.push(tempIp);
			smooth.push(smooth.getFront());
			smooth.push(smooth.getFront());
			tempIp = (Gray8Image) smooth.getFront();
		} catch (Error e) {
			//This shouldn't happens
		}
		
		smooth = null;
		
		Gray8Image tempBp = new ByteGradient(tempIp).getIpN();
		//tempBp.threshold(35);
		
		try {
			Gray8Threshold thresholder = new Gray8Threshold(ImageUtils.getAutoThreshold(tempBp), false);
			thresholder.push(tempBp);
			tempBp = (Gray8Image) thresholder.getFront();
		} catch (Error e) {
			//This shouldn't happens
		}
		
		//Get the result
		arrLapla=lapla.getLaplaZeroCro((byte[])tempBp.getData());
		//arrLapla=lapla.getLaplaZeroCro();
		//arrLapla=lapla.getLapla();
		//lapla.abs2(arrLapla);
		
		//ImagePlus im1=new ImagePlus("lapla",lapla.showArrayInGraph(arrLapla));

		//ImagePlus im2=new ImagePlus("gradient",tempBp);
		//ImagePlus im1=new ImagePlus("lapla",lapla.showArrayInGraph(arrLapla));
		//im1.show();
		//ImagePlus im2=new ImagePlus("gradient",grad.showArrayInGraph(arrIntGradN));
		//im2.show();
		//im1.show();	
		
		lapla=null;
		System.gc();		

		wd=4;
		initPara(30,20,0,0);
	}
	
	public Scissor(Image image) {
		this(ImageUtils.toGray8(image));
	}
	/**
	 * Define weight and start point coordinates
	 * @param wg		weight of gradient
	 * @param wl 		weight of laplacian
	 * @param beginX	X-coordinate of start point
	 * @param beginY	Y-coordinate of start point
	 */
	public void initPara(int wg, int wl, int beginX,int beginY)
	{
		initPara(wg,wl);
		this.setBegin(beginX, beginY);
	}
	
	/**
	 * Define weight and start point coordinates
	 * @param wg		weight of gradient
	 * @param wl 		weight of laplacian
	 */
	public void initPara(int wg, int wl)
	{
		int[] weight=new int[length];
		for (int i=0;i<this.length;i++)
		{
			weight[i]=wg*arrIntGradN[i]+wl*arrLapla[i];
		}
//		ByteEdge a=new ByteEdge(ip);
//		ImagePlus im3=new ImagePlus("weight",a.showArrayInGraph2(weight));
//		im3.show();
		this.setWeight(weight, ip.getWidth(), ip.getHeight());
	}
	
	/**
	 * Define weight and start point coordinates using default setting
	 * weight of gradient = 10, weight of laplacian = 10, begin point = (0, 0)
	 */
	public void initPara()
	{
		initPara(10,10,0,0);
	}
	/**
	 * Return current Image
	 * @return	Return current Image
	 */
	public Gray8Image getIp()
	{
		return ip;
	}
	/**
	 * Overwrite the computePaths() method of Dijkstra algorithm.
	 * Application of Dijkstra algorithm considering the direction of gradient
	 * to compute the lowest cost path from start point to the end point in the 
	 * active region
	 */
	public void computePaths()
	{
		while (!vertexQueue.isEmpty())
		{
			//find the lowest minWeight vertex
			Vertex v=vertexQueue.poll();
			for (int k=0;k<8;k++)
			{
				int vInd=this.index(v.x+csts.sys8X[k],v.y+csts.sys8Y[k]);
				Vertex u=vertex[vInd];
				int w=(int) (u.weight*csts.dist8[k])+v.minWeight+wd*gradDirectWeight(v.index,vInd,csts.sys8X[k],csts.sys8Y[k],csts.dist8[k]);
				//Compare existing weight to new weight to determine the lowest cost path
				if (w<u.minWeight)
				{
					u.minWeight=w;
					u.previous=v;
					addToVertexQueue(u);

				}
			}
			vertexQueue.remove(v);
		}
	}
	/**
	 * Calculate the gradient direction weight
	 * @param ind1 	first point index
	 * @param ind2	second point index
	 * @param kx 	x component of gradient at first point
	 * @param ky	y component of gradient at first point
	 * @param kq	distance between first point and second point
	 * @return		int weight of two given point
	 */
	protected int gradDirectWeight(int ind1,int ind2,int kx,int ky,double kq)
	{
		int w;
		if (kx*arrGradY[ind1]-ky*arrGradX[ind1]<0)
		{
			kx=-kx;
			ky=-ky;
		}
		double dp=kx*arrGradY[ind1]-ky*arrGradX[ind1];
		double dq=kx*arrGradY[ind2]-ky*arrGradX[ind2];
		dp=dp/arrGradN[ind1]/kq;
		dq=dq/arrGradN[ind2]/kq;
		w=(int) (100*(Math.acos(dp)+Math.acos(dq)));
		return w;
	}
}
