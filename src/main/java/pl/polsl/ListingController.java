/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pl.polsl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.*;
import javafx.stage.Stage;
import pl.polsl.jktab.model.Listing;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;


/**
 * FXML Controller class
 *
 * @author Jan
 */
public class ListingController {
    private final String username;
    private final Listing listing;
    @FXML
    private Text listingTitle;
    @FXML
    private TextFlow listingDesc;
    @FXML
    private Text listingPrice;
    @FXML
    private TextField qrAddress;
    @FXML
    private Text soldBy;
    @FXML
    private Text contactInfo;
    @FXML
    private Button showContactInfoButton;
    @FXML
    private Shape status;
    /**
     * Initializes the controller class.
     */
    public ListingController(Listing _listing, String _username) {
       this.username = _username;
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
        String str = new String(df.format(this.listing.getPrice()));
        this.listingPrice.setText(str + (this.listing.isNegotiable() ? ", Negotiable" : ""));
        
        soldBy.setText(this.listing.getAuthorUname());
        
        var color = Color.RED;
        if(this.listing.isAvilable()) {
            color = Color.GREEN;
        } else if(this.listing.isClaimed()) {
            color = Color.ORANGE;
        }
        this.status.setFill(color);
    }
    
    @FXML
    public void generateQR() {
        try {
            String qrString = this.listing.generateCode(this.username, this.qrAddress.getText());
            this.showQR(qrString);
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.show();
        }
    }
    
    private void showQR(String qrString) {
        ByteArrayOutputStream out = QRCode.from(qrString).to(ImageType.PNG).withSize(200, 200).stream();
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        
        Stage stage = new Stage();
        
        BorderPane root = new BorderPane();
        Image image = new Image(in);
        ImageView view = new ImageView(image);
        view.setStyle("-fx-stroke-width: 2; -fx-stroke: blue");
        root.setCenter(view);
        Scene scene = new Scene(root, 200, 200);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void showContactInfo() {
        this.contactInfo.setText(this.listing.getAuthorContact());
        this.showContactInfoButton.setVisible(false);
    }
}
