
package Interactive_SPT_Sim.systems;

import Interactive_SPT_Sim.components.FluorescenceComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import java.util.Random;


public class FluorescenceSystem extends IteratingSystem {
    
    ComponentMapper <FluorescenceComponent> fluorescence;
    public FluorescenceSystem() {
        super(Aspect.all());
    }

    @Override
    protected void process(int e) {
        Random random = new Random(); //fluorescence will be on with 0.8 probability
        int r = random.nextInt(10); //generate random number from 0-9
        if (r <= 7) { //if number is 0-7(80% of possible numbers) then add fluorescence component as tag
            fluorescence.set(e, true);
        } else { // otherwise remove it
            fluorescence.set(e, false);
        } //component mapper only acts if component is/isn't there as appropriate
    }
    
}
