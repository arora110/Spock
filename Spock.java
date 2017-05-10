// Chinmai Raman and Akash Arora

import java.util.*;

public class Spock
{

  Spock() {
	reset();
  }

  //Records which weapons have/haven't been defeated
  public int isAlive[] = new int[5];
  //Records who has selected a weapon 
  public int isLocked[] = new int[5];
  //Stores weapon 
  public String player1 = "";
  public String player2 = "";
  public String player3 = "";
  public String player4 = "";
  public String player5 = "";
  //For Display
  public String playerChoices = "";
  public String winners = "";
  //Game state
  public boolean isOver = false;
  //Determines if user input is a valid weapon choice, and limits game to five players
  public boolean isValidInputTwo(int playerNumber, String input) {
        input = input.toLowerCase();
        if ((input.equals("rock") || input.equals("paper") || input.equals("scissors") || input.equals("lizard") || input.equals("spock")) && playerNumber <= 5) {
                 assign(playerNumber, input);
		 kill(input);
		 playerNumber = playerNumber - 1;
		 isLocked[playerNumber] = 1;
		 return true;
        } else {
                return false;
        }
  }

  //Resets all variables for new game
  public void reset() {
	isAlive[0] = 0;
	isAlive[1] = 0;
	isAlive[2] = 0;
	isAlive[3] = 0;
	isAlive[4] = 0;
        isLocked[0] = 0;
	isLocked[1] = 0;
	isLocked[2] = 0;
	isLocked[3] = 0;
	isLocked[4] = 0;
	player1 = "";
	player2 = "";
	player3 = "";
	player4 = "";
	player5 = "";
	winners = "";
	playerChoices = "";
  }
  
  //Prints which players have/haven't locked in (For Testing Purposes) 
  public String printLocked() {
	String result = "";
	for (int i = 0; i < isLocked.length; i++) {
		result += isLocked[i];
	}
	return result;
  }
  
  //Stores player's weapons
  public void assign(int player, String input) {
	if(player == 1) {
		player1 = input;
	} else if (player == 2) {
		player2 = input;
	} else if (player == 3) {
		player3 = input;
	} else if (player == 4) {
		player4 = input;
	} else if (player == 5) {
		player5 = input;
	}	
  }
	
  //Determines and Records which weapons die 
  public void kill(String input) {
	input = input.toLowerCase();
	if (input.equals("rock")) { //Rock crushes lizard - and as it always has, rock crushes scissors
		isAlive[3] = 1;
		isAlive[2] = 1;
	} else if (input.equals("paper")) { //Paper covers rock and disproves spocks
		isAlive[0] = 1;
                isAlive[4] = 1;
	} else if (input.equals("scissors")) { //Scissors cuts paper and decapitates lizard
		isAlive[1] = 1;
                isAlive[3] = 1;
	} else if (input.equals("lizard")) { //Lizard poisons spock and eats paper
		isAlive[4] = 1;
                isAlive[1] = 1;
	} else if (input.equals("spock")) { //Spock smashes scissors and vaporizes rock
		isAlive[2] = 1;
                isAlive[0] = 1;
	}
  }

  //Prints which player's chose which weapons
  public String displayChoices() {
	playerChoices = "WEAPON SELECTIONS: \n";
	if (!player1.equals("")) {
		playerChoices += "    Player 1 chose: " + player1 + "\n";
	}
	if (!player2.equals("")) {
		playerChoices += "    Player 2 chose: " + player2 + "\n";
	} 
	if (!player3.equals("")) {
                playerChoices += "    Player 3 chose: " + player3 + "\n";
        } 
        if (!player4.equals("")) {
                playerChoices += "    Player 4 chose: " + player4 + "\n";
        }
	if (!player5.equals("")) {
                playerChoices += "    Player 5 chose: " + player5 + "\n";
        } 
	return playerChoices;
  }
 
 //Prints which weapon won
 public String displayWinners() {
	winners = "";
	/* For Testing Purposes
	winners += "Rock: " + isAlive[0] + "\n";
	winners += "Paper: " + isAlive[1] + "\n";
	winners += "Scissors: " + isAlive[2] + "\n";
	winners += "Lizard: " + isAlive[3] + "\n";
	winners += "Spock: " + isAlive[4] + "\n\n";
	*/
	String finalChoices = displayChoices();
	if (isAlive[0] == 0 && finalChoices.contains("rock")) {
		winners += "ROCK WINS (crushes scissors, crushes lizard)";
	} else if(isAlive[1] == 0 && finalChoices.contains("paper")) {
        	winners += "PAPER WINS (covers rock, disproves spock)";
        } else if (isAlive[2] == 0 && finalChoices.contains("scissors")) {
        	winners += "SCISSORS WINS (cuts paper, decapitates lizard)";
        } else if (isAlive[3] == 0 && finalChoices.contains("lizard")) {
        	winners += "LIZARD WINS (poisons spock, eats paper)";
        } else if (isAlive[4] == 0 && finalChoices.contains("spock")) {
        	winners += "SPOCK WINS (smashes scissors, vaporizes rock)";
        } else {
		winners += "Everyone DIES";
	}
	return winners; 
  }
}
