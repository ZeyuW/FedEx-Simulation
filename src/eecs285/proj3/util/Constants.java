package eecs285.proj3.util;

/**
 * YOU MAY NOT MODIFY THIS FILE.<br>
 */
public class Constants
{
  /**
   * The fuel cost in dollars per gallon for vehicles
   */
  public static final double FUEL_COST_PER_GALLON__CARGO_PLANE = 6.5;
  public static final double FUEL_COST_PER_GALLON__DELIVERY_TRUCK = 3.2;
  public static final double FUEL_COST_PER_GALLON__FREIGHT_TRUCK = 3.7;
  /**
   * After identifying next parcel to pickup, a vehicle can pickup a parcel
   * in
   * the <i>same</i> clock tick if the
   * parcel is within this distance of the vehicle. Otherwise, the vehicle
   * must wait until the next clock tick to
   * start moving toward the parcel.
   */
  public static final double IMMEDIATE_PICKUP_RADIUS = 0.5;
  /**
   * For each vehicle type, the vehicle is allowed to drop off a parcel
   * within
   * X miles of its destination.
   */
  public static final double MAX_DROPOFF_DISTANCE__CARGO_PLANE = 200;
  public static final double MAX_DROPOFF_DISTANCE__DELIVERY_TRUCK = 0;
  public static final double MAX_DROPOFF_DISTANCE__FREIGHT_TRUCK = 60;
  /**
   * The Miles Per Gallon each vehicle gets
   */
  public static final double MPG__CARGO_PLANE = 1.6;
  public static final double MPG__DELIVERY_TRUCK = 12;
  public static final double MPG__FREIGHT_TRUCK = 6.5;
  /**
   * The shipping cost multiplier used to figure cost of shipping
   */
  public static final double SHIPPING_MULTIPLIER__HIGH_PRIORITY = .005;
  public static final double SHIPPING_MULTIPLIER__LOW_PRIORITY = .001;
  public static final double SHIPPING_MULTIPLIER__MEDIUM_PRIORITY = .0025;
  /**
   * Each tick of the clock, a vehicle travels X miles.
   */
  public static final double SPEED_PER_TICK__CARGO_PLANE = 35;
  public static final double SPEED_PER_TICK__DELIVERY_TRUCK = 5;
  public static final double SPEED_PER_TICK__FREIGHT_TRUCK = 10;
  /**
   * The weight multiplier on fuel cost
   */
  public static final double WEIGHT_FUEL_COST_MULTIPLIER__LARGE_PARCEL = 1.5;
  public static final double WEIGHT_FUEL_COST_MULTIPLIER__MEDIUM_PARCEL =
      1.25;
  public static final double WEIGHT_FUEL_COST_MULTIPLIER__SMALL_PARCEL = 1.1;
  /**
   * The Weight of each type of parcel.
   */
  public static final double WEIGHT__LARGE_PARCEL = 50;
  public static final double WEIGHT__MEDIUM_PARCEL = 40;
  public static final double WEIGHT__SMALL_PARCEL = 30;
}// Constants
