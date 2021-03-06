package Interactive_SPT_Sim.systems;

import Interactive_SPT_Sim.utilities.SingleMoleculeImageGenerator;
import Interactive_SPT_Sim.components.FluorescenceComponent;
import Interactive_SPT_Sim.components.PositionComponent;
import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import java.util.Arrays;

//This system needs to perform tasks before and after iterating through entities. 
//Extends BaseEntitySystem so that ProcessSystem can be overidden.
//Is actually a non-abstract version of the abstract IteratingSystem
public class RenderSystem extends BaseEntitySystem {
    
    ComponentMapper<FluorescenceComponent> fluorescence;
    ComponentMapper<PositionComponent> position;
    ImagePlus outputImage;
    int pixR;//pixel resolution of image
    int [][] psfArray;
    int[][] pixValues;//array to store pixel values as they are generated for each fluorescent entity
    double [] eLoc;//store entity position each iteration
    double [][] eLocs;//array to store entity positions for rendering
    long lastTime;
    
    public RenderSystem (ImagePlus outputImage, int[][] psfArray){
        super(Aspect.all(FluorescenceComponent.class));//only interested in entities tagged with FluorescenceComponent
        this.outputImage = outputImage;
        this.psfArray = psfArray;
        pixR = outputImage.getWidth();
        pixValues = new int [pixR][pixR];
        eLoc = new double [2];
    }
    
    //process an entity this system is interested in. All we need right now is to get all of their positions for creating a pixel map with
    //SingleMoleculeImageGenerator
    protected void process(int entityId) {
        PositionComponent p = position.getSafe(entityId, null);
        eLoc[0] = p.x;
        eLoc[1] = p.y;   
    }
    
    @Override
    protected void processSystem() {
        //reset the array that will store pixel values at the start of every tick (otherwise sequential images accumulate)
        for (int i=0; i<pixR; i++){
            Arrays.fill(pixValues[i],0);
        }
        
        //get entity IDs that this sytem is interested in
        IntBag actives = subscription.getEntities();
        int[] ids =actives.getData();
        //Iterate through entitiesto get their positions in a usable format
        eLocs = new double [actives.size()][2];
        for (int i=0; i<actives.size(); i++){
            process(ids[i]);
            eLocs[i][0] = eLoc[0];
            eLocs[i][1] = eLoc[1];
        }
        
        //Now we scent entity have all the fluorescent entity positions, we can generate a pixel map and draw the image
        ImageProcessor ip = outputImage.getProcessor();
        pixValues = SingleMoleculeImageGenerator.addCountsToImage(pixValues,81920, psfArray, eLocs);
        for (int i=0; i<pixR; i++) {
            for (int j=0; j <pixR; j++){
                ip.putPixel(i, j, pixValues[i][j]);
            }
        }
        //Now we find out how long it's been since the last image update (lastTime) and if this is less than the world Delta wait for the difference
        double nanoDelta = (world.getDelta()*1000000000);
        double timeToWait = nanoDelta - (System.nanoTime()-lastTime);
        if (timeToWait >0){
            IJ.wait((int)Math.round(timeToWait/1000000));
        }
        //update ImagePlus object
        outputImage.updateAndDraw();
        lastTime = System.nanoTime();
        //TODO: Need a better way of regulating draw time-step.
    }
}