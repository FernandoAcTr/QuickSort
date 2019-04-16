package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import model.FileList;
import model.NumberFile;
import model.QuickSort;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button btnGenerateFiles;

    @FXML
    private Button btnOrder;

    @FXML
    private Button btnDeleteAll;

    @FXML
    private LineChart<Integer, Integer> lineChart;

    @FXML
    private MenuItem mnuNumberOfFiles, mnuQuantityNumbers;

    FileList fileList;
    QuickSort quickSort;
    int numberOfFiles;
    int numbersPerFile;

    public void initialize(URL location, ResourceBundle resources) {
        initData();
        initComponents();
    }

    private void initData() {
        numberOfFiles = 100;
        numbersPerFile = 1000;
        quickSort = new QuickSort();
        fileList = new FileList(numberOfFiles);
    }

    private void initComponents() {
        btnGenerateFiles.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                generateFiles();
            }
        });

        btnDeleteAll.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FileList.deleteFiles();
                lineChart.getData().clear();
            }
        });

        mnuNumberOfFiles.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                numberOfFiles = showInputDialog("Numero de Archivos", null,
                        "Archivos de Prueba: ", numberOfFiles+"");
                FileList.deleteFiles();
                fileList = new FileList(numberOfFiles);
            }
        });

        mnuQuantityNumbers.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                numbersPerFile = showInputDialog("Números por Archivo", null,
                        "Aleatorios por Archivo: ", numbersPerFile+"");
            }
        });

        btnOrder.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                int[] times = orderFiles();
                graphic(times);
            }
        });
    }

    private void generateFiles() {
        fileList.generateDisorderFiles(numbersPerFile, numberOfFiles);
    }

    private int showInputDialog(String title, String header, String content, String defValue){
        TextInputDialog input = new TextInputDialog(defValue);
        input.setTitle(title);
        input.setHeaderText(header);
        input.setContentText(content);

        Optional<String> result = input.showAndWait();
        if(result.isPresent())
            return Integer.valueOf(result.get());

        return numberOfFiles;
    }


    private int[] orderFiles(){

        long timeStart, timeEnd;
        int[] times = new int[fileList.listFiles.size()];
        int vector[];
        int pos = 0;

        NumberFile orderFile;

        for(NumberFile file : fileList.listFiles){

            //recuperar los numeros del archivo
            vector = NumberFile.getNumsOfFile(file);

            //ordenar el vector con quicksort
            timeStart = System.nanoTime();
            quickSort.sort(vector, 0, vector.length-1);
            timeEnd = System.nanoTime() - timeStart;

            //escribir los numeros ordenados en un archivo nuevo
            orderFile = new NumberFile(NumberFile.DIR_ORDERED, "archivo"+(pos+1));
            orderFile.writeOrderedNums(vector);

            times[pos] = (int)timeEnd/1000000;
            pos++;
        }

        return times;
    }

    private void graphic(int times[]){
        int[] filesPerTime = getFilesPerTime(times);

        XYChart.Series<Integer, Integer> serie = new XYChart.Series<Integer, Integer>();

        for (int i = 0; i < filesPerTime.length; i++)
            serie.getData().add(new XYChart.Data<Integer, Integer>(filesPerTime[i], times[i]));

        lineChart.getData().add(serie);
    }

    /**
     * Obtiene cuantos numeros contiene el archivo a partir de su tiempo y genera un arreglo paralelo.
     * Para que corresponda un tiempo por cantidad de numeros ordenados.
     * @param times
     * @return
     */
    private int[] getFilesPerTime(int times[]){
        int[] nFiles = new int[times.length];
        int increment = numbersPerFile / times.length;
        int n = increment;

        for (int i = 0; i < nFiles.length; i++) {
            nFiles[i] = n;
            n += increment;
        }

        return nFiles;
    }
}