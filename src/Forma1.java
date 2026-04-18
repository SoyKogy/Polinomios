import javax.swing.JOptionPane;

public class Forma1 {    

    // Atributos
    private int DU, VPF1[];

    // Constructor

    public Forma1(int Grado) {
        DU = Grado + 1;
        VPF1 = new int[DU + 1];
        VPF1[0] = Grado;
        
    }

    // Getters y Setters

    public int getDU() { 
        return DU;
    }

    public void setDU(int DU) {
        this.DU = DU;
    }

    public int[] getVPF1() { // se devuelve el vector completo
        return VPF1;
    }

    public void setVPF1(int[] VPF1) { // se obtiene el vector completo para modificarlo
        this.VPF1 = VPF1;
    }

    public int getVPF1(int i) {
        return VPF1[i];
    }

    public void setVPF1(int i, int d) {
        this.VPF1[i] = d;
    }

    // Metodos

    public void PasarVPF1(String[] vectorString) {
        int gradosVPF1 = (DU - 1);
        for (int i = 1, j = 1; gradosVPF1 >= 0; i += 2, j++, gradosVPF1--) {
            // se busca guardar el coeficiente en la posición descendente del vector,
            // es decir, el coeficiente del término de mayor grado se guarda en la 
            // posición 1 del vector, el coeficiente del término de grado n-1
            // se guarda en la posición 2, y así sucesivamente. si un grado no 
            // tiene coeficiente, se guarda un 0 en su posición correspondiente.
            if (i < vectorString.length && vectorString[i] != null
                    && (Integer.parseInt(vectorString[i])) == (gradosVPF1)) {
                setVPF1(j, Integer.parseInt(vectorString[i - 1]));
            } else {
                setVPF1(j, 0);
                i -= 2; // se resta 2 para volver a revisar el mismo término del vectorString en la siguiente iteración
            }
        }

        System.out.println("\nVPF1: ");
        for (int i = 0; i < (DU + 1); i++) {
            System.out.print(VPF1[i] + "|");
        } 
    }

    public void Insertar() {
        String termino = JOptionPane.showInputDialog("Ingrese el término a insertar:");
        String polinomioOriginal = Reconstruir(); // se guarda el polinomio original para poder imprimirlo después

        String[] terminoConcatenado = new String[Polinomios.ConcatenarTermino(termino).length];
        terminoConcatenado = Polinomios.ConcatenarTermino(termino);

        int gradosVPF1 = (DU - 1), ban = 0;

        // Definir en donde colocar el nuevo termino en función de su grado
        for (int i = 1; gradosVPF1 >= 0; i++, gradosVPF1--) {

            // Comparación con el mayor grado
            if (i == 1 && Integer.parseInt(terminoConcatenado[1]) == gradosVPF1) {
                setVPF1(1, VPF1[1] + Integer.parseInt(terminoConcatenado[0])); //  si el nuevo termino es igual
                                                                                //  al mayor grado, se suma con el 
                                                                                //  coeficiente de mayor grado
                gradosVPF1 = -1; // como ya se añadió el nuevo término, se rompe el ciclo              
            } else if (i == 1 && Integer.parseInt(terminoConcatenado[1]) > gradosVPF1){
                /* si el nuevo termino es mayor que el mayor grado
                   hay que definir cuantas posiciones vamos a correr los otros terminos hacia la derecha
                   y meter todo de nuevo dentro de otro ciclo externo a este. 

                   para eso, usamos InsertarAlInicio() para simplificar la estructura, pero se activa
                   afuera del ciclo para poder definir el tamaño del vector nuevo
                */
                ban = 1;

                gradosVPF1 = -1;
            } else if (VPF1[i] == 0 && (Integer.parseInt(terminoConcatenado[1])) == gradosVPF1) {
            
                // si no, hay que buscar si la casilla donde se va a insertar está vacía
                setVPF1(i, Integer.parseInt(terminoConcatenado[0]));

                gradosVPF1 = -1; // como ya se añadió el nuevo término, se rompe el ciclo
            } else if (VPF1[i] != 0 && (Integer.parseInt(terminoConcatenado[1])) == gradosVPF1) {

                // si para el grado actual ya hay un término, entonces se operan
                int suma = VPF1[i] + Integer.parseInt(terminoConcatenado[0]);
                setVPF1(i, suma);

                gradosVPF1 = -1; // como ya se añadió el nuevo término, se rompe el ciclo
            }
        }

        if (ban != 1) {
            // print del vector con el nuevo termino insertado o sumado
            JOptionPane.showMessageDialog(null, "Polinomio original:\n" + polinomioOriginal + "\nTermino a insertar:\n" + termino + "\nPolinomio total:\n" + Reconstruir());

        } else {
            /*actualizo el vector forma 1 con el nuevo
              vector que es más grande
             */

            setVPF1(InsertarYAjustar(VPF1, terminoConcatenado));
            setDU(Integer.parseInt(terminoConcatenado[1]) + 1);

            // print del nuevo vector con nuevo tamaño
            JOptionPane.showMessageDialog(null, "Polinomio original:\n" + polinomioOriginal + "\nTermino a insertar:\n" + termino + "\nPolinomio total:\n" + Reconstruir());
        }

    }

