package com.example.dbinventory;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.bson.Document;

import java.io.File;
import java.net.URL;
import java.util.*;

public class DashboardController implements Initializable {
    private double x = 0;
    private double y = 0;

    private final static String HOST = "localhost";
    private final static int PORT = 27017;



    @FXML
    private Button addProducts_addBtn;

    @FXML
    private TextField addProducts_brand;

    @FXML
    private Button addProducts_btn;

    @FXML
    private TableView<Map<String, String>> addProducts_tableView;
    @FXML
    private TableColumn<Map<String, String>, String> addProducts_col_productId;
    @FXML
    private TableColumn<Map<String, String>, String> addProducts_col_type;
    @FXML
    private TableColumn<Map<String, String>, String> addProducts_col_brand;
    @FXML
    private TableColumn<Map<String, String>, String> addProducts_col_productName;
    @FXML
    private TableColumn<Map<String, String>, String> addProducts_col_quantity;
    @FXML
    private TableColumn<Map<String, String>, String> addProducts_col_price;

    private ObservableList<Map<String, String>> productList;



    @FXML
    private Button addProducts_deleteBtn;

    @FXML
    private AnchorPane addProducts_form;

    @FXML
    private ImageView addProducts_imageView;

    @FXML
    private Button addProducts_importBtn;

    @FXML
    private TextField addProducts_price;

    @FXML
    private TextField addProducts_productId;

    @FXML
    private TextField addProducts_productName;

    @FXML
    private ComboBox<String> addProducts_productType;

    @FXML
    private Button addProducts_resetBtn;

    @FXML
    private TextField addProducts_search;

    @FXML
    private TextField addProducts_quantity;

    @FXML
    private Button addProducts_updateBtn;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private Label home_availableProducts;

    @FXML
    private Button home_btn;

    @FXML
    private AnchorPane home_form;

    @FXML
    private AreaChart<?, ?> home_incomeChart;

    @FXML
    private Label home_numberOrder;

    @FXML
    private BarChart<?, ?> home_orderChart;

    @FXML
    private Label home_totalIncome;

    @FXML
    private FontAwesomeIcon logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField orders_amount;

    @FXML
    private Label orders_balance;

    @FXML
    private ComboBox<String> orders_brand;

    @FXML
    private Button orders_btn;

    @FXML
    private TableView<Map<String, String>> orders_tableView;

    @FXML
    private TableColumn<Map<String, String>, String> orders_col_type;

    @FXML
    private TableColumn<Map<String, String>, String> orders_col_brand;

    @FXML
    private TableColumn<Map<String, String>, String> orders_col_productName;

    @FXML
    private TableColumn<Map<String, String>, Integer> orders_col_quantity;

    @FXML
    private TableColumn<Map<String, String>, Double> orders_col_price;

    @FXML
    private AnchorPane orders_form;

    @FXML
    private Button orders_payBtn;

    @FXML
    private Button orders_addBtn;

    @FXML
    private ComboBox<String> orders_productName;

    @FXML
    private ComboBox<String> orders_productType;

    @FXML
    private Spinner<Integer> orders_quantity;

    @FXML
    private ComboBox<String> orders_customerName;

    @FXML
    private Label orders_total;

    @FXML
    private Label username;

    private Image image;
    private File imageFile;
    private String imagePath;
    private Map<String, String> selectedProduct;

    private ObservableList<Map<String, String>> orderList;

    public static MongoClient mongoclient = new MongoClient(HOST, PORT);
    public static MongoDatabase db = mongoclient.getDatabase("Inventory");
    public static MongoCollection<Document> productCollection = db.getCollection("product");
    public static MongoCollection<Document> orderCollection = db.getCollection("orders");
    public static MongoCollection<Document> customerCollection = db.getCollection("customer");


    public void importImageClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(main_form.getScene().getWindow());
        if (file != null) {

            image = new Image(file.toURI().toString() ,106 , 115 , false, true);
            addProducts_imageView.setImage(image);
            imageFile = file;
            imagePath = file.getAbsolutePath();
        }
    }


    public void onResetBtnClicked(ActionEvent event) {
        addProducts_productId.setText("");
        addProducts_productType.getSelectionModel().clearSelection();
        addProducts_brand.setText("");
        addProducts_productName.setText("");
        addProducts_price.setText("");
        addProducts_quantity.setText("");
        addProducts_imageView.setImage(null);
        imagePath="";
        productCollection.deleteMany(new Document());
        addProducts_tableView.getItems().clear();
    }

