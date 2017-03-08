package Interactive_SPT_Sim.systems;

import Interactive_SPT_Sim.components.FluorescenceComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import java.util.Random;


public class FluorescenceSystem extends IteratingSystem {
    
    public FluorescenceSystem() {
        super(Aspect.all());
    }
    
    ComponentMapper <FluorescenceComponent> fluorescence;
    Random random = new Random();
    @Override
    protected void process(int e) {
        //TODO: more realistic blinking
        int r = random.nextInt(100); //generate random number from 0-99
        if (r <= 85) { //if number is 0-85(85% of possible numbers) then add fluorescence component as tag
            fluorescence.set(e, true);
        } else { // otherwise remove it
            fluorescence.set(e, false);
        } //component mapper only acts if component is/isn't there as appropriate
    }
    
}
