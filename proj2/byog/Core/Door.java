package byog.Core;
import byog.TileEngine.Tileset;
import byog.TileEngine.TETile;
import org.w3c.dom.html.HTMLTitleElement;

import java.util.*;

/**
 * The door connects floor to nothing
 * Position rules: around the pos
 * There are two walls,one nothing and one floor
 */
public class Door {
    Position pos;
    boolean locked;
    /**
     * Create a door initially locked
     */
    Door()
    {
        pos = new Position();
        locked = true;
    }
    /**
     * Return the random & proper position int world.
     */
    void setRandomDoor (TETile[][] world,Random random)
    {
        pos = getRandomDoorPos(world, random);
        world[pos.x][pos.y]=Tileset.LOCKED_DOOR;
    }
    private Position getRandomDoorPos(TETile[][]world, Random random)
    {
        List<Position>pList = getValidPosList(world);
        int i = random.nextInt(pList.size());
        return pList.get(i);
    }

    /**
     * Get all the valid position for the door in the world
     * @param world
     * @return pList
     */
    private List<Position> getValidPosList(TETile[][]world)
    {
        List<Position> pList = new ArrayList<>();
        for(int i = 0;i< world.length;i++)
        {
            for(int j = 0;j<world[0].length;j++)
            {
                Position p = new Position(i,j);
                if(isDoor(world,p))
                {
                    pList.add(p);
                }
            }
        }
        return pList;
    }

    /**
     * return true if the position can be the door
     * @param world
     * @param p
     */
    private boolean isDoor (TETile[][] world, Position p)
    {
        Map<TETile,Integer> tileMap= cntSurroundings(world,p);
        return tileMap.get(Tileset.WALL)==2 && tileMap.get(Tileset.FLOOR) == 1 && tileMap.get(Tileset.NOTHING) == 1;
    }
    private Map<TETile,Integer> cntSurroundings(TETile[][]world,Position p)
    {
        //init the map
        Map<TETile,Integer> tileMap = new HashMap<>(){{
        put(Tileset.NOTHING,0);
        put(Tileset.WALL,0);
        put(Tileset.FLOOR,0);
    }
        };
        //The p can't be at the edge of the map
        if(p.x>0 && p.x< world.length -1 && p.y>0 && p.y< world[0].length-1)
        {
            Position newPos = p.up();
            cntValue(tileMap,world,p);
            newPos = p.down();
            cntValue(tileMap,world,p);
            newPos = p.left();
            cntValue(tileMap,world,p);
            newPos = p.right();
            cntValue(tileMap,world,p);
        }
        return tileMap;
    }

    private void cntValue(Map<TETile,Integer>tileMap,TETile[][]world,Position p )
    {
        TETile key = world[p.x][p.y];
        Integer cnt = tileMap.get(key);
        if(tileMap.containsKey(key))
        {
            tileMap.put(key,cnt+1);
        }
    }
}