//    public void addProductBtnClicked(){
//        boolean taken = false;
//        MongoCursor<Document> cursor2 = productCollection.find().iterator();
//        try {
//            while (cursor2.hasNext()) {
//                Document document = cursor2.next();
//                if (document.getString("product_id").equals(addProducts_productId.getText())) taken = true;
//            }
//        }
//        finally {
//            cursor2.close();
//        }
//        if (taken){
//            addProducts_productId.setText("product already exists, choose another name");
//        }else {
//            Document newProduct = new Document("product_id",addProducts_productId.getText());
//            newProduct.append("type", addProducts_productType.getValue());
//            newProduct.append("brand", addProducts_brand.getText());
//            newProduct.append("productName", addProducts_productName.getText());
//            newProduct.append("quantity", addProducts_quantity.getText());
//            newProduct.append("price", addProducts_price.getText());
//            //newProduct.append("image",addProducts_imageView.getImage());
//            if (imagePath != null){
//                newProduct.append("imagePath" , imagePath);
//            }
//            productCollection.insertOne(newProduct);
//            // Load the new product data into the TableView
//            loadProductData(newProduct);
//        }
//        addProducts_productId.setText("");
//        addProducts_productType.getSelectionModel().clearSelection();
//        addProducts_brand.setText("");
//        addProducts_productName.setText("");
//        addProducts_price.setText("");
//        addProducts_quantity.setText("");
//        addProducts_imageView.setImage(null);
//        imagePath="";
//    }
    public void addProductBtnClicked() {
        if (addProducts_productId.getText().isEmpty() ||
                addProducts_productType.getValue() == null ||
                addProducts_brand.getText().isEmpty() ||
                addProducts_productName.getText().isEmpty() ||
                addProducts_price.getText().isEmpty() ||
                addProducts_quantity.getText().isEmpty()) {

            showAlert("Missing Information", "Please fill in all the required fields.");
            return;
        }

        boolean taken = false;
        MongoCursor<Document> cursor2 = productCollection.find().iterator();
        try {
            while (cursor2.hasNext()) {
                Document document = cursor2.next();
                if (document.getString("product_id").equals(addProducts_productId.getText())) {
                    taken = true;
                    break;
                }
            }
        } finally {
            cursor2.close();
        }

        if (taken) {
            showAlert("Product Exists", "Product with the same ID already exists. Please choose another ID.");
        } else {
            Document newProduct = new Document("product_id", addProducts_productId.getText());
            newProduct.append("type", addProducts_productType.getValue());
            newProduct.append("brand", addProducts_brand.getText());
            newProduct.append("productName", addProducts_productName.getText());
            newProduct.append("quantity", addProducts_quantity.getText());
            newProduct.append("price", addProducts_price.getText());
            if (imagePath != null) {
                newProduct.append("imagePath", imagePath);
            }
            productCollection.insertOne(newProduct);

            // Load the new product data into the TableView
            loadProductData(newProduct);
        }

        // Clear all fields after adding the product
        addProducts_productId.clear();
        addProducts_productType.getSelectionModel().clearSelection();
        addProducts_brand.clear();
        addProducts_productName.clear();
        addProducts_price.clear();
        addProducts_quantity.clear();
        addProducts_imageView.setImage(null);
        imagePath = "";
    }


    public void updateProductBtnClicked() {
        if (selectedProduct != null) {
            // Create a new Document for the updated product
            Document updatedProductDoc = new Document("product_id", addProducts_productId.getText());
            updatedProductDoc.append("type", addProducts_productType.getValue());
            updatedProductDoc.append("brand", addProducts_brand.getText());
            updatedProductDoc.append("productName", addProducts_productName.getText());
            updatedProductDoc.append("quantity", addProducts_quantity.getText());
            updatedProductDoc.append("price", addProducts_price.getText());

            if (imagePath != null) {
                updatedProductDoc.append("imagePath", imagePath);
            }

            // Update the product in MongoDB
            Document query = new Document("product_id", selectedProduct.get("product_id"));
            productCollection.replaceOne(query, updatedProductDoc);

            // Create a Map from the updated Document
            Map<String, String> updatedProduct = new HashMap<>();
            updatedProduct.put("product_id", updatedProductDoc.getString("product_id"));
            updatedProduct.put("type", updatedProductDoc.getString("type"));
            updatedProduct.put("brand", updatedProductDoc.getString("brand"));
            updatedProduct.put("productName", updatedProductDoc.getString("productName"));
            updatedProduct.put("quantity", updatedProductDoc.getString("quantity"));
            updatedProduct.put("price", updatedProductDoc.getString("price"));
            if (updatedProductDoc.containsKey("imagePath")) {
                updatedProduct.put("imagePath", updatedProductDoc.getString("imagePath"));
            }

            // Update the productList and refresh the TableView
            int selectedIndex = addProducts_tableView.getSelectionModel().getSelectedIndex();
            productList.set(selectedIndex, updatedProduct);
            addProducts_tableView.refresh();
        }
        addProducts_productId.setText("");
        addProducts_productType.getSelectionModel().clearSelection();
        addProducts_brand.setText("");
        addProducts_productName.setText("");
        addProducts_price.setText("");
        addProducts_quantity.setText("");
        addProducts_imageView.setImage(null);
        imagePath="";
    }

    public void deleteProductBtnClicked() {
        if (selectedProduct != null) {
            Document query = new Document("product_id", selectedProduct.get("product_id"));
            productCollection.deleteOne(query);

            // Remove the product from the productList and refresh the table view
            productList.remove(selectedProduct);
            addProducts_tableView.refresh();
        }
        addProducts_productId.setText("");
        addProducts_productType.getSelectionModel().clearSelection();
        addProducts_brand.setText("");
        addProducts_productName.setText("");
        addProducts_price.setText("");
        addProducts_quantity.setText("");
        addProducts_imageView.setImage(null);
        imagePath="";
    }



    private void loadAllProductData() {
        MongoCursor<Document> cursor = productCollection.find().iterator();
        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Map<String, String> product = new HashMap<>();
                product.put("product_id", doc.getString("product_id"));
                product.put("type", doc.getString("type"));
                product.put("brand", doc.getString("brand"));
                product.put("productName", doc.getString("productName"));
                product.put("quantity", doc.getString("quantity"));
                product.put("price", doc.getString("price"));
                product.put("imagePath", doc.getString("imagePath"));  // Adding imagePath to the product map
                productList.add(product);
            }
        } finally {
            cursor.close();
        }
        addProducts_tableView.setItems(productList);
    }

    private void loadProductData(Document newProductDoc) {
        // Create a map from the Document and add it to the ObservableList
        Map<String, String> product = new HashMap<>();
        product.put("product_id", newProductDoc.getString("product_id"));
        product.put("type", newProductDoc.getString("type"));
        product.put("brand", newProductDoc.getString("brand"));
        product.put("productName", newProductDoc.getString("productName"));
        product.put("quantity", newProductDoc.getString("quantity"));
        product.put("price", newProductDoc.getString("price"));
        product.put("imagePath", newProductDoc.getString("imagePath"));  // Adding imagePath to the product map

        // Add the new product to the ObservableList
        productList.add(product);

        // Refresh the TableView to display the new product
        addProducts_tableView.refresh();
    }


    public void switchForm(ActionEvent event){
        if (event.getSource() == home_btn){
            home_form.setVisible(true);
            addProducts_form.setVisible(false);
            orders_form.setVisible(false);
            home_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #25a473 ,#89892b)");
            addProducts_btn.setStyle("-fx-background-color:transparent");
            orders_btn.setStyle("-fx-background-color:transparent");
        }else if(event.getSource() == addProducts_btn){
            home_form.setVisible(false);
            addProducts_form.setVisible(true);
            orders_form.setVisible(false);
            home_btn.setStyle("-fx-background-color:transparent");
            addProducts_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #25a473 ,#89892b)");
            orders_btn.setStyle("-fx-background-color:transparent");
        }else if (event.getSource() == orders_btn){
            home_form.setVisible(false);
            addProducts_form.setVisible(false);
            orders_form.setVisible(true);
            home_btn.setStyle("-fx-background-color:transparent");
            addProducts_btn.setStyle("-fx-background-color:transparent");
            orders_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #25a473 ,#89892b)");
        }
    }

    public void logout(){
        try {
            logout.getScene().getWindow().hide();

            Parent root = FXMLLoader.load(getClass().getResource("homepage.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            root.setOnMousePressed((MouseEvent event) -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent event) -> {
                stage.setX((event.getScreenX() - x));
                stage.setY((event.getScreenY()) - y);
            });

            root.setOnMouseReleased((MouseEvent event) -> {
                stage.setOpacity(1);
            });

            stage.initStyle(StageStyle.TRANSPARENT);

            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void minimize(){
        Stage stage = (Stage)main_form.getScene().getWindow();
        stage.setIconified(true);

    }

    public void close(){
        System.exit(0);
    }

    public void searchProducts() {
        String searchText = addProducts_search.getText();

        // Perform a MongoDB query based on the search input
        Document query = new Document();
        if (searchText != null && !searchText.isEmpty()) {
            query.append("$or", Arrays.asList(
                    new Document("product_id", new Document("$regex", searchText).append("$options", "i")),
                    new Document("type", new Document("$regex", searchText).append("$options", "i")),
                    new Document("brand", new Document("$regex", searchText).append("$options", "i")),
                    new Document("productName", new Document("$regex", searchText).append("$options", "i")),
                    new Document("quantity", new Document("$regex", searchText).append("$options", "i")),
                    new Document("price", new Document("$regex", searchText).append("$options", "i"))
            ));
        }

        MongoCursor<Document> cursor = productCollection.find(query).iterator();
        productList.clear();
        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Map<String, String> product = new HashMap<>();
                product.put("product_id", doc.getString("product_id"));
                product.put("type", doc.getString("type"));
                product.put("brand", doc.getString("brand"));
                product.put("productName", doc.getString("productName"));
                product.put("quantity", doc.getString("quantity"));
                product.put("price", doc.getString("price"));
                product.put("imagePath", doc.getString("imagePath"));
                productList.add(product);
            }
        } finally {
            cursor.close();
        }
        addProducts_tableView.setItems(productList);
    }

    public void loadProductTypes() {
        List<String> productTypes = Arrays.asList("Jewelry", "Clothes", "Makeup");
        orders_productType.setItems(FXCollections.observableArrayList(productTypes));
    }


    public void loadProductBrands() {
        List<String> productBrands = new ArrayList<>();
        MongoCursor<String> cursor = productCollection.distinct("brand", String.class).iterator();
        try {
            while (cursor.hasNext()) {
                productBrands.add(cursor.next());
            }
        } finally {
            cursor.close();
        }
        orders_brand.setItems(FXCollections.observableArrayList(productBrands));
    }

    public void loadProductNames() {
        updateProductNames();
    }

    public void updateProductNames() {
        if (orders_productType.getValue() != null && orders_brand.getValue() != null) {
            List<String> productNames = new ArrayList<>();
            Document query = new Document("type", orders_productType.getValue())
                    .append("brand", orders_brand.getValue());
            MongoCursor<String> cursor = productCollection.distinct("productName", query, String.class).iterator();
            try {
                while (cursor.hasNext()) {
                    productNames.add(cursor.next());
                }
            } finally {
                cursor.close();
            }
            orders_productName.setItems(FXCollections.observableArrayList(productNames));
        } else {
            orders_productName.setItems(FXCollections.observableArrayList());
        }
    }

    public void loadCustomerNames() {
        List<String> customerNames = new ArrayList<>();
        MongoCursor<Document> cursor = customerCollection.find().iterator();
        try {
            while (cursor.hasNext()) {
                Document customerDoc = cursor.next();
                String firstName = customerDoc.getString("first_name");
                String lastName = customerDoc.getString("last_name");
                String fullName = firstName + " " + lastName;
                customerNames.add(fullName);
            }
        } finally {
            cursor.close();
        }
        orders_customerName.setItems(FXCollections.observableArrayList(customerNames));
    }

    public void orders_addBtnClicked() {

        String type = orders_productType.getValue();
        String brand = orders_brand.getValue();
        String productName = orders_productName.getValue();
        Integer quantity = orders_quantity.getValue();

        if (type != null && brand != null && productName != null && quantity != null) {
            Document query = new Document("type", type)
                    .append("brand", brand)
                    .append("productName", productName);
            Document productDoc = productCollection.find(query).first();

            if (productDoc != null) {
                int availableStock = Integer.parseInt(productDoc.getString("quantity"));

                if (quantity <= availableStock) {
                    Map<String, String> orderProduct = new HashMap<>();
                    orderProduct.put("type", productDoc.getString("type"));
                    orderProduct.put("brand", productDoc.getString("brand"));
                    orderProduct.put("productName", productDoc.getString("productName"));
                    orderProduct.put("quantity", quantity.toString());
                    orderProduct.put("price", productDoc.getString("price"));

                    orderList.add(orderProduct);
                    orders_tableView.refresh();
                    calculateTotalOrderAmount();
                } else {
                    showAlert("Not Enough Stock", "The selected quantity exceeds the available stock for this product.");
                }
            } else {
                showAlert("Product not found", "No product matches the selected criteria.");
            }
        } else {
            showAlert("Missing Information", "Please select product type, brand, and name.");
        }
        clearProductFields();
    }

    private void calculateTotalOrderAmount() {
        double totalAmount = 0.0;
        for (Map<String, String> product : orderList) {
            double price = Double.parseDouble(product.get("price"));
            int quantity = Integer.parseInt(product.get("quantity"));
            totalAmount += price * quantity;
        }
        orders_total.setText(String.format("%.2f", totalAmount));
    }

    public void saveOrder() {
        if (!orderList.isEmpty()) {
            try {
                double totalOrderCost = 0.0;

                for (Map<String, String> orderProduct : orderList) {
                    double price = Double.parseDouble(orderProduct.get("price"));
                    int quantity = Integer.parseInt(orderProduct.get("quantity"));
                    double productCost = price * quantity;
                    totalOrderCost += productCost;
                }
                for (Map<String, String> orderProduct : orderList) {
                    String productName = orderProduct.get("productName");
                    int orderedQuantity = Integer.parseInt(orderProduct.get("quantity"));

                    Document query = new Document("productName", productName);
                    Document productDoc = productCollection.find(query).first();

                    if (productDoc != null) {
                        int availableQuantity = Integer.parseInt(productDoc.getString("quantity"));
                        if (orderedQuantity <= availableQuantity) {
                            int updatedQuantity = availableQuantity - orderedQuantity;

                            // Update the quantity of the product in the database
                            productCollection.updateOne(
                                    query,
                                    new Document("$set", new Document("quantity", String.valueOf(updatedQuantity)))
                            );
                        } else {
                            showAlert("Error", "Not enough quantity available for product: " + productName);
                            return; // Stop processing the order
                        }
                    } else {
                        showAlert("Error", "Product not found in the database: " + productName);
                        return; // Stop processing the order
                    }
                }

                // Create a new order document
                Document orderDoc = new Document();
                orderDoc.append("order_id", generateOrderId());
                orderDoc.append("customer", orders_customerName.getValue().toString());
                orderDoc.append("date", new Date());

                // Prepare products array
                List<Document> products = new ArrayList<>();
                for (Map<String, String> product : orderList) {
                    Document productDoc = new Document();
                    productDoc.append("type", product.get("type"));
                    productDoc.append("brand", product.get("brand"));
                    productDoc.append("productName", product.get("productName"));
                    productDoc.append("quantity", Integer.parseInt(product.get("quantity")));
                    productDoc.append("price", Double.parseDouble(product.get("price")));
                    products.add(productDoc);
                }

                orderDoc.append("totalCost", totalOrderCost);  // Add total cost to the order document

                // Insert the order document into MongoDB
                orderCollection.insertOne(orderDoc);

                // Store the order ID in the customer document
                String firstName = orders_customerName.getValue().split(" ")[0]; // Assuming selected value is in "First Name Last Name" format
                String lastName = orders_customerName.getValue().split(" ")[1];

                Document customerQuery = new Document("first_name", firstName).append("last_name", lastName);
                Document customer = customerCollection.find(customerQuery).first();

                if (customer != null) {
                    String orderId = orderDoc.getString("order_id");

                    // Add the order ID to the customer's document in the 'orderIds' array
                    customerCollection.updateOne(
                            customerQuery,
                            new Document("$addToSet", new Document("orderIds", orderId))
                    );
                } else {
                    showAlert("Error", "Customer not found in the database.");
                }

                // Calculate the total income of all orders from the database
                double totalIncome = calculateTotalIncomeFromDatabase();

                // Update the home_totalIncome label with the total income
                home_totalIncome.setText(String.format("%.2f", totalIncome));

                // Count the number of orders from the database
                int numberOfOrders = countOrdersFromDatabase();

                // Update the home_numberOrder label with the count
                home_numberOrder.setText(String.valueOf(numberOfOrders));

                // Clear the order list after processing
                orderList.clear();
                orders_tableView.refresh();
                orders_total.setText(""); // Clear total amount

                clearProductFields(); // Clear selected product information fields

                // Display confirmation message
                showAlert("Order Saved", "Order has been saved successfully.");

                // Reload all product data from the database and refresh the table view
                productList.clear();
                loadAllProductData();
                homeOrdersDataChart();
                homeIncomeChart();

            } catch (Exception e) {
                showAlert("Error", "Failed to save the order.");
                e.printStackTrace();
            }
        } else {
            showAlert("Empty Order", "Please add products to the order.");
        }
    }

    private String generateOrderId() {
        // Implement your logic to generate a unique order ID
        return UUID.randomUUID().toString();
    }

    private double calculateTotalIncomeFromDatabase() {
        double totalIncome = 0.0;

        MongoCursor<Document> cursor = orderCollection.find().iterator();
        try {
            while (cursor.hasNext()) {
                Document orderDoc = cursor.next();
                double orderTotalCost = orderDoc.getDouble("totalCost");
                totalIncome += orderTotalCost;
            }
        } finally {
            cursor.close();
        }

        return totalIncome;
    }

    private int countOrdersFromDatabase() {
        return (int) orderCollection.countDocuments();
    }


    public void homeIncomeChart() {
        home_incomeChart.getData().clear();

        XYChart.Series chart = new XYChart.Series(); // Set the y-axis data type to Double

        try {
            MongoCursor<Document> cursor = orderCollection.find().sort(new Document("date", -1)).limit(1).iterator();

            if (cursor.hasNext()) {
                Document latestOrder = cursor.next();
                Date latestDate = latestOrder.getDate("date");

                double totalIncome = calculateTotalIncomeUntilDate(latestDate);

                // Add the latest order date and total income to the series
                chart.getData().add(new XYChart.Data(latestDate.toString(), totalIncome));
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        home_incomeChart.getData().add(chart);
    }

    private double calculateTotalIncomeUntilDate(Date date) {
        double totalIncome = 0.0;

        MongoCursor<Document> cursor = orderCollection.find(new Document("date", new Document("$lte", date))).iterator();
        try {
            while (cursor.hasNext()) {
                Document orderDoc = cursor.next();
                totalIncome += orderDoc.getDouble("totalCost");
            }
        } finally {
            cursor.close();
        }

        return totalIncome;
    }


    public void homeOrdersDataChart() {
        home_orderChart.getData().clear();

        XYChart.Series chart = new XYChart.Series<>();

        // Fetch the data from MongoDB and populate the chart
        try {
            HashMap<String, Integer> ordersPerDay = calculateOrdersPerDay();

            for (Map.Entry<String, Integer> entry : ordersPerDay.entrySet()) {
                chart.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }

            home_orderChart.getData().add(chart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, Integer> calculateOrdersPerDay() {
        HashMap<String, Integer> ordersPerDay = new HashMap<>();

        MongoCursor<Document> cursor = orderCollection.find().iterator();
        try {
            while (cursor.hasNext()) {
                Document orderDoc = cursor.next();
                String orderDateStr = orderDoc.getDate("date").toString().substring(0, 10); // Get date in "yyyy-MM-dd" format
                ordersPerDay.put(orderDateStr, ordersPerDay.getOrDefault(orderDateStr, 0) + 1);
            }
        } finally {
            cursor.close();
        }

        return ordersPerDay;
    }



    private void clearProductFields() {
        orders_productType.getSelectionModel().clearSelection();
        orders_brand.getSelectionModel().clearSelection();
        orders_productName.getSelectionModel().clearSelection();
        orders_quantity.getValueFactory().setValue(1);
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private int countProductDocuments() {
        return (int) productCollection.countDocuments();
    }

    private void updateTotalIncomeLabel() {
        double totalIncome = calculateTotalIncomeFromDatabase();
        home_totalIncome.setText(String.format("%.2f", totalIncome));
    }

    private void updateNumberOfOrdersLabel() {
        int numberOfOrders = countOrdersFromDatabase();
        home_numberOrder.setText(String.valueOf(numberOfOrders));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        username.setText(LoginPageController.userNamE);
        ObservableList<String> productTypes = FXCollections.observableArrayList(
                "Jewelry", "Clothes", "Makeup"
        );
        addProducts_productType.setItems(productTypes);
        orders_productType.setItems(productTypes);



        productList = FXCollections.observableArrayList();

        addProducts_col_productId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("product_id")));
        addProducts_col_type.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("type")));
        addProducts_col_brand.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("brand")));
        addProducts_col_productName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("productName")));
        addProducts_col_quantity.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("quantity")));
        addProducts_col_price.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("price")));

        loadAllProductData();

