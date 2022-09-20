import java.util.*; // Importando todas las librerías para evitar problemas.

public class StatesAFD {
    // Creando variables para los estados del AFD.
    private int idAFD; // Identificador del estado del AFD.
    private List<StatesAFD> estado_anteriorAFD; // Lista que guarda los estados anteriores del AFD.
    private List<StatesAFD> estado_siguienteAFD; // Lista que guarda los estados siguientes del AFD.

    private int inicio; // Variable que indica si el estado es de inicio o no.
    private int fin; // Variable que indica si el estado es de aceptación o no.

    public StatesAFD() {

    }

    public StatesAFD(int id) { // Constructor de la clase Estado.
        this.idAFD = id; // Se le asigna el id al estado.
        this.estado_anteriorAFD = new ArrayList<StatesAFD>(); // Se inicializa la lista de estados anteriores.
        this.estado_siguienteAFD = new ArrayList<StatesAFD>(); // Se inicializa la lista de estados siguientes.
    }

    public StatesAFD AFDE(int id) { // Método que se encarga de crear los estados del AFD que se va a generar del
        // AFN.

        this.idAFD = id; // Se le asigna el id al estado.
        this.estado_anteriorAFD = new ArrayList<StatesAFD>(); // Se inicializa la lista de estados anteriores.
        this.estado_siguienteAFD = new ArrayList<StatesAFD>(); // Se inicializa la lista de estados siguientes.

        return this;
    }

    public StatesAFD(int id, List<StatesAFD> estado_anterior, List<StatesAFD> estado_siguiente) { // Constructor de la clase
                                                                                         // Estado.
        this.idAFD = id; // Se le asigna el id al estado.
        this.estado_anteriorAFD = estado_anterior; // Se inicializa la lista de estados anteriores.
        this.estado_siguienteAFD = estado_siguiente; // Se inicializa la lista de estados siguientes.

    }

    public void agregarEstadoAnterior(StatesAFD estado) { // Método para agregar un estado anterior.
        this.estado_anteriorAFD.add(estado); // Se agrega el estado a la lista de estados anteriores.
    }

    public void agregarEstadoSiguiente(StatesAFD estado) { // Método para agregar un estado siguiente.
        this.estado_siguienteAFD.add(estado); // Se agrega el estado a la lista de estados siguientes.
    }

    public List<StatesAFD> getEstadoAnterior() { // Método para obtener los estados anteriores.
        return this.estado_anteriorAFD; // Se retorna la lista de estados anteriores.
    }

    public List<StatesAFD> getEstadoSiguiente() { // Método para obtener los estados siguientes.
        return this.estado_siguienteAFD; // Se retorna la lista de estados siguientes.
    }

    public String toString() { // Método para imprimir el estado.
        return "" + this.idAFD; // Se retorna el id del estado como string.
    }

    public int getId() { // Método para obtener el id del estado.
        return this.idAFD; // Se retorna el id del estado.
    }

    // Getter del estado inicial del AFD.
    public int getIdAFD() {
        return this.idAFD;
    }

    public void setInicio(int inicio) { // Método para asignar el estadoinicial.
        this.inicio = inicio; // Se asigna el estado inicial.
    }

    public int getInicio() { // Método para obtener el estado inicial.
        return this.inicio; // Se retorna el estado inicial.
    }

    public void setFinal(int fin) { // Método para asignar el estado final.
        this.fin = fin; // Se asigna el estado final.
    }

    public int getFinal() { // Método para obtener el estado final.
        return this.fin; // Se retorna el estado final.
    }
}
