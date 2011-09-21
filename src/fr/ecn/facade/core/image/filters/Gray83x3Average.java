package fr.ecn.facade.core.image.filters;

import jjil.algorithm.ErrorCodes;
import jjil.core.Error;
import jjil.core.Gray8Image;
import jjil.core.Image;
import jjil.core.PipelineStage;

public class Gray83x3Average extends PipelineStage {

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
        
        int cWidth  = imageInput.getWidth();
        int cHeight = imageInput.getHeight();
        byte[] grayInput = ((Gray8Image)imageInput).getData();
        
        Gray8Image imageResult = new Gray8Image(cWidth, cHeight);
        byte[] grayOutput = imageResult.getData();
        
        for(int i=0;i<cHeight;i++) {
            /* declare and initialize integers which will hold the
             * pixel value. The variables are named and numbered as if
             * they were array indices for three different 3x3 arrays.
             * They are set to -128 because this represents black in the
             * signed byte representation of a pixel.
             */
            byte g00 = -128, g01 = -128, g02 = -128;
            byte g10 = -128, g11 = -128, g12 = -128;
            byte g20 = -128, g21 = -128, g22 = -128;
          
            /* set column indices into this row
             * for first row use row 0 instead of row -1
             * for last row use row cHeight-1 instead of row cHeight
             */
            int pos0 = (i==0) ? 0 : (i-1) * cWidth;
            int pos1 = i * cWidth;
            int pos2 = (i==cHeight-1) ? i * cWidth : (i+1) * cWidth;
            
            /* initialize the (*,2) variables so the initial step to
             * the right does the right thing.
             */
            g02 = grayInput[pos0];
            g12 = grayInput[pos1];
            g22 = grayInput[pos2];
            
            for(int j=0;j<cWidth;j++) {
                
                /* move one step to the right
                 */
                g00 = g01;
                g01 = g02;
                
                g10 = g11;
                g11 = g12;
                
                g20 = g21;
                g21 = g22;
                
                /* get new pixel value. 
                 * In this code the value is treated as an
                 * unsigned value from 0 to 255, rather than as a signed
                 * value from -128 to 127, as it is in the byte image code.
                 * This is mathematically equivalent for averaging and 
                 * requires less computation than doing sign extension.
                 */
                if (j < cWidth-1) {
                    g02 = grayInput[pos0+1];
                    g12 = grayInput[pos1+1];
                    g22 = grayInput[pos2+1];
                } else {
                    /* we use black (-128) as the border in the last column 
                     */
                    g02 = g12 = g22 = -128;
                }
                
                /* calculate average g value
                 */
                byte g = (byte) ((g00 + g01 + g02 + 
                                  g10 + g11 + g12 + 
                                  g20 + g21 + g22) / 9);
                /* note g will always be between 0 and 255 so
                 * it is not necessary to mask etc.
                 */
                grayOutput[pos1] = g;
                /* advance column indices to next position
                 */
                pos0++;
                pos1++;
                pos2++;
            }
        }
 
        /* send output to PipelineStage
         */
        super.setOutput(imageResult);
	}

}
