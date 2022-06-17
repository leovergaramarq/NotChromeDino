package main; import game.Game;
/**
 * Clase Main.
 * <p>
 * Contiene el método main y se encarga de ejecutar el juego.
 * </p>
 * @author Leonardo D. Vergara Márquez
 */
public class Main {
    
    /**
     * Declara e inicializa un objeto game de tipo Game.
     * @param args 
     */
    public static void main(String[] args){
        Game game = new Game();
        game.setResizable(false);
        game.setLocationRelativeTo(null);
        game.setVisible(true);
    }
}