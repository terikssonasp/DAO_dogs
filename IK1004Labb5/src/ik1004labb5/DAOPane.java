/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ik1004labb5;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Simple Preloader Using the ProgressBar Control
 *
 * @author TErik
 */
public class DAOPane extends BorderPane {

    String[] labelArr = {"ID", "Namn", "Ras", "Bild"};

    String[] tfArr = {"idTF", "namnTF", "rasTF", "bildTF"};
    ArrayList<TextField> textFields = new ArrayList<TextField>();

    /* String[] yesnoArr = {"Ja", "Nej"};
    ToggleGroup yesno = new ToggleGroup();
    SimpleBooleanProperty hundgård = new SimpleBooleanProperty();*/
    String[] buttonsArr = {"Lägg till", "Ta bort", "Uppdatera", "<<", ">>"};
    ArrayList<Button> buttons = new ArrayList<Button>();

    ImageView imgView = new ImageView();
    Image logo = new Image("file:sot-hund.jpg");

    DAOHund daoHund; //Programmera mot detta interface
    ObservableList<DTOHund> listan = FXCollections.observableArrayList();
    ObservableList<String> raser = FXCollections.observableArrayList("Visa vald ras", "Borzoi");

    BtnListener btnListener = new BtnListener();

    MenuBar menuBar = new MenuBar();
    Menu file = new Menu("File");
    MenuItem open = new MenuItem("Open");
    MenuItem save = new MenuItem("Save");

    TableView<DTOHund> tableView;
    TableColumn<DTOHund, String> colID = new TableColumn("ID");
    TableColumn<DTOHund, String> colNamn = new TableColumn("Namn:");
    TableColumn<DTOHund, String> colRas = new TableColumn("Ras:");
    TableColumn<DTOHund, String> colURL = new TableColumn("Bild URL:");
    //TableColumn<DTOHund, SimpleBooleanProperty> colHundgård = new TableColumn("I hundgård:");

    public DAOPane(DAOHund daoHund) { //Här skickas det in vilken typ av datakälla som använts
        setTop(setTopMenu());
        setCenter(setCenterGP());
        setBottom(setBottomTable());
        this.daoHund = daoHund;
        listan.setAll(daoHund.getHundar()); //När objektet har antagit rätt subklass används metoden för att returnera lista

    }

    private MenuBar setTopMenu() {

        file.getItems().add(open);
        open.setOnAction(btnListener);
        file.getItems().add(save);
        menuBar.getMenus().add(file);

        menuBar.setPadding(new javafx.geometry.Insets(0, 0, 25, 0));

        return menuBar;
    }

    private GridPane setCenterGP() {
        GridPane centerGP = new GridPane();
        centerGP.setVgap(10.0);

        int antal = labelArr.length;
        for (int i = 0; i < antal; i++) {
            Label lbl = new Label(labelArr[i]);
            centerGP.add(lbl, 0, i);
        }

        antal = tfArr.length;

        for (int i = 0; i < antal; i++) {
            textFields.add(new TextField());
            centerGP.add(textFields.get(i), 1, i);
        }

        /*HBox rbBox = new HBox();
        rbBox.setSpacing(5.0);
        rbBox.setAlignment(Pos.CENTER);
        antal = yesnoArr.length;
        for (int i = 0; i < antal; i++) {
            RadioButton rb = new RadioButton(yesnoArr[i]);
            rb.setToggleGroup(yesno);
            rb.setUserData(yesnoArr[i]);
            rbBox.getChildren().add(rb);
        }
       
        centerGP.add(rbBox, 1, 4);*/
        raser.setAll(getRaslista());
        ComboBox sortBox = new ComboBox(FXCollections.observableArrayList(raser));
        sortBox.getSelectionModel().selectFirst();
        centerGP.add(sortBox, 0, 5, 2, 1);
        sortBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> sortRasTable((String) newValue));

        imgView.setFitWidth(250);
        imgView.setPreserveRatio(true);
        imgView.setImage(logo);

        centerGP.add(imgView, 2, 0, 1, 5);

        antal = buttonsArr.length;
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(15.0);
        for (int i = 0; i < antal; i++) {
            buttons.add(new Button(buttonsArr[i]));
            buttons.get(i).setOnAction(btnListener);
            buttonBox.getChildren().add(buttons.get(i));
        }

        centerGP.add(buttonBox, 0, 6, 3, 1);

        centerGP.setPadding(new javafx.geometry.Insets(5, 0, 15, 10));
        centerGP.setHgap(5.0);

