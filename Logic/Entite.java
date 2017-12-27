package Logic;

import java.util.Vector;
import java.util.Iterator;

public class Entite {
	protected int largeur, longueur, x, y;
	//GESTION_COLLISION private final static Vector<Entite> listeEntite;
	
	public int getLargeur() {
		return largeur;
	}
	
	public int getLongueur() {
		return longueur;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Entite(int largeur, int longueur, int x, int y) throws IllegalArgumentException {
		if ((largeur < 1) || (longueur < 1) || (x<0) || (y <0)) {
			throw new IllegalArgumentException("erreur lors de l'initialisation de l'entite");
		}
		this.largeur = largeur;
		this.longueur = longueur;
		this.x = x;
		this.y = y;
		//attention, s'il y a plusieurs thread en cours d'exécution, cette ligne devra être modifié pour empécher l'accès à l'objet avant la fin de sa construction
		//GESTION_COLLISION listeEntite.add(this);
	} 
	/* GESTION_COLLISION
	
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
		synchronized (listeEntite) {
			Iterator<Entite> itr = listeEntite.iterator();
			while (itr.hasNext()) {
				Entite nextEntite = itr.next();
				if (collision(nextEntite)) {
					itr.remove();
					listeEntite.remove(this);
				}
			}
		}
	}
	
	public static void afficheEntites() {
		System.out.println(listeEntite.toString());
	}
	*/
}
