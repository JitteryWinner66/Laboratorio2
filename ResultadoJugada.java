public class ResultadoJugada {
    private final boolean esPar;
    private final String simboloCoincidente; 

    public ResultadoJugada(boolean esPar, String simboloCoincidente) {
        this.esPar = esPar;
        this.simboloCoincidente = simboloCoincidente;
    }
    public boolean isPar() { return esPar; }
    public String getSimboloCoincidente() { return simboloCoincidente; }
}
