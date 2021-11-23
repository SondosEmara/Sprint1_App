
package main;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        /*
        add admin from the system not  make the register admin
        */
        Account accountAdmin=new Admin();
        accountAdmin.setEmail("admin");
        accountAdmin.setMobileNum("admin");
        accountAdmin.setPassword("admin");
        accountAdmin.setStatus("admin");
        accountAdmin.setUserName("admin");
        SystemManager.UserManager.addNewAdmin(accountAdmin);
        
        Scanner input = new Scanner(System.in);
        int choice = 0;
        while (choice != 3) {

            System.out.println("1-LogIn\n" + "2-Register\n" + "3-exit\n"); 
            choice = input.nextInt();
            switch (choice) {

                case 1:

                    System.out.println("Enter UserName:");
                    String userName = input.next();
                    System.out.println("Enter password:");
                    String password = input.next();

                    try{
                        Account account = SystemManager.UserManager.logIn(userName, password);
                       
                        account.openScreen();
                    }catch (Exception error){
                        System.out.println(error.getMessage());
                    } 
                    break;

                    
                case 2:
                    int choice2 = 0;
                        while (choice2 != 3) {
                        System.out.println("1-UserRegister\n" + "2-DriverRegister\n" + "3-backTOLogIn\n");
                        choice2= input.nextInt();
                        switch (choice2) {
                           
                            case 1:
                                Iregister passengerAccount=new PassengerRegister();
                                SystemManager.UserManager.register(passengerAccount);
                                break;
                                
                            case 2:
                                Iregister driverRegister=new DriverRegister();
                                SystemManager.UserManager.register(driverRegister);
                                break;
                            case 3:
                                break;
                            default:
                                System.out.println("Please Enter the correct choice");
                                break;
                        }
                    }
                    break;
                    
                case 3:
                    System.out.println("Thanks to use our Program");
                    break;
                default:
                    System.out.println("please enter the correct choice ,Try Again");
                    break;
            }

        }
        
      
    }

    }
