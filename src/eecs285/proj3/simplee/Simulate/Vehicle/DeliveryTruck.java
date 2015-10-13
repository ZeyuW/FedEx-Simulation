package eecs285.proj3.simplee.Simulate.Vehicle;

import eecs285.proj3.util.Constants;
import eecs285.proj3.util.Location;
import eecs285.proj3.util.PickupPriorityLevel;

/**
 * The DeliveryTruck subclass of Vehicle
 */
public class DeliveryTruck extends Vehicle implements Cloneable
{
  private static double deliveryTrucksTotalFuelCost = 0;
  private static double deliveryTrucksTotalMilesTraveled = 0;

  // ---------------------------------------------------------------------------

  /**
   * The DeliveryTruck constructor.
   *
   * @param inStartLocation
   *     The starting location of the vehicle.
   */
  public DeliveryTruck( Location inStartLocation )
  {
    super(inStartLocation);
  }

  // ---------------------------------------------------------------------------

  /**
   * @return deliveryTrucksTotalFuelCost
   */
  public static double getDeliveryTrucksTotalFuelCost()
  {
    return deliveryTrucksTotalFuelCost;
  }

  // ---------------------------------------------------------------------------

  /**
   * @return deliveryTrucksTotalMilesTraveled
   */
  public static double getDeliveryTrucksTotalMilesTraveled()
  {
    return deliveryTrucksTotalMilesTraveled;
  }

  // ---------------------------------------------------------------------------

  /**
   * Resets the private static statistic variables.
   */
  public static void reset()
  {
    deliveryTrucksTotalFuelCost = 0;
    deliveryTrucksTotalMilesTraveled = 0;
  }

  // ---------------------------------------------------------------------------

  @Override
  public Object clone() throws CloneNotSupportedException
  {
    return super.clone();
  }

  // ---------------------------------------------------------------------------

  @Override
  public double getFuelCostPerGallon()
  {
    return Constants.FUEL_COST_PER_GALLON__DELIVERY_TRUCK;
  }

  // ---------------------------------------------------------------------------

  @Override
  public double getMPG()
  {
    return Constants.MPG__DELIVERY_TRUCK;
  }

  // ---------------------------------------------------------------------------

  @Override
  public double getSpeed()
  {
    return Constants.SPEED_PER_TICK__DELIVERY_TRUCK;
  }

  // ---------------------------------------------------------------------------

  @Override
  public String getVehicleType()
  {
    return "Delivery Truck";
  }

  // ---------------------------------------------------------------------------

  @Override
  public void addFuelCostToTotalForVehicleType( double fuelCost )
  {
    deliveryTrucksTotalFuelCost += fuelCost;
  }

  // ---------------------------------------------------------------------------

  @Override
  public void addTotalMilesTraveledForVehicleType( double totalMilesTraveled )
  {
    deliveryTrucksTotalMilesTraveled += totalMilesTraveled;
  }

  // ---------------------------------------------------------------------------

  @Override
  public double getMaxDropoffDistance()
  {
    return Constants.MAX_DROPOFF_DISTANCE__DELIVERY_TRUCK;
  }

  // ---------------------------------------------------------------------------

  @Override
  public boolean isAllowedToPickupPriorityLevel( String priority )
  {
    return priority.equals(PickupPriorityLevel.LOW_PRIORITY.toString());
  }

  // ---------------------------------------------------------------------------
}
