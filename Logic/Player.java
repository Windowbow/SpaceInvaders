package Logic;

import java.util.LinkedList;

public class Player extends Vaisseau {
	public static int LARGEUR_MISSILE = 2;
	public static int LONGUEUR_MISSILE = 2;
	
	public Player(int largeur, int longueur, int x, int y, LinkedList<Entite> targets) {
		super(largeur, longueur, x, y, LARGEUR_MISSILE, LONGUEUR_MISSILE, targets);
	}
	
	public boolean deplacement(int nbCasesATraverser, Direction direction) throws IllegalArgumentException {
		if ((direction != Direction.LEFT) && (direction != Direction.RIGHT)) {
			throw new IllegalArgumentException("la direction du joueur doit être horizontale");
		}

		return super.deplacement(nbCasesATraverser,direction, false);
		
	}
	
	public Missile lanceMissile() {
		// si (x, y) coin supérieur gauche
		int xMissile = x + (largeur-LARGEUR_MISSILE)/2;
		int yMissile = y - LONGUEUR_MISSILE;
		//Missiles vises n'importe quelle entite
		return super.lanceMissile(xMissile, yMissile, Direction.UP);
	}
	
	public static void main(String[] args) {
		//test 1
		Player fst = new Player(4, 4, 5, 5, Entite.getListeEntite());
		Missile missile = fst.lanceMissile();
		if (Entite.nbEntite() != 2 || missile.getX() != 6 || missile.getY() != 3) {
			System.out.println("Le lancement d'un missile n'est pas centré");
		}
		
	}
	
}