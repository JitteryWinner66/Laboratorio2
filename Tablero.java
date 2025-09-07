import java.util.Random;

public class Tablero {
    private final int filas;
    private final int columnas;
    private final Ficha[][] celdas;

    public Tablero(int filas, int columnas, String[] emojiPool) {
        this.filas = filas;
        this.columnas = columnas;
        this.celdas = new Ficha[filas][columnas];

        int total = filas * columnas;
      if (total % 2 != 0) {
            throw new IllegalArgumentException("El tablero debe tener un numero par de casillas.");
        }
        int paresNecesarios = total / 2;
        if (emojiPool == null || emojiPool.length < paresNecesarios) {
            throw new IllegalArgumentException("No hay suficientes simbolos/emoji para " + paresNecesarios + " pares.");
        }

      
        String[] seleccion = seleccionarSimbolosUnicos(emojiPool, paresNecesarios);

        String[] bolsa = new String[total];
        int idx = 0;
        for (int i = 0; i < paresNecesarios; i++) {
            bolsa[idx++] = seleccion[i];
            bolsa[idx++] = seleccion[i];
        }

        
        mezclar(bolsa);
        idx = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new Ficha(bolsa[idx++]);
            }
        }
    }

    public boolean estaDentro(int r, int c) {
        return r >= 0 && r < filas && c >= 0 && c < columnas;
    }

    public Ficha getFicha(int r, int c) {
        if (!estaDentro(r, c)) throw new IndexOutOfBoundsException("Coordenada fuera del tablero.");
        return celdas[r][c];
    }

    public void revelar(int r, int c) {
        Ficha f = getFicha(r, c);
        if (!f.isEmparejada()) f.revelar();
    }

    public void ocultar(int r, int c) {
        Ficha f = getFicha(r, c);
        if (!f.isEmparejada()) f.ocultar();
    }

    public void marcarEmparejadas(int r1, int c1, int r2, int c2) {
        Ficha f1 = getFicha(r1, c1);
        Ficha f2 = getFicha(r2, c2);
        f1.marcarEmparejada();
        f2.marcarEmparejada();
    }

    public boolean todosEmparejados() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (!celdas[i][j].isEmparejada()) return false;
            }
        }
        return true;
    }

    public int getFilas() { return filas; }
    public int getColumnas() { return columnas; }


    private String[] seleccionarSimbolosUnicos(String[] pool, int n) {
        
        int[] indices = new int[pool.length];
        for (int i = 0; i < pool.length; i++) indices[i] = i;
        mezclar(indices);

        String[] sel = new String[n];
        for (int i = 0; i < n; i++) {
            sel[i] = pool[indices[i]];
        }
        return sel;
    }

    private void mezclar(String[] arr) {
        Random rnd = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            String tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

    private void mezclar(int[] arr) {
        Random rnd = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }
}
