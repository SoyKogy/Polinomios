import javax.swing.JOptionPane;
/* Practica 1 polinomios
 * Creador por Thomas Madrigal Morales
 * 
 * Hecho con ayuda de Github Copilot y ChatGPT Codex 
 * con fines educativos
 * 
 * 1. Ingreso del polinomio en string
 * 2. Insertar termino
 * 3. Eliminar termino
 * 4. Mostrar forma
 * 5. Mostrar polinomio (reconstruir a partir del vector)
 * 6. Evaluar polinomio segun el valor de x ingresado por el usuario
 * 7. Sumar polinomios en la misma forma (ingresar otro polinomio y sumarlo al primero)
 * 8. Multiplicar polinomios en la misma forma (ingresar otro polinomio y multiplicarlo al primero)
 * 9. F2 + F3 = F1
 * 10. F3 * F1 = F2
 * 
 * Tests para copiar y pegar:
 * 6x^4-3x^2
 * 6x^4-3x^2+10x-1
 * 6x^4+3x^2+5x^4+10x-1+3x^2+5x^5+5x^5+1
 * 6x^4+3x^2+5x^4+10x-1+3x^2-2+5x^5+5x^5+1 
 * 
*/
public class Polinomios {
    public static void main(String[] args) throws Exception {
        int opc = 0; // variable para el menu
        int opcForma = 0; // variable para seleccionar la forma

        /*
        Creación de la forma 1
        */
        Forma1 F1; // se declara el objeto de la clase Forma1

        String vectorString[] = CrearPolinomio(); // se crea el polinomio 
        int numeroTerminos = CantidadNumeros(vectorString); // se obtiene el número de términos del polinomio
                                                           // sin usar .length ya que las ultimas casillas 
                                                           // contienen NULL

        vectorString = OrdenarVector(vectorString, numeroTerminos); // se ordena el vector de términos del polinomio

        F1 = new Forma1(Integer.parseInt(vectorString[1])); // se crea el objeto de la clase Forma1
                     // ^ pasa el vectorString a int        // con el grado del polinomio, que se
                                                            // encuentra en el segundo término 
                                                            // del vectorString
        F1.PasarVPF1(vectorString);

        /*
        Creación de la forma 2
        */
        Forma2 F2 = new Forma2(CantidadTerminos(vectorString));
        F2.PasarVPF2(vectorString);
        /*
        Creación de la forma 3
        */
        Forma3 F3 = new Forma3();
        F3.PasarVPF3(vectorString);

        // Menú principal
        do {
            opcForma = MenuPrincipal();
            switch (opcForma) {
                // Menú forma 1
                case 1:
                    do {
                        opc = Menu();
                        switch (opc) {
                            case 2: 
                                F1.Insertar();
                                break;
                            case 3:
                                F1.Eliminar();
                                break;
                            case 4:
                                F1.Mostrar();
                                break;
                            case 5:
                                F1.Reconstruir();
                                break;
                            case 6:
                                F1.Evaluar();
                                break;
                            case 7:
                                F1.Sumar();
                                break;
                            case 8:
                                F1.Multiplicar();
                                break;
                            case 0:
                                break;
                            default:
                                throw new AssertionError();
                        }
                    } while (opc != 0);
                    break;

                // Menú forma 2
                case 2:
                    do {
                        opc = Menu();
                        switch (opc) {
                            case 2:
                                F2.Insertar();
                                break;
                            case 3:
                                F2.Eliminar();
                                break;
                            case 4:
                                F2.Mostrar();
                                break;
                            case 5:
                                F2.Reconstruir();
                                break;
                            case 6:
                                F2.Evaluar();
                                break;
                            case 7:
                                F2.Sumar();
                                break;
                            case 8:
                                F2.Multiplicar();
                                break;
                            case 0:
                                break;
                            default:
                                throw new AssertionError();
                        }
                    } while (opc != 0);
                    break;

                // Menú forma 3
                case 3:
                    do {
                        opc = Menu();
                        switch (opc) {
                            case 2:
                                F3.Insertar();
                                break;
                            case 3:
                                F3.Eliminar();
                                break;
                            case 4:
                                F3.Mostrar();
                                break;
                            case 5:
                                F3.Reconstruir();
                                break;
                            case 6:
                                F3.Evaluar();
                                break;
                            case 7:
                                F3.Sumar();
                                break;
                            case 8:
                                F3.Multiplicar();
                                break;
                            case 0:
                                break;
                            default:
                                throw new AssertionError();
                        }
                    } while (opc != 0);
                    break;

                case 4:
                    SumarF2F3aF1(F1);
                    break;

                case 5:
                    MultiplicarF3F1aF2(F2);
                    break;

                case 0:
                    System.out.println("Saliendo del programa...");
                    return;
                default:
                    throw new AssertionError();
            }
        } while (true);
    }

