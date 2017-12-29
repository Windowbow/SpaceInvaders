package Logic;

public class Player extends Entite {
	public static int LARGEUR_MISSILE = 2;
	public static int LONGUEUR_MISSILE = 2;
	
	public Player(int largeur, int longueur, int x, int y) {
		super(largeur, longueur, x, y);
	}
	
	public void deplacement(int nbCasesATraverser, Direction direction) throws IllegalArgumentException {
		if ((direction != Direction.LEFT) && (direction != Direction.RIGHT)) {
			throw new IllegalArgumentException("la direction du joueur doit être horizontale");
		}

		super.deplacement(nbCasesATraverser,direction);
		
	}
	
	public Missile lanceMissile() {
		// si (x, y) coin supérieur gauche
		int xMissile = x + (largeur-LARGEUR_MISSILE)/2;
		int yMissile = y - LONGUEUR_MISSILE;
		//Missiles vises n'importe quelle entite
		return new Missile(LARGEUR_MISSILE, LONGUEUR_MISSILE, xMissile, yMissile, Direction.UP, Entite.getListeEntite());
	}
	
	public static void main(String[] args) {
		Player fst= new Player(3, 3, 5, 5);
		
		//test 1
		fst.deplacement(0, Direction.LEFT);
		if (fst.getX() != 5) {
			System.out.println("Erreur dans le premier test: pas de déplacement");
		}

		//test2
		fst.deplacement(4, Direction.LEFT);
		if (fst.getX() != 1) {
			System.out.println("Erreur dans le deuxieme test: deplacement vers la gauche");
		}
		
		//test3
		fst.deplacement(5, Direction.RIGHT);
		if (fst.getX() != 6) {
			System.out.println("Erreur dans le troisieme test: deplacement vers la droite");
		}
		
		//test4
		fst.deplacement(10, Direction.LEFT);
		if (fst.getX() != 0) {
			System.out.println("Erreur dans le quatrieme test: limite gauche");
		}
		
		//deplacement ok, fst revenu au point de départ
		
		//test5 limite droite
		
		//test6 lancement du Missile et test de l'ajout dans la liste des entités
		Player snd = new Player(3, 3, 5, 5);
		Missile missile1 = snd.lanceMissile();
		//test le positionnement du missile
		if ((missile1.getX() != 5) || (missile1.getY() != 3) || (Entite.nbEntite() != 3)) {
			System.out.println("Erreur dans le sixieme test: lancement du missile");
		}
		
		// test7 collision !!!!
		Player third = new Player(3,3, 2, 0);
		missile1.deplacement(3);
		if (Entite.nbEntite() != 2) {
			System.out.println("Erreur dans le septième test: collision mal gérée");
		}
		
	}
	
}