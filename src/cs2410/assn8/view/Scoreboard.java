package cs2410.assn8.view;

import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * @author Dylan Cox
 */
public class Scoreboard extends FlowPane {

    private Button startButton;
    private Text bombText;
    private Text timeText;

    public Scoreboard()
    {
        bombText = new Text("Bombs Left");
        bombText.setTextAlignment(TextAlignment.CENTER);
        startButton = new Button("Start");
        timeText = new Text("Time");
        timeText.setTextAlignment(TextAlignment.CENTER);

        this.getChildren().addAll(bombText, startButton, timeText);
        this.setHgap(40);
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER);
    }

    public void setStartButtonAction(EventHandler<ActionEvent> event)
    {
        startButton.setOnAction(event);
    }

    public void updateBombText(String toSet)
    {
        bombText.setText(toSet);
    }

    public void updateTimeText(String toSet)
    {
        timeText.setText(toSet);
    }

    public void disableStartButton()
    {
        startButton.setDisable(true);
    }

    public void enableStartButton()
    {
        startButton.setDisable(false);
    }

}
