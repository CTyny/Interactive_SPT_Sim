package Interactive_SPT_Sim;

import Interactive_SPT_Sim.systems.*;
import Interactive_SPT_Sim.utilities.SingleMoleculeImageGenerator;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import ij.ImagePlus;
import ij.gui.NewImage;


public class SPT_ECS {
    
    ImagePlus simImage;
    World microWorld;
    int pxRes = 1024;
    int [][] psfArray = SingleMoleculeImageGenerator.pixelatedPSF(pxRes, 81920, 2000, 280);//one time generation of oversampled psf for fast construction of pixel maps
    long lastTime; //hold the system time for previous render update
    
    public World worldBuilder() {
        
        simImage = NewImage.createImage("",pxRes, pxRes,1, 8, NewImage.FILL_BLACK);
        simImage.show();
        //Configure world
        //note: systems are called in the order they are added to WorldConfigurationBuilder
        WorldConfiguration simConfig = new WorldConfigurationBuilder()
        .with(new PopulationSystem(), new InitialisationSystem(), new BrownianSystem(), new MotionSystem(), new FluorescenceSystem(), new RenderSystem(simImage, psfArray)).build();
    
        World w = new World(simConfig);
        
        return w;
    }
    
    public void fluorophoreCreator(World w){
        //create entity 
        int e = w.create();    
    }
    
    public void simLoop(World w) {
        w.setDelta(0.0333f);//units set as whole seconds for now
        w.process();
    }
    
    public static void main (final String[] args) {
       
        SPT_ECS sim = new SPT_ECS();
        World microWorld = sim.worldBuilder();
        
        //create all entities at start, nothing bleaches (Quantum dot simulator lol) 
        //until I can figure out how to create/destroy entities in a system
        for (int i=0; i<600; i++){
            sim.fluorophoreCreator(microWorld);
        }
        //run a limited number of ticks for now
        for (int i=0; i<500; i++){
            sim.simLoop(microWorld);
            //System.out.println("tick");
        }
        System.exit(0);
    }
}


