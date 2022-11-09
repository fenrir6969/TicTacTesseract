import java.util.Random;

public class States {
    protected char[][][][] board;
    protected char[][] cube;
    // cubes[depth][axis], x=0, y=1, z=2, w=3
    protected char hyper;

    public States() {
        board = new char[3][3][3][3];
        cube = new char[2][4];
        hyper = ' ';
    }


    // Test Functions
    private String filterAlignment(int value, char ch) {
        if(value!=1){
            return "" + ch + value + " ";
        } else {
            return "";
        }
    }
    private void printArray( char[] array) {
        for(int i = 0;i < 6; i++){
            System.out.print(array[i]);
            if((i-1)%2 == 0) {
                System.out.print(" ");
            }
        }
    }

    //Internal
    public boolean isWon(char state) {
        if(state == 'x') {
            return true;
        } else if(state == 'o') {
            return true;
        } else {
            return false;
        }
    }
    private char parseAxis(int axis) {
        if( axis == 0) {
            return 'x';
        } else if( axis == 1) {
            return 'y';
        } else if( axis == 2) {
            return 'z';
        } else {
            return 'w';
        }
    }
    public char[] getNet(int depth, int axis) {
        System.out.println("Retreiving Net " + parseAxis(axis) + depth/2 + "...");
        //fill net from boardStates
        int[] columnA = {0, 2, 1, 1, 1, 1};
        int[] columnB = {1, 1, 0, 2, 1, 1};
        int[] columnC = {1, 1, 1, 1, 0, 2};
        char[] net = new char[6];
        if (axis == 0) {
            for (int i = 0; i < 6; i++) {
                net[i] = board[depth][columnA[i]][columnB[i]][columnC[i]];
                System.out.println("   boardState " + filterAlignment(depth,'x') + filterAlignment(columnA[i],'y') + filterAlignment(columnB[i],'z') + filterAlignment(columnC[i],'w') + net[i]);
            }
        }
        else if (axis == 1) {
            for (int i = 0; i < 6; i++) {
                net[i] = board[columnA[i]][depth][columnB[i]][columnC[i]];
                System.out.println("   boardState " + filterAlignment(columnA[i],'x') + filterAlignment(depth,'y') + filterAlignment(columnB[i],'z') + filterAlignment(columnC[i],'w') + net[i]);
            }
        }
        else if (axis == 2) {
            for (int i = 0; i < 6; i++) {
                net[i] = board[columnA[i]][columnB[i]][depth][columnC[i]];
                System.out.println("   boardState " + filterAlignment(columnA[i],'x') + filterAlignment(columnB[i],'y') + filterAlignment(depth,'z') + filterAlignment(columnC[i],'w') + net[i]);
            }
        }
        else {
            for (int i = 0; i < 6; i++) {
                net[i] = board[columnA[i]][columnB[i]][columnC[i]][depth];
                System.out.println("   boardState " + filterAlignment(columnA[i],'x') + filterAlignment(columnB[i],'y') + filterAlignment(columnC[i],'z') + filterAlignment(depth,'w') + net[i]);
            }
        }
        return net;
    }
    public char getState( Slice slice) {
        char flattenedC = slice.flattenCase(slice.C);
        char flattenedD = slice.flattenCase(slice.D);
        int valueC = slice.depthValue(slice.C);
        int valueD = slice.depthValue(slice.D);
        char state;
        if(flattenedC=='x') {
            if(flattenedD=='y') {
                state = board[valueC][valueD][1][1];
            } else if(flattenedD=='z') {
                state = board[valueC][1][valueD][1];
            } else {
                state = board[valueC][1][1][valueD];
            }
        } else if(flattenedC=='y') {
            if(flattenedD=='x') {
                state = board[valueD][valueC][1][1];
            } else if(flattenedD=='z') {
                state = board[1][valueC][valueD][1];
            } else {
                state = board[1][valueC][1][valueD];
            }
        } else if(flattenedC=='z') {
            if(flattenedD=='x') {
                state = board[valueD][1][valueC][1];
            } else if(flattenedD=='y') {
                state = board[1][valueD][valueC][1];
            } else {
                state = board[1][1][valueC][valueD];
            }
        } else {
            if (flattenedD == 'x') {
                state = board[valueD][1][1][valueC];
            } else if (flattenedD == 'y') {
                state = board[1][valueD][1][valueC];
            } else {
                state = board[1][1][valueD][valueC];
            }
        }
        System.out.println("boardState " + flattenedC + valueC + " " + flattenedD + valueD + " is " + state);
        return state;
    }
    public void setState( Slice slice, char state) {
        //flattened characters for comparison
        char flattenedC = slice.flattenCase(slice.C);
        char flattenedD = slice.flattenCase(slice.D);
        // value for choosing correct depth
        int valueC = slice.depthValue(slice.C);
        int valueD = slice.depthValue(slice.D);

        //console message
        System.out.println("boardState " + flattenedC + valueC + " " + flattenedD + valueD + " set to " + state);

        if(flattenedC=='x') {
            if(flattenedD=='y') {
                board[valueC][valueD][1][1] = state;
            } else if(flattenedD=='z') {
                board[valueC][1][valueD][1] = state;
            } else {
                board[valueC][1][1][valueD] = state;
            }
        } else if(flattenedC=='y') {
            if(flattenedD=='x') {
                board[valueD][valueC][1][1] = state;
            } else if(flattenedD=='z') {
                board[1][valueC][valueD][1] = state;
            } else {
                board[1][valueC][1][valueD] = state;
            }
        } else if(flattenedC=='z') {
            if(flattenedD=='x') {
                board[valueD][1][valueC][1] = state;
            } else if(flattenedD=='y') {
                board[1][valueD][valueC][1] = state;
            } else {
                board[1][1][valueC][valueD] = state;
            }
        } else {
            if (flattenedD == 'x') {
                board[valueD][1][1][valueC] = state;
            } else if (flattenedD == 'y') {
                board[1][valueD][1][valueC] = state;
            } else {
                board[1][1][valueD][valueC] = state;
            }
        }
    }

