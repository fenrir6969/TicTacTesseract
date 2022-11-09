import javax.swing.*;
import java.util.Scanner;

public class Game {
    Slice currSlice;
    char[][] board;
    char player;
    boolean game;

    public Game() {
        currSlice = new Slice();
        board = new char[3][3];
        player = 'x';
        game = true;
    }

    public char[] getCube(int depth,int axis){
        return Slice.states.getNet(depth,axis);
    }
    public char[] getHyperCube() {
        char[] hyperNet = new char[8];
        int i = 0;
        for(int axis = 0; axis<4; axis++) {
            for (int depth = 0; depth < 2; depth++) {
                hyperNet[i] = Slice.states.cube[depth][axis];
                i++;
            }
        }
        return hyperNet;
    }
    public Slice getRotation(int direction) {
        Slice slice = new Slice();
        if(direction==0){
            slice = currSlice.rotateAC(false);
        } else if (direction==1) {
            slice = currSlice.rotateAD(false);
        } else if (direction==2) {
            slice = currSlice.rotateBC(true);
        } else if (direction==3) {
            slice = currSlice.rotateBD(true);
        } else if (direction==4) {
            slice = currSlice.rotateAC(true);
        } else if (direction==5) {
            slice = currSlice.rotateAD(true);
        } else if (direction==6) {
            slice = currSlice.rotateBC(false);
        } else {
            slice = currSlice.rotateBD(false);
        }
        return slice;
    }
    public void updateBoard(){
        board = currSlice.load();
    }
    public void nextTurn(){
        if(player=='x') {
            player = 'o';
        } else {
            player = 'x';
        }
        System.out.println("[Game] Player " + player + "'s turn");
        chooseAction();
    }
    public void play() {
        Scanner input = new Scanner(System.in);
        System.out.println("");
        if (!Slice.states.isWon(Slice.states.getState(currSlice))) {
            System.out.println("[Game] Choose X position");
            System.out.println("[Game]    Integer 0 - 2");
            System.out.println("[Game]   -1 to cancel play");
            int x = Integer.parseInt(input.nextLine());
            if (x < 0) {
                System.out.println("[Game] Canceling Move...");
                chooseAction();
            } else {
                System.out.println("");
                System.out.println("[Game] Choose Y position");
                System.out.println("[Game]    Integer 0 - 2");
                System.out.println("[Game]   -1 to cancel play");
                int y = Integer.parseInt(input.nextLine());
                if (y < 0) {
                    System.out.println("[Game] Canceling Move...");
                    chooseAction();
                } else {
                    if (!Slice.states.isWon(board[x][y])) {
                        board[x][y] = player;
                        currSlice.save(board);
                        System.out.println("[Game] Placing " + player + " at " + x + " " + y);
                        if (currSlice.checkBoard()) {
                            endGame(Slice.states.hyper);
                        }
                        nextTurn();
                    } else {
                        System.out.println("[Game] Cannot place " + player + " at " + x + " " + y);
                        System.out.println("[Game] Reselect Move");
                        play();
                    }
                }
            }
        } else {
            System.out.println("[Game] Board already won. Select different Action");
            chooseAction();
        }

    }
    public void rotate() {
        Scanner input = new Scanner(System.in);
        System.out.println("");
        System.out.println("[Game] Choose direction:");
        System.out.println("[Game]   -1: Cancel rotation");
        System.out.println("[Game]    0: Up C");
        System.out.println("[Game]    1: Up D");
        System.out.println("[Game]    2: Right C");
        System.out.println("[Game]    3: Right D");
        System.out.println("[Game]    4: Down C");
        System.out.println("[Game]    5: Down D");
        System.out.println("[Game]    6: Left C");
        System.out.println("[Game]    7: Left D");
        int i = Integer.parseInt(input.nextLine());
        if(i<0){
            System.out.println("[Game] Canceling Rotation...");
        } else {
            currSlice = getRotation(i);
            System.out.println("[Game] Rotating to " + currSlice.getName());
        } chooseAction();
    }
    public void newGame(){
        Slice.clearBoard();
        currSlice.define('X','Y','Z','W');
        player = 'x';
        chooseAction();
    }
    public void endGame(char winner){
        Scanner input = new Scanner(System.in);
        System.out.println("");
        System.out.println("[Game] Game won by " + winner);
        System.out.println("[Game] Play again?");
        System.out.println("[Game]    0: yes");
        System.out.println("[Game]    1: no");
        int i = Integer.parseInt(input.nextLine());
        if(i<1){
            newGame();
        }
    }
    public void chooseAction() {
        updateBoard();
        Scanner input = new Scanner(System.in);
        System.out.println("");
        System.out.println("[Game] Player " + player + " choose an action:");
        System.out.println("[Game]    1: Rotate");
        System.out.println("[Game]    2: Play");
        System.out.println("[Game]    3: Skip");
        int i = Integer.parseInt(input.nextLine());
        if(i==1){
            rotate();
        } else if(i==2) {
            play();
        } else {
            nextTurn();
        }
    }


}
