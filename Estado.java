import java.util.*; // Importando todas las librerías para evitar problemas.

public class Estado { // Clase estado, esta se va a encargar de guardar los estados del AFN.
    private List<Estado> estado_anterior; // Lista que guarda los estados anteriores.
    private List<Estado> estado_siguiente; // Lista que guarda los estados siguientes.
    private int id; // Identificador del estado.

    private boolean inicio; // Variable que indica si el estado es de aceptación o no.
    private boolean fin; // Variable que indica si el estado es de aceptación o no.

    public Estado(int id) { // Constructor de la clase Estado.
        this.id = id; // Se le asigna el id al estado.
        this.estado_anterior = new ArrayList<Estado>(); // Se inicializa la lista de estados anteriores.
        this.estado_siguiente = new ArrayList<Estado>(); // Se inicializa la lista de estados siguientes.

    }

    public Estado(int id, List<Estado> estado_anterior, List<Estado> estado_siguiente) { // Constructor de la clase
                                                                                         // Estado.
        this.id = id; // Se le asigna el id al estado.
        this.estado_anterior = estado_anterior; // Se inicializa la lista de estados anteriores.
        this.estado_siguiente = estado_siguiente; // Se inicializa la lista de estados siguientes.

    }

    public void agregarEstadoAnterior(Estado estado) { // Método para agregar un estado anterior.
        this.estado_anterior.add(estado); // Se agrega el estado a la lista de estados anteriores.
    }

    public void agregarEstadoSiguiente(Estado estado) { // Método para agregar un estado siguiente.
        this.estado_siguiente.add(estado); // Se agrega el estado a la lista de estados siguientes.
    }

    public List<Estado> getEstadoAnterior() { // Método para obtener los estados anteriores.
        return this.estado_anterior; // Se retorna la lista de estados anteriores.
    }

    public List<Estado> getEstadoSiguiente() { // Método para obtener los estados siguientes.
        return this.estado_siguiente; // Se retorna la lista de estados siguientes.
    }

    public String toString() { // Método para imprimir el estado.
        return "Estado: " + this.id; // Se retorna el id del estado.
    }

    public int getId() { // Método para obtener el id del estado.
        return this.id; // Se retorna el id del estado.
    }

    public void setInicio(boolean inicio) { // Método para asignar el estado inicial.
        this.inicio = inicio; // Se asigna el estado inicial.
    }

    public boolean getInicio() { // Método para obtener el estado inicial.
        return this.inicio; // Se retorna el estado inicial.
    }

    public void setFinal(boolean fin) { // Método para asignar el estado final.
        this.fin = fin; // Se asigna el estado final.
    }

    public boolean getFinal() { // Método para obtener el estado final.
        return this.fin; // Se retorna el estado final.
    }
}