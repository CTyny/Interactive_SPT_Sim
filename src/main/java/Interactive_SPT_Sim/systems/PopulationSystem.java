package Interactive_SPT_Sim.systems;

import Interactive_SPT_Sim.components.PositionComponent;
import Interactive_SPT_Sim.components.VelocityComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import java.util.Random;


public class PopulationSystem extends IteratingSystem {
    
    public PopulationSystem() {
        super(Aspect.all());
    }
    ComponentMapper <PositionComponent> position;
    ComponentMapper <VelocityComponent> velocity;
    
    int pop = 1;
    
    
    @Override
    protected void process (int e){
        //destroy some entities!
        for (int i=0; i<pop; i++) {
            //create some entities!
        }
        
    }
}
