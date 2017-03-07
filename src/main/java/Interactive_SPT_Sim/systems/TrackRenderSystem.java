package Interactive_SPT_Sim.systems;

import Interactive_SPT_Sim.SingleMoleculeImageGenerator;
import Interactive_SPT_Sim.components.FluorescenceComponent;
import Interactive_SPT_Sim.components.PositionComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import java.util.Arrays;


public class TrackRenderSystem extends IteratingSystem {
    
    ComponentMapper<FluorescenceComponent> fluorescence;
    ComponentMapper<PositionComponent> position;
    ImagePlus outputImage;
    int pixR;
    int[][] pixValues;
    
    
    public TrackRenderSystem (ImagePlus outputImage){
        super(Aspect.all());
        this.outputImage = outputImage;
        pixR = outputImage.getWidth();
        pixValues = new int [pixR][pixR];
    }
    //TODO: how to reset array at start of loop? if put into process(e) will reset every entity. 
    protected void resetArray(){
        for (int i=0; i<pixR; i++){
            Arrays.fill(pixValues[i],0); // need to reset this array or all changes are cumulative TODO: could take advantage of this for 'smear' control
        }
    }
    
    @Override
    protected void process(int e) {
        
        ImageProcessor ip = outputImage.getProcessor();
        double eLoc [] = new double [2];
        
        PositionComponent p = position.getSafe(e, null);
        eLoc[0] = p.x;
        eLoc[1] = p.y;

        SingleMoleculeImageGenerator smig = new SingleMoleculeImageGenerator();
        pixValues = smig.addCountsToImage(pixValues, pixR, 81920, 1500, 150, eLoc);
        
        for (int i=0; i<pixR; i++) {
            for (int j=0; j <pixR; j++){
                ip.putPixel(i, j, pixValues[i][j]);
            }
        }
        outputImage.updateAndDraw();
    }
}