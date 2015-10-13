package eecs285.proj3.simplee.Simulate;


import eecs285.proj3.simplee.FedexSimulatorGUI;
import eecs285.proj3.simplee.MainFile;
import eecs285.proj3.simplee.ParcelException;
import eecs285.proj3.simplee.ParcelFactory;
import eecs285.proj3.simplee.VehicleException;
import eecs285.proj3.simplee.VehicleFactory;
import eecs285.proj3.simplee.Simulate.Parcel.Parcel;
import eecs285.proj3.simplee.Simulate.Vehicle.CargoPlane;
import eecs285.proj3.simplee.Simulate.Vehicle.DeliveryTruck;
import eecs285.proj3.simplee.Simulate.Vehicle.FreightTruck;
import eecs285.proj3.simplee.Simulate.Vehicle.Vehicle;
import eecs285.proj3.util.Location;
import eecs285.proj3.util.Utility;

import javax.swing.*;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by Colin on 10/23/2014.
 * <p/>
 * This class controls the simulation.
 * <p/>
 * This is a static class is which all member variables and methods are static.
 * You do not need to create an instance of this class everything should be
 * accessed statically
 */
public class Simulator
{
  private static ArrayList< Parcel > initialParcelList = new ArrayList<>();
  private static ArrayList< Vehicle > initialVehicleList = new ArrayList<>();
  private static int numberConsecutiveTicksAllVehiclesIdle = 0;
  private static ArrayList< Parcel > parcelArrayList = new ArrayList<>();
  private static int tick = 0;
  private static ArrayList< Vehicle > vehicleArrayList = new ArrayList<>();

  // ---------------------------------------------------------------------------

  /**
   * Adds a parcel to the initialParcelList and the parcelArrayList. 
   * This should be called when adding a parcel.
   *
   * @param parcel
   *         The parcel you wish to add.
   */
  public static void add( final Parcel parcel )
  {
      try
      {
          initialParcelList.add(( Parcel ) parcel.clone());
      }
      catch ( CloneNotSupportedException e )
      {
          System.out.println("Alert staff to this Error: " + e);
          System.exit(4);
      }
      parcelArrayList.add(parcel);
  }

  // ---------------------------------------------------------------------------

  /**
   * Adds a vehicle to the initialVehicleList and the vehicleArrayList. 
   * This should be called when adding a vehicle.
   */
  public static void add( final Vehicle vehicle )
  {
      try
      {
          initialVehicleList.add(( Vehicle ) vehicle.clone());
      }
      catch ( CloneNotSupportedException e )
      {
          System.out.println("Alert staff to this Error: " + e);
          System.exit(4);
      }
      vehicleArrayList.add(vehicle);
  }

  // ---------------------------------------------------------------------------

  //---------------------------------------------------------------------------

  /**
   * Clears the initialPartialList and parcelArrayList. This should be called when the clearParcelList button is pressed.
   */
  public static void clearParcelList()
  {
      initialParcelList.clear();
      parcelArrayList.clear();
      Parcel.resetIDCounter();
  }

  // ---------------------------------------------------------------------------

  /**
   * Clears the initialVehicleList and the vehicleArrayList. This should be called when the clearVehicleList button is pressed.
   */
  public static void clearVehicleList()
  {
      initialVehicleList.clear();
      vehicleArrayList.clear();
      Vehicle.resetIDCounter();

      Vehicle.reset();
      DeliveryTruck.reset();
      FreightTruck.reset();
      CargoPlane.reset();
  }

  // ---------------------------------------------------------------------------

  
  
  /**
   * Ends the simulation and dumps the statistics. This should be called when
   * the end simulation button is pressed.
   *
   * @return A string containing all the text pertaining to the end of the
   * simulation.
   */
  public static String endSimulation()
  {
    String output = "--- Done ---\n";
    output += dumpStats();
    return output;
  }

  // ---------------------------------------------------------------------------

