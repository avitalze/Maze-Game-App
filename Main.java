import Model.MyModel;
import View.MyViewController;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.Optional;

public class Main extends Application {

   /* @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View/MyView.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add("Main.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

*/
    public static void main(String[] args) {
        launch(args);
    }

   @Override
    public void start(Stage primaryStage) throws Exception {
        //ViewMoel -> Model
        MyModel model = new MyModel();
        model.startServers();
        MyViewModel viewModel = new MyViewModel(model);
        model.addObserver(viewModel);

        //Loading Main Windows
        primaryStage.setTitle("My Application!");
        primaryStage.setWidth(800);
        primaryStage.setHeight(700);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("View/MyView.fxml").openStream());
        Scene scene = new Scene(root, 800, 700);
       scene.getStylesheets().add("Main.css");
        //scene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());
        primaryStage.setScene(scene);

        //View -> ViewModel
        MyViewController view = fxmlLoader.getController();
        view.initialize(viewModel,primaryStage,scene);
        viewModel.addObserver(view);
        //--------------
        setStageCloseEvent(primaryStage, model,view);
        //
        //Show the Main Window
        primaryStage.show();
    }

    private void setStageCloseEvent(Stage primaryStage, MyModel model, MyViewController view) {
        primaryStage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.getDialogPane().getStylesheets().add("Alert.css");
            Image kobi= null;
            try {
                kobi = new Image(new File("").toURI().toURL().toExternalForm());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            ImageView iv= new ImageView(kobi);
            iv.setFitHeight(600);
            //iv.setX(700);
            iv.setFitWidth(600);
            //iv.setY(650);
            alert.setGraphic(iv);//!!!!!!!!!!!!!!1 iv
            alert.setHeaderText(null);
            alert.setWidth(600);
            alert.setHeight(600);
            alert.setContentText("Are you sure you want to exit?");
            if(view.mediaPlayer != null){
                view.mediaPlayer.stop();
            }
            Media temp = new Media(Paths.get("resources/songs/man_crying.mp3").toUri().toString());
            MediaPlayer player = new MediaPlayer(temp);
            player.play();
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // ... user chose OK
                // Close the program properly
                model.close();
            } else {
                // ... user chose CANCEL or closed the dialog
                view.playGameMusic();
                event.consume();
            }


        });

    }

}
