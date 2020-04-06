package View;

import ViewModel.MyViewModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.scene.image.ImageView;
import java.nio.file.Paths;
import java.util.Optional;

public class MyViewController implements IView, Observer {

    public MazeDisplayer mazeDisplayer;
    public MyViewModel viewModel;
    public StringProperty characterPositionRow = new SimpleStringProperty("0");
    public StringProperty characterPositionColumn = new SimpleStringProperty("0");
    public Label rowLabel;
    public Label colLabel;
    public Button solveMaze;
    public Button GenerateNewMaze;
    public TextField numOfRow;
    public TextField numOfCol;
    public BorderPane BorderPane;
    public VBox VBox;
    public ImageView pic1;
    public ImageView pic2;

    private Scene mainScene;
    private Stage mainStage;

    public MediaPlayer mediaPlayer;
    boolean noSong = true;


    public void initialize(MyViewModel viewModel, Stage mainStage, Scene mainScene) {
        this.viewModel = viewModel;
        this.mainScene = mainScene;
        this.mainStage = mainStage;
        bindProperties();
        setResizeEvent();
        zoomInOut();
        mazeDisplayer.setSongPath("resources\\songs\\toy.mp3");
        Image im2 = null;
        try {
            im2 = new Image(new File("resources/images/2.png").toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        pic1.setImage(im2);
        //pic3.setImage(im);

    }


    private void bindProperties() {
        rowLabel.textProperty().bind(this.characterPositionRow);
        colLabel.textProperty().bind(this.characterPositionColumn);
        //VBox.prefHeightProperty().bind(BorderPane.heightProperty());
        //VBox.prefWidthProperty().bind(BorderPane.widthProperty());
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == viewModel) {

            mazeDisplayer.setGoalPos(viewModel.getGoalPosition());
            mazeDisplayer.setIsSolved(viewModel.isSolved());
            mazeDisplayer.setStartPos(viewModel.getStartPosition());
            mazeDisplayer.setSolution(viewModel.getSolutionArray());
            displayMaze(viewModel.getMaze());// we send the maze
            if (viewModel.gotToGoal() ) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Game Done!!!");
                alert.getDialogPane().getStylesheets().add("Popup.css");
                playEndMusic();
                alert.show();
                viewModel.setGotToGoal(false);
            }

            GenerateNewMaze.setDisable(false);
        }
    }


    @Override
    public void displayMaze(int[][] maze) {
        mazeDisplayer.setMaze(maze);
        int characterPositionRow = viewModel.getCharacterPositionRow();
        int characterPositionColumn = viewModel.getCharacterPositionColumn();
        mazeDisplayer.setCharacterPosition(characterPositionRow, characterPositionColumn);
        this.characterPositionRow.set(characterPositionRow + "");
        this.characterPositionColumn.set(characterPositionColumn + "");
        mazeDisplayer.redraw();
        GenerateNewMaze.setDisable(false);
        solveMaze.setDisable(false);
    }


    
    public void generateMaze() {
        //if (noSong == true)
            playGameMusic();
        int height;
        int width;
        try {
            height = Integer.valueOf(numOfRow.getText());
        } catch (Exception e) {
            height = 15;
        }
        try {
            width = Integer.valueOf(numOfCol.getText());
        } catch (Exception e) {
            width = 15;
        }
        GenerateNewMaze.setDisable(true);
        solveMaze.setDisable(true);
        viewModel.generateMaze(width, height);
        solveMaze.setVisible(true);

    }


    public void solveMaze(ActionEvent actionEvent)
    {
        viewModel.getSolution(this.viewModel,viewModel.getCharacterPositionRow(),viewModel.getCharacterPositionColumn());
    }


    public void KeyPressed(KeyEvent keyEvent) {
        viewModel.moveCharacter(keyEvent.getCode());
        keyEvent.consume();
    }

    public void setResizeEvent() {
        this.mainScene.widthProperty().addListener((observable, oldValue, newValue) -> {
            mazeDisplayer.redraw();
            //System.out.println("Width: " + newValue);
        });

        this.mainScene.heightProperty().addListener((observable, oldValue, newValue) -> {
            mazeDisplayer.redraw();
            //System.out.println("Height: " + newValue);
        });
    }

    /*
   @Override
    public void displayMaze(int[][] maze) {
        int characterPositionRow = viewModel.getCharacterPositionRow();
        int characterPositionColumn = viewModel.getCharacterPositionColumn();
        mazeDisplayer.setCharacterPosition(characterPositionRow, characterPositionColumn);
        mazeDisplayer.goalPos(viewModel.getGoalPosition());
        mazeDisplayer.startPos(viewModel.getStartPosition());
       // mazeDisplayer.Solved(viewModel.getMazeSolutionArr());
        //mazeDisplayer.isSolved(viewModel.isSolved());
        this.characterPositionRow.set(characterPositionRow + "");
        this.characterPositionColumn.set(characterPositionColumn + "");
        //if (viewModel.isSolved())
            mazeDisplayer.redraw();
   // }*/






