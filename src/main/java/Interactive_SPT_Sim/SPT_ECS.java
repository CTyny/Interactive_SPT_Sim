package Interactive_SPT_Sim;

import Interactive_SPT_Sim.systems.*;
import Interactive_SPT_Sim.components.*;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;


public class SPT_ECS {
    
    public World worldBuilder() {
        
        //Configure world
        //note: systems are called in the order they are added to WorldConfigurationBuilder
        WorldConfiguration simConfig = new WorldConfigurationBuilder()
        .with(new PopulationSystem(), new MotionSystem(), new FluorescenceSystem(), new BrownianSystem(), new TrackRenderSystem()).build();
    
        World w = new World(simConfig);
        
        return w;
    }
    
    public void fluorophoreCreator(World w){
        //create entity 
        int e = w.create();
        
    }
    
    public void simLoop(World w) {
        w.setDelta(1);
        w.process();
    }
    
    public static void main (final String[] args) {
       
        SPT_ECS sim = new SPT_ECS();
        World microWorld = sim.worldBuilder();
        sim.fluorophoreCreator(microWorld);
        for (int i=0; i<10; i++){
            sim.simLoop(microWorld);
        }
        //new SPT_ECS().simLoop(); //never ending simulation!!!
    }
}


