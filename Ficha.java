public class Ficha {
    private final String simbolo;
    private boolean revelada = false;
    private boolean emparejada = false;

    public Ficha(String simbolo) {
        if (simbolo == null || simbolo.isEmpty()) {
            throw new IllegalArgumentException("SImbolo invalido para la ficha.");
        }
        this.simbolo = simbolo;
    }

    public String getSimbolo() { return simbolo; }
    public boolean isRevelada() { return revelada; }
    public boolean isEmparejada() { return emparejada; }

    public void revelar() { this.revelada = true; }
    public void ocultar() { this.revelada = false; }
    public void marcarEmparejada() {
        this.emparejada = true;
        this.revelada = true;
    }
}
