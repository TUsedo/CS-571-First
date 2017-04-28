package bridgecontroller;

import java.util.Random;
 
public class Vehicle implements Runnable{
  
  private int weight;
  private int vehicleId;
  private EnumClass.Type vehType;
  private EnumClass.Direction vehDirection;
  private VehicleBridge vb;

  
  public Vehicle (int id, double vp, double dp, VehicleBridge vb) {
    
    Random random = new Random();
    double value;
    
    vehicleId = id;
    
    value = random.nextDouble();
    if(value <= vp) {
      vehType = EnumClass.Type.Car;
      weight = 100;
    }
    else {
      vehType = EnumClass.Type.Truck;
      weight = 300;
    }
    
    value = random.nextDouble();
    if(value <= dp) 
      vehDirection = EnumClass.Direction.Northbound;
    else 
      vehDirection = EnumClass.Direction.Southbound;
    
    this.vb = vb;    
            
  }
  
  
  
  public void run () {
    
    arrive();
    cross();
    leave();
   
  }
  
  public void arrive() {
    
    vb.classifyVehicle(this);
     
    while(!vb.isBridgeAvailable(this)) {
     
      vehicleWait();
     
    }
    
  }
  
  
  public void cross() {
    
    vb.startCrossing(this);
    try {
      Thread.sleep(2000);
    }
    catch (InterruptedException ex) {
      ex.printStackTrace();
    }
    
  }
  
  
  public void leave() {
    
    vb.finishedCrossing(this);    
  }
  
  public synchronized void vehicleWait() {
    
    try {
      wait();
    }
    catch(InterruptedException ex) {
      ex.printStackTrace();
    }
    
  }
  
  
  public synchronized void notifyVehicle() {
    notify();
  }
  
  public int getVehicleId() {
    return vehicleId;
  }
  
  public int getVehicleWeight() {
    return weight;
  }
  
  public EnumClass.Direction getVehicleDirection() {
    return vehDirection;
  }
  
  public EnumClass.Type getVehicleType() {
    return vehType;        
  }
  

  
  
}