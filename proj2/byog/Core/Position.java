package byog.Core;

public class Position extends Coordinate{
    int x;
    int y;

    public Position(int x, int y) {
        super(x,y);
    }
    Position()
    {
        super();
    }
    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
        {
            return true;
        }
        if(obj == null)
        {
            return false;
        }
        if(getClass() != obj.getClass())
        {
            return false;
        }
        Position other = (Position) obj;
        return other.x == this.x && other.y==this.y;
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
