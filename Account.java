/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;

public abstract class Account {

    private String userName;
    private String email;
    private String mobileNum;
    private String password;
    private String status;
    private Iscreen accountScreen;
    private  int accountId ;
    public Account() {
       
    }
    public void  setAccountId(int accountId){this.accountId=accountId;}
    public int getAccountId(){return accountId;}

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNum() {
        return this.mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    void setScreenType(Iscreen screenType) {
        accountScreen = screenType;
    }

    void openScreen() {
       this.accountScreen.displayFeatures(accountId);
    }


 
}

class Admin extends Account {

}

class Passenger extends Account {
    private int currentRideId=-1;
    Ride rideRequest(String source, String destination) {
        Ride newRide=new Ride();
        newRide.setDestination(destination);
        newRide.setSource(source);
        newRide.setPassenger(this);
        newRide.setStatus("Waiting");
        return newRide;
        
    }
    
    void setcurrentRideId(int currentRideId){this.currentRideId=currentRideId;}
    int getcurrentRideId(){return currentRideId;}
}

class Driver extends Account {

    private String drivingLicense;
    private String nationalId;
    private Admin admin;
    private double averageRate=0;
    private  ArrayList<String> favoriteArea=new ArrayList<>();
    private int currentRideId=-1;
    public void setAdmin(Admin admin){this.admin=admin;}

    public void setcurrentRideId(int currentRideId){
      this.currentRideId=currentRideId;
    }
    public int getCurrentRideId() {
        return currentRideId;
    }
    public String getNationalId() {
        return nationalId;
    }

    public String getDriverLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public Admin getAdmin() {
        return admin;
    }
    
    public double getAverageRate() {
        
        ArrayList<Ride>rides=SystemManager.RideManager.getRides();
        
        int counter=0;double rate=0;
        for (Ride ride : rides) {
          
           if(ride.getDriver()!=null&&ride.getDriver().getAccountId()==this.getAccountId()){
            rate+=ride.getStar();
            counter++;
           }
        }
        if(counter==0) return 0;
        averageRate=rate/counter;
        System.out.println("AVERAGERATE:"+averageRate);
        return averageRate;
    }
    public void addFavouriteArea(String area){
        favoriteArea.add(area);
    }
    
    public ArrayList<String>getfavouruteArea(){return favoriteArea;}

}
