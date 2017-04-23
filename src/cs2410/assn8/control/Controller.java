package cs2410.assn8.control;

import cs2410.assn8.view.Cell;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Dylan on 12/3/2016.
 */
public class Controller {

    private ArrayList<Cell> cellList;
    private Cell[][] cellArray;
    private int bombsLeft;
    private int cellsCleared;

    public Controller()
    {
        initCellList();
    }

    public void initCellList()
    {
        bombsLeft = 100;
        cellsCleared = 0;
        cellList = new ArrayList<Cell>();
        //Populate array list with cell objects
        for(int i = 0; i < 400; i++)
        {
            if(i < 100)
            {
                Cell tempCell = new Cell(true);
                cellList.add(tempCell);
            }
            else
            {
                Cell tempCell = new Cell();
                cellList.add(tempCell);
            }
        }


        //Shuffle array list
        Collections.shuffle(cellList);


        //Populate 20x20 2D array with shuffled cells.  Also declares the coordinates of the Cell.
        cellArray = new Cell[20][20];
        int counter = 0;
        for(int i = 0; i < 20; i++)
        {
            for(int j = 0; j < 20; j++)
            {
                cellArray[i][j] = cellList.get(counter);
                cellArray[i][j].setCoordinates(j, i);
                counter++;
            }
        }
    }

    public Cell[][] getCellList()
    {
        return cellArray;
    }

    public ArrayList<Cell> getNeighboringCells(int xCoord, int yCoord)
    {
        ArrayList<Cell> neighbors = new ArrayList<Cell>();

        //Handle corners first, avoiding going out of bounds
        if(xCoord == 0 && yCoord == 0)
        {
            neighbors.add(cellArray[0][1]);
            neighbors.add(cellArray[1][0]);
            neighbors.add(cellArray[1][1]);
        }

        else if(xCoord == 19 && yCoord == 0)
        {
            neighbors.add(cellArray[0][18]);
            neighbors.add(cellArray[1][18]);
            neighbors.add(cellArray[1][19]);
        }

        else if(xCoord == 0 && yCoord == 19)
        {
            neighbors.add(cellArray[18][0]);
            neighbors.add(cellArray[18][1]);
            neighbors.add(cellArray[19][1]);
        }

        else if(xCoord == 19 && yCoord == 19)
        {
            neighbors.add(cellArray[19][18]);
            neighbors.add(cellArray[18][19]);
            neighbors.add(cellArray[18][18]);
        }

        //Handle next special case of top, right, bottom, and left rows/columns, respectively.
        else if(xCoord >= 1 && xCoord <= 18 && yCoord == 0)
        {
            neighbors.add(cellArray[yCoord][xCoord - 1]);
            neighbors.add(cellArray[yCoord][xCoord + 1]);
            neighbors.add(cellArray[yCoord + 1][xCoord - 1]);
            neighbors.add(cellArray[yCoord + 1][xCoord]);
            neighbors.add(cellArray[yCoord + 1][xCoord + 1]);
        }

        else if(yCoord >= 1 && yCoord <= 18 && xCoord == 19)
        {
            neighbors.add(cellArray[yCoord - 1][xCoord - 1]);
            neighbors.add(cellArray[yCoord - 1][xCoord]);
            neighbors.add(cellArray[yCoord][xCoord - 1]);
            neighbors.add(cellArray[yCoord + 1][xCoord - 1]);
            neighbors.add(cellArray[yCoord + 1][xCoord]);
        }

        else if(xCoord >= 1 && xCoord <= 18 && yCoord == 19)
        {
            neighbors.add(cellArray[yCoord - 1][xCoord - 1]);
            neighbors.add(cellArray[yCoord - 1][xCoord]);
            neighbors.add(cellArray[yCoord - 1][xCoord + 1]);
            neighbors.add(cellArray[yCoord][xCoord - 1]);
            neighbors.add(cellArray[yCoord][xCoord + 1]);
        }

        else if(yCoord >= 1 && yCoord <= 18 && xCoord == 0)
        {
            neighbors.add(cellArray[yCoord - 1][xCoord]);
            neighbors.add(cellArray[yCoord - 1][xCoord + 1]);
            neighbors.add(cellArray[yCoord][xCoord + 1]);
            neighbors.add(cellArray[yCoord + 1][xCoord]);
            neighbors.add(cellArray[yCoord + 1][xCoord + 1]);
        }

        //Handles every other cell that has no special case.
        else
        {
            neighbors.add(cellArray[yCoord - 1][xCoord - 1]);
            neighbors.add(cellArray[yCoord - 1][xCoord]);
            neighbors.add(cellArray[yCoord - 1][xCoord + 1]);
            neighbors.add(cellArray[yCoord][xCoord - 1]);
            neighbors.add(cellArray[yCoord][xCoord + 1]);
            neighbors.add(cellArray[yCoord + 1][xCoord - 1]);
            neighbors.add(cellArray[yCoord + 1][xCoord]);
            neighbors.add(cellArray[yCoord + 1][xCoord + 1]);
        }



        return neighbors;
    }

    public int getBombsLeft()
    {
        return bombsLeft;
    }

    public void incrementBombsLeft()
    {
        bombsLeft++;
    }

    public void decrementBombsLeft()
    {
        bombsLeft--;
    }

    public void incrementCellsCleared()
    {
        cellsCleared++;
    }

    public int getCellsCleared()
    {
        return cellsCleared;
    }
}
