package Interactive_SPT_Sim;

import Interactive_SPT_Sim.systems.*;
import Interactive_SPT_Sim.components.*;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;


public class SPT_ECS {
    
    public class world {
    //Configure world
    //note: systems are called in the order they are added to WorldConfigurationBuilder    
    WorldConfiguration simConfig = new WorldConfigurationBuilder()
    .with(new MotionSystem(), new FluorescenceSystem(), new BrownianSystem()).build();

    World microWorld = new World(simConfig);

    int e = microWorld.create();

    ComponentMapper<FluorescenceComponent> fluorescence;
    //ComponentMapper<PositionComponent> position;
    //ComponentMapper<VelocityComponent> velocity;

    FluorescenceComponent fluor = fluorescence.create(e);
    //PositionComponent pos = position.create(e);
    //VelocityComponent vel = velocity.create(e);

    for (int j=0; j<10; j++){
            microWorld.setDelta(j);
            microWorld.process();
        }
    }
    public static void main (final String...args) {
        
        
    }
}


