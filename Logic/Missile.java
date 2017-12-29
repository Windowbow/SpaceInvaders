package Logic;

import java.util.Iterator;
import java.util.Vector;

public class Missile extends Entite{
	
	private Direction direction;
	private Vector<Entite> targets;
	
	public Missile (int largeur, int longueur, int x, int y, Direction direction, Vector<Entite> targets) {
		super(largeur, longueur, x, y);
		
		//vérifie que la direction est verticale
		if ((direction != Direction.UP) && (direction != Direction.DOWN)) {
			throw new IllegalArgumentException("la direction du missile doit être verticale");
		}
		
		this.direction = direction;
		this.targets = targets;
	}
	
	public void deplacement(int nbCasesATraverser) {
		super.deplacement(nbCasesATraverser, direction);
		
		//puis on regarde si une collision a eu lieu et on agit en conséquence 
		testCollision();
	}
	
	public boolean collision(Entite e) {
		// (x, y) = point supérieur gauche
		// (0, 0) point en haut à gauche
		// definition du point inférieur gauche
		int xInf = x+ largeur;
		int yInf = y+longueur;
		int entiteXInf = e.getX() + e.getLargeur();
		int entiteYInf = e.getY() + e.getLongueur();
		
		//condition sur la position horizontale
		if ((xInf >= e.getX()) && (x <= entiteXInf)) {
			// condition sur la position verticale
			if ((yInf >= e.getY()) && (y<= entiteYInf)) {
				return true;
			}
		}
		return false;
		
	}
	
	public void testCollision() {
		//synchronized utile pour le multithreading
		synchronized (targets) {
			boolean thisASupprimer = false;
			for(Iterator<Entite> itr = targets.iterator(); itr.hasNext();) {
				Entite nextEntite = itr.next();
				if (nextEntite != this) {
					if (collision(nextEntite)) {
						//destruction des objets
						itr.remove();
						thisASupprimer = true;
					}
				}
			}
			if (thisASupprimer) {
				targets.remove(this);
			}
		}
	}
	
	public static void main(String[] args) {
		//test les fonctions
		Missile missile1 = new Missile(1, 1, 1, 7, Direction.UP, new Vector<Entite>());
		Missile missile2 = new Missile(1, 2, 0, 0, Direction.DOWN, new Vector<Entite>());
		int x1 = missile1.getX();
		int y1 = missile1.getY();
		int x2 = missile2.getX();
		int y2 = missile2.getY();
		
		// test 1
		missile1.deplacement(0);
		if (missile1.getY() != y1) {
			System.out.println("Erreur dans le premier test: pas de déplacement");
		}
		
		//test 2
		missile1.deplacement(6);
		if (missile1.getY() != 1) {
			System.out.println("Erreur dans le deuxième test: deplacement vers le haut");
		}
		
		//test 3
		missile1.deplacement(1);
		if (missile1.getY() != 0) {
			System.out.println("Erreur dans le troisième test");
		}
		
		//test 4
		missile1.deplacement(1);
		if (missile1.getY() != 0) {
			System.out.println("Erreur dans le quatrieme test: limite haute");
		}
		
		// test 5
		missile2.deplacement(0);
		if (missile2.getY() != 0) {
			System.out.println("Erreur dans le cinquieme test");
		}
		
		//test 6
		missile2.deplacement(6);
		if (missile2.getY() != 6) {
			System.out.println("Erreur dans le sixieme test: deplacement vers le bas");
		}
		
		//test 7 limite basse
		
	}
	
}
