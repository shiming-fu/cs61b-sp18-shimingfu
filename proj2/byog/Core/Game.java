package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import javax.imageio.IIOException;
import java.io.*;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    Player player;
    TETile[][] world;
    Door door;
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final int MAXFAILTIME = 1000;
    private Size size = new Size(WIDTH,HEIGHT);

    public Game()
    {
        createEmptyWorld(size());
        player = new Player();
        door = new Door();
    }

    Size size()
    {
        return size;
    }
    void createEmptyWorld(Size size)
    {
        this.size = new Size(size.x,size.y);
        world = new TETile[size.x][size.y];
        initWorld();
    }
    private void initWorld()
    {
        for(int x = 0; x < size().x; x++)
        {
            for(int y = 0;y<size().y;y++)
            {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }



    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        ter.initialize(size().x, size().y);
        String input = "";
        boolean isPlaying = false;
        ter.showMenu();
        while(true)
        {
            if(!StdDraw.hasNextKeyTyped())
            {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            input += key;
            if(playWithInputString(input) != null)
            {
                isPlaying = true;
                ter.renderFrame(world);
                input = "";//reset the input string
            }
            if(isPlaying)
            {
                detectMovePlayer(key);
            }
        }
    }
    private void detectMovePlayer(char key)
    {
        switch (key)
        {
            case 'w':
                player.moveUp(world);
                break;
            case 'a':
                player.moveLeft(world);
                break;
            case's':
                player.moveDown(world);
                break;
            case'd':
                player.moveRight(world);
                break;
        }
        ter.renderFrame(world);
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
    int option = getOption(input);
    String sSeed = getSeed(input);
    TETile[][] finalWorldState = null;
    switch(option)
    {
        case 0:{
            long seed = Long.parseLong(sSeed);
            Random random  = new Random(seed);
            finalWorldState = generateWorld(random);
            break;
        }
        case 1:{
            finalWorldState = loadGame();
            break;
        }
        case 2:{
            saveGame();
            System.exit(0);
        }
    }
    return finalWorldState;
    }
    /**
     * Save information of the player and the world
     */
    private void saveGame()
    {
        try{
            FileOutputStream fileout1  = new FileOutputStream("savePlayer.ser");
            FileOutputStream fileout2 = new FileOutputStream("saveWorld.ser");
            ObjectOutputStream playerout = new ObjectOutputStream(fileout1);
            ObjectOutputStream worldOut  =new ObjectOutputStream(fileout2);
            playerout.writeObject(player);
            worldOut.writeObject(world);
            playerout.close();
            worldOut.close();
            fileout1.close();
            fileout2.close();
        }catch(IOException i)
        {
            i.printStackTrace();
        }
    }

    /**
     * load the world in the files
     */
    private TETile[][] loadGame()
    {
        try{
            FileInputStream fileIn1 = new FileInputStream("savePlayer.ser");
            FileInputStream fileIn2 = new FileInputStream("SaveWorld.ser");
            ObjectInputStream playerIn = new ObjectInputStream(fileIn1);
            ObjectInputStream worldIn = new ObjectInputStream(fileIn2);
            player  = (Player) playerIn.readObject();
            world = (TETile[][]) worldIn.readObject();
            playerIn.close();
            worldIn.close();
            fileIn1.close();
            fileIn2.close();
        }catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }catch(IOException i){
            i.printStackTrace();
            System.exit(1);
        }catch(ClassNotFoundException c){
            System.out.println("Employee class Not found");
            c.printStackTrace();
            System.exit(1);
        }
        size.x = world.length;
        size.y = world[0].length;
        return world;
    }

    /**
    Generate random world
     **/


    private TETile[][] generateWorld(Random random)
    {
        initWorld();
        Connection cons = new Connection();
        cons.addFirstCon(size,random);
        Position c = cons.connections.poll();
        boolean isFirstConnected = false;
        Position tmp = c;
        int failTimes = 0;
        while(c != null && failTimes<MAXFAILTIME)
        {
            Room room = new Room();
            Position[] news = room.addRandomRoom(random,c,world);
            if(news == null)
            {
                failTimes++;
            }
            else{
                for(int i = 0;i<news.length;i++)
                {
                    cons.connections.offer(news[i]);
                }
            }
            c = cons.connections.poll();
            if(tmp.equals(c))
            {
                isFirstConnected = true;
            }
        }
        if(!isFirstConnected)
        {
            world[tmp.x][tmp.y]=Tileset.WALL;
        }
    player.setRandomPlayer(world,random);
    player.addplayer(world);
    return world;
    }

    static int getOption(String input)
    {
        String regexNew="(?i).*(N[1-9][0-9]*|0)S";
        String regexLoad = "(?i)^L.*";
        String regexSave ="(?i).*(:Q)$";
        if(input.matches(regexNew)) {
            return 0;
        }
        if(input.matches(regexLoad))
        {
            return 1;
        }
        if(input.matches(regexSave))
        {
            return 2;
        }
        return -1;//Invalid input
    }
     static String getSeed(String input)
    {
        String regexSplit = "(?i)N|S";
        String[] ret = input.split(regexSplit);
        String regexFind = "[1-9][0-9]*|0";
        for(String s:ret)
        {
            if(s.matches((regexFind)))
            {
                return s;
            }
        }
        return "";
    }
}