  /**
   * @return An array of parcels from the parcelArrayList. This should be
   * called anytime you need an array of the current parcelArrayList.
   */
  public static Parcel[] getParcelArray()
  {
    return parcelArrayList.toArray(new Parcel[parcelArrayList.size()]);
  }

  // ---------------------------------------------------------------------------

  /**
   * @return An array of vehicles from the vehicleArrayList. This should be
   * called anytime you need an array of the current vehicleArrayList.
   */
  public static Vehicle[] getVehicleArray()
  {
    return vehicleArrayList.toArray(new Vehicle[vehicleArrayList.size()]);
  }

  // ---------------------------------------------------------------------------

  /**
   * Removes a vehicle from the vehicleArrayList. Also removes vehicle from
   * the initialVehicleList. This should be called when deleting a vehicle.
   *
   * @param vehicle
   *     The vehicle you wish to remove.
   */
  public static void remove( Vehicle vehicle )
  {
    vehicleArrayList.remove(vehicle);
    initialVehicleList.remove(vehicle);
  }

  // ---------------------------------------------------------------------------

  /**
   * Removes a parcel from the parcelArrayList. Also removes parcel from the
   * initialParcelList. This should be called when deleting a parcel.
   *
   * @param parcel
   *     The parcel you wish to remove.
   */
  public static void remove( Parcel parcel )
  {
    parcelArrayList.remove(parcel);
    initialParcelList.remove(parcel);
  }

  // ---------------------------------------------------------------------------

  /**
   * Resets the vehicles and parcels for the simulation. This function should
   * be called first when resetting the GUI.
   */
  public static void reset()
  {
    setVehicleArrayList();
    setParcelArrayList();
    Vehicle.reset();
    DeliveryTruck.reset();
    FreightTruck.reset();
    CargoPlane.reset();

    tick = 0;
    numberConsecutiveTicksAllVehiclesIdle = 0;
  }

  // ---------------------------------------------------------------------------

  /**
   * Starts the simulation. This function should be called to start the
   * simulation.
   *
   * @param numberOfTimesToRunSimulation
   *     The number of times to advanceSimulation the simulation.
   * @param dumpStats
   *     A boolean value. If true the statistics will be dumped.
   *
   * @return output of the simulation thus far.
   */
  public static String startSimulation( int numberOfTimesToRunSimulation,
                                        boolean dumpStats )
  {
    String output = "";
    output += "Simulation starting...\n";
    output += String.format("%s\n", Utility.createFence());

    output += advanceSimulation(numberOfTimesToRunSimulation, dumpStats);
    return output;
  }

  // ---------------------------------------------------------------------------

  /**
   * Advances the simulate X amount of times. This function should be called
   * to advance the simulation.
   *
   * @param numberOfTimesToRunSimulation
   *     The number of times to advanceSimulation the simulation.
   * @param dumpStats
   *     A boolean value. If true the statistics will be dumped.
   *
   * @return The output created by the advancement.
   */
  public static String advanceSimulation( int numberOfTimesToRunSimulation,
                                          boolean dumpStats )
  {
    String output = "";
    for ( int i = 0; i < numberOfTimesToRunSimulation; ++i )
    {
      output += run();
    }

    if ( dumpStats )
    {
      output += dumpStats();
    }

    return output;
  }

  // ---------------------------------------------------------------------------

  /**
   * Sets the initialLists from the beginning of the simulation to preserve
   * the state of the vehicles and parcels for resetting later. This function
   * should be called just be the start of the simulation to set the initial
   * states of the vehicles and parcels.
   */
  public static void setInitialLists()
  {
    try
    {
      setInitialParcelList();
      setInitialVehicleList();
    }
    catch ( CloneNotSupportedException e )
    {
      System.out.println("Contact Staff with error");
      System.exit(1);
    }
  }

  // ---------------------------------------------------------------------------