    public int[] InsertarYAjustar(int[] VPF1, String[] termino) {

        // calcular cuanto crece el grado (para saber cuantas casillas mover)
        int diferencia = Integer.parseInt(termino[1]) - VPF1[0];
        int newVPF1[] = new int[VPF1.length + diferencia];

        // defino el nuevo grado maximo
        newVPF1[0] = Integer.parseInt(termino[1]);
        
        // "correr" términos hacia la derecha 
        for (int i = 1; i < VPF1.length; i++) {
            newVPF1[i + diferencia] = VPF1[i];
        }

        newVPF1[1] = Integer.parseInt(termino[0]);
        
        return newVPF1;

    }

    public void Eliminar() {
        String exponente = JOptionPane.showInputDialog("Ingrese el exponente del término a eliminar:");

        int gradosVPF1 = (DU - 1);
        String polinomioOriginal = Reconstruir(); // se guarda el polinomio original para poder imprimirlo después

        // busca el término y lo elimina poniendolo en cero
        for (int i = 1, ban = 0; gradosVPF1 >= 0 && ban == 0; i++, gradosVPF1--) {
            if (Integer.parseInt(exponente) == gradosVPF1) {
                VPF1[i] = 0;
                ban = 1;
            }
        }

        // Eliminar al inicio
        if (VPF1[1] == 0) { // si se eliminó el primer término (osea el mayor)

            setVPF1(EliminarYAjustar(VPF1));
            setDU(VPF1[0] + 1);

            JOptionPane.showMessageDialog(null, "Polinomio original:\n" + polinomioOriginal + "\nExponente del termino a eliminar:\n" + exponente + "\nPolinomio total:\n" + Reconstruir());

        } else {
            JOptionPane.showMessageDialog(null, "Polinomio original:\n" + polinomioOriginal + "\nExponente del termino a eliminar:\n" + exponente + "\nPolinomio total:\n" + Reconstruir());

        }
        
    }

    public int[] EliminarYAjustar(int[] VPF1) {
        int ceros = 0, newGradoMax = 1;
        int gradosVPF1 = (DU - 1);

        // vamos a buscar cuantos ceros (contando el actual) quedaron
        // a la derecha del primer termino

        for (; gradosVPF1 >= 0 && VPF1[newGradoMax] == 0; gradosVPF1--, ceros++, newGradoMax++);
        
        int newVPF1[] = new int[VPF1.length - ceros]; // creamos el nuevo vector con esos ceros eliminados
        newVPF1[0] = gradosVPF1; // se guarda el nuevo grado maximo en la primera posicion

        // "correr" terminos hacia la izquierda
        for (int i = 1, j = 0; i < newVPF1.length; i++, j++) {
            newVPF1[i] = VPF1[newGradoMax + j];
        }

        System.out.println("newVPF sin termino eliminado:\n");
        for (int i = 0; i < newVPF1.length; i++) {
            System.out.print(newVPF1[i] + "|");
        }
        return newVPF1;
    }
    
