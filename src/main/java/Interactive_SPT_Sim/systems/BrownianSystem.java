package Interactive_SPT_Sim.systems;


import Interactive_SPT_Sim.components.VelocityComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import java.util.Random;

public class BrownianSystem extends IteratingSystem {
     
    public BrownianSystem() {
        super(Aspect.all(VelocityComponent.class));
    }
    ComponentMapper<VelocityComponent> velocity;
    
    Random r = new Random();
    
    float diffConst = 70000;// square namometres per second TODO: How should temperature change Diffusion Const?
    
    
    @Override
    protected void process(int e) {
        //change velocityComponent to a new velocity vector to simulate brownian 
        //motion (new velocity randomly chosen with gaussian dist, mean 0.0 and variance 
        float variance = 2*diffConst*world.getDelta();//variance = 2 x D x time
        VelocityComponent vel = velocity.get(e);
        vel.vx = (float) (variance*(r.nextGaussian()));
        vel.vy = (float) (variance*(r.nextGaussian()));
    }
    
}
