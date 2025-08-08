package sudokulab3progra2_said_samuel;

import javax.swing.*;
import java.awt.*;

public class SudokuApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("Sudoku Mejorado");
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setSize(800, 600);
            ventana.setLocationRelativeTo(null);

            ModeloSudoku modelo = new ModeloSudoku();
            VistaSudoku vista = new VistaSudoku(modelo);
            ControladorSudoku controlador = new ControladorSudoku(modelo, vista);

            ventana.setLayout(new BorderLayout(5, 5));
            ventana.add(vista, BorderLayout.CENTER);
            ventana.add(controlador.obtenerPanelLateral(), BorderLayout.EAST);
            ventana.setVisible(true);
        });
    }
}
