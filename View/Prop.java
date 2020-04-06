package View;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class Prop implements Initializable {
    public Button close;
    public Button save;
    public ChoiceBox searchingAlgo;
    public ChoiceBox mazeGenerateAlgo;
    public ChoiceBox numOfThread;
    String search ="BestFirstSearch";
    String generate ="MyMazeGenerator";
    String numThread ="4";

    public void close() {
        Platform.exit();
    }

    public void closew() {
        Stage s = (Stage) close.getScene().getWindow();
        s.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Configurations.Conf();

        searchingAlgo.getItems().addAll("Breadth First Search", "Depth First Search","Best First Search");
        mazeGenerateAlgo.getItems().addAll("Simple Maze Generator", "My Maze Generator", "Empty Maze Generator");
        numOfThread.getItems().addAll("1", "2", "3", "4","8");

    }

    public void SetConf() throws IOException {
        OutputStream output = new FileOutputStream("resources/config.properties");
            Properties prop = new Properties(); //create new prop file
        //searching options
        if(searchingAlgo.getValue()!=null) {// something was chosen
            if (searchingAlgo.getValue().equals("Breadth First Search")) {
                search = "BreadthFirstSearch";
            }
            if (searchingAlgo.getValue().equals("Depth First Search")) {
                search = "DepthFirstSearch";
            }
            if (searchingAlgo.getValue().equals("Best First Search")) {
                search = "BestFirstSearch";
            }
        }
        if(mazeGenerateAlgo.getValue()!=null) {// something was chosen
            // generating options
            if (mazeGenerateAlgo.getValue().equals("Simple Maze Generator")) {
                generate = "SimpleMazeGenerator";
            }
            if (mazeGenerateAlgo.getValue().equals("My Maze Generator")) {
                generate = "MyMazeGenerator";
            }
            if (mazeGenerateAlgo.getValue().equals("Empty Maze Generator")) {
                generate = "EmptyMazeGenerator";
            }
        }
        if(numOfThread.getValue()!=null) {// something was chosen
            //num of threads
            if (numOfThread.getValue().equals("1")) {
                numThread = "1";
            }
            if (numOfThread.getValue().equals("2")) {
                numThread = "2";
            }
            if (numOfThread.getValue().equals("3")) {
                numThread = "3";
            }
            if (numOfThread.getValue().equals("4")) {
                numThread = "4";
            }
            if (numOfThread.getValue().equals("8")) {
                numThread = "8";
            }
        }
            prop.setProperty("MazeGenerator", generate);
            prop.setProperty("Numofthreads", numThread);
            prop.setProperty("MazeSolver", search);

            // save properties to project root folder
            prop.store(output, null);


        if (output != null) {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        closew();
    }
}
