package eecs285.proj3.simplee;

import javax.swing.*;

import eecs285.proj3.simplee.Simulate.Simulator;
import eecs285.proj3.simplee.Simulate.Parcel.Parcel;
import eecs285.proj3.util.Location;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RerouteParcelDialog extends JDialog
{
  private JButton OKButton;
  private JButton cancelButton;
  private JTextField parcelIDField;
  private JTextField newXField;
  private JTextField newYField;
  
  private JPanel parcelFirstLine;
  private JPanel parcelSecondLine;
  private JPanel parcelThirdLine;
  public RerouteParcelDialog(JFrame mainFrame, String title)
  {
    super(mainFrame, title, true);
    
    OKButton = new JButton("OK");
    OKButton.addActionListener(new ButtonListener());
    cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(new ButtonListener());
    newXField = new JTextField(10);
    newYField = new JTextField(10);
    parcelIDField = new JTextField(10);
    
    parcelFirstLine = new JPanel(new FlowLayout());
    parcelSecondLine = new JPanel(new FlowLayout());
    parcelThirdLine = new JPanel(new FlowLayout());
    
    parcelFirstLine.add(new JLabel("Parcel ID For Rerouting:"));
    parcelFirstLine.add(parcelIDField);
    parcelSecondLine.add(new JLabel("New X Destination:"));
    parcelSecondLine.add(newXField);
    parcelSecondLine.add(new JLabel("New Y Destination:"));
    parcelSecondLine.add(newYField);
    parcelThirdLine.add(OKButton);
    parcelThirdLine.add(cancelButton);
    
    setLayout(new GridLayout(3, 1));
    add(parcelFirstLine);
    add(parcelSecondLine);
    add(parcelThirdLine);
    
    pack();
    setVisible(true);
  }
  
  public class ButtonListener implements ActionListener
  {
    private double newX;
    private double newY;
    private int ID;
    private Parcel [] parcelList;
    private Parcel tarParcel;
    private boolean flag = false;
    public void actionPerformed(ActionEvent e) 
    {
      if (e.getSource() == OKButton)
      {
        try
        {
          if (newXField.getText().length() == 0 ||
              newYField.getText().length() == 0 ||
              parcelIDField.getText().length() == 0)
            throw new EmptyTextFieldException();
          newX = Double.parseDouble(newXField.getText());
          newY = Double.parseDouble(newYField.getText());
          ID = Integer.parseInt(parcelIDField.getText());

          int choice = JOptionPane.showConfirmDialog(RerouteParcelDialog.this,
              "Are you sure you want to reroute this parcel?", 
              "Confirmation", JOptionPane.YES_NO_OPTION);
          if (choice == 0)
          {
            parcelList = Simulator.getParcelArray();
            for (Parcel parcel : parcelList)
            {
              if (parcel.getParcelID() == ID)
              {
                tarParcel = parcel;
                flag = true;
              }
            }
            if (flag)
            {
              if (tarParcel.isInTransit())
                throw new ParcelInTransit();
              Location Destination = new Location(newX, newY);
              tarParcel.setDestination(Destination);
              FedexSimulatorGUI.updateParcelList(tarParcel,
                  tarParcel.getPreviousPriorityLevel());
            }
            else
              throw new ParcelDoesNotExistException();
          }
          setVisible(false);
        }
        catch (NumberFormatException unknownException)
        {
          JOptionPane.showMessageDialog(RerouteParcelDialog.this, 
              "Error: x or y coordinate not formatted as a number!", 
              "Error!", JOptionPane.ERROR_MESSAGE);
        }
        catch (EmptyTextFieldException emptyException)
        {
          JOptionPane.showMessageDialog(RerouteParcelDialog.this, 
              "Text Fields Cannot Be Empty!", 
              "Error!", JOptionPane.ERROR_MESSAGE);
        }
        catch (ParcelDoesNotExistException noParcelException)
        {
          JOptionPane.showMessageDialog(RerouteParcelDialog.this, 
              "Parcel with id:" + ID + " doesn't exist", 
              "Error!", JOptionPane.ERROR_MESSAGE);
        }
        catch (ParcelInTransit inTransitException)
        {
          JOptionPane.showMessageDialog(RerouteParcelDialog.this, 
              "This parcel cannot be rerouted it is in transit", 
              "Cannot Reroute", JOptionPane.WARNING_MESSAGE);
        }
      }
      else if (e.getSource() == cancelButton)
      {
        newXField.setText("");
        newYField.setText("");
        parcelIDField.setText("");
        setVisible(false);
      }
    }
  }
}
