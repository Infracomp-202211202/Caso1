import java.util.ArrayList;

public class Celda extends Thread {

    public int id;
    private int fil;
    private int col;

    public boolean vivaAhora;
    public boolean vivaSiguiente;

    public int capacidad;
    public ArrayList<Integer> buff;

    public int cantidadVecinos;
    public int vecinosVivos;
    public int mensajesRecibidos;
    public ArrayList<Integer> mensajesEnviados;

    public Celda(int fil, int col, boolean vivaAhora, int id) {

        this.id = id;
        this.fil = fil;
        this.col = col;
        this.vivaAhora = vivaAhora;

        this.capacidad = fil + 1;
        this.buff = new ArrayList<Integer>();
        
        this.vecinosVivos = 0;
        this.mensajesRecibidos = 0;
        this.mensajesEnviados = new ArrayList<Integer>();

        this.vivaSiguiente = vivaAhora;

    }

    @Override
    public void run() {

        while (mensajesEnviados.size() < cantidadVecinos || mensajesRecibidos < cantidadVecinos || buff.size() > 0){
            enviarEstado();
            leerEstados();
        }
        leerEstados();
        
        if (vecinosVivos == 3) {
            vivaSiguiente = true;
        } else if (vecinosVivos == 0 || vecinosVivos > 3) {
            vivaSiguiente = false;
        }


        Tablero.bufferTablero.almacenar();
        Tablero.bufferTablero.puedoActualizar();

        this.vivaAhora = this.vivaSiguiente;

    }

    public void enviarEstado() {
        for (int i = fil - 1; i <= fil + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < Tablero.tablero.length && j >= 0 && j < Tablero.tablero.length && (i != fil || j != col)) {
                    if (!this.mensajesEnviados.contains(Tablero.tablero[i][j].id)) {
                        boolean resp = Tablero.tablero[i][j].recibirEstado(this.vivaAhora);
                        if (resp) {
                            mensajesEnviados.add(Tablero.tablero[i][j].id);
                        }
                    }
                }
            }
        }
    }

    public synchronized boolean recibirEstado(boolean mensaje) {
        if (this.buff.size() < capacidad) {
            this.mensajesRecibidos++;
            if (mensaje) {
                this.buff.add(1);
            } else {
                this.buff.add(0);
            }
            return true;
        }
        return false;
    }

    public synchronized void leerEstados() {
        for (int i = 0; i < buff.size(); i++) {
            if (buff.get(i) == 1) {
                this.vecinosVivos++;
            }
        }
        buff.clear();
    }
}
