package com.electra.canbusdemo;

import com.electra.canbusdemo.CANbus.CANbus_Controller;
import com.electra.canbusdemo.CANbus.Notifiable;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Section;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HexFormat;


import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import org.controlsfx.control.*;

import static com.electra.canbusdemo.CANbus.CANbus_Controller.getCanBusController;
import static javafx.geometry.Orientation.VERTICAL;




public class MainViewController implements Notifiable {
    @FXML
    private Button connectButton;
    @FXML
    private Button sendButton;
    @FXML
    private TextArea receivedTextArea;
    @FXML
    private TextArea sentTextArea;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField data0TextField, data1TextField, data2TextField, data3TextField, data4TextField,
            data5TextField, data6TextField, data7TextField;






    @FXML
    private ComboBox<String> deviceComboBox;

    private ObservableList<String> canBusDevice_List = FXCollections.observableArrayList();

    private CANbus_Controller canBusController;
    private String canBusDevice;




    //****************************************************************************//
    // INPUT PANEL
    @FXML
    private ToggleSwitch in_contactor1;
    @FXML
    private ToggleSwitch in_contactor2;
    @FXML
    private ToggleSwitch in_chargerProfile;
    @FXML
    private ToggleSwitch in_controlMode;
    @FXML
    protected ToggleSwitch in_GridResChoice;
    @FXML
    private TextField in_currentTreshold;
    @FXML
    private TextField in_voltageTreshold;
    @FXML
    private TextField in_desiredTorque;
    @FXML
    protected TextField in_desiredVelocity;
    @FXML
    protected CheckBox in_Forward;
    @FXML
    protected CheckBox in_Reverse;
    @FXML
    protected ComboBox<String> in_SportEcoChoices;
    @FXML
    private Gauge in_VelocityTorque;
    @FXML
    private Button in_ScramButton;
    @FXML//protected per usare al di fuori della classe
    protected CheckBox in_ParkingMode;
    @FXML
    protected CheckBox in_ChargingMode;




    //@FXML
   // private Label in_ForwardLabel;
   // @FXML
   // private Label in_ReverseLabel;

    @FXML
    private Label in_Contactor1Label;
    @FXML
    private Label in_Contactor2Label;
    @FXML
    private Label in_CurrentTresholdLabel;
    @FXML
    private Label in_VoltageTresholdLabel;
    @FXML
    private Label in_DesiredTorqueLabel;
    @FXML
    private Label in_DesiredVelocityLabel;
    @FXML
    private Label in_ChargerProfileLabel;
    @FXML
    private Label in_CustomLabel;
    @FXML
    private Label in_Discharge1Label;
    @FXML
    private Label in_Discharge2Label;
    @FXML
    private Label in_AmpereLabel;
    @FXML
    private Label in_VoltLabel;
    @FXML
    private Label in_NmLabel;
    @FXML
    private Label in_VelocityLabel;
    @FXML
    private Label in_KmhLabel;
    @FXML
    private HBox in_TorqueBox;
    @FXML
    private HBox in_VelocityBox;

    private ArrayList<Control> in_Widgets;


   @FXML
   private ToggleButton scram;

    //********************************************************************************//
    // OUTPUT PANEL
    @FXML
    private Gauge out_voltageBatteryPackGauge;
    @FXML
    private Gauge out_currentBatteryPackGauge;
    @FXML
    private Gauge out_voltageChargerGauge;
    @FXML
    private Gauge out_currentChargerGauge;
    @FXML
    private Gauge out_battery;
    @FXML
    private Gauge out_motorTemp;
    @FXML
    private Gauge out_velEngGauge;
    @FXML
    private Gauge out_torqueEngGauge;

    // Use a boolean variable to store the state of the reverse button
    private boolean  reverse = false;



    private final int MAX_SHOW_MEX = 500;
    private int sendID = 0, sendCycle = 0, receiveID = 0, receiveCycle = 0;

    //commit remoto
    private void checkTextFieldInput(KeyEvent keyEvent, TextField textField){
        String newChar = keyEvent.getCharacter().toUpperCase();
        String keyFilter = "0123456789ABCDEF";

        if (!keyFilter.contains(newChar) || textField.getText().length() > 1) {
            keyEvent.consume();
        }
    }


    private void disableTractionMode(MouseEvent mouseEvent, CheckBox checkBox){
      //  if(checkBox.getText().equals("Forward")){
            in_Reverse.setDisable(true);
      //  }
    }



