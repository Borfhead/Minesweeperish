package cs2410.assn8.view;



import javafx.scene.control.Button;

/**
 * @author Dylan Cox
 */
public class Cell extends Button {

    private boolean isBomb;
    private int bombsNearby;
    private int xCoord;
    private int yCoord;
    private boolean isSelected;
    public Cell()
    {
        this.getStylesheets().add("cs2410/assn8/resources/cell.css");
        this.setId("cell");
        this.setPrefWidth(30);
        this.setPrefHeight(30);
        isBomb = false;
        bombsNearby = 0;
        isSelected = false;

    }

    public Cell(boolean isBomb)
    {
        if(isBomb)
        {
            this.isBomb = true;
        }
        this.getStylesheets().add("cs2410/assn8/resources/cell.css");
        this.setId("bomb");
        this.setPrefWidth(30);
        this.setPrefHeight(30);

    }

    public void incrementBombsNearby()
    {
        bombsNearby++;
    }

    public int getBombsNearby()
    {
        return bombsNearby;
    }

    public boolean isBomb()
    {
        return isBomb;
    }

    public void select()
    {
        this.setDisable(true);
        this.setText(Integer.toString(bombsNearby));
        isSelected = true;
    }

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setCoordinates(int x, int y)
    {
        xCoord = x;
        yCoord = y;
    }

    public int getX()
    {
        return xCoord;
    }

    public int getY()
    {
        return yCoord;
    }

}
