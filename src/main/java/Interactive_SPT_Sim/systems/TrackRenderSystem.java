package Interactive_SPT_Sim.systems;

import Interactive_SPT_Sim.components.FluorescenceComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;


public class TrackRenderSystem extends IteratingSystem {
    
    ComponentMapper<FluorescenceComponent> fluorescence;
    public TrackRenderSystem (){
        super(Aspect.all());
}

    @Override
    protected void process(int e) {
        if (fluorescence.has(e)){
            System.out.println("*");
        } else {
            System.out.println("-");
        }    
    }
}
