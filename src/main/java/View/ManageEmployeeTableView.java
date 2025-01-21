package View;

import Model.Users.Employee;
import Model.Users.Permission;
import Model.Users.Role;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

import java.util.EnumSet;


public class ManageEmployeeTableView extends VBox {

    private final TableView<Employee> table = new TableView<>();
    private final TableColumn<Employee, String> employeeIDCol;
    private final TableColumn<Employee, String> fullNameCol;
    private final TableColumn<Employee, String> emailCol;
    private final TableColumn<Employee, String> usernameCol;
    private final TableColumn<Employee, String> passwordCol;
    private final TableColumn<Employee, Role> roleCol;
    private final TableColumn<Employee, String> addressCol;
    private final TableColumn<Employee, Double> salaryCol;
    private final TableColumn<Employee, String> phoneNumberCol;
    private final TableColumn<Employee, EnumSet<Permission>> permissionCol;

    private final TextField addFirstName = new TextField();
    private final TextField addLastName = new TextField();
    private final TextField addEmail = new TextField();
    private final TextField addAddress = new TextField();
    private final ComboBox<Role> roleComboBox = new ComboBox<>();
    private final TextField addSalary = new TextField();
    private final TextField addPhoneNumber = new TextField();
    private final TextField addUsername = new TextField(); // New field for username
    private final PasswordField addPassword = new PasswordField();// New field for password
    private final ListView<Permission> permissionList = new ListView<>();

    private final Button searchButton = new Button("Search");
    private final Button deleteButton;
    private final Button addNewEmployeeButton;
    private final Button saveButton;
    private final Button homeButton = new Button("Home");
    private final Button editPermissionsButton = new Button("Edit Permissions");

    private final TextField searchField = new TextField();


    public ManageEmployeeTableView() {

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setEditable(false);

        employeeIDCol = new TableColumn<>("Employee ID");
        employeeIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeeIDCol.setStyle("-fx-alignment: CENTER;");

        fullNameCol = new TableColumn<>("First Name");
        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        fullNameCol.setStyle("-fx-alignment: CENTER;");

        emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setStyle("-fx-alignment: CENTER;");

        roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

        addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressCol.setCellFactory(TextFieldTableCell.forTableColumn());
        addressCol.setStyle("-fx-alignment: CENTER;");

        salaryCol = new TableColumn<>("Salary");
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        salaryCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        salaryCol.setStyle("-fx-alignment: CENTER;");

        phoneNumberCol = new TableColumn<>("Phone Number");
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phoneNumberCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumberCol.setStyle("-fx-alignment: CENTER;");

        usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        usernameCol.setStyle("-fx-alignment: CENTER;");

        passwordCol = new TableColumn<>("Password");
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        passwordCol.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordCol.setStyle("-fx-alignment: CENTER;");

        permissionCol = new TableColumn<>("Permissions");
        permissionCol.setCellValueFactory(new PropertyValueFactory<>("permissions"));
        permissionCol.setStyle("-fx-alignment: CENTER;");
        permissionCol.setMaxWidth(200);


        table.getColumns().add(employeeIDCol);
        table.getColumns().add(fullNameCol);
        table.getColumns().add(emailCol);
        table.getColumns().add(usernameCol);
        table.getColumns().add(passwordCol);
        table.getColumns().add(roleCol);
        table.getColumns().add(addressCol);
        table.getColumns().add(salaryCol);
        table.getColumns().add(phoneNumberCol);
        table.getColumns().add(permissionCol);


        addFirstName.setPromptText("First Name");
        addLastName.setPromptText("Last Name");
        addEmail.setPromptText("Email");
        addAddress.setPromptText("Address");

        roleComboBox.getItems().setAll(EnumSet.allOf(Role.class));
        roleComboBox.setPromptText("Select Role");
        roleComboBox.setMinWidth(100);

        addSalary.setPromptText("Salary");
        addPhoneNumber.setPromptText("Phone Number");
        addUsername.setPromptText("Username"); // Prompt for username
        addPassword.setPromptText("Password");// Prompt for password

        Label permission = new Label("Permissions: ");
        permission.setStyle("-fx-font-size: 14 px;");
        permissionList.getItems().addAll(EnumSet.allOf(Permission.class));
        permissionList.setMinWidth(80);
        permissionList.setMaxHeight(100);
        permissionList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        HBox addBox1 = new HBox();
        addBox1.setSpacing(30);
        addBox1.setPadding(new Insets(10));
        addBox1.getChildren().addAll(addFirstName, addLastName, addEmail, addAddress, roleComboBox);

        HBox addBox2 = new HBox();
        addBox2.setSpacing(30);
        addBox2.setPadding(new Insets(10));
        addBox2.getChildren().addAll(addSalary, addPhoneNumber, addUsername, addPassword, permission, permissionList);

        // Button for adding new employees
        addNewEmployeeButton = new Button("Add New Employee");


        // Button for deleting employees
        deleteButton = new Button("Delete");

        saveButton = new Button("Save");


        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(10));
        buttonBox.getChildren().addAll(addNewEmployeeButton, deleteButton, saveButton, editPermissionsButton);

