package fr.ecn.facade.scissor;

import java.util.PriorityQueue;

/**
 * Implementation of Dijkstra algorithm for 2D weighted graph to calculate the lowest cost way from the begin point to end point
 * 
 * Each pixel in the graph is considered as a vertex connected by its 8 neighborhoods 
 * 
 * @author LIU
 *
 */

public class Dijkstra
{
	
	protected PriorityQueue<Vertex> vertexQueue;
	protected Vertex[] vertex;
	private int width, height,length;
	private int beginX,beginY;
	protected Consts csts=new Consts();
	
	/**Active region for dynamic programming*/
	private int activeX1,activeX2,activeY1,activeY2;
	
	public Dijkstra()
	{
		vertexQueue=new PriorityQueue<Vertex>();
		width=0;
		height=0;
		length=0;
		activeX1=0;
		activeX2=0;
		activeY1=0;
		activeY2=0;
		
	}
	/**
	 * Constructor of class Dijkstra with graph information
	 * @param arr 	Weight array for each pixel in the graph
	 * @param w 	Width of the graph
	 * @param h 	Height of the graph
	 */
	
	public Dijkstra(int[] arr, int w, int h)
	{
		this();
		setWeight(arr,w,h);
	}
	
	/**
	 * Constructor of class Dijkstra with graph information and begin point.
	 * @param arr 	Weight array of pixels in the graph.
	 * @param w 	Width of the graph.
	 * @param h 	Height of the graph.
	 * @param x 	X-coordinate of begin point.
	 * @param y 	Y-coordinate of begin point.
	 */
	public Dijkstra(int[] arr, int w, int h, int x, int y)
	{
		this(arr,w,h);
		setBegin(x,y);
	}
	/**
	 * Set the weight of each vertex.
	 * @param arr	Weight array of pixels in the graph.
	 * @param w		Width of the graph.
	 * @param h 	Height of the graph.
	 */
	public void setWeight(int[] arr, int w, int h)
	{
		width=w;
		height=h;
		length=arr.length;
		vertex=new Vertex[length];
		for(int i=0;i<arr.length;i++)
		{
			vertex[i]=new Vertex(i,arr[i],getX(i),getY(i));
			
		}

	}
	/**
	 * Set the start point of the graph.
	 * @param x		X-coordinate of start point.
	 * @param y 	Y-coordinate of start point.
	 */	
	public void setBegin(int x,int y)
	{
		beginX=x;
		beginY=y;
		activeX1=x;
		activeX2=x;
		activeY1=y;
		activeY2=y;		
		resetVertices();
		vertex[index(beginX,beginY)].minWeight=0;
		vertex[index(beginX,beginY)].previous=null;
		vertexQueue.add(vertex[index(beginX,beginY)]);
		
	}
	
