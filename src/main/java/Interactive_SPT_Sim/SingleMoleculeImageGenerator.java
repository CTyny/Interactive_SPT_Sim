package Interactive_SPT_Sim;

import java.util.Random;

public class SingleMoleculeImageGenerator {
    
    private final Random r;

    public SingleMoleculeImageGenerator() {
        this.r = new Random();
    }
    
    public int[][] addCountsToImage (int[][] imageArray, int pixelRes, int realSize, int totalCounts, double airyDia, double [] entityPosition) {
        //create array to hold pixel counts
        //int[][] imageArray = new int[pixelRes][pixelRes];
        double pixelSize = realSize/pixelRes;
        System.out.println(entityPosition[0] + " " + entityPosition[1]);
            for (int j=0; j<totalCounts; j++) {
                double detX =getGauss(entityPosition[0], airyDia);
                double assessX = detX/pixelSize;
                int pixelX = (int) assessX;
                double detY =getGauss(entityPosition[1], airyDia);
                double assessY = detY/pixelSize;
                int pixelY = (int) assessY;
                //don't try to add counts when gaussian spread falls outside the image boundaries!
                if (pixelY<pixelRes && pixelX>=0 && pixelX<pixelRes && pixelY>=0) { 
                    imageArray[pixelX][pixelY] += 1;
                }
            }
        
        return imageArray;
    }
    
    public double getGauss (double mean, double stdeviation) {
        
        double g = mean + (r.nextGaussian()*stdeviation);
        return g;
    }
}
