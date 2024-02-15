import java.util.ArrayList;

public class Buffer extends Thread{

    public ArrayList<Integer> buff;
    public ArrayList<Integer> buff2;
    public int capacidad;

    public Buffer(int capacidad) {
        this.capacidad = capacidad;
        buff = new ArrayList<Integer>();
        buff2 = new ArrayList<Integer>();
    }


    public synchronized void almacenar() {
        buff.add(1);
        if (buff.size() == capacidad) {
            notifyAll();
        }
    }
    
    public synchronized void almacenar2() {
        buff2.add(1);
        if (buff2.size() == capacidad*2) {
            buff2.clear();
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

    public synchronized void puedoActualizar2() {
        while (buff2.size() > 0)
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