	/**
	 * Set the active square region of the graph where Dijkstra algorithm is performed.
	 * @param x		X-coordinate of new point to add in.
	 * @param y		Y-coordinate of new point to add in.
	 */
	public void setActiveRegion(int x,int y)
	{
		int ax1=activeX1, ax2=activeX2;
		int i;
		//detect if x is already in the active region
		if (x>=0 & x<width)
			if (x<activeX1)
			{
				for (i=activeY1;i<=activeY2;i++) //add edge points of previous active region to the queue to calculate
					vertexQueue.add(vertex[index(activeX1-1,i)]);
				activeX1=x;
			}
			else if (x>activeX2)
			{
				for (i=activeY1;i<=activeY2;i++)
					vertexQueue.add(vertex[index(activeX2+1,i)]);
				activeX2=x;
			}
		//detect if y is already in the active region
		if (y>=0 & y<height)
			if (y<activeY1)
			{
				for (i=ax1;i<=ax2;i++)
					vertexQueue.add(vertex[index(i,activeY1-1)]);
				activeY1=y;
			}
			else if (y>activeY2)
			{
				for (i=ax1;i<=ax2;i++)
					vertexQueue.add(vertex[index(i,activeY2+1)]);
				activeY2=y;
			}
	}
	/**
	 * Reset the minimum weight sum of each vertex to max value
	 */
	public void resetVertices()
	{
		for(int i=0;i<length;i++)
		{
			vertex[i].minWeight=Integer.MAX_VALUE;
		}
	}
	/**
	 * Return index of a point given x and y 
	 * @param x		X-coordinate of a point
	 * @param y		Y-coordinate of a point
	 * @return		index of this point
	 */
	public int index(int x,int y)
	{
		if(x<0) x=0;
		if(y<0) y=0;
		if(x>width-1) x=width-1;
		if(y>height-1) y=height-1;		
		return x+y*this.width;
	}
	/**
	 * Return X-coordinate of a point given index
	 * @param ind 	Index of a point
	 * @return		X-coordinate of this point
	 */
	public int getX(int ind){
		if (ind<0) ind=0;
		if (ind>length-1) ind=length-1;
		return ind-ind/width*width;
	}
	/**
	 * Return Y-coordinate of a point given index
	 * @param ind 	Index of a point
	 * @return		Y-coordinate of this point
	 */
	public int getY(int ind){
		if (ind<0) ind=0;
		if (ind>length-1) ind=length-1;
		return ind/width;
	}	
	/**
	 * Application of Dijkstra algorithm to compute the lowest cost path from start point to the end point in the active region
	 */
	public void computePaths()
	{
		while (!vertexQueue.isEmpty())
		{
			//find the lowest minWeight vertex
			Vertex v=vertexQueue.poll();
			for (int k=0;k<8;k++)
			{
				Vertex u=vertex[this.index(v.x+csts.sys8X[k],v.y+csts.sys8Y[k])];
				int w=(int) (u.weight*csts.dist8[k])+v.minWeight;
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
	 * Add a vertex in the calculation list if u is in the active region
	 * @param u vertex to add
	 */
	public void addToVertexQueue(Vertex u)
	{
		if (u.x>=activeX1 & u.x<=activeX2 & u.y>=activeY1 & u.y<=activeY2)
		{
			vertexQueue.remove(u);
			vertexQueue.add(u);	
		}
	}
	/**
	 * Return an array to indicate the result path of Dijkstra algorithm
	 * @param endX		X-coordinate of end point
	 * @param endY		Y-coordinate of end point
	 * @return			A graph array, if an element is on the path, note 1, otherwise 0.
	 */
	public int[] getPathsArr(int endX, int endY)
	{
		return getPathsArr(index(endX,endY));
	}
	
	/**
	 * Return an array to indicate the result path of Dijkstra algorithm
	 * @param index	Index of end point
	 * @return		A graph array, if an element is on the path, note 1, otherwise 0.
	 */
	public int[] getPathsArr(int index)
	{
		
		setActiveRegion(getX(index),getY(index));
		computePaths();
		int[] arr=new int[this.length];
		for (int i=0;i< this.length;i++)
			arr[i]=0;
		for (Vertex v = vertex[index]; v != null; v = v.previous)
		{
			arr[v.index]=1;
			//IJ.log("("+v.x+","+v.y+")"+v.index);
		}
		return arr;
	}
	/**
	 * Return a polygon to indicate the result path of Dijkstra algorithm
	 * @param endX	X-coordinate of end point
	 * @param endY	Y-coordinate of end point
	 * @return A polygon of the vertices in order
	 */
	public ScissorPolygon getPathsList(int endX, int endY)
	{
		return getPathsList(index(endX,endY));
	}
	/**
	 * Return a polygon to indicate the result path of Dijkstra algorithm
	 * @param index	Index of end point
	 * @return		A polygon of the vertices in order
	 */
	public ScissorPolygon getPathsList(int index)
	{
		setActiveRegion(getX(index),getY(index));
		computePaths();
		ScissorPolygon pg=new ScissorPolygon();
		for (Vertex v = vertex[index]; v != null; v = v.previous)
		{
			pg.addPoint(v.x, v.y);
		}
		return pg;
	}
}
