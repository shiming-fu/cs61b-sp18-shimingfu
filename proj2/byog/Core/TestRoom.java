package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestRoom {

    @Test
    /* ####
     * #  #
     * #  #
     * ####
     */
    public void testAddRoom() {
        Position p = new Position(0 , 0);
        Size size = new Size(4, 4);
        Size worldSize = new Size(4, 4);
        Room r = new Room(p, size);

        TETile f = Tileset.FLOOR;
        TETile w = Tileset.WALL;
        TETile[][] exp = {{w, w, w, w}, {w, f, f, w}, {w, f, f, w}, {w, w, w, w}};

        Game game = new Game();
        game.createEmptyWorld(worldSize);
        r.addRoom(game.world);

        for (int i = 0; i < game.size().x; i++) {
            for (int j = 0; j < game.size().y; j++) {
                assertEquals(exp[i][j].character(), game.world[i][j].character());
            }
        }
    }
}