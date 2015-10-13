package eecs285.proj3.simplee;

import eecs285.proj3.simplee.Simulate.Vehicle.CargoPlane;
import eecs285.proj3.simplee.Simulate.Vehicle.DeliveryTruck;
import eecs285.proj3.simplee.Simulate.Vehicle.FreightTruck;
import eecs285.proj3.simplee.Simulate.Vehicle.Vehicle;
import eecs285.proj3.util.Location;

/**
 * This a Factory Method Class for creating Vehicles.
 */
public class VehicleFactory
{
  /**
   * Creates a vehicle.
   *
   * @param beginX
   *     The starting X location of the vehicle.
   * @param beginY
   *     The starting Y location of the vehicle.
   * @param type
   *     The type of the vehicle to create.
   *
   * @return A newly created vehicle.
   *
   * @throws eecs285.proj3.provided.Exceptions.VehicleException
   *     This exception occurs if the type cannot be recognized.
   */
  public static Vehicle createVehicle( double beginX, double beginY,
                                       String type ) throws VehicleException
  {
    switch ( type )
    {
      case "Delivery Truck":
        return new DeliveryTruck(new Location(beginX, beginY));
      case "Freight Truck":
        return new FreightTruck(new Location(beginX, beginY));
      case "Cargo Plane":
        return new CargoPlane(new Location(beginX, beginY));
      default:
        throw new VehicleException("Vehicle Type Not Recognized");
    }
  }

  // ---------------------------------------------------------------------------
}
