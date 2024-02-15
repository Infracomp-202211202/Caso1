import java.util.ArrayList;

public class Celda extends Thread {

    public int id;
    private int fil;
    private int col;

    public boolean vivaAhora;
    private boolean vivaSiguiente;

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

    }

    @Override
    public void run() {

        enviarEstado();

        Tablero.bufferTablero2.almacenar2();
        Tablero.bufferTablero2.puedoActualizar2();

        vecinosVivos=Tablero.tablero2[fil][col].vecinosVivos;  
        // System.out.println("Celda " + id + " tiene " + Tablero.tablero2[fil][col].buff + " buffer");        

        if (vecinosVivos == 3) {
            vivaSiguiente = true;
        } else if (vecinosVivos == 0 || vecinosVivos > 3) {
            vivaSiguiente = false;
        }else{
            vivaSiguiente = vivaAhora;
        }

        Tablero.bufferTablero.almacenar();
        Tablero.bufferTablero.puedoActualizar();

        vivaAhora = vivaSiguiente;

    }

    public void enviarEstado() {
        for (int i = fil - 1; i <= fil + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < Tablero.tablero.length && j >= 0 && j < Tablero.tablero[0].length
                        && (i != fil || j != col)) {
                    Tablero.tablero2[i][j].recibirEstado(this.vivaAhora);
                }
            }
        }
    }
}