    public void Mostrar() {
        // muestra el VPF1, obteniendo cada elemento y guardándolo en un string
        String texto = "";

        for (int i = 0; i < VPF1.length; i++) {
            texto += VPF1[i] + " | ";
        }

        JOptionPane.showMessageDialog(null, texto);
    }

    public String Reconstruir() {
        // Revierte (pasa) el polinomio a su forma algebraica
        // si todos los coeficientes son 0, el polinomio es cero
        int banTerminoNoCero = 0;
        for (int i = 1; i < VPF1.length && banTerminoNoCero == 0; i++) {
            if (VPF1[i] != 0) {
                banTerminoNoCero = 1;
            }
        }

        if (banTerminoNoCero == 0) {
            JOptionPane.showMessageDialog(null, "0");
            return "0";
        }

        String cadenaAux = "";
        int gradosVPF1 = (DU - 1); 
        for (int i = 1; i < VPF1.length; i++, gradosVPF1--) {
            
            // cuando se encuentra un 0, no se hace nada
            // y se pasa al siguiente término
            while (i < VPF1.length && VPF1[i] == 0) {
                if (VPF1[i] == 0) {
                i++;
                gradosVPF1--;
                }
            }
            
            if (i < VPF1.length) {
                if (gradosVPF1 == 1) { // El penultimo no tiene exponente

                    if (VPF1[i] > 0) {
                        cadenaAux += ("+" + VPF1[i] + "x");
                        System.out.println("Cadena hasta i = " + i + ": " + cadenaAux);
                    } else {
                        cadenaAux += (VPF1[i] + "x");     
                        System.out.println("Cadena hasta i = " + i + ": " + cadenaAux);
                    }
                    
                    System.out.println("Cadena hasta i = " + i + ": " + cadenaAux);
                } else if (gradosVPF1 == 0) { // El ultimo no tiene x ni exponente

                    if (VPF1[i] > 0) {
                        cadenaAux += ("+" + VPF1[i]);     
                        System.out.println("Cadena hasta i = " + i + ": " + cadenaAux);
                    } else {
                        cadenaAux += (VPF1[i]);     
                        System.out.println("Cadena hasta i = " + i + ": " + cadenaAux);
                    }
                } else {    // Caso para cualquier número

                    if (VPF1[i] > 0) {
                        if (i != 1) {
                            cadenaAux += ("+" + VPF1[i] + "x^" + gradosVPF1);     
                            System.out.println("Cadena hasta i = " + i + ": " + cadenaAux);
                        } else {
                            cadenaAux += (VPF1[i] + "x^" + gradosVPF1);     
                            System.out.println("Cadena hasta i = " + i + ": " + cadenaAux);
                        }
                    } else {
                        cadenaAux += (VPF1[i] + "x^" + gradosVPF1);     
                        System.out.println("Cadena hasta i = " + i + ": " + cadenaAux);
                    }
                }
            }
        }

        return cadenaAux;
    }

