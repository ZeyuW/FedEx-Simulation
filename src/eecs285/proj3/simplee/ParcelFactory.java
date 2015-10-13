package eecs285.proj3.simplee;

import eecs285.proj3.simplee.Simulate.Parcel.LargeParcel;
import eecs285.proj3.simplee.Simulate.Parcel.MediumParcel;
import eecs285.proj3.simplee.Simulate.Parcel.Parcel;
import eecs285.proj3.simplee.Simulate.Parcel.SmallParcel;
import eecs285.proj3.util.Location;

/**
 * This a Factory Method Class for creating parcels.
 */
public class ParcelFactory
{
  /**
   * Creates a parcel.
   *
   * @param beginX
   *     The starting X location of a parcel.
   * @param beginY
   *     The starting Y location of a parcel.
   * @param endX
   *     The destination X location of a parcel.
   * @param endY
   *     The destination Y location of a parcel.
   * @param type
   *     The type of parcel to create.
   *
   * @return A newly created parcel.
   *
   * @throws eecs285.proj3.provided.Exceptions.ParcelException
   *     Occurs when the type of parcel isn't recognized.
   */
  public static Parcel createParcel( double beginX, double beginY,
                                     double endX, double endY,
                                     String type ) throws ParcelException
  {
    switch ( type )
    {
      case "Large Parcel":
        return new LargeParcel(new Location(beginX, beginY),
                               new Location(endX, endY));
      case "Medium Parcel":
        return new MediumParcel(new Location(beginX, beginY),
                                new Location(endX, endY));
      case "Small Parcel":
        return new SmallParcel(new Location(beginX, beginY),
                               new Location(endX, endY));
      default:
        throw new ParcelException("Invalid Parcel Type");
    }
  }

  // ---------------------------------------------------------------------------
}
