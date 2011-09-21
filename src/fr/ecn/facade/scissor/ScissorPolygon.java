package fr.ecn.facade.scissor;

import android.graphics.Point;

/**
 * ScissorPolygon inherts from Polygon class. It provides additional
 * basic operations of a Polygon such as find the last point, find the separation point 
 * between two Polygon.
 * @author LIU Xinchang
 *
 */
class ScissorPolygon extends Polygon
{
	/**
	 * Version 1.0
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Same as the constructor of Polygon
	 */
	ScissorPolygon()
	{
		super();
	}
	/**
	 * 
	 * Same as the constructor of Polygon
	 * @param x int array, x coordinates of vertices of a polygon
	 * @param y int array, y coordinates of vertices of a polygon
	 * @param n int number, number of vertices
	 */
	ScissorPolygon(int[] x,int[] y,int n)
	{
		super(x,y,n);
	}
	/**
	 * @return x coordinate of last vertex 
	 */
	public int getBeginX()
	{
		return this.xpoints[npoints-1];
	}
	/**
	 * @return y coordinate of last vertex 
	 */
	public int getBeginY()
	{
		return this.ypoints[npoints-1];
	}
	/**
	 * Append a polygon to this polygon<br>
	 * if we use p1.appends(p2), we store first the 
	 * points of p2, then points of p1.
	 * @param sp a polygon to be appended
	 */
	public void appends(ScissorPolygon sp)
	{
		int n=this.npoints+sp.npoints;
		int[] xp=new int[n],yp=new int[n];
		for (int i=0;i<sp.npoints;i++)
		{
			xp[i]=sp.xpoints[i];
			yp[i]=sp.ypoints[i];
		}
		for (int i=sp.npoints;i<n;i++)
		{
			xp[i]=xpoints[i-sp.npoints];
			yp[i]=ypoints[i-sp.npoints];
		}
		this.xpoints=xp;
		this.ypoints=yp;
		this.npoints=n;
	}
	/**
	 * If the point (x,y) is a vertex of this polygon, return true, otherwise false.
	 * @param x a int number, x coordinate
	 * @param y	a int number, y coordinate
	 * @return  a boolean value to indicate if the point is a vertex of the polygon
	 */
	public boolean containVertex(int x,int y)
	{
		for (int i=0;i<npoints;i++)
			if (xpoints[i]==x && ypoints[i]==y)
				return true;
		return false;
	}	
	/**
	 * If the point (x,y) is a vertex of this polygon and isn't included in last n vertices, return true, otherwise false.
	 * @param x a int number, x coordinate
	 * @param y	a int number, y coordinate
	 * @param n a int number, number of last vertices
	 * @return  a boolean value to indicate if the point is a vertex of the polygon and isn't included in last n vertices
	 */
	public boolean containVertex(int x,int y,int n)
	{
		for (int i=0;i<npoints-n;i++)
			if (xpoints[i]==x && ypoints[i]==y)
				return true;
		return false;
	}
	/**
	 * Find the last same vertex of this polygon and another polygon.
	 * This vertex is considered as a separationPoint.
	 * @param p another polygon
	 * @return the separation point
	 */
	public Point separationPoint(Polygon p)
	{
		return separationPoint(p,p.npoints);
	}
	/**
	 * Find the last same vertex of this polygon and another polygon and
	 * this vertex is belonged to last n vertices.
	 * @param p another polygon
	 * @param n int number, number of last vertices
	 * @return the separation point
	 */
	public Point separationPoint(Polygon p,int n)
	{
		Point point=new Point();
		int i=0;
		while (i<p.npoints-n && !containVertex(p.xpoints[i],p.ypoints[i]))
			i++;
		if (i<p.npoints-n)
			point.set(p.xpoints[i],p.ypoints[i]);
		else
			point=null;
		return point;
	}
	
}