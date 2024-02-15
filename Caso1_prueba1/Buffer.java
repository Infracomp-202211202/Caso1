import java.util.ArrayList;

public class Buffer extends Thread{

    public ArrayList<Integer> buff;
    private int capacidad;

    public Buffer(int capacidad) {
        this.capacidad = capacidad;
        buff = new ArrayList<Integer>();
    }


    public synchronized void almacenar() {
        buff.add(1);
        if (buff.size() == capacidad) {
            notifyAll();
        }
    }

    public synchronized void puedoActualizar() {
        while (buff.size() > 0)
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    public synchronized void almacenar2() {
        buff.add(1);
        if (buff.size() == capacidad) {
            notifyAll();
        }
    }

    public synchronized void puedoActualizar2() {
        while (buff.size() < capacidad)
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    public synchronized void nextTurn() {
        while (buff.size() != capacidad){
            try {
                wait();
            } catch (InterruptedException e) {
              
            }}
        
        buff.clear();
        notifyAll();
    }

}

