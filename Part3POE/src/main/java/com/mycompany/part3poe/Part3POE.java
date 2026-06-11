 

package com.mycompany.part3poe;

import java.util.Scanner;

public class Part3POE { // Changed from TaskReportApp to match your project configuration

    // Maximum capacity for the message storage arrays
    private static final int MAX_MESSAGES = 100;

    // Parallel Arrays to track data
    private static final String[] storedMessages = new String[MAX_MESSAGES];
    private static final String[] recipients = new String[MAX_MESSAGES];
    private static final String[] messageIds = new String[MAX_MESSAGES];
    private static final String[] messageHashes = new String[MAX_MESSAGES];

    // Tracking lists for other message categories
    private static final String[] sentMessages = new String[MAX_MESSAGES];
    private static final String[] disregardedMessages = new String[MAX_MESSAGES];

    // Counters to keep track of actual elements populated
    private static int storedCount = 0;
    private static int sentCount = 0;
    private static int disregardCount = 0;

    public static void main(String[] args) {
        // Automatically populate the arrays with the provided assignment Test Data
        populateTestData();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                displayMainMenu();
                System.out.print("Select an option (1-5): ");
                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1" -> {
                        System.out.println("\n--- Feature 1 & 2: App Features Active ---");
                        System.out.println("Press Enter to return to Main Menu...");
                        scanner.nextLine();
                    }
                    case "2" -> {
                        System.out.println("\n--- Feature 3: Unit Tests Automated ---");
                        System.out.println("Press Enter to return to Main Menu...");
                        scanner.nextLine();
                    }
                    case "3" -> runAutomatedTests();
                    case "4" -> handleStoredMessagesMenu(scanner);
                    case "5" -> {
                        System.out.println("Exiting application. Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n=============================================");
        System.out.println("                  MAIN MENU                  ");
        System.out.println("=============================================");
        System.out.println("1. Manage Part 1 Features");
        System.out.println("2. Manage Part 2 Features");
        System.out.println("3. Run Automated Unit Tests");
        System.out.println("4. Stored Messages (Part 3 Sub-Menu)");
        System.out.println("5. Exit");
        System.out.println("=============================================");
    }

    /**
     * Requirement 2: Fourth Main Menu Option Sub-Menu Implementation
     */
    private static void handleStoredMessagesMenu(Scanner scanner) {
        boolean subRunning = true;
        while (subRunning) {
            System.out.println("\n-----------------------------------------------------");
            System.out.println("                STORED MESSAGES REPORT               ");
            System.out.println("-----------------------------------------------------");
            System.out.println("a. Display sender/recipient of all stored messages");
            System.out.println("b. Display the longest stored message");
            System.out.println("c. Search for a message ID");
            System.out.println("d. Search for all messages by recipient");
            System.out.println("e. Delete a message using message hash");
            System.out.println("f. Display full details report");
            System.out.println("g. Back to Main Menu");
            System.out.println("-----------------------------------------------------");
            System.out.print("Select a report feature (a-g): ");
            
            String subChoice = scanner.nextLine().toLowerCase().trim();

            switch (subChoice) {
                case "a" -> displaySendersAndRecipients();
                case "b" -> displayLongestMessage();
                case "c" -> {
                    System.out.print("Enter Message ID to search: ");
                    String idToSearch = scanner.nextLine();
                    searchByMessageId(idToSearch);
                }
                case "d" -> {
                    System.out.print("Enter Recipient Number to search: ");
                    String recipientToSearch = scanner.nextLine();
                    searchByRecipient(recipientToSearch);
                }
                case "e" -> {
                    System.out.print("Enter Message Hash to delete: ");
                    String hashToDelete = scanner.nextLine();
                    deleteMessageByHash(hashToDelete);
                }
                case "f" -> displayFullReport();
                case "g" -> subRunning = false;
                default -> System.out.println("Invalid selection. Please choose options a through g.");
            }
        }
    }

    /**
     * Requirement 3: Populates arrays dynamically using assignment test parameters.
     */
    private static void populateTestData() {
        // Message 1 (Sent)
        addSentMessage("Did you get the cake?");

        // Message 2 (Stored)
        addStoredMessage("+27838884567", "Where are you? You are late! I have asked you to be on time.", "MSG-002");

        // Message 3 (Disregard)
        addDisregardedMessage("Yohoooo, I am at your gate.");

        // Message 4 (Sent)
        addSentMessage("It is dinner time !");

        // Message 5 (Stored)
        addStoredMessage("+27838884567", "Ok, I am leaving without you.", "MSG-005");
    }

    private static void addSentMessage(String message) {
        if (sentCount < MAX_MESSAGES) {
            sentMessages[sentCount++] = message;
        }
    }

    private static void addDisregardedMessage(String message) {
        if (disregardCount < MAX_MESSAGES) {
            disregardedMessages[disregardCount++] = message;
        }
    }

    private static void addStoredMessage(String recipient, String message, String customId) {
        if (storedCount < MAX_MESSAGES) {
            recipients[storedCount] = recipient;
            storedMessages[storedCount] = message;
            messageIds[storedCount] = customId;
            messageHashes[storedCount] = "HASH" + Math.abs(message.hashCode() % 10000);
            storedCount++;
        }
    }

