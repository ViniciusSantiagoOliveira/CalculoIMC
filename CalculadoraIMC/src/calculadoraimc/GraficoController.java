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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;

/**
 *
 * @author Vinis
 */
public class GraficoController implements Initializable {

    @FXML
    LineChart grafico;

    ObservableList<Imc> dados;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dados = FXCollections.observableArrayList();
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

                    dados.add(new Imc(pesde, imc, res, file));
                    br.close();
                } catch (FileNotFoundException ex) {
                    System.err.println("Arquivo inexistente");
                } catch (IOException ex) {
                    System.err.println("Erro ao ler arquivo");
                }

                System.out.println(file);
            }
        }
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Month");
        //creating the chart
        final LineChart<String, Number> lineChart
                = new LineChart(xAxis, yAxis);

        grafico.setTitle("Visualização do Progresso");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("IMC");
        for (Imc dado : dados) {
            series.getData().add(new XYChart.Data(dado.getData(), dado.getImc()));
        }
        grafico.getData().add(series);
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Alvo inferior");
        for (Imc dado : dados) {
            series2.getData().add(new XYChart.Data(dado.getData(), 18.5));
        }
        grafico.getData().add(series2);
        series2.getNode().setStyle("-fx-opacity: 0.3;");
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Alvo superior");
for (Imc dado : dados) {
           series3.getData().add(new XYChart.Data(dado.getData(), 25));
        }
       
        grafico.getData().add(series3);
        series3.getNode().setStyle("-fx-opacity: 0.3;");   
        grafico.setCreateSymbols(false);
    }

    void setDados(ObservableList<Imc> imcData) {
        dados = imcData;
    }
}
