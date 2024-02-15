import java.util.ArrayList;

public class CeldaRecibe extends Thread {

    public int id;
    private int fil;

    public int capacidad;
    public ArrayList<Integer> buff;

    public int cantidadVecinos;
    public int vecinosVivos;
    public int mensajesRecibidos;
  

    public CeldaRecibe(int fil, int col, int id) {

        this.id = id;
        this.fil = fil;

        this.capacidad = fil + 1;
        this.buff = new ArrayList<Integer>();
        
        this.vecinosVivos = 0;
        this.mensajesRecibidos = 0;

    }

    @Override
    public void run() {
        while (mensajesRecibidos < cantidadVecinos) {
            leerEstados();
        }
        Tablero.bufferTablero2.almacenar2();
        
    }

    public synchronized void recibirEstado(boolean mensaje) {
        while (this.buff.size() == capacidad) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }

        if (mensaje) {
            this.buff.add(1);
        } else {
            this.buff.add(0);
        }
    }

    public synchronized void leerEstados() {
        for (int i = 0; i < buff.size(); i++) {
            if (buff.get(i) == 1) {
                vecinosVivos++;
            }
            mensajesRecibidos++;
        }
        buff.clear();
        notifyAll();
    }
}
