import java.util.*;
public class BankingSystem {

    static Scanner sc = new Scanner(System.in);
    static HashMap<String, Customer> customers = new HashMap<>();
    static final String ADMIN_USERNAME = "Arpit";
    static final String ADMIN_PASSWORD = "Arpit123";
    public static void main(String[] args) {

        while (true) {
            System.out.println("\n==== BANKING SYSTEM ====");
            System.out.println("1. Register");
            System.out.println("2. Customer Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> register();
                case 2 -> customerLogin();
                case 3 -> adminLogin();
                case 4 -> {
                    System.out.println("Thank you!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void register() {
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        String accountNumber = generateAccountNumber();

        Customer customer = new Customer(name, accountNumber, password);
        customers.put(accountNumber, customer);

        System.out.println("Account Created Successfully!");
        System.out.println("Your Account Number: " + accountNumber);
    }

    static void customerLogin() {
        System.out.print("Enter Account Number: ");
        String accNo = sc.nextLine();

        System.out.print("Enter Password: ");
        String pass = sc.nextLine();

        Customer customer = customers.get(accNo);

        if (customer != null && customer.password.equals(pass)) {
            customerMenu(customer);
        } else {
            System.out.println("Invalid Credentials!");
        }
    }

    static void customerMenu(Customer customer) {
        while (true) {
            System.out.println("\n---- Customer Menu ----");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Transaction History");
            System.out.println("6. Logout");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> System.out.println("Balance: Rs." + customer.balance);
                case 2 -> deposit(customer);
                case 3 -> withdraw(customer);
                case 4 -> transfer(customer);
                case 5 -> customer.showTransactions();
                case 6 -> {
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void deposit(Customer customer) {
        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();

        customer.balance += amount;
        customer.addTransaction("Deposited Rs." + amount);

        System.out.println("Deposit Successful!");
    }

    static void withdraw(Customer customer) {
        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();

        if (customer.balance >= amount) {
            customer.balance -= amount;
            customer.addTransaction("Withdrew Rs." + amount);
            System.out.println("Withdrawal Successful!");
        } else {
            System.out.println("Insufficient Balance!");
        }
    }

    static void transfer(Customer sender) {
        sc.nextLine();
        System.out.print("Enter Receiver Account Number: ");
        String receiverAcc = sc.nextLine();

        Customer receiver = customers.get(receiverAcc);

        if (receiver == null) {
            System.out.println("Receiver not found!");
            return;
        }

        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();

        if (sender.balance >= amount) {
            sender.balance -= amount;
            receiver.balance += amount;

            sender.addTransaction("Transferred Rs." + amount + " to " + receiverAcc);
            receiver.addTransaction("Received Rs." + amount + " from " + sender.accountNumber);

            System.out.println("Transfer Successful!");
        } else {
            System.out.println("Insufficient Balance!");
        }
    }

    static void adminLogin() {
        System.out.print("Enter Admin Username: ");
        String user = sc.nextLine();

        System.out.print("Enter Admin Password: ");
        String pass = sc.nextLine();

        if (user.equals(ADMIN_USERNAME) && pass.equals(ADMIN_PASSWORD)) {
            adminMenu();
        } else {
            System.out.println("Invalid Admin Credentials!");
        }
    }

    static void adminMenu() {
        while (true) {
            System.out.println("\n---- Admin Menu ----");
            System.out.println("1. View All Customers");
            System.out.println("2. Remove Customer");
            System.out.println("3. View Total Bank Balance");
            System.out.println("4. View Total Number of Accounts");
            System.out.println("5. View Customer Transaction History");
            System.out.println("6. Logout");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> viewAllCustomers();
                case 2 -> removeCustomer();
                case 3 -> viewTotalBalance();
                case 4 -> System.out.println("Total Accounts: " + customers.size());
                case 5 -> viewCustomerTransactions();
                case 6 -> {
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void viewAllCustomers() {
        for (Customer c : customers.values()) {
            System.out.println("Name: " + c.name +
                    " | Acc No: " + c.accountNumber +
                    " | Balance: Rs." + c.balance);
        }
    }

    static void removeCustomer() {
        System.out.print("Enter Account Number to Remove: ");
        String acc = sc.nextLine();

        if (customers.remove(acc) != null) {
            System.out.println("Customer Removed Successfully!");
        } else {
            System.out.println("Customer Not Found!");
        }
    }

    static void viewTotalBalance() {
        double total = 0;
        for (Customer c : customers.values()) {
            total += c.balance;
        }
        System.out.println("Total Bank Balance: Rs." + total);
    }

    static void viewCustomerTransactions() {
        System.out.print("Enter Account Number: ");
        String acc = sc.nextLine();

        Customer customer = customers.get(acc);

        if (customer != null) {
            customer.showTransactions();
        } else {
            System.out.println("Customer Not Found!");
        }
    }
    
    static String generateAccountNumber() {
        return "ACC" + (1000 + customers.size());
    }
}

class Customer {
    String name;
    String accountNumber;
    String password;
    double balance;
    ArrayList<String> transactions;

    Customer(String name, String accountNumber, String password) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.password = password;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    void addTransaction(String message) {
        transactions.add(message);
    }

    void showTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No Transactions Yet.");
            return;
        }

        System.out.println("---- Transaction History ----");
        for (String t : transactions) {
            System.out.println(t);
        }
    }
}