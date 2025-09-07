public class Jugador {
    private final String nombre;
    private int pares = 0;

    public Jugador(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre de jugador inválido.");
        }
        this.nombre = nombre.trim();
    }

    public String getNombre() { return nombre; }
    public int getPares() { return pares; }
    public void anotarPar() { pares++; }
}