    public static int MenuPrincipal() {
        int opcForma = Integer.parseInt(JOptionPane.showInputDialog("*** Menú Principal =) ***\n" +
                                        "1. Forma 1\n" +
                                        "2. Forma 2\n" +
                                        "3. Forma 3\n" +
                                        "4. F2 + F3 = F1\n" +
                                        "5. F3 * F1 = F2\n" +
                                        "0. Salir\n" +
                                        "Ingrese una opción: "));
        return opcForma;
    }

    public static int Menu() {
        int opc = Integer.parseInt(JOptionPane.showInputDialog("*** Menú de Operaciones =) ***\n" +
                                    "2. Insertar término\n" +
                                    "3. Eliminar término\n" +
                                    "4. Mostrar forma interna\n" +
                                    "5. Mostrar polinomio reconstruido\n" +
                                    "6. Evaluar polinomio\n" +
                                    "7. Sumar otro polinomio\n" +
                                    "8. Multiplicar por otro polinomio\n" +
                                    "0. Regresar al menú principal\n" +
                                    "Ingrese una opción: "));
        return opc;
    }

    public static int CantidadNumeros(String vectorString[]) {
        int cont = 0;

        // Ciclo para ocntarr la cantidad de numeros en el vectorString,
        // que es el vector que contiene los términos del polinomio
        for (int i = 0; i < vectorString.length; i++) {
            if (vectorString[i] != null) {
                cont++;
            }
        }
        return cont;

    }

    public static int CantidadTerminos(String vectorString[]) {
        int cont = 0;

        // Ciclo para ocntarr la cantidad de numeros en el vectorString,
        // que es el vector que contiene los términos del polinomio
        for (int i = 0; i + 1 < vectorString.length; i += 2) {
            if (vectorString[i] != null && vectorString[i + 1] != null) {
                cont++;
            }
        }

        return cont;
    }

    public static String[] CrearPolinomio() {

        String cadena = JOptionPane.showInputDialog("Ingrese el polinomio: ");

        String vectorString[] = new String[ConcatenarTermino(cadena).length];
        vectorString = ConcatenarTermino(cadena);

        return vectorString;
    }

    private static String[] PedirPolinomioOrdenado(String mensaje) {
        // pide un polinomio en forma algebraica y lo pasa a coef-exp ordenado
        // para poder usarlo directamente en las operaciones entre formas
        String cadena = JOptionPane.showInputDialog(mensaje);
        String[] vectorTemporal = ConcatenarTermino(cadena);
        int numeroTerminos = CantidadNumeros(vectorTemporal);
        return OrdenarVector(vectorTemporal, numeroTerminos);
    }

