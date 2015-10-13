package eecs285.proj3.simplee;


/**
 * This a Factory Method Class for creating a file path to an image.
 */
public class ImagePathFactory
{
  /**
   * Creates a path to a vehicle or parcel image.
   *
   * @param type
   *     The type of image you want to find the path for.
   *
   * @return The path of the image.
   *
   * @throws eecs285.proj3.colindj.Exceptions.ImagePathException
   *     Occurs when the type isn't recognized.
   */
  public static String createImagePath( String type ) throws ImagePathException
  {
    switch ( type )
    {
      case "Delivery Truck":
        return "images/Delivery_Truck.jpg";
      case "Freight Truck":
        return "images/Freight_Truck.jpg";
      case "Cargo Plane":
        return "images/Cargo_Plane.jpg";
      case "Large":
        return "images/Large_Parcel.png";
      case "Medium":
        return "images/Medium_Parcel.png";
      case "Small":
        return "images/Small_Parcel.png";
      default:
        throw new ImagePathException();
    }
  }

  // ---------------------------------------------------------------------------
}