        return centerGP;
    }

    private TableView setBottomTable() {

        //Kolumn för ID
        colID.setMinWidth(100);
        colID.setCellValueFactory(new PropertyValueFactory<DTOHund, String>("id"));

        //Kolumn för namn
        colNamn.setMinWidth(100);
        colNamn.setCellValueFactory(new PropertyValueFactory<DTOHund, String>("namn"));

        //Kolumn för ras
        colRas.setMinWidth(100);
        colRas.setCellValueFactory(new PropertyValueFactory<DTOHund, String>("ras"));

        //Kolumn för bildURL
        colURL.setMinWidth(100);
        colURL.setCellValueFactory(new PropertyValueFactory<DTOHund, String>("bildURL"));

        /*Kolumn för hundgård
        colHundgård.setMinWidth(100);
        colHundgård.setCellValueFactory(new PropertyValueFactory<DTOHund, SimpleBooleanProperty>("iHundgård"));*/
        tableView = new TableView();
        tableView.setItems(listan);
        tableView.getColumns().addAll(colID, colNamn, colRas, colURL);

        tableView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue != null) {
                setFields(newValue.getId());
            }
        });

        return tableView;

    }

    private class BtnListener implements EventHandler {

        @Override
        public void handle(Event event) {

            if (event.getSource() == buttons.get(0)) { //Lägg till

                //hundgård = getHundgårdCB();
                DTOHund dtoHund = new DTOHund(Integer.parseInt(textFields.get(0).getText()), textFields.get(1).getText(), textFields.get(2).getText(), textFields.get(3).getText());
                daoHund.add(dtoHund); //Metoden add ägger till dto-hundobjektet i min databas via DAOFactory och DAOHundTextFile

                listan.setAll(daoHund.getHundar());//Sätter om listan efetr föränring i textfil

            }

            if (event.getSource() == buttons.get(1)) { //Ta bort

                int delID = -1;

                for (int i = 0; i < listan.size(); i++) {
                    if (listan.get(i).getId() == Integer.parseInt(textFields.get(0).getText())) {
                        delID = listan.get(i).getId();
                        listan.remove(i);
                        break;
                    }

                }

                daoHund.delete(delID);

                listan.setAll(daoHund.getHundar());//Sätter om listan efetr föränring i textfil
            }
            if (event.getSource() == buttons.get(2)) {//Uppdatera

                for (int i = 0; i < listan.size(); i++) {
                    if (listan.get(i).getId() == Integer.parseInt(textFields.get(0).getText())) {
                        listan.get(i).setNamn(textFields.get(1).getText());
                        listan.get(i).setRas(textFields.get(2).getText());
                        listan.get(i).setBildURL(textFields.get(3).getText());
                        //listan.get(i).setiHundgård(getHundgårdCB());
                        DTOHund dtoHund = (new DTOHund(listan.get(i).getId(), listan.get(i).getNamn(), listan.get(i).getRas(), listan.get(i).getBildURL()));
                        daoHund.update(dtoHund);
                        setFields(i);
                    }
                }
                listan.setAll(daoHund.getHundar());//Sätter om listan efetr föränring i textfil

            }

            if (event.getSource() == buttons.get(3)) { //Bläddra vänster

                for (int i = 0; i < listan.size(); i++) {
                    if ((listan.get(i).getId() == Integer.parseInt(textFields.get(0).getText())) && (Integer.parseInt(textFields.get(0).getText()) >= 1)) {
                        textFields.get(0).setText(Integer.toString(listan.get(i - 1).getId()));
                        textFields.get(1).setText(listan.get(i - 1).getNamn());
                        textFields.get(2).setText(listan.get(i - 1).getRas());
                        textFields.get(3).setText(listan.get(i - 1).getBildURL());
                        imgView.setImage(new Image("file:" + listan.get(i - 1).getBildURL()));
                    }
                }

            }

            if (event.getSource() == buttons.get(4)) { //Bläddra höger

                for (int i = 0; i < listan.size(); i++) {
                    if ((listan.get(i).getId() == Integer.parseInt(textFields.get(0).getText())) && (Integer.parseInt(textFields.get(0).getText()) < listan.size())) {
                        textFields.get(0).setText(Integer.toString(listan.get(i + 1).getId()));
                        textFields.get(1).setText(listan.get(i + 1).getNamn());
                        textFields.get(2).setText(listan.get(i + 1).getRas());
                        textFields.get(3).setText(listan.get(i + 1).getBildURL());
                        imgView.setImage(new Image("file:" + listan.get(i + 1).getBildURL()));
                        break;
                    }
                }
            }

            if (event.getSource().equals(open)) {
                System.out.println(listan.get(0).getNamn());
                //setFile();

            }

        }

        /* private SimpleBooleanProperty getHundgårdCB() {
            if (yesno.getSelectedToggle().getUserData().toString().equals("Ja")) {
                hundgård.set(true);
            } else {
                hundgård.set(false);
            }
            return hundgård;
        }*/
    }

    private void setFields(int tempID) {
        {
            for (int i = 0; i < listan.size(); i++) {

                if (listan.get(i).getId() == tempID) {

                    textFields.get(0).setText(Integer.toString(listan.get(i).getId()));
                    textFields.get(1).setText(listan.get(i).getNamn());
                    textFields.get(2).setText(listan.get(i).getRas());
                    textFields.get(3).setText(listan.get(i).getBildURL());
                    imgView.setImage(new Image("file:" + listan.get(i).getBildURL()));
                    break;
                }
            }
        }
    }

    public ObservableList<String> getRaslista() {

        ObservableList<String> dynRaser = FXCollections.observableArrayList("Alla raser", "Whippet", "Borzoi", "Blandras", "Griffon");
        return dynRaser;

    }

    public void sortRasTable(String ras) {
        ObservableList<DTOHund> sortList = FXCollections.observableArrayList();

        if (ras.equalsIgnoreCase("Alla raser")) {
            tableView.setItems(listan);
        } else {
            for (int i = 0; i < listan.size(); i++) {
                if (ras.equalsIgnoreCase(listan.get(i).getRas())) {
                    sortList.add(new DTOHund(listan.get(i).getId(), listan.get(i).getNamn(), listan.get(i).getRas(), listan.get(i).getBildURL()));
                }
            }

            tableView.setItems(sortList);
        }
    }

}
