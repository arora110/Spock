// Chinmai Raman and Akash Arora

import java.net.Socket;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class SpockClientListener implements Runnable
{
	private Socket connectionSock = null;

	int playerTurn = 2;

	SpockClientListener(Socket sock)
	{
		this.connectionSock = sock;
	}

	public void run()
	{
    // Wait for data from the server.  If received, output it.
		try
		{
			Spock game = new Spock();
			// Welcome Speech
			System.out.println("\nXx-------------------------------------------------------------------------------------------xX");
			System.out.println("                          Welcome to Rock, Paper, Scissors, Lizard, Spock!\n");
			System.out.println("Rules:");
			System.out.println("     1) Weapon choices are case sensitive. Please refer to the title");
			System.out.println("        incase you forgot how to spell.");
			System.out.println("     2) WAIT until all players have logged in before choosing a weapon.");
			System.out.println("     3) Enter 'GO' only when after all players have locked in.");
			System.out.println("     4) Enter 'RESET' to reset game\n");
			System.out.println("In the Big Bang Theory episode 'The Rothman Disintegration' Sheldon explains:\n");
			System.out.println("\"Scissors cuts paper, paper covers rock, rock crushes lizard, lizard poisons Spock,");
			System.out.println("Spock smashes scissors, scissors decapitates lizard,lizard eats paper, paper disproves Spock");
			System.out.println("Spock vaporizes rock, and as it always has, rock crushes scissors.\"");
			System.out.println("\nSelect Weapon: ");
			BufferedReader serverInput = new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));
			while (true)
			{
				// Get data sent from the server
				String serverText = serverInput.readLine();
				String[] serverTextArr = serverText.split(" ");
				String command = serverTextArr[0].toLowerCase();
				int currPlayer = Integer.parseInt(serverTextArr[2]);
			        if ((game.isLocked[currPlayer] == 0) && game.isValidInputTwo(currPlayer, serverTextArr[0])) {
					System.out.println("\nUPDATE: Player " + serverTextArr[2] + " has locked in. Enter 'GO' to run game once all players have locked in.");
					System.out.println();
				} else if (command.equals("display")) {
					System.out.println("\n" + game.displayChoices());
				} else if (command.equals("go")) {
					System.out.println("\n" + game.displayWinners()); 
					System.out.println("\n" + game.displayChoices());
				} 
				if (command.equals("reset") || command.equals("go")) {
					game.reset();
					System.out.println("\n                                        Game has been RESET\n");
					System.out.println("Select Weapon: ");
				}      
				
				if (serverInput != null)
				{
					if (game.isOver())
					{
						System.out.println("Game over my guys. Player " + playerTurn + " wins.");
						System.out.println("Goodbye, please press enter.");
						connectionSock.close();
						break;
					}
				}
				else
				{
					// Connection was lost
					System.out.println("Closing connection for socket " + connectionSock);
					connectionSock.close();
					break;
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("Error: " + e.toString());
		}
	}
}