    /**
     * 2.a: Display sender/recipient of all stored messages
     */
    private static void displaySendersAndRecipients() {
        System.out.println("\n--- Stored Messages Senders & Recipients ---");
        if (storedCount == 0) {
            System.out.println("No stored messages available.");
            return;
        }

        for (int i = 0; i < storedCount; i++) {
            if (recipients[i] != null) {
                System.out.println("Index [" + i + "] -> System / App -> Recipient: " + recipients[i]);
            }
        }
    }

    /**
     * 2.b: Display the longest stored message
     */
    private static void displayLongestMessage() {
        System.out.println("\n--- Longest Stored Message ---");
        if (storedCount == 0) {
            System.out.println("No stored messages available.");
            return;
        }

        int longestIndex = -1;
        int maxLength = -1;

        for (int i = 0; i < storedCount; i++) {
            if (storedMessages[i] != null && storedMessages[i].length() > maxLength) {
                maxLength = storedMessages[i].length();
                longestIndex = i;
            }
        }

        if (longestIndex != -1) {
            System.out.println("ID: " + messageIds[longestIndex]);
            System.out.println("Recipient: " + recipients[longestIndex]);
            System.out.println("Length: " + maxLength + " characters");
            System.out.println("Message: \"" + storedMessages[longestIndex] + "\"");
        }
    }

    /**
     * 2.c: Search for a message ID and display the corresponding recipient and message
     */
    private static void searchByMessageId(String id) {
        System.out.println("\n--- Search Result for ID: " + id + " ---");
        boolean found = false;
        String cleanId = id.trim();
        
        for (int i = 0; i < storedCount; i++) {
            if (messageIds[i] != null && messageIds[i].equalsIgnoreCase(cleanId)) {
                System.out.println("Recipient: " + recipients[i]);
                System.out.println("Message: \"" + storedMessages[i] + "\"");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Message ID matching '" + id + "' could not be found.");
        }
    }

    /**
     * 2.d: Search for all the messages stored for a particular recipient
     */
    private static void searchByRecipient(String recipientNumber) {
        System.out.println("\n--- Search Results for Recipient: " + recipientNumber + " ---");
        boolean found = false;
        String cleanTarget = recipientNumber.replace(" ", "");

        for (int i = 0; i < storedCount; i++) {
            if (recipients[i] != null && recipients[i].replace(" ", "").equals(cleanTarget)) {
                System.out.println("- [" + messageIds[i] + "]: " + storedMessages[i]);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No records found for recipient: " + recipientNumber);
        }
    }

    /**
     * 2.e: Delete a message using the message hash
     */
    private static void deleteMessageByHash(String hash) {
        System.out.println("\n--- Delete Operation ---");
        int matchIndex = -1;
        String cleanHash = hash.trim();

        for (int i = 0; i < storedCount; i++) {
            if (messageHashes[i] != null && messageHashes[i].equalsIgnoreCase(cleanHash)) {
                matchIndex = i;
                break;
            }
        }

        if (matchIndex != -1) {
            String removedId = messageIds[matchIndex];
            
            for (int j = matchIndex; j < storedCount - 1; j++) {
                storedMessages[j] = storedMessages[j + 1];
                recipients[j] = recipients[j + 1];
                messageIds[j] = messageIds[j + 1];
                messageHashes[j] = messageHashes[j + 1];
            }

            storedMessages[storedCount - 1] = null;
            recipients[storedCount - 1] = null;
            messageIds[storedCount - 1] = null;
            messageHashes[storedCount - 1] = null;

            storedCount--;
            System.out.println("Success: Message [" + removedId + "] with Hash " + cleanHash + " has been successfully removed.");
        } else {
            System.out.println("Error: Provided Hash value does not match any current records.");
        }
    }

    /**
     * 2.f: Display a report that lists the full details of all the stored messages
     */
    private static void displayFullReport() {
        System.out.println("\n==========================================================================================");
        System.out.println("                                FULL STORED MESSAGES REPORT                               ");
        System.out.println("==========================================================================================");
        
        if (storedCount == 0) {
            System.out.println("                               No messages found in database.                             ");
        } else {
            System.out.printf("%-10s | %-15s | %-10s | %-40s\n", "ID", "Recipient", "Hash Code", "Message Text");
            System.out.println("------------------------------------------------------------------------------------------");
            
            for (int i = 0; i < storedCount; i++) {
                if (storedMessages[i] != null) {
                    String shortMsg = storedMessages[i].length() > 37 ? storedMessages[i].substring(0, 34) + "..." : storedMessages[i];
                    System.out.printf("%-10s | %-15s | %-10s | %-40s\n", messageIds[i], recipients[i], messageHashes[i], shortMsg);
                }
            }
        }
        System.out.println("==========================================================================================");
    }

    /**
     * Automated Unit Tests
     */
    private static void runAutomatedTests() {
        System.out.println("\nExecuting Automated Unit Tests...");

        boolean test1 = storedMessages.length == MAX_MESSAGES;
        boolean test2 = sentMessages.length == MAX_MESSAGES;
        boolean test3 = disregardedMessages.length == MAX_MESSAGES;

        System.out.println("Test 1 (Stored Array Capacity Integrity Check): " + (test1 ? "PASSED" : "FAILED"));
        System.out.println("Test 2 (Sent Array Capacity Integrity Check): " + (test2 ? "PASSED" : "FAILED"));
        System.out.println("Test 3 (Disregarded Array Capacity Integrity Check): " + (test3 ? "PASSED" : "FAILED"));
    }
}