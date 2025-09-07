import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;



public class Principal {

   private static final String[] EMOJIS_POOL = new String[] {
    Character.toString(0x1F34E), 
    Character.toString(0x1F436), 
    Character.toString(0x1F697), 
    Character.toString(0x1F3B5), 
    Character.toString(0x2B50),  
    Character.toString(0x26BD),  
    Character.toString(0x1F370), 
    Character.toString(0x1F680), 
    Character.toString(0x1F3B2), 
    Character.toString(0x1F3A8), 
    Character.toString(0x1F4DA), 
    Character.toString(0x1F9E9), 
    Character.toString(0x1F9F8), 
    Character.toString(0x1F340), 
    Character.toString(0x1F308), 
    Character.toString(0x1F525), 
    Character.toString(0x2744),  
    Character.toString(0x1F319), 
    Character.toString(0x2600),  
    Character.toString(0x1F354)  
};


    private int victoriasP1 = 0;
    private int victoriasP2 = 0;
    private int empates = 0;

    public static void main(String[] args) throws Exception {
    System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
    new Principal().run();
}
    private void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Memoria");

        System.out.println("Nombre del Jugador 1: ");
        String n1 = sc.nextLine().trim();
        if (n1.isEmpty()) n1 = "Jugador 1";

        System.out.println("Nombre del Jugador 2: ");
        String n2 = sc.nextLine().trim();
        if (n2.isEmpty()) n2 = "Jugador 2";

        boolean continuarSesion = true;
        while (continuarSesion) {

            System.out.println("\nSeleccione el tamano del tablero (debe ser numero par de casillas):");
            int filas = leerEnteroRango(sc, "Filas (ejemplo. 4, 6): ", 2, 10);
            int cols  = leerEnteroRango(sc, "Columnas (ejemplo. 4, 6): ", 2, 10);

            try {
                Juego juego = new Juego(filas, cols, n1, n2, EMOJIS_POOL);

                while (!juego.haTerminado()) {
                    System.out.println("Turno de: " + juego.getJugadorActual().getNombre());
                    imprimirTablero(juego.getTablero());

                    int[] c1 = leerCoordenada(sc, "Primera ficha (fila,col): ", filas, cols);
                    int[] c2 = leerCoordenada(sc, "Segunda ficha (fila,col): ", filas, cols);

                    try {
                        ResultadoJugada res = juego.intentarJugada(c1[0], c1[1], c2[0], c2[1]);

                        
                        imprimirTablero(juego.getTablero());

                        if (res.isPar()) {
                            System.out.println("Acertaste! Par de: " + res.getSimboloCoincidente());
                            System.out.println("Puntaje: " + juego.getJugadorActual().getNombre() +
                                    " = " + juego.getJugadorActual().getPares() + " pares.");
                           
                        } else {
                            System.out.println("No es par. Las fichas se ocultaran.");
                            pausa(sc);
                    
                            juego.ocultarFichas(c1[0], c1[1], c2[0], c2[1]);
                            juego.pasarTurno();
                        }

                    } catch (MovimientoInvalidoException ex) {
                        System.out.println("Jugada invalida: " + ex.getMessage());
                        
                    }
                }

                System.out.println("Tablero Completado");
                imprimirTablero(juego.getTablero());
                Jugador ganador = juego.getGanador();
                if (ganador == null) {
                    System.out.println("Resultado: Empate");
                    empates++;
                } else {
                    System.out.println("Ganador de la partida: " + ganador.getNombre() +
                            " con " + ganador.getPares() + " pares");
                    if (ganador.getNombre().equals(n1)) victoriasP1++;
                    else victoriasP2++;
                }

            } catch (IllegalArgumentException ex) {
                System.out.println("Configuracion invalida: " + ex.getMessage());
            }

            System.out.print("\n¿Desean jugar otra partida? (S/N): ");
            String resp = sc.nextLine().trim().toUpperCase();
            continuarSesion = resp.equals("S");
        }

    
        System.out.println("Resultados");
        System.out.println("Victorias " + "de " + "Jugador 1: " + victoriasP1 + "»".replace("Jugador 1", "") + n1 + ": " + victoriasP1);
        System.out.println("Victorias " + "de " + "Jugador 2: " + victoriasP2 + "»".replace("Jugador 2", "") + n2 + ": " + victoriasP2);
        System.out.println("Empates: " + empates);
        System.out.println("¡Gracias por jugar!");
        sc.close();
    }


    private int leerEnteroRango(Scanner sc, String prompt, int min, int max) {
        boolean ok = false;
        int val = min;
        while (!ok) {
            System.out.print(prompt);
            try {
                val = Integer.parseInt(sc.nextLine().trim());
                if (val < min || val > max) {
                    System.out.println("Debe estar entre " + min + " y " + max );
                } else {
                    ok = true;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Ingrese un numero entero valido");
            }
        }
        return val;
    }

    private int[] leerCoordenada(Scanner sc, String prompt, int filas, int cols) {
        boolean ok = false;
        int r = 1, c = 1;
        while (!ok) {
            System.out.println(prompt);
            String linea = sc.nextLine().trim();
            String[] partes = linea.split(",");
            if (partes.length != 2) {
                System.out.println("Formato invalido. Use fila,col (ejemplo. 2,3)");
                continue;
            }
            try {
                r = Integer.parseInt(partes[0].trim());
                c = Integer.parseInt(partes[1].trim());
                if (r < 1 || r > filas || c < 1 || c > cols) {
                    System.out.println("Fuera de rango. Filas: 1-" + filas + ", Cols: 1-" + cols);
                } else {
                    ok = true;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Ingrese numeros enteros validos para fila y columna");
            }
        }
    
        return new int[]{r - 1, c - 1};
    }

    private void imprimirTablero(Tablero t) {
        int f = t.getFilas();
        int c = t.getColumnas();
        System.out.print("    ");
        for (int j = 0; j < c; j++) {
            System.out.printf("%-4d", j + 1);
        }
        System.out.println();
        System.out.print("    ");
        for (int j = 0; j < c; j++) {
            System.out.print("----");
        }
        System.out.println();
        for (int i = 0; i < f; i++) {
            System.out.printf("%-3d|", i + 1);
            for (int j = 0; j < c; j++) {
                Ficha ficha = t.getFicha(i, j);
                String mostrar = "?"; 
                if (ficha.isRevelada() || ficha.isEmparejada()) {
                    mostrar = ficha.getSimbolo();
                }
                System.out.printf("%-4s", mostrar);
            }
            System.out.println();
        }
    }

    private void pausa(Scanner sc) {
        System.out.println("Presiona enter para continuar");
        sc.nextLine();
    }
}
