
import java.util.Arrays;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author visti
 */
public class Util {

    /**
     * *
     * Returns the var of a cell variable
     *
     * @param row The 0 based row index
     * @param col The 0 based column index
     * @param N The sidelength of the board
     * @return
     */
    public static int getVarNumber(int N, int col, int row) {
        return (row * N) + col;
    }

    /**
     * Get all valid domains of the variables in a given BDD, returned in an
     * array, ordered by variable no.
     *
     * @param bdd
     * @return
     */
    public static int[] getValidDomains(BDD bdd, BDDFactory fact, int size) {



        int vars = size * size;
        int validDomains[] = new int[vars];

        // step through all variables
        for (int i = 0; i < vars; i++) {
            BDD var = fact.ithVar(i);
            BDD restricted = bdd.restrict(var);

            // if the restricted BDD is not unsatifiable we can place
            // a queen in the spot
            if (!(restricted.isZero())) {
                validDomains[i] = 1;
            }
        }
        System.out.println("Valid domains: " + Arrays.toString(validDomains));
        return validDomains;
    }
    
     public static void update(int[] domains, int[][] board) {

        // the number of spots on the board where a queen can be placed
        int validSpots = 0;


        // find all valid spots
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++) {
                int varNum = Util.getVarNumber(board.length, x, y);
                if (domains[varNum] == 0) {
                    board[x][y] = -1;
                } else {
                    validSpots++;
                }
            }
        }

        // the number of valid spots correspond to the one possible
        // solution, which means that we can place a queen in all the
        // valid spots and hereby end configuration
        if (validSpots == board.length) {
            for (int x = 0; x < board.length; x++) {
                for (int y = 0; y < board.length; y++) {
                    if (board[x][y] != -1) {
                        board[x][y] = 1;
                    }
                }
            }
        }
    }
}
