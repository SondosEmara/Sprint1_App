/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class SystemManager {

    public static class UserManager {

        private static int accountsNumber = 0;
        private static ArrayList<Account> systemAccounts = new ArrayList<>();

        public UserManager() {
            systemAccounts = new ArrayList<>();
            accountsNumber = 0;
        }

        public static Account logIn(String userName, String password) throws Exception {

            Account targetAccount = null;
            int i;
            for (i = 0; i < systemAccounts.size(); i++) {
                if ((systemAccounts.get(i).getUserName().equals(userName)) && (systemAccounts.get(i).getPassword().equals(password))) {
                    targetAccount = systemAccounts.get(i);
                    break;
                }
            }

            if (targetAccount == null) {

                throw new Exception("the info not correct try again");
            } else if ("pandding".equals(targetAccount.getStatus())) {
                throw new Exception("you are now in pandding State");
            } else if ("Susspend".equals(targetAccount.getStatus())) {
                throw new Exception("your account is susupend ");
            }

            targetAccount.setStatus("Online");
            systemAccounts.set(i, targetAccount);
            return targetAccount;
        }

        public static void register(Iregister registerType) {

            String email = "";
            Scanner input = new Scanner(System.in);
            System.out.println("Enter userName:");
            String userName = input.next();
            System.out.println("The email is not mandratory:Enter yes TO enter or no:");
            String opinion = input.next();

            if ("yes".equalsIgnoreCase(opinion)) {
                System.out.println("Enter email:");
                email = input.next();
            }

            System.out.println("Enter mobilPhone:");
            String mobilPhone = input.next();
            System.out.println("Enter password:");
            String password = input.next();

            Account newAccount = registerType.registerLogic(userName, email, mobilPhone, password);
            if (newAccount instanceof Passenger) {
                newAccount.setScreenType(new PassengerScreen());
            } else if (newAccount instanceof Driver) {
                newAccount.setScreenType(new DriverScreen());
            }
            newAccount.setAccountId(++accountsNumber);
            systemAccounts.add(newAccount);
        }

        public static void addNewAdmin(Account newAdmin) {
            newAdmin.setAccountId(++accountsNumber);
            newAdmin.setScreenType(new AdminScreen());
            systemAccounts.add(newAdmin);

        }

        public static ArrayList<Account> getSystemAccounts() {
            return systemAccounts;
        }

        public static Account search(int accountId) throws Exception {
            for (int i = 0; i < systemAccounts.size(); i++) {
                if (systemAccounts.get(i).getAccountId() == accountId) {
                    return systemAccounts.get(i);
                }
            }
            throw new Exception("The account id is not correct");
        }

        public static void updateAccount(Account targetAccount, int accountId) {

            for (int i = 0; i < systemAccounts.size(); i++) {
                if (systemAccounts.get(i).getAccountId() == accountId) {
                    systemAccounts.set(i, targetAccount);
                }
            }

        }

        public static void logOut(int accountId) throws Exception {

            Account account = search(accountId);
            account.setStatus("Offline");
            updateAccount(account, accountId);
        }

        //to only test
        public static void print() {

            for (Account systemAccount : systemAccounts) {

                if (systemAccount instanceof Admin) {
                    System.out.println("Admin:" + systemAccount.getStatus());

                } else if (systemAccount instanceof Passenger) {
                    System.out.println("Passenger id :" + systemAccount.getAccountId() + ((Passenger) systemAccount).getcurrentRideId());
                } else if (systemAccount instanceof Driver) {
                    Driver driver = (Driver) systemAccount;
                    System.out.println("Driver:" + driver.getStatus() + driver.getAccountId());
                }
            }
        }

    }

    public static class RideManager {

        private static int rideNumber;

        private static ArrayList<Ride> systemRides = new ArrayList<>();

         public static ArrayList<Ride>getRides()

        {
            return systemRides;
        }

      
        public RideManager() {
            //systemRides = new ArrayList<>();
            rideNumber = 0;
        }

        public static int addNewRide(Object newRide) {

            Ride ride = (Ride) newRide;

            ride.setRideId(++rideNumber);

            systemRides.add(ride);
            return rideNumber;

        }

        //to only tESt
        public static void print() {

            for (Ride systemRide : systemRides) {
                Map<Integer, String> map = systemRide.getRideoffers();

                System.out.println("The offers of that ride is : ");
                for (Map.Entry<Integer, String> entry : map.entrySet()) {
                    System.out.println("driver id = " + entry.getKey()
                            + ", Price = " + entry.getValue());
                }
                System.out.println("The PAssenger Name: " + systemRide.getpassenger().getUserName() + " "
                        + "Passenger Id : " + systemRide.getpassenger().getAccountId() + " "
                        + "Source : " + systemRide.getSource() + " "
                        + "RideId: " + systemRide.getRideId() + " "
                        + "Destination: " + systemRide.getDestination() + " ");
            }

        }

        public static Ride search(int rideId, int accountId) throws Exception {
            for (Ride systemRide : systemRides) {

                if (systemRide.getRideId() == rideId && systemRide.getpassenger().getAccountId() == accountId) {
                    return systemRide;
                }
            }
            throw new Exception("You are not in this Ride");
        }

        public static void updateRide(Ride newRide, int rideId) {
            for (int i = 0; i < systemRides.size(); i++) {
                Ride ride = systemRides.get(i);
                if (ride.getRideId() == rideId) {
                    systemRides.set(i, newRide);
                    break;
                }

            }
        }

        public static ArrayList<Ride> searchRide(ArrayList<String> driverArea) {
            ArrayList<Ride> commonArea = new ArrayList<>();
            for (int i = 0; i < systemRides.size(); i++) {
                Ride ride = systemRides.get(i);
                if (driverArea.contains(ride.getSource())) {
                    commonArea.add(ride);
                }
            }
            return commonArea;
        }

        public static Ride searchById(int rideId) throws Exception {

            for (int i = 0; i < systemRides.size(); i++) {
                Ride ride = systemRides.get(i);
                if (ride.getRideId() == rideId) {
                    return ride;
                }
            }
            throw new Exception("No eXIst");
        }

    }

}
