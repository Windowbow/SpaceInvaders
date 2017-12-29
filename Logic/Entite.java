package Logic;

import java.util.Vector;
import java.util.Iterator;

public class Entite {
	private final static Vector<Entite> listeEntite = new Vector<Entite>();
	protected final static int ADDITIONNEUR = 1;
	protected final static int SOUSTRACTEUR = -1;
	
	protected static int LIMITE_BASSE_VERTICALE = 0;
	protected static int LIMITE_HAUTE_VERTICALE = 1400;
	protected static int LIMITE_BASSE_HORIZONTALE = 0;
	protected static int LIMITE_HAUTE_HORIZONTALE = 1400;
	
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
	
	public static Vector<Entite> getListeEntite() {
		return listeEntite;
	}
	
	// renvoie le nombre d'entité existante
	public static int nbEntite() {
		return listeEntite.size();
	}
	
	public static void afficheEntites() {
		System.out.println(listeEntite.toString());
	}
	
	private int calculPos(int position, int nbCasesATraverser, int signe) throws IllegalArgumentException {
		if ((signe != ADDITIONNEUR) && (signe != SOUSTRACTEUR)) {
			throw new IllegalArgumentException("le signe vaut 1 ou -1");
		}
		int newPos = position + signe*nbCasesATraverser;
		return newPos;
	}
	
	
	//verifPos renvoie la limite basse si nextPos plus petite que celle ci, la limite haute si nextPos plus grande que celle-ci
	//elle renvoie nextPos sinon
	private int verifPos(int nextPos, int limiteBasse, int limiteHaute) {
		if (limiteBasse > nextPos) {
			return limiteBasse;
		}
		else if (limiteHaute < nextPos) {
			return limiteHaute;
		}
		else {
			return nextPos;
		}
	}
	
	public void deplacement(int nbCasesATraverser, Direction direction) throws IllegalArgumentException {
		int nextPos;
		
		//verif arg
		if (nbCasesATraverser < 0) {
			throw new IllegalArgumentException("le nombre de cases à traverser doit être positif");
		}
		
		switch (direction) {
			case UP: 		
				nextPos = calculPos(y, nbCasesATraverser, SOUSTRACTEUR);
				y = verifPos(nextPos, LIMITE_BASSE_VERTICALE, LIMITE_HAUTE_VERTICALE);
				break;
									
			case DOWN:
				nextPos = calculPos(y, nbCasesATraverser, ADDITIONNEUR);
				y = verifPos(nextPos, LIMITE_BASSE_VERTICALE, LIMITE_HAUTE_VERTICALE);
				break;
									
			case LEFT:
				nextPos = calculPos(x, nbCasesATraverser, SOUSTRACTEUR);
				x = verifPos(nextPos, LIMITE_BASSE_HORIZONTALE, LIMITE_HAUTE_HORIZONTALE);
				break;
									
			case RIGHT:
				nextPos = calculPos(x, nbCasesATraverser, ADDITIONNEUR);
				x = verifPos(nextPos, LIMITE_BASSE_HORIZONTALE, LIMITE_HAUTE_HORIZONTALE);
				break;
		}
		
	}
	
}