    private BiMap<String,String> pool = HashBiMap.create();


  //  @FXML
  //  private PC_to_VCUMessage pC_to_VCUMessage;

    @FXML
    public void initialize (){



        // IDEA: create an ArrayList of all input objects, so it is easier to perform operation on them as a whole


        canBusDevice_List.addAll();




        deviceComboBox.setItems(canBusDevice_List);


        canBusController = getCanBusController();
        canBusController.setNotifiable(this);



        in_Widgets = new ArrayList<Control>();
        in_Widgets.add (in_controlMode);
        in_Widgets.add (in_contactor1);
        in_Widgets.add (in_contactor2);
        in_Widgets.add (in_Forward);
        in_Widgets.add (in_Reverse);
        in_Widgets.add (in_SportEcoChoices);
        in_Widgets.add (in_chargerProfile);
        in_Widgets.add (in_currentTreshold);
        in_Widgets.add (in_voltageTreshold);
        in_Widgets.add (in_desiredVelocity);
        in_Widgets.add (in_desiredTorque);
        in_Widgets.add (in_VelocityTorque);
        in_Widgets.add (in_ScramButton);
        in_Widgets.add (in_Contactor1Label);
        in_Widgets.add (in_Contactor2Label);
        in_Widgets.add (in_CurrentTresholdLabel);
        in_Widgets.add (in_VoltageTresholdLabel);
        in_Widgets.add (in_ChargerProfileLabel);
        in_Widgets.add (in_DesiredVelocityLabel);
        in_Widgets.add (in_DesiredTorqueLabel);
        in_Widgets.add (in_CustomLabel);
        in_Widgets.add (in_Discharge1Label);
        in_Widgets.add (in_Discharge2Label);
        in_Widgets.add (in_AmpereLabel);
        in_Widgets.add (in_VoltLabel);
        in_Widgets.add (in_NmLabel);
        in_Widgets.add (in_VelocityLabel);
        in_Widgets.add (in_KmhLabel);
        in_Widgets.add (in_ParkingMode);
        in_Widgets.add (in_ChargingMode);
        in_Widgets.add (in_GridResChoice);


        // Loop through the list and disable each widget using a lambda expression
//in_Widgets.forEach(widget -> widget.setDisable(true));
        //setDisableInputsWidgets();


        // Default behaviour
        in_SportEcoChoices.setDisable(true);
        in_GridResChoice.setDisable(true);
        //in_desiredTorque.setDisable(false);
        in_desiredVelocity.setDisable(true);
        in_DesiredVelocityLabel.setDisable(true);

        //in_Forward.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseEvent-> {disableTractionMode(MouseEvent, in_Forward);});
        in_Forward.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseEvent-> {
            //in_Reverse.setDisable(!in_Reverse.isDisabled());
            if(!in_Reverse.isDisabled()) {
                in_Reverse.setDisable(true);
                in_SportEcoChoices.setDisable(false);
            }
            else {
                in_SportEcoChoices.setDisable(true);
                in_Reverse.setDisable(false);
            }
        });
        //in_Reverse.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseEvent-> {
        //        in_Forward.setDisable(!in_Forward.isDisabled());
       // });

