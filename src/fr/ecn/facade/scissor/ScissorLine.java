package fr.ecn.facade.scissor;

//import ij.IJ;
//import ij.ImagePlus;
//import ij.gui.PolygonRoi;

import android.graphics.Color;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import java.util.ArrayList;

/**
 * Class for control one track of scissor tool from begin to end.
 * This class provides method to add, remove points, auto generation
 * of key points, auto remove recent point and so on. Paint method
 * can be call if user want to show this track. 
 * @author LIU Xinchang
 *
 */
public class ScissorLine{
	//the operation is determined by current state 
	private SCISSOR_STATE state;
	
	private ScissorPolygon currentScissorLine;
	private ArrayList<ScissorPolygon> scissorLine; //this array list stores polygons from one key point to next key point
	
	private int currentLineColor;
	private int lineColor;
	private Scissor scissor;		//each polygon is obtained from the result of algorithm in class Scissor 
	
	/*
	 * interactive operation variable, used detect mouse motion 
	 */
	private int mouseCount;			 
	private int mouseCountMax;
	private int endCounter;
	private ArrayList<AutoKeyVertex> autoKeyVertexList;
	private int removeCounter;

	/**
	 * Constructor of ScissorLine, initial parameters
	 * @param s Scissor associated to current graph
	 */
	public ScissorLine(Scissor s){
		scissor=s;
		lineColor=Color.YELLOW;
		currentLineColor=Color.RED;
		scissorLine=new ArrayList<ScissorPolygon>();
		state=SCISSOR_STATE.HOLD;
		mouseCount=0;
		mouseCountMax=4;
		autoKeyVertexList=new ArrayList<AutoKeyVertex>();
		
	}
	/**
	 *Add a new key point in scissor track.
	 * @param x	x coordinate
	 * @param y y coordinate
	 */
	public void addNewKeyPoint(int x,int y)
	{
		if (state==SCISSOR_STATE.BEGIN)	//just after the scissor tool begin 	
    	{
			reset();
			scissor.setBegin(x, y);
    		currentScissorLine=scissor.getPathsList(x, y);

    		state=SCISSOR_STATE.DOING;
    		this.autoKeyVertexList.clear();
    		removeCounter=0;
    		endCounter=0;
        	
    	}else if(state ==SCISSOR_STATE.DOING)
    	{
    		ScissorPolygon tempPath=scissor.getPathsList(x, y);
			scissorLine.add(tempPath);
			scissor.setBegin(x, y);
    		currentScissorLine=scissor.getPathsList(x, y);
    		this.autoKeyVertexList.clear();
    		removeCounter=0;
    		endCounter=0;
    	}
	}
	/**
	 * When mouse move, call this method for generating changeable track
	 * and handle the interactive function.
	 * @param x	x coordinate
	 * @param y y coordinate
	 */
	public void setMovePoint(int x,int y)
	{
        //Check current state
		if (state==SCISSOR_STATE.DOING)
		{	
			//Backup current changeable scissor line
           	ScissorPolygon tempSp=currentScissorLine;
           	//update current changeable scissor line
        	currentScissorLine=scissor.getPathsList(x, y);
        	//Auto generate key point
        	if(mouseCount++>mouseCountMax)
        	{
        		mouseCount=0;
        		Point p=currentScissorLine.separationPoint(tempSp, 10);
        		if (p!=null)
        		{
        			AutoKeyVertex v=new AutoKeyVertex(p);
        			this.autoKeyVertexList.add(v);
        		}
        		int i=0;
        		//Check whether ancient key points are still on the track
        		while(i<autoKeyVertexList.size())
        		{
	        		AutoKeyVertex v =autoKeyVertexList.get(i);
	        		if (currentScissorLine.containVertex(v.getX(), v.getY(),10))
	    			{
	    				if (v.count())
	    				{
	    					Rect r=currentScissorLine.getBounds();
	    					this.addNewKeyPoint(v.getX(), v.getY());
	    					scissor.setActiveRegion(r.left, r.top);
	    					scissor.setActiveRegion(r.right, r.bottom);
	    					autoKeyVertexList.remove(v);
	    				}
	    				i++;
	    			}
	    			else
	    				autoKeyVertexList.remove(v);	
        		}
        	}
        	//Auto remove last key point if the cursor is close to it
    		if (removeCounter<10)
    		{
        		if (currentScissorLine.npoints<6)
        			removeCounter++;
    		}
    		else
    		{
    			this.removeRecentKeyPoint();
    			removeCounter=0;
    		}
    		//Auto end the scissor track if the cursor is close to begin point
    		if (scissorLine.size()>1)
    		{
	    		if(endCounter<10)
	    		{
	    			ScissorPolygon sp=scissorLine.get(0);
	        		if (x-sp.getBeginX()<10 && sp.getBeginX()-x<10 && sp.getBeginY()-y<10 && y-sp.getBeginY()<10)
	        			endCounter++;
	    		}
	    		else
	    		{
	    			this.endScissor();
	    			endCounter=0;
	    		}
    		}
		}
	}
	/**
	 * End scissor process and connect last point to begin point
	 */
	public void endScissor()
	{
		if (state==SCISSOR_STATE.DOING)
		{
			currentScissorLine=null;
			ScissorPolygon pg=scissorLine.get(0);
			scissorLine.add(scissor.getPathsList(pg.getBeginX(), pg.getBeginY()));
			state=SCISSOR_STATE.HOLD;
		}
	}	
	/**
	 * If current line is not from begin point, Remove recent key point.
	 */
	public void removeRecentKeyPoint()
	{
		if (scissorLine.size()>1)
		{
			scissorLine.remove(scissorLine.size()-1);
			ScissorPolygon pg=scissorLine.get(scissorLine.size()-1);
			scissor.setBegin(pg.xpoints[0],pg.ypoints[0]);
		}
		else if (scissorLine.size()==1)
		{
			ScissorPolygon pg=scissorLine.get(0);
			scissor.setBegin(pg.getBeginX(),pg.getBeginY());
			scissorLine.remove(scissorLine.size()-1);
		}
	}
	/**
	 * Reset scissorLine, clear all tracks.
	 */
	public void reset()
	{
		this.scissorLine.clear();
		currentScissorLine=null;
		this.state=SCISSOR_STATE.HOLD;
	}
	
