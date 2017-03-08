package Interactive_SPT_Sim.systems;

import Interactive_SPT_Sim.SingleMoleculeImageGenerator;
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
import java.util.Random;

//This system needs to perform tasks before and after iterating through entities. 
//Extends BaseEntitySystem so that ProcessSystem can be overidden.
//Is actually a non-abstract version of the abstract IteratingSystem
public class RenderSystem extends BaseEntitySystem {
    
    ComponentMapper<FluorescenceComponent> fluorescence;
    ComponentMapper<PositionComponent> position;
    ImagePlus outputImage;
    int pixR;//pixel resolution of image
    int[][] pixValues;//array to store pixel values as they are generated for each fluorescent entity
    
    public RenderSystem (ImagePlus outputImage){
        super(Aspect.all(FluorescenceComponent.class));//only interested in entities tagged with FluorescenceComponent
        this.outputImage = outputImage;
        pixR = outputImage.getWidth();
        pixValues = new int [pixR][pixR];
    }
    
    //process an entity this system is interested in
    protected void process(int entityId) {
        double eLoc [] = new double [2];
        ImageProcessor ip = outputImage.getProcessor();
        PositionComponent p = position.getSafe(entityId, null);
        eLoc[0] = p.x;
        eLoc[1] = p.y;
        Random r = new Random();
        int counts = (int) (1500+(r.nextGaussian()*0.5));//add some variation to total counts for each molecule - not sure this is having much impact!
        SingleMoleculeImageGenerator smig = new SingleMoleculeImageGenerator();
        pixValues = smig.addCountsToImage(pixValues, pixR, 81920, counts, 150, eLoc);
        
        for (int i=0; i<pixR; i++) {
            for (int j=0; j <pixR; j++){
                ip.putPixel(i, j, pixValues[i][j]);
            }
        }
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
        //Iterate through entities
        for (int i=0; i<actives.size(); i++){
            process(ids[i]);
        }
        //update ImagePlus object
        IJ.wait(50);//wait to update. TODO: figure out how to get world Delta then convert to millisecond wait time here
        outputImage.updateAndDraw();
    }
}