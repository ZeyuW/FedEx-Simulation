package eecs285.proj3.simplee;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import eecs285.proj3.simplee.Simulate.Simulator;
import eecs285.proj3.simplee.Simulate.Parcel.Parcel;
import eecs285.proj3.simplee.Simulate.Vehicle.Vehicle;
import eecs285.proj3.util.Location;
/**
 * This class creates the FedexSimulator JFrame and is the main GUI class.
 */
public class FedexSimulatorGUI extends JFrame
{
  // Declare components
  private JButton startSimButton;
  private JButton advanceSimButton;
  private JButton endSimButton;
  private JButton clearVehicleButton;
  private JButton clearParcelButton;
  private JButton clearOutputButton;
  private JButton resetButton;
  private static DefaultListModel< String > vehicleListModel;
  private static DefaultListModel< String > parcelListModel;

  private JList< String > vehicleList;
  private static JList< String > parcelList;
  
  private static JTextArea outputArea;
  private JTextField advanceTickField;
  private JComboBox< String > vehicleTypeCombo;
  private static JComboBox< String > parcelTypeCombo;
  private JCheckBox statsBox;
  private Dimension parcelDimension;
  private Dimension vehicleDimension;
  private JScrollPane parcelJS;
  private JScrollPane vehicleJS;
  private JScrollPane outputJS;
  private JMenuItem loadVehicleItem;
  private JMenuItem loadParcelItem;
  private JMenuItem saveOutputItem;
  private JMenuItem saveVehicleItem;
  private JMenuItem saveParcelItem;
  private JMenuItem exitItem;
  private JMenuItem addVehicleItem;
  private JMenuItem addParcelItem;
  private JMenuItem rerouteParcelItem;
  private JMenuItem deleteParcelItem;
  private JMenuItem deleteVehicleItem;
  
  private String vehicleComboString = "All Vehicles";
  private String parcelComboString = "All Priorities";
  private Vehicle [] inVehicleList;
  private Parcel [] inParcelList;
  
  // Declare sub-panels
  private JPanel leftPanel;
  private JPanel rightPanel;
  private JPanel topPanel;
  private JPanel middlePanel;
  private JPanel bottomPanel;
  private JPanel topLeftPanel;
  private JPanel topRightPanel;
  private JPanel middleLeftPanel;
  private JPanel middleRightPanel;
  private JPanel bottomFirstPanel;
  private JPanel bottomSecondPanel;
  private JPanel bottomThirdPanel;
  private JPanel bottomFourthPanel;
  private static boolean needReset = false;

  
  /**
   * Constructor. This creates the JFrame that controls the simulation.
   */
  public FedexSimulatorGUI()
  {
    super("Fedex Simulation");

    // Set up whole panel
    setWholePanel();
    
    // Add menu bar
    setMenu(); 
  }
  
  private void setTopLeftPanel()
  {
    // Set up topLeftPanel
    vehicleTypeCombo = new JComboBox< String >();
    vehicleTypeCombo.addActionListener(new VehicleComboListener());
    vehicleTypeCombo.addItem("All Vehicles");
    vehicleTypeCombo.addItem("Delivery Truck");
    vehicleTypeCombo.addItem("Freight Truck");
    vehicleTypeCombo.addItem("Cargo Plane");
    topLeftPanel = new JPanel(new GridLayout(2, 1));
    topLeftPanel.add(new JLabel("Show Selected Vehicle Type", 
        SwingConstants.CENTER));
    topLeftPanel.add(vehicleTypeCombo); 
  }
  
  private void setTopRightPanel()
  {
    // Set up topRightPanel
    parcelTypeCombo = new JComboBox< String >();
    parcelTypeCombo.addActionListener(new ParcelComboListener());
    parcelTypeCombo.addItem("All Priorities");
    parcelTypeCombo.addItem("Low");
    parcelTypeCombo.addItem("Medium");
    parcelTypeCombo.addItem("High");
    topRightPanel = new JPanel(new GridLayout(2, 1));
    topRightPanel.add(new JLabel("Show Selected Parcel Type", 
        SwingConstants.CENTER));
    topRightPanel.add(parcelTypeCombo);
  }
  