	/**
	 * Paint all tracks,  default color is yellow for tracks and
	 * red for changeable part. A square is also painted at every
	 * key point.
	 * @param canvas Canvas
	 */
	public void draw(Canvas canvas)  {
		Paint paint = new Paint();
		
		paint.setColor(this.lineColor);
		for (ScissorPolygon pg : this.scissorLine) {
			canvas.drawRect(pg.xpoints[0]-2, pg.ypoints[0]-2, pg.xpoints[0]+2, pg.ypoints[0]+2, paint);
			for (int i=1;i<pg.npoints;i++) {
				canvas.drawLine(pg.xpoints[i-1], pg.ypoints[i-1], pg.xpoints[i], pg.ypoints[i], paint);
			}
		}
		
		paint.setColor(this.currentLineColor);
		if (currentScissorLine != null) {
			for (int i=1;i<currentScissorLine.npoints;i++) {
				canvas.drawLine(currentScissorLine.xpoints[i-1], currentScissorLine.ypoints[i-1], currentScissorLine.xpoints[i], currentScissorLine.ypoints[i], paint);
			}
		}
	}

	/**
	 * Set current state to a user defined state.
	 * @param s state of type SCISSOR_STATE
	 */
	public void setState(SCISSOR_STATE s)
	{
		state=s;
	}
	/**
	 * Set current state to BEGIN.
	 */
	public void setActive()
	{
		state=SCISSOR_STATE.BEGIN;
	}
	/**
	 * Set current state to HOLD.
	 */
	public void setHold()
	{
		state=SCISSOR_STATE.HOLD;
	}
	/**
	 * Check current state
	 * @return current state
	 */
	public SCISSOR_STATE getState()
	{
		return state;
	}
	/**
	 * Check current state
	 * @return true if surrent state is isDoing
	 */
	public boolean isDoing()
	{
		return (state==SCISSOR_STATE.DOING);
	}
	/**
	 * Convert scissor track to a free ROI of ImageJ
	 * @return A PolygonRoi 
	 */
//	public PolygonRoi toRoi()
//	{
//		ScissorPolygon pg=new ScissorPolygon();
//		for (int i=0;i<this.scissorLine.size();i++)
//			pg.appends(scissorLine.get(i));
//		PolygonRoi pr=new PolygonRoi(pg, PolygonRoi.FREEROI);
//		return pr;
//	}
	/**
	 * Get the area in the scissor track
	 * @return int area
	 */
//	public int getSurface()
//	{
//		int surface=0;
//		byte[] b=(byte[]) toRoi().getMask().getPixels();
//		for (int i=0;i<b.length;i++)
//			if (b[i]!=0)
//				surface++;
//		return surface;
//	}
	/**
	 * Set color of scissor track
	 * @param c A color
	 */
	public void setLineColor(int c)
	{
		this.lineColor=c;
	}
	/**
	 * Set color of changeable part of track line
	 * @param c A color
	 */
	public void setCurrentLineColor(int c)
	{
		this.currentLineColor=c;
	}
	
	/**
	 * @return the scissorLine
	 */
	public ArrayList<ScissorPolygon> getScissorLine() {
		return scissorLine;
	}
}
