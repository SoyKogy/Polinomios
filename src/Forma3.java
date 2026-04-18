import javax.swing.JOptionPane;

public class Forma3 {
    // Atributos
    private Nodo Punta;

    // Constructor
    public Forma3() {
        this.Punta = null;
    }

    // Getters y Setters
    public Nodo getPunta() {
        // se devuelve la punta de la lista (polinomio completo)
        return Punta;
    }

    public void setPunta(Nodo Punta) {
        // se reemplaza la lista completa cambiando su punta
        this.Punta = Punta;
    }

    // Metodos

    public void PasarVPF3(String vectorString[]) {
        // se pasa el vectorString a lista ligada, y como ya está ordenado,
        // simplemente se insertan los nodos en el mismo orden.

        // reiniciamos la lista para reconstruirla completa desde cero
        Punta = null;

        // vectorString viene como: coef - exp - coef - exp ...
        // i recorre coeficientes y j recorre exponentes
        for (int i = 0, j = 1; i < vectorString.length && j < vectorString.length; i += 2, j += 2) {
            int coeficiente = Integer.parseInt(vectorString[i]);
            int exponente = Integer.parseInt(vectorString[j]);

            // si el coeficiente es 0, ese término no se guarda en la lista
            if (coeficiente != 0) {
                // se inserta al final para respetar el orden ya recibido
                InsertarAlFinal(coeficiente, exponente);
            }
        }

        System.out.println("\nVPF3:");
        Nodo p = Punta;
        while (p != null) {
            System.out.print(p.getCoe() + "|" + p.getExp() + "| -> |");
            p = p.getLiga();
        }
    }

    public void InsertarAlFinal(int Coe, int Exp) {
       
        Nodo p = Punta, x = new Nodo(Coe, Exp);

        /*this.getPunta().getExp();
          this.getPunta().; */
        
        // si la lista está vacía, el nuevo nodo se convierte en la punta
        if (Punta == null) {
            Punta = x;
        } else { // si la lista no está vacía, se recorre hasta el final y se inserta el nuevo nodo
            while (p.getLiga() != null) {
                p = p.getLiga();
            }
            p.setLiga(x);
        }
    }

    public void InsertarYAjustar(int terminoNuevo, int gradoNuevo) {
        Nodo p = Punta, ant = null, x;

        // si el término es 0, no cambia nada en el polinomio
        if (terminoNuevo != 0) {
            // se busca si el grado ya existe o dónde insertar manteniendo orden.
            // "ant" se queda en el nodo anterior y "p" en el actual.
            while (p != null && p.getExp() > gradoNuevo) {
                ant = p;
                p = p.getLiga();
            }

            // si ya existe el grado, se suma en ese nodo
            if (p != null && p.getExp() == gradoNuevo) {
                p.setCoe(p.getCoe() + terminoNuevo);

                // si se anula, se elimina ese nodo ajustando ligas
                if (p.getCoe() == 0) {
                    if (ant == null) {
                        // si era el primer nodo, la punta pasa al siguiente
                        Punta = p.getLiga();
                    } else {
                        // si estaba en medio/final, el anterior salta al siguiente
                        ant.setLiga(p.getLiga());
                    }
                }
            } else {
                // si no existe el grado, se crea nodo nuevo y se inserta en orden
                x = new Nodo(terminoNuevo, gradoNuevo);
                if (ant == null) {
                    // inserción al inicio (nuevo mayor grado)
                    x.setLiga(Punta);
                    Punta = x;
                } else {
                    // inserción en medio o al final
                    x.setLiga(p);
                    ant.setLiga(x);
                }
            }
        }
    }

    public void EliminarYAjustar(int gradoAEliminar) {
        Nodo p = Punta, ant = null;

        // se recorre hasta encontrar el nodo con el grado a eliminar
        while (p != null && p.getExp() != gradoAEliminar) {
            ant = p;
            p = p.getLiga();
        }

        // si se encontró el grado, se elimina
        if (p != null) {
            if (ant == null) {
                // si el nodo a eliminar era la punta
                Punta = p.getLiga();
            } else {
                // si no era la punta, se religa el nodo anterior
                ant.setLiga(p.getLiga());
            }

            // debug de la lista tras eliminar
            System.out.println("\nVPF3 tras eliminar término:");
            p = Punta;
            while (p != null) {
                System.out.print(p.getCoe() + "|" + p.getExp() + "| -> |");
                p = p.getLiga();
            }
        }
    }

    public void Insertar() {
        // Ingresamos el término a insertar
        String termino = JOptionPane.showInputDialog("Ingrese el término a insertar:");
        String polinomioOriginal = Reconstruir(); // se guarda el polinomio original para poder imprimirlo después


        // pasamos el término ingresado a un vector de string
        String[] terminoConcatenado = new String[Polinomios.ConcatenarTermino(termino).length];
        terminoConcatenado = Polinomios.ConcatenarTermino(termino);

        // terminoConcatenado[0] = coeficiente
        // terminoConcatenado[1] = exponente
        InsertarYAjustar(Integer.parseInt(terminoConcatenado[0]), Integer.parseInt(terminoConcatenado[1]));

        JOptionPane.showMessageDialog(null, "Polinomio original:\n" + polinomioOriginal + "\nTermino a insertar:\n" + termino + "\nPolinomio total:\n" + Reconstruir());
    }

