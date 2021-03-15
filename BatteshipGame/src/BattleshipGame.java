import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class BattleshipGame {
    public static void main(String[] args) {
        // Declare Variables to make it easier to call
        // Set up Board size 10 by 10
        int gameBoardLength = 10;
        // Set up water function display lines within game board
        char water = '-';
        // Set up ship
        char ship = 's';
        // When a ship it is hit X will be displayed 
        char hit = 'X';
        // When ship is missed O will be displayed
        char miss = 'O';
        // Number of ships to be added
        int shipNumber = 5;

        // Create the Game Board making sure to include what needs to be in game
        char[][] gameBoard = createGameBoard(gameBoardLength, water, ship, shipNumber);
        printGameBoard(gameBoard, water, ship); //Displaying the game board
        int undetectedShipNumber = shipNumber;
        while (undetectedShipNumber > 0) {
            int[] guessCoordinates = getUserCoordinates(gameBoardLength);
            char locationViewUpdate = evaluateGuessAndGetTheTarget(guessCoordinates, gameBoard, ship, water, hit, miss);
            if (locationViewUpdate == hit) {
                undetectedShipNumber--;
            }
            gameBoard = updateGameBoard(gameBoard, guessCoordinates, locationViewUpdate);
            printGameBoard(gameBoard, water, ship);
        }
        System.out.print("You Won!");
    }

    // Updating game board by placing ships at ramdom
    private static char[][] updateGameBoard(char[][] gameBoard, int[] guessCoordinates, char locationViewUpdate) {
        int row = guessCoordinates[0];
        int col = guessCoordinates[1];
        gameBoard[row][col] = locationViewUpdate;
        return gameBoard;
    }

    // making sure to update if when the ship has been attacked or not printing miss or hit also updating the game board
    private static char evaluateGuessAndGetTheTarget(int[] guessCoordinates, char[][] gameBoard, char ship, char water,
        char hit, char miss) {
        String message;
        int row = guessCoordinates[0];
        int col = guessCoordinates[1];
        char target = gameBoard[row][col];
        if (target == ship) {
            message = "Hit!";
            target = hit;
        } else if (target == water) {
            message = "Miss!";
            target = miss;
        } else {
            message = "Already hit!";
        }
        System.out.println(message);
        return target;
    }

    // User to enter where they want to attack
    private static int[] getUserCoordinates(int gameBoardLength) {
        int row;
        int col;
        do {
            System.out.print("Row: ");
            row = new Scanner(System.in).nextInt();
        } while (row < 1 || row > gameBoardLength + 1);
        do {
            System.out.print("Column: ");
            col = new Scanner(System.in).nextInt();
        } while (col < 1 || col > gameBoardLength + 1);
        return new int[] {
            row - 1, col - 1
        };
    }

    // Printing the Game Board function
    private static void printGameBoard(char[][] gameBoard, char water, char ship) {
        int gameBoardLength = gameBoard.length;
        System.out.print("  ");
        for (int i = 0; i < gameBoardLength; i++) {
            System.out.print(i + 1 + " ");
        }
        System.out.println();
        for (int row = 0; row < gameBoardLength; row++) {
            System.out.print(row + 1 + " ");
            for (int col = 0; col < gameBoardLength; col++) {
                char position = gameBoard[row][col];
                if (position == ship) {
                    System.out.print(water + " ");
                } else {
                    System.out.print(position + " ");
                }
            }
            System.out.println();
        }
        System.out.println();

    }

    // Creating the Game Board including what it needs
    private static char[][] createGameBoard(int gameBoardLength, char water, char ship, int shipNumber) {
        char[][] gameBoard = new char[gameBoardLength][gameBoardLength];
        for (char[] row: gameBoard) {
            Arrays.fill(row, water);
        }

        return placeShips(gameBoard, shipNumber, water, ship);
    }

    // Computer ramdom placing ships
    private static char[][] placeShips(char[][] gameBoard, int shipNumber, char water, char ship) {
        int placedShips = 0;
        int gameBoardLength = gameBoard.length;
        while (placedShips < shipNumber) {
            int[] location = generateShipCoordinates(gameBoardLength);
            char possiblePlacement = gameBoard[location[0]][location[1]];
            if (possiblePlacement == water) {
                gameBoard[location[0]][location[1]] = ship;
                placedShips++;
            }
        }
        return gameBoard;
    }

    // Creating the coordinates I.E. Column starts at 1 Row Starts at 1
    private static int[] generateShipCoordinates(int gameBoardLength) {
        int[] coordinates = new int[2];
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = new Random().nextInt(gameBoardLength);
        }
        return coordinates;
    }

}