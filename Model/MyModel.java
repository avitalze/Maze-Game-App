package Model;

import Client.Client;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import Client.IClientStrategy;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import algorithms.search.AState;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Aviadjo on 6/14/2017.
 */
public class MyModel extends Observable implements IModel {

    private ExecutorService threadPool = Executors.newCachedThreadPool();
    private Server serverMazeGenerator;
    private Server serverMazeSolver;
    private boolean Mazesolved;
    private boolean gotToGoal;
    private Position startPos;
    private Position goalPos;
    private Maze theMazeObj;
    private int[][] maze;
    private ArrayList<AState> mazeSolution;
    private int characterPositionRow = 1;
    private int characterPositionColumn = 1;

    public MyModel() {

    }


    public void startServers() {

    }

    public void stopServers() {

    }


    @Override
    public int[][] getMaze() {
        return maze;
    }

    @Override
    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    @Override
    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }

    public boolean isGotToGoal()
    {
        return gotToGoal;
    }

    public void setGotToGoal(boolean gotToGoal) {
        this.gotToGoal = gotToGoal;
    }

    @Override
    public void save(File file) {
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            theMazeObj.setStartPosition(new Position(characterPositionRow,characterPositionColumn));
            oos.writeObject(theMazeObj);
            oos.flush();
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load(File file) {
        FileInputStream fin=null;
        try{
            fin= new FileInputStream(file);
            ObjectInputStream in= new ObjectInputStream(fin);
            theMazeObj=((Maze)in.readObject());
            maze=mazeToArr(theMazeObj);
            startPos=theMazeObj.getStartPosition();
            goalPos=theMazeObj.getGoalPosition();
            characterPositionRow=startPos.getRowIndex();
            characterPositionColumn=startPos.getColumnIndex();
            Mazesolved=false;
            in.close();
            setChanged();
            notifyObservers();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            threadPool.shutdown();
            threadPool.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void getSolution(MyViewModel viewModel, int characterPositionRow, int characterPositionColumn) {
        serverMazeSolver = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        serverMazeSolver.start();
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        Maze maze = theMazeObj;
                        Position currPos= new Position(characterPositionRow,characterPositionColumn);
                        maze.setStartPosition(currPos);
                        toServer.writeObject(maze); //send maze to server
                        toServer.flush();
                        Solution SolutionFromServer = (Solution) fromServer.readObject();
                        Mazesolved = true;
                        mazeSolution = SolutionFromServer.getSolutionPath();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        serverMazeSolver.stop();
        setChanged();
        notifyObservers();
    }

    @Override
    public boolean getIsSolved() {
        return Mazesolved;
    }

    @Override
    public ArrayList<AState> getSolutionArrayList() {
        return mazeSolution;
    }


    @Override
    public void generateMaze(int width, int height) {
        serverMazeGenerator = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        serverMazeGenerator.start();
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        Mazesolved = false;
                        gotToGoal = false;
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{width, height};
                        toServer.writeObject(mazeDimensions); //send mazedimensions to server
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed withMyCompressor)from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[width*height + 12]; //allocating byte[] for the decompressedmaze -
                        is.read(decompressedMaze); //Fill decompressedMazewith bytes
                        Maze fromServerMaze = new Maze(decompressedMaze);
                        startPos=fromServerMaze.getStartPosition();
                        theMazeObj = fromServerMaze;
                        characterPositionColumn = startPos.getColumnIndex();
                        characterPositionRow = startPos.getRowIndex();
                        goalPos = fromServerMaze.getGoalPosition();
                        maze=mazeToArr(theMazeObj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        serverMazeGenerator.stop();
        setChanged();
        notifyObservers();
    }

    private int[][] mazeToArr(Maze m) {
        int row = m.getNumRow();
        int col = m.getNumColumn();
        int[][]ans  = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                ans[i][j] = m.getMazeCell(i, j);
            }
        }
        return ans;
    }

    @Override
    public void moveCharacter(KeyCode movement) {
        if (maze != null) {
            int row = characterPositionRow;
            int col = characterPositionColumn;
            switch (movement) {
                case NUMPAD8:
                    if (isLegalMove(row - 1, col)) {
                        characterPositionRow--;
                    }
                    break;
                case NUMPAD2:
                    if (isLegalMove(row + 1, col)) {
                        characterPositionRow++;
                    }
                    break;
                case NUMPAD6:
                    if (isLegalMove(row, col + 1)) {
                        characterPositionColumn++;
                    }
                    break;
                case NUMPAD4:
                    if (isLegalMove(row, col - 1)) {
                        characterPositionColumn--;
                    }
                    break;
                case NUMPAD1:
                    if (isLegalMove(row + 1, col - 1)) {
                        if (isLegalMove(row + 1, col) || isLegalMove(row, col - 1)) {
                            characterPositionRow++;
                            characterPositionColumn--;
                        }
                    }
                    break;
                case NUMPAD3:
                    if (isLegalMove(row + 1, col + 1)) {
                        if (isLegalMove(row + 1, col) || isLegalMove(row, col + 1)) {
                            characterPositionRow++;
                            characterPositionColumn++;
                        }
                    }
                    break;
                case NUMPAD7:
                    if (isLegalMove(row - 1, col - 1)) {
                        if (isLegalMove(row - 1, col) || isLegalMove(row, col - 1)) {
                            characterPositionRow--;
                            characterPositionColumn--;
                        }
                    }
                    break;
                case NUMPAD9:
                    if (isLegalMove(row - 1, col + 1)) {
                        if (isLegalMove(row - 1, col) || isLegalMove(row, col + 1)) {
                            characterPositionRow--;
                            characterPositionColumn++;
                        }
                    }
                    break;
                //FOR DEBUG!!!!!!!!!!!!!!!!!!!!!!!
                case UP:
                    if (isLegalMove(row - 1, col)) {
                        characterPositionRow--;
                    }
                    break;
                case DOWN:
                    if (isLegalMove(row + 1, col)) {
                        characterPositionRow++;
                    }
                    break;
                case RIGHT:
                    if (isLegalMove(row, col + 1)) {
                        characterPositionColumn++;
                    }
                    break;
                case LEFT:
                    if (isLegalMove(row, col - 1)) {
                        characterPositionColumn--;
                    }
                    break;
            }
            if (goalPos.getColumnIndex() == getCharacterPositionColumn() && goalPos.getRowIndex() == getCharacterPositionRow())
                gotToGoal = true;
            setChanged();
            notifyObservers(/*Can forward an Object*/);
        }
    }

    private boolean isLegalMove(int x, int y)
    {
        if(x>=maze.length||y>=maze[0].length||x<0||y<0){
            return false;
        }
        if(maze[x][y]==1){
            return false;
        }
        return true;
    }

    public Position getGoalPosition(){
        return goalPos;
    }

    public Position getStartPosition(){
        return startPos;
    }

}
