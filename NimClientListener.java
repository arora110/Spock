// Bobby Kain and Akash
// edited version of Dr Fahys ClientListener.java

import java.net.Socket;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class NimClientListener implements Runnable
{
	private Socket connectionSock = null;

	int playerTurn = 2;

	NimClientListener(Socket sock)
	{
		this.connectionSock = sock;
	}

	public void run()
	{
    // Wait for data from the server.  If received, output it.
		try
		{
			Nim game = new Nim();
			// Welcome Speech
			System.out.println("\nWelcome to Rock, Paper, Scissors, Lizard, Spock!\n");
			System.out.println("Incase you forgot how to spell your options, check out the title.");
			System.out.println("After both players have chosen their weapon we will tell you who won.");
			System.out.println("Thanks and have fun!\n");
			System.out.println("\nSelect Weapon: ");
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
} // ClientListener for MTClient
