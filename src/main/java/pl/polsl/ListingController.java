/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pl.polsl;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import pl.polsl.jktab.model.Listing;

/**
 * FXML Controller class
 *
 * @author Jan
 */
public class ListingController {
    private final Listing listing;
    @FXML
    private Text listingTitle;
    /**
     * Initializes the controller class.
     */
    public ListingController(Listing _listing) {
       this.listing = _listing;
       System.out.println(this.listing.getPrice());
    }
    
    @FXML
    public void initialize() {
        this.listingTitle.setText(this.listing.getTitle());
    }
}
