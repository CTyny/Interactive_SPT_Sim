
package Interactive_SPT_Sim.systems;

import Interactive_SPT_Sim.components.PositionComponent;
import Interactive_SPT_Sim.components.VelocityComponent;
import com.artemis.Aspect;
import com.artemis.systems.IteratingSystem;

public class BrownianSystem extends IteratingSystem {

    public BrownianSystem() {
        super(Aspect.all(PositionComponent.class, VelocityComponent.class));
    }

    @Override
    protected void process(int i) {
        //this needs to change velocityComponent to a new random velocity
    }
    
}
