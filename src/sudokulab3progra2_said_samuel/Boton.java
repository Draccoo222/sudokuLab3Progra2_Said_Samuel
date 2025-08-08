package sudokulab3progra2_said_samuel;

import javax.swing.JButton;

public abstract class Boton extends JButton {
    private final int columna;
    private final int fila;
    private int numBot;

    public Boton(int columna, int fila) {
        super();
        this.columna = columna;
        this.fila = fila;
        setOpaque(true);
        setContentAreaFilled(true);
        setBorderPainted(true);
    }

    public int getColumna() { return columna; }
    public int getFila() { return fila; }

    public void setNum(int num) {
        this.numBot = num;
        setText(num > 0 ? String.valueOf(num) : "");
    }

    public int getNum() { return numBot; }
}
