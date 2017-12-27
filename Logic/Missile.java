package Logic;


public class Missile extends Entite{
	
	private DirectionVerticale direction;
	
	public Missile (int largeur, int longueur, int x, int y, DirectionVerticale direction) {
		super(largeur, longueur, x, y);
		this.direction = direction;
	}
	
	public void deplacement(int nbCasesATraverser) throws IllegalArgumentException {
		int newPosY;
		
		//verif arg
		if (nbCasesATraverser < 0) {
			throw new IllegalArgumentException("le nombre de cases à traverser doit être positif");
		}
		
		//calcul prochaine position
		if (direction == DirectionVerticale.UP) {
			newPosY = y-nbCasesATraverser;
		}
		else {
			newPosY = y+nbCasesATraverser;
		}
		
		// verification de la position. limite haute à rajouter
		if (newPosY >= 0) {
			y = newPosY;
		}
		else {
			y = 0;
		}
		
		//regarde si une collision a eu lieu et agit en conséquence 
		// GESTION_COLLISION : testCollision();
	}
	
	public static void main(String[] args) {
		//test les fonctions
		Missile missile1 = new Missile(1, 1, 1, 7, DirectionVerticale.UP);
		Missile missile2 = new Missile(1, 2, 0, 0, DirectionVerticale.DOWN);
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
		if (missile2.getY() != y2) {
			System.out.println("Erreur dans le cinquieme test");
		}
		
		//test 6
		missile2.deplacement(6);
		if (missile2.getY() != y2+6) {
			System.out.println("Erreur dans le sixieme test: deplacement vers le bas");
		}
		
		//test 7 limite basse
		
	}
	
}
