package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.model.Quote;
import ca.jrvs.apps.stockquote.service.PositionService;
import ca.jrvs.apps.stockquote.service.QuoteService;
import ca.jrvs.apps.util.DatabaseConnectionManager;
import ca.jrvs.apps.util.LoggerUtil;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class StockQuoteController {

    private static final Logger logger = LoggerUtil.getLogger();
    private PositionService positionService;
    private QuoteService quoteService;
    private Connection dbConnection;
    private Scanner scanner;

    public StockQuoteController() {
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        StockQuoteController controller = new StockQuoteController();
        controller.startClient();
    }

    /**
     * Main program loop
     */
    public void startClient() {
        if (this.establishDatabaseConnection()) {
            logger.info("Database connection established successfully.");
            System.out.println("Database connected.");
        } else {
            return;
        }
        System.out.println("Welcome to the Stock Quote Application!");
        positionService = new PositionService(this.dbConnection);
        quoteService = new QuoteService(this.dbConnection);
        displayHelp();

        boolean exit = false;
        while (!exit) {
            System.out.print("> ");
            String[] commandArgs = scanner.nextLine().trim().split(" ");
            if (commandArgs.length == 0) {
                System.out.println("No input detected, please try again.");
                continue;
            }
            String command = commandArgs[0].toLowerCase();
            switch (command) {
                case "buy":
                    executeBuy(commandArgs);
                    break;
                case "sell":
                    executeSell(commandArgs);
                    break;
                case "quote":
                    displayQuote(commandArgs);
                    break;
                case "list":
                    listPositions(commandArgs);
                    break;
                case "check":
                    checkPosition(commandArgs);
                    break;
                case "quit":
                    exit = true;
                    System.out.println("Shutting down.");
                    break;
                case "help":
                    if (commandArgs.length != 1) break;
                    displayHelp();
                    break;
                default:
                    System.out.println("ERROR: Unknown command. Type 'help' for available commands.");
            }
        }
        scanner.close();
    }

    public void executeBuy(String[] args) {
        if (!isValidArgCount(args, 3)) return;
        int quantity;
        String tickerSymbol = args[1].toUpperCase();
        try {
            quantity = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid quantity. It must be an integer.");
            return;
        }
        Optional<Quote> quoteOptional = quoteService.fetchAndStoreQuote(tickerSymbol);
        if (quoteOptional.isEmpty()) return;
        Quote quote = quoteOptional.get();
        positionService.buy(tickerSymbol, quantity, quote.getPrice());
    }

    public void executeSell(String[] args) {
        if (!isValidArgCount(args, 2)) return;
        String tickerSymbol = args[1].toUpperCase();
        positionService.sell(tickerSymbol);
    }

    public void displayQuote(String[] args) {
        if (!isValidArgCount(args, 2)) return;
        String tickerSymbol = args[1].toUpperCase();
        quoteService.fetchLatestQuote(tickerSymbol, true);
    }

    public void listPositions(String[] args) {
        if (!isValidArgCount(args, 1)) return;
        positionService.list();
    }

    public void checkPosition(String[] args) {
        if (!isValidArgCount(args, 2)) return;
        String tickerSymbol = args[1].toUpperCase();
        positionService.check(tickerSymbol);
    }

    /**
     * Establishes a connection to the database
     * @return true if connected successfully
     */
    public boolean establishDatabaseConnection() {
        try {
            this.dbConnection = new DatabaseConnectionManager().getConnection();
            return true;
        } catch (SQLException e) {
            System.out.println("ERROR: Failed to connect to the database. Please enter credentials manually or type 'quit' to exit.");
        }
        // If the initial connection fails, request user input for connection details
        while (this.dbConnection == null) {
            System.out.println("Enter: host database username password | quit");
            System.out.print("> ");
            String[] userInput = scanner.nextLine().trim().split(" ");
            if (userInput.length == 0) {
                System.out.println("No input detected, please try again.");
                continue;
            } else if (userInput.length == 1 && userInput[0].toLowerCase().equals("quit")) {
                System.out.println("Shutting down.");
                return false;
            }
            try {
                this.dbConnection = new DatabaseConnectionManager(userInput[0], userInput[1], userInput[2], userInput[3]).getConnection();
                return true;
            } catch (SQLException e) {
                System.out.println("ERROR: Failed to connect to the database with provided details.");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("ERROR: Insufficient arguments provided. Please try again.");
            }
        }
        return false;
    }

    /**
     * Displays available commands to the user
     */
    private void displayHelp() {
        StringBuilder helpMessage = new StringBuilder();
        helpMessage.append("Commands:\n")
                .append("1. buy symbol amount : Buy shares of a stock\n")
                .append("2. sell symbol : Sell all shares of a stock position\n")
                .append("3. quote symbol : View the latest quote for a stock symbol\n")
                .append("4. list : List all current positions\n")
                .append("5. check symbol : View current position and its gain/loss\n")
                .append("6. quit : Exit the application\n");
        System.out.println(helpMessage);
    }

    /**
     * Validates the number of arguments provided for each command
     */
    private boolean isValidArgCount(String[] args, int expectedCount) {
        if (args.length == expectedCount) {
            return true;
        }
        System.out.println("ERROR: Invalid number of arguments. Type 'help' for a list of commands.");
        return false;
    }
}
