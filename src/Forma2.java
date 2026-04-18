import javax.swing.JOptionPane;

public class Forma2 {
    // Atributos
    private int DU, VPF2[];

    // Constructor

    public Forma2(int Terminos) {
        this.DU = Terminos * 2; // cada término en forma2 ocupa 2 casillas (coeficiente y exponente)
        this.VPF2 = new int[DU + 1];
        VPF2[0] = Terminos; // primer dato = cantidad de términos
    }

    // Getters y Setters

    public int getDU() {
        return DU;
    }

    public void setDU(int DU) {
        this.DU = DU;
    }

    public int[] getVPF2() { // se devuelve el vector completo
        return VPF2;
    }

    public void setVPF2(int[] VPF2) { // se obtiene el vector completo para modificarlo
        this.VPF2 = VPF2;
    }

    public int getVPF2(int i) {
        return VPF2[i];
    }

    public void setVPF2(int i, int d) {
        this.VPF2[i] = d;
    }

    // Metodos

    public void PasarVPF2(String[] vectorString) {
        // se busca pasar cada dato del vectorString al VPF2,
        // donde VPF2[0] es la cantidad de términos y desde la
        // posición 1 se guarda coef - exp...
        for (int i = 1; i < VPF2.length; i++) {
            if (vectorString[i - 1] != null) {
                setVPF2(i, Integer.parseInt(vectorString[i - 1]));
            }
        }

        System.out.println("\nVPF2:");
        for (int i = 0; i < VPF2.length; i++) {
            System.out.print(VPF2[i] + "|");
        }
    }

    public void Insertar() {
        // Ingresamos el término a insertar
        String termino = JOptionPane.showInputDialog("Ingrese el término a insertar:");
        String polinomioOriginal = Reconstruir(); // se guarda el polinomio original para poder imprimirlo después

        // pasamos el término ingresado a un vector de string
        String[] terminoConcatenado = new String[Polinomios.ConcatenarTermino(termino).length];
        terminoConcatenado = Polinomios.ConcatenarTermino(termino);

        // reemplazamos el VPF2 por el nuevo vector ajustado
        setVPF2(InsertarYAjustar(VPF2, terminoConcatenado));
        setDU(getVPF2(0) * 2);

        JOptionPane.showMessageDialog(null, "Polinomio original:\n" + polinomioOriginal + "\nTermino a insertar:\n" + termino + "\nPolinomio total:\n" + Reconstruir());

    }

