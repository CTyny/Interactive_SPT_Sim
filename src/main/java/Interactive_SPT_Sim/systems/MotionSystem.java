package Interactive_SPT_Sim.systems;

import Interactive_SPT_Sim.components.PositionComponent;
import Interactive_SPT_Sim.components.VelocityComponent;
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
        
        pos.x += vel.vx*world.getDelta();
        pos.y += vel.vy*world.getDelta();
    }
}
