package cs2410.assn8.view;

import cs2410.assn8.control.Controller;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Dylan on 12/2/2016.
 */
public class Minesweeperish extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private Scoreboard mainBoard;
    private Scene mainScene;
    private TilePane gridPane;
    private BorderPane mainPane;
    private Controller control;
    private EventHandler<MouseEvent> cellHandler;
    private EventHandler<ActionEvent> startHandler;
    private Timer timer;
    private boolean timeStarted = false;
    private Alert gameOverAlert;
    private int seconds = 0;


    public void start(Stage primaryStage) throws Exception {

        initHandlers();
        control = new Controller();
        timer = new Timer();

        mainBoard = new Scoreboard();
        mainBoard.updateBombText("Bombs Left: \n" + Integer.toString(control.getBombsLeft()));
        mainBoard.updateTimeText("Time:\n0");
        mainBoard.setStartButtonAction(startHandler);

        mainPane = new BorderPane();
        mainPane.setTop(mainBoard);
        populateGrid();

        mainScene = new Scene(mainPane);


        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Minesweeperish");
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                timer.cancel();
                primaryStage.close();
            }
        });



    }

    public void initHandlers()
    {
        cellHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Cell temp = (Cell)event.getSource();
                if(event.getButton() == MouseButton.PRIMARY)
                {
                    if(temp.getText() == "  ")
                    {
                        if (!temp.isBomb())
                        {
                            clearCell(temp);
                            mainBoard.disableStartButton();
                            if(!timeStarted)
                            {
                                startTimer();
                                timeStarted = true;
                            }
                            if(control.getCellsCleared() >= 300)
                            {
                                timer.cancel();
                                gameOver(true);
                                mainBoard.enableStartButton();
                                timeStarted = false;
                            }

                        }
                        else if(temp.isBomb())
                        {
                            mainBoard.enableStartButton();
                            timer.cancel();
                            timeStarted = false;
                            gameOver(false);
                        }
                    }
                }
                else if(event.getButton() == MouseButton.SECONDARY)
                {
                    switch(temp.getText())
                    {
                        case "  ": temp.setText("X");
                            control.decrementBombsLeft();
                            mainBoard.updateBombText("Bombs Left: \n" + Integer.toString(control.getBombsLeft()));
                            break;
                        case "X": temp.setText("?");
                            break;
                        case "?": temp.setText("  ");
                            control.incrementBombsLeft();
                            mainBoard.updateBombText("Bombs Left: \n" + Integer.toString(control.getBombsLeft()));
                            break;
                    }
                }

            }
        };

        //Calls the initCellList method of the controller object in order to reset the list,
        //as well as populateGrid()
        startHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                control.initCellList();
                populateGrid();
                mainBoard.updateBombText("Bombs Left: \n" + Integer.toString(control.getBombsLeft()));
                mainBoard.updateTimeText("Time:\n0");
                timer = new Timer();

            }
        };
    }

    //Handles getting all cells from the controller's list and populating the tilePane (named gridPane)
    //Also handles setting the gridPane to the center of the mainPane.
    public void populateGrid()
    {
        gridPane = new TilePane();
        gridPane.setPrefColumns(20);
        gridPane.setPrefRows(20);
        for(Cell[] subArray : control.getCellList())
        {
            for(Cell cell : subArray)
            {
                cell.setOnMousePressed(cellHandler);
                cell.setText("  ");
                gridPane.getChildren().add(cell);
            }
        }
        mainPane.setCenter(gridPane);
        findAllBombs();
    }

    //Finds all bombs in the grid and assigns values to each Cell's bombsNearby member.
    public void findAllBombs()
    {
        for(int i = 0; i < 20; i++)
        {
            for(int j = 0; j < 20; j++)
            {
                if(control.getCellList()[j][i].isBomb())
                {
                    for (Cell cell : control.getNeighboringCells(i, j)) {
                        cell.incrementBombsNearby();
                    }
                }
            }
        }

    }

    //Takes a cell and clears it, as well as its neighbors recursively until the cell to be cleared is next to a bomb.
    public void clearCell(Cell cell)
    {
        cell.select();
        control.incrementCellsCleared();
        if(!cell.isBomb() && cell.getBombsNearby() == 0)
        {
            ArrayList<Cell> neighbors = control.getNeighboringCells(cell.getX(), cell.getY());
            for(Cell neighbor : neighbors)
            {
                if(!neighbor.isSelected() && neighbor.getText() == "  ")
                {
                    clearCell(neighbor);
                }
            }
        }
    }

    public void startTimer()
    {
        seconds = 0;
        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                seconds++;
                mainBoard.updateTimeText("Time:\n" + Integer.toString(seconds));
            }
        }, 0, 1000);
    }

    public void gameOver(boolean didWin)
    {


        if(!didWin)
        {
            gameOverAlert = new Alert(Alert.AlertType.INFORMATION);
            gameOverAlert.setTitle("Game Over");
            gameOverAlert.setHeaderText("You lose!  Time was " + seconds + " seconds");
            gameOverAlert.showAndWait();

            for(Cell[] subArray : control.getCellList())
            {
                for(Cell cell : subArray)
                {
                    cell.setDisable(true);
                    if(cell.isBomb() && cell.getText() != "  ")
                    {
                        cell.setId("markedBomb");
                    }
                    else if(cell.isBomb() && cell.getText() == "  ")
                    {
                        cell.setId("unmarkedBomb");
                    }
                    else if(!cell.isBomb() && (cell.getText() == "X" || cell.getText() == "?"))
                    {
                        cell.setId("markedCell");
                    }
                }
            }
        }

        if(didWin)
        {
            gameOverAlert = new Alert(Alert.AlertType.INFORMATION);
            gameOverAlert.setTitle("Victory!");
            gameOverAlert.setHeaderText("You win!!  Time was " + seconds + " seconds");
            gameOverAlert.showAndWait();

            for(Cell[] subArray : control.getCellList())
            {
                for(Cell cell : subArray)
                {
                    cell.setDisable(true);
                    if(cell.isBomb())
                    {
                        cell.setId("markedBomb");
                    }
                }
            }
        }
    }

}
