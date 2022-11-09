import java.lang.*;
import java.util.Random;

public class Slice {
    public static States states = new States();
    public static char[][][][] hyperBoard = new char[3][3][3][3];

    protected char A;
    protected char B;
    protected char C;
    protected char D;

    public Slice() {
        A = ' ';
        B = ' ';
        C = ' ';
        D = ' ';
    }
    //  TEST FUNCTIONS
        public void randomizeBoard( int randomness) {
            hyperBoard = new char[3][3][3][3];
            System.out.println("");
            System.out.println("[Slice] Randomizing Board...");
            Random ran = new Random();
            for(int w = 0; w < 3; w++) {
                for(int y = 0; y < 3; y++) {
                    for(int x = 0; x < 3; x++) {
                        for(int z = 0; z < 3; z++) {
                            int r = ran.nextInt(randomness);
                            if (r == 0) {
                                hyperBoard[x][y][z][w] = 'x';
                                System.out.print('x');
                            } else if (r == 1) {
                                hyperBoard[x][y][z][w] = 'o';
                                System.out.print('o');
                            } else {
                                System.out.print('-');
                            }
                        }
                    }
                }
            }
            System.out.println("");
            System.out.println("");
        }
        // randomly set hyperboard values
        public static void clearBoard(){
            hyperBoard = new char[3][3][3][3];
            System.out.println("");
            System.out.println("[Slice] Clearing Board...");
        }
        public void printArray(char[][] array) {
            for(int b = 0; b<2;b++){
                System.out.print("   ");
                for(int a=0;a<3;a++){
                    System.out.print(array[a][b]);
                }
                System.out.println(" ");
            }
            System.out.print("   ");
            for(int a=0;a<3;a++){
                System.out.print(array[a][2]);
            }
        }
        // iterates through a 2x2 array and prints the values
        public String getName() {
            return "" + A + B + C + D;
        }
        // prints a string that says what slice it is
        public void rotationMessage( Slice s) {
            System.out.println("");
            System.out.println("[Slice] Rotating from " + this.getName() + " to " + s.getName());
            //s.printSlice();
        }
        // prints information about what direction you are rotating to console
        public void define(char axisA,char axisB,char depthC,char depthD) {
        A = axisA ;
        B = axisB;
        C = depthC;
        D = depthD;
        System.out.println("");
        System.out.println("[Slice] Defined Slice " + getName());
    }
        // manually set slice

    //  INTERNAL FUNCTIONS
        public int depthValue(char ch) {
            if(Character.isUpperCase(ch)){
                return 2;
            } else {
                return 0;
            }
        }
        // this functions determines the depth of the slice on a given axis based off of its depth character.

        private char flipCase(char ch) {
            if(Character.isUpperCase(ch)){
                return Character.toLowerCase(ch);
            } else {
                return Character.toUpperCase(ch);
            }
        }
        // reverse changes the case of a depth character, from lowercase to uppercase, or uppercase to lowercase.
        // this is important for generic rotation logic, as the letter itself must remain the same but it

        public char flattenCase(char ch) {
            return (Character.toLowerCase(ch));
        }
        // flatten changes a character to lowercase, no matter the case. This is important for case sensitive checks,
        // for example, 'z' and 'Z' are different characters, and rather than check for both of them I just ensure it's lowercase.

        private int reverseAxis(char ch, int val) {
            int[] forwards = {0,1,2};
            int[] backwards = {2,1,0};
            if(Character.isUpperCase(ch)){
                return forwards[val];
            } else {
                return backwards[val];
            }
        }
        // gear shift virtually reverses the direction of a loop.
        // If lowercase, that signifies the axis should decrement rather than increment.
        // And so a new value will be substituted.


