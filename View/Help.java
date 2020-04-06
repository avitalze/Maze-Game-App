package View;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class Help implements Initializable {
    public javafx.scene.control.Button exit;
    public javafx.scene.control.Label text;

    public void close() {
        Platform.exit();
    }

    public void closew() {
        Stage s = (Stage) exit.getScene().getWindow();
        s.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        text.setWrapText(true);
        text.setText("Hello and welcome to our mazeGenerateAlgo game. In this game you will need to find your way to the eurovision trophy and bring your country the douz puae.\n "+
                "To play make sure your numlock is working!! :)\n"+
                "Up = 8    Diagnal Up And Left = 7\n"+
                "Down = 2   Diagnal Up And Right = 9\n"+
                "Left = 4   Diagnal Down And Left = 1\n"+
                "Right = 6  Diagnal Down And Right = 3");
    }
}


/*
"Hello, and welcome to our mazeGenerateAlgo game.&#10;In this game you will need to find a path from start position to&#10;end position. If you want to get the solution please use the&#10;&quot;Solve Maze &quot; button and follow the signs with your character&#10;if you want a hint, you can use the Hint button to get only 1&#10; step of the solution each time you press Hint.&#10;enjoy~!"
 */