  /**
   * loadVehicles opens a file and loads the contents to the vehicle list and
   * vehicle array. This function should be called to load the vehicles.
   * <p/>
   * A vehicle line is formatted name beginningXLocation
   * beginningYLocation.<br>
   * <p/>
   * i.e. Delivery_Truck 0 0
   *
   * @param inputFile
   *     The file you wish to load the vehicles from.
   *
   * @return An array of Strings containing the vehicle descriptions.
   */
  public static String[] loadVehicles( File inputFile )
      throws VehicleException
  {
    if ( inputFile == null )
    {
      return null;
    }
    ArrayList< String > vehicleStrings = new ArrayList<>();
    try
    {
      Scanner in = new Scanner(inputFile);
      int fileLine = 0;
      while ( in.hasNextLine() )
      {
        try
        {
          String line = in.nextLine();
          Scanner lineScanner = new Scanner(line);

          String type = lineScanner.next().replace('_', ' ');
          double beginX = Double.parseDouble(lineScanner.next());
          double beginY = Double.parseDouble(lineScanner.next());

          Vehicle vehicle =
              VehicleFactory.createVehicle(beginX, beginY, type);

          Simulator.add(vehicle);
          vehicleStrings.add(vehicle.toString());

          ++fileLine;
        }
        catch ( VehicleException e )
        {
          throw new VehicleException(
              String.format("File line: %d Error: %s", fileLine,
                            e.getMessage()));
        }
      }
    }
    catch ( FileNotFoundException | NumberFormatException |
        VehicleException e )
    {
      throw new VehicleException(
          String.format("Error: %s\n", e.getMessage()));
    }

    return vehicleStrings.toArray(new String[vehicleStrings.size()]);
  }

  // ---------------------------------------------------------------------------

  /**
   * loadParcels opens a file and loads the contents to the parcel list and
   * parcel array. This function should be called to load parcels.
   * <p/>
   * A parcel line is formatted as name beginningXLocation beginningYLocation
   * endXLocation endYLocation.
   * <p/>
   * i.e Large_Parcel 5 10 0 0
   *
   * @param inputFile
   *     The file you wish to load the parcels from.
   *
   * @return An array of strings contain the parcels description.
   */
  public static String[] loadParcels( File inputFile ) throws ParcelException
  {
    if ( inputFile == null )
    {
      return null;
    }
    ArrayList< String > parcelStrings = new ArrayList<>();
    try
    {
      Scanner in = new Scanner(inputFile);
      int fileLine = 0;
      while ( in.hasNextLine() )
      {
        try
        {
          String line = in.nextLine();
          Scanner lineScanner = new Scanner(line);

          String type = lineScanner.next().replace('_', ' ');
          double beginLocationX = Double.parseDouble(lineScanner.next());
          double beginLocationY = Double.parseDouble(lineScanner.next());
          double endLocationX = Double.parseDouble(lineScanner.next());
          double endLocationY = Double.parseDouble(lineScanner.next());

          Parcel parcel = ParcelFactory
              .createParcel(beginLocationX, beginLocationY,
                            endLocationX, endLocationY,
                            type);

          Simulator.add(parcel);
          parcelStrings.add(parcel.toString());
          ++fileLine;
        }
        catch ( ParcelException | NoSuchElementException e )
        {
          String message =
              e.getMessage() == null ? "Type, Beginning, " +
                                       "or End Location not " +
                                       "specified correctly" : e.getMessage();
          throw new ParcelException(
              String.format("File line: %d in File: %s, Error: %s", fileLine,
                            inputFile.getName(), message));
        }
      }
    }
    catch ( FileNotFoundException | NumberFormatException |
        ParcelException e )
    {
      throw new ParcelException(e.getMessage() + "\n");
    }

    return parcelStrings.toArray(new String[parcelStrings.size()]);
  }

  // ---------------------------------------------------------------------------

  /**
   * saveSimulationOutput saves the output in the text area. This function
   * should be called to save the output currently in the textArea to file.
   *
   * @param outputFile
   *     The file you wish to save to.
   */
  public static void saveSimulationOutput( File outputFile )
      throws IOException
  {
    if ( outputFile == null )
    {
      return;
    }
    try
    {
      BufferedWriter writer =
          new BufferedWriter(new FileWriter(outputFile));

      writer.write(FedexSimulatorGUI.getTextArea().getText());
      writer.newLine();

      writer.flush();
      writer.close();

      JOptionPane.showMessageDialog(MainFile.win, "Output Has Been Saved",
                                    "Saved Output",
                                    JOptionPane.PLAIN_MESSAGE);
    }
    catch ( IOException e )
    {
      throw new IOException(
          String.format("Error writing to file. Error: %s\n",
                        e.getMessage()));
    }
  }

