
package Interactive_SPT_Sim.systems;

import Interactive_SPT_Sim.components.PositionComponent;
import Interactive_SPT_Sim.components.VelocityComponent;
import com.artemis.Aspect;
import com.artemis.systems.IteratingSystem;

public class MotionSystem extends IteratingSystem {
    
    public MotionSystem() {
        super(Aspect.all(PositionComponent.class, VelocityComponent.class));
    }
    
    @Override
    protected void process(int i) {
        //This needs to contain something that changes the positionComponent based on the velocityComponent
    }
}
