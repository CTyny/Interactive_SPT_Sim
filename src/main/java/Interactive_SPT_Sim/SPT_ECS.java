package Interactive_SPT_Sim;

import Interactive_SPT_Sim.systems.*;
import Interactive_SPT_Sim.components.*;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;


public class SPT_ECS {
    
    //Configure world
    //note: systems are called in the order they are added to WorldConfigurationBuilder
    int chipSize = 512;
    double pixelSize = 160;
    double fov = chipSize*pixelSize; //field of view size in nm
    
    WorldConfiguration simConfig = new WorldConfigurationBuilder()
    .with(new MotionSystem(), new FluorescenceSystem(), new BrownianSystem(), new TrackRenderSystem()).build();
    
    World microWorld = new World(simConfig);

    ComponentMapper<FluorescenceComponent> fluorescence;
    ComponentMapper<PositionComponent> position;
    ComponentMapper<VelocityComponent> velocity;

    //having these here causes build failure(?!) need to figure out why!
    
    //VelocityComponent vel = velocity.create(e);
    
    public void fluorophoreCreator(){
        //create fluorophore entity with random initial position and velocity
        int e = microWorld.create();
        
        position.set(e, true);
        //pos.x = 10000;
        //pos.y = 10000;
    }
    
    public void simLoop() {
        microWorld.setDelta(1);
        microWorld.process();
    }
    
    public static void main (final String[] args) {
       
        SPT_ECS sim = new SPT_ECS();
        sim.fluorophoreCreator();
        for (int i=0; i<10; i++){
            sim.simLoop();
        }
        //new SPT_ECS().simLoop(); //never ending simulation!!!
    }
}


