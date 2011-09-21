package fr.ecn.facade.scissor;

import android.graphics.Point;

/**
 * AutoKeyVertex is a countable point, which help to determine whether 
 * a vertex satisfies the conditions to be a key vertex.
 * @author LIU Xinchang
 *
 */
class AutoKeyVertex{
	private int x;
	private int y;
	private int count; 
	private int maxCount=5;
	public AutoKeyVertex(Point p)
	{
		this(p.x,p.y);
	}
	public AutoKeyVertex(int x,int y)
	{
		this.x=x;
		this.y=y;
		count=0;
	}
	/**
	 * Count the appear times
	 * @return true if more than max appear times
	 */
	public boolean  count()
	{
		this.count++;
		return (count>maxCount);
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
}