  // ---------------------------------------------------------------------------

  /**
   * saveVehicleList saves the vehicleList. This function should be called
   * when you want to save the vehicle list.
   *
   * @param outputFile
   *     The file you wish to save to.
   */
  public static void saveVehicleList( File outputFile ) throws IOException
  {
    if ( outputFile == null )
    {
      return;
    }
    try
    {
      BufferedWriter writer =
          new BufferedWriter(new FileWriter(outputFile));

      for ( Vehicle v : Simulator.getVehicleArray() )
      {
        String type = v.getVehicleType();
        Location location = v.getCurrentLocation();
        String xLocation = Double.toString(location.getX());
        String yLocation = Double.toString(location.getY());
        writer.write(String.format("%s %s %s", type.replace(' ', '_'),
                                   xLocation, yLocation));
        writer.newLine();
      }

      writer.flush();
      writer.close();

      JOptionPane
          .showMessageDialog(MainFile.win, "Vehicles Have Been Saved",
                             "Saved Vehicles",
                             JOptionPane.PLAIN_MESSAGE);
    }
    catch ( IOException e )
    {
      throw new IOException(
          String.format("Error writing to file. Error: %s\n",
                        e.getMessage()));
    }
  }

  //---------------------------------------------------------------------------

  /**
   * saveParcelList saves the parcel list. This function should be called
   * when you want to save the parcel list.
   *
   * @param outputFile
   *     The file you wish to save to.
   */
  public static void saveParcelList( File outputFile ) throws IOException
  {
    if ( outputFile == null )
    {
      return;
    }
    try
    {
      BufferedWriter writer =
          new BufferedWriter(new FileWriter(outputFile));

      for ( Parcel p : Simulator.getParcelArray() )
      {
        String size = p.getSize();

        Location currentLocation = p.getCurrentLocation();
        Location destination = p.getDestination();

        String currentXLocation =
            Double.toString(currentLocation.getX());
        String currentYLocation =
            Double.toString(currentLocation.getY());
        String destinationXLocation =
            Double.toString(destination.getX());
        String destinationYLocation =
            Double.toString(destination.getY());

        writer.write(String.format("%s_Parcel %s %s %s %s", size,
                                   currentXLocation, currentYLocation,
                                   destinationXLocation,
                                   destinationYLocation));
        writer.newLine();
      }

      writer.flush();
      writer.close();

      JOptionPane
          .showMessageDialog(MainFile.win, "Parcels Have Been Saved",
                             "Saved Parcels",
                             JOptionPane.PLAIN_MESSAGE);
    }
    catch ( IOException e )
    {
      throw new IOException(
          String.format("Error writing to file. Error: %s\n",
                        e.getMessage()));
    }
  }

  // ---------------------------------------------------------------------------

  /**
   * @return True if all parcels have been delivered, false otherwise.
   */
  private static boolean areAllParcelsDelivered()
  {
    if ( parcelArrayList.isEmpty() )
    {
      return false;
    }
    for ( Parcel currentParcel : parcelArrayList )
    {
      if ( !currentParcel.isDelivered() )
      {
        return false;
      }
    }

    return true;
  }

  // ---------------------------------------------------------------------------

  /**
   * @return true if all vehicles are currently idle, false otherwise.
   */
  private static boolean areAllVehiclesIdle()
  {
    for ( Vehicle currentVehicle : vehicleArrayList )
    {
      if ( !currentVehicle.isIdle() )
      {
        return false;
      }
    }

    return true;
  }

  // ---------------------------------------------------------------------------

