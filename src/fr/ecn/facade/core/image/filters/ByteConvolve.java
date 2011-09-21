package fr.ecn.facade.core.image.filters;

import fr.ecn.facade.core.image.ByteImage;
import jjil.algorithm.ErrorCodes;
import jjil.core.Error;
import jjil.core.Gray8Image;
import jjil.core.Image;
import jjil.core.PipelineStage;

public class ByteConvolve extends PipelineStage {
	
	protected float[] kernel;
	protected int kernelWidth;
	protected int kernelHeight;
	
	protected int kernelCenterX;
	protected int kernelCenterY;

	/**
	 * @param kernel
	 * @param kernelWidth
	 * @param kernelHeight
	 */
	public ByteConvolve(float[] kernel, int kernelWidth, int kernelHeight) {
		super();
		this.kernel = kernel;
		this.kernelWidth = kernelWidth;
		this.kernelHeight = kernelHeight;
		
		//Compute kernel center
		this.kernelCenterX = (kernelWidth - 1)/2;
		this.kernelCenterY = (kernelHeight - 1)/2;
	}

	@Override
	public void push(Image imageInput) throws Error {
		if (!(imageInput instanceof Gray8Image)) 
        {
			throw new Error(
										Error.PACKAGE.ALGORITHM,
										ErrorCodes.IMAGE_NOT_GRAY8IMAGE,
										imageInput.toString(),
										null,
										null);
        }
		
		int width  = imageInput.getWidth();
		int height = imageInput.getHeight();
		byte[] grayInput = ((Gray8Image)imageInput).getData();
		
		ByteImage imageResult = new ByteImage(width, height);
		byte[] grayOutput = imageResult.getData();
		
		for (int i=0;i<width;i++) {
			for (int j=0;j<height;j++) {
				float value = 0;
				for (int k=0;k<kernelWidth;k++) {
					for (int l=0;l<kernelHeight;l++) {
						int x = i + k - kernelCenterX;
						if (x<0) {
							x = 0;
						}
						if (x>width-1) {
							x = width - 1;
						}
						
						int y = j + l - kernelCenterY;
						if (y<0) {
							y = 0;
						}
						if (y>height-1) {
							y = height - 1;
						}
						
						value += kernel[l*kernelWidth+k]*grayInput[y*width+x];
					}
				}
				grayOutput[j*width+i] = (byte) value;
			}
		}
		
        super.setOutput(imageResult);
	}

}
