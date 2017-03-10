package Interactive_SPT_Sim.utilities;

import java.util.Random;

//Utility class for generating a pixel map that looks like single fluorescent molecules from an array of x,y positions
public final class SingleMoleculeImageGenerator {
    
    private static final Random r = new Random();

    public SingleMoleculeImageGenerator() {
    //only static methods    
    }
    
    public static int[][] addCountsToImage (int[][] imageArray, int realSize, int [][] pxPSF, double [][] entityPositions){
        //takes entity positions and pre-calculated 4 x oversampled gaussian and uses them to generate pixel map to simulate a single molecule image
        double pxSizeImage = realSize/(imageArray.length);
        double pxSizePSF = pxSizeImage/4;
        double pxPSFDim = pxPSF.length;
        for (int i=0; i<entityPositions.length; i++){
            for (int j=0; j<pxPSFDim; j++){
                for (int k=0; k<pxPSFDim; k++){
                    double detX = entityPositions[i][0]-((pxSizePSF/2)*(pxPSFDim-1))+(j*pxSizePSF);
                    double assessX = detX/pxSizeImage;
                    int pixelX = (int) assessX;
                    double detY = entityPositions[i][1]-((pxSizePSF/2)*(pxPSFDim-1))+(k*pxSizePSF);
                    double assessY = detY/pxSizeImage;
                    int pixelY = (int) assessY;
                    //don't try to add counts when gaussian spread falls outside the image boundaries (or entity has wanderedoutside)!
                    if (pixelY<imageArray.length && pixelX>=0 && pixelX<imageArray.length && pixelY>=0) { 
                        imageArray[pixelX][pixelY] += pxPSF[j][k];
                    }
                }
            }
        }
        return imageArray;
    }
    
    
    public static int[][] pixelatedPSF (int pixelRes, int realSize, int totalCounts, double airyRad) {
        //create array to hold analytical pixelated gaussian
        double pixelSize = realSize/(pixelRes*4);//want oversampling of PSF
        double gaussStDev = airyRad/2;
        int pxPSFDim = (int)((2*airyRad)/pixelSize);
        int[][] pxPSF = new int [pxPSFDim][pxPSFDim];
        for (int i=0; i<totalCounts; i++) {
            double detX = getGauss((pxPSFDim*pixelSize)/2, gaussStDev);
            double assessX = detX/pixelSize;
            int pixelX = (int) assessX;
            double detY =getGauss((pxPSFDim*pixelSize)/2, gaussStDev);
            double assessY = detY/pixelSize;
            int pixelY = (int) assessY;
            //don't try to add counts when gaussian spread falls outside the array boundaries
            if (pixelY<pxPSFDim && pixelX>=0 && pixelX<pxPSFDim && pixelY>=0) { 
                pxPSF[pixelX][pixelY]++;
            }
        }
        return pxPSF;
    }
    
    public static double getGauss (double mean, double stdeviation) {
        
        double g = mean + (r.nextGaussian()*stdeviation);
        return g;
    }
}