    //  ROTATION FUNCTIONS
        public Slice rotateAC( boolean clockwise) {
            Slice Next = new Slice();
            Next.A = A;
            Next.D = D;
            if( clockwise ) {
                Next.B = flipCase(C);
                Next.C = B;
            }
            else {
                Next.C = flipCase(B);
                Next.B = C;
            }
            rotationMessage(Next);
            return Next;
        }
        public Slice rotateAD( boolean clockwise) {
            Slice Next = new Slice();
            Next.A = A;
            Next.C = C;
            if( clockwise ) {
                Next.B = flipCase(D);
                Next.D = B;
            }
            else {
                Next.D = flipCase(B);
                Next.B = D;
            }
            rotationMessage(Next);
            return Next;
        }
        public Slice rotateBC( boolean clockwise) {
            Slice Next = new Slice();
            Next.B = B;
            Next.D = D;
            if( clockwise ) {
                Next.A = flipCase(C);
                Next.C = A;
            }
            else {
                Next.C = flipCase(A);
                Next.A = C;
            }
            rotationMessage(Next);
            return Next;
        }
        public Slice rotateBD( boolean clockwise) {
            Slice Next = new Slice();
            Next.B = B;
            Next.C = C;
            if( clockwise ) {
                Next.A = flipCase(D);
                Next.D = A;
            }
            else {
                Next.D = flipCase(A);
                Next.A = D;
            }
            rotationMessage(Next);
            return Next;
        }
        // generic rotation logic below
        /*
        You rotate around axis A or B, to depth C or D, either clockwise or counter-clockwise.
        1.  The axis that you rotate around remains unchanged.

        2.  The depth you do not rotate to also remains unchanged.

        3.  Rotating clockwise means towards the origin, counter-clockwise means away from it.
            The origin is top left.

        4.  The depth you rotate to becomes the new axis.
            If its depth value is 0, and you rotate clockwise, than the new axis will go from 0 to 2.
            If its depth value is 2, and you rotate clockwise, than the new axis will go from 2 to 0.
            If its depth value is 0, and you rotate counter-clockwise, than the new axis will go from 2 to 0.
            If its depth value is 2, and you rotate counter-clockwise, than the new axis will go from 0 to 2.
            case value: lowercase = 0, uppercase = 2
            case direction: lowercase = ( 2 to 0 ), uppercase = ( 0 to 2 )
            so
            if depth lowercase, and clockwise rotation, new axis uppercase
            if depth uppercase, and clockwise rotation, new axis lowercase
            if depth lowercase, and counter-clockwise rotation, new axis lowercase
            if depth uppercase, and counter-clockwise rotation, new axis uppercase
            so
            if rotating clockwise, then the new axis is the old axis but reversed.
            if rotating counter-clockwise, then the new axis is just the old axis

        5.  The old axis becomes the new depth.
            If it goes from 0 to 2, and you rotate clockwise, than the new depth value will be 2.
            If it goes from 2 to 0, and you rotate clockwise, than the new depth value will be 0.
            If it goes from 0 to 2, and you rotate counter-clockwise, than the new depth value will be 0.
            If it goes from 2 to 0, and you rotate counter-clockwise, than the new depth value will be 2.
            case value: lowercase = 0, uppercase = 2
            case direction: lowercase = ( 2 to 0 ), uppercase = ( 0 to 2 )
            so
            If old axis uppercase, and clockwise rotation, new depth uppercase
            If old axis lowercase, and clockwise rotation, new depth lowercase
            If old axis uppercase, and counter-clockwise rotation, new depth lowercase
            If old axis lowercase, and counter-clockwise rotation, new depth uppercase
            so
            If rotating clockwise, then the new depth is just the old axis.
            If rotating counter-clockwise, then the new depth is the old axis but reversed.
        */


