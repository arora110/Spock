// Chinmai Raman and Akash Arora

import java.net.Socket;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class SpockClientHandler implements Runnable
{
	private Socket connectionSock = null;
	private ArrayList<Socket> socketList;
	private int player;
	private Spock game;
	SpockClientHandler(Socket sock, ArrayList<Socket> socketList, Spock game)
	{
		this.game = game;
		this.connectionSock = sock;
		this.socketList = socketList;	// Keep reference to master list
		player = socketList.size(); // let server know what player is sending inputs
	}

	public void run()
	{
                // Get data from a client and send it to everyone else
		try
		{
			System.out.println("Connection made with socket " + connectionSock);
			BufferedReader clientInput = new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));
			while (true)
			{
				// Get data sent from a client
				String clientText = clientInput.readLine();
				if (clientText != null)
				{
					System.out.println("Received: " + clientText);
					// Turn around and output this data
					// to all clients
					for (Socket s : socketList)
					{
						DataOutputStream clientOutput = new DataOutputStream(s.getOutputStream());
						clientOutput.writeBytes(clientText + " player: " + player + "\n");
					}
				}
				else
				{
				  // Connection was lost
				  System.out.println("Closing connection for socket " + connectionSock);
				  // Remove from arraylist
				  socketList.remove(connectionSock);
				  connectionSock.close();
				  break;
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("Error: " + e.toString());
			// Remove from arraylist
			socketList.remove(connectionSock);
		}
	}
} 
