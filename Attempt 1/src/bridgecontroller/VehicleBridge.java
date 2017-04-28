package bridgecontroller;

import java.util.LinkedList;


public class VehicleBridge {
 

  private VehicleList northBoundList = new VehicleList();
  private VehicleList southBoundList = new VehicleList();
  private VehicleList readyList = new VehicleList();
  private Bridge bridge = new Bridge();
  private Object lockObj = new Object();
  
  
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
  
  /*Checks whether the bridge is available for the given vehicle*/
  
  public boolean isBridgeAvailable(Vehicle v) {
    
    synchronized(lockObj) {
      int var = bridge.vehicleCompability(v.getVehicleDirection(), v.getVehicleWeight(), v.getVehicleId());
      
      if(var == -1) {
        return false;
      }
      
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
      }      
      return true;
    }
  }
  
  /*Looks for a car in the given direction and returns true if found one*/
  
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
  
  
  private void updateWaitingList (Vehicle v) {
    
    if(v.getVehicleDirection() == EnumClass.Direction.Southbound)
      southBoundList.remove(v);
    else
      northBoundList.remove(v);
      
  }
  
  
  private void nextVehicle(EnumClass.Direction d) {
    
    LinkedList<Vehicle> list;
    
    if(EnumClass.Direction.Northbound == d) 
      list = northBoundList.returnList();
    else 
      list = southBoundList.returnList(); 
    
    for(Vehicle v: list) {
     /*if(v.getStatus())*/
       v.notifyVehicle();
    }
  }
  
  
  public void startCrossing(Vehicle v) {
    
    synchronized(lockObj) {
      
      bridge.addVehicle(v);
      readyList.remove(v);
      System.out.println("Waiting Vehicles (Northbound List): ");
      northBoundList.printList();
      System.out.println("\n.................................... \n");
      System.out.println("Waiting Vehicles (Southbound List): ");
      southBoundList.printList();
      System.out.println("\n.................................... \n");
      
    }
  }
  
  
  public void finishedCrossing(Vehicle v) {
    
    synchronized(lockObj) {
      
      bridge.removeVehicle(v,checkWaitingList(v.getVehicleDirection()));
      
     
      System.out.println("Waiting Vehicles (Northbound List): ");
      northBoundList.printList();
      System.out.println("\n.................................... \n");
      
      System.out.println("Waiting Vehicles (Southbound List): ");
      southBoundList.printList();
      System.out.println("\n.................................... \n");
      
      if(bridge.isBridgeEmpty())
        notifyWaitingVehicles();
      else
        nextVehicle(v.getVehicleDirection());
      
    }
  }
  
  
  private boolean checkWaitingList(EnumClass.Direction d) {
    
    if(d == EnumClass.Direction.Southbound)
      return northBoundList.isEmpty();
    else
      return southBoundList.isEmpty();
  }
  
  
  private void notifyWaitingVehicles() {
    
    if(!northBoundList.isEmpty())
      northBoundList.get(0).notifyVehicle();
    if(!southBoundList.isEmpty())
      southBoundList.get(0).notifyVehicle();
  }
  
  
}