    //  LOAD AND SAVE
        public char[][] load() {
            //all possible orientations
            char[][] raw = new char[3][3];
            int d = depthValue(D);
            int c = depthValue(C);

            //System.out.println("");
            System.out.println("[Slice] Loading Slice...");
            /*
            System.out.println(flattenCase(A) + " goes from " + axisDirection(A) );
            System.out.println(flattenCase(B) + " goes from " + axisDirection(B) );
            System.out.println("depth " + flattenCase(C) + " = " + c + ", " + flattenCase(D) + " = " + d);
             */

            if(flattenCase(A)=='x'){
                if(flattenCase(B)=='y'){
                    //xyzw
                    if(flattenCase(C)=='z'){
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                raw[reverseAxis(A,a)][reverseAxis(B,b)] = hyperBoard[a][b][c][d];
                            }
                        }
                    }
                    //xywz
                    else {
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                raw[reverseAxis(A,a)][reverseAxis(B,b)] = hyperBoard[a][b][d][c];
                            }
                        }
                    }
                }
                else if(flattenCase(B)=='z') {
                    //xzyw
                    if(flattenCase(C)=='y'){
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                raw[reverseAxis(A,a)][reverseAxis(B,b)] = hyperBoard[a][c][b][d];
                            }
                        }
                    }
                    //xzwy
                    else {
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                raw[reverseAxis(A,a)][reverseAxis(B,b)] = hyperBoard[a][d][b][c];
                            }
                        }
                    }
                }
                else {
                    //xwzy
                    if(flattenCase(C)=='z'){
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                raw[reverseAxis(A,a)][reverseAxis(B,b)] = hyperBoard[a][d][c][b];
                            }
                        }
                    }
                    //xwyz
                    else {
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                raw[reverseAxis(A,a)][reverseAxis(B,b)] = hyperBoard[a][c][d][b];
                            }
                        }
                    }
                }
            }
            else if(flattenCase(A)=='y'){
                if(flattenCase(B)=='x'){
                    //yxzw
                    if(flattenCase(C)=='z'){
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                raw[reverseAxis(A,a)][reverseAxis(B,b)] = hyperBoard[b][a][c][d];
                            }
                        }
                    }
                    //yxwz
                    else {
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                raw[reverseAxis(A,a)][reverseAxis(B,b)] = hyperBoard[b][a][d][c];
                            }
                        }
                    }
                }
                else if(flattenCase(B)=='z'){
                    //yzxw
                    if(flattenCase(C)=='x'){
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                raw[reverseAxis(A,a)][reverseAxis(B,b)] = hyperBoard[c][a][b][d];
                            }
                        }
                    }
                    //yzwx
                    else {
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                raw[reverseAxis(A,a)][reverseAxis(B,b)] = hyperBoard[d][a][b][c];
                            }
                        }
                    }
                }
                else {
                    //ywzx
                    if(flattenCase(C)=='z'){
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                raw[reverseAxis(A,a)][reverseAxis(B,b)] = hyperBoard[d][a][c][b];
                            }
                        }
                    }
                    //ywxz
                    else {
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                raw[reverseAxis(A,a)][reverseAxis(B,b)] = hyperBoard[c][a][d][b];
                            }
                        }
                    }
                }
            }
            else if(flattenCase(A)=='z'){
                if(flattenCase(B)=='x'){
                    //zxyw
                    if(flattenCase(C)=='y'){
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                raw[reverseAxis(A,a)][reverseAxis(B,b)] = hyperBoard[b][c][a][d];
                            }
                        }
                    }
                    //zxwy
                    else {
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                raw[reverseAxis(A,a)][reverseAxis(B,b)] = hyperBoard[b][d][a][c];
                            }
                        }
                    }
                }
                else if(flattenCase(B)=='y'){
                    //zyxw
                    if(flattenCase(C)=='x'){
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                raw[reverseAxis(A,a)][reverseAxis(B,b)] = hyperBoard[c][b][a][d];
                            }
                        }
                    }
                    //zywx
                    else {
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                raw[reverseAxis(A,a)][reverseAxis(B,b)] = hyperBoard[d][b][a][c];
                            }
                        }
                    }
                }
                else{
                    //zwxy
                    if(flattenCase(C)=='x'){
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                raw[reverseAxis(A,a)][reverseAxis(B,b)] = hyperBoard[c][d][a][b];
                            }
                        }
                    }
                    //zwyx
                    else {
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                raw[reverseAxis(A,a)][reverseAxis(B,b)] = hyperBoard[d][c][a][b];
                            }
                        }
                    }
                }
            }
            else{
                if(flattenCase(B)=='x'){
                    //wxzy
                    if(flattenCase(C)=='z'){
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                raw[reverseAxis(A,a)][reverseAxis(B,b)] = hyperBoard[b][d][c][a];
                            }
                        }
                    }
                    //wxyz
                    else {
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                raw[reverseAxis(A,a)][reverseAxis(B,b)] = hyperBoard[b][c][d][a];
                            }
                        }
                    }
                }
                else if(flattenCase(B)=='y'){
                    //wyzx
                    if(flattenCase(C)=='z'){
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                raw[reverseAxis(A,a)][reverseAxis(B,b)] = hyperBoard[d][b][c][a];
                            }
                        }
                    }
                    //wyxz
                    else {
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                raw[reverseAxis(A,a)][reverseAxis(B,b)] = hyperBoard[c][b][d][a];
                            }
                        }
                    }
                }
                else{
                    //wzxy
                    if(flattenCase(C)=='x'){
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                raw[reverseAxis(A,a)][reverseAxis(B,b)] = hyperBoard[c][d][b][a];
                            }
                        }
                    }
                    //wzyx
                    else {
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                raw[reverseAxis(A,a)][reverseAxis(B,b)] = hyperBoard[d][c][b][a];
                            }
                        }
                    }
                }
            }
            printArray(raw);
            return raw;
        }
        public void save( char[][] raw) {
            //all possible orientations
            int d = depthValue(D);
            int c = depthValue(C);
            System.out.println("");
            System.out.println("[Slice] Saving slice...");

            if(flattenCase(A)=='x'){
                if(flattenCase(B)=='y'){
                    //xyzw
                    if(flattenCase(C)=='z'){
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                hyperBoard[a][b][c][d] = raw[reverseAxis(A,a)][reverseAxis(B,b)];
                            }
                        }
                    }
                    //xywz
                    else {
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                hyperBoard[a][b][d][c] = raw[reverseAxis(A,a)][reverseAxis(B,b)];
                            }
                        }
                    }
                }
                else if(flattenCase(B)=='z') {
                    //xzyw
                    if(flattenCase(C)=='y'){
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                hyperBoard[a][c][b][d] = raw[reverseAxis(A,a)][reverseAxis(B,b)];
                            }
                        }
                    }
                    //xzwy
                    else {
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                hyperBoard[a][d][b][c] = raw[reverseAxis(A,a)][reverseAxis(B,b)];
                            }
                        }
                    }
                }
                else {
                    //xwzy
                    if(flattenCase(C)=='z'){
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                hyperBoard[a][d][c][b] = raw[reverseAxis(A,a)][reverseAxis(B,b)];
                            }
                        }
                    }
                    //xwyz
                    else {
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                hyperBoard[a][c][d][b] = raw[reverseAxis(A,a)][reverseAxis(B,b)];
                            }
                        }
                    }
                }
            }
            else if(flattenCase(A)=='y'){
                if(flattenCase(B)=='x'){
                    //yxzw
                    if(flattenCase(C)=='z'){
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                hyperBoard[b][a][c][d] = raw[reverseAxis(A,a)][reverseAxis(B,b)];
                            }
                        }
                    }
                    //yxwz
                    else {
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                hyperBoard[b][a][d][c] = raw[reverseAxis(A,a)][reverseAxis(B,b)];
                            }
                        }
                    }
                }
                else if(flattenCase(B)=='z'){
                    //yzxw
                    if(flattenCase(C)=='x'){
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                hyperBoard[c][a][b][d] = raw[reverseAxis(A,a)][reverseAxis(B,b)];
                            }
                        }
                    }
                    //yzwx
                    else {
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                hyperBoard[d][a][b][c] = raw[reverseAxis(A,a)][reverseAxis(B,b)];
                            }
                        }
                    }
                }
                else {
                    //ywzx
                    if(flattenCase(C)=='z'){
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                hyperBoard[d][a][c][b] = raw[reverseAxis(A,a)][reverseAxis(B,b)];
                            }
                        }
                    }
                    //ywxz
                    else {
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                hyperBoard[c][a][d][b] = raw[reverseAxis(A,a)][reverseAxis(B,b)];
                            }
                        }
                    }
                }
            }
            else if(flattenCase(A)=='z'){
                if(flattenCase(B)=='x'){
                    //zxyw
                    if(flattenCase(C)=='y'){
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                hyperBoard[b][c][a][d] = raw[reverseAxis(A,a)][reverseAxis(B,b)];
                            }
                        }
                    }
                    //zxwy
                    else {
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                hyperBoard[b][d][a][c] = raw[reverseAxis(A,a)][reverseAxis(B,b)];
                            }
                        }
                    }
                }
                else if(flattenCase(B)=='y'){
                    //zyxw
                    if(flattenCase(C)=='x'){
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                hyperBoard[c][b][a][d] = raw[reverseAxis(A,a)][reverseAxis(B,b)];
                            }
                        }
                    }
                    //zywx
                    else {
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                hyperBoard[d][b][a][c] = raw[reverseAxis(A,a)][reverseAxis(B,b)];
                            }
                        }
                    }
                }
                else{
                    //zwxy
                    if(flattenCase(C)=='x'){
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                hyperBoard[c][d][a][b] = raw[reverseAxis(A,a)][reverseAxis(B,b)];
                            }
                        }
                    }
                    //zwyx
                    else {
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                hyperBoard[d][c][a][b] = raw[reverseAxis(A,a)][reverseAxis(B,b)];
                            }
                        }
                    }
                }
            }
            else{
                if(flattenCase(B)=='x'){
                    //wxzy
                    if(flattenCase(C)=='z'){
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                hyperBoard[b][d][c][a] = raw[reverseAxis(A,a)][reverseAxis(B,b)];
                            }
                        }
                    }
                    //wxyz
                    else {
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                hyperBoard[b][c][d][a] = raw[reverseAxis(A,a)][reverseAxis(B,b)];
                            }
                        }
                    }
                }
                else if(flattenCase(B)=='y'){
                    //wyzx
                    if(flattenCase(C)=='z'){
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                hyperBoard[d][b][c][a] = raw[reverseAxis(A,a)][reverseAxis(B,b)];
                            }
                        }
                    }
                    //wyxz
                    else {
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                hyperBoard[c][b][d][a] = raw[reverseAxis(A,a)][reverseAxis(B,b)];
                            }
                        }
                    }
                }
                else{
                    //wzxy
                    if(flattenCase(C)=='x'){
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                hyperBoard[c][d][b][a] = raw[reverseAxis(A,a)][reverseAxis(B,b)];
                            }
                        }
                    }
                    //wzyx
                    else {
                        for(int b=0;b<3;b++) {
                            for(int a=0;a<3;a++) {
                                hyperBoard[d][c][b][a] = raw[reverseAxis(A,a)][reverseAxis(B,b)];
                            }
                        }
                    }
                }
            }
        }

    //  CHECK BOARD
        public boolean checkBoard(){
            System.out.println("[Slice] Checking Boards...");
            states.checkBoard(this);
            states.checkBoard(rotateAC(false));
            states.checkBoard(rotateAC(true));
            states.checkBoard(rotateBC(false));
            states.checkBoard(rotateBC(true));
            states.checkBoard(rotateAD(false));
            states.checkBoard(rotateAD(true));
            states.checkBoard(rotateBD(false));
            states.checkBoard(rotateBD(true));
            return(states.checkGame());
        }
}
