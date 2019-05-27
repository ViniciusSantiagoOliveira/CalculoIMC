/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadoraimc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Vinis
 */
public class ComparacaoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Imc> comparacao;

    @FXML
    private TableColumn<Imc, String> dataCol;

    @FXML
    private TableColumn<Imc, String> pesoCol;

    @FXML
    private TableColumn<Imc, String> imcCol;

    @FXML
    private TableColumn<Imc, String> resCol;
    
    @FXML
    private Button verprog;

    private ObservableList<Imc> imcData;
    
    @FXML
    private void clicouVerprog(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader (getClass().getResource("Grafico.fxml"));
            Parent root = loader.load();
            
            GraficoController graf = loader.getController();
            graf.setDados(imcData);
            Scene scene = new Scene(root);
            Stage stage1 = new Stage();
            stage1.setScene(scene);
            stage1.show();
        } catch (IOException ex){
            System.err.println("Erro ao abrir Grafico");
            ex.printStackTrace();
        }
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        imcData = FXCollections.observableArrayList();
        comparacao.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        dataCol.setCellValueFactory(cellData -> cellData.getValue().data());
        pesoCol.setCellValueFactory(cellData -> cellData.getValue().pes());
        imcCol.setCellValueFactory(cellData -> cellData.getValue().imc());
        resCol.setCellValueFactory(cellData -> cellData.getValue().res());


        System.out.println("É um diretório cujo conteúdo tem os seguintes arquivos: ");
        String[] arquivos = new File("C:\\Users\\Vinis\\Documents\\NetBeansProjects\\CalculadoraIMC").list();

        for (String file : arquivos) {
            if (file.contains(".txt")) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line = null;
                    float imc = Float.parseFloat(br.readLine());
                    float pesde = Float.parseFloat(br.readLine());
                    String res = br.readLine();

                    imcData.add(new Imc(pesde, imc, res, file));
                    br.close();
                } catch (FileNotFoundException ex) {
                    System.err.println("Arquivo inexistente");
                } catch (IOException ex) {
                    System.err.println("Erro ao ler arquivo");
                }

                System.out.println(file);
            }
        }

        comparacao.setItems(imcData);
    }

    void passaDados(double pes, double imc, String res,String data) {
        imcData.add(new Imc(pes, imc, res, data));

    }

}
