package Logic;

import java.util.Iterator;
import java.util.LinkedList;

public class Missile extends Entite{
	
	private Direction direction;
	private LinkedList<Entite> targets;
	
	public Missile (int largeur, int longueur, int x, int y, Direction direction, LinkedList<Entite> targets) throws IllegalArgumentException {
		super(largeur, longueur, x, y);
		
		//vérifie que la direction est verticale
		if ((direction != Direction.UP) && (direction != Direction.DOWN)) {
			throw new IllegalArgumentException("la direction du missile doit être verticale");
		}
		
		this.direction = direction;
		this.targets = targets;
	}
	
	public boolean deplacement(int nbCasesATraverser) {
		boolean hasMoved = super.deplacement(nbCasesATraverser, direction);
		
		//puis on regarde si une collision a eu lieu et on agit en conséquence 
		testCollision();
		return hasMoved;
	}
	
	//renvoie vrai s'il y a eu collision, faux sinon
	public boolean testCollision() {
		//synchronized utile pour le multithreading
		boolean collision = false;
		
		for(Iterator<Entite> itr = targets.iterator(); itr.hasNext();) {
			Entite nextEntite = itr.next();
			if (nextEntite != this) {
				if (collision(nextEntite)) {
					//destruction des objets
					Entite.supprimeEntite(nextEntite);
					collision = true;
				}
			}
		}
		
		// s'il y a eu une collision on enlève le missile
		if (collision) {
			Entite.supprimeEntite(this);
		}
		return collision;
		
	}
	
	public static void main(String[] args) {
		//test les fonctions
		Missile missile1 = new Missile(1, 1, 1, 7, Direction.UP, new LinkedList<Entite>());
		Missile missile2 = new Missile(1, 2, 0, 0, Direction.DOWN, new LinkedList<Entite>());
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
