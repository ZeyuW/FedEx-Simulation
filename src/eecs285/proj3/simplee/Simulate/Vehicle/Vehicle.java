package eecs285.proj3.simplee.Simulate.Vehicle;

import eecs285.proj3.simplee.FedexSimulatorGUI;
import eecs285.proj3.simplee.Simulate.Parcel.Parcel;
import eecs285.proj3.simplee.Simulate.Simulator;
import eecs285.proj3.util.Constants;
import eecs285.proj3.util.Location;

/**
 * The abstract base class of Vehicle.
 */
public abstract class Vehicle implements Cloneable
{
  private static double allVehiclesTotalFuelCost = 0;
  private static double allVehiclesTotalMilesTraveled = 0;
  private static int nextID = 0;
  private final int vehicleID;
  private Location currentLocation;
  private VehicleState currentState;
  private double fuelCost = 0;
  private Parcel parcel;
  private double totalMilesTraveled = 0;

  private enum VehicleState
  {
    IDLE, ENROUTE, TRANSPORTING
  }

  // ---------------------------------------------------------------------------

  /**
   * @return allVehiclesTotalFuelCost
   */
  public static double getAllVehiclesTotalFuelCost()
  {
    return allVehiclesTotalFuelCost;
  }

  // ---------------------------------------------------------------------------

  /**
   * @return allVehiclesTotalMilesTraveled
   */
  public static double getAllVehiclesTotalMilesTraveled()
  {
    return allVehiclesTotalMilesTraveled;
  }

  // ---------------------------------------------------------------------------

  /**
   * Resets the private static statistic variables.
   */
  public static void reset()
  {
    allVehiclesTotalFuelCost = 0;
    allVehiclesTotalMilesTraveled = 0;
  }

  // ---------------------------------------------------------------------------

  /**
   * Resets the internal ID counter to 0. If you wish to run multiple
   * simulations in a single run of the program, then this should be called at
   * the start of each simulation.
   */
  public static void resetIDCounter()
  {
    nextID = 0;
  }

  // ---------------------------------------------------------------------------

  /**
   * @return A cloned vehicle if error is throw return null;
   */
  public Object clone() throws CloneNotSupportedException
  {
    return super.clone();
  }

  // ---------------------------------------------------------------------------

  /**
   * Example: "Vehicle #0 (Delivery Truck) at (0.52786, 1.05573)"
   *
   * @return See above.
   */
  @Override
  public String toString()
  {
    return String.format("Vehicle #%d (%s)", vehicleID, getVehicleType());
  }

  // ---------------------------------------------------------------------------

  /**
   * Executes a single tick of the clock.
   */
  public String executeClockTick()
  {
    String output = "";
    if ( currentState == VehicleState.IDLE )
    {
      output += handleIdle();
    }
    else if ( currentState == VehicleState.ENROUTE )
    {
      output += handleEnRoute();
    }
    else if ( currentState == VehicleState.TRANSPORTING )
    {
      output += handleTransporting();
    }
    else
    {
      throw new RuntimeException("execute clock tick error");
    }

    return output;
  }

  // ---------------------------------------------------------------------------

  /**
   * @return currentLocation
   */
  public Location getCurrentLocation()
  {
    return currentLocation;
  }

  // ---------------------------------------------------------------------------

  /**
   * @return fuelCost
   */
  public double getFuelCost()
  {
    return fuelCost;
  }

  // ---------------------------------------------------------------------------

  /**
   * @return fuelCostPerGallon
   */
  public abstract double getFuelCostPerGallon();

  // ---------------------------------------------------------------------------

  /**
   * @return mpg
   */
  public abstract double getMPG();

  // ---------------------------------------------------------------------------

  /**
   * @return speed
   */
  public abstract double getSpeed();

  // ---------------------------------------------------------------------------

