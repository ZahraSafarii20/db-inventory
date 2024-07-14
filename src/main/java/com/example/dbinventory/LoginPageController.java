package com.example.dbinventory;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.bson.Document;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class LoginPageController implements Initializable {

    private final static String HOST = "localhost";
    private final static int PORT = 27017;
    public static String userNamE;

    @FXML
    private Button close;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    public static MongoClient mongoclient = new MongoClient(HOST, PORT);
    public static MongoDatabase db = mongoclient.getDatabase("Inventory");
    public static MongoCollection<Document> loginInfo = db.getCollection("loginInfo");

    @FXML
    public void close(){
        System.exit(0);
    }


    public void EnterDashboard() throws IOException{
        try{
            Parent backParent = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            Scene backScene = new Scene(backParent);
            Stage window = (Stage)loginBtn.getScene().getWindow();

            window.setScene(backScene);

            backParent.setOnMousePressed((MouseEvent event) -> {
                x = event.getSceneX();
                y = event.getSceneY();
            } );
            backParent.setOnMouseDragged((MouseEvent event) -> {
                window.setX(event.getScreenX() - x);
                window.setY(event.getScreenY() - y);
            } );
            window.initStyle(StageStyle.TRANSPARENT);

            window.show();


        }catch(IOException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("failed to load dashboard");
            alert.showAndWait();
        }
    }

    public void clickedOnLogin(ActionEvent event) throws IOException {
        String pass = password.getText();
        String user = username.getText();
        userNamE = user;
        boolean athrize = false;
//        boolean filled = true;
        Alert alert;


        if(user.isEmpty() || pass.isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Maessage");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
//            filled = false
        }else{

            MongoCursor<Document> cursor2 = loginInfo.find().iterator();
            try {
                while(cursor2.hasNext()){
                    Document document = cursor2.next();
                    if (document.getString("username").equals(user)
                        && document.getString("password").equals(pass)) {
                        athrize = true;
                    }
                }

            }finally {
                cursor2.close();
            }
            if(athrize) {
                //if correct username and  password, then proceed to dashboard form
                System.out.println("User with name " + user + " and password " + pass + " authorized.");

                EnterDashboard();
//                loginBtn.getScene().getWindow().hide();

//
//                Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
//                Stage stage = new Stage();
//                Scene scene = new Scene(root);
//                stage.setScene(scene);
//                stage.show();
            }else{
                //if wrong then the message will appear
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Maessage");
                alert.setHeaderText(null);
                alert.setContentText("No such account exists.");
                alert.showAndWait();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    private double x = 0;
    private double y = 0;
}

