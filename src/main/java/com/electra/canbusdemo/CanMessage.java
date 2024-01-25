package com.electra.canbusdemo;



import javafx.scene.control.CheckBox;

import static java.lang.Integer.TYPE;
import static java.lang.Integer.parseInt;

// Define an abstract class
//public  abstract class canMessage extends MainViewController{
// Definizione di una classe astratta CanMessage
public abstract class CanMessage {

    // Declare common attributes
    // Dichiarazione di attributi comuni protetti

    protected String id;
    protected byte[] data;




    // Define a constructor that takes the message id
    // Costruttore che prende l'id del messaggio
    public CanMessage(String id) {
        this.id = id;
        // Initialize an empty byte array for data
        // Inizializzazione di un array di byte vuoto per i dati
        this.data = new byte[8];
    }


    // Define an abstract method to construct the data array
    //public abstract void constructData();
    // Metodo astratto per costruire l'array di dati
    public abstract void constructData(MainViewController controller);


    // Define an abstract method to validate the inputs
    // Metodo astratto per validare gli input
    public abstract void validateInputs() throws Exception;

    // Define a getter method for the data array
    // Getter per ottenere l'array di dati
    public byte[] getData() {
        return this.data;
    }

    // Getter per ottenere l'id del messaggio
    public String getId() {
        return this.id;
    }

  //  protected MainViewController parentController;
  //  public void initParentController(MainViewController controller) {
   //     parentController = controller;
   // }


}


// Define a subclass for VCU_Velocity message
// Definizione di una sottoclasse PC_to_VCUMessage che estende CanMessage

class PC_to_VCUMessage extends CanMessage {

// Costruttore che chiama il costruttore della classe madre e inizializza eventuali attributi specifici
    public PC_to_VCUMessage(String id) {
        // Call the superclass constructor
        super(id);

        /*
        if(in_desiredVelocity != null)
        {
            System.out.println("Non è null");
            System.out.println(in_desiredVelocity.getText());
        }

         */

        // Assign the specific attributes
      //  this.forwardReverseSwitchButtonDisabled = forwardReverseSwitchButtonDisabled;
      //  this.forwardReverseSwitchButtonSelected = forwardReverseSwitchButtonSelected;
      //  this.contattore1SwitchButtonSelected = contattore1SwitchButtonSelected;
      //  this.velocita = velocita;
    }


    // Override the constructData method
    @Override
    //public void constructData() {
    // Override del metodo astratto constructData per costruire l'array di dati
    public void constructData(MainViewController controller) {
        // Costruzione dell'array di dati usando dati specifici da MainViewController
        data[0] = (byte) 0;
        data[1] = (byte) 0;
        data[2] = (byte) 0;
        // data[3] = (byte) parseInt("50");
        data[3] = (byte) parseInt(controller.in_desiredVelocity.getText());
        //data[3] = (byte) parseInt(in_desiredVelocity.getText());
        data[4] = (byte) 0;
        data[5] = (byte) 0;
        data[6] = (byte) 0;
        data[7] = (byte) 0;

        //

    }
    // Implementazione vuota del metodo astratto validateInputs
    public  void validateInputs() throws Exception{

        // Implementazione della validazione degli input (vuota nell'esempio)

    }
}



  class Parking_CharcingMode extends CanMessage {



    public Parking_CharcingMode(String id) {
        super(id);

    }

    @Override
    public void constructData(MainViewController controller) {
        // Costruzione dell'array di dati usando dati specifici da MainViewController
        data[0] = (byte) 0;
        data[1] = (byte) 0;

        data[2] = (byte) (controller.in_ParkingMode.isSelected() ? 1 : 0); // Modalità di parcheggio
        data[3] = (byte) 0;
        data[4] = (byte) (controller.in_ChargingMode.isSelected() ? 1 : 0);  // Modalità di carica

        // Se la modalità di carica è attiva, controlla il valore di in_GridResChoice
        data[5] = (byte) (controller.in_GridResChoice.isSelected() ? 1 : 0); // Grid o Res
        data[6] = (byte) 0;
        data[7] = (byte) 0;
    }

    // Implementazione vuota del metodo astratto validateInputs
    public void validateInputs() throws Exception {
        // Implementazione della validazione degli input
    }
}





