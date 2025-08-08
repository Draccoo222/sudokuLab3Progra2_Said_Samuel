package sudokulab3progra2_said_samuel;

import java.util.*;

public class ModeloSudoku {
    private int[][] tablero = new int[9][9];
    private boolean[][] fijos = new boolean[9][9];

    public int obtener(int f, int c) { return tablero[f][c]; }
    public boolean esFijo(int f, int c) { return fijos[f][c]; }
    public void asignar(int f, int c, int v) { tablero[f][c] = v; }

    public void generar(int pistas) {
        llenarTableroCompleto();
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
        int eliminado = 0, intentos = 0;
        Random rnd = new Random();
        while (eliminado < cuenta && intentos < cuenta * 5) {
            int f = rnd.nextInt(9), c = rnd.nextInt(9);
            if (tablero[f][c] == 0) { intentos++; continue; }
            int[][] copia = copiarTablero();
            copia[f][c] = 0;
            if (contarSoluciones(copia, 2) == 1) {
                tablero[f][c] = 0;
                eliminado++;
            }
            intentos++;
        }
    }

    private int contarSoluciones(int[][] bt, int lim) {
        return contarRecursivo(bt, 0, 0, lim);
    }

    private int contarRecursivo(int[][] bt, int f, int c, int lim) {
        if (f == 9) return 1;
        int nf = (c == 8 ? f + 1 : f), nc = (c == 8 ? 0 : c + 1);
        if (bt[f][c] != 0) return contarRecursivo(bt, nf, nc, lim);
        int cnt = 0;
        for (int v = 1; v <= 9; v++) {
            if (validoEn(bt, f, c, v)) {
                bt[f][c] = v;
                cnt += contarRecursivo(bt, nf, nc, lim);
                if (cnt >= lim) break;
            }
        }
        bt[f][c] = 0;
        return cnt;
    }

    private boolean validoEn(int[][] bt, int f, int c, int v) {
        for (int i = 0; i < 9; i++) {
            if (bt[f][i] == v || bt[i][c] == v) return false;
        }
        int sr = (f / 3) * 3, sc = (c / 3) * 3;
        for (int i = sr; i < sr + 3; i++) {
            for (int j = sc; j < sc + 3; j++) {
                if (bt[i][j] == v) return false;
            }
        }
        return true;
    }

    private int[][] copiarTablero() {
        int[][] copia = new int[9][9];
        for (int i = 0; i < 9; i++) System.arraycopy(tablero[i], 0, copia[i], 0, 9);
        return copia;
    }
}