        in_Reverse.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseEvent-> {
            if(!in_Forward.isDisabled()) {
                in_Forward.setDisable(true);
                in_SportEcoChoices.setDisable(true);
            }
            else {
                //in_Reverse.setDisable(false);
                in_Forward.setDisable(false);
                in_SportEcoChoices.setDisable(true);
            }
        });



        in_ParkingMode.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseEvent-> {
            if(!in_ChargingMode.isDisabled()) {
                in_ChargingMode.setDisable(true);
                in_GridResChoice.setDisable(true);
            }
            else
                in_ChargingMode.setDisable(false);
        });



        in_ChargingMode.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseEvent-> {
            if(!in_ParkingMode.isDisabled()) {
                in_ParkingMode.setDisable(true);
                in_GridResChoice.setDisable(false);
            }
            else {
                in_GridResChoice.setDisable(true);
                in_ParkingMode.setDisable(false);
            }

        });



        in_controlMode.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
          // if(event.getEventType() == MouseEvent.MOUSE_CLICKED || event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                if (in_controlMode.isPickOnBounds()) {  // isPickOnBounds è quello giusto da usare?????
                    if (!in_desiredTorque.isDisabled()) {
                        //in_desiredTorque.setDisable(true);
                        //  in_DesiredTorqueLabel.setDisable(true);
                        //in_NmLabel.setDisable(true);
                        in_TorqueBox.getChildren().forEach(node -> {
                            // Do something with the node
                            node.setDisable(true);
                        });
                        in_desiredVelocity.setDisable(false);
                        in_DesiredVelocityLabel.setDisable(false);
                        in_KmhLabel.setDisable(false);
                    } else {
                        // in_desiredTorque.setDisable(false);
                        // in_DesiredTorqueLabel.setDisable(false);
                        // in_NmLabel.setDisable(false);
                        in_TorqueBox.getChildren().forEach(node -> {
                            // Do something with the node
                            node.setDisable(false);
                        });
                        in_desiredVelocity.setDisable(true);
                        in_DesiredVelocityLabel.setDisable(true);
                        in_KmhLabel.setDisable(true);
                    }
                }
          //  }
        });





        //in_ScramButton.getStyleClass().add("giuseppe");
        scram.getStyleClass().add("giuseppe");






        in_desiredVelocity.addEventFilter(KeyEvent.KEY_PRESSED, event -> {

                    if (event.getCode() == KeyCode.ENTER) {

                        try {
                            sendMessage(new PC_to_VCUMessage("222"));
                        }
                        catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
        });



       // pC_to_VCUMessage.initParentController(this);

        /*
        in_desiredTorque.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseEvent-> {
            if(!in_desiredTorque.isDisabled())
                in_desiredVelocity.setDisable(true);
            else{
                in_desiredVelocity.setDisable(true);
                in_controlMode.setSelected(false);
                in_desiredTorque.setDisable(false);
            }
        });

        in_desiredVelocity.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseEvent-> {
            if(!in_desiredVelocity.isDisabled()) {
                System.out.println("desiredVElocityTextFieldisisEnabled");
                in_desiredTorque.setDisable(true);
            }
            else{
                System.out.println("desiredVElocityTextFieldisDisabled");
                in_desiredVelocity.setDisable(false);
                in_desiredTorque.setDisable(true);
                in_controlMode.setSelected(true);
            }
        });

         */


/*
// Add a toggle event handler to the reverse button
        in_Reverse.setOnAction(e -> {
            // Flip the value of the reverse variable
            reverse = !reverse;

            // Disable or enable the forward and sport/eco choices buttons based on the reverse state
            in_Forward.setDisable(reverse);
            in_SportEcoChoices.setDisable(reverse);
        });

        */
       // in_Reverse.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseEvent-> {
       //     in_SportEcoChoices.setDisable(!in_Forward.isDisabled());
       // });

        //inizializzazione ComboBox con le opzioni "Sport" "Eco"
        in_SportEcoChoices.getItems().addAll("Sport","Eco");
       // in_SportEcoChoices.setValue("Sport"); // Imposta l'opzione predefinita
       // in_SportEcoChoices.setOnMouseClicked (event -> {
      //     in_SportEcoChoices.getItems().removeAll(canBusDevice_List);
         //  in_SportEcoChoices.getItems().addAll("Sport","Eco");
       // });




        //in_contactor1.setDisable(true);
        //in_contactor2.setDisable(true);

        /*

        in_velocityGauge = GaugeBuilder.create()
               .skinType(Gauge.SkinType.DIGITAL)
               .animated(true)
               .foregroundBaseColor(Color.rgb(0, 222, 249))
               .barColor(Color.rgb(0, 222, 249))
               .title("SLIM")
               .unit("UNIT")
               .build();

        in_velocityGauge.setSkinType(Gauge.SkinType.DIGITAL);
        in_velocityGauge.setAnimated(true);
        in_velocityGauge.setForegroundBaseColor(Color.rgb(0, 150, 255));
        in_velocityGauge.setBarColor(Color.rgb(0, 150, 255));
        in_velocityGauge.setTitle("SLIM");
        in_velocityGauge.setUnit("UNIT");

        out_velocityGauge.setSkinType(Gauge.SkinType.DIGITAL);
        out_velocityGauge.setAnimated(true);
        out_velocityGauge.setForegroundBaseColor(Color.rgb(0, 150, 255));
        out_velocityGauge.setBarColor(Color.rgb(0, 150, 255));
        out_velocityGauge.setTitle("VELOCITY");
        out_velocityGauge.setUnit("UNIT");



        out_coupleGauge.setSkinType(Gauge.SkinType.DIGITAL);
        out_coupleGauge.setAnimated(true);
        out_coupleGauge.setForegroundBaseColor(Color.rgb(0, 150, 255));
        out_coupleGauge.setBarColor(Color.rgb(0, 150, 255));
        out_coupleGauge.setTitle("COPPIA");
        out_coupleGauge.setUnit("UNIT");




        out_motorTemp.setSkinType(Gauge.SkinType.DASHBOARD);
        out_motorTemp.setAnimated(true);
        out_motorTemp.setTitle("TEMPERATURE");
        out_motorTemp.setTitleColor(Color.rgb(0, 150, 255));
        out_motorTemp.setUnit("\u00B0C");
        out_motorTemp.setUnitColor(Color.rgb(0, 150, 255));
        out_motorTemp.setValueColor(Color.rgb(0, 150, 255));
        out_motorTemp.setGradientBarStops(new Stop(0.00, Color.BLUE),
                new Stop(0.25, Color.CYAN),
                new Stop(0.50, Color.LIME),
                new Stop(0.75, Color.YELLOW),
                new Stop(1.00, Color.RED));
        */


        in_VelocityTorque.setSkinType(Gauge.SkinType.GAUGE);
        in_VelocityTorque.setForegroundBaseColor(Color.WHITE);



        out_velEngGauge.setSkinType(Gauge.SkinType.MODERN);
        out_velEngGauge.setGradientBarStops(new Stop(0.0, Color.BLUE),                                     // Color gradient that will be use to color fill bar
                new Stop(0.25, Color.CYAN),
                new Stop(0.5, Color.LIME),
                new Stop(0.75, Color.YELLOW),
                new Stop(1.0, Color.RED));
        out_velEngGauge.setCustomFont(Font.font(".\\Project\\src\\main\\resources\\com\\electra\\canbusdemo\\MesloLGLNerdFont-Regular.ttf"));
        out_velEngGauge.setSections(new Section(85, 90, "", Color.rgb(204, 0, 0, 0.5)),
                new Section(90, 95, "", Color.rgb(204, 0, 0, 0.75)),
                new Section(95, 100, "", Color.rgb(204, 0, 0)));
        out_velEngGauge.setSectionsVisible(true);
        out_velEngGauge.setGradientBarEnabled(true);
       // out_velEngGauge.setForegroundBaseColor(Color.rgb(0, 150, 255));
       // out_velEngGauge.setBarColor(Color.rgb(0, 150, 255));
        out_velEngGauge.setTitle("VELOCITY");
        out_velEngGauge.setUnit("Km/h");
        out_velEngGauge.setAnimated(true);
        out_velEngGauge.setValue(96);


        out_torqueEngGauge.setSkinType(Gauge.SkinType.MODERN);
        out_torqueEngGauge.setSections(new Section(85, 90, "", Color.rgb(204, 0, 0, 0.5)),
                new Section(90, 95, "", Color.rgb(204, 0, 0, 0.75)),
                new Section(95, 100, "", Color.rgb(204, 0, 0)));
        out_torqueEngGauge.setSectionsVisible(true);
        out_torqueEngGauge.setForegroundBaseColor(Color.rgb(0, 150, 255));
        out_torqueEngGauge.setBarColor(Color.rgb(0, 150, 255));
        out_torqueEngGauge.setTitle("TORQUE");
        out_torqueEngGauge.setUnit("Nm");
        out_torqueEngGauge.setAnimated(true);
        out_torqueEngGauge.setValue(40);


        out_motorTemp.setSkinType(Gauge.SkinType.DASHBOARD);
       // out_motorTemp.setPrefSize(400, 400);
        out_motorTemp.setSections(new Section(85, 90, "", Color.rgb(204, 0, 0, 0.5)),
                new Section(90, 95, "", Color.rgb(204, 0, 0, 0.75)),
                new Section(95, 100, "", Color.rgb(204, 0, 0)));
        out_motorTemp.setSectionsVisible(true);
        out_motorTemp.setTitle("TEMPERATURE");
        out_motorTemp.setTitleColor(Color.WHITE);
        out_motorTemp.setUnit("°C");
        out_motorTemp.setUnitColor(Color.WHITE);
        out_motorTemp.setAnimated(true);
        out_motorTemp.setValue(84);
        out_motorTemp.setValueColor(Color.WHITE);

        out_battery.setSkinType(Gauge.SkinType.BATTERY);
        out_battery.setOrientation(VERTICAL);
        out_battery.setAnimated(true);
        out_battery.setSectionsVisible(true);
        out_battery.setSections(new Section(0, 10, Color.rgb(200, 0, 0, 0.8)),
                new Section(10, 30, Color.rgb(200, 200, 0, 0.8)),
                new Section(30, 100, Color.rgb(0, 200, 0, 0.8)));
        out_battery.setValue(20);

        out_voltageBatteryPackGauge.setSkinType(Gauge.SkinType.GAUGE);
        out_voltageBatteryPackGauge.setForegroundBaseColor(Color.WHITE);
        out_voltageBatteryPackGauge.setTitle("VOLTAGE");

        out_currentBatteryPackGauge.setSkinType(Gauge.SkinType.GAUGE);
        out_currentBatteryPackGauge.setForegroundBaseColor(Color.WHITE);
        out_currentBatteryPackGauge.setTitle("CURRENT");


        out_voltageChargerGauge.setSkinType(Gauge.SkinType.GAUGE);
        out_voltageChargerGauge.setForegroundBaseColor(Color.WHITE);
        out_voltageChargerGauge.setTitle("VOLTAGE");


        out_currentChargerGauge.setSkinType(Gauge.SkinType.GAUGE);
        out_currentChargerGauge.setForegroundBaseColor(Color.WHITE);
        out_currentChargerGauge.setTitle("CURRENT");
/*
        gauge8 = GaugeBuilder.create()
                .skinType(Gauge.SkinType.MODERN)
                .prefSize(400, 400)
                .sections(new Section(85, 90, "", Color.rgb(204, 0, 0, 0.5)),
                        new Section(90, 95, "", Color.rgb(204, 0, 0, 0.75)),
                        new Section(95, 100, "", Color.rgb(204, 0, 0)))
                .sectionTextVisible(true)
                .title("MODERN")
                .unit("UNIT")
                .threshold(85)
                .thresholdVisible(true)
                .animated(true)
                .build();
*/
  // rgb(0, 222, 249)
       /* gauge5 = GaugeBuilder.create()
               .skinType(Gauge.SkinType.DASHBOARD)
              .animated(true)
               .title("Dashboard")
               .unit("\u00B0C")
               .barColor(Color.CRIMSON)
               .valueColor(Color.WHITE)
                .titleColor(Color.WHITE)
                .unitColor(Color.WHITE)
               .thresholdVisible(true)
                .threshold(35)
               .shadowsEnabled(true)
                .gradientBarEnabled(true)
                .gradientBarStops(new Stop(0.00, Color.BLUE),
                        new Stop(0.25, Color.CYAN),
                        new Stop(0.50, Color.LIME),
                        new Stop(0.75, Color.YELLOW),
                        new Stop(1.00, Color.RED))
               .build();

        */
        //data0TextField.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
        //   checkTextFieldInput(keyEvent, data0TextField);
        //});

        //data1TextField.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
        //    checkTextFieldInput(keyEvent, data1TextField);
        //});

        //data2TextField.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
        //    checkTextFieldInput(keyEvent, data2TextField);
        //});

        //data3TextField.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
        //    checkTextFieldInput(keyEvent, data3TextField);
        //});

        //data4TextField.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
        //    checkTextFieldInput(keyEvent, data4TextField);
        //});

        //data5TextField.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
        //    checkTextFieldInput(keyEvent, data5TextField);
        //});

        //data6TextField.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
        //    checkTextFieldInput(keyEvent, data6TextField);
        //});

        //data7TextField.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
        //    checkTextFieldInput(keyEvent, data7TextField);
        //});

       // TextField[] dataTextFields = {data0TextField, data1TextField, data2TextField, data3TextField, data4TextField,
       //         data5TextField, data6TextField, data7TextField};

     //   for (TextField dataTextField : dataTextFields) {
    //        dataTextField.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
      //          checkTextFieldInput(keyEvent, dataTextField);// lambda expression
    //        });
    //    }

        deviceComboBox.setOnMouseClicked(event -> {
            canBusDevice_List.removeAll(canBusDevice_List);
            canBusDevice_List.addAll(canBusController.getAvailableHandlers());
        });
    }

   // void setDisableInputsWidgets()  {
   //     for (Control Widget : in_Widgets) {
   //         Widget.setDisable(true);
   //     }
    //}


    @FXML
    void connectButtonAction() {
        if(connectButton.getText().equals("Connect")){
            canBusDevice = deviceComboBox.getValue();
            if (!canBusController.isConnected()) {
                if (canBusDevice == null || canBusDevice.equals("")) {
                        fireAlarm(Alert.AlertType.ERROR, "Warning", "Please select a valid CAN bus adapter Device.");
                        return;
                    }
                }

            if (!canBusController.connect(canBusDevice)) {
                String mex = "Connection with the CANbus failed!!\n";
                mex += "Possible causes:\n";
                mex += "  - the selected device is wrong;\n" +
                        "  - the device is not connected to the USB port;\n\n";
                mex += "If the problem persist, try to unplug and replug the device.";

                fireAlarm(Alert.AlertType.ERROR, "Connection ERROR!!", mex);
                return;
            }

            //sendButton.setDisable(false);
            connectButton.setText("Disconnect");
           // in_Widgets.forEach(widget -> widget.setDisable(false));

        } else {
            canBusController.disconnect();
          //  sendButton.setDisable(true);
            connectButton.setText("Connect");
          //  in_Widgets.forEach(widget -> widget.setDisable(true));
        }

    }

    @FXML
    public void clearAllButtonAction(){
        receiveID = receiveCycle = sendID = sendCycle = 0;
        receivedTextArea.clear();
        sentTextArea.clear();
    }
