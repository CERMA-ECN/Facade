package fr.ecn.facade.core.image;

public class FloatImage extends Image {
	
	private final float pixels[];
    
    /**
     * Creates a new instance of FloatImage
     * @param cWidth Width of the image (columns).
     * @param cHeight Height of the image (rows)
     */
    public FloatImage(int cWidth, int cHeight) {
        super(cWidth, cHeight);
        this.pixels = new float[getWidth()*getHeight()];
    }

    /**
     * Creates a new instance of FloatImage using a double array for pixels data
     * @param width
     * @param height
     * @param pixels
     */
    public FloatImage(int width, int height, double[] pixels) {
		this(width, height);
		for (int i=0; i<pixels.length; i++) {
			this.pixels[i] = (float) pixels[i];
		}
	}
    
    /**
     * Creates a new instance of FloatImage, assigning a constant value.
     * @param cWidth Width of the image (columns).
     * @param cHeight Height of the image (rows)
     * @param nValue constant value to be assigned to the image
     */
    public FloatImage(int cWidth, int cHeight, float nValue) {
        super(cWidth, cHeight);
        this.pixels = new float[getWidth()*getHeight()];
        for (int i=0; i<this.getWidth()*this.getHeight();i++) {
            this.pixels[i] = nValue;
        }
    }

	/**
     * Copy this image
     * @return the image copy.
     */
    public Image clone()
    {
        FloatImage image = new FloatImage(getWidth(),getHeight());
        System.arraycopy(
                this.getData(),
                0,
                image.getData(),
                0,
                getWidth()*getHeight());
        return image;
    }
    
    /**
     * Return a pointer to the image data.
     * @return the data pointer.
     */
    public float[] getData()
    {
        return this.pixels;
    }
    
    public float getPixel(int x, int y) {
    	return this.pixels[y*getWidth()+x];
    }
    
    public void setPixel(int x, int y, float value) {
    	this.pixels[y*getWidth()+x] = value;
    }
    
    /**
     * Return a string describing the image.
     * @return the string.
     */
    public String toString()
    {
        return super.toString() + " (" + getWidth() + "x" + getHeight() + ")";
    }
}