  private void setTopPanel()
  {
    // Set up topPanel
    setTopLeftPanel();
    setTopRightPanel();
    topPanel = new JPanel(new FlowLayout());
    topPanel.add(topLeftPanel);
    topPanel.add(new JLabel("  "));
    topPanel.add(topRightPanel);
  }
  
  private void setMiddleLeftPanel()
  {
    // Set up middleLeftPanel
    vehicleListModel = new DefaultListModel< String >();
    vehicleList = new JList< String >(vehicleListModel);
    vehicleList.addMouseListener(new VehicleListListener());
    vehicleDimension = new Dimension(200, 300);
    vehicleJS = new JScrollPane(vehicleList);
    vehicleJS.setPreferredSize(vehicleDimension);
    vehicleJS.setHorizontalScrollBarPolicy( 
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
    vehicleJS.setVerticalScrollBarPolicy( 
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    middleLeftPanel = new JPanel(new BorderLayout());
    middleLeftPanel.add(new JLabel("Vehicle List", SwingConstants.CENTER),
        BorderLayout.NORTH);
    middleLeftPanel.add(vehicleJS, BorderLayout.CENTER);
  }
  
  private void setMiddleRightPanel()
  {
    // Set up middleRightPanel
    parcelListModel = new DefaultListModel< String >();
    parcelList = new JList< String >(parcelListModel);
    parcelList.addMouseListener(new ParcelListListener());
    parcelDimension = new Dimension(250,300);
    parcelJS = new JScrollPane(parcelList);
    parcelJS.setPreferredSize(parcelDimension);
    parcelJS.setHorizontalScrollBarPolicy( 
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
    parcelJS.setVerticalScrollBarPolicy( 
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    middleRightPanel = new JPanel(new BorderLayout());
    middleRightPanel.add(new JLabel("Parcel List", SwingConstants.CENTER),
        BorderLayout.NORTH);
    middleRightPanel.add(parcelJS, BorderLayout.CENTER);
  }
  
  private void setMiddlePanel()
  {
    // Set up middlePanel
    setMiddleLeftPanel();
    setMiddleRightPanel();
    middlePanel = new JPanel(new FlowLayout());
    middlePanel.add(middleLeftPanel);
    middlePanel.add(new JLabel(" "));
    middlePanel.add(middleRightPanel);
  }
  
  private void setBottomPanel()
  {
    // Set up bottomFirstPanel
    startSimButton = new JButton("Start Simulation");
    advanceSimButton = new JButton("Advance Simulation");
    advanceSimButton.setEnabled(false);
    endSimButton = new JButton("End Simulation");
    endSimButton.setEnabled(false);
    bottomFirstPanel = new JPanel(new FlowLayout());
    bottomFirstPanel.add(startSimButton);
    bottomFirstPanel.add(advanceSimButton);
    bottomFirstPanel.add(endSimButton);
    
    // Set up bottomSecondPanel
    clearVehicleButton = new JButton("Clear Vehicle List");
    clearParcelButton = new JButton("Clear Parcel List");
    clearOutputButton = new JButton("Clear Output");
    resetButton = new JButton("Reset");
    bottomSecondPanel = new JPanel(new FlowLayout());
    bottomSecondPanel.add(clearVehicleButton);
    bottomSecondPanel.add(clearParcelButton);
    bottomSecondPanel.add(clearOutputButton);
    bottomSecondPanel.add(resetButton);
    
    // Set up bottomThirdPanel
    advanceTickField = new JTextField(20);
    advanceTickField.setText("1");
    bottomThirdPanel = new JPanel(new FlowLayout());
    bottomThirdPanel.add(new JLabel("Advance Next Tick Amount",
        SwingConstants.RIGHT));
    bottomThirdPanel.add(advanceTickField);
    
    // Set up bottomFourthPanel
    statsBox = new JCheckBox();
    bottomFourthPanel = new JPanel(new FlowLayout());
    bottomFourthPanel.add(statsBox);
    bottomFourthPanel.add(new JLabel("Dump Stats After Next Advancement",
        SwingConstants.RIGHT));
    
    // Set up bottomPanel
    bottomPanel = new JPanel(new GridLayout(4, 1));
    bottomPanel.add(bottomFirstPanel);
    bottomPanel.add(bottomSecondPanel);
    bottomPanel.add(bottomThirdPanel);
    bottomPanel.add(bottomFourthPanel);
    
    // Add button actionListener
    startSimButton.addActionListener(new ButtonListener());
    endSimButton.addActionListener(new ButtonListener());
    advanceSimButton.addActionListener(new ButtonListener());
    clearVehicleButton.addActionListener(new ButtonListener());
    clearParcelButton.addActionListener(new ButtonListener());
    clearOutputButton.addActionListener(new ButtonListener());
    resetButton.addActionListener(new ButtonListener());
  }
  
  private void setLeftPanel()
  {
    // Set up leftPanel
    setTopPanel();
    setMiddlePanel();
    setBottomPanel();
    leftPanel = new JPanel(new BorderLayout());
    leftPanel.add(topPanel, BorderLayout.NORTH);
    leftPanel.add(middlePanel, BorderLayout.CENTER);
    leftPanel.add(bottomPanel, BorderLayout.SOUTH);
  }
  
  private void setRightPanel()
  {
    // Set up rightPanel
    outputArea = new JTextArea(31, 50);
    outputArea.setEditable(false);
    outputJS = new JScrollPane(outputArea);
    outputJS.setHorizontalScrollBarPolicy( 
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
    outputJS.setVerticalScrollBarPolicy( 
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    rightPanel = new JPanel(new BorderLayout());
    rightPanel.add(new JLabel("*** Output ***", SwingConstants.CENTER),
        BorderLayout.NORTH);
    rightPanel.add(new JLabel(" "));
    rightPanel.add(outputJS, BorderLayout.SOUTH);
  }
  
  private void setWholePanel()
  {
    // Set up whole panel
    setLeftPanel();
    setRightPanel();
    setLayout(new FlowLayout());
    add(leftPanel);
    add(new JLabel(" "));
    add(rightPanel);
    add(new JLabel(" "));
  }
  
  private void setMenu()
  {
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenu editMenu = new JMenu("Edit");
    loadVehicleItem = new JMenuItem("Load Vehicles");
    loadParcelItem = new JMenuItem("Load Parcels");
    saveOutputItem = new JMenuItem("Save Output");
    saveVehicleItem = new JMenuItem("Save Vehicle List");
    saveParcelItem = new JMenuItem("Save Parcel List");
    exitItem = new JMenuItem("Exit Program");
    addVehicleItem = new JMenuItem("Add Vehicle");
    addParcelItem = new JMenuItem("Add Parcel");
    rerouteParcelItem = new JMenuItem("Reroute Parcel");
    deleteVehicleItem = new JMenuItem("Delete Vehicle");
    deleteParcelItem = new JMenuItem("Delete Parcel");
    fileMenu.add(loadVehicleItem);
    fileMenu.add(loadParcelItem);
    fileMenu.add(saveOutputItem);
    fileMenu.add(saveVehicleItem);
    fileMenu.add(saveParcelItem);
    fileMenu.add(exitItem);
    editMenu.add(addVehicleItem);
    editMenu.add(addParcelItem);
    editMenu.add(rerouteParcelItem);
    editMenu.add(deleteVehicleItem);
    editMenu.add(deleteParcelItem);
    menuBar.add(fileMenu);
    menuBar.add(editMenu);
    setJMenuBar(menuBar);
    
    // Add action listener
    loadVehicleItem.addActionListener(new MenuListener());
    loadParcelItem.addActionListener(new MenuListener());
    saveOutputItem.addActionListener(new MenuListener());
    saveVehicleItem.addActionListener(new MenuListener());
    saveParcelItem.addActionListener(new MenuListener());
    exitItem.addActionListener(new MenuListener());
    addVehicleItem.addActionListener(new MenuListener());
    addParcelItem.addActionListener(new MenuListener());
    rerouteParcelItem.addActionListener(new MenuListener());
    deleteVehicleItem.addActionListener(new MenuListener());
    deleteParcelItem.addActionListener(new MenuListener());
    
  }
  // ---------------------------------------------------------------------------

  
  //Handle menu clicker
  public class MenuListener implements ActionListener
  {
    
    // Declare menu-related members
    private JFileChooser chooser = new JFileChooser();;
    private int chooserReturn; 
    private File chosenFile;
    public void actionPerformed(ActionEvent event) 
    {
      chooser.setSelectedFile(null);
      chosenFile = null;
      if (event.getSource().equals(loadVehicleItem))
      {
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        chooserReturn = chooser.showOpenDialog(FedexSimulatorGUI.this);
        if (chooserReturn == JFileChooser.APPROVE_OPTION)
        {
          chosenFile = chooser.getSelectedFile();
        }
        try
        {
          Simulator.loadVehicles(chosenFile);
          vehiclePrint();
        }
        catch (VehicleException vehicleException)
        {
          outputArea.append(vehicleException.getMessage());
          outputArea.paintImmediately(outputArea.getBounds());
        }
      }
      else if (event.getSource().equals(loadParcelItem))
      {
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        chooserReturn = chooser.showOpenDialog(FedexSimulatorGUI.this);
        if (chooserReturn == JFileChooser.APPROVE_OPTION)
        {
          chosenFile = chooser.getSelectedFile();
        }   
        try
        {
          Simulator.loadParcels(chosenFile);
          parcelPrint(); 
        }
        catch (ParcelException parcelException)
        {
          outputArea.append(parcelException.getMessage());
          outputArea.paintImmediately(outputArea.getBounds());
        }  
      }
      else if (event.getSource().equals(saveOutputItem))
      {
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        chooserReturn = chooser.showSaveDialog(FedexSimulatorGUI.this);
        if (chooserReturn == JFileChooser.APPROVE_OPTION)
        {
          chosenFile = chooser.getSelectedFile();
        }
        try
        {
          Simulator.saveSimulationOutput(chosenFile);
        }
        catch (IOException IOexception)
        {
          // nothing implemented
        }
       
      }
      else if (event.getSource().equals(saveVehicleItem))
      {
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        chooserReturn = chooser.showSaveDialog(FedexSimulatorGUI.this);
        if (chooserReturn == JFileChooser.APPROVE_OPTION)
        {
          chosenFile = chooser.getSelectedFile();
        }
        try
        {
          Simulator.saveVehicleList(chosenFile);
        }
        catch (IOException IOexception)
        {
          // nothing implemented
        }
      }
      else if (event.getSource().equals(saveParcelItem))
      {
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        chooserReturn = chooser.showSaveDialog(FedexSimulatorGUI.this);
        if (chooserReturn == JFileChooser.APPROVE_OPTION)
        {
          chosenFile = chooser.getSelectedFile();
        }
        try
        {
          Simulator.saveParcelList(chosenFile);
        }
        catch (IOException IOexception)
        {
          // nothing implemented
        }
      }
      else if (event.getSource().equals(exitItem))
      {
        FedexSimulatorGUI.this.dispose();
      }
      // Beginning of Edit menu
      else if (event.getSource().equals(addVehicleItem))
      {
        AddVehicleDialog addVehicleDialog;
        addVehicleDialog = new AddVehicleDialog(FedexSimulatorGUI.this,
            "Adding Vehicle");
        vehiclePrint();
      }
      else if (event.getSource().equals(addParcelItem))
      {
        AddParcelDialog addParcelDialog;
        addParcelDialog = new AddParcelDialog(FedexSimulatorGUI.this,
            "Adding Parcel");
        parcelPrint();
      }
      else if (event.getSource().equals(rerouteParcelItem))
      {
        RerouteParcelDialog rerouteParcelDialog;
        rerouteParcelDialog = new RerouteParcelDialog(FedexSimulatorGUI.this,
            "Rerouting Parcel");
        parcelPrint();
      }
      else if (event.getSource().equals(deleteVehicleItem))
      {
        DeleteVehicleDialog deleteVehicleDialog;
        deleteVehicleDialog = new DeleteVehicleDialog(FedexSimulatorGUI.this,
            "Deleting Vehicle");
        vehiclePrint();
      }
      else if (event.getSource().equals(deleteParcelItem))
      {
        DeleteParcelDialog deleteParcelDialog;
        deleteParcelDialog = new DeleteParcelDialog(FedexSimulatorGUI.this,
            "Deleting Parcel");
        parcelPrint();
      }
    } 
  }
  
  
  public class VehicleComboListener implements ActionListener
  {
    
    public void actionPerformed(ActionEvent e)
    {
      JComboBox< String > combo = (JComboBox< String >)e.getSource();
      if (combo.getSelectedItem().equals("Freight Truck"))
        vehicleComboString = "Freight Truck";
      else if (combo.getSelectedItem().equals("Cargo Plane"))
        vehicleComboString = "Cargo Plane";
      else if (combo.getSelectedItem().equals("Delivery Truck"))
        vehicleComboString = "Delivery Truck";
      else if (combo.getSelectedItem().equals("All Vehicles"))
        vehicleComboString = "All Vehicles";
      if (Simulator.getVehicleArray().length != 0)
        vehiclePrint();
    }
  }
  
  public class ParcelComboListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      JComboBox< String > combo = (JComboBox< String >)e.getSource();
      if (combo.getSelectedItem().equals("Low"))
        parcelComboString = "Low";
      else if (combo.getSelectedItem().equals("Medium"))
        parcelComboString = "Medium";
      else if (combo.getSelectedItem().equals("High"))
        parcelComboString = "High";
      else if (combo.getSelectedItem().equals("All Priorities"))
        parcelComboString = "All Priorities";
      if (Simulator.getParcelArray().length != 0)
        parcelPrint();
    }
  }
  
  public void vehiclePrint()
  {
    inVehicleList = Simulator.getVehicleArray();
    vehicleListModel.clear();
    if (vehicleComboString == "All Vehicles")
    {
      for (Vehicle vehicle : inVehicleList) 
      {
        vehicleListModel.addElement(vehicle.toString());
      }
    }
    else
    {
      for (Vehicle vehicle : inVehicleList)
      {
        if (vehicle.getVehicleType() == vehicleComboString)
          vehicleListModel.addElement(vehicle.toString());
      }
    }
  }
  
  public void parcelPrint()
  {
    inParcelList = Simulator.getParcelArray();
    parcelListModel.clear();
    if (parcelComboString == "All Priorities")
    {
      for (Parcel parcel : inParcelList)
        parcelListModel.addElement(parcel.toString());
    }
    else
    {
      for (Parcel parcel : inParcelList)
      {
        if (parcel.getPickupPriorityLevelForDistance(
            Location.getDistanceBetween(
                parcel.getCurrentLocation(),
                parcel.getDestination())) == parcelComboString)
          parcelListModel.addElement(parcel.toString());
      }
    }
  }
  
  public class VehicleListListener extends MouseAdapter
  {
    public void mouseClicked(MouseEvent e)
    {
      if (vehicleList.getSelectedIndex() != -1)
      {
        if (e.getClickCount() == 2)
        {
          String selectVehicle = vehicleList.getSelectedValue().toString();
          Vehicle [] allVehicle = Simulator.getVehicleArray();
          Vehicle theVehicle = null;
          for (Vehicle vehicle : allVehicle)
          {
            if (vehicle.toString().equals(selectVehicle))
            {
              theVehicle = vehicle;
              break;
            }
          }
          VehicleInfoDialog vehicleInfoDialog;
          vehicleInfoDialog = new VehicleInfoDialog(FedexSimulatorGUI.this,
              "Vehicle Information", theVehicle);
        }
      }
    }
  }
  
  
  public class ParcelListListener extends MouseAdapter
  {
    public void mouseClicked(MouseEvent e)
    {
      if (parcelList.getSelectedIndex() != -1)
      {
        if (e.getClickCount() == 2)
        {
          String selectParcel = parcelList.getSelectedValue().toString();
          Parcel [] allParcel = Simulator.getParcelArray();
          Parcel theParcel = null;
          for (Parcel parcel : allParcel)
          {
            if (parcel.toString().equals(selectParcel))
            {
              theParcel = parcel;
              break;
            }
          }
          ParcelInfoDialog parcelInfoDialog;
          parcelInfoDialog = new ParcelInfoDialog(FedexSimulatorGUI.this,
              "Parcel Information", theParcel);
        }
      }
    }
  }
  
  public class ButtonListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      int advanceTick = 1;
      if (e.getSource() == startSimButton)
      {
        try
        {
          if(advanceTickField.getText().length() == 0)
            throw new EmptyTextFieldException();
          if (needReset)
            throw new SimulationResetException();
          
          Simulator.setInitialLists();
        
          advanceTick = Integer.parseInt(advanceTickField.getText());
          outputArea.setText("");
          outputArea.append(Simulator.startSimulation(advanceTick,
              statsBox.isSelected()));
          outputArea.paintImmediately(outputArea.getBounds());
          parcelPrint();
          
          startSimButton.setEnabled(false);
          clearVehicleButton.setEnabled(false);
          clearParcelButton.setEnabled(false);
          resetButton.setEnabled(false);
          advanceSimButton.setEnabled(true);
          endSimButton.setEnabled(true);
          clearOutputButton.setEnabled(true);
          needReset = true;
        }
        catch (NumberFormatException unknownException)
        {
          outputArea.append("Error non-numeric character in number of ticks"
              + " to advanceSimulation simulation field. "
              + "Error: For input string: \"" 
              + advanceTickField.getText() + "\"");
          outputArea.paintImmediately(outputArea.getBounds());
        }
        catch (EmptyTextFieldException emptyException)
        {
          outputArea.append("Error number of ticks to advanceSimulation"
              + " simulation cannot be empty");
          outputArea.paintImmediately(outputArea.getBounds());
        }
        catch (SimulationResetException simResetException)
        {
          JOptionPane.showMessageDialog(null, 
              "Please Reset The Simulation!", 
              "Reset The Simulation", JOptionPane.WARNING_MESSAGE);
        }
      }
      else if (e.getSource() == advanceSimButton)
      {
        try
        {
          if(advanceTickField.getText().length() == 0)
            throw new EmptyTextFieldException();
          advanceTick = Integer.parseInt(advanceTickField.getText());
          outputArea.append(Simulator.advanceSimulation(advanceTick,
              statsBox.isSelected()));
          outputArea.paintImmediately(outputArea.getBounds());
          parcelPrint();
        }
        catch (NumberFormatException unknownException)
        {
          outputArea.append("Error non-numeric character in number of ticks"
              + " to advanceSimulation simulation field. "
              + "Error: For input string: \"" 
              + advanceTickField.getText() + "\"");
          outputArea.paintImmediately(outputArea.getBounds());
        }
        catch (EmptyTextFieldException emptyException)
        {
          outputArea.append("Error number of ticks to advanceSimulation"
              + " simulation cannot be empty");
          outputArea.paintImmediately(outputArea.getBounds());
        }
      }
      else if (e.getSource() == endSimButton)
      {
        outputArea.append(Simulator.endSimulation());
        outputArea.paintImmediately(outputArea.getBounds());
        parcelPrint();
        advanceSimButton.setEnabled(false);
        endSimButton.setEnabled(false);
        clearVehicleButton.setEnabled(false);
        clearParcelButton.setEnabled(false);
        startSimButton.setEnabled(true);
        resetButton.setEnabled(true);        
      }
      else if (e.getSource() == clearOutputButton)
      {
        outputArea.setText("");
      }
      else if (e.getSource() == clearVehicleButton)
      {
        vehicleListModel.clear();
        Simulator.clearVehicleList();
      }
      else if (e.getSource() == clearParcelButton)
      {
        parcelListModel.clear();
        Simulator.clearParcelList();
      }
      else if (e.getSource() == resetButton)
      {
        Simulator.reset();
        parcelTypeCombo.setSelectedIndex(0);
        vehicleTypeCombo.setSelectedIndex(0);
        JOptionPane.showMessageDialog(null, 
            "All Vehicles and Parcels have been reset.", 
            "Reset Message", JOptionPane.INFORMATION_MESSAGE);
        startSimButton.setEnabled(true);
        advanceSimButton.setEnabled(false);
        endSimButton.setEnabled(false);
        clearVehicleButton.setEnabled(true);
        clearParcelButton.setEnabled(true);
        clearOutputButton.setEnabled(true);
        resetButton.setEnabled(false);
        needReset = false;
      }
    }
  }
 
  public static void updateVehicleList( Vehicle[] vehicleArray )
  {
    getVehicleDefaultListModel().clear();
    for ( Vehicle v : vehicleArray )
    {
      getVehicleDefaultListModel().addElement(v.toString());
    }
  }

  /**
   * Updates the DefaultListView of parcels first clearing it and adding the
   * the parcels from the array passed in back to it. This function is called
   * when a reset occurs.
   *
   * @param parcelArray
   *     The parcel array to add to the DefaultListModel.
   */
  public static void updateParcelList( Parcel[] parcelArray )
  {
    getParcelDefaultListModel().clear();
    for ( Parcel p : parcelArray )
    {
      getParcelDefaultListModel().addElement(p.toString());
    }
  }

  // ---------------------------------------------------------------------------



  /**
   * Updates a parcel in the parcel DefaultListModel. If a special view is
   * selected from the ComboBox it will update the current DefaultListModel
   * either adding the parcel to it due to a change in priority or removing it.
   * This function is called from within Parcel when a parcel's priority
   * changes from High to Medium or Medium to Low. It is also called from
   * reroute parcel when rerouting a parcel changes its priority.
   *
   * @param parcel
   *     The parcel to be updated.
   * @param oldPickupPriorityLevel
   *     The parcel's previousPriority to handle updating specialized
   *     views. I.E. only showing medium priority
   *     parcels
   */
  public static void updateParcelList( Parcel parcel,
                                       String oldPickupPriorityLevel )
  {
    for ( int index = 0; index < getParcelDefaultListModel().size();
          ++index )
    {
      String parcelString = getParcelDefaultListModel().elementAt(index);
      int indexOfEquals = parcelString.indexOf('=');
      int idVal;
      try
      {
        idVal = Integer.parseInt(parcelString
                                     .substring(indexOfEquals + 1,
                                                indexOfEquals + 2));
      }
      catch ( NumberFormatException nfe )
      {
        getTextArea().append(String.format(
            "Error updating parcel list. Error: %s\n", nfe.getMessage()));
        return;
      }
      if ( idVal == parcel.getParcelID() )
      {
        getParcelDefaultListModel()
            .setElementAt(parcel.toString(), index);
        if ( getParcelTypeComboBox().getSelectedItem()
            .equals(parcel.toString().substring(
                parcel.toString().lastIndexOf('=') + 1,
                parcel.toString().lastIndexOf(')'))) )
        {
          (( DefaultListModel< String > ) getParcelJList().getModel())
              .addElement(parcel.toString());
        }
        else if ( getParcelTypeComboBox().getSelectedItem()
            .equals(oldPickupPriorityLevel) )
        {
          for ( int i = 0; i < getParcelJList().getModel().getSize();
                ++i )
          {
            parcelString =
                (( DefaultListModel< String > ) getParcelJList()
                    .getModel()).elementAt(i);
            indexOfEquals = parcelString.indexOf('=');
            idVal = Integer.parseInt(parcelString.substring(
                indexOfEquals + 1, indexOfEquals + 2));
            if ( idVal == parcel.getParcelID() )
            {
              (( DefaultListModel< String > ) getParcelJList()
                  .getModel()).removeElementAt(i);
              break;
            }
          }
        }
        break;
      }
    }
  }

  private static JComboBox getParcelTypeComboBox() 
  {
    return parcelTypeCombo;
  }

  private static JList getParcelJList() 
  {
    return parcelList;
  }

  public static JTextArea getTextArea() 
  {
    return outputArea;
  }

  public static DefaultListModel<String> getParcelDefaultListModel() 
  {
    return parcelListModel;
  }
  
  private static DefaultListModel<String> getVehicleDefaultListModel() {
    return vehicleListModel;
  }

  // ---------------------------------------------------------------------------
}
