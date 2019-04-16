package model;

public class QuickSort {


    public QuickSort(){

    }

    public void sort(int array[], int punteroIzq, int punteroDer){
        int pivote, aux;
        int i = punteroIzq;
        int j = punteroDer;

        pivote = array[(punteroIzq+punteroDer)/2];

        do{
            while (array[i] < pivote)
                i++;

            while (array[j] > pivote)
                j--;

            if(i <= j){
                aux = array[i];
                array[i] = array[j];
                array[j] = aux;
                i++;
                j--;
            }

        }while (i <= j);

        if(punteroIzq < j){
            sort(array, punteroIzq, j);
        }

        if(i < punteroDer){
            sort(array, i, punteroDer);
        }
    }


}