    public void Evaluar() {
        // Sería: en todas las posiciones, elevar el numero al grado, y despues multiplicarlo.
        // ese resultado ponerlo en un arreglo string junto al resto de resultados
        // para despues suimar
        String n = JOptionPane.showInputDialog("Ingrese el número por el cual reemplazar:");
        int termino = Integer.parseInt(n); // paso a int el termino ingresado para no tener que
                                           // hacerle parseo a cada rato
        int total = 0;
        String polinomioOriginal = Reconstruir(); // se guarda el polinomio original para poder imprimirlo después
        // elevar termino ingresado (ya que siempre es "termino^n")
        int gradosVPF1 = (DU - 1);
        int[] vectorElevar = new int[VPF1.length];
        for (int i = 1; gradosVPF1 >= 0; i++, gradosVPF1--) {

            // si un grado no tiene exponentes, no existe, por ende se pasa al siguiente
            for (; VPF1[i] == 0; i++, gradosVPF1--);

            vectorElevar[i] = Math.powExact(termino, gradosVPF1);

            // vectorElevar[0] queda vacío, pero no pasa nada porque:
            // - ya tenemos el dato que estábamos buscando
            // - este vector es temporal
        }

        System.out.println("\nvectorElevar:");
        for (int i = 0; i < vectorElevar.length; i++) {
            System.out.print(vectorElevar[i] + "|");
        }

        // multiplicar los datos ya elevados al nuevo vector
        gradosVPF1 = (DU - 1);
        for (int i = 1; gradosVPF1 >= 0; i++, gradosVPF1--) {
            VPF1[i] *= vectorElevar[i];
        }

        System.out.println("\nVPF1 multiplicado:");
        for (int i = 0; i < vectorElevar.length; i++) {
            System.out.print(VPF1[i] + "|");
        }

        // sumar cada termino ya evaluado
        gradosVPF1 = (DU - 1);
        for (int i = 1; gradosVPF1 >= 0; i++, gradosVPF1--) {
            total += VPF1[i];
            System.out.println("Total:" + total);
        }

        System.out.println("Polinomio desp:" + polinomioOriginal);
        JOptionPane.showMessageDialog(null, "Polinomio:\n" + polinomioOriginal + "\nTotal:\n" + total);
    }

    public void Sumar() {
        // Ingresamos el polinomio a sumar
        String cadena = JOptionPane.showInputDialog("Ingrese el polinomio a sumar:");
        /*if (cadena == null || cadena.trim().isEmpty()) {
            return;
        }*/

        // pasamos el polinomio ingresado a un vector de string
        String[] vectorASumar = Polinomios.ConcatenarTermino(cadena);
        String polinomioOriginal = Reconstruir(); // se guarda el polinomio original para poder imprimirlo después


        // creamos una variable para guardar el número de términos del polinomio a sumar
        int numeroTerminosASumar = Polinomios.CantidadNumeros(vectorASumar);
        vectorASumar = Polinomios.OrdenarVector(vectorASumar, numeroTerminosASumar);

        Forma1 f1ASumar = new Forma1(Integer.parseInt(vectorASumar[1])); // se crea otro polinomio en forma1
        f1ASumar.PasarVPF1(vectorASumar); // se pasa el vectorstring (vectorASumar) a forma1

        
        int gradoMaximo = Math.max(VPF1[0], f1ASumar.getVPF1(0)); // se calcula grado maximo para poder
                                                                         // crear un vector con el tamaño del vector
                                                                        // más grande entre los dos polinomios a sumar 
        Forma1 f1Total = new Forma1(gradoMaximo); // se crea otro polinomio en forma1 para el resultado entre ambos 

        int gradosVPF1 = (f1Total.getDU() - 1); // se obtiene el grado máximo del polinomio total
                                                // para recorrerlo desde el mayor grado hasta el menor
        
        for (int i = 1, j = 1 + (gradoMaximo - VPF1[0]), k = 1 + (gradoMaximo - f1ASumar.getVPF1(0)); 
             gradosVPF1 >= 0;                   // j itera sobre VPF1
                                                // k itera sobre el polinomio a sumar
             i++, j++, k++, gradosVPF1--) { 
            
            int coef1 = 0, coef2 = 0;
            // - coef 1 guarda el coeficiente del VPF1
            // - coef 2 guarda el coeficiente del polinomio a sumar

            if (j >= 1 && j < VPF1.length) { // si el término del VPF1 existe,
                coef1 = VPF1[j]; // se guarda su coeficiente en coef1
            }

            if (k >= 1 && k < f1ASumar.getVPF1().length) { // si el término del polinomio a sumar existe,
                coef2 = f1ASumar.getVPF1(k); // se guarda su coeficiente en coef2
            }

            f1Total.setVPF1(i, coef1 + coef2); // se suman ambos coeficientes y se guarda en su respectiva posición
        }

        // reemplazamos el VPF1 por el f1Total
        setVPF1(f1Total.getVPF1());
        setDU(f1Total.getDU());

        JOptionPane.showMessageDialog(null, "Polinomio original:\n" + polinomioOriginal + "\nPolinomios a sumar:\n" + f1ASumar.Reconstruir() + "\nPolinomio total:\n" + f1Total.Reconstruir());
    }

