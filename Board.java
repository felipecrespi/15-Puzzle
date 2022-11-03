import java.util.Random;
import java.util.ArrayList;
import java.util.Stack;

public class Board
{
  private int moves;
  private int side; //the side can also serve to count the size of the board
  private int[][] board;
  private boolean mustSolvable;
  private Stack<int[][]> allMoves; //this will serve not only to restart the game, but also so the user can go back

  public Board() //we cannot have parameter since in the Gui class, it will be called befor we have the neccessary information
  {
    moves = 0;
    //assign random values so some methods in the Gui class (such as getWin() do not crash)
    side = 3;
    mustSolvable = true;
  }

  public void startBoard() //when the user has already provided all the necessary information, we can now set all the variable (moves was already correct, no need to change)
  {
    board = new int[side][side];
    allMoves = new Stack<int[][]>();//int[][] is an object; thus, it can be stored in the Stack
    setBoard();
    allMoves.push(copy()); // the initial board is the first one in the list of movements
  }

  public boolean checkWin(boolean canCheck) //canCheck because at the start, the Gui was crashing, since the board was waiting to be completly set.
  {
    if(canCheck) //if the board was already set in the Gui class and the game has started
    {
      int num = 1;
      for(int i = 0; i < side; i++)
      {
        for(int j = 0; j < side; j++)
        {
          if(i == (side - 1) && j == (side - 1)){} //no nned to check the last board; if it is misplaced, there will also be at least one other block in the wrong position, which will be caught by the next if statement
          else if(board[i][j] != num){return false;} //if there is at least one block out of its place, then it is not solved
          num++; //set in increasing order of 1 to (side^2)-1
        }
      }
    return true;
    }
    return false; //while we wait for user input, return false, since returning true would mend the game before it even started
  }

  public void reset()
  {
    while(allMoves.size() > 1)
    {
      move("B"); //this move goes back to the board before the last move; doing so in a while loop will eventually take it back to the first board
    }
    moves = 0; //if the use chooses to reset, then his/her moves will go back to 0.
  }

  public int getMoves() //needed in the Gui class
  {
    return moves;
  }

  public int getSize() //needed in the Gui class
  {
    return side;
  }

  public int[][] getBoard() //needed in the Gui class
  {
    return board;
  }

  public void setSize(int size) //needed in the Gui class so it can change the assigned values in the constructor with the ones given by the user
  {
    side = size;
  }

  public void setSolvable(boolean a) //needed in the Gui class so it can change the assigned values in the constructor with the ones given by the user
  {
    mustSolvable = a;
  }


  //---------------------------------------------------Section for Moving Blocks------------------------------------------------------------
  public void move(String a)
  {
    boolean moved = true; //assume the user will move
    switch(a)
    {
      case "B":
        undo(); //do not increase moves. If it did so, it would be a uselles button, since then the user could just press the opposite keys.
        //This serves so that the user can go back a move without increasing the moves variable
        break;
      case "W":
        //the comments here apply for the next cases too
        moved = moveUp(); //see if the move actually changed the board
        if(moved) //this must repeat in the following 3 cases since it should not be applied to all cases (case "B")
        {
          allMoves.push(copy()); //a new board is set in the list of movements
          moves++; //if the user's choice did not result in any changes on the board, it should not count as a move
        }
        break;
      case "A":
        moved = moveLeft();
        if(moved)
        {
          allMoves.push(copy());
          moves++;
        }
        break;
      case "S":
        moved = moveDown();
        if(moved)
        {
          allMoves.push(copy());
          moves++;
        }
        break;
      case "D":
        moved = moveRight();
        if(moved)
        {
          allMoves.push(copy());
          moves++;
        }
        break;
    }
  }


  public boolean moveRight()
  {
    int pos[] = blankPosition(); //pos[0] is row and pos[1] is col of the blank position

    if(pos[1] > 0) //if the empty block is at the extreme left, nothing can be moved right
    {
      board[pos[0]][pos[1]] = board[pos[0]][pos[1] - 1]; //the empty block will now have the value of the block at its left
      board[pos[0]][pos[1] - 1] = 0; //the block that was at the left of the empty block is now empty
      return true; //the board has changed
    }
    return false; //the board has not changed
  }

  public boolean moveLeft()
  {
    int pos[] = blankPosition();

    if(pos[1] < side - 1) //if the empty block it is at the extreme right, nothing can be moved left
    {
      board[pos[0]][pos[1]] = board[pos[0]][pos[1] + 1]; //the empty block will now have the value of the block at its right
      board[pos[0]][pos[1] + 1] = 0; //the block that was at the right of the empty block is now empty
      return true;
    }
    return false;//the board has not changed
  }

  public boolean moveUp()
  {
    int pos[] = blankPosition();

    if(pos[0] < side - 1) //if the empty block it is at the extreme bottom, nothing can be moved up
    {
      board[pos[0]][pos[1]] = board[pos[0] + 1][pos[1]]; //the empty block will now have the value of the block at its bottom
      board[pos[0] + 1][pos[1]] = 0; //the block that was at the bottom of the empty block is now empty
      return true;
    }
    return false;//the board has not changed
  }

  public boolean moveDown()
  {
    int pos[] = blankPosition();

    if(pos[0] > 0) //if the empty block it is at the extreme top, nothing can be moved down
    {
      board[pos[0]][pos[1]] = board[pos[0] - 1][pos[1]]; //the empty block will now have the value of the block at its top
      board[pos[0] - 1][pos[1]] = 0; //the block that was at the top of the empty block is now empty
      return true;
    }
    return false;//the board has not changed
  }

