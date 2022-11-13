/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pl.polsl;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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
    @FXML
    private TextFlow listingDesc;
    @FXML
    private Text listingPrice;
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
        Text text = new Text(this.listing.getDesc());
        text.setFill(Color.BLACK);
        text.setFont(Font.font("Helvetica", FontPosture.ITALIC, 15));
        this.listingDesc.getChildren().add(text);
        
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
 
        this.listingPrice.setText(df.format(this.listing.getPrice()));
    }
}
