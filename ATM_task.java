import java.util.*;
class Account{
    private String Id;
    private String Pin;
    private double balance;
    private ArrayList<String> transhistory;

    public Account(String Id,String Pin,double balance){
        this.Id=Id;
        this.Pin=Pin;
        this.balance=balance;
        this.transhistory=new ArrayList<>();
    }

    public String getId(){
        return Id;
    }

    public boolean authenticate(String Pin){
        return this.Pin.equals(Pin);
    }

    public void showTransHistory(){
        if (transhistory.isEmpty()){
            System.out.println("No trans yet.");
        } else{
            for (String trans:transhistory){
                System.out.println(trans);
            }
        }
    }

    public void withdraw(double amount){
        if (amount > balance){
            System.out.println("Insufficient balance for the withdraw.");
        } else{
            balance-=amount;
            transhistory.add("Withdraw:Rs"+amount);
            System.out.println("Withdraw successful ! New balance:Rs"+balance);
        }
    }

    public void deposit(double amount){
        balance+=amount;
        transhistory.add("Deposit:Rs"+amount);
        System.out.println("Deposit successful.New balance:Rs"+balance);
    }

    public void transfer(Account recipient,double amount){
        if (amount>balance){
            System.out.println("Insufficient balance!");
        } else{
            balance-=amount;
            recipient.balance+=amount;
            transhistory.add("Transfer:Rs"+amount+" to "+recipient.Id);
            recipient.transhistory.add("Transfer:Rs"+amount+" from "+Id);
            System.out.println("Transfer successful.New balance:Rs"+balance);
        }
    }
}
public class MainATMInterface{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        ArrayList<Account> accounts=new ArrayList<>();

        System.out.println("Enter the number of accounts to create:");
        int numberOfAccounts=sc.nextInt();
        sc.nextLine();

        for (int i=0; i < numberOfAccounts; i++) {
            System.out.println("Create Account " + (i + 1) + ":");
            System.out.println("Enter User ID:");
            String userId=sc.nextLine();
            System.out.println("Enter PIN:");
            String userPin=sc.nextLine();
            System.out.println("Enter initial balance:");
            double initialBalance=sc.nextDouble();
            sc.nextLine(); 

            accounts.add(new Account(userId, userPin, initialBalance));
        }
       System.out.println("Enter User ID to login:");
        String loginId=sc.nextLine();
        System.out.println("Enter PIN to login:");
        String loginPin=sc.nextLine();

        Account currentAccount=null;
        for (Account account:accounts) {
            if (account.getId().equals(loginId) && account.authenticate(loginPin)) {
                currentAccount=account;
                break;
            }
        }

        if (currentAccount == null) {
            System.out.println("Authentication failed.Sorry!");
            return;
        }

        while (true) {
            System.out.println("ATM Menu:");
            System.out.println("1.trans History");
            System.out.println("2.Withdraw");
            System.out.println("3.Deposit");
            System.out.println("4.Transfer");
            System.out.println("5.Quit");

            int ch=sc.nextInt();
            switch (ch) {
                case 1:
                    currentAccount.showTransHistory();
                    break;
                case 2:
                    System.out.println("Enter amount to withdraw:");
                    double withdrawAmount=sc.nextDouble();
                    currentAccount.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.println("Enter amount to deposit:");
                    double dep_amt=sc.nextDouble();
                    currentAccount.deposit(dep_amt);
                    break;
                case 4:
                    System.out.println("Enter recipient User ID:");
                    sc.nextLine(); 
                    String recipientId=sc.nextLine();
                    System.out.println("Enter amount to transfer:");
                    double transferAmount=sc.nextDouble();
                    sc.nextLine();
                    Account recipientAccount=null;
                    for (Account account:accounts) {
                        if (account.getId().equals(recipientId)) {
                            recipientAccount=account;
                            break;
                        }
                    }
                    if (recipientAccount != null) {
                        currentAccount.transfer(recipientAccount, transferAmount);
                    } else {
                        System.out.println("Recipient not found.");
                    }
                    break;
                case 5:
                    System.out.println("Thank you for using our ATM System.Goodbye!");
                    return;
                default:
                    System.out.println("Invalid ch value. Please try again.");
            }
        }
    }
}
