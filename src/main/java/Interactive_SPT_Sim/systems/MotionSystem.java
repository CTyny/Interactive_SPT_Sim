package Interactive_SPT_Sim.systems;

import Interactive_SPT_Sim.components.PositionComponent;
import Interactive_SPT_Sim.components.VelocityComponent;
import Interactive_SPT_Sim.utilities.ZoneHandler;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;

public class MotionSystem extends IteratingSystem {
    
    public MotionSystem() {
        super(Aspect.all(PositionComponent.class, VelocityComponent.class));
    }
    
    ComponentMapper<PositionComponent> position;
    ComponentMapper<VelocityComponent> velocity;
    
    @Override
    protected void process(int e) {
        PositionComponent pos = position.get(e);
        VelocityComponent vel = velocity.get(e);
        
    boolean inside = false;
    float[] testXY = new float[2];
    testXY[0] = pos.x + (vel.vx*world.getDelta());
    testXY[1] = pos.y + (vel.vy*world.getDelta());
    inside = ZoneHandler.insideZone(testXY);
    if (inside ==true) {
        pos.x = testXY[0];
        pos.y = testXY[1];
    }
    }
}
