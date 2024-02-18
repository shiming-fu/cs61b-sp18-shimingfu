package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;

public class Player implements Serializable{
    private Position pos;
    private TETile occupiedTile;
    Player(){
        pos = new Position();
    }
    /**
     * Set Player at the proper position
     */
    void setRandomPlayer(TETile[][] world, Random rand)
    {
        do {
            pos.x = rand.nextInt(world.length);
            pos.y = rand.nextInt(world[0].length);
            occupiedTile = world[pos.x][pos.y];
        } while(world[pos.x][pos.y]!= Tileset.FLOOR);
    }
    /**
     *  Add Player to the world
     */
    void addplayer(TETile[][] world)
    {
        if(world[pos.x][pos.y] == Tileset.WALL)
        {
            throw new RuntimeException("Players should not be in the wall");
        }
        world[pos.x][pos.y]=Tileset.PLAYER;
    }

    /**
     * Move the player up in the world
      */
    void moveUp(TETile[][]world)
    {
        Position lastPos = new Position(pos.x, pos.y);
        Position newPos = lastPos.up();
        if(isConflict(world,newPos))
        {
            return;
        }
        pos = newPos;
        setWorldStatus(world,lastPos);
    }
    void moveDown(TETile[][]world)
    {
        Position lastPos = new Position(pos.x, pos.y);
        Position newPos = lastPos.down();
        if(isConflict(world,newPos))
        {
            return;
        }
        pos = newPos;
        setWorldStatus(world,lastPos);
    }
    void moveLeft(TETile[][]world)
    {
        Position lastPos = new Position(pos.x, pos.y);
        Position newPos = lastPos.left();
        if(isConflict(world,newPos))
        {
            return;
        }
        pos = newPos;
        setWorldStatus(world,lastPos);
    }
    void moveRight(TETile[][]world)
    {
        Position lastPos = new Position(pos.x, pos.y);
        Position newPos = lastPos.right();
        if(isConflict(world,newPos))
        {
            return;
        }
        pos = newPos;
        setWorldStatus(world,lastPos);
    }
    /**
     *  return true if the present position is conflicted
     */
    boolean isConflict(TETile[][]world,Position p)
    {
        return p.x >= world.length || p.y>=world[0].length || p.y<0 || p.x<0
                || world[p.x][p.y].character() == Tileset.WALL.character()
                || world[p.x][p.y].character() == Tileset.NOTHING.character();
    }
    /**
     * After one move, record the status of the previous position
     */
    private void setWorldStatus(TETile[][]world, Position lastpos)
    {
        world[lastpos.x][lastpos.y] = occupiedTile;
        occupiedTile = world[pos.x][pos.y];
        world[pos.x][pos.y] = Tileset.PLAYER;
    }
}
