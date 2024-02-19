package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;

 public class Room {
    private Size size;
    private Position pos;//Left Bottom
    private static final int RMAX = 15;
    private static final int RMIN = 4;
    private static final int CMAX = 7;
    Room(Position pos, Size size)
    {
        if(size.y<=2 || size.x<=2) {
            throw new RuntimeException("The room is too small");
        }
        this.pos = pos;
        this.size = size;
    }
    Room()
    {

    }
    /**
     * Add a room to the world
     */
    public void addRoom(TETile[][]world)
    {
        Position boundary = new Position(pos.x + size.x -1,pos.y+size.y-1);
        /* up and down */
        for(int i = pos.x; i <= boundary.x;i++)
        {
            world[i][pos.y] = Tileset.WALL;
            world[i][boundary.y] = Tileset.WALL;
        }
        /*left and right*/
        for(int i = pos.y; i <= boundary.y;i++)
        {
            world[pos.x][i]=Tileset.WALL;
            world[boundary.x][i] = Tileset.WALL;
        }
        /*middle*/
        for(int i = pos.x + 1;i < boundary.x; i++)
        {
            for(int j = pos.y + 1; j < boundary.y; j++)
            {
                world[i][j] = Tileset.FLOOR;
            }
        }
    }
    Position[] addRandomRoom (Random random,Position c,TETile[][] world)
    {
        int tryTimes = 0;
        /*Randomly generate room*/
        do {
            size = new Size(RandomUtils.uniform(random, RMIN, RMAX), RandomUtils.uniform(random, RMIN, RMAX));
            /* Make sure connection(c) is on the wall of room */
            pos = getRandomPosWithRoom(random, c);
            tryTimes++;
        }while(isConflict(world) && tryTimes <=100);
        if(isConflict(world))
        {
            return null;
        }
        addRoom(world);
        world[c.x][c.y] = Tileset.FLOOR;
        /*Add new connections*/
        Position[] news = new Position[RandomUtils.uniform(random, CMAX) + 1];
        for (int i = 0; i < news.length; i++) {
            news[i] = getRandomPosWithRoom(random, pos);
        }
        return news;
    }
    /*Make sure that the connection is on the wall*/
    private Position getRandomPosWithRoom(Random random,Position p)
    {
        Position boundary;
        double select = RandomUtils.uniform(random);
        Position gp = new Position();
        if(p == pos)
        {
            boundary = new Position(p.x+size.x-1,p.y+size.y-1);
            if (select < 0.25) {
                gp.x = RandomUtils.uniform(random, p.x + 1, boundary.x);
                gp.y = p.y;
            } else if (select < 0.5) {
                gp.x = RandomUtils.uniform(random, p.x + 1, boundary.x);
                gp.y = boundary.y;
            } else if (select < 0.75) {
                gp.y = RandomUtils.uniform(random, p.y + 1, boundary.y);
                gp.x = p.x;
            } else {
                gp.y = RandomUtils.uniform(random, p.y + 1, boundary.y);
                gp.x = boundary.x;
            }
        }
        else { // get pos
            boundary  = new Position(p.x - size.x + 1, p.y - size.y + 1);
            if (select < 0.25) {
                gp.x = RandomUtils.uniform(random, boundary.x + 1, p.x);
                gp.y = p.y;
            } else if (select < 0.5) {
                gp.x = RandomUtils.uniform(random, boundary.x + 1, p.x);
                gp.y = boundary.y;
            } else if (select < 0.75) {
                gp.y = RandomUtils.uniform(random, boundary.y + 1, p.y);
                gp.x = p.x;
            } else {
                gp.y = RandomUtils.uniform(random, boundary.y + 1, p.y);
                gp.x = boundary.x;
            }
        }
        return gp;
        }
    /* return true if the position is occupied*/
    boolean isConflict(TETile[][]world)
    {
        Position boundary = new Position(size.x+pos.x-1,size.y+pos.y-1);
        for(int i = pos.x + 1 ;i < boundary.x; i++)
        {
            for(int j = pos.y+1;j< boundary.y;j++)
            {
                if(i-1<0||j-1<0 ||i + 1 >= world.length ||j+1>= world[0].length||world[i][j].character()!=Tileset.NOTHING.character())
                {
                    return true;
                }
            }
        }
        return false;
    }


}
