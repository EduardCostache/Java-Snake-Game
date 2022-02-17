import java.awt.*;

public class Walls {
    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }


    private int startX;
    private int startY;
    private int endX;
    private int endY;

    Walls(int startX, int startY, int endX, int endY, int UNIT_SIZE, Graphics g)
    {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        g.setColor(Color.GRAY);
        for(int i = startY; i<=endY; i++)
        {
            for(int j = startX; j<=endX; j++)
            {
                g.fillRect(UNIT_SIZE*j, UNIT_SIZE*i, UNIT_SIZE, UNIT_SIZE);
            }
        }
    }
}