    public void Multiplicar() {
        // Ingresamos el polinomio a multiplicar
        String cadena = JOptionPane.showInputDialog("Ingrese el polinomio a multiplicar:");

        // pasamos el polinomio ingresado a un vector de string
        String[] vectorAMultiplicar = Polinomios.ConcatenarTermino(cadena);
        String polinomioOriginal = Reconstruir(); // se guarda el polinomio original para poder imprimirlo después

        // creamos una variable para guardar el número de términos del polinomio a multiplicar
        int numeroTerminosAMultiplicar = Polinomios.CantidadNumeros(vectorAMultiplicar);
        vectorAMultiplicar = Polinomios.OrdenarVector(vectorAMultiplicar, numeroTerminosAMultiplicar);

        Forma1 f1AMultiplicar = new Forma1(Integer.parseInt(vectorAMultiplicar[1])); // se crea otro polinomio en forma1
        f1AMultiplicar.PasarVPF1(vectorAMultiplicar); // se pasa el vectorstring (vectorAMultiplicar) a forma1

        // para hallar el grado maximo entre VPF1 y f1AMultiplicar, sumamos sus grados máximos
        int gradoMaximo = VPF1[0] + f1AMultiplicar.getVPF1(0); 
        Forma1 f1Total = new Forma1(gradoMaximo); // se crea el polinomio resultado 

        int gradosVPF1 = (DU - 1);

        for (int i = 1; i < VPF1.length; i++, gradosVPF1--) {
            if (VPF1[i] != 0) { // si el coeficiente actual no es cero
                int gradosF1AMultiplicar = (f1AMultiplicar.getDU() - 1); // lo mismo que gradosVPF1, pero para iterar
                //                                                          sobre el f1AMultiplicar
                for (int j = 1; j < f1AMultiplicar.getVPF1().length; j++, gradosF1AMultiplicar--) {
                    if (f1AMultiplicar.getVPF1(j) != 0) { // si el coeficiente del f1AMultiplicar no es cero,
                                                                   
                        int posicionGradoResultado = (gradoMaximo - (gradosVPF1 + gradosF1AMultiplicar)) + 1;   // se convierte ese grado
                        //                                             ^                                           a su respectiva posición en f1Total
                                                                    // ^ se calcula el grado de la posición en f1Total en la que se va a guardar el coeficiente
                        int acumulado = f1Total.getVPF1(posicionGradoResultado) + (VPF1[i] * f1AMultiplicar.getVPF1(j)); // se multiplica el coeficiente del VPF1
                        //                                                                                                  por el coeficiente del f1AMultiplicar,
                        //                                                                                                  y se suma al coeficiente que ya estaba 
                        //                                                                                                  en esa posición (en caso de que ya haya algo ahí)
                        //
                        f1Total.setVPF1(posicionGradoResultado, acumulado); // guarda el resultado de la multiplicación
                    }
                }
            }
        }

        // reemplazamos el VPF1 por el f1Total
        setVPF1(f1Total.getVPF1());
        setDU(f1Total.getDU());

        JOptionPane.showMessageDialog(null, "Polinomio original:\n" + polinomioOriginal + "\nPolinomios a sumar:\n" + f1AMultiplicar.Reconstruir() + "\nPolinomio total:\n" + f1Total.Reconstruir());
    }
}
