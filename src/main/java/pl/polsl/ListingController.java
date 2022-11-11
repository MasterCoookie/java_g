/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pl.polsl;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;

import javafx.scene.text.Text;
import pl.polsl.jktab.model.Listing;
/**
 * FXML Controller class
 *
 * @author JK
 */
public class ListingController {
    
    private final Listing listing;

    @FXML
    private Text listiingTitle;
    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public ListingController(Listing _listing) {
        this.listing = _listing;
        this.listiingTitle.setText(this.listing.getTitle());
    }
}
