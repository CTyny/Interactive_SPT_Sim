package Interactive_SPT_Sim.systems;

import Interactive_SPT_Sim.components.PositionComponent;
import Interactive_SPT_Sim.components.VelocityComponent;
import Interactive_SPT_Sim.utilities.ZoneHandler;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import java.util.Random;


public class InitialisationSystem extends IteratingSystem {
    
    ComponentMapper <PositionComponent> position;
    ComponentMapper <VelocityComponent> velocity;
    
    public InitialisationSystem() {
        super(Aspect.exclude(PositionComponent.class, VelocityComponent.class));//system acts on entities with no position or velocity i.e. that were created at initialisation or in the last game loop
    }
    
    @Override
    protected void process(int e) {
    
    Random random = new Random();
    
    PositionComponent p = position.create(e);
    
    boolean inside = false;
    float[] testXY = new float[2];
    while (inside ==false){
        testXY[0]= random.nextInt(81920);//physical size of imaged region is square with side 81920nm.
        testXY[1]= random.nextInt(81920);// position acurrate to nearest nm
        inside = ZoneHandler.insideZone(testXY, 81920);
    }
    p.x = testXY[0];
    p.y = testXY[1];
    
    VelocityComponent v = velocity.create(e); //don't need to give entity velocity vector here, first loop of BrownianSystem will do that
    }
}
