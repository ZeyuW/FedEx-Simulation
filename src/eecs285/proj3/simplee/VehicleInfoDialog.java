package eecs285.proj3.simplee;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import eecs285.proj3.simplee.Simulate.Vehicle.Vehicle;

public class VehicleInfoDialog extends JDialog
{
  private JButton okButton = new JButton("OK");
  private JPanel topPanel;
  private JPanel middlePanel;
  private JPanel bottomPanel;
  private JPanel middleLeftPanel;
  private JPanel middleMiddlePanel;
  private JPanel middleRightPanel;
  private JPanel bottomFirstPanel;
  private JPanel bottomSecondPanel;
  
  
  public VehicleInfoDialog(JFrame mainFrame, String title, Vehicle vehicle) 
  {
    super(mainFrame, title, true);
    
    // Set up topPanel
    JLabel titleLabel = new JLabel(vehicle.getVehicleType());
    titleLabel.setFont(new Font("Dialog", 1, 18));
    topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    topPanel.add(new JLabel(" "));
    topPanel.add(titleLabel);
    
    // Set up middleLeftPanel
    middleLeftPanel = new JPanel(new GridLayout(8, 1));
    middleLeftPanel.add(new JLabel("Vehicle ID:"));
    middleLeftPanel.add(new JLabel("Vehicle Type:"));
    middleLeftPanel.add(new JLabel("Vehicle Current Location:"));
    middleLeftPanel.add(new JLabel("Vehicle Speed:"));
    middleLeftPanel.add(new JLabel("Vehicle Fuel Cost Per Gallon:"));
    middleLeftPanel.add(new JLabel("Vehicle MPG:"));
    middleLeftPanel.add(new JLabel("Vehicle Total Fuel Costs:"));
    middleLeftPanel.add(new JLabel("Vehicle Total Miles Traveled:"));
    
    // Set up middleMiddlePanel
    middleMiddlePanel = new JPanel(new GridLayout(8, 1));
    middleMiddlePanel.add(new JLabel(vehicle.getVehicleID() + ""));
    middleMiddlePanel.add(new JLabel(vehicle.getVehicleType()));
    middleMiddlePanel.add(new JLabel(vehicle.getCurrentLocation().toString()));
    middleMiddlePanel.add(new JLabel(String.format("%.2f",
        vehicle.getSpeed())));
    middleMiddlePanel.add(new JLabel("$" + String.format("%.2f", 
        vehicle.getFuelCostPerGallon())));
    middleMiddlePanel.add(new JLabel(String.format("%.2f", vehicle.getMPG())));
    middleMiddlePanel.add(new JLabel("$" + String.format("%.2f",
        vehicle.getFuelCost())));
    middleMiddlePanel.add(new JLabel(String.format("%.4f", 
        vehicle.getTotalMilesTraveled())));
    
    // Set up middleRightPanel
    try
    {
      JLabel imageLabel = new JLabel(new ImageIcon(
          System.getProperty("user.dir") + "/src/" + 
          ImagePathFactory.createImagePath(vehicle.getVehicleType())));
      middleRightPanel = new JPanel(new FlowLayout());
      middleRightPanel.add(imageLabel);
    }
    catch (ImagePathException imagePathException)
    {
      System.out.println("Unrecognized image path!");
    }
    
    // Set up middlePanel
    middlePanel = new JPanel(new FlowLayout());
    middlePanel.add(new JLabel("    "));
    middlePanel.add(middleLeftPanel);
    middlePanel.add(new JLabel("  "));
    middlePanel.add(middleMiddlePanel);
    middlePanel.add(new JLabel("  "));
    middlePanel.add(middleRightPanel);
    
    // Set up bottomPanel
    bottomFirstPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    bottomSecondPanel = new JPanel(new FlowLayout());
    bottomPanel = new JPanel(new BorderLayout());
    okButton.addActionListener(
        new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            setVisible(false);
          }
        });
    bottomFirstPanel.add(new JLabel(" "));
    bottomFirstPanel.add(new JLabel(vehicle.getStatusString()));
    bottomSecondPanel.add(okButton, SwingConstants.CENTER);
    bottomPanel.add(bottomFirstPanel, BorderLayout.NORTH);
    bottomPanel.add(bottomSecondPanel, BorderLayout.SOUTH);
    
    
    // Set up wholePanel
    setLayout(new BorderLayout());
    add(topPanel, BorderLayout.NORTH);
    add(middlePanel, BorderLayout.CENTER);
    add(bottomPanel, BorderLayout.SOUTH);
    
    setResizable(false);
    pack();
    setVisible(true);
  }
}
