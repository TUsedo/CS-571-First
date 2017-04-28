package bridgecontroller;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
 
public class Vehicle implements Runnable{
  
  private int weight;
  private int vehicleId;
  private EnumClass.Type vehType;
  private EnumClass.Direction vehDirection;
  private VehicleBridge vb;
  private static final Logger logger = LogManager.getLogger(Vehicle.class);
  
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
  
  
  @Override
  public void run () {
    
    try {
      arrive();
    } catch (InterruptedException e) {      
      logger.error("Arrive Method Interrupted");
    }
    try {
      cross();
    } catch (InterruptedException e) {     
      logger.error("Cross Method Interrupted");
    }
    leave();
  }
  
  public void arrive() throws InterruptedException {
    
    vb.classifyVehicle(this);
     
    while(!vb.isBridgeAvailable(this)) {
     
      vehicleWait();
     
    }
    
  }
  
  
  public void cross() throws InterruptedException {
    
    vb.startCrossing(this);
      Thread.sleep(2000);    
  }
  
  
  public void leave() {
    
    vb.finishedCrossing(this);    
  }
  
  public synchronized void vehicleWait() throws InterruptedException{
        wait();  
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