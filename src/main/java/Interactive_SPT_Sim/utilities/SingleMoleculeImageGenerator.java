package Interactive_SPT_Sim.utilities;

import java.util.Random;

//Utility class for generating a pixel map that looks like single fluorescent molecules from an array of x,y positions
public final class SingleMoleculeImageGenerator {
    
    private static final Random r = new Random();

    public SingleMoleculeImageGenerator() {
    //only static methods    
    }
    
    public static int[][] addCountsToImage (int[][] imageArray, int pixelRes, int realSize, int totalCounts, double airyDia, double [][] entityPositions) {
        //create array to hold pixel counts
        double pixelSize = realSize/pixelRes;
            for (int i=0; i<entityPositions.length; i++) {
                for (int j=0; j<totalCounts; j++) {
                    double detX =getGauss(entityPositions[i][0], airyDia);
                    double assessX = detX/pixelSize;
                    int pixelX = (int) assessX;
                    double detY =getGauss(entityPositions[i][1], airyDia);
                    double assessY = detY/pixelSize;
                    int pixelY = (int) assessY;
                    //don't try to add counts when gaussian spread falls outside the image boundaries (or entity has wanderedoutside)!
                    if (pixelY<pixelRes && pixelX>=0 && pixelX<pixelRes && pixelY>=0) { 
                        imageArray[pixelX][pixelY] += 1;
                    }
                }
            }
        return imageArray;
    }
    
    public static int[][] addCountsToImage (int[][] imageArray, int realSize, int [][] pxPSF, double [][] entityPositions){
        
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
    
    
    public static int[][] pixelatedPSF (int pixelRes, int realSize, int totalCounts, double airyDia) {
        //create array to hold analytical pixelated gaussian
        double pixelSize = realSize/(pixelRes*2);//want oversampling of PSF
        int pxPSFDim = (int)((4*airyDia)/pixelSize);
        int[][] pxPSF = new int [pxPSFDim][pxPSFDim];
        for (int i=0; i<totalCounts; i++) {
            double detX = getGauss((pxPSFDim*pixelSize)/2, airyDia);
            double assessX = detX/pixelSize;
            int pixelX = (int) assessX;
            double detY =getGauss((pxPSFDim*pixelSize)/2, airyDia);
            double assessY = detY/pixelSize;
            int pixelY = (int) assessY;
            //don't try to add counts when gaussian spread falls outside the array boundaries
            if (pixelY<pxPSFDim && pixelX>=0 && pixelX<pxPSFDim && pixelY>=0) { 
                pxPSF[pixelX][pixelY] += 1;
            }
        }
        return pxPSF;
    }
    
    public static double getGauss (double mean, double stdeviation) {
        
        double g = mean + (r.nextGaussian()*stdeviation);
        return g;
    }
}