  public void undo()
  {
    if(allMoves.size() > 1) //in case user click the Go Back button when the board is already at its original position
    {
      allMoves.pop(); //remove the last move from the list of moves
      for(int i = 0; i < allMoves.peek().length; i++)
      {
        for(int j = 0; j < allMoves.peek().length; j++)
        {
          board[i][j] = allMoves.peek()[i][j]; //set the board to the one before the last move
        }
      }
    }
  }

  public int[][] copy() //this method is necessary to set the data for allMoves. this could not be done directly using the board variable since it would be updated every move
  {
    int[][] a = new int[board.length][board.length];
    for(int i = 0; i < board.length; i++)
    {
      for(int j = 0; j < board.length; j++)
      {
        a[i][j] = board[i][j]; //assign all present values of the board to a, which will be then stored in the allMoves
      }
    }
    return a;
  }


    //---------------------------------------------------End of the Section for Moving Blocks------------------------------------------------------------

   //---------------------------------------------------Section for Creating Board------------------------------------------------------------

  public void setBoard() //organize everything in one method
  {
    createBoard(); //createBoard already shuffles it
    if(mustSolvable) //cannot simply create a random array if the users requires it to be solvable
    {
      while(checkSolve() == false)
      {
        shuffleBoard(); //continue to shuffle until is solvable
      }
    }
  }

  public void createBoard()
  {
    int num = 1;
    for(int i = 0; i < side; i++)
    {
      for(int j = 0; j < side; j++)
      {
        board[i][j] = num;
        num++; //set in increasing order of 1 to (side^2)-1
      }
    }
    board[side - 1][side - 1] = 0; //last one must be set to 0 instead of side^2

    shuffleBoard(); //there won't be the need for a perfectly set
  }

  public void shuffleBoard()
  {
    Random rand = new Random();

    for(int times = 0; times < 2; times++) //shuffle it at least 2 times to make sure is really shuffled
    {
      for(int row = 0; row < side; row++)
      {
        for(int col = 0; col < side; col++)
        {
          int i = rand.nextInt(side);
          int j = rand.nextInt(side);

          //swap current block [row][col] with a random one at [i][j]
          int a = board[i][j];
          board[i][j] = board[row][col];
          board[row][col] = a;
        }
      }
    }
  }


  // check if it is solvable (the rules that makes it solvable were taken from
  // https://www.geeksforgeeks.org/check-instance-15-puzzle-solvable/
  // note that I did not use any of the code provided by the website, only its rules for a solvable board and its definition of inversions/swaps
  public boolean checkSolve() //check if it is solvable (the rules that makes it solvable were taken from )
  {
    int inversions = getInversion();
    int[] pos = blankPosition(); //find row of the blank space
    int row = board.length - pos[0]; //row counting from bottom = 1 (this counting of rows was also defined by the website)
    if (board.length % 2 != 0) //if side is odd, then we do not need to check the blankPosition, only the number of inversions
    {
      if(inversions % 2 == 0){return true;} //number of inversions must be even
      return false;
    }
    //otherwise, we need to check both. To be solvable, one needs to be odd and the other even.
    if(inversions % 2 == 0 && row % 2 != 0) //inversion must be even and row must be odd
    {
      return true;
    }
    else if(inversions % 2 != 0 && row % 2 == 0)//OR inversion must be odd and row must be even
    {
      return true;
    }
    return false;
  }

  public int getInversion() //counting the inversions/changes in Bubble Sort
  {
    ArrayList<Integer> aBoard = new ArrayList<Integer>(); //since I'll have to use a 1d array removing the position with value 0, I decided to use ArrayList
    int a = 0; //position in 1d array
    int inver = 0; //number of inversions/swaps
    boolean organized = false;

    for(int i = 0; i < board.length; i++) //converting 2d array to 1d
    {
      for(int j = 0; j < board.length; j++)
      {
        if(board[i][j] != 0){aBoard.add(board[i][j]);} //add without the 0 / empty space
      }
    }

    while(!organized) //keep going until the entire list is organized (Bubble Sort)
    {
      organized = true; //assume that it is organized until an inversion/swap happens.
      for(int i = 0; i < aBoard.size() - 1; i++)
      {
        for(int j = 0; j < aBoard.size() - i; j++) //the -i is because after every round of the i loop, it is expected that the largest value was sent to the end of the list
        {
          if(aBoard.get(i) > aBoard.get(i + 1))
          {
            int num = aBoard.get(i);
            aBoard.set(i, aBoard.get(i + 1));
            aBoard.set(i + 1, num);
            //it must organize the list to count the inversions, for otherwise the loop will go forever
            inver++;
            organized = false; //if it inverted/swapped once, than the while loop has to go one more time
          }
        }
      }
    }
    return inver;
  }
   //---------------------------------------------------End of the Section for Creating Board------------------------------------------------------------

  public int[] blankPosition() //this will also be used for moving the block, not just for creating the board
  {
    int[] pos = new int[2]; //pos[0] is the row value and pos[1] is the col value
    for(int i = 0; i < board.length; i++)
    {
      for(int j = 0; j < board.length; j++)
      {
        if(board[i][j] == 0)
        {
          //define row and col of the blank block
          pos[0] = i;
          pos[1] = j;
        }
      }
    }
    return pos;
  }
}
