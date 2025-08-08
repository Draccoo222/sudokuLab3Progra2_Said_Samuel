package sudokulab3progra2_said_samuel;

import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Insets;

public class CeldaBoton extends Boton {
    public CeldaBoton(int columna, int fila) {
        super(columna, fila);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setFont(getFont().deriveFont(java.awt.Font.BOLD, 20f));
        setMargin(new Insets(0, 0, 0, 0));
        setOpaque(true);
        setContentAreaFilled(true);
        setBorderPainted(true);
        setBorder(new MatteBorder(
            (fila % 3 == 0 ? 4 : 1),
            (columna % 3 == 0 ? 4 : 1),
            (fila == 8 ? 4 : 1),
            (columna == 8 ? 4 : 1),
            Color.BLACK
        ));
        setBackground(Color.WHITE);
    }
}

