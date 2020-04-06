package Model;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.util.ArrayList;


public interface IModel {
    //Maze
    void generateMaze(int width, int height);
    int[][] getMaze();

    //Character
    void moveCharacter(KeyCode movement);
    int getCharacterPositionRow();
    int getCharacterPositionColumn();
    Position getGoalPosition();
    Position getStartPosition();
    //
    void close();

    void getSolution(MyViewModel viewModel, int characterPositionRow, int characterPositionColumn);

    boolean getIsSolved();

    ArrayList<AState> getSolutionArrayList();

    boolean isGotToGoal();

    void setGotToGoal(boolean gotToGoal);

    void save(File file);
    void load(File file);
}