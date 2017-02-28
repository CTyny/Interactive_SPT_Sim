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
    protected void process(int e) {
        //TODO: change the entities positionComponent co-ordinates based on it's velocityComponent vector
        //TODO: check new position falls inside ROI
        //TODO: if new position outside reflect (i.e. reverse direction of vector) and retest
    }
}
