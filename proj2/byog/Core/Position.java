package byog.Core;

public class Position {
    int x;
    int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    Position()
    {

    }

    /**
     * Return middle position.
     */
    public Position middlePosition(Position des) {
        int middleX = des.x - this.x;
        int middleY = des.y - this.y;
        return new Position((int) (this.x + 0.5 * middleX), (int) (this.y + 0.5 * middleY));
    }
    public Position up()
    {
        return new Position(x,y+1);
    }
    public Position down()
    {
        return new Position(x,y-1);
    }
    public Position right()
    {
        return new Position(x+1,y);
    }
    public Position left()
    {
        return new Position(x-1,y);
    }




}
