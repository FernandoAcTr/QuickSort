package model;

import java.io.File;
import java.util.ArrayList;

public class FileList {

    public ArrayList<NumberFile> listFiles;
    private int numberOfFiles;

    public FileList(int numberOfFiles) {
        listFiles = new ArrayList<NumberFile>();
        this.numberOfFiles = numberOfFiles;
    }

    /**
     * crea tantos archivos de numeros como se indico en el contructor. En cada archivo genera una cierta
     * cantidad de numeros aleatorios. Cada archivo contiene mas numeros aleatorios que el anterior.
     * Para hacer una buena graficacion de tiempos.
     *
     * @param numbersPerFile cantidad de numeros aleatorios maximo por un archivo.
     */
    public void generateDisorderFiles(int numbersPerFile, int numberOfFiles) {
        NumberFile numberFile;
        int increment = numbersPerFile / numberOfFiles;
        int n = increment;

        for (int i = 1; i <= numberOfFiles; i++) {
            numberFile = new NumberFile(NumberFile.DIR_DISORDERED, "archivo" + i + ".txt");

            numberFile.generateNums(n);
            n += increment;

            listFiles.add(numberFile);
        }
    }

    /**
     * Borra todos los archivos de las carpetas ordered y disordered
     */
    public static boolean deleteFiles() {
        File orderDir = new File(NumberFile.DIR_ORDERED);
        File disorderDir = new File(NumberFile.DIR_DISORDERED);
        boolean succes = true;

        if (orderDir.exists())
            for (File file : orderDir.listFiles())
                if (!file.delete())
                    succes = false;

        if (disorderDir.exists())
            for (File file : disorderDir.listFiles())
                if (!file.delete())
                    succes = false;

        return succes;
    }

}
