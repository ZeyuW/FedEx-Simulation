package eecs285.proj3.simplee;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import eecs285.proj3.simplee.Simulate.Simulator;


public class AddParcelDialog extends JDialog
{
  private JButton parcelOKButton;
  private JButton parcelCancelButton;
  private JTextField parcelXField;
  private JTextField parcelYField;
  private JTextField parcelDestXField;
  private JTextField parcelDestYField;
  private JComboBox< String > selectParcelCombo;
  
  private JPanel parcelFirstLine;
  private JPanel parcelSecondLine;
  private JPanel parcelThirdLine;
  private JPanel parcelFourthLine;
  
  private String comboString = "Small Parcel";
  public AddParcelDialog(JFrame mainFrame, String title)
  {
    super(mainFrame, title, true);
    
    parcelOKButton = new JButton("OK");
    parcelCancelButton = new JButton("Cancel");
    parcelXField = new JTextField(10);
    parcelDestXField = new JTextField(10);
    parcelYField = new JTextField(10);
    parcelDestYField = new JTextField(10);
    selectParcelCombo = new JComboBox< String >();
    selectParcelCombo.addItem("Small Parcel");
    selectParcelCombo.addItem("Medium Parcel");
    selectParcelCombo.addItem("Large Parcel");
    
    parcelFirstLine = new JPanel(new FlowLayout());
    parcelSecondLine = new JPanel(new FlowLayout());
    parcelThirdLine = new JPanel(new FlowLayout());
    parcelFourthLine = new JPanel(new FlowLayout());
    
    parcelFirstLine.add(new JLabel("Parcel Type:"));
    parcelFirstLine.add(selectParcelCombo);
    parcelSecondLine.add(new JLabel("Start X Location:"));
    parcelSecondLine.add(parcelXField);
    parcelSecondLine.add(new JLabel("Start Y Location:"));
    parcelSecondLine.add(parcelYField);
    parcelThirdLine.add(new JLabel("End X Location:"));
    parcelThirdLine.add(parcelDestXField);
    parcelThirdLine.add(new JLabel("End Y Location:"));
    parcelThirdLine.add(parcelDestYField);
    parcelFourthLine.add(parcelOKButton);
    parcelFourthLine.add(parcelCancelButton);
    
    setLayout(new GridLayout(4, 1));
    add(parcelFirstLine);
    add(parcelSecondLine);
    add(parcelThirdLine);
    add(parcelFourthLine);
    
    selectParcelCombo.addActionListener(new ComboListener());
    parcelOKButton.addActionListener(new ButtonListener());
    parcelCancelButton.addActionListener(new ButtonListener());
    
    pack();
    setVisible(true);
  }
  
  public class ComboListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      JComboBox< String > combo = (JComboBox< String >)e.getSource();
      if (combo.getSelectedItem().equals("Medium Parcel"))
        comboString = "Medium Parcel";
      else if (combo.getSelectedItem().equals("Large Parcel"))
        comboString = "Large Parcel";
    }
  }
  
  public class ButtonListener implements ActionListener
  {
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    public void actionPerformed(ActionEvent e) 
    {
      if (e.getSource() == parcelOKButton)
      {
        try
        {
          if (parcelXField.getText().length() == 0 ||
              parcelYField.getText().length() == 0 ||
              parcelDestXField.getText().length() == 0 ||
              parcelDestYField.getText().length() == 0)
            throw new EmptyTextFieldException();
          startX = Double.parseDouble(parcelXField.getText());
          startY = Double.parseDouble(parcelYField.getText());
          endX = Double.parseDouble(parcelDestXField.getText());
          endY = Double.parseDouble(parcelDestYField.getText());
          int choice = JOptionPane.showConfirmDialog(AddParcelDialog.this, 
              "Are you sure you want to add this parcel?", 
              "Confirmation", JOptionPane.YES_NO_OPTION);
          if (choice == 0)
          {
            Simulator.add(ParcelFactory.createParcel(startX,
                startY, endX, endY, comboString));
            setVisible(false);
          }
        }
        catch (NumberFormatException unknownException)
        {
          JOptionPane.showMessageDialog(AddParcelDialog.this, 
              "Error x and y location must be numbers!", 
              "Error!", JOptionPane.ERROR_MESSAGE);
        }
        catch (EmptyTextFieldException emptyException)
        {
          JOptionPane.showMessageDialog(AddParcelDialog.this, 
              "Text Fields Cannot Be Empty!", 
              "Error!", JOptionPane.ERROR_MESSAGE);
        }
        catch (ParcelException parcelException)
        {
          // parcel type unrecognized
        }
      }
      else if (e.getSource() == parcelCancelButton)
      {
        parcelXField.setText("");
        parcelDestXField.setText("");
        parcelYField.setText("");
        parcelDestYField.setText("");
        setVisible(false);
      }

    }
  }
  
}
