
//Assignment 5 Solution: GuessGameFrame.java
//Guess the number
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

public class GuessGameFrame extends JFrame
{
	private static Random generator = new Random();
	private int number; // number chosen by application
	private JTextField guessInputJTextField; // for guessing
	private JLabel prompt1JLabel; // first prompt to user
	private JLabel prompt2JLabel; // second prompt to user
	private JLabel messageJLabel; // displays message of game status
	private JButton newGameJButton; // creates new game

	// set up GUI and initialize values
	public GuessGameFrame()
	{
		super( "Guessing Game" );

		prompt1JLabel = new JLabel(
				"I have a number between 1 and 1000." ); // describe game
		prompt2JLabel = new JLabel(
				"Can you guess my number? Enter your Guess:" ); // prompt user

		guessInputJTextField = new JTextField( 5 ); // to enter guesses
		guessInputJTextField.addActionListener( new GuessHandler( ) );
		messageJLabel = new JLabel( "Guess result appears here." );

		newGameJButton = new JButton( "New Game" ); // starts new game
		newGameJButton.addActionListener(

				new ActionListener() // anonymous inner class
				{
					public void actionPerformed( ActionEvent e )
					{
						messageJLabel.setText( "Guess Result" );
						guessInputJTextField.setText( "" ); // reset guess field
						guessInputJTextField.setEditable( true ); // allow guesses
						startTheGame(); // start new game
						repaint(); // repaint application
					} // end method actionPerformed
				} // end anonymous inner class
				); // end call to addActionListener

		setLayout( new FlowLayout() ); // set layout
		add( prompt1JLabel ); // add first prompt
		add( prompt2JLabel ); // add second prompt
		add( guessInputJTextField ); // add guessing textfield
		add( messageJLabel ); // add message label
		add( newGameJButton ); // add button to create new game

		startTheGame(); // start new game
	} // end GuessGameFrame constructor

	// choose a new random number
	public void startTheGame()
	{
		number = generator.nextInt( 1000 ) + 1;
	} // end method theGame

	// react to new guess
	public void response( int guess )
	{
		// guess is too high
		if ( guess > number )
		{
			messageJLabel.setText( "Too High. Try a lower number." );
		} // end if
		else if ( guess < number ) // guess is too low
		{
			messageJLabel.setText( "Too Low. Try a higher number." );
		} // end else if
		else // guess is correct
		{
			messageJLabel.setText( "Correct!" );
			guessInputJTextField.setEditable( false );
		} // end else

		repaint();
	} // end method response

	// inner class acts on user input
	class GuessHandler implements ActionListener
	{
		public void actionPerformed( ActionEvent e )
		{
			int guess = Integer.parseInt( guessInputJTextField.getText() );
			response( guess );
		} // end method actionPerformed
	} // end inner class GuessHandler
} // end class GuessGameFrame