    public int[] InsertarYAjustar(int[] VPF2, String[] terminoConcatenado) {
        int[] newVPF2 = VPF2;

        // terminoConcatenado[0] es el coef
        // terminoConcatenado[1] es el grado
        int posicionTermino = -1, posicionDondeInsertar = -1, ban = 0;
        /*posiciónTérmino guarda donde insertar SI Y SOLO SI se encontró un grado existente que concuerda con el grado a insertar
          posicionDondeInsterar guaarda donde poner un grado que no esté en el arreglo,
            ademas de que funciona como verificador de si ya se pasó por el elseif:
            si el valor cambia de -1, eso significa que se encontró una posición dónde insertar. 
        */
  
        for (int i = 1; i < VPF2.length; i += 2) {  
            if (VPF2[i + 1] == (Integer.parseInt(terminoConcatenado[1]))) { // en cada termino se compara el grado actual con el grado nuevo
                posicionTermino = i;        // si el grado es igual, entonces se guarda la posición de su respectivo coeficiente
                i = VPF2.length;            // se fuerza salida del ciclo
                ban = 1;                    
            } else if (posicionDondeInsertar == -1 && VPF2[i + 1] < (Integer.parseInt(terminoConcatenado[1]))) {
                posicionDondeInsertar = i;      // sino, siencuentra el primer grado menor: guarda esa posición para insertar ahí
            }                                   // si no encuentra ninguno menor: inserta al final (porque era el grado más pequeño)
        }

        // si el grado ya existe, se suma el coeficiente en la misma posición
        if (ban == 1) {
            newVPF2 = new int[VPF2.length]; 
            // se copia todo el VPF2 en otro vector
            for (int i = 0; i < VPF2.length; i++) {
                newVPF2[i] = VPF2[i];
            }

            // se suma el respectivo termino
            newVPF2[posicionTermino] += (Integer.parseInt(terminoConcatenado[0]));

            // si se anula, se elimina ese término y se ajusta el vector
            if (newVPF2[posicionTermino] == 0) {
                newVPF2 = EliminarYAjustar(newVPF2, Integer.parseInt(terminoConcatenado[1]));
            }

            ban = 1;

            System.out.println("\nnewVPF2 con término sumado:");
            for (int i = 0; i < newVPF2.length; i++) {
                System.out.print(newVPF2[i] + "|");
            }
        } else {
            // si NO existe el grado a insertar, se inserta en su lugar ordenado
            newVPF2 = new int[VPF2.length + 2]; // se crea newVPF2 con 2 casillas adicionales
                                                // para añadir el neuvo temrino
            newVPF2[0] = VPF2[0] + 1;

            // se busca donde insertar ese malparido termino asqueroso infeliz
            for (int i = 1, j = 1; i < VPF2.length; i += 2) {
                // i itera sobre VPF2
                // j itera sobre newVPF2
                // y estoy a punto de añadir otro iterador para la cantidad
                // de horas que este trabajo ha hecho mi vida más decadente

                if (j == posicionDondeInsertar) {
                    // si se encontró la posición dónde insertar, pues se inserta
                    newVPF2[j] = (Integer.parseInt(terminoConcatenado[0]));     
                    newVPF2[j + 1] = (Integer.parseInt(terminoConcatenado[1]));
                    j += 2;
                }

                // se va copiando el VPF2 al nuevo vector
                newVPF2[j] = VPF2[i];
                newVPF2[j + 1] = VPF2[i + 1];
                j += 2;
            }

            // inserción al final (cuando el nuevo grado es el menor de todos)
            if (posicionDondeInsertar == VPF2.length) {
                newVPF2[newVPF2.length - 2] = (Integer.parseInt(terminoConcatenado[0]));
                newVPF2[newVPF2.length - 1] = (Integer.parseInt(terminoConcatenado[1]));
            }

            System.out.println("\nnewVPF2 con término insertado:");
            for (int i = 0; i < newVPF2.length; i++) {
                System.out.print(newVPF2[i] + "|");
            }
        }
        return newVPF2;
    }

    public void Eliminar() {
        // Ingresamos el grado del término a eliminar
        String exponente = JOptionPane.showInputDialog("Ingrese el exponente del término a eliminar:");
        String polinomioOriginal = Reconstruir(); // se guarda el polinomio original para poder imprimirlo después

        // reemplazamos el VPF2 por el nuevo vector ajustado
        setVPF2(EliminarYAjustar(VPF2, Integer.parseInt(exponente)));
        setDU(getVPF2(0) * 2);

        JOptionPane.showMessageDialog(null, "Polinomio original:\n" + polinomioOriginal + "\nExponente del término a eliminar:\n" + exponente + "\nPolinomio total:\n" + Reconstruir());

    }

