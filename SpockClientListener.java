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
			System.out.println("\nXx--------------------------------------------------------------------------------xX");
			System.out.println("                  Welcome to Rock, Paper, Scissors, Lizard, Spock!\n");
			System.out.println("Rules:");
			System.out.println("     1) Weapon Choices are case sensitive. Please refer to the title");
			System.out.println("        incase you forgot how to spell.");
			System.out.println("     2) WAIT until all players have logged in before choosing a weapon.");
			System.out.println();
			BufferedReader serverInput = new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));
			while (true)
			{
				// Turn handling
			//	if (playerTurn == 2)
			//		playerTurn = 1;
			//	else
			//		playerTurn = 2;
			//	System.out.println("It is player " + playerTurn + "'s turn:");
			
			//	System.out.println(game.getBoard());
				// Get data sent from the server
				String serverText = serverInput.readLine();
				String[] serverTextArr = serverText.split(" ");
				int currPlayer = Integer.parseInt(serverTextArr[2]);
			        if (game.isValidInputTwo(currPlayer, serverTextArr[0])) {
					System.out.println("Player " + serverTextArr[2] + " has locked in.");
					System.out.println();
				} else if (serverTextArr[0].equals("display")) {
					System.out.println(game.displayChoices());
				} else if (serverTextArr[0].equals("go")) {
					System.out.println(game.displayWinners()); 
					System.out.println();
					System.out.println(game.displayChoices());
				} else {
				//	System.out.println("serverText: " + serverTextArr[0]);
					System.out.println();
					System.out.println("Invalid Input, please try again: ");
				}      
				
				if (serverInput != null)
				{
					// get ints from string input, to be used in Nim's updateHeap()
				//	String[] serverTextArr = serverText.split(" ");
				//	int heap = Integer.parseInt(serverTextArr[0]);
				//	int num = Integer.parseInt(serverTextArr[1]);
				//	game.updateHeap((heap - 1), num);
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
