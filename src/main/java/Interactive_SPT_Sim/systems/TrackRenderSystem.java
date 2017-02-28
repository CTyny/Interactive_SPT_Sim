package Interactive_SPT_Sim.systems;

import Interactive_SPT_Sim.components.FluorescenceComponent;
import Interactive_SPT_Sim.components.PositionComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;


public class TrackRenderSystem extends IteratingSystem {
    
    ComponentMapper<FluorescenceComponent> fluorescence;
    ComponentMapper<PositionComponent> position;
    
    public TrackRenderSystem (){
        super(Aspect.all());
}

    @Override
    protected void process(int e) {
        PositionComponent p = position.getSafe(e, null);
        float x = p.x;
        float y = p.y;
        if (fluorescence.has(e)){
            System.out.println("x = " + x + " y = " + y + "*");
        } else {
            System.out.println("x = " + x + " y = " + y + "-");
        }    
    }
}
