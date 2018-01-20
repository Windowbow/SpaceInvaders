package Logic;

import java.util.LinkedList;
import java.util.Iterator;

public class Entite {
	private final static LinkedList<Entite> listeEntite = new LinkedList<Entite>();
	protected final static int ADDITIONNEUR = 1;
	protected final static int SOUSTRACTEUR = -1;
	
	public static int LIMITE_BASSE_VERTICALE = 0;
	public static int LIMITE_HAUTE_VERTICALE = 1400;
	public static int LIMITE_BASSE_HORIZONTALE = 0;
	public static int LIMITE_HAUTE_HORIZONTALE = 1400;
	
	protected int largeur, longueur, x, y;
	
	
	public Entite(int largeur, int longueur, int x, int y) throws IllegalArgumentException {
		if ((largeur < 1) || (longueur < 1) || (x<0) || (y <0)) {
			throw new IllegalArgumentException("erreur lors de l'initialisation de l'entite");
		}
		this.largeur = largeur;
		this.longueur = longueur;
		this.x = x;
		this.y = y;
		
		//attention, s'il y a plusieurs thread en cours d'exécution, cette ligne devra être modifié pour empécher l'accès à l'objet avant la fin de sa construction
		listeEntite.add(this);
	} 
	
	/*
	 * getter
	 */
	
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
	
	/*
	 * fonctions gérant la liste des entités
	 */
	
	public static LinkedList<Entite> getListeEntite() {
		return listeEntite;
	}
	
	// renvoie le nombre d'entité existante
	public static int nbEntite() {
		return listeEntite.size();
	}
	
	public static void afficheEntites() {
		System.out.println(listeEntite.toString());
	}
	
	public static boolean supprimeEntite(Entite entiteASupprimer) {
		return listeEntite.remove(entiteASupprimer);
	}

	/*
	 * fonction pour gérer le déplacement
	 */
	
	private int calculPos(int position, int nbCasesATraverser, int signe) throws IllegalArgumentException {
		if ((signe != ADDITIONNEUR) && (signe != SOUSTRACTEUR)) {
			throw new IllegalArgumentException("le signe vaut 1 ou -1");
		}
		int newPos = position + signe*nbCasesATraverser;
		return newPos;
	}
	
	
	//peutBouger regarde si l'entité ne sort pas du terrain
	public boolean peutBouger(int nextPos, int limiteBasse, int limiteHaute, boolean forcedMove) {
		if ((limiteBasse > nextPos || limiteHaute < nextPos) && (!forcedMove)){
			return false;
		}
		else {
			return true;
		}
	}
	
	//surcharge de déplacement
	public boolean deplacement(int nbCasesATraverser, Direction direction) {
		return deplacement(nbCasesATraverser, direction, false);
	}
	
	// fonction testé dans vaisseau
	//renvoie vrai si l'entité a bougée.
	public boolean deplacement(int nbCasesATraverser, Direction direction, boolean forcedMove) throws IllegalArgumentException {
		int nextPos;
		
		//verif arg
		if (nbCasesATraverser < 0) {
			throw new IllegalArgumentException("le nombre de cases à traverser doit être positif");
		}
		
		switch (direction) {
			case UP: 
				nextPos = calculPos(y, nbCasesATraverser, SOUSTRACTEUR);
				if (peutBouger(nextPos, LIMITE_BASSE_VERTICALE, LIMITE_HAUTE_VERTICALE, forcedMove)) {
					y = nextPos;
					return true;
				}
				break;
									
			case DOWN:
				nextPos = calculPos(y, nbCasesATraverser, ADDITIONNEUR);
				if (peutBouger(nextPos, LIMITE_BASSE_VERTICALE, LIMITE_HAUTE_VERTICALE, forcedMove)) {
					y = nextPos;
					return true;
				}
				break;
									
			case LEFT:
				nextPos = calculPos(x, nbCasesATraverser, SOUSTRACTEUR);
				if (peutBouger(nextPos, LIMITE_BASSE_HORIZONTALE, LIMITE_HAUTE_HORIZONTALE, forcedMove)) {
					x = nextPos;
					return true;
				}
				break;
									
			case RIGHT:
				nextPos = calculPos(x, nbCasesATraverser, ADDITIONNEUR);
				if (peutBouger(nextPos, LIMITE_BASSE_HORIZONTALE, LIMITE_HAUTE_HORIZONTALE, forcedMove)) {
					x = nextPos;
					return true;
				}
				break;
		}
		
		return false;
		
	}
	
	/*
	 * fonctions permettant de gérer la collision
	 */
	
	//vérifie si deux entités sont l'une sur l'autre
	protected boolean collision(Entite e) {
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
}