    private static void SumarF2F3aF1(Forma1 F1) {
        // se piden dos polinomios nuevos temporales: uno para F2 y otro para F3
        String[] vectorF2Temporal = PedirPolinomioOrdenado("Ingrese el polinomio para F2:");
        String[] vectorF3Temporal = PedirPolinomioOrdenado("Ingrese el polinomio para F3:");

        // se construyen objetos temporales para operar sin modificar las formas actuales
        Forma2 f2Temporal = new Forma2(CantidadTerminos(vectorF2Temporal));
        f2Temporal.PasarVPF2(vectorF2Temporal);
        Forma3 f3Temporal = new Forma3();
        f3Temporal.PasarVPF3(vectorF3Temporal);

        System.out.println("\nF2 temporal ordenado:");
        for (int i = 0; i < vectorF2Temporal.length; i++) {
            System.out.print(vectorF2Temporal[i] + "|");
        }
        System.out.println("\nF3 temporal ordenado:");
        for (int i = 0; i < vectorF3Temporal.length; i++) {
            System.out.print(vectorF3Temporal[i] + "|");
        }

        // por defecto asumimos que F3 no tiene términos (grado mayor = 0)
        int gradoMayorF3 = 0;

        // si F3 sí tiene al menos un nodo, su punta guarda el término de mayor grado
        if (f3Temporal.getPunta() != null) {
            gradoMayorF3 = f3Temporal.getPunta().getExp();
        }

        // se compara el mayor grado de F2 con el mayor grado de F3
        // y se toma el más grande para crear el F1 resultado
        int gradoMayorResultado = Math.max(f2Temporal.getVPF2(2), gradoMayorF3);

        Forma1 f1Resultado = new Forma1(gradoMayorResultado);

        // se pasa cada término de F2 temporal a forma1 (sumando por posición de grado)
        for (int i = 1; i + 1 < f2Temporal.getVPF2().length; i += 2) {
            if (f2Temporal.getVPF2(i) != 0) {                 // si el coeficiente es 0, ese término no aporta nada, se ignora
                int posicionGrado = (gradoMayorResultado - f2Temporal.getVPF2(i + 1)) + 1;
                f1Resultado.setVPF1(posicionGrado, f1Resultado.getVPF1(posicionGrado) + f2Temporal.getVPF2(i));
            }
        }

        // se pasa cada nodo de F3 temporal a forma1 (sumando por posición de grado)
        for (Nodo p = f3Temporal.getPunta(); p != null; p = p.getLiga()) {
            if (p.getCoe() != 0) {
                int posicionGrado = (gradoMayorResultado - p.getExp()) + 1;
                f1Resultado.setVPF1(posicionGrado, f1Resultado.getVPF1(posicionGrado) + p.getCoe());
            }
        }

        System.out.println("\nVPF1 resultado:");
        for (int i = 0; i < f1Resultado.getVPF1().length; i++) {
            System.out.print(f1Resultado.getVPF1(i) + "|");
        }

        // solo se actualiza el destino (F1). F2 y F3 originales no se tocan.
        F1.setVPF1(f1Resultado.getVPF1());
        F1.setDU(f1Resultado.getDU());

        // se muestra estructura interna y luego reconstrucción algebraica
        F1.Mostrar();
        F1.Reconstruir();
    }

    private static void MultiplicarF3F1aF2(Forma2 F2) {
        // se piden dos polinomios nuevos temporales: uno para F3 y otro para F1
        String[] vectorF3Temporal = PedirPolinomioOrdenado("Ingrese el polinomio para F3:");
        String[] vectorF1Temporal = PedirPolinomioOrdenado("Ingrese el polinomio para F1:");

        // se construyen objetos temporales para operar sin modificar las formas actuales
        Forma3 f3Temporal = new Forma3();
        f3Temporal.PasarVPF3(vectorF3Temporal);
        Forma1 f1Temporal = new Forma1(Integer.parseInt(vectorF1Temporal[1]));
        f1Temporal.PasarVPF1(vectorF1Temporal);

        System.out.println("\nF3 temporal ordenado:");
        for (int i = 0; i < vectorF3Temporal.length; i++) {
            System.out.print(vectorF3Temporal[i] + "|");
        }
        System.out.println("\nF1 temporal ordenado:");
        for (int i = 0; i < vectorF1Temporal.length; i++) {
            System.out.print(vectorF1Temporal[i] + "|");
        }

        // se crea forma2 temporal en cero para ir acumulando los productos
        Forma2 f2Resultado = new Forma2(1);
        f2Resultado.PasarVPF2(new String[]{"0", "0"});

        String[] terminoProducto = new String[2]; // este vector se utiliza para almacenar el término producto

        // multiplicación directa entre términos de F3 y términos de F1
        for (Nodo p = f3Temporal.getPunta(); p != null; p = p.getLiga()) {

            int gradosF1Temporal = f1Temporal.getDU() - 1; // gradosF1Temporal se utiliza como contador 
                                                           // para saber en que grado estamos de F1                                                     
            for (int i = 1; i < f1Temporal.getVPF1().length; i++, gradosF1Temporal--) {

                // si el término de F3 y el término de F1 no son cero, se multiplican y se acumulan en el resultado
                if (p.getCoe() != 0 && f1Temporal.getVPF1(i) != 0) {
                    // InsertarYAjustar acumula en forma2 por grado (si existe, suma. si no, inserta)
                    terminoProducto[0] = Integer.toString(p.getCoe() * f1Temporal.getVPF1(i)); // coeficiente del término producto
                    terminoProducto[1] = Integer.toString(p.getExp() + gradosF1Temporal);   // exponente del término producto

                    // se inserta el término producto en el vector de forma2 resultado
                    // sumando por grado si ya existe un término con ese grado
                    f2Resultado.setVPF2( f2Resultado.InsertarYAjustar( f2Resultado.getVPF2(), terminoProducto) );
                                        // ^ a InsertarYAjustar se le pasa el vector actual acumulado y 
                                        // el término producto (osea el term ya multiplicado)
                    // ^ el polinomio insertado y ajustado se le pasa a f2Resultado para actualizar el acumulado.

                }
            }
        }

        // se actualiza DU del objeto temporal con su vector final
        f2Resultado.setDU(f2Resultado.getVPF2(0) * 2);

        System.out.println("\nVPF2 resultado:");
        for (int i = 0; i < f2Resultado.getVPF2().length; i++) {
            System.out.print(f2Resultado.getVPF2(i) + "|");
        }

        // solo se actualiza el destino (F2). F1 y F3 originales no se tocan.
        F2.setVPF2(f2Resultado.getVPF2());
        F2.setDU(f2Resultado.getDU());

        // se muestra estructura interna y luego reconstrucción algebraica
        F2.Mostrar();
        F2.Reconstruir();
    }

