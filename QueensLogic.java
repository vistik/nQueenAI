
/**
 * This class implements the logic behind the BDD for the n-queens problem You
 * should implement all the missing methods
 *
 * @author Stavros Amanatidis
 *
 */
import java.util.*;

import net.sf.javabdd.*;

public class QueensLogic {

    private int x = 0;
    private int y = 0;
    private int[][] board;
    private BDDFactory fact;
    private BDD queen;
    private int nodes = 2000000;
    private int cache = 200000;

    public QueensLogic() {
        //construct
    }

    public void initializeGame(int size) {
        this.x = size;
        this.y = size;
        this.board = new int[x][y];
        fact = JFactory.init(nodes, cache);
        fact.setVarNum(size*size);
//        System
        queen = Creator.createBDD(size, fact);
        
        
    }

    public int[][] getGameBoard() {
        return board;
    }

    public boolean insertQueen(int column, int row) {

        if (board[column][row] == -1 || board[column][row] == 1) {
            return true;
        }

        board[column][row] = 1;
        
        BDD restrict = Restrictor.restrict(queen, fact, board);

        
        int validDomains[] = Util.getValidDomains(restrict, fact, x);
		
		// update the board using these domains
	Util.update(validDomains, board);
		

        return true;
    }
}