  /**
   * Example: "Status: Idle"
   *
   * @return statusString
   */
  public String getStatusString()
  {
    if ( currentState == VehicleState.IDLE )
    {
      return "Status: Idle";
    }

    String priority =
        parcel.getPickupPriorityLevelForDistance(
            Location.getDistanceBetween(parcel.getCurrentLocation(),
                                        parcel.getDestination()));

    if ( currentState == VehicleState.ENROUTE )
    {

      return String
          .format("Status: Moving to pick up %s Parcel (id=%d " +
                  "Pickup_Priority=%s) at %s",
                  parcel.getSize(),
                  parcel.getParcelID(), priority,
                  parcel.getCurrentLocation());
    }
    else if ( currentState == VehicleState.TRANSPORTING )
    {
      return String
          .format("Status: Moving to drop off %s Parcel (id=%d " +
                  "Pickup_Priority=%s) at %s",
                  parcel.getSize(),
                  parcel.getParcelID(), priority,
                  parcel.getDestination());
    }
    else
    {
      return "ERROR IN getStatusString()";
    }
  }

  // ---------------------------------------------------------------------------

  /**
   * @return totalMilesTraveled
   */
  public double getTotalMilesTraveled()
  {
    return totalMilesTraveled;
  }

  // ---------------------------------------------------------------------------

  /**
   * @return vehicleID
   */
  public int getVehicleID()
  {
    return vehicleID;
  }

  // ---------------------------------------------------------------------------

  /**
   * @return One of ["Cargo Plane", "Freight Truck", "Delivery Truck"].
   */
  public abstract String getVehicleType();

  // ---------------------------------------------------------------------------

  /**
   * @return True if the vehicle is in the idle state, false otherwise.
   */
  public boolean isIdle()
  {
    return currentState == VehicleState.IDLE;
  }

  // ---------------------------------------------------------------------------

  /**
   * This function keeps track of fuel costs for each vehicle.
   *
   * @param fuelCost
   *     The fuelCost to be added to the vehicle subtype's total.
   */
  protected abstract void addFuelCostToTotalForVehicleType( double fuelCost );

  // ---------------------------------------------------------------------------

  /**
   * This function keeps track of the Total Miles Traveled for each vehicle.
   *
   * @param totalMilesTraveled
   *     The total miles traveled to be added to the vehicle subtype's
   *     total.
   */
  protected abstract void addTotalMilesTraveledForVehicleType(
      double totalMilesTraveled );

  // ---------------------------------------------------------------------------

  /**
   * @return maxDropoffDistance
   */
  protected abstract double getMaxDropoffDistance();

  // ---------------------------------------------------------------------------

  /**
   * Checks if the vehicle is allowed to pick up a parcel of a particular
   * priority level.
   *
   * @return True if the parcel can be picked up, false otherwise.
   */
  protected abstract boolean isAllowedToPickupPriorityLevel(
      String priority );

  // ---------------------------------------------------------------------------

  /**
   * Adds the fuel cost of the vehicle first the fuel cost of this vehicle,
   * then
   * adds it to the fuel cost of all vehicles, then adds it to the total for
   * all
   * vehicles of that subtype.
   *
   * @param nextLocation
   *     The nextLocation used to calculate the fuel cost from your current
   *     location. After calculation and addition the currentLocation is set to
   *     the nextLocation.
   */
  private void addVehicleFuelCostAndSetNextLocation( Location nextLocation )
  {
    double cost = calculateFuelCost(currentLocation, nextLocation, parcel);

    fuelCost += cost;
    allVehiclesTotalFuelCost += cost;
    addFuelCostToTotalForVehicleType(cost);

    currentLocation = nextLocation;
  }

  // ---------------------------------------------------------------------------

  /**
   * Adds the distance the vehicle will travel to the total miles for this
   * vehicle, then adds it to the total miles for all vehicles, then adds it to
   * all vehicles of that subtype.
   *
   * @param distanceVehicleWillTravel
   *     The distance the vehicle will travel in a given tick if it is not
   *     idle.
   */
  private void addVehicleTotalMilesTraveled(
      double distanceVehicleWillTravel )
  {
    totalMilesTraveled += distanceVehicleWillTravel;
    allVehiclesTotalMilesTraveled += distanceVehicleWillTravel;
    addTotalMilesTraveledForVehicleType(distanceVehicleWillTravel);
  }

  // ---------------------------------------------------------------------------

