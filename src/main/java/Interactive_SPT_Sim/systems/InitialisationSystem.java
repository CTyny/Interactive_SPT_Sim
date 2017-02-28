package Interactive_SPT_Sim.systems;

import Interactive_SPT_Sim.components.PositionComponent;
import Interactive_SPT_Sim.components.VelocityComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import java.util.Random;


public class InitialisationSystem extends IteratingSystem {
    
    ComponentMapper <PositionComponent> position;
    ComponentMapper <VelocityComponent> velocity;
    
    public InitialisationSystem() {
        super(Aspect.exclude(PositionComponent.class, VelocityComponent.class));//system acts on entities with no position or velocity - should only occur in the first simulation loop!
    }
    
    @Override
    protected void process(int e) {
    
    Random random = new Random();
    
    PositionComponent p = position.create(e);
    p.x = random.nextInt(81920);//physical size of imaged region is square with side 81920nm.
    p.y = random.nextInt(81920);// position acurrate to nearest nm
    //TODO: add logic here to check if position falls within an roi (e.g. cell boundary) and reassign new random co-oords until it does
    VelocityComponent v = velocity.create(e); //don't need to give entity velocity vector here, first loop of BrownianSystem will do that
    }
}
