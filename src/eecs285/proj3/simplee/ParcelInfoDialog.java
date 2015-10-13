package eecs285.proj3.simplee;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import eecs285.proj3.simplee.Simulate.Parcel.Parcel;
import eecs285.proj3.util.Location;

public class ParcelInfoDialog extends JDialog
{
  private JButton okButton = new JButton("OK");
  private JPanel topPanel;
  private JPanel middlePanel;
  private JPanel bottomPanel;
  private JPanel middleLeftPanel;
  private JPanel middleTopLeftPanel;
  private JPanel middleMiddlePanel;
  private JPanel middleCenterPanel;
  private JPanel middleRightPanel;
  
  
  public ParcelInfoDialog(JFrame mainFrame, String title, Parcel parcel) 
  {
    super(mainFrame, title, true);
    
    // Set up topPanel
    JLabel titleLabel = new JLabel(parcel.getSize() + " Parcel");
    titleLabel.setFont(new Font("Dialog", 1, 18));
    topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    topPanel.add(titleLabel);
    
    // Set up middleTopLeftPanel
    middleTopLeftPanel = new JPanel(new GridLayout(7, 1));
    middleTopLeftPanel.add(new JLabel("Parcel ID:"));
    middleTopLeftPanel.add(new JLabel("Parcel Size:"));
    middleTopLeftPanel.add(new JLabel("Parcel Weight:"));
    middleTopLeftPanel.add(new JLabel("Parcel Current Location:"));
    middleTopLeftPanel.add(new JLabel("Parcel Destination:"));
    middleTopLeftPanel.add(new JLabel("Parcel Priority:"));
    middleTopLeftPanel.add(new JLabel("Parcel Shipping Cost:"));
    
    // Set up middleMiddlePanel
    middleMiddlePanel = new JPanel(new GridLayout(7, 1));
    middleMiddlePanel.add(new JLabel(parcel.getParcelID() + ""));
    middleMiddlePanel.add(new JLabel(parcel.getSize()));
    middleMiddlePanel.add(new JLabel(String.format("%.2f",
        parcel.getWeight())));
    middleMiddlePanel.add(new JLabel(parcel.getCurrentLocation().toString()));
    middleMiddlePanel.add(new JLabel(parcel.getDestination().toString()));
    middleMiddlePanel.add(new JLabel(parcel.getPickupPriorityLevelForDistance(
        Location.getDistanceBetween(
            parcel.getCurrentLocation(),
            parcel.getDestination()))));
    middleMiddlePanel.add(new JLabel("$" + String.format("%.2f",
        parcel.getShippingCost())));
    
    // Set up middleLeftPanel
    middleLeftPanel = new JPanel(new BorderLayout());
    //middleLeftPanel.add(topPanel, BorderLayout.NORTH);
    middleLeftPanel.add(middleTopLeftPanel, BorderLayout.WEST);
    middleLeftPanel.add(new JLabel(" "));
    middleLeftPanel.add(middleMiddlePanel, BorderLayout.EAST);
    
    // Set up middleCenterPanel
    middleCenterPanel = new JPanel(new BorderLayout());
    middleCenterPanel.add(topPanel, BorderLayout.NORTH);
    middleCenterPanel.add(middleLeftPanel, BorderLayout.SOUTH);
    
    // Set up middleRightPanel
    try
    {
      JLabel imageLabel = new JLabel(new ImageIcon(
          System.getProperty("user.dir") + "/src/" + 
          ImagePathFactory.createImagePath(parcel.getSize())));
      middleRightPanel = new JPanel(new FlowLayout());
      middleRightPanel.add(imageLabel);
    }
    catch (ImagePathException imagePathException)
    {
      System.out.println("Unrecognized image path!");
    }
    
    // Set up middlePanel
    middlePanel = new JPanel(new FlowLayout());
    middlePanel.add(new JLabel("  "));
    middlePanel.add(middleCenterPanel);
    middlePanel.add(new JLabel("  "));
    middlePanel.add(middleRightPanel);
    
    // Set up bottomPanel
    bottomPanel = new JPanel(new FlowLayout());
    okButton.addActionListener(
        new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            setVisible(false);
          }
        });
    bottomPanel.add(okButton, SwingConstants.CENTER);
    
    // Set up wholePanel
    setLayout(new BorderLayout());
    add(middlePanel, BorderLayout.NORTH);
    add(bottomPanel, BorderLayout.SOUTH);
    
    setResizable(false);
    pack();
    setVisible(true);
  }
}