  /**
   * Calculates the fuel cost given a start and end location and also factoring
   * in the parcel's weight.
   *
   * @param start
   *     The starting location of the vehicle.
   * @param end
   *     The ending location of the vehicle.
   * @param parcel
   *     The parcel the vehicle is carrying.
   *
   * @return The calculated fuel cost.
   */
  private double calculateFuelCost( Location start, Location end,
                                    Parcel parcel )
  {
    return ((Location.getDistanceBetween(start, end) / getMPG()) *
            getFuelCostPerGallon()) *
           Parcel.getWeightFuelCostMultiplier(parcel.getWeight());
  }

  // ---------------------------------------------------------------------------

  /**
   * Handles the enroute state for executeClockTick.
   */
  private String handleEnRoute()
  {
    String output = "";
    if ( parcel.isInTransit() )
    {
      output += String.format(
          "    Vehicle #%d was too late to pick up %s at %s\n",
          vehicleID, parcel,
          parcel.getCurrentLocation());
      parcel = null;
      currentState = VehicleState.IDLE;
    }
    else if ( !isAllowedToPickupPriorityLevel(
        parcel.getPickupPriorityLevelForDistance(
            Location.getDistanceBetween(parcel.getCurrentLocation(),
                                        parcel.getDestination()))) )
    {
      output += String.format(
          "    Vehicle #%d cannot pick up Parcel #%d anymore. %s " +
          "has been rerouted\n",
          vehicleID, parcel.getParcelID(), parcel);

      parcel = null;
      currentState = VehicleState.IDLE;
    }
    else
    {
      double distanceToParcelLocation =
          Location.getDistanceBetween(currentLocation,
                                      parcel.getCurrentLocation());

      boolean rerouted = false;
      for ( Parcel p : Simulator.getParcelArray() )
      {
        double parcelDistanceToDestination =
            Location.getDistanceBetween(p.getCurrentLocation(),
                                        p.getDestination());


        if (
            isAllowedToPickupPriorityLevel(
                p.getPickupPriorityLevelForDistance(
                    parcelDistanceToDestination)) && !p.isInTransit() &&
            !p.isDelivered() )
        {
          double distance =
              Location.getDistanceBetween(currentLocation,
                                          p.getCurrentLocation());
          if ( distanceToParcelLocation > distance )
          {
            distanceToParcelLocation = distance;
            parcel = p;
            rerouted = true;
          }
        }
      }

      if ( rerouted )
      {
        output += String.format(
            "    Vehicle #%d has been rerouted to %s\n",
            getVehicleID(), parcel);
      }

      if ( getSpeed() >= distanceToParcelLocation )
      {
        Location nextLocation = parcel.getCurrentLocation();

        double distanceVehicleWillTravel =
            Location.getDistanceBetween(currentLocation,
                                        nextLocation);
        addVehicleTotalMilesTraveled(distanceVehicleWillTravel);
        addVehicleFuelCostAndSetNextLocation(nextLocation);

        output += String.format("    Vehicle #%d picked up %s at %s\n",
                                vehicleID, parcel,
                                parcel.getCurrentLocation());
        currentState = VehicleState.TRANSPORTING;
        parcel.setInTransit(true);
      }
      else
      {
        Location nextLocation =
            Location.calculateNextLocation(currentLocation,
                                           parcel.getCurrentLocation(),
                                           getSpeed());
        double distanceVehicleWillTravel =
            Location.getDistanceBetween(currentLocation,
                                        nextLocation);
        addVehicleTotalMilesTraveled(distanceVehicleWillTravel);
        addVehicleFuelCostAndSetNextLocation(nextLocation);
      }
    }

    return output;
  }

  // ---------------------------------------------------------------------------

