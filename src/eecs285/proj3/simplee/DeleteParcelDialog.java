package eecs285.proj3.simplee;

import javax.swing.*;

import eecs285.proj3.simplee.Simulate.*;
import eecs285.proj3.simplee.Simulate.Parcel.Parcel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import eecs285.proj3.simplee.DeleteParcelDialog.ButtonListner;

public class DeleteParcelDialog extends JDialog
{
  private JTextField IDField = new JTextField(10);
  private JButton deleteButton = new JButton("Delete");
  private JButton cancelButton = new JButton("Cancel");
  
  private JPanel firstLinePanel = new JPanel(new FlowLayout());
  private JPanel secondLinePanel = new JPanel(new FlowLayout());
  
  public DeleteParcelDialog(JFrame mainFrame, String title)
  {
    super(mainFrame, title, true);
    
    firstLinePanel.add(new JLabel("Please enter the Parcel ID"
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
    private Parcel [] parcelList;
    private Parcel tarParcel;
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

          int choice = JOptionPane.showConfirmDialog(DeleteParcelDialog.this, 
              "Are you sure you want to delete this parcel?", 
              "Confirmation", JOptionPane.YES_NO_OPTION);
          if (choice == 0)
          {
            // delete the parcel
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
              Simulator.remove(tarParcel);
            }
            else
              throw new ParcelDoesNotExistException();
          }
          setVisible(false);
        }
        catch (NumberFormatException unknownException)
        {
          JOptionPane.showMessageDialog(DeleteParcelDialog.this, 
              "parcel ID should be an Integer!", 
              "Error!", JOptionPane.ERROR_MESSAGE);
        }
        catch (EmptyTextFieldException emptyException)
        {
          JOptionPane.showMessageDialog(DeleteParcelDialog.this, 
              "Text Field Cannot Be Empty!", 
              "Error!", JOptionPane.ERROR_MESSAGE);
        }
        catch (ParcelDoesNotExistException parcelNotExistException)
        {
          JOptionPane.showMessageDialog(DeleteParcelDialog.this, 
              "Parcel ID Cannot Be Found!", 
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
