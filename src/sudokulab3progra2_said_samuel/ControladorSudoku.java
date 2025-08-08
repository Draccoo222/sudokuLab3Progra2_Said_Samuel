package sudokulab3progra2_said_samuel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ControladorSudoku {
    private ModeloSudoku modelo;
    private VistaSudoku vista;
    private JPanel panelLateral;
    private JLabel etiquetaErrores;
    private JComboBox<String> comboDificultad;
    private int filaSel = -1, colSel = -1;
    private int contErrores = 0;
    private int pistasInicial = 30;
    private static final int MAX_ERRORES = 3;

    public ControladorSudoku(ModeloSudoku m, VistaSudoku v) {
        this.modelo = m;
        this.vista = v;
        vista.setOyente((f, c) -> {
            this.filaSel = f;
            this.colSel = c;
            refrescarVista();
        });
        crearPanelLateral();
        reiniciarJuego();
    }

    private void crearPanelLateral() {
        panelLateral = new JPanel(new BorderLayout(5, 5));
        JPanel top = new JPanel(new BorderLayout(5, 5));
        JPanel diff = new JPanel(new FlowLayout(FlowLayout.LEFT));
        diff.add(new JLabel("Dificultad:"));
        comboDificultad = new JComboBox<>(new String[]{"Fácil", "Media", "Difícil"});
        comboDificultad.setSelectedIndex(1);
        comboDificultad.addActionListener((ActionEvent e) -> {
            String d = comboDificultad.getSelectedItem().toString();
            pistasInicial = d.equals("Fácil") ? 40 : d.equals("Difícil") ? 20 : 30;
            reiniciarJuego();
        });
        diff.add(comboDificultad);
        top.add(diff, BorderLayout.NORTH);
        etiquetaErrores = new JLabel("Errores: 0/" + MAX_ERRORES, SwingConstants.CENTER);
        etiquetaErrores.setFont(etiquetaErrores.getFont().deriveFont(Font.BOLD, 16f));
        top.add(etiquetaErrores, BorderLayout.SOUTH);
        panelLateral.add(top, BorderLayout.NORTH);

        JPanel numPanel = new JPanel(new GridLayout(4, 3, 5, 5));
        for (int i = 1; i <= 9; i++) {
            JButton btn = new JButton(String.valueOf(i));
            btn.setFont(btn.getFont().deriveFont(Font.BOLD, 18f));
            btn.addActionListener(e -> ingresarNumero(Integer.parseInt(btn.getText())));
            numPanel.add(btn);
        }
        panelLateral.add(numPanel, BorderLayout.CENTER);

        JPanel ctrl = new JPanel(new GridLayout(2, 1, 5, 5));
        JButton nuevo = new JButton("Nuevo Juego"), rein = new JButton("Reiniciar");
        nuevo.addActionListener(e -> reiniciarJuego());
        rein.addActionListener(e -> limpiarEntradas());
        ctrl.add(nuevo);
        ctrl.add(rein);
        panelLateral.add(ctrl, BorderLayout.SOUTH);
    }

    public JPanel obtenerPanelLateral() { return panelLateral; }

    private void ingresarNumero(int v) {
        if (filaSel < 0 || colSel < 0 || contErrores >= MAX_ERRORES) return;
        if (modelo.esValido(filaSel, colSel, v)) {
            modelo.asignar(filaSel, colSel, v);
        } else {
            contErrores++;
            etiquetaErrores.setText("Errores: " + contErrores + "/" + MAX_ERRORES);
            if (contErrores >= MAX_ERRORES) {
                JOptionPane.showMessageDialog(null, "Límite de errores. Nuevo juego.");
                reiniciarJuego();
                return;
            } else {
                JOptionPane.showMessageDialog(null, "Error. Quedan " + (MAX_ERRORES - contErrores) + " intentos.");
            }
        }
        refrescarVista();
    }

    private void reiniciarJuego() {
        contErrores = 0;
        modelo.generar(pistasInicial);
        filaSel = colSel = -1;
        refrescarVista();
    }

    private void limpiarEntradas() {
        contErrores = 0;
        modelo.limpiarEntradas();
        refrescarVista();
    }

    private void refrescarVista() {
        etiquetaErrores.setText("Errores: " + contErrores + "/" + MAX_ERRORES);
        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                vista.actualizarCelda(f, c, modelo.obtener(f, c), modelo.esFijo(f, c));
            }
        }
        vista.destacar(filaSel, colSel);
    }
}


