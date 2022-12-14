/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pl.polsl;

import java.io.IOException;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pl.polsl.jktab.model.Listing;
import pl.polsl.jktab.model.ListingAccessException;
import pl.polsl.jktab.model.Tab;

/**
 * FXML Controller class
 *
 * @author JK
 * @version f3.0
 */
public class TabController {

    @FXML
    private TableView listingsTable;
    @FXML
    private TableColumn listingName;
    @FXML
    private TableColumn listingPrice;
    @FXML
    private TableColumn postedBy;
    @FXML
    private Button deleteListingBtn;
    @FXML
    private Button createListingBtn;
    @FXML
    private Button closeAll;
    @FXML
    private TextField createListingTitle;
    @FXML
    private TextField createListingPrice;
    @FXML
    private CheckBox createListingNegotiable;
    @FXML
    private TextArea createListingDesc;
    @FXML
    private Text createNewText;
    @FXML
    private Text alreadySellingText;
    
    private final ObservableList<Listing> listings;
    private final Tab tab;
    
    /**
     * initializes application GUI values based on 
     * TAB class instance passed from constructor
     */    
    @FXML
    public void initialize() {
        listingsTable.setItems(listings);
        listingName.setCellValueFactory(new PropertyValueFactory<Listing, String>("title"));
        listingPrice.setCellValueFactory(new PropertyValueFactory<Listing, Float>("price"));
        postedBy.setCellValueFactory(new PropertyValueFactory<Listing, String>("authorUname"));
        
        listingsTable.setRowFactory(rv -> {
            TableRow<Listing> row = new TableRow();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty()
                     && event.getButton()==MouseButton.PRIMARY 
                     && event.getClickCount() == 2) {

                    Listing clickedRow = row.getItem();
                    rowClick(clickedRow).show();
                }
            });
            return row ;
        });
        
        this.updateSellingText();
    }
    
    /**
     * Creates new controller based on Tab instance recieved in main app. 
     * It creates observableArrayList which is used to display listings 
     * inside a table. Also attaches change listener to it.
     * @param _tab Tab instance recieved
     */
    public TabController(Tab _tab) {
        this.tab = _tab;
        this.tab.setUsername("JK");
        this.tab.setContact("123456789");
        
        listings = FXCollections.observableArrayList(tab.getListings());
        
        listings.addListener(new ListChangeListener<Listing>(){
            @Override
            public void onChanged(ListChangeListener.Change<? extends Listing> change) {
                while (change.next()) {
                    if (change.wasPermutated()) {
                    } else if (change.wasUpdated()) {
                    } else {
                        for (var remitem : change.getRemoved()) {
                            updateSellingText();
                        }                        
                        for (var additem : change.getAddedSubList()) {
                            tab.addListing(additem, false);
                            updateSellingText();
                        }
                    }
                }
            }
        });
        
    }
    
    /**
     * Generates new Stage class instance based on row in table clicked     * 
     * @param l clicked listing
     * @return new Listing stage, ready to be displayed
     * @see pl.polsl.ListingController
     */
    private Stage rowClick(Listing l) {
        
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("listing.fxml"));
        fxmlLoader.setControllerFactory( t -> { return new ListingController(l, this.tab.getUsername()); });
        
        Stage stage = new Stage();
        stage.setTitle("Listing " + l.getTitle());
        stage.getIcons().add(new Image("https://i.pinimg.com/736x/ba/92/7f/ba927ff34cd961ce2c184d47e8ead9f6.jpg"));
        try{
            stage.setScene(new Scene(fxmlLoader.load()));
            return stage;
        } catch(IOException e) {
//            System.out.println("Except: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * removes listing from table, 
     * can display alert
     */
    @FXML
    private void deleteListing() {
        int observableListindex = listingsTable.getSelectionModel().getSelectedIndex();
        if(observableListindex != -1) {
            var l = this.listings.get(observableListindex);
            int tabIndex = tab.getListings().indexOf(l);
            try {
             if(tabIndex != -1) {
                Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + l.getTitle() + "?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    tab.removeListing(tabIndex, tab.getUsername(), false);
                    listings.remove(observableListindex);
                }
             } else {
//                 System.out.println("tabIndex: " + tabIndex);
             }
            } catch (ListingAccessException e) {
                Alert alert = new Alert(AlertType.ERROR, "You cannot delete this listing!", ButtonType.OK);
                alert.show();
            }
            
        } 
//        System.out.println("observableListindex: " + observableListindex);
    }
    
    /**
     * creates new listing and inserts it into table
     */
    @FXML
    private void createListing() {
        try {
            float price = Float.parseFloat(this.createListingPrice.getText());
            var l = new Listing(
                    this.createListingTitle.getText(),
                    price,
                    this.createListingDesc.getText(),
                    this.createListingNegotiable.selectedProperty().get(),
                    this.tab.getUsername(),
                    this.tab.getContact()
            );
            listings.add(l);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.show();
        }
    }
    
    /**
     * closes all users listings
     */
    @FXML
    private void closeAll() {
        this.tab.closeUserListings();
    }
    
    /**
     * updates text which is displayed above listing creation form 
     */
    private void updateSellingText() {
        var userListings = this.tab.generateUserListingsNames();
        if(userListings.size() > 0) {
            this.createNewText.setText("How about you sell more?");
            if(userListings.size() > 3){
                userListings = userListings.stream()
                .limit(3)
                .collect(Collectors.toList());
            }
            String listingsStr = String.join(", ", userListings);
            this.alreadySellingText.setText("Selling " + listingsStr);
        } else {
            this.alreadySellingText.setText("");
            this.createNewText.setText("Create new listing");
        }
    }
}
