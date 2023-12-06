import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class TicTacToeFrame extends JFrame
{
    JPanel mainP, topP, centerP, bottomP;
    JButton quitB;
    JLabel turn;

    int numAdvance = 0;

    boolean playerAdvance = true;

    private static final int ROW = 3;
    private static final int COL = 3;

    TicTacToeTile[][] slat = new TicTacToeTile[3][3];
    public TicTacToeFrame() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension displaySize = kit.getScreenSize();
        int displayHeight = displaySize.height;
        int displayWidth = displaySize.width;

        setSize(displayWidth * 3/4,600);
        setLocation(displayWidth / 8, (displayHeight - 600) / 2);

        setTitle("Game of Tic, Tac, & Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createGUI();
        setVisible(true);
    }

    private void createGUI() {
        mainP = new JPanel();
        topP = new JPanel();
        centerP = new JPanel();
        bottomP = new JPanel();

        mainP.setLayout(new BorderLayout());
        mainP.add(topP, BorderLayout.NORTH);
        mainP.add(centerP, BorderLayout.CENTER);
        mainP.add(bottomP, BorderLayout.SOUTH);

        add(mainP);
        createtopP();
        creategridP();
        createbottomP();
    }


    private void createtopP() {
        turn = new JLabel("Turn for X");
        turn.setFont(new Font(Font.SERIF, Font.PLAIN, 35));

        topP.add(turn);
    }
    private void creategridP()
    {
        centerP.setLayout(new GridLayout(3, 3));

        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
            {
                slat[row][col] = new TicTacToeTile(row, col);
                slat[row][col].setText(" ");
                slat[row][col].setFont(new Font(Font.SERIF, Font.PLAIN, 15));
                slat[row][col].addActionListener((ActionEvent ae) -> {

                    TicTacToeTile player =  (TicTacToeTile) ae.getSource();

                    if (!player.getText().equals(" "))
                    {
                        JOptionPane.showMessageDialog(null, "Illegal Move. Try another empty square");
                        return;

                    }
                    if (playerAdvance)
                    {
                        player.setText("X");
                        player.setFont(new Font(Font.SERIF, Font.PLAIN, 30));
                        turn.setText("O turn");
                    }
                    else
                    {
                        player.setText("O");
                        player.setFont(new Font(Font.SERIF, Font.PLAIN, 30));
                        turn.setText("X turn");
                    }
                    playerAdvance = !playerAdvance;
                    numAdvance++;

                    if (numAdvance >= 5)
                    {
                        ProcessWin();
                    }
                    if (numAdvance >= 7)
                    {
                        ProcessTie();
                    }

                });
                centerP.add(slat[row][col]);

            }
    }

    private void ProcessTie() {
        boolean xFlag = false;
        boolean oFlag = false;
        // Check all 8 win vectors for an X and O so
        // no win is possible
        // Check for row ties
        for(int row=0; row < ROW; row++)
        {
            if(slat[row][0].equals("X") ||
                    slat[row][1].equals("X") ||
                    slat[row][2].equals("X"))
            {
                // xFlag = true; // This col has an X in it
                JOptionPane.showMessageDialog(null, "Game is Tie");
                restartGame();
                return;
            }
            if(slat[row][0].equals("O") ||
                    slat[row][1].equals("O") ||
                    slat[row][2].equals("O"))
            {
                //oFlag = true; // there is an O in this row
                JOptionPane.showMessageDialog(null, "Game is Tie");
                restartGame();
                return;
            }
            /*if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a row win
            }
            xFlag = oFlag = false;  */
        }
        // Now scan the columns
        for(int col=0; col < COL; col++)
        {
            if(slat[0][col].equals("X") ||
                    slat[1][col].equals("X") ||
                    slat[2][col].equals("X"))
            {
                //xFlag = true; // there is an X in this col
                JOptionPane.showMessageDialog(null, "Game is Tie");
                restartGame();
                return;
            }
            if(slat[0][col].equals("O") ||
                    slat[1][col].equals("O") ||
                    slat[2][col].equals("O"))
            {
                //oFlag = true; // there is an O in this col
                JOptionPane.showMessageDialog(null, "Game is Tie");
                restartGame();
                return;
            }
            /*if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a col win
            } */
        }
        // Now check for the diagonals
        //xFlag = oFlag = false;
        if(slat[0][0].equals("X") ||
                slat[1][1].equals("X") ||
                slat[2][2].equals("X") )
        {
            //xFlag = true;
            JOptionPane.showMessageDialog(null, "Game is Tie");
            restartGame();
            return;
        }
        if(slat[0][0].equals("O") ||
                slat[1][1].equals("O") ||
                slat[2][2].equals("O") )
        {
            //oFlag = true;
            JOptionPane.showMessageDialog(null, "Game is Tie");
            restartGame();
            return;
        }
        /* if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }
        xFlag = oFlag = false; */

        if(slat[0][2].equals("X") ||
                slat[1][1].equals("X") ||
                slat[2][0].equals("X") )
        {
            //xFlag = true;
            JOptionPane.showMessageDialog(null, "Game is Tie");
            restartGame();
            return;
        }
        if(slat[0][2].equals("O") ||
                slat[1][1].equals("O") ||
                slat[2][0].equals("O") )
        {
            //oFlag = true;
            JOptionPane.showMessageDialog(null, "Game is Tie");
            restartGame();
            return;
        }
        /* if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        } */

        // Checked every vector so I know I have a tie

        return ;
    }



    private void ProcessWin() {
        //row win
        for (int row = 0; row < 3; row++) {
            if (slat[row][0].getText().equals(slat[row][1].getText())
                    && slat[row][1].getText().equals(slat[row][2].getText())
                    && !slat[row][0].getText().equals(" ")) {
                JOptionPane.showMessageDialog(null, slat[row][0].getText() + " wins!");
                restartGame();
                return;
            }
        }
        //col win
        for (int col = 0; col < 3; col++) {
            if (slat[0][col].getText().equals(slat[1][col].getText())
                    && slat[1][col].getText().equals(slat[2][col].getText())
                    && !slat[0][col].getText().equals(" ")) {
                JOptionPane.showMessageDialog(null, slat[0][col].getText() + " wins!");
                restartGame();
                return;
            }
        }
        // diagonial win
        if (slat[0][0].getText().equals(slat[1][1].getText())
                && slat[1][1].getText().equals(slat[2][2].getText())
                && !slat[0][0].getText().equals(" ")) {
            JOptionPane.showMessageDialog(null, slat[0][0].getText() + " wins!");
            restartGame();
            return;
        }
        //diagonial win
        if (slat[0][2].getText().equals(slat[1][1].getText())
                && slat[1][1].getText().equals(slat[2][0].getText())
                && !slat[0][2].getText().equals(" ")) {
            JOptionPane.showMessageDialog(null, slat[0][2].getText() + " wins!");
            restartGame();
            return;
        }
    }
    private boolean restartGame()
    {
        int dialogButton = JOptionPane.showConfirmDialog(null, "Do you play the game again?", "End Game?", JOptionPane.YES_NO_OPTION);
        if (dialogButton == JOptionPane.YES_OPTION)
        {
            clearSlat();
        }
        else
        {
            System.exit(0);
        }
        return false;
    }

    private void clearSlat() {
        for(int row=0; row < ROW; row++)
        {
            for(int col=0; col < COL; col++)
            {
                slat[row][col].setText(" ");
            }
        }
        numAdvance= 0;
        playerAdvance = true;
        turn.setText("X turn");
    }
    private void createbottomP()
    {
        quitB = new JButton("Terminate");
        quitB.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        quitB.addActionListener((ActionEvent ae) -> System.exit(0));

        bottomP.add(quitB);
    }

}
