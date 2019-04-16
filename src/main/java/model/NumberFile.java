package model;

import java.io.*;

public class NumberFile {

    private File file;
    public static final String DIR_DISORDERED = "files/disordered/";
    public static final String DIR_ORDERED = "files/ordered/";

    public NumberFile(String path, String name) {
        file = new File(path + name);

        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Genera un archivo de texto con numeros aleatorios, desde el 1 hasta el numero como parametro.
     * El primer numero que se escribe en el archivo es la cantidad de aleatorios en él
     * @param numberOfNumbs la cantidad de numeros aleatorios del archivo
     */
    public void generateNums(int numberOfNumbs) {
        PrintWriter writer = null;
        try {

            writer = new PrintWriter(new FileWriter(file));
            writer.println(numberOfNumbs+"");

            int num;
            for (int i = 0; i < numberOfNumbs; i++) {
                num = generateRandom(1, numberOfNumbs);
                writer.println(num + "");
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Escribe un vector de numeros en un archivo
     * @param nums vector int a escribir en el archivo
     */
    public void writeOrderedNums(int[] nums){
        PrintWriter writer = null;
        try {

            writer = new PrintWriter(new FileWriter(file));
            writer.println(nums.length+"");

            int cont = 0;
            for (int num : nums) {
                cont++;
                writer.print(num + " ");
                if(cont > 100) {
                    writer.println();
                    cont = 0;
                }
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getFile() {
        return file;
    }

    /**
     * Regresa un vector con los numeros de un archivo
     * @return
     */
    public static int[] getNumsOfFile(NumberFile numberFile){
        File file = numberFile.getFile();
        int n;
        int leido;

        int numbers[] = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            n = Integer.valueOf(reader.readLine());
            numbers = new int[n];

            for (int i = 0; i < n; i++) {
                leido = Integer.valueOf(reader.readLine());
                numbers[i] = leido;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return numbers;
    }


    public int generateRandom(int from, int to) {
        return (int) (Math.random() * (to - from + 1) + from);
    }
}
