package fr.ecn.facade.core.image.filters;

import jjil.algorithm.ErrorCodes;
import jjil.core.Error;
import jjil.core.Gray8Image;
import jjil.core.Image;
import jjil.core.PipelineStage;
import jjil.core.RgbImage;

/**
 * @author jerome
 *
 */
public class Gray8toRgb extends PipelineStage {

	/* (non-Javadoc)
	 * @see jjil.core.PipelineStage#push(jjil.core.Image)
	 */
	@Override
	public void push(Image image) throws Error {
		if (!(image instanceof Gray8Image)) {
            throw new Error(
                        Error.PACKAGE.ALGORITHM,
                        ErrorCodes.IMAGE_NOT_GRAY8IMAGE,
                        image.toString(),
                        null,
                        null);
        }
        Gray8Image gray = (Gray8Image) image;
        RgbImage rgb = new RgbImage(image.getWidth(), image.getHeight());
        byte[] grayData = gray.getData();
        int[] rgbData = rgb.getData();
        for (int i=0; i<gray.getWidth() * gray.getHeight(); i++) {
            /* Convert from signed byte value to unsigned byte for storage
             * in the RGB image.
             */
            int grayUnsigned = (grayData[i]) - Byte.MIN_VALUE;
            /* Create ARGB word */
            rgbData[i] = 
                        0xFF000000 |
                    ((grayUnsigned)<<16) | 
                    ((grayUnsigned)<<8) | 
                    grayUnsigned;
        }
        super.setOutput(rgb);
	}

}
