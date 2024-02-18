package byog.Core;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
public class Connection {
    Queue<Position> connections;
    Connection()
    {
        connections = new LinkedList();
    }
    /**
     * Add a connection which is restricted by the size s
     */
    void addFirstCon(Size s,Random random)
    {
        int x = RandomUtils.uniform(random,10,s.x-10);
        int y = RandomUtils.uniform(random,10,s.y-10);
        Position p  = new Position(x,y);
        connections.offer(p);
    }

}