  /**
   * Handles the idle state for executeClockTick.
   */
  private String handleIdle()
  {
    Parcel nearestParcel = null;
    double nearestDistance = 0;
    String output = "";

    for ( Parcel p : Simulator.getParcelArray() )
    {
      double parcelDistanceToDestination =
          Location.getDistanceBetween(p.getCurrentLocation(),
                                      p.getDestination());

      if ( nearestParcel == null &&
           isAllowedToPickupPriorityLevel(
               p.getPickupPriorityLevelForDistance(
                   parcelDistanceToDestination)) &&
           !p.isInTransit() && !p.isDelivered() )
      {
        nearestParcel = p;
        nearestDistance = Location.getDistanceBetween(currentLocation,
                                                      p.getCurrentLocation());
      }
      else
      {
        if (
            isAllowedToPickupPriorityLevel(
                p.getPickupPriorityLevelForDistance(
                    parcelDistanceToDestination)) &&
            !p.isInTransit() &&
            !p.isDelivered() )
        {
          double distance =
              Location.getDistanceBetween(currentLocation,
                                          p.getCurrentLocation());
          if ( nearestDistance > distance )
          {
            nearestDistance = distance;
            nearestParcel = p;
          }
        }
      }
    }

    if ( nearestParcel == null )
    {
      return output;
    }

    parcel = nearestParcel;
    if ( nearestDistance <= Constants.IMMEDIATE_PICKUP_RADIUS )
    {
      output += String.format(
          "    Vehicle #%d is immediately picking up %s at %s\n",
          getVehicleID(), parcel,
          parcel.getCurrentLocation());
      currentState = VehicleState.TRANSPORTING;
      assert !parcel.isInTransit();
      parcel.setInTransit(true);
    }
    else
    {
      currentState = VehicleState.ENROUTE;
      assert !parcel.isInTransit();
    }

    return output;
  }

  // ---------------------------------------------------------------------------

  /**
   * Handles the transporting state for executeClockTick.
   */
  private String handleTransporting()
  {
    String output = "";

    assert (parcel.getCurrentLocation().equals(currentLocation));
    double distanceToParcelDestination =
        Location.getDistanceBetween(currentLocation,
                                    parcel.getDestination());
    if ( distanceToParcelDestination <= getMaxDropoffDistance() )
    {
      output += String.format("    Vehicle #%d dropped off %s at %s\n",
                              vehicleID, parcel,
                              parcel.getCurrentLocation());

      if ( distanceToParcelDestination <= 0 )
      {
        assert (parcel.getDestination().equals(currentLocation));
        assert (parcel.getDestination()
            .equals(parcel.getCurrentLocation()));
        output += String.format(
            "    Parcel #%d is at its Final Destination at %s\n",
            parcel.getParcelID(),
            parcel.getCurrentLocation());
        parcel.setDelivered();
        parcel = null;
        currentState = VehicleState.IDLE;
      }
      else
      {
        output += String.format(
            "    Parcel #%d is waiting for pickup at %s\n",
            parcel.getParcelID(),
            parcel.getCurrentLocation());
        parcel.setInTransit(false);
        currentState = VehicleState.IDLE;
        FedexSimulatorGUI.updateParcelList(parcel,
                                           parcel.getPreviousPriorityLevel());
        parcel = null;
      }
    }
    else
    {
      if ( distanceToParcelDestination <= getSpeed() )
      {
        double distanceToNextLocation =
            Location.getDistanceBetween(currentLocation,
                                        parcel.getDestination());

        addVehicleTotalMilesTraveled(distanceToNextLocation);
        addVehicleFuelCostAndSetNextLocation(parcel.getDestination());
      }
      else
      {
        Location nextLocation =
            Location.calculateNextLocation(currentLocation,
                                           parcel.getDestination(),
                                           getSpeed());
        double distanceToNextLocation =
            Location.getDistanceBetween(currentLocation,
                                        nextLocation);

        addVehicleTotalMilesTraveled(distanceToNextLocation);
        addVehicleFuelCostAndSetNextLocation(nextLocation);
      }
      parcel.setCurrentLocation(currentLocation);
    }

    return output;
  }

  // ---------------------------------------------------------------------------

  /**
   * Constructor of Vehicle.
   */
  Vehicle( Location inStartLocation )
  {
    currentLocation = inStartLocation;

    parcel = null;
    currentState = VehicleState.IDLE;
    vehicleID = nextID++;
  }

  // ---------------------------------------------------------------------------
}// class
