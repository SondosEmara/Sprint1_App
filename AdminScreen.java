package main;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminScreen implements Iscreen {

    public void showDriverPendding() throws Exception {

        boolean check = false;
        ArrayList<Account> accounts = SystemManager.UserManager.getSystemAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            if (account instanceof Driver && "pandding".equals(account.getStatus())) {
                Driver driver = (Driver) account;
                System.out.println(
                        i + "-" + "DriverId: " + driver.getAccountId() + " , "
                        + "UserName: " + driver.getUserName() + " , "
                        + "LisenceId: " + driver.getDriverLicense() + " , "
                        + "NationalId: " + driver.getNationalId() + " , ");
                check = true;
            }

        }
        if (!check) {
            throw new Exception("NOt exists Any Account");
        }
    }

    public void showDriverAccounts() throws Exception {

        boolean check = false;
        ArrayList<Account> accounts = SystemManager.UserManager.getSystemAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            if (account instanceof Driver && !"pandding".equals(account.getStatus()) && !"susspend".equals(account.getStatus())) {
                Driver driver = (Driver) account;
                System.out.println(
                        i + "-" + "DriverId: " + driver.getAccountId() + " , "
                        + "UserName: " + driver.getUserName() + " , "
                        + "LisenceId: " + driver.getDriverLicense() + " , "
                        + "NationalId: " + driver.getNationalId() + " , ");
                check = true;
            }
        }
        if (!check) {
            throw new Exception("NOt exists Any Account");
        }
    }

    public void showPassengerAccounts() throws Exception {
        boolean check = false;
        ArrayList<Account> accounts = SystemManager.UserManager.getSystemAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            if (account instanceof Passenger && !"susspend".equals(account.getStatus())) {
                Passenger passenger = (Passenger) account;
                System.out.println(
                        i + "-" + "UserId: " + passenger.getAccountId() + " , "
                        + "UserName: " + passenger.getUserName() + " , "
                        + "Emial: " + passenger.getEmail() + " , ");
                check = true;
            }
        }
        if (!check) {
            throw new Exception("NOt exists Any Account");
        }

    }

    public void commonFunction(String newState, int accountId) {

        String accountIds;
        String[] accIds;
        Scanner input = new Scanner(System.in);
        System.out.println("if you are select driver if that (yes) or (no)");
        String opinion = input.next();
        if ("yes".equalsIgnoreCase(opinion)) {
            System.out.println("Enter the driver id accepeted Info by the following syntax 'accountId1,accountId2'");
            accountIds = input.next();

            accIds = accountIds.split(",");
            for (String accId : accIds) {
                try {
                    Account targetAccount = SystemManager.UserManager.search(Integer.parseInt(accId));
                    if ("Accepted".equals(newState)) {
                        targetAccount.setStatus("Accepted");
                        Driver driver = (Driver) targetAccount;
                        driver.setAdmin((Admin) SystemManager.UserManager.search(accountId));
                    } else if ("Susspend".equals(newState)) {
                        targetAccount.setStatus("Susspend");
                    }
                    SystemManager.UserManager.updateAccount(targetAccount, Integer.parseInt(accId));
                } catch (Exception error) {

                    System.out.println(error.getMessage());
                }
            }

        }
    }

    @Override
    public void displayFeatures(int accountId
    ) {

        Scanner input = new Scanner(System.in);
        int choice = 0;

        while (choice != 4) {
            System.out.println("1-ShowDriverPendding\n" + "2-suspendDriverAccount\n" + "3-suspendPassengerAccount\n" + "4-logOut\n");
            choice = input.nextInt();
            switch (choice) {
                case 1: {
                    try {
                        showDriverPendding();
                        commonFunction("Accepted", accountId);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                   
                    break;
                }
                case 2: {
                    try {
                        showDriverAccounts();
                        commonFunction("Susspend", accountId);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    
                    break;
                }

                case 3: {
                    try {
                        showPassengerAccounts();
                        commonFunction("Susspend", accountId);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    
                    break;

                }

                case 4: {
                    try {
                        SystemManager.UserManager.logOut(accountId);
                    } catch (Exception ex) {
                        System.out.println("Errrorrr");
                    }
                    break;
                }

                default:
                    break;
            }
        }

    }

}
