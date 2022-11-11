/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pl.polsl;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.jktab.model.Listing;
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
    
    private final ObservableList<Listing> listings;
    private final Tab tab;
    
    @FXML
    public void initialize() {
        listingsTable.setItems(listings);
        listingName.setCellValueFactory(new PropertyValueFactory<Listing, String>("title"));
        listingPrice.setCellValueFactory(new PropertyValueFactory<Listing, Float>("price"));

    }
 
    public TabController(Tab _tab) {
        this.tab = _tab;
        listings = FXCollections.observableArrayList(tab.getListings());
        this.tab.getListings().forEach(x -> {
            System.out.println(x.getTitle());
        });
    }
}