    public int[] EliminarYAjustar(int[] VPF2, int gradoAEliminar) {
        int[] newVPF2 = VPF2;
        int posicionDondeEliminar = -1;
                                // este metodo es bastante similar a insertar: -1 se usa como una bandera
                                // y despues se usa la variable para guardar dónde eliminar
                                // es la 1am del miercoles 4 y me estoy volviendo completamente loco
        
        // se busca la posición del grado a eliminar
        for (int i = 1; i < VPF2.length; i += 2) {
            if (VPF2[i + 1] == gradoAEliminar) {
                posicionDondeEliminar = i;
                i = VPF2.length;
            }
        }

        // si se encontró una posición dónde eliminar
        if (posicionDondeEliminar != -1) {
            // si era el único término, queda polinomio cero
            if (VPF2[0] == 1) {
                newVPF2 = new int[]{0};
            } else {
                newVPF2 = new int[VPF2.length - 2];
                newVPF2[0] = VPF2[0] - 1;

                for (int i = 1, j = 1; i < VPF2.length; i += 2) {
                    // i itera sobre VPF2
                    // j itera sobre newVPF2

                    // si NO se ha encontrado la posición a eliminar, 
                    if (i != posicionDondeEliminar) {
                        // se copia todo al nuevo vector EXCEPTO la posicion a eliminar
                        newVPF2[j] = VPF2[i];
                        newVPF2[j + 1] = VPF2[i + 1];
                        j += 2;
                    }
                }
            }

            System.out.println("\nnewVPF2 tras eliminar término:");
            for (int i = 0; i < newVPF2.length; i++) {
                System.out.print(newVPF2[i] + "|");
            }
        }

        // si no se encontró posición, eso significa que el término a eliminar no existía,
        // por lo que se pasa igual
        return newVPF2;
    }
    
    public void Mostrar() {
        // ni para qué comento este método
        String texto = "";

        for (int i = 0; i < VPF2.length; i++) {
            texto += VPF2[i] + " | ";
        }

        JOptionPane.showMessageDialog(null, texto);
    }

    public String Reconstruir() {
        // pasa el polinomio a su forma algebraica
    
        String cadenaAux = "";

        for (int i = 1; i < VPF2.length; i += 2) {
            // para términos positivos (excepto el primero), se añade el signo +
            if (VPF2[i] > 0 && i != 1) {
                cadenaAux += "+";
            }

            // estructura de impresión según el grado actual
            if (VPF2[i + 1] == 0) {         // si el grado es cero (constante)
                cadenaAux += VPF2[i];       // simplemente se pasa el coeficiente
            } else if (VPF2[i + 1] == 1) {          // si el grado es 1 (x sin exponente)
                cadenaAux += (VPF2[i] + "x");       // se pone solo la x
            } else {
                cadenaAux += (VPF2[i] + "x^" + VPF2[i + 1]);
            }
        }

        return cadenaAux;
    }

    public void Evaluar() {
        // Sería: en todos los términos, elevar el número al grado
        // y multiplicarlo por su coeficiente, para después sumar
        String n = JOptionPane.showInputDialog("Ingrese el número por el cual reemplazar:");
        int termino = Integer.parseInt(n);
        int total = 0;

        // elevar y multiplicar término a término
        for (int i = 1; i < VPF2.length; i += 2) {
            total += (VPF2[i] * Math.powExact(termino, VPF2[i + 1]));
        }

        // a diferencia de la desgraciada forma1, acá sí lo podemos hacer todo con los datos del mismo arreglo
        JOptionPane.showMessageDialog(null, "Total:\n" + total);
    }

    public void Sumar() {
        // Ingresamos el polinomio a sumar
        String cadena = JOptionPane.showInputDialog("Ingrese el polinomio a sumar:");
        String polinomioOriginal = Reconstruir(); // se guarda el polinomio original para poder imprimirlo después

        // pasamos el polinomio ingresado a un vector de string
        String[] vectorASumar = Polinomios.ConcatenarTermino(cadena);

        // creamos una variable para guardar la cantidad de números del polinomio a sumar
        int cantidadNumerosASumar = Polinomios.CantidadNumeros(vectorASumar);
        vectorASumar = Polinomios.OrdenarVector(vectorASumar, cantidadNumerosASumar);

        Forma2 f2ASumar = new Forma2(Polinomios.CantidadTerminos(vectorASumar)); // se crea otro polinomio en forma2
        f2ASumar.PasarVPF2(vectorASumar); // se pasa el vectorstring (vectorASumar) a forma2

        // se crea una copia del VPF2 actual para ir guardando la suma total
        int[] vectorTotal = new int[VPF2.length];
        for (int i = 0; i < VPF2.length; i++) {
            vectorTotal[i] = VPF2[i];
        }

        // se recorren los términos del segundo polinomio y se insertan en el primero
        for (int i = 1; i < f2ASumar.getVPF2().length; i += 2) {
            String[] terminoConcatenado = new String[2];
            terminoConcatenado[0] = Integer.toString(f2ASumar.getVPF2(i));
            terminoConcatenado[1] = Integer.toString(f2ASumar.getVPF2(i + 1));

            // se va acumulando la suma término por término del segundo polinomio.
            // es como si se hiciera "+=". como mi InsertarYAjustar también suma lo que se inserte,
            // entonces todo realmente se suma dentro de ese metodo.

            // la diferencia es que vectorTotal se va sobreescribiendo en cada ciclo, pero insertando término
            // a término: se cambia el vector COMPLETO por uno nuevo
            vectorTotal = InsertarYAjustar(vectorTotal, terminoConcatenado);
        }

        // reemplazamos el VPF2 por el vector total
        setVPF2(vectorTotal);
        setDU(getVPF2(0) * 2);

        System.out.println("\nVPF2 sumado:");
        for (int i = 0; i < VPF2.length; i++) {
            System.out.print(VPF2[i] + "|");
        }
        
        JOptionPane.showMessageDialog(null, "Polinomio original:\n" + polinomioOriginal + "\nPolinomio a sumar:\n" + f2ASumar.Reconstruir() + "\nPolinomio total:\n" + Reconstruir());

    }

