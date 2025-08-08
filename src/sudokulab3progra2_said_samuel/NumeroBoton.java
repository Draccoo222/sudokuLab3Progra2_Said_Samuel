package sudokulab3progra2_said_samuel;

public class NumeroBoton extends Boton {
    public NumeroBoton(int num) {
        super(-1, -1);
        setNum(num);
        setFont(getFont().deriveFont(java.awt.Font.BOLD, 18f));
    }
}


