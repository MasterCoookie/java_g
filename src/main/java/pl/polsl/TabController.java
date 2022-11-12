/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pl.polsl;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
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
    
    private final ObservableList<Listing> listings;
    private final Tab tab;
    
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
    }
 
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
                        for (int i = change.getFrom(); i < change.getTo(); ++i) {
                            System.out.println("zamiana");
                        }
                    } else if (change.wasUpdated()) {
                        System.out.println("uaktualnienie");
                    } else {
                        for (var remitem : change.getRemoved()) {
                           
                            System.out.println("Removed");
                        }
                        for (var additem : change.getAddedSubList()) {
//                            persons.getData().add(additem);
                        }
                    }
                }
            }
        });
        
    }
    
    private Stage rowClick(Listing l) {
        System.out.println(l.getTitle());
        
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("listing.fxml"));
        fxmlLoader.setControllerFactory( t -> { return new ListingController(l); });
        
        Stage stage = new Stage();
        try{
            stage.setScene(new Scene(fxmlLoader.load()));
            return stage;
        } catch(IOException e) {
            System.out.println("Except: " + e.getMessage());
            return null;
        }
    }
    
    @FXML
    private void deleteListing() {
        int index = listingsTable.getSelectionModel().getSelectedIndex();
        try {
            if(index != -1) {
                tab.removeListing(index, tab.getUsername(), false);
                listings.remove(index);
            } 
            System.out.println("Index: " + index);
            
            
        } catch (ListingAccessException e) {
            //TODO HANDLE
            System.out.println(e.getMessage());
        }
    }
}