        VBox mainVBox = new VBox();
        mainVBox.setSpacing(15);
        mainVBox.setPadding(new Insets(10));
        mainVBox.getChildren().addAll(addBox1, addBox2, buttonBox);

        // Adding search functionality
        searchField.setPromptText("Search by Name");


        HBox searchBox = new HBox();
        searchBox.setSpacing(10);
        searchBox.setPadding(new Insets(10));
        searchBox.getChildren().addAll(searchField, searchButton);

        // Old version
//        VBox leftSidebar = new VBox();
//        leftSidebar.setSpacing(10);
//        leftSidebar.setPadding(new Insets(10));

        // Home button with icon
        ImageView homeIcon = new ImageView(new Image("file:src/main/resources/images/home_icon.png"));
        homeIcon.setFitHeight(16);
        homeIcon.setFitWidth(16);

        homeButton.setGraphic(homeIcon);
        homeButton.setPrefWidth(150); // Set the same width for all buttons



//        leftSidebar.getChildren().add(homeButton);
//        leftSidebar.setStyle("-fx-background-color: #90614d;");

        table.setStyle("-fx-background-color: #D2CFDA; -fx-text-fill: #884135;");
        addBox1.setStyle("-fx-background-color: #c9af7b;");
        addBox2.setStyle("-fx-background-color: #C9AF7BFF;");
        mainVBox.setStyle("-fx-background-color: #D39C7E;");
        searchBox.setStyle("-fx-background-color: #9c2929; -fx-text-fill: white;");


        this.getChildren().addAll(searchBox, table, mainVBox);
        this.setPadding(new Insets(10));
        this.setSpacing(10);
        //this.setStyle("-fx-background-color: #90614d;");

    }

    private void clearFormFields() {
        addFirstName.clear();
        addLastName.clear();
        addEmail.clear();
        addAddress.clear();
        roleComboBox.getSelectionModel().clearSelection();
        addSalary.clear();
        addPhoneNumber.clear();
        addUsername.clear(); // Clear username
        addPassword.clear(); // Clear password
    }

    public void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setHeaderText(message);
        alert.setTitle("Error");
        alert.showAndWait();
    }


    public TableView<Employee> getTable() {
        return table;
    }

    public TableColumn<Employee, String> getEmployeeIDCol() {
        return employeeIDCol;
    }

    public TableColumn<Employee, String> getFullNameCol() {
        return fullNameCol;
    }

    public TableColumn<Employee, String> getEmailCol() {
        return emailCol;
    }

    public TableColumn<Employee, String> getAddressCol() {
        return addressCol;
    }

    public TableColumn<Employee, Double> getSalaryCol() {
        return salaryCol;
    }

    public TableColumn<Employee, String> getPhoneNumberCol() {
        return phoneNumberCol;
    }

    public TableColumn<Employee, String> getUsernameCol() {
        return usernameCol;
    }

    public TableColumn<Employee, String> getPasswordCol() {
        return passwordCol;
    }

    public TextField getAddFirstName() {
        return addFirstName;
    }

    public TextField getAddLastName() {
        return addLastName;
    }

    public TextField getAddEmail() {
        return addEmail;
    }

    public TextField getAddAddress() {
        return addAddress;
    }

    public ComboBox<Role> getRoleComboBox() {
        return roleComboBox;
    }

    public TextField getAddSalary() {
        return addSalary;
    }

    public TextField getAddPhoneNumber() {
        return addPhoneNumber;
    }

    public TextField getAddUsername() {
        return addUsername;
    }

    public PasswordField getAddPassword() {
        return addPassword;
    }

    public Button getSearchButton(){
        return searchButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getAddNewEmployeeButton() {
        return addNewEmployeeButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getEditPermissionsButton() {
        return editPermissionsButton;
    }

    public Button getHomeButton() {
        return homeButton;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public ListView<Permission> getPermissionList() {
        return permissionList;
    }

    public TableColumn<Employee, EnumSet<Permission>> getPermissionCol() {
        return permissionCol;
    }
}
