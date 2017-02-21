package Interactive_SPT_Sim.components;

import com.artemis.Component;

public class VelocityComponent extends Component {
    
    public float vx, vy;
    
    public VelocityComponent (float vx, float vy){
        this.vx = vx;
        this.vy = vy;
    }
    
    public VelocityComponent(){
        this(0,0);
    }
}

