import java.io.*;
import java.lang.Math;
import java.util.*;

class nQueens {

  private static final int Unassigned = -1;
  private static final int NoPosAvail = -2;
  private int n;
  private int curcol; //holds value of the current column, initialized to 0
  private int[] queenPos; //array of queen positions for each row, initialized to unassigned for all values
  private int[] nextAvail; //array of next available positions for each row, initialized to 0 for all values

  public nQueens(int bsize) {
    queenPos = new int[bsize];
    for (int i = 0; i < n; i++) {
      queenPos[i] = Unassigned;
    }
    nextAvail = new int[bsize];
    for (int i = 0; i < n; i++) {
      nextAvail[i] = 0;
    }
    curcol = 0;
    n = bsize;
  }

  //Get next available row and confirm it has no conflicts
  // private int getNextAvail(int curcol) {
  //   nextAvail[curcol]++;
  //   for (int i = 0; i < curcol; i++) {
  //     if (!nc(i, nextAvail[curcol])) {
  //       return NoPosAvail;
  //     }
  //   }
  //   return nextAvail[curcol];
  // }
  private int getNextAvail(int col) {
    //System.out.println(Arrays.toString(queenPos));
    int pos = nextAvail[col];
    while (true) {
      if (pos == n) {
        return NoPosAvail;
      } else if (nc(col, pos)) {
        nextAvail[col]++;
        return pos;
      } else {
        nextAvail[col]++;
        pos = nextAvail[col];
      }
    }
  }

  //Checks to see if there is a conflict
  private boolean nc(int c, int pos) {
    int qpa = queenPos[c];
    int qpb = queenPos[pos];

    for (int j = c - 1; j >= 0; j--) {
      if (pos == queenPos[j]) {
        return false;
      }

      int cdiff = c - j;
      int rdiff = pos - queenPos[j];
      if (cdiff == Math.abs(rdiff)) {
        return false;
      }
    }
    return true;
  }

  //Main Method to solve check possible solutions
  private void findSolution() {
    while (curcol >= 0) {
      if (curcol == n) { //One solution found, advance off the right end of board
        System.out.println("Solution: " + Arrays.toString(queenPos));
        //System.out.println("equal");
        curcol--; //Backtrack to previous column
        queenPos[curcol] = Unassigned; //undo queen position for that column but don't erase tried position for column
        continue;
      } else if (curcol == -1) { //All solutions found
        System.out.println("NO SOLUTION");
        break;
      } else {
        //System.out.println(Arrays.toString(queenPos));
        int pos = getNextAvail(curcol);
        if (pos == NoPosAvail) { //backtrack
          //System.out.println(Arrays.toString(queenPos));
          queenPos[curcol] = Unassigned;
          nextAvail[curcol] = 0;
          curcol--;
          continue;
        } else {
          queenPos[curcol] = pos;
          curcol++;
          continue;
        }
      }
    }
  }

  //Runner
  public static void main(String[] args) {
    int n = 20; //Change this to make the board of size nxn
    nQueens sol = new nQueens(n);
    sol.findSolution();
  }
}