    public void Multiplicar() {
        // Ingresamos el polinomio a multiplicar
        String cadena = JOptionPane.showInputDialog("Ingrese el polinomio a multiplicar:");
        String polinomioOriginal = Reconstruir(); // se guarda el polinomio original para poder imprimirlo después

        // pasamos el polinomio ingresado a un vector de string
        String[] vectorAMultiplicar = Polinomios.ConcatenarTermino(cadena);

        // creamos una variable para guardar la cantidad de números del polinomio a multiplicar
        int cantidadNumerosAMultiplicar = Polinomios.CantidadNumeros(vectorAMultiplicar);
        vectorAMultiplicar = Polinomios.OrdenarVector(vectorAMultiplicar, cantidadNumerosAMultiplicar);

        Forma2 f2AMultiplicar = new Forma2(Polinomios.CantidadTerminos(vectorAMultiplicar)); // se crea otro polinomio en forma2
        f2AMultiplicar.PasarVPF2(vectorAMultiplicar); // se pasa el vectorstring (vectorAMultiplicar) a forma2

        // vector base para ir acumulando los términos del producto
        int[] vectorTotal = {0};

        // multiplicación término a término y suma por grado
        for (int i = 1; i < VPF2.length; i += 2) {
            if (VPF2[i] != 0) {
                for (int j = 1; j < f2AMultiplicar.getVPF2().length; j += 2) {
                    // i itera sobre VPF2
                    // j itera sobre el f2AMultiplicar

                    if (f2AMultiplicar.getVPF2(j) != 0) {
                        String[] terminoConcatenado = new String[2]; // se crea el termino concatenado para pasarle a insertaryajustar

                        // se hace la operación de multiplicar (sumar grados, multiplicar coeficientes)
                        terminoConcatenado[0] = Integer.toString(VPF2[i] * f2AMultiplicar.getVPF2(j));
                        terminoConcatenado[1] = Integer.toString(VPF2[i + 1] + f2AMultiplicar.getVPF2(j + 1));

                        // se va almacenando el resultado en vectorTotal, tal y como en el método de suma
                        vectorTotal = InsertarYAjustar(vectorTotal, terminoConcatenado);
                    }
                }
            }
        }

        // reemplazamos el VPF2 por el vector total
        setVPF2(vectorTotal);
        setDU(getVPF2(0) * 2);

        System.out.println("\nVPF2 multiplicado:");
        for (int i = 0; i < VPF2.length; i++) {
            System.out.print(VPF2[i] + "|");
        }
        JOptionPane.showMessageDialog(null, "Polinomio original:\n" + polinomioOriginal + "\nPolinomio a multiplicar:\n" + f2AMultiplicar.Reconstruir() + "\nPolinomio total:\n" + Reconstruir());
    }

}
