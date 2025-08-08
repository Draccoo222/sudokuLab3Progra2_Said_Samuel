package sudokulab3progra2_said_samuel;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VistaSudoku extends JPanel {
    private JTextField[][] celdas = new JTextField[9][9];
    private OyenteCelda oyente;

    public VistaSudoku(ModeloSudoku m) {
        setLayout(new GridLayout(9, 9));
        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                JTextField tf = new JTextField();
                tf.setHorizontalAlignment(SwingConstants.CENTER);
                tf.setFont(tf.getFont().deriveFont(Font.BOLD, 20f));
                tf.setBorder(new MatteBorder(
                        (f % 3 == 0 ? 3 : 1),
                        (c % 3 == 0 ? 3 : 1),
                        (f == 8 ? 3 : 1),
                        (c == 8 ? 3 : 1),
                        Color.BLACK
                ));
                tf.setEditable(false);
                tf.setOpaque(true);
                tf.setBackground(Color.WHITE);
                final int rf = f, cf = c;
                tf.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (oyente != null) oyente.celdaSeleccionada(rf, cf);
                    }
                });
                celdas[f][c] = tf;
                add(tf);
            }
        }
    }

    public void setOyente(OyenteCelda o) { this.oyente = o; }

    public void actualizarCelda(int f, int c, int v, boolean fijo) {
        JTextField tf = celdas[f][c];
        tf.setText(v == 0 ? "" : String.valueOf(v));
        tf.setForeground(fijo ? Color.BLACK : Color.BLUE);
        tf.setBackground(Color.WHITE);
    }

    public void destacar(int selF, int selC) {
        Color hi = new Color(200, 230, 255);
        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                if (selF >= 0 && (f == selF || c == selC || (f/3 == selF/3 && c/3 == selC/3)))
                    celdas[f][c].setBackground(hi);
                else
                    celdas[f][c].setBackground(Color.WHITE);
            }
        }
    }

    public interface OyenteCelda { void celdaSeleccionada(int fila, int col); }
}

