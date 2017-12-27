package Logic;

public class Player extends Entite {
	public static int LARGEUR_MISSILE = 2;
	public static int LONGUEUR_MISSILE = 2;
	
	public Player(int largeur, int longueur, int x, int y) {
		super(largeur, longueur, x, y);
	}
	
	public void deplacement(int nbCasesATraverser, DirectionHorizontale direction) {
		int newPosX;
		
		//verif arg
		if (nbCasesATraverser < 0) {
			throw new IllegalArgumentException("le nombre de cases à traverser doit être positif");
		}
		
		//calcul prochaine position
		if (direction == DirectionHorizontale.LEFT) {
			newPosX = x - nbCasesATraverser;
		}
		else {
			newPosX = x + nbCasesATraverser;
		}
		
		// verification de la position. limite haute à rajouter
		if (newPosX >= 0) {
			x = newPosX;
		}
		else {
			x = 0;
		}
		
		//regarde si une collision a eu lieu et agit en conséquence 
		//GESTION_COLLISION testCollision();
	}
	
	public Missile lanceMissile() {
		// si (x, y) coin supérieur gauche
		int xMissile = x + (largeur-LARGEUR_MISSILE)/2;
		int yMissile = y - LONGUEUR_MISSILE;
		return new Missile(LARGEUR_MISSILE, LONGUEUR_MISSILE, xMissile, yMissile, DirectionVerticale.UP);
	}
	
	public static void main(String[] args) {
		Player fst= new Player(3, 3, 5, 5);
		
		//test 1
		fst.deplacement(0, DirectionHorizontale.LEFT);
		if (fst.getX() != 5) {
			System.out.println("Erreur dans le premier test: pas de déplacement");
		}
		
		//test2
		fst.deplacement(4, DirectionHorizontale.LEFT);
		if (fst.getX() != 1) {
			System.out.println("Erreur dans le deuxieme test: deplacement vers la gauche");
		}
		
		//test3
		fst.deplacement(5, DirectionHorizontale.LEFT);
		if (fst.getX() != 0) {
			System.out.println("Erreur dans le troisieme test: limite gauche");
		}
		
		//test4
		fst.deplacement(5, DirectionHorizontale.RIGHT);
		if (fst.getX() != 5) {
			System.out.println("Erreur dans le quatrieme test: deplacement vers la droite");
		}
		//deplacement ok, fst revenu au point de départ
		
		//test5 limite droite
		
		//test6 lancement du Missile
		Missile missile1 = fst.lanceMissile();
		//test le positionnement du missile
		if ((missile1.getX() != 5) || (missile1.getY() != 3)) {
			System.out.println("Erreur dans le sixieme test: lancement du missile");
		}
		
		/*GESTION_COLLISION test7 collision !!!!
		Player snd = new Player(3,3, 2, 0);
		missile1.deplacement(3);
		Entite.afficheEntites();
		*/
	}
	
}