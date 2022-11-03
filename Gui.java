import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.PrintStream;
import java.io.File;
import java.io.FileNotFoundException;

public class Gui implements ActionListener, KeyListener
{
  private boolean won;
  private boolean mustSolvable;
  private int size;
  private boolean canCheck = false; //canCheck because at the start, the Gui is crashing/not working when using the getWin and getHighScore methods, since the board is waiting to be completly set.
  private boolean playAgain = false;
  //folowing booleans to check if the user has already choosed the option before clicking next
  private boolean sizeClick = false;
  private boolean solvableClick = false;

  private JFrame frame;
  private JPanel panel;
  private JPanel start;
  private JPanel theBoard;
  private JPanel info;
  private JPanel endGame;
  private JPanel numButtons, solveButtons;

  private JButton goBack;
  private JButton reset;
  private JButton newGame;
  private JButton again, notAgain;
  private JButton viewTutorial;
  private JButton three, four, five, six, seven;
  private JButton solvable, notSolvable;
  private JButton next;

  private JLabel tutorial = new JLabel("");
  private JLabel labelSolvable, labelSize;
  private JLabel moves;
  private JLabel mustChose;
  private JLabel highScore;
  private JLabel winScreen;
  private JLabel[][] boxes;

  private Board board;

  public Gui() throws java.io.FileNotFoundException
  {
    frame = new JFrame();
    panel = new JPanel();
    theBoard = new JPanel();
    info = new JPanel();
    endGame = new JPanel();
    start = new JPanel();
    numButtons = new JPanel();
    solveButtons = new JPanel();

    board = new Board();

    won = false;

    //-------------set JLabels and JButtons (Link 2 and Link 12)--------
    goBack = new JButton("Go Back One Move");
    reset = new JButton("Reset");
    newGame = new JButton("New Game");
    again = new JButton("Yes");
    notAgain = new JButton("No");
    viewTutorial = new JButton("View Tutorial");
    three = new JButton("3");
    four = new JButton("4");
    five = new JButton("5");
    six = new JButton("6");
    seven = new JButton("7");
    solvable = new JButton("Yes");
    notSolvable = new JButton("No");
    next = new JButton("Next ->");

    setTutorialLabel(); //since tutorial text is long, I decided to set it on a method so as not to polute the constructor
    labelSize = new JLabel("Select the size of your board:");
    labelSolvable = new JLabel("Do you want a necessarily solvable board?");
    moves = new JLabel("Moves: 0");
    mustChose = new JLabel("You need to chose the options shown before starting the game!");
    winScreen = new JLabel("<html>Congratulations!!! You won!!!", SwingConstants.CENTER); //<html> is for breaking lines in the showWin method (Link 11); SwingConstants.CENTER to center it on the panel (Link 5)
    setLabelFont();

    //-----------------Give action, color and set size of JButtons (Link 2)-------
    goBack.addActionListener(this);
    goBack.setSize(new Dimension(70, 30));
    goBack.setBackground(Color.BLACK);
    goBack.setForeground(Color.WHITE);

    newGame.addActionListener(this);
    newGame.setSize(new Dimension(70, 30));
    newGame.setBackground(Color.BLACK);
    newGame.setForeground(Color.WHITE);

    reset.addActionListener(this);
    reset.setSize(new Dimension(70, 30));
    reset.setBackground(Color.BLACK);
    reset.setForeground(Color.WHITE);

    again.addActionListener(this);
    again.setSize(new Dimension(70, 30));
    again.setBackground(Color.BLACK);
    again.setForeground(Color.WHITE);

    notAgain.addActionListener(this);
    notAgain.setSize(new Dimension(70, 30));
    notAgain.setBackground(Color.BLACK);
    notAgain.setForeground(Color.WHITE);

    viewTutorial.addActionListener(this);
    viewTutorial.setSize(new Dimension(70, 30));
    viewTutorial.setBackground(Color.BLACK);
    viewTutorial.setForeground(Color.WHITE);

    solvable.addActionListener(this);
    solvable.setSize(new Dimension(70, 30));
    solvable.setBackground(Color.BLACK);
    solvable.setForeground(Color.WHITE);

    notSolvable.addActionListener(this);
    notSolvable.setSize(new Dimension(70, 30));
    notSolvable.setBackground(Color.BLACK);
    notSolvable.setForeground(Color.WHITE);

    three.addActionListener(this);
    three.setSize(new Dimension(70, 30));
    three.setBackground(Color.BLACK);
    three.setForeground(Color.WHITE);

    four.addActionListener(this);
    four.setSize(new Dimension(70, 30));
    four.setBackground(Color.BLACK);
    four.setForeground(Color.WHITE);

    five.addActionListener(this);
    five.setSize(new Dimension(70, 30));
    five.setBackground(Color.BLACK);
    five.setForeground(Color.WHITE);

    six.addActionListener(this);
    six.setSize(new Dimension(70, 30));
    six.setBackground(Color.BLACK);
    six.setForeground(Color.WHITE);

    seven.addActionListener(this);
    seven.setSize(new Dimension(70, 30));
    seven.setBackground(Color.BLACK);
    seven.setForeground(Color.WHITE);

    next.addActionListener(this);
    next.setSize(new Dimension(70, 30));
    next.setBackground(Color.BLACK);
    next.setForeground(Color.WHITE);



    //------------set start JPanel (together with numButtons and solveButtons JPanels)-------
    //since the start JPanel is in the Y_AXIS and the buttons need to be organized in the X_AXIS, I created two sepate panels for the two groups of buttons (numButtons and solveButtons)
    //I tried using GirdBagLayout so I didn't have to create these extra panels but I could not organize it properly
    numButtons.setLayout(new BoxLayout(numButtons, BoxLayout.X_AXIS)); //Link 3
    numButtons.add(three);
    numButtons.add(four);
    numButtons.add(five);
    numButtons.add(six);
    numButtons.add(seven);

    solveButtons.setLayout(new BoxLayout(solveButtons, BoxLayout.X_AXIS));//Link 3
    solveButtons.add(solvable);
    solveButtons.add(notSolvable);

    start.setLayout(new BoxLayout(start, BoxLayout.Y_AXIS));//Link 3

    start.add(viewTutorial);
    start.add(Box.createRigidArea(new Dimension(0, 5))); // space between each button/label
    start.add(tutorial);
    tutorial.setVisible(false); //only visible after user clicked on viewTutorial (Link 8)
    start.add(Box.createRigidArea(new Dimension(0, 25))); // space between each button/label
    start.add(labelSize);
    start.add(Box.createRigidArea(new Dimension(0, 5))); // space between each button/label
    start.add(numButtons);
    start.add(Box.createRigidArea(new Dimension(0, 25))); // space between each button/label
    start.add(labelSolvable);
    start.add(Box.createRigidArea(new Dimension(0, 5))); // space between each button/label
    start.add(solveButtons);
    start.add(Box.createRigidArea(new Dimension(0, 25))); // space between each button/label
    start.add(next);
    start.add(Box.createRigidArea(new Dimension(0, 5))); // space between each button/label
    start.add(mustChose);
    mustChose.setVisible(false); //only show this label if user clicks next before selecting all the options

    viewTutorial.setAlignmentX(Component.LEFT_ALIGNMENT);
    tutorial.setAlignmentX(Component.LEFT_ALIGNMENT);
    labelSize.setAlignmentX(Component.LEFT_ALIGNMENT);
    numButtons.setAlignmentX(Component.LEFT_ALIGNMENT);
    labelSolvable.setAlignmentX(Component.LEFT_ALIGNMENT);
    solveButtons.setAlignmentX(Component.LEFT_ALIGNMENT);
    next.setAlignmentX(Component.LEFT_ALIGNMENT);
    mustChose.setAlignmentX(Component.LEFT_ALIGNMENT);


    //------------set endGame JPane (Everything here I learned from Link 3)-------
    endGame.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    endGame.setLayout(new GridBagLayout());
    GridBagConstraints g = new GridBagConstraints();

    g.gridx = 0;
    g.gridy = 0;
    g.fill = GridBagConstraints.BOTH;
    endGame.add(winScreen, g);

    g.gridx = 0;
    g.gridy = 2;
    g.fill = GridBagConstraints.NONE;
    endGame.add(again, g);

    //buttons in the same row
    g.gridx = 2;
    g.gridy = 2;
    endGame.add(notAgain, g);
    //this panel will only be visible after the game ends (it becomes visible when showWin is called)
    endGame.setVisible(false); //Link 8

    //---------set panel JPanel (Link 2)------
    panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS)); //Link 3
    panel.setPreferredSize(new Dimension(900, 650));
    panel.add(endGame); //Link1
    panel.add(start); //Link1

    changeKeyListener(true);
    panel.addKeyListener(this);

    //----------setJFrame (Link 2)------------
    frame.setContentPane(panel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("15 Puzzle");
    frame.pack();
    frame.setVisible(true);
  }

  public void initializeBoard()
  {
    start.setVisible(false); //Link 8
    //update board now that we have the user's choices
    board.setSize(size);
    board.setSolvable(mustSolvable);
    board.startBoard();

    boxes = new JLabel[board.getSize()][board.getSize()]; //each box is a block of the board

    //we cannot fully define the highScore here because it would need to throw
    //the FileNotFoundException here, and therefore, on the ActionListener, which, for some reason, does not accept it.
    //On the other hand, we must assign it something so that there is no crash when the main method call setHighScore
    highScore = new JLabel("");
    highScore.setFont(new Font(tutorial.getFont().getName(), Font.PLAIN, 20)); //now that it is assigned we can change its font (Link 7)


    //the following panels were not set in the contructor because they rely on the board to be properly defined
    //-------------set theBoard JPanel----
    theBoard.setLayout(new GridLayout(board.getSize(), board.getSize())); //GridLayout chosen as it is easier to create a board shape
    setBoard(); //this method defines each part of the boxes array and adds it to the theBoard panel

    //------------set info JPanel----------
    info.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
    info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS)); //Link 3
    info.add(goBack);
    info.add(Box.createRigidArea(new Dimension(0, 15))); // space between each button/label
    info.add(reset);
    info.add(Box.createRigidArea(new Dimension(0, 15)));
    info.add(newGame);
    info.add(Box.createRigidArea(new Dimension(0, 15)));
    info.add(moves);
    info.add(Box.createRigidArea(new Dimension(0, 15)));
    info.add(highScore);


    panel.add(theBoard); //Link1
    panel.add(info); //Link1
    changeBoard(); //I could only include the design changes in this method; thus, we need to call it even though there was no change in the board after setBoard was called
  }


  //------------------MAIN METHOD----------------------------
  public static void main(String[] args) throws java.lang.InterruptedException, java.io.FileNotFoundException
  {
    do
    {
      boolean continueWhile = false; //if the user presses newGame, it must break the while loop that it's in and continue the main while loop
      Gui gui = new Gui(); //when creating the gui, also create an object in order to call non-static methods

      while(gui.getCanCheck() == false){}

      gui.setHighScoreLabel();  //now the board is fully intialized, so we have the information to do so

      while(gui.getWin() == false)
      {
        Thread.sleep(100); //the loop was not working, probably because it was going too fast
        if(gui.getPlayAgain() == true)
        {
          continueWhile = true; //now we have to skip to the next iteration
          break;
        }
      }

      if(continueWhile)
      {
        gui.hideThisGui(); //I don't know how to restart the game in the same frame; thus, the present frame will be hidden while another one is created
        continue; //skip next lines and go to next interation of the loop because the user did not win the game
      }

      gui.showWin();
      gui.changeKeyListener(false); //user cannot make any further move with the keyboard

      //before seeing what the user chose, we must make sure he/she chose something using canCheck. Since canCheck is set to true after the game ends, we must set it to false
      gui.setCanCheck(false);
      while(gui.getCanCheck() == false){}//wait until getCanCheck is true
      if(gui.getPlayAgain())
      {
        gui.hideThisGui(); //I don't know how to restart the game in the same frame; thus, the present frame will be hidden while another one is created
      }
      //no need for an else statement, since if the user chose not to play again, the game will finish immediately; therefore, we don't have to break the while loop at any time
    }while(true);
  }




  //---------------------GETTER METHODS-------------------------------
  public int[] getHighScore() throws java.io.FileNotFoundException
  {
    Scanner inputFile = new Scanner(new File("HighScore.txt"));

    int[] theHighScore = setScoreData(inputFile.nextLine());
    //high score will be read from the file storing it.
    //the score is measured in the least amount of movements. Therefore, the first score on the file cannot be 0, because this would mean it will never be beaten.
    //I set the intial scores to be 10 million to each board size, since it is highly unlikely the user will make this many moves.
    return theHighScore;
  }

  public boolean getWin()
  {
    if(canCheck){won = board.checkWin(true);} //canCheck means that the board was already fully initialized. calling checkWin without doing so would crash the program
    return won;
  }

  public boolean getCanCheck()
  {
    if(!canCheck)
    {
      System.out.print(""); //I don't know why, but both the if statement and the print were necessary to update the canCheck
    }
    return canCheck;
  }

  public boolean getPlayAgain()
  {
    return playAgain;
  }

  public boolean getSolvable()
  {
    return mustSolvable;
  }

  public int getSize()
  {
    return size;
  }




  //-------------------------------------SETTER METHODS--------------------------------
  public void setCanCheck(boolean a)
  {
    canCheck = a;
  }

  public void setHighScoreLabel() throws java.io.FileNotFoundException
  {
    if(canCheck == false) //canCheck is here for the same reason it is in the getWin method
    {

    }
    else
    {
      highScore.setText("High Score: " + Integer.toString(getHighScore()[board.getSize() - 3]) + " moves."); //(board.getSize - 3) since getHighScore[0] stores the value of the 3-sized board
      if(getHighScore()[board.getSize() - 3] == 10000000) //1000000 is the value on the .txt file before the game is played at least once.
      {
        highScore.setText("High Score: ----");
      }
    }
  }

  public static int[] setScoreData(String t) //transform what is in the .txt file into an array
  {
    int[] data = new int[5]; //different high scores for each of the 5 board sizes;

    for(int i = 0; i < data.length; i++)
    {
      if(i == data.length - 1)
      {
        data[i] = Integer.parseInt(t.substring(0)); //if it is the last one, there will not be a space after
      }
      else
      {
        data[i] = Integer.parseInt(t.substring(0, t.indexOf(" "))); //turns the string in the file to a integer so it can be stored on the array
        t = t.substring(t.indexOf(" ") + 1);
      }
    }
    return data;
  }

  public void setNewHighScore(int newScore, int[] data) throws java.io.FileNotFoundException
  {
    data[board.getSize() - 3] = newScore; //the new high score is only for the specific board size that was played
    String score = "";

    for(int i : data)
    {
      score = score + i + " "; //there must be space so the code works after the user wins and updates the .txt file
    }

    PrintStream writeFile = new PrintStream("HighScore.txt");
    writeFile.print(score.trim()); //.trim() to remove space after the last number, sicne the loop sets a space after each value
  }

  public void setTutorialLabel()
  {
    tutorial = new JLabel("<html>This puzzle goes by the name '15-puzzle' when played with a size 4 board. However, you can pick sizes from 3 to 7" +
                     "<br>Slide the numbers around (using the arrow keys in your keyboard) until they are in numerical order from least to greatest, " +
                     "left to right and top to bottom. <br>The white space is the open space that the numbered squares are moved to. Only numbers that " +
                     "are immediately to the left, right, above or below the empty space can be moved. <br>The open space should end up in the lower right-hand " +
                     "corner when the puzzle has been solved.<br>If you are feeling confident, you can choose your board not to be necessarily solvable. This will " +
                     "generate a random board that may be impossible to solve. You�ll only know if you try!<br><br>Tip: If you want to redo your action, you can do " +
                      "so by pressing the opposite key. However, this will cost you a move. To avoid this, press the button Go Back. You can also press Reset, which " +
                      "will bring to the original board and set your moves back to 0.<br>Good Luck!!!!</html>", SwingConstants.CENTER);
  }

  public void setLabelFont() //highScore not included because it is yet to be assigned (Link 7)
  {
    tutorial.setFont(new Font(tutorial.getFont().getName(), Font.PLAIN, 15));
    labelSolvable.setFont(new Font(tutorial.getFont().getName(), Font.PLAIN, 20));
    labelSize.setFont(new Font(tutorial.getFont().getName(), Font.PLAIN, 20));
    moves.setFont(new Font(tutorial.getFont().getName(), Font.PLAIN, 20));
    mustChose.setFont(new Font(tutorial.getFont().getName(), Font.PLAIN, 20));
    winScreen.setFont(new Font(tutorial.getFont().getName(), Font.PLAIN, 30));
  }


  //----------------------OTHER METHODS-------------------------
  public void hideThisGui()
  {
    frame.setVisible(false);
  }

  public void showWin() throws java.io.FileNotFoundException
  {
    int[] theHighScore = getHighScore();
    int highScore = theHighScore[board.getSize() - 3]; //side 3 is on [0], side 4 on [1], etc.;

    theBoard.setVisible(false); //Link 8
    info.setVisible(false); //Link 8
    endGame.setVisible(true); //Link 8

    if(board.getMoves() < highScore) //user beat the previous high score
    {
      winScreen.setText(winScreen.getText() + "<br><br>NEW HIGH SCORE!!!<br> It took you only " + board.getMoves() + " moves to win."); //Link 11
      setNewHighScore(board.getMoves(), theHighScore);
    }

    winScreen.setText(winScreen.getText() + "<br><br>Do you want to play again?<br><br></html>"); //Link 11
  }



  //-------------------KEY MOVEMENTS-------------------
  public void changeKeyListener(boolean a)
  {
    panel.setFocusable(a); //Link 6
    panel.requestFocusInWindow(); //Link 6
  }

  public void keyPressed(KeyEvent arg0) //this method is an adaption of the one that I used for last year's culminating assignment
  {
    switch(arg0.getKeyCode())
    {
        case KeyEvent.VK_DOWN:
          board.move("S");
          changeBoard();
          break;
        case KeyEvent.VK_UP:
          board.move("W");
          changeBoard();
          break;
        case KeyEvent.VK_RIGHT:
          board.move("D");
          changeBoard();
          break;
        case KeyEvent.VK_LEFT:
          board.move("A");
          changeBoard();
          break;
     }
    moves.setText("Moves: " + board.getMoves()); //update the label
  }

    public void keyReleased (KeyEvent arg0) {}

    public void keyTyped (KeyEvent arg0) {}





  //------------BOARD ACTIONS--------------
  public void setBoard()
  {
    for(int i = 0; i < board.getSize(); i++)
    {
      for(int j = 0; j < board.getSize(); j++)
      {
        String num = Integer.toString(board.getBoard()[i][j]); //cannot add int to label, so assign it to a String
        if(num.equals("0"))
        {
          boxes[i][j] = new JLabel("", SwingConstants.CENTER); //keep block blank if it is 0
        }
        else
        {
          boxes[i][j] = new JLabel(num, SwingConstants.CENTER);
        }
        boxes[i][j].addKeyListener(this);
        boxes[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); //Link 4; setting the border of each label creates the lines of the grid
        theBoard.add(boxes[i][j]);
      }
    }
  }

  public void changeBoard() //this method is to update the board whenever it might have changed; it also sets the design for the board (I could not do that in the setBoard method)
  {
    for(int i = 0; i < board.getSize(); i++)
    {
      for(int j = 0; j < board.getSize(); j++)
      {
        String num = Integer.toString(board.getBoard()[i][j]);
        if(num.equals("0"))
        {
          boxes[i][j].setText("");
        }
        else
        {
          boxes[i][j].setText(num);
        }

        Font labelFont = boxes[i][j].getFont(); //LINK 7
        boxes[i][j].setFont(new Font(labelFont.getName(), Font.PLAIN, 60)); //LINK 7
      }
    }
  }



  //--------------BUTTON ACTIONs & DESIGN------------------
  public void makeButtonBlack(String buttonType) //using Link 12
  {
    switch(buttonType)
    {
      case "solvable":
        solvable.setBackground(Color.BLACK); //set back to standard in case this button was pressed before
        notSolvable.setBackground(Color.BLACK); //set back to standard in case this button was pressed before
        break;
      case "size":
        three.setBackground(Color.BLACK);
        four.setBackground(Color.BLACK);
        five.setBackground(Color.BLACK);
        six.setBackground(Color.BLACK);
        seven.setBackground(Color.BLACK);
        break;
    }
  }


  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource() == goBack) //Link 10
    {
      board.move("B");
      changeBoard(); //update for new board
      moves.setText("Moves: "+ board.getMoves());

    }
    else if(e.getSource() == reset)
    {
      board.reset();
      changeBoard();
      moves.setText("Moves: "+ board.getMoves());
    }
    else if(e.getSource() == newGame || e.getSource() == again)
    {
      playAgain = true;
      canCheck = true;
      moves.setText("Moves: "+ board.getMoves());
    }
    else if(e.getSource() == notAgain)
    {
      System.exit(0); //Link 9
    }
    else if(e.getSource() == viewTutorial)
    {
      tutorial.setVisible(true);
    }
    else if(e.getSource() == solvable)
    {
      mustSolvable = true; //user this option
      solvableClick = true; //user already chose an option
      makeButtonBlack("solvable"); //make sure that, if the user has already pressed another solve button, it goes back to normal (instead of having more than one green button). Now all buttons will be black
      solvable.setBackground(Color.GREEN); //make sure the button pressed becomes green
    }
    else if(e.getSource() == notSolvable)
    {
      mustSolvable = false;
      solvableClick = true;
      makeButtonBlack("solvable");
      notSolvable.setBackground(Color.GREEN);
    }
    else if(e.getSource() == three)
    {
      size = 3; //user chose this size
      sizeClick = true; //user already chose a size
      makeButtonBlack("size"); //make sure that, if the user has already pressed another size button, it goes back to normal (instead of having more than one green button). Now all buttons will be black
      three.setBackground(Color.GREEN); //make sure the button pressed becomes green
    }
    else if(e.getSource() == four)
    {
      size = 4;
      sizeClick = true;
      makeButtonBlack("size");
      four.setBackground(Color.GREEN);
    }
    else if(e.getSource() == five)
    {
      size = 5;
      sizeClick = true;
      makeButtonBlack("size");
      five.setBackground(Color.GREEN);
    }
    else if(e.getSource() == six)
    {
      size = 6;
      sizeClick = true;
      makeButtonBlack("size");
      six.setBackground(Color.GREEN);
    }
    else if(e.getSource() == seven)
    {
      size = 7;
      sizeClick = true;
      makeButtonBlack("size");
      seven.setBackground(Color.GREEN);
    }
    else if(e.getSource() == next)
    {
      if(sizeClick && solvableClick)
      {
        initializeBoard(); //only continue if user already chose the options
        canCheck = true; //now getWin and getHighScore can be called
      }
      else
      {
        mustChose.setVisible(true); //show the user that he/she must choose before advancing
      }
    }
    //everytime the button was pressed, the keyListener would stop working, so I added this to make sure it does not happen
    changeKeyListener(true); //Link 6
  }
}