    public static String[] OrdenarVector(String vectorString[], int numeroTerminos) {

        int maxExp = 0, terminoActual[] = new int[2]; // 0 para coeficiente, 1 para exponente
        String vectorSumado[] = new String[numeroTerminos];

        int contTerminosSumados[] = new int[numeroTerminos]; // este vector se utiliza para
                                                            // llevar un conteo de los 
                                                            // términos que ya han sido 
                                                            // sumados, para evitar sumarlos 
                                                            // más de una vez
        /* vectorString es coef - exp - coef - exp - coef - exp
           iteramos sobre casillas pares para hallar max grado
           iteramos sobre casillas IMpares para sumar o restar
        */
        
        System.out.println("\nvectorString: ");
        for (int i = 0; i < numeroTerminos; i++) {
            System.out.print(vectorString[i] + "|");
        }
        // Suma de terminos con mismo grado
        for (int iVs = 0, jVs = 1, iVsum = 0, jVsum = 1; iVs < numeroTerminos && jVs < numeroTerminos; iVs += 2, jVs += 2) {
            if (contTerminosSumados[jVs] == 0) {
                terminoActual[0] = Integer.parseInt(vectorString[iVs]); // coeficiente
                terminoActual[1] = Integer.parseInt(vectorString[jVs]); // exponente

                // si terminoActual[1] == vectorString[j], entonces se suman los coeficientes
                // se empieza a comparar con el grado del siguiente término. el objetivo es no
                // borrar nada del vectorString.

                for (int k = (jVs + 2); k < numeroTerminos; k += 2) {
                    if ((terminoActual[1] == Integer.parseInt(vectorString[k])) && contTerminosSumados[k] == 0) {
                        terminoActual[0] += Integer.parseInt(vectorString[k - 1]); // se suman los coeficientes
                                                                        // ^ es k-1 porque el coeficiente
                                                                        // está en la casilla anterior 
                                                                        // al exponente
                        contTerminosSumados[k]++; // se marca el término como sumado
                    }
                    if (maxExp < terminoActual[1]) {
                        maxExp = terminoActual[1]; // se actualiza el grado máximo
                    }
                }

                vectorSumado[iVsum] = Integer.toString(terminoActual[0]); // se guarda el coeficiente en el nuevo vector
                vectorSumado[jVsum] = Integer.toString(terminoActual[1]); // se guarda el exponente en el nuevo vector
                iVsum += 2;
                jVsum += 2;
            }
        }

        System.out.println("\nvectorSumado: ");
        for (int i = 0; i < numeroTerminos; i++) {
            System.out.print(vectorSumado[i] + "|");
        }
        // vectorSumado es el vectorString pero con los términos de igual grado sumados,
        // aunque no ordenados. se ordenará vectorSumado en funcion del grado, de mayor a menor,
        // teniendo en cuenta que el coeficiente siempre acompaña al grado, por lo que si se 
        // mueve un grado, se mueve el coeficiente que le corresponde.

        // Insertion sort
        for (int i = 2; i + 1 < numeroTerminos; i += 2) {
            if (vectorSumado[i] != null && vectorSumado[i + 1] != null) {

                int coefActual = Integer.parseInt(vectorSumado[i]); // se guarda el coeficiente actual antes de moverlo
                int expActual = Integer.parseInt(vectorSumado[i + 1]); // se guarda el exponente actual antes de moverlo
                
                int j = i - 2;
                while (j >= 0 && vectorSumado[j + 1] != null
                        && Integer.parseInt(vectorSumado[j + 1]) < expActual) {
                    vectorSumado[j + 2] = vectorSumado[j];
                    vectorSumado[j + 3] = vectorSumado[j + 1];
                    j -= 2;
                }

                vectorSumado[j + 2] = Integer.toString(coefActual);
                vectorSumado[j + 3] = Integer.toString(expActual);
            }
        }

        System.out.println("\nvector ordenado: ");
        for (int i = 0; i < numeroTerminos; i++) {
            System.out.print(vectorSumado[i] + "|");
        }


        int cont = 0;
        // Ciclo para contar el número de términos en el vectorSumado,
        // que es el vector que contiene los términos del polinomio
        for (int i = 0; i < vectorSumado.length; i++) {
            if (vectorSumado[i] != null) {
                cont++;
            }
        }

        String vectorFinal[] = new String[cont];

        // Ciclo para copiar los términos del vectorSumado al vectorFinal
        for (int i = 0; i < cont; i++) {
            vectorFinal[i] = vectorSumado[i];
        }
        
        System.out.println("\nvector final: ");
        for (int i = 0; i < vectorFinal.length; i++) {
            System.out.print(vectorFinal[i] + "|");
        }

        return vectorFinal;
    }

