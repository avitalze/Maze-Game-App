package View;



import algorithms.mazeGenerators.Position;

import algorithms.search.AState;
import algorithms.search.MazeState;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class MazeDisplayer extends Canvas {
    private int[][] maze;
    private Position startPos;
    private Position goalPos;

    private int characterPositionRow;
    private int characterPositionColumn;

    private ArrayList<AState> solution;
    private boolean isSolved;

    //region Prop
    private StringProperty ImageFileNameWall = new SimpleStringProperty();
    private StringProperty ImageFileNameCharacter = new SimpleStringProperty();
    private StringProperty ImageFileNameGoal = new SimpleStringProperty();
    private StringProperty Songpath= new SimpleStringProperty();

    public String getSongPath() {
        return Songpath.get();
    }

    public void setSongPath(String path) {
        this.Songpath.set(path);
    }

    public String getImageFileNameWall() {
        return ImageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.ImageFileNameWall.set(imageFileNameWall);
    }

    public String getImageFileNameCharacter() {
        return ImageFileNameCharacter.get();
    }

    public void setImageFileNameCharacter(String imageFileNameCharacter) {
        this.ImageFileNameCharacter.set(imageFileNameCharacter);
    }

    public String getImageFileNameGoal() {
        return ImageFileNameGoal.get();
    }

    public void setImageFileNameGoal(String imageFileNameGoal) {
        this.ImageFileNameGoal.set(imageFileNameGoal);
    }

    public void goalPos(Position end) {
        goalPos = end;
    }

    public void startPos(Position start) {
        goalPos = start;
    }

    //GETTERS
    public int[][] getMaze() {
        return maze;
    }

    public Position getStartPos() {
        return startPos;
    }

    public Position getGoalPos() {
        return goalPos;
    }

    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }

    public boolean getIsSolved(){
        return isSolved;
    }

    public ArrayList<AState> getSolution() {
        return solution;
    }
    //SETTERS

    public void setMaze(int[][] maze) {
        this.maze = maze;
    }

    public void setStartPos(Position startPos) {
        this.startPos = startPos;
    }

    public void setGoalPos(Position goalPos) {
        this.goalPos = goalPos;
    }

    public void setCharacterPositionRow(int characterPositionRow) {
        this.characterPositionRow = characterPositionRow;
    }

    public void setCharacterPositionColumn(int characterPositionColumn) {
        this.characterPositionColumn = characterPositionColumn;
    }

    public void setCharacterPosition(int row, int col) {
        characterPositionRow = row;
        characterPositionColumn = col;
    }

    public void setIsSolved(boolean b){
        isSolved=b;
    }

    public void setSolution(ArrayList<AState> solution) {
        this.solution = solution;
    }

    public void redraw() {
        if (maze != null) {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double cellHeight = canvasHeight / maze.length;
            double cellWidth = canvasWidth / maze[0].length;
            GraphicsContext gc = getGraphicsContext2D();
            gc.setFill(Color.rgb(255,255,255,0.5));
            try {

                gc.clearRect(0, 0, getWidth(), getHeight()); //Clears the canvas
                Image wallImage = new Image(new FileInputStream(ImageFileNameWall.get()));

                for (int i = 0; i < maze.length; i++) {
                    for (int j = 0; j < maze[i].length; j++) {
                        if (maze[i][j] == 1) {
                            //gc.drawImage(wallImage, j * cellHeight, i * cellWidth, cellHeight, cellWidth);
                            gc.drawImage(wallImage,j*cellWidth,i*cellHeight,cellWidth,cellHeight);
                        }
                        else{
                            gc.fillRect(j*cellWidth,i*cellHeight,cellWidth,cellHeight);
                        }
                    }
                }
                //draw start point
                //Image start = new Image(new FileInputStream("resources/images/house.jpg"));
                //graphicsContext2D.drawImage(StartPoint, 0, 0, cellHeight, cellWidth);
                if (isSolved) {
                    //Image SolutionImage = new Image(new FileInputStream("resources/images/eat.png"));
                    Image SolutionImage = new Image(new FileInputStream("resources/images/star.png"));
                    int x,y;
                    for (AState curr: solution) {
                        x=((MazeState)curr).getPosition().getRowIndex();
                        y=((MazeState)curr).getPosition().getColumnIndex();
                        gc.drawImage(SolutionImage, y * cellWidth, x * cellHeight, cellWidth, cellHeight);
                    }
                }
                //draw end point
                Image goal = new Image(new FileInputStream(getImageFileNameGoal()));
                gc.drawImage(goal, goalPos.getColumnIndex() * cellWidth, goalPos.getRowIndex() * cellHeight, cellWidth, cellHeight);
                //Draw Character
                Image characterImage = new Image(new FileInputStream(getImageFileNameCharacter()));
                gc.drawImage(characterImage, characterPositionColumn * cellWidth, characterPositionRow * cellHeight, cellWidth, cellHeight);
            } catch (FileNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(String.format("Image doesn't exist: %s", e.getMessage()));
                alert.show();
            }
        }
    }
}

