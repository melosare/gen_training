
//Assignment 5 Application: GuessGame.java
//Guess the number
import javax.swing.JFrame;

public class GuessGame
{
public static void main( String args[] )
{
   GuessGameFrame guessGameFrame = new GuessGameFrame();
   guessGameFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
   guessGameFrame.setSize( 300, 175 ); // set frame size
   guessGameFrame.setVisible( true ); // display frame
} // end main
} // end class GuessGame