    public static String[] ConcatenarTermino(String cadena) {
        
        // ConcatenarTermino() pasa el input de forma algebraica a forma coef - exp - ...
        
        char vectorChar[] = cadena.toCharArray(); // convierte la cadena en un arreglo de caracteres
        String vectorString[] = new String[Math.max(2, vectorChar.length * 2)], terminoActual = "";

        for (int i = 0, j = 0; i < vectorChar.length; i++) {

            
            // Si el termino actual de arreglo es un #numero# Y el siguiente es un 'signo' 
            
            // Si el término actual del arreglo es un '-' o un numero
            if (vectorChar[i] == '-' || Character.isDigit(vectorChar[i])) {
                terminoActual += vectorChar[i]; // Lo mete en una variable auxiliar con el fin de hacer una cadena
                                                // y conectarlo con el siguiente numero.

                                                // por ejemplo, si hay "-6x" se almacena primero el "-"
                                                // y despues el "6" para formar "-6", sin la x.
                if (i != (vectorChar.length - 1)) { /* si el termino actual del arreglo no es el último, 
                                                       se revisa si el siguiente es un signo o una variable.
                                                     */
                    if (Character.isDigit(vectorChar[i]) && (vectorChar[i + 1] == '-' || vectorChar[i + 1] == '+')) {
                        // este condicional es para el caso de constantes.

                        vectorString[j] = terminoActual;    
                        terminoActual = "";
                        vectorString[j + 1] = "0"; // si el término es una constante, se le asigna un exponente de 0
                        j += 2;
                    } 
                } else {
                    if (Character.isDigit(vectorChar[i])) {
                        vectorString[j] = terminoActual;    
                        terminoActual = "";
                        vectorString[j + 1] = "0"; // si el término es una constante, se le asigna un exponente de 0
                        j += 2;
                    }
                }
            } else if (vectorChar[i] == 'x' || vectorChar[i] == 'X') {

                if (i == 0) {
                    terminoActual += "1"; // si el término es una variable sin coeficiente, se le asigna un coeficiente de 1
                } else if (vectorChar[i - 1] == '-' || vectorChar[i - 1] == '+') {
                    terminoActual += "1"; // si el término es una variable sin coeficiente, se le asigna un coeficiente de 1
                }

                vectorString[j] = terminoActual; // guardar el término completo en el arreglo de términos
                terminoActual = "";   // reiniciar la cadena de término para el siguiente término
                
                if (i != (vectorChar.length - 1)) {
                    if (vectorChar[i + 1] != '^') { // si el término es una variable sin exponente
                        vectorString[j + 1] = "1";  // se le asigna un exponente de 1
                        j++;           
                    }
                } else {    // si el término es una variable sin exponente Y es el último término del polinomio
                    vectorString[j + 1] = "1"; // se le asigna un exponente de 1
                    j++;
                }

                j++;     // incrementar el índice para el siguiente término

            } else if (vectorChar[i] == '^') {
                vectorString[j] = Character.toString(vectorChar[i + 1]);
                j++;
                i++; // tanto 'X' como "un numero que tiene un signo a su derecha" se consideran
                     // finalizadores para la cadena de caracteres, pero el '^' no es un finalizador, 
                     // por lo que no reinicia la cadena actual sino que lo añade de una al arreglo.
            }
        }

    return vectorString;
    }

    /*
     */
    
}
