package sudokulab3progra2_said_samuel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;  
import sudokulab3progra2_said_samuel.Sudoku;

public class ModeloSudoku {
    private int[][] tablero = new int[9][9];
    private int[][] solucion = new int[9][9];  
    private boolean[][] fijos = new boolean[9][9];
    private int errores = 0;             

    public int obtener(int f, int c) { return tablero[f][c]; }
    public boolean esFijo(int f, int c) { return fijos[f][c]; }
    public int getErrores() { return errores; }
    public void resetErrores() { errores = 0; }

 
    public boolean asignar(int f, int c, int v) {
        if (fijos[f][c]) {
            errores++;
            JOptionPane.showMessageDialog(null, "No puedes cambiar una casilla fija.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (v != solucion[f][c]) {
            errores++;
            JOptionPane.showMessageDialog(null, "Número incorrecto. Intenta otro.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        tablero[f][c] = v;
        return true;
    }

    public void generar(int pistas) {
        resetErrores();
        llenarTableroCompleto();
        for (int i = 0; i < 9; i++) {
            System.arraycopy(tablero[i], 0, solucion[i], 0, 9);
        }
        quitarNumeros(81 - pistas);
        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                fijos[f][c] = tablero[f][c] != 0;
            }
        }
    }

    public void limpiarEntradas() {
        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                if (!fijos[f][c]) tablero[f][c] = 0;
            }
        }
    }

    public boolean esValido(int f, int c, int v) {
        for (int i = 0; i < 9; i++) {
            if (tablero[f][i] == v || tablero[i][c] == v) return false;
        }
        int sr = (f / 3) * 3, sc = (c / 3) * 3;
        for (int i = sr; i < sr + 3; i++) {
            for (int j = sc; j < sc + 3; j++) {
                if (tablero[i][j] == v) return false;
            }
        }
        return true;
    }

    private void llenarTableroCompleto() {
        tablero = new int[9][9];
        rellenarRecursivo(0, 0);
    }

    private boolean rellenarRecursivo(int f, int c) {
        if (f == 9) return true;
        int nf = (c == 8 ? f + 1 : f), nc = (c == 8 ? 0 : c + 1);
        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= 9; i++) nums.add(i);
        Collections.shuffle(nums);
        for (int v : nums) {
            if (esValido(f, c, v)) {
                tablero[f][c] = v;
                if (rellenarRecursivo(nf, nc)) return true;
            }
        }
        tablero[f][c] = 0;
        return false;
    }

    private void quitarNumeros(int cuenta) {
        List<int[]> cells = new ArrayList<>();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                cells.add(new int[]{r, c});
            }
        }
        Collections.shuffle(cells);
        int removed = 0;
        for (int[] cell : cells) {
            if (removed >= cuenta) break;
            int r = cell[0], c = cell[1];
            int backup = tablero[r][c];
            if (backup == 0) continue;
            tablero[r][c] = 0;
            int[][] copy = new int[9][9];
            for (int i = 0; i < 9; i++) System.arraycopy(tablero[i], 0, copy[i], 0, 9);
            if (esSoluble(copy) && tieneCandidatos(copy)) {
                removed++;
            } else {
                tablero[r][c] = backup;
            }
        }
    }

    private boolean esSoluble(int[][] puzzle) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) System.arraycopy(puzzle[i], 0, copy[i], 0, 9);
        Sudoku solver = new Sudoku(copy);
        return solver.solve();
    }

    private boolean tieneCandidatos(int[][] puzzle) {
        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                if (puzzle[f][c] == 0) {
                    boolean ok = false;
                    for (int v = 1; v <= 9; v++) {
                        if (esValidoEnTablero(puzzle, f, c, v)) {
                            ok = true;
                            break;
                        }
                    }
                    if (!ok) return false;
                }
            }
        }
        return true;
    }
    
public boolean isComplete() {
    for (int f = 0; f < 9; f++) {
        for (int c = 0; c < 9; c++) {
            if (tablero[f][c] == 0) return false;
        }
    }
    return true;
}


    private boolean esValidoEnTablero(int[][] puzzle, int f, int c, int v) {
        for (int i = 0; i < 9; i++) {
            if (puzzle[f][i] == v || puzzle[i][c] == v) return false;
        }
        int sr = (f / 3) * 3, sc = (c / 3) * 3;
        for (int i = sr; i < sr + 3; i++) {
            for (int j = sc; j < sc + 3; j++) {
                if (puzzle[i][j] == v) return false;
            }
        }
        return true;
    }
}