  /**
   * @return A string of the statistics of the simulation.
   */
  private static String dumpStats()
  {
    String output = "";

    output += "";
    output += "\nStatistics:";
    output += "\n" + Utility.createFence() + "\n\n";

    output += "Status of All Parcels...\n";
    output += printParcelOutcomes();

    output += "\n" + Utility.createFence() + "\n\n";

    output += printFuelCosts();
    output += "\n";
    output += printIndividualFuelCosts();

    output += "\n" + Utility.createFence() + "\n\n";

    output += printTotalMilesTraveled();
    output += "\n";
    output += printIndividualMilesTraveled();

    output += "\n" + Utility.createFence() + "\n";

    return output;
  }

  // ---------------------------------------------------------------------------

  /**
   * Handles executing a tick and printing needed output.
   *
   * @param tick
   *     The current tick
   */
  private static String handleExecutingTick( int tick )
  {
    String output = "";

    output += String.format("Executing tick %d:\n", tick);
    for ( Vehicle v : vehicleArrayList )
    {
      output += String.format("    Vehicle #%d is executing...\n",
                              v.getVehicleID());
      output += v.executeClockTick();
      output += "\n";
    }

    return output;
  }

  // ---------------------------------------------------------------------------

  /**
   * Handles printing the output for pre tick and post tick.
   *
   * @param beforeVsAfter
   *     a value of true indicates pre tick, a value of false indicates
   *     post tick
   * @param tick
   *     The current tick
   */
  private static String handlePrePostTick( boolean beforeVsAfter, int tick )
  {
    String output = "";

    output += String.format("%s tick %d:\n",
                            (beforeVsAfter ? "\nBefore" : "After"), tick);

    for ( Vehicle curVehicle : vehicleArrayList )
    {
      output += String.format("    %s at %s\n", curVehicle,
                              curVehicle.getCurrentLocation());
      output += String.format("    %s\n\n", curVehicle.getStatusString());
    }

    return output;
  }

  // ---------------------------------------------------------------------------

  /**
   * @return A string of the the total fuel costs of all vehicle types and
   * subtypes.
   */
  private static String printFuelCosts()
  {
    String output = "";

    output += String.format("Total Fuel Cost For All Vehicles: $%.2f\n",
                            Vehicle.getAllVehiclesTotalFuelCost());
    output += String.format(
        "Total Fuel Cost For All Delivery Trucks: $%.2f\n",
        DeliveryTruck.getDeliveryTrucksTotalFuelCost());
    output +=
        String.format("Total Fuel Cost For All Freight Trucks: $%.2f\n",
                      FreightTruck.getFreightTrucksTotalFuelCost());
    output +=
        String.format("Total Fuel Cost For All Cargo Plane: $%.2f\n",
                      CargoPlane
                          .getCargoPlanesTotalFuelCost());

    return output;
  }
  // ---------------------------------------------------------------------------

  /**
   * @return A string of each individual vehicles fuel cost.
   */
  private static String printIndividualFuelCosts()
  {
    String output = "";

    for ( Vehicle v : vehicleArrayList )
    {
      output += String.format("Vehicle #%d Fuel Cost: $%.2f\n",
                              v.getVehicleID(), v.getFuelCost());
    }

    return output;
  }

  // ---------------------------------------------------------------------------

  /**
   * @return A string of each individual vehicles total miles traveled.
   */
  private static String printIndividualMilesTraveled()
  {
    String output = "";

    for ( Vehicle v : vehicleArrayList )
    {
      output += String.format("Vehicle #%d Total Miles Traveled: %.4f\n",
                              v.getVehicleID(),
                              v.getTotalMilesTraveled());
    }

    return output;
  }

  // ---------------------------------------------------------------------------

  /**
   * @return A string of the parcel outcomes from the simulation.
   */
  private static String printParcelOutcomes()
  {
    String output = "";

    output +=
        String.format("Total shipping costs for simulation: $%.2f\n\n",
                      Parcel.getTotalShippingCost());

    for ( Parcel p : parcelArrayList )
    {
      output +=
          String.format("%s at %s is %s\n", p, p.getCurrentLocation(),
                        p.isDelivered() ? "Delivered" :
                        "Not Delivered");
      output += String.format("    Parcel #%d's Shipping Cost: $%.2f\n",
                              p.getParcelID(), p.getShippingCost());
    }

    return output;
  }

