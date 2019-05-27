/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadoraimc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Scanner;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

/**
 *
 * @author Vinis
 */
public class FXMLDocumentController implements Initializable {

    private boolean homem, mulher, sede, mode, inte;
    private int ida;
    private double imc;
    private double bmr, alt, pid, pes;
    private String res, rec;
    private boolean pesoIdeal = false;
    private boolean dadosOpcionais = false;

    File f = null;

    @FXML
    private Button masculino, feminino, sedentaria, moderada, intensa, calcular, comparar, ver;

    @FXML
    private TextField idade, altura, peso;
    
    DecimalFormat df = new DecimalFormat("###.##");

    @FXML
    private void clicouMasculino(ActionEvent event) {
        homem = true;
        mulher = false;
    }

    @FXML
    private void clicouFeminino(ActionEvent event) {
        homem = false;
        mulher = true;
    }

    @FXML
    private void clicouSedentaria(ActionEvent event) {
        sede = true;
        mode = false;
        inte = false;

    }

    @FXML
    private void clicouModerada(ActionEvent event) {
        sede = false;
        mode = true;
        inte = false;
    }

    @FXML
    private void clicouIntensa(ActionEvent event) {
        sede = false;
        mode = false;
        inte = true;
    }

    @FXML
    private void clicouCalcular(ActionEvent event) {
        pesoIdeal = false;
        dadosOpcionais = false;
        ida = Integer.parseInt(idade.getText());
        alt = Float.parseFloat(altura.getText());
        pes = Integer.parseInt(peso.getText());
        
        imc = (float) (pes / (alt * alt));
        if (imc < 18.5) {
            res = ("ABAIXO DO PESO IDEAL");
            pid = ((18.5*(Math.pow(alt, 2)))- pes);
            rec = ("ganhar");
        } else if (imc < 25) {
            res = ("PESO IDEAL");
            pesoIdeal = true;
        } else if (imc < 30) {
            res = ("SOBREPESO");
            pid = (pes - (25*(Math.pow(alt, 2))));
            rec = ("perder");
        } else if (imc < 35) {
            res = ("OBESIDADE GRAU 1");
            pid = (pes - (25*(Math.pow(alt, 2))));
            rec = ("perder");
        } else if (imc < 40) {
            res = ("OBESIDADE GRAU 2");
            pid = (pes - (25*(Math.pow(alt, 2))));
            rec = ("perder");
        } else {
            res = ("OBESIDADE GRAU 3");
            pid = (pes - (25*(Math.pow(alt, 2))));
            rec = ("perder");
        }
        if (homem == false && mulher == false || sede == false && mode == false && inte == false){
            dadosOpcionais = true;
        } else if(homem == true){
            if(sede == true){
                bmr = (((10 * pes) + (625 * alt) - (5 * ida) + 5) * 1.53);
            }
            if(mode == true){
                bmr = (((10 * pes) + (625 * alt) - (5 * ida) + 5) * 1.76);
            }
            if(inte == true){
                bmr = (((10 * pes) + (625 * alt) - (5 * ida) + 5) * 2.25);
            }
        } else if(mulher == true){
            if(sede == true){
                bmr = (((10 * pes) + (625 * alt) - (5 * ida) - 161) * 1.53);
            }
            if(mode == true){
                bmr = (((10 * pes) + (625 * alt) - (5 * ida) - 161) * 1.76);
            }
            if(inte == true){
                bmr = (((10 * pes) + (625 * alt) - (5 * ida) - 161) * 2.25);
            }
        }
        try {
            int i = 1;
            f = new File(String.valueOf(Calendar.getInstance().getTime()).replaceAll(" ", "_").replaceAll(":", "-") + ".txt");
            if (!f.exists()) {
                f.createNewFile();
            }
//        FileWriter fw = new FileWriter("arquivo.txt");
//        BufferedWriter bw = new BufferedWriter(fw);
//        PrintWriter out = new PrintWriter(bw);
//        out.println(imc);
//        out.close();
            PrintStream ps = new PrintStream(f);
            System.out.println(imc + "\n" + pes + "\n" + res + "\n" + rec + "\n" + pid);
            ps.println(imc + "\n" + pes + "\n" + res + "\n" + rec + "\n" + pid);
            ps.flush();
            ps.close();
        } catch (IOException e) {
            System.err.println("Erro ao manipular o arquivo");
        }
        if (dadosOpcionais == true){
            if (pesoIdeal == false) {
                Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
                dialogoInfo.setTitle("Resultado");
                dialogoInfo.setHeaderText("Seu IMC é " + df.format(imc) + ".");
                dialogoInfo.setContentText("Situação: " + res + "\n" + "Você precisa " + rec + " " + df.format(pid) + " quilos para atingir seu peso ideal.");
                dialogoInfo.showAndWait();
                
            } else {
                Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
                dialogoInfo.setTitle("Resultado");
                dialogoInfo.setHeaderText("Seu IMC é " + df.format(imc) + ".");
                dialogoInfo.setContentText("Você está no seu " + res + ".");
                dialogoInfo.showAndWait();
            }
            
        }else{
            if (pesoIdeal == false) {
             Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
                dialogoInfo.setTitle("Resultado");
                dialogoInfo.setHeaderText("Seu IMC é " + df.format(imc) + ".");
                dialogoInfo.setContentText("Situação: " + res + "\n" + "Você precisa " + rec + " " + df.format(pid) + " quilos para atingir seu peso ideal." + "\n" + "É recomendável que você ingira " + df.format(bmr) + " calorias por dia para manter uma vida saudável.");
                dialogoInfo.showAndWait();
            }else {
                Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
                dialogoInfo.setTitle("Resultado");
                dialogoInfo.setHeaderText("Seu IMC é " + df.format(imc) + ".");
                dialogoInfo.setContentText("Você está no seu " + res + "." + " É recomendável que você ingira " + df.format(bmr) + " calorias por dia para manter uma vida saudável.");
                dialogoInfo.showAndWait();
            }
        }
    }
    @FXML
    private void clicouVer(ActionEvent event) {
       if (dadosOpcionais == true){
            if (pesoIdeal == false) {
                Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
                dialogoInfo.setTitle("Resultado");
                dialogoInfo.setHeaderText("Seu IMC é " + df.format(imc) + ".");
                dialogoInfo.setContentText("Situação: " + res + "\n" + "Você precisa " + rec + " " + df.format(pid) + " quilos para atingir seu peso ideal.");
                dialogoInfo.showAndWait();
                
            } else {
                Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
                dialogoInfo.setTitle("Resultado");
                dialogoInfo.setHeaderText("Seu IMC é " + df.format(imc) + ".");
                dialogoInfo.setContentText("Você está no seu " + res + ".");
                dialogoInfo.showAndWait();
            }
            
        }else{
            if (pesoIdeal == false) {
             Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
                dialogoInfo.setTitle("Resultado");
                dialogoInfo.setHeaderText("Seu IMC é " + df.format(imc) + ".");
                dialogoInfo.setContentText("Situação: " + res + "\n" + "Você precisa " + rec + " " + df.format(pid) + " quilos para atingir seu peso ideal." + "\n" + "É recomendável que você ingira " + df.format(bmr) + " calorias por dia para manter uma vida saudável.");
                dialogoInfo.showAndWait();
            }else {
                Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
                dialogoInfo.setTitle("Resultado");
                dialogoInfo.setHeaderText("Seu IMC é " + df.format(imc) + ".");
                dialogoInfo.setContentText("Você está no seu " + res + "." + " É recomendável que você ingira " + df.format(bmr) + " calorias por dia para manter uma vida saudável.");
                dialogoInfo.showAndWait();
            }
        }
    }

    @FXML
    private void clicouComparar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Comparacao.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        ComparacaoController c = loader.getController();

        stage.show();
        c.passaDados(pes, imc, res, String.valueOf(Calendar.getInstance().getTime()).replaceAll(" ", "_").replaceAll(":", "-"));

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        intensa.setTooltip(new Tooltip("Trabalhadores rurais manuais e pessoas que nadam duas horas por dia."));
        moderada.setTooltip(new Tooltip("Trabalhadores de construção e pessoas que correm uma hora por dia."));
        sedentaria.setTooltip(new Tooltip("Trabalhadores de escritório e pessoas que se exercitam pouco."));
    }

}