    public void mouseClicked(MouseEvent mouseEvent) {
        this.mazeDisplayer.requestFocus();
    }

    public void About(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("About");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("About.fxml").openStream());
            Scene scene = new Scene(root, 300, 165);
            scene.getStylesheets().add("Popup.css");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {
//            System.out.println(e);
            System.out.println("Error About.fxml not found");
        }
    }

    public void Help(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Help");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("Help.fxml").openStream());
            Scene scene = new Scene(root);
            scene.getStylesheets().add("Popup.css");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {
            System.out.println("Error Help.fxml not found");
        }
    }

    public void Properties(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Option");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("Properties.fxml").openStream());
            Scene scene = new Scene(root);
            scene.getStylesheets().add("Popup.css");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {
            System.out.println("Error Propeties.fxml not found");
        }
    }

    public void ChooseSinger(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("ChooseSinger");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("ChooseSinger.fxml").openStream());
            ChooseSinger singer = fxmlLoader.getController();
            singer.initVar(this);
            Scene scene = new Scene(root);
            scene.getStylesheets().add("Popup.css");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {
            System.out.println("Error ChooseSinger.fxml not found");
        }
    }

  /*  public void setResizeEvent(Scene scene) {
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                mazeDisplayer.redraw();
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                mazeDisplayer.redraw();
            }
        });
    }*/

    public void ChooseCountry(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("ChooseCountry");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("ChooseCountry.fxml").openStream());
            ChooseCountry country = fxmlLoader.getController();
            country.initVar(this);
            Scene scene = new Scene(root);
            scene.getStylesheets().add("Popup.css");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {
            System.out.println("Error ChooseCountry.fxml not found");
        }
    }

    public void loadGame() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Load Maze");
        File filePath = new File("./Eurovision Maze Game/");
        if (!filePath.exists())
            filePath.mkdir();
        fc.setInitialDirectory(filePath);

        File file = fc.showOpenDialog(new PopupWindow() {
        });
        if (file != null && file.exists() && !file.isDirectory()) {
            viewModel.load(file);
           if(noSong==true) {
                playGameMusic();
            }
            //mazeDisplayer.redraw();
            solveMaze.setVisible(true);
        }

    }

    public void saveGame() {

        FileChooser fc = new FileChooser();
        File filePath = new File("./Eurovision Maze Game/");
        if (!filePath.exists())
            filePath.mkdir();
        fc.setTitle("Save Maze");
        //fc.setInitialFileName("Maze Number " + i + "");
        //i++;
        fc.setInitialDirectory(filePath);
        File file = fc.showSaveDialog(mazeDisplayer.getScene().getWindow());
        if (file != null)
            viewModel.save(file);

    }

    public void playGameMusic() {
        if (mediaPlayer != null)
            mediaPlayer.stop();
        String path = mazeDisplayer.getSongPath();
        noSong = false;
        Media temp = new Media(Paths.get(path).toUri().toString());
        mediaPlayer = new MediaPlayer(temp);
        mediaPlayer.setCycleCount(mediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public void playEndMusic(){
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        noSong=false;
        String path ="resources/songs/end.mp3";
        Media temp = new Media(Paths.get(path).toUri().toString());
        mediaPlayer = new MediaPlayer(temp);
        //mediaPlayer.setCycleCount(mediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public void NewInMenu(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Generating New Maze");
        alert.getDialogPane().getStylesheets().add("Popup.css");
        alert.setHeaderText("You are generating a maze with "+numOfRow.getText()+" rows and "+numOfCol.getText()+" columns");
        alert.setContentText("Are you OK with this?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            generateMaze();
        } else {
            alert.close();
        }
    }

    public void zoomInOut(){
        this.mazeDisplayer.setOnScroll(event -> {
            if(event.isControlDown()){
                double zoomFactor = 1.05;
                double deltaY = event.getDeltaY();
                if (deltaY < 0){
                    zoomFactor = 2.0 - zoomFactor;
                }
                mazeDisplayer.setScaleX(mazeDisplayer.getScaleX() * zoomFactor);
                mazeDisplayer.setScaleY(mazeDisplayer.getScaleY() * zoomFactor);
                //mazeDisplayer.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, null, null)));
                // mazeDisplayer.redraw();
                //event.consume();
            }
        });
    }

    public void exit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().getStylesheets().add("Alert.css");
        Image kobi = null;
        try {
            kobi = new Image(new File("").toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageView iv = new ImageView(kobi);
        iv.setFitHeight(600);
        //iv.setX(700);
        iv.setFitWidth(600);
        //iv.setY(650);
        alert.setGraphic(iv);//!!!!!!!!!!!!!!1 iv
        alert.setHeaderText(null);
        alert.setWidth(600);
        alert.setHeight(600);
        alert.setContentText("Are you sure you want to exit?");
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        Media temp = new Media(Paths.get("resources/songs/man_crying.mp3").toUri().toString());
        MediaPlayer player = new MediaPlayer(temp);
        player.play();
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
            // Close the program properly
            Platform.exit();
        } else {
            // ... user chose CANCEL or closed the dialog
            playGameMusic();

        }
    }

}
