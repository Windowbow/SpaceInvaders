package Logic;

import java.util.LinkedList;
import java.util.Iterator;

public class Monstre extends Vaisseau {
	public static int LARGEUR_MISSILE = 2;
	public static int LONGUEUR_MISSILE = 2;
	
	public Monstre(int largeur, int longueur, int x, int y, LinkedList<Entite> targets) {
		super(largeur, longueur, x, y, LARGEUR_MISSILE, LONGUEUR_MISSILE, targets);
	}
	
	public Missile lanceMissile() {
		// si (x, y) coin supérieur gauche
		int xMissile = x + (largeur-LARGEUR_MISSILE)/2;
		int yMissile = y - LONGUEUR_MISSILE;
		//Missiles vises n'importe quelle entite
		return super.lanceMissile(xMissile, yMissile, Direction.DOWN);
	}
	
	public void foeInRange() {
		for (Iterator<Entite> itr = targets.iterator(); itr.hasNext();) {
			Entite currentTarget = itr.next(); 
			if ((currentTarget.getX() < (largeur+LARGEUR_MISSILE)/2 + x) && ((largeur-LARGEUR_MISSILE)/2 < currentTarget.getX())){
				lanceMissile();
			}
		}
	}
	
	public static void main(String[] args) {
		
	}
}