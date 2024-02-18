package byog.Core;
import java.io.Serializable;
abstract class Coordinate implements Serializable {
    int x,y;
    Coordinate(int x,int y)
    {
        this.x = x;
        this.y = y;
    }
    Coordinate(){

    }
}
