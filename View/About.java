package View;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class About implements Initializable {
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
        String gameInfo="";
        OutputStream output = null;
        String mazeGenerator="";
        String numOfThreads="";
        String solveAlgorithm="";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("resources/config.properties"));
            String line;

            line = bufferedReader.readLine();
            while (line != null) {
                String [] splitedLine = line.split("=");
                switch (splitedLine[0]){
                    case "MazeGenerator":
                        mazeGenerator=splitedLine[1];
                        break;

                    case "Numofthreads":
                        numOfThreads=splitedLine[1];
                        break;

                    case "MazeSolver":
                        solveAlgorithm=splitedLine[1];
                        break;
                }
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException e) { }

        gameInfo=" The mazeGenerateAlgo generating algorithm is: "+mazeGenerator+".\n"+" The number of threads are: "+numOfThreads+".\n"+" The mazeGenerateAlgo solving algorithm is: "+solveAlgorithm+".\n";
        text.setText(gameInfo);

    }

}