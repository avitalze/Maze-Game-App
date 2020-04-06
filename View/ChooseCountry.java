package View;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.naming.ldap.Rdn;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChooseCountry implements Initializable {

    public Button saveChanges;
    public RadioButton country1;
    public RadioButton country2;
    public RadioButton country3;
    public RadioButton country4;
    public RadioButton country5;
    public RadioButton country6;
    public RadioButton country7;
    public RadioButton country8;
    public ImageView israel;
    public ImageView france;
    public ImageView greece;
    public ImageView ireland;
    public ImageView czech;
    public ImageView georgia;
    public ImageView switzerland;
    public ImageView sanmarino;
    public MyViewController viewCon;

    private void closew() {
        Stage s = (Stage) saveChanges.getScene().getWindow();
        s.close();
    }

    public void initVar(MyViewController view){
        viewCon=view;
    }

    public void saveChanges(){
        if(country1.isSelected()){
            viewCon.mazeDisplayer.setImageFileNameWall("resources/images/israel_flag.jpg");

        }
        if(country2.isSelected()){
            viewCon.mazeDisplayer.setImageFileNameWall("resources/images/france_flag.png");

        }
        if(country3.isSelected()){
            viewCon.mazeDisplayer.setImageFileNameWall("resources/images/greece_flag.jpg");
        }
        if(country4.isSelected()){
            viewCon.mazeDisplayer.setImageFileNameWall("resources/images/irelend_flag.gif");
        }
        if(country5.isSelected()){
            viewCon.mazeDisplayer.setImageFileNameWall("resources/images/czech_flag.jpg");
        }
        if(country6.isSelected()){
            viewCon.mazeDisplayer.setImageFileNameWall("resources/images/georgia_flag.png");
        }
        if(country7.isSelected()){
            viewCon.mazeDisplayer.setImageFileNameWall("resources/images/switzerland_flag.png");
        }
        if(country8.isSelected()){
            viewCon.mazeDisplayer.setImageFileNameWall("resources/images/sanmarino_flag.gif");
        }

        viewCon.mazeDisplayer.redraw();
        closew();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image israelFlag = null;
        try {
            israelFlag = new Image(new File("resources/images/israel_flag.jpg").toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        israel.setImage(israelFlag);

        Image franceFlag=null;
        try{
            franceFlag=new Image(new File("resources/images/france_flag.png").toURI().toURL().toExternalForm());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        france.setImage(franceFlag);

        Image greeceFlag=null;
        try{
            greeceFlag=new Image(new File("resources/images/greece_flag.jpg").toURI().toURL().toExternalForm());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        greece.setImage(greeceFlag);

        Image irelendFlag = null;
        try {
            irelendFlag = new Image(new File("resources/images/irelend_flag.gif").toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ireland.setImage(irelendFlag);

        Image czechFlag = null;
        try {
            czechFlag = new Image(new File("resources/images/czech_flag.jpg").toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        czech.setImage(czechFlag);

        Image georgiaFlag = null;
        try {
            georgiaFlag = new Image(new File("resources/images/georgia_flag.png").toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        georgia.setImage(georgiaFlag);

        Image swissFlag = null;
        try {
            swissFlag = new Image(new File("resources/images/switzerland_flag.png").toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        switzerland.setImage(swissFlag);

        Image sanmarinoFlag = null;
        try {
            sanmarinoFlag = new Image(new File("resources/images/sanmarino_flag.gif").toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        sanmarino.setImage(sanmarinoFlag);

    }
}
