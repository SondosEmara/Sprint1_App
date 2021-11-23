/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Map;
import java.util.Scanner;

public class PassengerScreen implements Iscreen {

    public void showAllRideOffers(Map<Integer, String> rideOffers) throws Exception {

        if (!rideOffers.isEmpty()) {
            System.out.println("NEW NOTIFICATION :");
            for (Map.Entry<Integer, String> offer : rideOffers.entrySet()) {

                try {
                    Driver driver = (Driver) SystemManager.UserManager.search(offer.getKey());

                    System.out.println("The Driver Id : " + offer.getKey()
                            + " Price :" + offer.getValue() + " driverAverageRating: "+driver.getAverageRate());
                } catch (Exception error) {
                    throw new Exception("No Exist");
                }
            }
        } else {
            throw new Exception("no Exist any Notifiaction");
        }

    }

    public void requestRide(int accountId) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the source Ride");
        String source = input.next();
        System.out.println("Enter the destination Ride");
        String destination = input.next();

        try {
            Passenger account = (Passenger) SystemManager.UserManager.search(accountId);
            if (account.getcurrentRideId() != -1) {
                System.out.println("Sorry you can not register new ride before end the current ride");
            } else {
                Object ride = account.rideRequest(source, destination);
                int rideId = SystemManager.RideManager.addNewRide(ride);
                account.setcurrentRideId(rideId);
                SystemManager.UserManager.updateAccount(account, accountId);
            }
        } catch (Exception error) {
            System.out.println("error");
        }

    }

    public void rideNotification(int accountId) {

        Scanner input = new Scanner(System.in);
        try {
            Passenger passenger = (Passenger) SystemManager.UserManager.search(accountId);
            int rideId = passenger.getcurrentRideId();

            if (rideId != -1) {
                Ride currentRide = SystemManager.RideManager.search(rideId, accountId);

                if (!currentRide.getRideoffers().isEmpty()) {

                    if (("Start".equals(currentRide.getStatus()))) {
                        System.out.println("No exist Any Notification");
                    } else {
                        try {

                            showAllRideOffers(currentRide.getRideoffers());

                            System.out.println("if you need to seclect Driver (yes or no)");
                            String opinion = input.next();
                            if ("yes".equalsIgnoreCase(opinion)) {
                                System.out.println("Select One Driver Id");
                                int driverId = input.nextInt();
                                try {
                                    currentRide.setDriver((Driver) SystemManager.UserManager.search(driverId));
                                    currentRide.setStatus("Start");
                                    SystemManager.RideManager.updateRide(currentRide, rideId);

                                } catch (Exception error) {
                                    System.out.println("The driver ID not correct try Again");

                                }
                            }
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());

                        }
                    }
                } else {
                    System.out.println("No exist any new Notifiacation");
                }
            } else {
                System.out.println("No exist any new Ride");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void evaluateRide(int accountId) throws Exception {

        Scanner input = new Scanner(System.in);
        System.out.println("Evalute the  driver ride from current Ride:");
        System.out.println("ENter the rideId");

        int rideId = input.nextInt();
        Ride ride = SystemManager.RideManager.searchById(rideId);
        if (ride.getpassenger().getAccountId() == accountId && "End".equals(ride.getStatus())) {
            System.out.println("Enter the StarNumber but between 1 to 5 to evaluate");
            int starNum = input.nextInt();

            if (!(starNum >= 1 && starNum <= 5)) {
                System.out.println("The star Number not correct try Again");

            } else {
                ride.setStar(starNum);
                SystemManager.RideManager.updateRide(ride, rideId);
            }

        } else {

            System.out.println("You can not evaluate this ride before the ride start ");
        }
    }

    @Override
    public void displayFeatures(int accountId) {
        Scanner input = new Scanner(System.in);
        int choice = 1;
        while (choice != 4) {
            System.out.println("1-RequestRide\n" + "2-RideNotification\n" + "3-Evalute any Ride you do it\n" + "4-logOut\n");

            choice = input.nextInt();
            switch (choice) {

                case 1: {
                    requestRide(accountId);
                    break;
                }

                case 2: {

                    rideNotification(accountId);
                    break;
                }
                case 3: {
                    try {
                        evaluateRide(accountId);
                    } catch (Exception ex) {
                        System.out.println("Not Exist");
                    }
                }
                break;

                case 4: {
                    try {
                        SystemManager.UserManager.logOut(accountId);
                    } catch (Exception ex) {
                        System.out.println("Errorr");
                    }
                    break;
                }
                default: {
                    System.out.println("please enter the correct choice try Again");
                }

            }
        }
    }
}
