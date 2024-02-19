package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.Random;

class Player implements Serializable {
    private Position pos;
    private TETile occupiedTile;

    Player() {
        pos = new Position();
    }

    /**
     * Set player at proper position (Used when initialize the game)
     * Violent algorithm.
     */
    void setRandomPlayer(TETile[][] world, Random rand) {
        do {
            pos.x = rand.nextInt(world.length);
            pos.y = rand.nextInt(world[0].length);
            occupiedTile = world[pos.x][pos.y];
        } while(world[pos.x][pos.y] != Tileset.FLOOR);
    }

    /**
     * Add player to the world.
     */
    void addPlayer(TETile[][] world) {
        if (world[pos.x][pos.y] == Tileset.WALL)
            throw new RuntimeException("Player shouldn't in wall");
        world[pos.x][pos.y] = Tileset.PLAYER;
    }

    /**
     * Move the player up in the world
     */
    void moveUp(TETile[][] world) {
        Position lastPos = new Position(pos.x, pos.y);
        Position newPos = pos.up();
        if (isConflict(world, newPos)) {
            return;
        }
        pos = newPos;
        setWorldStatus(world, lastPos);
    }

    void moveDown(TETile[][] world) {
        Position lastPos = new Position(pos.x, pos.y);
        Position newPos = pos.down();
        if (isConflict(world, newPos)) {
            return;
        }
        pos = newPos;
        setWorldStatus(world, lastPos);
    }

    void moveLeft(TETile[][] world) {
        Position lastPos = new Position(pos.x, pos.y);
        Position newPos = pos.left();
        if (isConflict(world, newPos)) {
            return;
        }
        pos = newPos;
        setWorldStatus(world, lastPos);
    }

    void moveRight(TETile[][] world) {
        Position lastPos = new Position(pos.x, pos.y);
        Position newPos = pos.right();
        if (isConflict(world, newPos)) {
            return;
        }
        pos = newPos;
        setWorldStatus(world, lastPos);
    }

    /**
     * Return true if the present position is conflicted.
     */
    boolean isConflict(TETile[][] world, Position p) {
        return p.x >= world.length || p.x < 0 || p.y >= world[0].length || p.y < 0 ||
                world[p.x][p.y].character() == Tileset.WALL.character() || world[p.x][p.y].character() == Tileset.NOTHING.character() ||
                world[p.x][p.y].character() == Tileset.LOCKED_DOOR.character();
    }

    /**
     * After One move, set tile in play's previous position.
     */
    private void setWorldStatus(TETile[][] world, Position lastPos) {
        world[lastPos.x][lastPos.y] = occupiedTile;
        occupiedTile = world[pos.x][pos.y];
        world[pos.x][pos.y] = Tileset.PLAYER;
    }
}