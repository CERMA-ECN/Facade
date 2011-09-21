package fr.ecn.facade.scissor;

/**
 * ScissorElement class adds some parameters in ScissorLine in 
 * order to meet the need of ScissorController 
 * @author LIU
 *
 */
class ScissorElement extends ScissorLine
{
	private int layerID;
	private int type;
	private boolean visible;
	public String name;
	
	public ScissorElement(Scissor s, int id) {
		this(s,id,0);
	}
	public ScissorElement(Scissor s, int id,int t) {
		super(s);
		type =t;
		layerID=id;	
		name=null;
		visible=true;
	}
	public int getLayerID()
	{
		return this.layerID;
	}
	public void setType(int t)
	{
		this.type=t;
	}
	public int getType()
	{
		return this.type;
	}
	public void setVisible(boolean b)
	{
		this.visible=true;
	}
	public boolean getVisible()
	{
		return this.visible;		
	}
}