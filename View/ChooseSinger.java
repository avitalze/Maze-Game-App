package View;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChooseSinger implements Initializable{
    public Button saveChanges;
    public RadioButton singer1;
    public RadioButton singer2;
    public RadioButton singer3;
    public ImageView neta;
    public ImageView conchita;
    public ImageView cyprus;
    public MyViewController viewCon;

    private void closew() {
        Stage s = (Stage) saveChanges.getScene().getWindow();
        s.close();
    }

    public void initVar(MyViewController view){
        viewCon=view;
    }
    public void saveChanges(){

        if(singer1.isSelected()){
            viewCon.mazeDisplayer.setImageFileNameCharacter("resources/images/neta.png");
            viewCon.mazeDisplayer.setSongPath("resources/songs/toy.mp3");
        }
        if(singer2.isSelected()){
            viewCon.mazeDisplayer.setImageFileNameCharacter("resources/images/conchita.png");
            viewCon.mazeDisplayer.setSongPath("resources/songs/fineox.mp3");
        }
        if(singer3.isSelected()){
            viewCon.mazeDisplayer.setImageFileNameCharacter("resources/images/cyprus-removebg.png");
            viewCon.mazeDisplayer.setSongPath("resources/songs/fuego.mp3");
        }
        viewCon.mazeDisplayer.redraw();
        if(viewCon.mazeDisplayer.getMaze()!=null){// maze is already on screen
            viewCon.playGameMusic();
        }
        closew();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image netaImage = null;
        try {
            netaImage = new Image(new File("resources/images/neta.png").toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        neta.setImage(netaImage);

        Image conchitaImage=null;
        try{
            conchitaImage=new Image(new File("resources/images/conchita.png").toURI().toURL().toExternalForm());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        conchita.setImage(conchitaImage);

        Image cyprusImage=null;
        try{
            cyprusImage=new Image(new File("resources/images/cyprus-removebg.png").toURI().toURL().toExternalForm());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        cyprus.setImage(cyprusImage);

    }

}
