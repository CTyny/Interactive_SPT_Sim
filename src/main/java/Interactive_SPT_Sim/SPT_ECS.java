package Interactive_SPT_Sim;

import Interactive_SPT_Sim.systems.*;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import ij.ImagePlus;
import ij.gui.NewImage;


public class SPT_ECS {
    
    ImagePlus simImage;
    World microWorld;
    int pxRes = 512;
    
    public World worldBuilder() {
        
        simImage = NewImage.createImage("",pxRes, pxRes,1, 8, NewImage.FILL_BLACK);
        simImage.show();
        //Configure world
        //note: systems are called in the order they are added to WorldConfigurationBuilder
        WorldConfiguration simConfig = new WorldConfigurationBuilder()
        .with(new InitialisationSystem(), new BrownianSystem(), new MotionSystem(), new FluorescenceSystem(), new RenderSystem(simImage)).build();
    
        World w = new World(simConfig);
        
        return w;
    }
    
    public void fluorophoreCreator(World w){
        //create entity 
        int e = w.create();
        
    }
    
    public void simLoop(World w) {
        w.setDelta(0.05f);//units set as whole seconds for now
        w.process();
    }
   
    public void regionOfInterest() {
        //TODO: Method for defining limits of an ROI (e.g. cell boundary)
    }
    
    public static void main (final String[] args) {
       
        SPT_ECS sim = new SPT_ECS();
        World microWorld = sim.worldBuilder();
        
        //create all entities at start, nothing bleaches (Quantum dot simulator lol) 
        //until I can figure out how to create/destroy entities in a system
        for (int i=0; i<20; i++){
            sim.fluorophoreCreator(microWorld);
        }
        //run a limited number of ticks for now
        for (int i=0; i<400; i++){
            sim.simLoop(microWorld);
        }
    }
}


