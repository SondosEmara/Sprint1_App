/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Scanner;



interface Iregister {

    public Account registerLogic(String userName, String email, String mobileNum, String password);
}

class DriverRegister implements Iregister {

    @Override
    public Account registerLogic(String userName, String email, String mobileNum, String password) {

        Scanner input = new Scanner(System.in);
        System.out.println("Enter nationalId: ");
        String nationalId = input.next();
        System.out.println("Enter licenseId:");
        String licenseId = input.next();
        Driver driver=new Driver();
        driver.setEmail(email);
        driver.setMobileNum(mobileNum);
        driver.setPassword(password);
        driver.setUserName(userName);
        driver.setDrivingLicense(licenseId);
        driver.setNationalId(nationalId);
        driver.setStatus("pandding");
        return driver;
    }
}

class PassengerRegister implements Iregister {

    @Override
    public Account registerLogic(String userName, String email, String mobileNum, String password) {
        Account passenger=new Passenger();
        passenger.setEmail(email);
        passenger.setMobileNum(mobileNum);
        passenger.setPassword(password);
        passenger.setUserName(userName);
        passenger.setStatus("Active");
        return passenger;
    }
}
