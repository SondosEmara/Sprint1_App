/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.Scanner;

public class DriverScreen implements Iscreen {

    public void showRides(ArrayList<Ride> ride) {

        for (Ride ride1 : ride) {
            if (!"Start".equals(ride1.getStatus()) && !"End".equals(ride1.getStatus())) {
                System.out.println("Ride Id: " + ride1.getRideId() + "  Source: " + ride1.getSource() + "  Destination: " + ride1.getDestination());
            }
        }

    }

    public void addNewAreaCase(int accountId) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the favourite area in the following sequence (area1,area2)");
        String areas = input.next();
        String[] area = areas.split(",");
        Driver driver = null;
        for (String area1 : area) {
            try {
                driver = (Driver) SystemManager.UserManager.search(accountId);
                driver.addFavouriteArea(area1);
            } catch (Exception ex) {
                System.out.println("No eXIst");
            }

        }
        SystemManager.UserManager.updateAccount(driver, accountId);

    }

    public void getRidesFromMySource(int accountId) {

        Scanner input = new Scanner(System.in);

        try {

            Driver driver = (Driver) SystemManager.UserManager.search(accountId);
            if (driver.getCurrentRideId() == -1) {
                ArrayList<String> favoriteArea = driver.getfavouruteArea();
                ArrayList<Ride> commonArea = SystemManager.RideManager.searchRide(favoriteArea);
                if (commonArea.isEmpty()) {
                    System.out.println("No exist any common area");
                } else {
                    showRides(commonArea);
                    System.out.println("If you will select Ride Enter(yes or no)");
                    String opinion = input.next();
                    if (opinion.equalsIgnoreCase("yes")) {
                        System.out.println("SelectOneRide..EnterRideId: ");
                        int rideId = input.nextInt();
                        System.out.println("Enter Ride Price: ");
                        String price = input.next();
                        driver.setcurrentRideId(rideId);
                        SystemManager.UserManager.updateAccount(driver, accountId);
                        Ride selectedRide = SystemManager.RideManager.searchById(rideId);

                        selectedRide.addToMap(accountId, price);//in class Ride
                        SystemManager.RideManager.updateRide(selectedRide, rideId);
                    }
                }
            } else {
                System.out.println("You can not select another ride before end the currnt ride");
            }
        } catch (Exception ex) {
            System.out.println("No Exist");
        }

    }

    public void rideNotification(int accountId) {

        try {
            Driver driver = (Driver) SystemManager.UserManager.search(accountId);

            if (driver.getCurrentRideId() != -1) {
                Ride ride = SystemManager.RideManager.searchById(driver.getCurrentRideId());

                if (driver.getAccountId() == ride.getDriver().getAccountId()) {

                    System.out.println("The Passenger accpted your offer:");
                    System.out.println("passenger MobilePhone: " + ride.getpassenger().getMobileNum()
                            + "  " + "Passenger Name :  " + ride.getpassenger().getUserName());
                } else {

                    driver.setcurrentRideId(-1);
                    SystemManager.UserManager.updateAccount(driver, accountId);
                    System.out.println("THe Passenger not accpted the offer");
                }
            } else {

                System.out.println("No Exist new Notification");
            }

        } catch (Exception ex) {
            System.out.println("No Exist new Notification");
        }

    }

    //the finishCurrentRide not the cancel the ride that is the case the ride is start and finished
    public void finishCurrentRide(int accountId) throws Exception {

        Driver driver = (Driver) SystemManager.UserManager.search(accountId);
        Ride currentRide = SystemManager.RideManager.searchById(driver.getCurrentRideId());
        if ("Start".equals(currentRide.getStatus())) {
            Passenger passenger = currentRide.getpassenger();
            driver.setcurrentRideId(-1);
            passenger.setcurrentRideId(-1);
            currentRide.setStatus("End");
            currentRide.setDriver(driver);
            currentRide.setPassenger(passenger);
            SystemManager.RideManager.updateRide(currentRide, currentRide.getRideId());
            SystemManager.UserManager.updateAccount(passenger, passenger.getAccountId());
            SystemManager.UserManager.updateAccount(driver, driver.getAccountId());
        } else {
            System.out.println("You can not finish the ride before start Ride");
        }
    }

    public void showAllRating(int accountId) {

        ArrayList<Ride> rides = SystemManager.RideManager.getRides();
        boolean check = false;
        for (Ride ride : rides) {

            if (ride.getDriver() != null && ride.getDriver().getAccountId() == accountId && "End".equals(ride.getStatus())) {

                System.out.println("Ride Id: " + ride.getRideId() + " PassengerRate: " + ride.getStar());
                check = true;
            }
        }
        if (!check) {
            System.out.println("No Exist Any Ride Rate");
        }

    }

    @Override
    public void displayFeatures(int accountId) {

        Scanner input = new Scanner(System.in);
        int choice = 1;
        while (choice !=6) {
            System.out.println("1-Add new Area\n" + "2-Get All Rides From My Source\n" + "3-RideNotification\n" + "4-FinishCurrentRide\n"
                    + "5-showAllRating\n" + "6-logOut\n");

            choice = input.nextInt();
            switch (choice) {

                case 1: {
                    addNewAreaCase(accountId);
                    break;
                }

                case 2: {
                    getRidesFromMySource(accountId);
                    break;
                }
                case 3: {
                    rideNotification(accountId);
                    break;
                }

                case 4: {

                    try {
                        finishCurrentRide(accountId);
                    } catch (Exception ex) {
                        System.out.println("Not exist");
                    }
                    break;
                }

                case 5: {
                    showAllRating(accountId);
                    break;
                }
                case 6: {
                    try {
                        SystemManager.UserManager.logOut(accountId);
                    } catch (Exception ex) {
                        System.out.println("Errorr");
                    }

                    break;
                }
                default: {
                    System.out.println("Please Enter the correct Choice");
                    break;
                }

            }

        }
    }
}
