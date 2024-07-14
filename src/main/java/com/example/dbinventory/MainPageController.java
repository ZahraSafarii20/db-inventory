package com.example.dbinventory;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.bson.Document;

import java.net.URL;
import java.util.ResourceBundle;

import static sun.management.jmxremote.ConnectorBootstrap.PropertyNames.HOST;
import static sun.management.jmxremote.ConnectorBootstrap.PropertyNames.PORT;


public class MainPageController implements Initializable {


    @FXML
    private MenuItem allBtn;

    @FXML
    private MenuItem clothesBtn;

    @FXML
    private MenuItem jewelleryBtn;

    @FXML
    private MenuItem makeupBtn;

    @FXML
    private MenuButton menuBtn;

    @FXML
    private ListView<?> productsList;

    @FXML
    private Button searchBtn;

    @FXML
    private TextField searchTxt;


    @FXML
    void clickOnAllButton(ActionEvent event) {
        System.out.println("hi");
    }

    @FXML
    void clickOnClothesButton(ActionEvent event) {

    }

    @FXML
    void clickOnJewelleryButton(ActionEvent event) {

    }

    @FXML
    void clickOnMakeupButton(ActionEvent event) {

    }

    @FXML
    void clickOnSearchButton(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}