/*
    @FXML
    public void sendButtonAction() throws Exception {
        byte data[] =
                {
                        (byte) HexFormat.fromHexDigits(data0TextField.getText()),
                        (byte) HexFormat.fromHexDigits(data1TextField.getText()),
                        (byte) HexFormat.fromHexDigits(data2TextField.getText()),
                        (byte) HexFormat.fromHexDigits(data3TextField.getText()),
                        (byte) HexFormat.fromHexDigits(data4TextField.getText()),
                        (byte) HexFormat.fromHexDigits(data5TextField.getText()),
                        (byte) HexFormat.fromHexDigits(data6TextField.getText()),
                        (byte) HexFormat.fromHexDigits(data7TextField.getText())
                };

        canBusController.sendCommand(HexFormat.fromHexDigits(idTextField.getText()), data);
        sendID++;
        if(sendID-(sendCycle*MAX_SHOW_MEX) > MAX_SHOW_MEX){
            sendCycle++;
            sentTextArea.clear();
        }
        sentTextArea.appendText("[" + sendID + "]: " + "ID: " + idTextField.getText().toUpperCase() + " data: " +
                HexFormat.of().formatHex(data).toUpperCase() + "\n");
    }

 */

    private void fireAlarm(Alert.AlertType type, String title, String contentText){
        Alert alert = new Alert(type);
        alert.initOwner(MainApplication.stage);
        alert.setTitle(title);
        alert.setContentText(contentText);
        //alert.setX(owner.getX());
        alert.showAndWait();
    }

    @Override
    public void _notify(String data) {
        Platform.runLater(() -> {
            receiveID++;
            if (receiveID-(receiveCycle*MAX_SHOW_MEX) > MAX_SHOW_MEX){
                receiveCycle++;
                receivedTextArea.clear();
            }
            receivedTextArea.appendText("[" + receiveID + "]: " + data + "\n");
        });
    }

    @FXML
    public void scramButtonAction(){
        if(!scram.isPressed()){
            if(scram.isDisabled()){

                System.out.println("LOREE");
                scram.getStyleClass().add("giuseppe-pressed");
            }
            else {
                System.out.println("GIUSEE");
                scram.getStyleClass().remove("giuseppe-pressed");
                scram.getStyleClass().add("giuseppe");
            }
        }
    }

  /*
    public void sendMessage(canMessage message) throws Exception {
        // Call the constructData method of the message object
        message.constructData();
        // Call the validateInputs method of the message object
        message.validateInputs();
        // Get the data array from the message object
        byte[] data = message.getData();
        // Send the data array to the CAN bus
        canBusController.sendCommand(HexFormat.fromHexDigits(message.getId()), data);
    }

   */


    // Example of usage: send(new PC_to_VCU(argomenti del costruttore))
    // Rewrite the send method to accept a VCU_Message object as a parameter
    public void sendMessage(CanMessage message) throws Exception {
        // Call the constructData method of the message object
        //message.constructData();
        message.constructData(this);
        // Call the validateInputs method of the message object
        message.validateInputs();
        // Get the data array from the message object
        byte[] data = message.getData();
        // Send the data array to the CAN bus
        canBusController.sendCommand(HexFormat.fromHexDigits(message.getId()), data);
    }

}
