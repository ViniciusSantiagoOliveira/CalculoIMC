/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadoraimc;

/**
 *
 * @author Vinis
 */
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.StringProperty;

public class Imc {
    private final StringProperty data;
    private final DoubleProperty pes;
    private final DoubleProperty imc;
    private final StringProperty res;
    
    public Imc (double pes, double imc, String res, String data){
        this.pes = new SimpleDoubleProperty(pes);
        this.data = new SimpleStringProperty(data.split("_")[1] + " " + data.split("_")[2] + " " + data.split("_")[5].replaceAll(".txt", ""));//Mon_May_06_13-52-55_BRT_2019
        this.imc = new SimpleDoubleProperty(imc);
        this.res = new SimpleStringProperty(res);
         
    }

    public String getData() {
        return data.get();
    }

    public Double getpes() {
        return pes.get();
    }

    public Double getImc() {
        return imc.get();
    }

    public String getRes() {
        return res.get();
    }
    

    public StringProperty data() {
        return data;
    }

    public StringProperty pes() {
        return new SimpleStringProperty(String.valueOf(pes.floatValue()));
    }

    public StringProperty imc() {
        return new SimpleStringProperty(String.valueOf(imc.floatValue()));
    }

    public StringProperty res() {
        return res;
    }
    
    public void setpes(float pes){
        this.pes.set(pes);
    }
    
    public void setimc(float imc){
        this.imc.set(imc);
    }
    
    public void setres(String res){
        this.res.set(res);
    }
    public void setdata(String data){
        this.res.set(data);
    
    }

    @Override
    public String toString() {
        return "Imc{" + "data=" + data + ", pes=" + pes + ", imc=" + imc + ", res=" + res + '}';
    }
    
    
}
