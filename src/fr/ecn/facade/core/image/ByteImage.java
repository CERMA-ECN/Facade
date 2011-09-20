package fr.ecn.facade.core.image;

/**
 * @author jerome
 *
 */
public class ByteImage extends Image {
	
    private final byte pixels[];
    
    /**
     * Creates a new instance of ByteImage
     * @param cWidth Width of the image (columns).
     * @param cHeight Height of the image (rows)
     */
    public ByteImage(int cWidth, int cHeight) {
        super(cWidth, cHeight);
        this.pixels = new byte[getWidth()*getHeight()];
    }
    
    /**
     * Creates a new instance of ByteImage, assigning a constant value.
     * @param cWidth Width of the image (columns).
     * @param cHeight Height of the image (rows)
     * @param nValue constant value to be assigned to the image
     */
    public ByteImage(int cWidth, int cHeight, byte nValue) {
        super(cWidth, cHeight);
        this.pixels = new byte[getWidth()*getHeight()];
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
        ByteImage image = new ByteImage(getWidth(),getHeight());
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
    public byte[] getData()
    {
        return this.pixels;
    }
    
    public byte getPixel(int x, int y) {
    	return this.pixels[y*getWidth()+x];
    }
    
    public void setPixel(int x, int y, byte value) {
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
