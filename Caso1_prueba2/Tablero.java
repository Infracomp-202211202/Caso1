import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Tablero {

    public static int filas;
    public static Celda[][] tablero;
    public static CeldaRecibe[][] tablero2;
    public static Buffer bufferTablero;
    public static Buffer bufferTablero2;


    public static void main(String[] args) throws NumberFormatException, IOException {

        // Preguntar por el nombre del archivo
        System.out.print("Ingrese el nombre del archivo donde se encuentra la informacion del tablero: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String nombreArchivo = reader.readLine();
        File archivo = new File(nombreArchivo);
        BufferedReader br = new BufferedReader(new FileReader(archivo));

        filas = Integer.parseInt(br.readLine());

        tablero = new Celda[filas][filas];
        tablero2 = new CeldaRecibe[filas][filas];

        bufferTablero = new Buffer(filas * filas);
        bufferTablero2 = new Buffer(filas * filas);

        // Cargar el tablero
        int id = 0;
        for (int i = 0; i < filas; i++) {
            String[] infoFila = br.readLine().split(",");
            for (int j = 0; j < filas; j++) {

                tablero2[i][j] = new CeldaRecibe(i, j, id);

                if (infoFila[j].equals("true")) {
                    tablero[i][j] = new Celda(i, j, true, id);
                } else {
                    tablero[i][j] = new Celda(i, j, false, id);
                }
                if ((i == 0 && j == 0) || (i == 0 && j == filas - 1) || (i == filas - 1 && j == 0)
                        || (i == filas - 1 && j == filas - 1)) {
                    tablero[i][j].cantidadVecinos = 3;
                    tablero2[i][j].cantidadVecinos = 3;
                } else if (i == 0 || i == filas - 1 || j == 0 || j == filas - 1) {
                    tablero[i][j].cantidadVecinos = 5;
                    tablero2[i][j].cantidadVecinos = 5;
                } else {
                    tablero[i][j].cantidadVecinos = 8;
                    tablero2[i][j].cantidadVecinos = 8;
                }
                id++;
            }
        }

        br.close();
        System.out.println("Tablero cargado...");

        // Preguntamos por la cantidad de turnos
        System.out.print("Cuantas generaciones desea simular? ");
        reader = new BufferedReader(new InputStreamReader(System.in));
        int generaciones = Integer.parseInt(reader.readLine());
        System.out.println();

        // Juego Principal
        for (int veces = 0; veces < generaciones; veces++) {
            System.out.println("Turno " + veces);
            imprimirTablero();

            for (int i = 0; i < tablero.length; i++) {
                for (int j = 0; j < tablero[0].length; j++) {
                    tablero2[i][j].start();
                    tablero[i][j].start();
                }
            }

            bufferTablero.nextTurn();

            for (int i = 0; i < tablero.length; i++) {
                for (int j = 0; j < tablero[0].length; j++) {
                    try {
                        tablero[i][j].join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < filas; j++) {
                    
                    tablero2[i][j] = new CeldaRecibe(i, j, tablero2[i][j].id);
                    tablero[i][j] = new Celda(i, j, tablero[i][j].vivaAhora, tablero[i][j].id);

                    if ((i == 0 && j == 0) || (i == 0 && j == filas - 1) || (i == filas - 1 && j == 0)
                            || (i == filas - 1 && j == filas - 1)) {
                        tablero[i][j].cantidadVecinos = 3;
                        tablero2[i][j].cantidadVecinos = 3;
                    } else if (i == 0 || i == filas - 1 || j == 0 || j == filas - 1) {
                        tablero[i][j].cantidadVecinos = 5;
                        tablero2[i][j].cantidadVecinos = 5;
                    } else {
                        tablero[i][j].cantidadVecinos = 8;
                        tablero2[i][j].cantidadVecinos = 8;
                    }
                }
            }
        }
        System.out.println("Turno " + generaciones);
        imprimirTablero();

    }

    public static void imprimirTablero() {
        System.out.println("----".repeat(filas));
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                if (tablero[i][j].vivaAhora) {
                    if (j == 0) {
                        System.out.print("|  X ");
                    } else if (j == filas - 1) {
                        System.out.print(" X |");

                    } else {
                        System.out.print(" X ");
                    }

                } else {
                    if (j == 0) {
                        System.out.print("|  - ");
                    } else if (j == filas - 1) {
                        System.out.print(" - |");

                    } else {
                        System.out.print(" - ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println("----".repeat(filas));
        System.out.println();
    }

}
