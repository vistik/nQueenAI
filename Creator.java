/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import net.sf.javabdd.*;

/**
 *
 * @author visti
 */
public class Creator {

    private static int size;
    private static BDDFactory fact;
    private static BDD[][] board;
    private static BDD org;

    public static BDD createBDD(int size, BDDFactory fact) {
        Creator.org = fact.one();

        Creator.fact = fact;
        Creator.size = size;

        Creator.board = new BDD[size][size];
        int i, j;
        
        // set the board
        for (i = 0; i < size; i++) {
            for (j = 0; j < size; j++) {
                board[i][j] = fact.ithVar(Util.getVarNumber(size,j,i));
            }
        }

        
        //Build constrains for each field/var
        for (i = 0; i < size; i++) {
            for (j = 0; j < size; j++) {
                create(i, j, fact);
            }
        }
        return org;
    }

    private static void create(int i, int j, BDDFactory fact) {

        BDD allrules = oneQprRow(i, j).
                andWith(oneQprCol(i, j)).
                andWith(oneQprUpRight(i, j)).
                andWith(oneQprDownRight(i, j)).
                andWith(minOneQprRow(i, j));

        Creator.org.andWith(allrules);
    }

    public static BDD oneQprRow(int i, int j) {
        BDD oneQprRow = fact.one();
        int l;
        for (l = 0; l < size; l++) {
            if (l != i) {
                BDD u = board[i][j].apply(board[l][j], BDDFactory.nand);
                oneQprRow.andWith(u);
            }
        }
        return oneQprRow;
    }

    public static BDD oneQprCol(int i, int j) {
        BDD oneQprCol = fact.one();
        int l;
        for (l = 0; l < size; l++) {
            if (l != j) {
                BDD u = board[i][l].apply(board[i][j], BDDFactory.nand);
                oneQprCol.andWith(u);
            }
        }
        return oneQprCol;

    }

    public static BDD oneQprUpRight(int i, int j) {
        BDD oneQprUpRight = fact.one();
        int l;
        for (l = 0; l < size; l++) {
            int x = l - i + j;
            if (x >= 0 && x < size) {
                if (l != i) {
                    BDD u = board[i][j].apply(board[l][x], BDDFactory.nand);
                    oneQprUpRight.andWith(u);
                }
            }
        }
        return oneQprUpRight;
    }

    public static BDD oneQprDownRight(int i, int j) {
        BDD oneQprUpRight = fact.one();
        int l;
        for (l = 0; l < size; l++) {
            int x = i + j - l;
            if (x >= 0 && x < size) {
                if (l != i) {
                    BDD u = board[i][j].apply(board[l][x], BDDFactory.nand);
                    oneQprUpRight.andWith(u);
                }
            }
        }
        return oneQprUpRight;
    }

    public static BDD minOneQprRow(int i, int j) {
        BDD minOneQprRow = fact.one();
        BDD u = fact.zero();
        int l;
        for (l = 0; l < size; l++) {
            u = u.apply(board[i][l], BDDFactory.xor);
        }
        minOneQprRow.andWith(u);
        return minOneQprRow;
    }
}