  // ---------------------------------------------------------------------------

  /**
   * @return A string of the total fuel costs of all vehicle types and
   * subtypes.
   */
  private static String printTotalMilesTraveled()
  {
    String output = "";

    output += String.format("Total Miles Traveled For All Vehicles: %.4f\n",
                            Vehicle.getAllVehiclesTotalMilesTraveled());
    output +=
        String.format("Total Fuel Cost For All Delivery Trucks: %.4f\n",
                      DeliveryTruck
                          .getDeliveryTrucksTotalMilesTraveled());
    output +=
        String.format("Total Fuel Cost For All Freight Trucks: %.4f\n",
                      FreightTruck
                          .getFreightTrucksTotalMilesTraveled());
    output += String.format("Total Fuel Cost For All Cargo Plane: %.4f\n",
                            CargoPlane.getCargoPlanesTotalMilesTraveled());

    return output;
  }

  // ---------------------------------------------------------------------------

  /**
   * Runs the simulation once.
   *
   * @return The output of the execution of the tick.
   */
  private static String run()
  {
    String output = "";
    if ( numberConsecutiveTicksAllVehiclesIdle >= 2 ||
         (areAllParcelsDelivered() && !parcelArrayList.isEmpty()) )
    {
      return output;
    }

    output += handlePrePostTick(true, tick);
    output += handleExecutingTick(tick);
    output += handlePrePostTick(false, tick);

    if ( areAllVehiclesIdle() )
    {
      ++numberConsecutiveTicksAllVehiclesIdle;
      output += "All vehicles are idle.\n";
      if ( numberConsecutiveTicksAllVehiclesIdle >= 2 )
      {
        output += String.format("%s\n", Utility.createFence());
        output += "All vehicles have been idle for two ticks.\n";
        return output;
      }
    }
    else
    {
      numberConsecutiveTicksAllVehiclesIdle = 0;
    }

    if ( areAllParcelsDelivered() )
    {
      output += String.format("%s\n", Utility.createFence());
      output += "All parcels have been delivered!\n";
      return output;
    }

    output += String.format("%s\n", Utility.createFence());
    ++tick;

    return output;
  }

  // ---------------------------------------------------------------------------

  /**
   * Sets the initialParcelList.
   */
  private static void setInitialParcelList() throws CloneNotSupportedException
  {
    initialParcelList = new ArrayList<>();
    for ( Parcel p : parcelArrayList )
    {
      initialParcelList.add(( Parcel ) p.clone());
    }
  }

  // ---------------------------------------------------------------------------

  /**
   * Sets the initialVehicleList.
   */
  private static void setInitialVehicleList()
      throws CloneNotSupportedException
  {
    initialVehicleList = new ArrayList<>();
    for ( Vehicle v : vehicleArrayList )
    {
      initialVehicleList.add(( Vehicle ) v.clone());
    }
  }

  // ---------------------------------------------------------------------------

  /**
   * This is called by reset. Its function is to set the parcelArrayList to
   * the initialParcelList so the parcels will be at their initial states from
   * before the simulation was run.
   */
  private static void setParcelArrayList()
  {
    parcelArrayList = new ArrayList<>();
    for ( Parcel p : initialParcelList )
    {
      parcelArrayList.add(p);
    }
  }

  // ---------------------------------------------------------------------------

  /**
   * This is called by reset. Its function is to set the vehicleArrayList to
   * the initialArrayList so the vehicles will be at their initial states from
   * before the simulation was run.
   */
  private static void setVehicleArrayList()
  {
    vehicleArrayList = new ArrayList<>();
    for ( Vehicle v : initialVehicleList )
    {
      vehicleArrayList.add(v);
    }
  }

  // ---------------------------------------------------------------------------
}
