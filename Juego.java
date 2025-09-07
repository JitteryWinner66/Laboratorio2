import java.util.Objects;

public class Juego {
    private final Tablero tablero;
    private final Jugador[] jugadores = new Jugador[2];
    private int indiceActual = 0;

    public Juego(int filas, int columnas, String nombre1, String nombre2, String[] emojiPool) {
        if (filas <= 0 || columnas <= 0) {
            throw new IllegalArgumentException("Filas y columnas deben ser positivas.");
        }
        if ((filas * columnas) % 2 != 0) {
            throw new IllegalArgumentException("El numero de casillas debe ser PAR.");
        }
        Objects.requireNonNull(nombre1, "Nombre del jugador 1 es nulo.");
        Objects.requireNonNull(nombre2, "Nombre del jugador 2 es nulo.");
        jugadores[0] = new Jugador(nombre1);
        jugadores[1] = new Jugador(nombre2);
        this.tablero = new Tablero(filas, columnas, emojiPool);
    }

    public Jugador getJugadorActual() { return jugadores[indiceActual]; }

    public Tablero getTablero() { return tablero; }

    public boolean haTerminado() { return tablero.todosEmparejados(); }

    public ResultadoJugada intentarJugada(int r1, int c1, int r2, int c2) throws MovimientoInvalidoException {
        validarSeleccion(r1, c1);
        validarSeleccion(r2, c2);
        if (r1 == r2 && c1 == c2) {
            throw new MovimientoInvalidoException("No puedes seleccionar la misma casilla dos veces.");
        }

        Ficha f1 = tablero.getFicha(r1, c1);
        Ficha f2 = tablero.getFicha(r2, c2);

        if (f1.isEmparejada() || f2.isEmparejada()) {
            throw new MovimientoInvalidoException("Una de las fichas ya fue emparejada.");
        }
        if (f1.isRevelada() || f2.isRevelada()) {
    
            throw new MovimientoInvalidoException("Una de las fichas ya está revelada, elija otra.");
        }

        tablero.revelar(r1, c1);
        tablero.revelar(r2, c2);

        if (f1.getSimbolo().equals(f2.getSimbolo())) {
            tablero.marcarEmparejadas(r1, c1, r2, c2);
            getJugadorActual().anotarPar();
            return new ResultadoJugada(true, f1.getSimbolo());
        } else {
            return new ResultadoJugada(false, null);
        }
    }

    public void ocultarFichas(int r1, int c1, int r2, int c2) {
        Ficha f1 = tablero.getFicha(r1, c1);
        Ficha f2 = tablero.getFicha(r2, c2);
        if (!f1.isEmparejada()) tablero.ocultar(r1, c1);
        if (!f2.isEmparejada()) tablero.ocultar(r2, c2);
    }

    public void pasarTurno() { indiceActual = 1 - indiceActual; }

    public Jugador getGanador() {
        if (jugadores[0].getPares() > jugadores[1].getPares()) return jugadores[0];
        if (jugadores[1].getPares() > jugadores[0].getPares()) return jugadores[1];
        return null; 
    }

    private void validarSeleccion(int r, int c) throws MovimientoInvalidoException {
        if (!tablero.estaDentro(r, c)) {
            throw new MovimientoInvalidoException("La coordenada (" + (r+1) + "," + (c+1) + ") está fuera del tablero");
        }
    }
}