    public void Eliminar() {
        // Ingresamos el grado del término a eliminar
        String exponente = JOptionPane.showInputDialog("Ingrese el exponente del término a eliminar:");
        String polinomioOriginal = Reconstruir(); // se guarda el polinomio original para poder imprimirlo después

        // se elimina por grado y se ajustan ligas si es necesario
        EliminarYAjustar(Integer.parseInt(exponente));

        JOptionPane.showMessageDialog(null, "Polinomio original:\n" + polinomioOriginal + "\nExponente del termino a eliminar:\n" + exponente + "\nPolinomio total:\n" + Reconstruir());

    }

    public void Mostrar() {
        // muestra el VPF3, obteniendo cada elemento y guardándolo en un string
        String texto = "";
        Nodo p = Punta;

        if (p == null) {
            // si la lista está vacía, el polinomio es cero
            texto = "0";
        } else {
            // se imprime como pares coef - exp para ver la estructura interna de la lista
            while (p != null) {
                texto += p.getCoe() + " | " + p.getExp() + " | -> | ";
                p = p.getLiga();
            }
        }

        JOptionPane.showMessageDialog(null, texto);
    }

    public String Reconstruir() {
        // Revierte (pasa) el polinomio a su forma algebraica
        if (Punta == null) {
            JOptionPane.showMessageDialog(null, "0");
            return "0";
        }

        String cadenaAux = "";
        Nodo p = Punta;

        while (p != null) {
            // para términos positivos (excepto el primero), se añade el signo +
            if (p.getCoe() > 0 && p != Punta) {
                cadenaAux += "+";
            }

            // estructura de impresión según el grado actual:
            // grado 0: constante
            if (p.getExp() == 0) {
                cadenaAux += p.getCoe();
            } else if (p.getExp() == 1) { // grado 1: x sin exponente
                cadenaAux += p.getCoe() + "x";
            } else { // grado n: x^n
                cadenaAux += p.getCoe() + "x^" + p.getExp();
            }
            p = p.getLiga();
        }

        return cadenaAux;
    }

    public void Evaluar() {
        // Sería: en todos los términos, elevar el número al grado
        // y multiplicarlo por su coeficiente, para después sumar.
        String n = JOptionPane.showInputDialog("Ingrese el número por el cual reemplazar:");
        int termino = Integer.parseInt(n);
        int total = 0;
        Nodo p = Punta;

        // elevar y multiplicar término a término
        while (p != null) {
            total += (p.getCoe() * Math.powExact(termino, p.getExp()));
            p = p.getLiga();
        }

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

        Forma3 f3ASumar = new Forma3(); // se crea otro polinomio en forma3
        f3ASumar.PasarVPF3(vectorASumar); // se pasa el vectorstring (vectorASumar) a forma3

        // se recorre el segundo polinomio y se va insertando/sumando en el actual
        // InsertarYAjustar se encarga de:
        // - insertar si no existe el grado
        // - sumar si el grado ya existe
        // - eliminar si el coeficiente queda en 0
        Nodo p = f3ASumar.getPunta();
        while (p != null) {
            InsertarYAjustar(p.getCoe(), p.getExp());
            p = p.getLiga();
        }

        // debug del resultado final en forma de lista ligada
        System.out.println("\nVPF3 sumado:");
        p = Punta;
        while (p != null) {
            System.out.print(p.getCoe() + "|" + p.getExp() + "| -> |");
            p = p.getLiga();
        }

        JOptionPane.showMessageDialog(null, "Polinomio original:\n" + polinomioOriginal + "\nPolinomio a sumar:\n" + f3ASumar.Reconstruir() + "\nPolinomio total:\n" + Reconstruir());
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

        Forma3 f3AMultiplicar = new Forma3(); // se crea otro polinomio en forma3
        f3AMultiplicar.PasarVPF3(vectorAMultiplicar); // se pasa el vectorstring (vectorAMultiplicar) a forma3

        // se crea otro polinomio en forma3 para guardar el resultado total
        Forma3 f3Total = new Forma3();
        Nodo p = Punta, q;

        // multiplicación término a término y acumulación por grado
        // por cada término de este polinomio:
        // se multiplica con cada término del segundo polinomio
        // y se inserta en f3Total usando InsertarYAjustar
        while (p != null) {
            q = f3AMultiplicar.getPunta();
            while (q != null) {
                // InsertarYAjustar acumula productos de igual grado
                f3Total.InsertarYAjustar((p.getCoe() * q.getCoe()), (p.getExp() + q.getExp()));
                q = q.getLiga();
            }
            p = p.getLiga();
        }

        // reemplazamos la lista actual por la lista resultado
        setPunta(f3Total.getPunta());

        // debug del resultado final en forma de lista ligada
        System.out.println("\nVPF3 multiplicado:");
        p = Punta;
        while (p != null) {
            System.out.print(p.getCoe() + "|" + p.getExp() + "| -> |");
            p = p.getLiga();
        }

        JOptionPane.showMessageDialog(null, "Polinomio original:\n" + polinomioOriginal + "\nPolinomio a multiplicar:\n" + f3AMultiplicar.Reconstruir() + "\nPolinomio total:\n" + Reconstruir());
    }

}
