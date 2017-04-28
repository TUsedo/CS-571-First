package bridgecontroller;

import java.util.LinkedList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VehicleBridge {
   
  private VehicleList northBoundList = new VehicleList();
  private VehicleList southBoundList = new VehicleList();
  private VehicleList readyList = new VehicleList();
  private VehicleList bridgeList = new VehicleList();
  private Bridge bridge = new Bridge();
  private Object lockObj = new Object();
  private static final Logger logger = LogManager.getLogger(VehicleBridge.class);
  
  
  /*Classify any arriving vehicle v into respective waiting (NorthBound/SouthBound)List*/
  
  public void classifyVehicle(Vehicle v) {
    
    synchronized(lockObj) {
      
      if(v.getVehicleDirection() == EnumClass.Direction.Southbound)
        southBoundList.add(v);
      else
        northBoundList.add(v);
      
      System.out.println("Vehicle #"+v.getVehicleId()+"(Direction: "+v.getVehicleDirection() +
          ", Type: "+v.getVehicleType()+") has arrived");
      
     System.out.println("\n.................................... \n");
      }
    
  }
  
  /*Returns a boolean value on whether the given vehicle v can be added to the bridge */
  
  public boolean isBridgeAvailable(Vehicle v) {
    
    synchronized(lockObj) {
      int var = bridge.vehicleCompability(v.getVehicleDirection(), v.getVehicleWeight(),v.getVehicleId(),
          oppWaitingList(v.getVehicleDirection()));
           
      if(var == 0) {
        if(v.getVehicleType() == EnumClass.Type.Truck && !findaCar(v.getVehicleDirection()) 
            || v.getVehicleType() == EnumClass.Type.Car) {
          try {
            Thread.sleep(4000);
          }
          catch(InterruptedException ex) {
            ex.printStackTrace();
          }       
        }
        return false;
      }
      
      if(var == 1) {
        readyList.add(v);
        updateWaitingList(v);
        nextVehicle(v.getVehicleDirection());
        return true;
      }      
      return false;
    }
  }
    
  
  /*Returns value for current status of the waiting List 
   * on the other side of the Direction d*/
  
  private boolean oppWaitingList(EnumClass.Direction d) {
    
    if(d == EnumClass.Direction.Southbound) 
      return northBoundList.isEmpty();
    else 
      return southBoundList.isEmpty();
  }
  
  
  /*Returns a boolean value after checking the waitingList 
   * of Direction d for a car */
  
  private boolean findaCar (EnumClass.Direction d) {
    
    LinkedList<Vehicle> list ;
    
    if(d == EnumClass.Direction.Northbound)
      list = northBoundList.returnList();
    else
      list = southBoundList.returnList();
    
    for(Vehicle v: list) {
      if(v.getVehicleType() == EnumClass.Type.Car) {
        
        v.notifyVehicle();
        return true;
      }
    }
    return false;      
  }
  
  /*Updates the Waiting List when the vehicle v starts crossing the Bridge*/
  
  private void updateWaitingList (Vehicle v) {
    
    if(v.getVehicleDirection() == EnumClass.Direction.Southbound)
      southBoundList.remove(v);
    else
      northBoundList.remove(v);
      
  }
  
  /*Adds Vehicle v to the BridgeList 
  and the prints VehicleList on the Bridge*/
  
  public void startCrossing(Vehicle v) {
    
    synchronized(lockObj) {
      
      bridgeList.add(v);
      System.out.println("Vehicle #"+v.getVehicleId() + " is now crossing the Bridge\n");
      System.out.println("Vehicles on the Bridge: ");
      printList();      
    }
  }
  
  /*Removes the Vehicle v from the BridgeList*/
  
  public void finishedCrossing(Vehicle v) {
    
    synchronized(lockObj) {
      
      bridgeList.remove(v);
      checkWaitingList(v.getVehicleDirection());
      bridge.removeVehicle(v.getVehicleWeight(),checkWaitingList(v.getVehicleDirection()),bridgeList.isEmpty());
      
      System.out.println("Vehicle #"+v.getVehicleId()+" has exited the Bridge \n");
      System.out.println("Vehicles on the Bridge: ");
      
      printList();
      
      if(bridgeList.isEmpty())
        notifyWaitingVehicles();
      else
        nextVehicle(v.getVehicleDirection());
      
    }
  }
  
/*Notifies next vehicle in the Waiting List of Direction d*/
  
  private void nextVehicle(EnumClass.Direction d) {
    
    LinkedList<Vehicle> list;
    
    if(EnumClass.Direction.Northbound == d) 
      list = northBoundList.returnList();
    else 
      list = southBoundList.returnList(); 
    
    for(Vehicle v: list) {
       v.notifyVehicle();
    }
  }
  
  /*Prints the Vehicle List*/
  
  private void printList() {
    
    bridgeList.printList();
    System.out.println("\nTotal weight on the Bridge: "+bridgeList.getTotalWeight());
    System.out.println("\n************************************ \n");
   
    if(bridgeList.getTotalWeight() > 750)
      logger.error("Total Weight on Bridge Greater Than 750 :---> "+bridgeList.getTotalWeight());
    
    System.out.println("Waiting Vehicles (Northbound List): ");
    northBoundList.printList();
    System.out.println("\n.................................... \n");
    
    System.out.println("Waiting Vehicles (Southbound List): ");
    southBoundList.printList();
    System.out.println("\n.................................... \n");
  }
  
  
  /*checks whether there are any waiting vehicles on the other Side of Direction "d"*/
  
  private int checkWaitingList(EnumClass.Direction d) {
    
    int value;    
    if(d == EnumClass.Direction.Southbound) {
      if(!southBoundList.isEmpty())
        value = 1;
      else 
        value = 0;
    }
    else { 
      if(!northBoundList.isEmpty())
        value = 1;
      else 
        value = 0;
    }
    return value;
  }
  
  private void notifyWaitingVehicles() {
    
    if(!northBoundList.isEmpty())
      northBoundList.get(0).notifyVehicle();
    if(!southBoundList.isEmpty())
      southBoundList.get(0).notifyVehicle();
  }
  
  
}
