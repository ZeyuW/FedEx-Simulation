package eecs285.proj3.simplee;

import javax.swing.*;

import eecs285.proj3.simplee.Simulate.Vehicle.Vehicle;
import eecs285.proj3.simplee.Simulate.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DeleteVehicleDialog extends JDialog
{
  private JTextField IDField = new JTextField(10);
  private JButton deleteButton = new JButton("Delete");
  private JButton cancelButton = new JButton("Cancel");
  
  private JPanel firstLinePanel = new JPanel(new FlowLayout());
  private JPanel secondLinePanel = new JPanel(new FlowLayout());
  
  public DeleteVehicleDialog(JFrame mainFrame, String title)
  {
    super(mainFrame, title, true);
    
    firstLinePanel.add(new JLabel("Please enter the Vehicle ID"
        + " you wish to delete:"));
    firstLinePanel.add(IDField);
    secondLinePanel.add(deleteButton);
    secondLinePanel.add(cancelButton);
    setLayout(new GridLayout(2, 1));
    add(firstLinePanel);
    add(secondLinePanel);
    
    deleteButton.addActionListener(new ButtonListner());
    cancelButton.addActionListener(new ButtonListner());
    
    pack();
    setVisible(true);
  }
  
  public class ButtonListner implements ActionListener
  {
    private int ID;
    private Vehicle [] vehicleList;
    private Vehicle tarVehicle;
    private boolean flag = false;
    public void actionPerformed(ActionEvent e)
    {
      if (e.getSource() == deleteButton)
      {
        try
        {
          if (IDField.getText().length() == 0)
            throw new EmptyTextFieldException();
          ID = Integer.parseInt(IDField.getText());

          int choice = JOptionPane.showConfirmDialog(DeleteVehicleDialog.this, 
              "Are you sure you want to delete this vehicle?", 
              "Confirmation", JOptionPane.YES_NO_OPTION);
          if (choice == 0)
          {
            // delete the vehicle
            vehicleList = Simulator.getVehicleArray();
            for (Vehicle vehicle : vehicleList)
            {
              if (vehicle.getVehicleID() == ID)
              {
                tarVehicle = vehicle;
                flag = true;
              }
            }
            if (flag)
            {
              Simulator.remove(tarVehicle);
            }
            else
              throw new VehicleDoesNotExistException();
          }
          setVisible(false);
        }
        catch (NumberFormatException unknownException)
        {
          JOptionPane.showMessageDialog(DeleteVehicleDialog.this, 
              "vehicle ID should be an Integer!", 
              "Error!", JOptionPane.ERROR_MESSAGE);
        }
        catch (EmptyTextFieldException emptyException)
        {
          JOptionPane.showMessageDialog(DeleteVehicleDialog.this, 
              "Text Field Cannot Be Empty!", 
              "Error!", JOptionPane.ERROR_MESSAGE);
        }
        catch (VehicleDoesNotExistException vehicleNotExistException)
        {
          JOptionPane.showMessageDialog(DeleteVehicleDialog.this, 
              "Vehicle ID Cannot Be Found!", 
              "Error!", JOptionPane.ERROR_MESSAGE);
        }
      }
      else if (e.getSource() == cancelButton)
      {
        IDField.setText("");
        setVisible(false);
      }
    }
  }
}
