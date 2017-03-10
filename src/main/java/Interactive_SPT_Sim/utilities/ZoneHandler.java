package Interactive_SPT_Sim.utilities;

//Utility class for defining boundaries and testing whether a position falls within them
public final class ZoneHandler {

    public ZoneHandler(){
        //static methods only
    }
    
    public static boolean insideZone (float[] position){
        //TODO: chnage diamond to something more cell shaped
        //TODO: fast checks for coarse zone boundaries (a
        double[][] vertices = {{(81920/4),(81920/2)},{(81920/2),(3*81920/4)},{(3*81920/4),(81920/2)},{(81920/2),(81920/4)},{(81920/4),(81920/2)}};
        
        for (int i=0; i<vertices.length; i++){
            vertices[i][0] -= position[0];
            vertices[i][1] -= position[1];
        }
        float wN =0;
        for (int i=0; i<(vertices.length-1); i++){
            if ((vertices[i][1]*vertices[i+1][1]) <0){
                double crossPoint = vertices[i][0] + (vertices[i][1]*((vertices[i+1][0]-vertices[i][0])/(vertices[i][1]-vertices[i+1][1])));
                if (crossPoint>0) {
                    if (vertices[i][1]<0){
                        wN +=1;
                    } else {
                        wN -=1;
                    }
                }
            } else if (vertices[i][1] == 0 && vertices [i][0] > 0) {
                if (vertices[i+1][1] > 0) {
                    wN += 0.5;
                } else {
                    wN -= 0.5;
                }
            } else if (vertices[i+1][1] == 0 && vertices [i+1][0] > 0) {
                if (vertices[i+1][1] < 0) {
                    wN += 0.5;
                } else {
                    wN -= 0.5;
                }
            }
        }
        boolean decision;
        if (wN == 0) {
            decision = false;
        } else {
            decision = true;
        }    
        return decision;
    }
}
