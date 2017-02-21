package Interactive_SPT_Sim.components;

import com.artemis.Component;

public class PositionComponent extends Component {
    
    public float x, y;
    
    public PositionComponent(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public PositionComponent(){
        this(0,0);
    } 
}