    //Checks
    public void checkBoard( Slice slice) {
        //intitialize
        String name = slice.getName();
        System.out.println("");
        System.out.println("[State] Checking slice " + name + "... ");
        char state = getState(slice);
        if( !isWon(state) ) {
            char[][] array = slice.load();
            boolean stopCheck = false;
            System.out.println("");
            char ch;
            //check diagonal 1
            if (isWon(array[0][0])) {
                ch = array[0][0];
                if (ch == array[1][1]) {
                    if (ch == array[2][2]) {
                        setState(slice, ch);
                        stopCheck=true;
                    }
                }
            }
            //check diagonal 2
            if(!stopCheck) {
                if (isWon(array[0][2])) {
                    ch = array[0][2];
                    if (ch == array[1][1]) {
                        if (ch == array[2][0]) {
                            setState(slice, ch);
                            stopCheck=true;
                        }
                    }
                }
            }
            if(!stopCheck) {
                for (int a = 0; a < 3; a++) {
                    //check vertical
                    if (isWon(array[a][0])) {
                        ch = array[a][0];
                        if (ch == array[a][1]) {
                            if (ch == array[a][2]) {
                                setState(slice, ch);
                                break;
                            }
                        }
                    }
                    //check horizontal
                    if (isWon(array[0][a])) {
                        ch = array[0][a];
                        if (ch == array[1][a]) {
                            if (ch == array[2][a]) {
                                setState(slice, ch);
                                break;
                            }
                        }
                    }
                }
            }
            state = getState(slice);
            if(isWon(state)){
                // somebody won
                System.out.println("Slice " + name + " won by " + state);
            }
            else {
                // nobody won
                System.out.println("Slice " + name + " won by nobody" );
            }
        }
        else {
            // already won, no need to check further
            System.out.println("Slice " + name + " already won by " + state);
        }
    }
    public boolean checkCube(int depth, int axis) {
        String cubeName = "" + parseAxis(axis) + depth;
        System.out.println("");
        System.out.println("[State] Checking Cube " + cubeName + "...");
        //check if already won or not
        if (!isWon(cube[depth][axis])) {
            char[] net = getNet(depth*2,axis);
            System.out.print(cubeName + " : ");
            printArray(net);
            System.out.println(" ");
            //checks cube for victory, updates its position in cubeStates
            char temp;
            for (int i = 0; i < 3; i++) {
                temp = net[i * 2];
                if (isWon(temp)) {
                    if (net[i * 2 + 1] == temp) {
                        for (int j = 0; j < 6; j++) {
                            if ((j != i * 2) && (j != i * 2 + 1)) {
                                if (net[j] == temp) {
                                    cube[depth][axis] = temp;
                                }
                            }
                        }
                    }
                }
            }
            if (isWon(cube[depth][axis])) {
                //someone won outcome
                System.out.println("cube " + cubeName + " won by " + cube[depth][axis]);
                return true;
            } else {
                //no change outcome
                System.out.println("cube " + cubeName + " won by nobody");
                return false;
            }
        }
        else {
            //already won outcome
            System.out.println("cube " + cubeName + " already won by " + cube[depth][axis]);
            return false;
        }
    }
    public boolean checkHyper() {
        char temp;
        String row;

        //print cubeStates
        System.out.println("");
        System.out.println("[State] Checking Hypercube...");
        if(!isWon(hyper)) {
            System.out.println("   0 1");
            for(int axis = 0; axis<4; axis++) {
                System.out.print(parseAxis(axis) + ":");
                for (int depth = 0; depth < 2; depth++) {
                    System.out.print(" " + cube[depth][axis]);
                }
                System.out.println("");
            }
            System.out.println("");
            for (int i = 0; i < 4; i++) {
                temp = cube[0][i];
                if (isWon(temp)) {
                    if (cube[1][i] == temp) {
                        for (int depth = 0; depth < 2; depth++) {
                            for (int axis = 0; axis < 4; axis++) {
                                if (axis != i) {
                                    row = "" + parseAxis(i) + 0 + " " + parseAxis(axis) + depth + " " + parseAxis(i) + '2';
                                    if (cube[depth][axis] == temp) {
                                        hyper = temp;
                                        System.out.println(temp + " wins " + row);
                                    } else {
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(!isWon(hyper)) {
                System.out.println("Hypercube won by nobody");
                return false;
            } else {
                System.out.println("Hypercube won by " + hyper);
                return true;
            }
        } else {
            System.out.println("Hypercube already won by " + hyper);
            return true;
        }
    }
    public boolean checkGame() {
        int checks = 0;
        boolean won = false;
        System.out.println("");
        System.out.println("[State] Checking Cubes...");
        for(int axis = 0; axis<4; axis++) {
            if(won) { break; }
            for (int depth = 0; depth < 2; depth++) {
                if(won) { break; }
                if (checkCube(depth, axis)) {
                    won = checkHyper();
                    checks++;
                    System.out.println("");
                    System.out.println("[State] Checking Cubes...");
                }
                checks++;
            }
        }
        checkHyper();
        System.out.println("");
        System.out.println("checks : " + checks);
        if(won) {
            System.out.println("Game won by " + hyper);
        } else {
            System.out.println("Nobody won");
        }
        return won;
    }

    //Testing
    public void testBoards(int randomness) {
        System.out.println("");
        System.out.println("Randomizing BoardStates...");
        Random ran = new Random();
        for(int w = 0; w < 3; w++) {
            for(int y = 0; y < 3; y++) {
                for(int x = 0; x < 3; x++) {
                    for(int z = 0; z < 3; z++) {
                        int r = ran.nextInt(randomness);
                        if (r == 0) {
                            board[x][y][z][w] = 'x';
                            System.out.print('x');
                        } else if (r == 1) {
                            board[x][y][z][w] = 'o';
                            System.out.print('o');
                        } else {
                            System.out.print('-');
                        }
                    }
                }
            }
        }
        System.out.println("");
        checkGame();
    }
    public void testCubes(int randomness) {
        System.out.println("");
        System.out.println("Randomizing CubeStates...");
        Random ran = new Random();
        for(int axis = 0; axis<4; axis++) {
            for (int depth = 0; depth < 2; depth++) {
                int r = ran.nextInt(randomness);
                if (r == 0) {
                    cube[depth][axis] = 'x';
                    System.out.print('x');
                } else if (r == 1) {
                    cube[depth][axis] = 'o';
                    System.out.print('o');
                } else {
                    System.out.print('-');
                }
            }
        }
        System.out.println("");
        checkHyper();
    }
    public void reset() {
        board = new char[3][3][3][3];
        cube = new char[2][4];
        hyper = ' ';
    }
}