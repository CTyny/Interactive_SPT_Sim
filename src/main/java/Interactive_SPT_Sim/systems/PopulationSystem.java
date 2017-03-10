package Interactive_SPT_Sim.systems;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.utils.IntBag;
import java.util.Random;

//system to create and destroy entities in a way that mimics photoactivation and photobleaching
public class PopulationSystem extends BaseEntitySystem {
    int destroy;
    int create;
    
    public PopulationSystem() {
        super(Aspect.all());
        destroy = 1;//TODO: rather than be defined by constructor, these variables could be adjusted each loop in response to changes in photoactivation and bleaching rate
        create = 1;
    }
    
    @Override
    protected void processSystem(){
        Random r = new Random();
        
        //need to interate through entities and randomly select some for deletion
        IntBag actives = subscription.getEntities();
        int[] ids =actives.getData();
        
        int n = 0;
        int nPicked = 0;
        int nLeft = actives.size();
        //can't destroy more entities than there are
        if (nLeft > destroy){
            while ((destroy-nPicked) >0) {
                int rand = r.nextInt(nLeft);
                if (rand < (destroy-nPicked)) {
                    process(ids[n]);//destroy this entity!
                    nPicked++;
                }
                nLeft--;
                n++;
            }
        }
        //creation is easy
        for (int i=0; i<create; i++){
        int e = world.create();
        }
    }
    //process the entities this system in interested in
    protected void process(int entityID){
        world.delete(entityID);//if an entity gets processed by this system, it's doomed!  
    }
}
