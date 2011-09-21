package fr.ecn.facade.scissor;

/**
 * Vertex of graph with comparable minimum weight sum used in Dijkstra algorithm.
 * 
 * @author LIU Xinchang
 *
 */
class Vertex implements Comparable<Vertex> 
{
	/**Weight of this vertex*/
    public int weight;
    /**Minimum weight sum from begin point to this vertex.*/
    public int minWeight;
    /**Previous vertex in the minimum weight chain.*/
    public Vertex previous;
    public int x, y,index;
    
    /**
     * Constructor of vertex.
     * @param ind Index of this vertex.
     * @param w Weight value.
     * @param x X-coordinate in the graph
     * @param y Y-coordinate in the graph
     */
    public Vertex(int ind,int w,int x,int y)
    {
    	index=ind;
    	weight = w;
    	this.x=x;
    	this.y=y;
    	previous=null;
    	minWeight=Integer.MAX_VALUE;
    	
    }
    /**Method inherited from Comparable<Vertex>*/
    public int compareTo(Vertex other)
    {
        return minWeight- other.minWeight;
    }

}