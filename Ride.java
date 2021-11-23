/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author c
 */
class Ride {
    
    private String source ;
    private String destination ;
    private int rideId;
    private Passenger passenger;
    private String rideStatus;
    //int is driverId and the String is Price.
    private Map<Integer, String> rideOffers = new HashMap();
    private Driver driver;
    private int starNum=0;
    
    
    public void setStar(int starNum){this.starNum=starNum;}
    public int getStar(){return starNum;}
    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination){this.destination=destination;}
   
    public String getDestination(){return destination;}
    
    public int getRideId() {
        return this.rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    void setPassenger(Passenger passenger) {
        this.passenger=passenger;
        
    }
    
    public Passenger getpassenger(){return passenger;}

    public void setStatus(String rideStatus) {
        this.rideStatus=rideStatus;
    }
    public String getStatus(){return rideStatus;}
    
    
   public void addToMap(int driverId,String price){
    rideOffers.put(driverId, price);
   
   }
   
   public  Map<Integer, String>getRideoffers(){return rideOffers;}

    void setDriver(Driver driver) {
        this.driver=driver;
    }
    
    Driver getDriver(){return driver;}
   
}
