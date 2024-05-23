import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

class ATM {
    String[] accountHolders;
    int[] accountNumbers;
    float[] accountBalances;
    int PIN = 11;
    //Scanner scanner = new Scanner(System.in);

    public void checkPin(Scanner scanner) {
        System.out.println("Enter Admin PIN: ");
        int enteredPin = scanner.nextInt();
        if (enteredPin == PIN) {
            menu(scanner);
        } else {
            System.out.println("Invalid PIN");
        }
    }

    public void menu(Scanner scanner) {
        System.out.println("Enter Your Choice");
        System.out.println("1. Add Account");
        System.out.println("2. View Account");
        System.out.println("3. Check Balance");
        System.out.println("4. Deposit Money");
        System.out.println("5. Withdraw Money");
        System.out.println("6. Exit");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                addAccount(scanner);
                break;
            case 2:
                viewAccount(scanner);
                break;
            case 3:
                checkBalance(scanner);
                break;
            case 4:
                depositMoney(scanner);
                break;
            case 5:
                withdrawMoney(scanner);
                break;
            case 6:
                return;
            default:
                System.out.println("Enter a valid choice");
                menu(scanner);
        }
    }

    public void checkBalance(Scanner scanner) {
        System.out.println("Enter Account Number: ");
        int accountNumber = scanner.nextInt();
        int index = findAccountIndex(accountNumber);
        if (index != -1) {
            System.out.println("Balance for account holder " + accountHolders[index] + ": " + accountBalances[index]);
        } else {
            System.out.println("Invalid Account Number");
        }
        menu(scanner);
    }

    public void withdrawMoney(Scanner scanner) {
        System.out.println("Enter Account Number: ");
        int accountNumber = scanner.nextInt();
        int index = findAccountIndex(accountNumber);
        if (index != -1) {
            System.out.println("Enter amount to withdraw: ");
            float amount = scanner.nextFloat();
            if (amount > accountBalances[index]) {
                System.out.println("Insufficient Balance");
            } else {
                accountBalances[index] -= amount;
                System.out.println("Money withdraw successful");
            }
        } else {
            System.out.println("Invalid Account Number");
        }
        menu(scanner);
    }

    public void depositMoney(Scanner scanner) {
        System.out.println("Enter Account Number: ");
        int accountNumber = scanner.nextInt();
        int index = findAccountIndex(accountNumber);
        if (index != -1) {
            System.out.println("Enter the amount: ");
            float amount = scanner.nextFloat();
            accountBalances[index] += amount;
            System.out.println("Money deposit successful");
        } else {
            System.out.println("Invalid Account Number");
        }
        menu(scanner);
    }

    public void viewAccount(Scanner scanner) {
        System.out.println("Enter Account Number: ");
        int accountNumber = scanner.nextInt();
        int index = findAccountIndex(accountNumber);
        if (index != -1) {
            System.out.println("Account Holder Name: " + accountHolders[index]);
            System.out.println("Account Balance: " + accountBalances[index]);
        } else {
            System.out.println("Invalid Account Number");
        }
        menu(scanner);
    }

    public void addAccount(Scanner scanner) {
        System.out.println("Enter new account number: ");
        int newAccountNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
        System.out.println("Enter account holder name: ");
        String newAccountHolder = scanner.nextLine();

        float initialBalance = 0;
        boolean validBalance = false;
        while (!validBalance) {
            System.out.println("Enter initial balance (minimum 500 TK): ");
            initialBalance = scanner.nextFloat();
            if (initialBalance >= 500) {
                validBalance = true;
            } else {
                System.out.println("Minimum initial balance required is 500 TK. Please enter again.");
            }
        }


        int newSize = accountNumbers != null ? accountNumbers.length + 1 : 1;
        String[] newAccountHolders = new String[newSize];
        int[] newAccountNumbers = new int[newSize];
        float[] newAccountBalances = new float[newSize];


        if (accountNumbers != null) {
            for (int i = 0; i < accountNumbers.length; i++) {
                newAccountHolders[i] = accountHolders[i];
                newAccountNumbers[i] = accountNumbers[i];
                newAccountBalances[i] = accountBalances[i];
            }
        }


        newAccountHolders[newSize - 1] = newAccountHolder;
        newAccountNumbers[newSize - 1] = newAccountNumber;
        newAccountBalances[newSize - 1] = initialBalance;


        accountHolders = newAccountHolders;
        accountNumbers = newAccountNumbers;
        accountBalances = newAccountBalances;

        System.out.println("Account added successfully!");
        menu(scanner);
    }

    private int findAccountIndex(int accountNumber) {
        if (accountNumbers != null) {
            for (int i = 0; i < accountNumbers.length; i++) {
                if (accountNumbers[i] == accountNumber) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void closeScanner(Scanner scanner) {
        scanner.close();
    }
}

public class Main {
    public static void main(String[] args) {

        try {


            File inputFile = new File("input.txt");
            Scanner scanner = new Scanner(inputFile);
            File outputFile = new File("output.txt");
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            PrintStream printStream = new PrintStream(fileOutputStream);
            System.setOut(printStream);

            ATM obj = new ATM();
            obj.checkPin(scanner);
            obj.closeScanner(scanner);
        }
        catch (Exception e) {
            System.out.println("Error reading input file\n" + e.getMessage());
            System.exit(1);
        }
    }

}

