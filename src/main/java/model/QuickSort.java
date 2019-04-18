package model;

public class QuickSort {


    public QuickSort(){

    }

    /**
     * Hay varias formas de seleccionar el pivote. Una de ellas es usar siempre el central, otra consiste en seleccionar
     * siempre el ultimo indice, una muy usada es que sea random.
     * @param array
     * @param punteroIzq
     * @param punteroDer
     */
    public void sort(int array[], int punteroIzq, int punteroDer){
        int pivote, aux;
        int i = punteroIzq;
        int j = punteroDer;

        pivote = array[(punteroIzq+punteroDer)/2];

        //pivote = array[punteroDer];

        //pivote = array[(int)(Math.random() * punteroDer)]; //esta forma produce un StackOverflow a los pocos numeros por ser muchas
                                                            //llamadas al metodo random

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

    public void bubble(int ordenado[]){
        boolean ban = true;
        int aux;
        for(int pas = 1;ban; pas++){
            ban = false;

            for(int com = 1; com < (ordenado.length-pas); com++)

                if(ordenado[com-1] > ordenado[com]){
                    ban = true;
                    aux = ordenado[com-1];
                    ordenado[com-1] = ordenado[com];
                    ordenado[com] = aux;
                }
        }
    }

}
