package eecs285.proj3.simplee;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import eecs285.proj3.simplee.Simulate.Simulator;


public class AddVehicleDialog extends JDialog
{
  private JButton vehicleOKButton;
  private JButton vehicleCancelButton;
  private JTextField vehicleXField;
  private JTextField vehicleYField;
  private JComboBox< String > selectVehicleCombo;
  
  private JPanel vehicleFirstLine;
  private JPanel vehicleSecondLine;
  private JPanel vehicleThirdLine;
  private String comboString = "Delivery Truck";
  public AddVehicleDialog(JFrame mainFrame, String title)
  {
    super(mainFrame, title, true);
    
    vehicleOKButton = new JButton("OK");
    vehicleCancelButton = new JButton("Cancel");
    vehicleXField = new JTextField(10);
    vehicleYField = new JTextField(10);
    selectVehicleCombo = new JComboBox< String >();
    selectVehicleCombo.addItem("Delivery Truck");
    selectVehicleCombo.addItem("Freight Truck");
    selectVehicleCombo.addItem("Cargo Plane");
    
    vehicleFirstLine = new JPanel(new FlowLayout());
    vehicleSecondLine = new JPanel(new FlowLayout());
    vehicleThirdLine = new JPanel(new FlowLayout());
    
    vehicleFirstLine.add(new JLabel("Vehicle Type:"));
    vehicleFirstLine.add(selectVehicleCombo);
    vehicleSecondLine.add(new JLabel("Start X Location:"));
    vehicleSecondLine.add(vehicleXField);
    vehicleSecondLine.add(new JLabel("Start Y Location:"));
    vehicleSecondLine.add(vehicleYField);
    vehicleThirdLine.add(vehicleOKButton);
    vehicleThirdLine.add(vehicleCancelButton);
    
    setLayout(new GridLayout(3, 1));
    add(vehicleFirstLine);
    add(vehicleSecondLine);
    add(vehicleThirdLine);

    selectVehicleCombo.addActionListener(new ComboListener());
    vehicleOKButton.addActionListener(new ButtonListener());
    vehicleCancelButton.addActionListener(new ButtonListener());
    
    pack();
    setVisible(true);
    
  }
  
  
  public class ComboListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      JComboBox< String > combo = (JComboBox< String >)e.getSource();
      if (combo.getSelectedItem().equals("Freight Truck"))
        comboString = "Freight Truck";
      else if (combo.getSelectedItem().equals("Cargo Plane"))
        comboString = "Cargo Plane";
    }
  }
  
  public class ButtonListener implements ActionListener
  {
    private double startX;
    private double startY;
    public void actionPerformed(ActionEvent e) 
    {
      if (e.getSource() == vehicleOKButton)
      {
        try
        {
          if (vehicleXField.getText().length() == 0 ||
              vehicleYField.getText().length() == 0)
            throw new EmptyTextFieldException();
          startX = Double.parseDouble(vehicleXField.getText());
          startY = Double.parseDouble(vehicleYField.getText());
          int choice = JOptionPane.showConfirmDialog(AddVehicleDialog.this, 
              "Are you sure you want to add this vehicle?", 
              "Confirmation", JOptionPane.YES_NO_OPTION);
          if (choice == 0)
          {
            Simulator.add(VehicleFactory.createVehicle(startX,
                startY, comboString));
            setVisible(false);
          }
        }
        catch (NumberFormatException unknownException)
        {
          JOptionPane.showMessageDialog(AddVehicleDialog.this, 
              "Error x and y location must be numbers!", 
              "Error!", JOptionPane.ERROR_MESSAGE);
        }
        catch (EmptyTextFieldException emptyException)
        {
          JOptionPane.showMessageDialog(AddVehicleDialog.this, 
              "Text Fields Cannot Be Empty!", 
              "Error!", JOptionPane.ERROR_MESSAGE);
        }
        catch (VehicleException vehicleException)
        {
          // vehicle type unrecognized
        }
      }
      else if (e.getSource() == vehicleCancelButton)
      {
        vehicleXField.setText("");
        vehicleYField.setText("");
        setVisible(false);
      }

    }
  }
}