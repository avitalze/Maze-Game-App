package ViewModel;

import Model.IModel;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {
    private IModel model;

    private int characterPositionRowIndex;
    private int characterPositionColumnIndex;

    public StringProperty characterPositionRow = new SimpleStringProperty("1"); //For Binding
    public StringProperty characterPositionColumn = new SimpleStringProperty("1"); //For Binding

    public MyViewModel(IModel model) {
        this.model = model;
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o==model){
            characterPositionRowIndex = model.getCharacterPositionRow();
            characterPositionRow.set(characterPositionRowIndex + "");
            characterPositionColumnIndex = model.getCharacterPositionColumn();
            characterPositionColumn.set(characterPositionColumnIndex + "");
            setChanged();
            notifyObservers();
        }
    }

    public void generateMaze(int width, int height){
        model.generateMaze(width, height);
    }

    public void moveCharacter(KeyCode movement){
        model.moveCharacter(movement);
    }

    public int[][] getMaze() {
        return model.getMaze();
    }

    public int getCharacterPositionRow() {
        //return characterPositionRowIndex;
        return model.getCharacterPositionRow();
    }

    public int getCharacterPositionColumn() {
        //return characterPositionColumnIndex;
        return model.getCharacterPositionColumn();
    }

    public Position getGoalPosition() {
        return model.getGoalPosition();

    }
    public Position getStartPosition(){
        return model.getStartPosition();
    }

    public void getSolution(MyViewModel viewModel, int characterPositionRow, int characterPositionColumn) {
        model.getSolution(viewModel,characterPositionRow,characterPositionColumn);
    }

    public boolean isSolved() {
        return model.getIsSolved();
    }

    public ArrayList<AState> getSolutionArray() {
        return model.getSolutionArrayList();
    }

    public boolean gotToGoal() {
        return model.isGotToGoal();
    }

    public void setGotToGoal(boolean b){
        model.setGotToGoal(b);
    }

    public void save(File file) {
        model.save(file);
    }

    public void load(File file) {
        model.load(file);
    }
}
/*

    public void save(File file) {
        model.save(file);
    }

    public void load(File file) {
        model.load(file);
    }
}
*/