//        addProducts_tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            selectedProduct = newValue;
//            if (selectedProduct != null){
//                addProducts_productId.setText(selectedProduct.get("product_id"));
//                addProducts_productType.setValue(selectedProduct.get("type"));
//                addProducts_brand.setText(selectedProduct.get("brand"));
//                addProducts_productName.setText(selectedProduct.get("productName"));
//                addProducts_quantity.setText(selectedProduct.get("quantity"));
//                addProducts_price.setText(selectedProduct.get("price"));
//                if(selectedProduct.get("imagePath") != null){
//                    Image image = new Image(new File(selectedProduct.get("imagePath")).toURI().toString());
//                    addProducts_imageView.setImage(image);
//                }
//            }
//        });

        addProducts_tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedProduct = newValue;
            if (selectedProduct != null) {
                addProducts_productId.setText(selectedProduct.get("product_id"));
                addProducts_productType.setValue(selectedProduct.get("type"));
                addProducts_brand.setText(selectedProduct.get("brand"));
                addProducts_productName.setText(selectedProduct.get("productName"));
                addProducts_quantity.setText(selectedProduct.get("quantity"));
                addProducts_price.setText(selectedProduct.get("price"));

                String imagePath = selectedProduct.get("imagePath");
                if (imagePath != null) {
                    Image image = new Image(new File(imagePath).toURI().toString());
                    addProducts_imageView.setImage(image);
                } else {
                    addProducts_imageView.setImage(null);
                }
            }
        });


        orders_col_type.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("type")));
        orders_col_brand.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("brand")));
        orders_col_productName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("productName")));
        orders_col_quantity.setCellValueFactory(cellData -> {
            String quantityString = cellData.getValue().get("quantity");
            Integer quantity = (quantityString != null) ? Integer.parseInt(quantityString) : 0;
            return new SimpleObjectProperty<>(quantity);
        });
        orders_col_price.setCellValueFactory(cellData -> {
            String priceString = cellData.getValue().get("price");
            Double price = (priceString != null) ? Double.parseDouble(priceString) : 0.0;
            return new SimpleObjectProperty<>(price);
        });

        // Initialize order list
        orderList = FXCollections.observableArrayList();
        orders_tableView.setItems(orderList);

        // Add listener for search input
        addProducts_search.textProperty().addListener((observable, oldValue, newValue) -> searchProducts());
        loadProductTypes();
        loadProductBrands();
        loadProductNames();
        loadCustomerNames();

        // Add listener to update product names based on selected type and brand
        orders_productType.valueProperty().addListener((observable, oldValue, newValue) -> updateProductNames());
        orders_brand.valueProperty().addListener((observable, oldValue, newValue) -> updateProductNames());

        //spinner
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,1);
        orders_quantity.setValueFactory(valueFactory);

        // Count the number of documents in the product collection
        int numberOfProducts = countProductDocuments();

        // Update the home_availableProducts label with the count
        home_availableProducts.setText(String.valueOf(numberOfProducts));
        updateTotalIncomeLabel();
        updateNumberOfOrdersLabel();

        homeIncomeChart();
        homeOrdersDataChart();

    }

}
