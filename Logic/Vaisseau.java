package Logic;

import java.util.LinkedList;
import java.util.Iterator;

public class Vaisseau extends Entite {
	private final static LinkedList<Vaisseau> listeVaisseau= new LinkedList<Vaisseau>();
	
	public static int largeurMissileTire;
	public static int longueurMissileTire;
	LinkedList<Entite> targets;
	
	/*
	 * constructeurs, et vérification que l'on peut construire l'objet (il ne se superpose à un vaisseau déjà exitant)
	 */
	
	//renvoie vrai s'il y a eu collision, faux sinon
	public void testCollision() throws IllegalArgumentException {
		//à modifier en cas de multi threading
		for(Iterator<Vaisseau> itr = listeVaisseau.iterator(); itr.hasNext();) {
			Entite nextEntite = itr.next();
			if (nextEntite != this) {
				if (collision(nextEntite)) {
					//destruction des objets
					throw new IllegalArgumentException("Le vaisseau ne peut pas être créé car il se superpose à un vaisseau déjà existant");
				}
			}
		}
		
	}
	
	public Vaisseau(int largeur, int longueur, int x, int y, int largeurMissileTire, int longueurMissileTire, LinkedList<Entite> targets) throws IllegalArgumentException {
		super(largeur, longueur, x, y);
		
		//le nouveau vaisseau ne se superpose pas à un déjà existant
		testCollision();
		
		//les arguments sont valides
		if ((largeurMissileTire < 0) || (longueurMissileTire < 0)) {
			throw new IllegalArgumentException("les longueurs et largeurs doivent être positifs, ");
		}
		else {
			this.longueurMissileTire = longueurMissileTire;
			this.largeurMissileTire = largeurMissileTire;
		}
		
		this.targets = targets;
		
		//attention, s'il y a plusieurs thread en cours d'exécution, cette ligne devra être modifié pour empécher l'accès à l'objet avant la fin de sa construction
		listeVaisseau.add(this);
		
	}
	
	public Missile lanceMissile(int xMissile, int yMissile, Direction direction) {
		return new Missile(largeurMissileTire, longueurMissileTire, xMissile, yMissile, direction, targets);
	}
	
	/*
	 * tests
	 */
	
	public static void main(String[] args) {
		Vaisseau fst= new Vaisseau(3, 3, 5, 5, 2, 2, Entite.getListeEntite());
		
		//test tous les déplacements possibles
		
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
		
		//test 4
		fst.deplacement(1, Direction.UP);
		if (fst.getX() != 6) {
			System.out.println("Erreur dans le quatrieme test: deplacement vers le haut");
		}
		
		//test 5
		fst.deplacement(3, Direction.DOWN);
		if (fst.getX() != 6) {
			System.out.println("Erreur dans le cinquieme test: deplacement vers le bas");
		}
		
		//test le déplacement lorsque le vaisseau est au bord
		
		//test6
		if (fst.deplacement(10, Direction.LEFT)) {
			System.out.println("Erreur dans le sixieme test: limite gauche");
		}
		
		//test7 limite droite
		if (fst.deplacement(10000, Direction.RIGHT)) {
			System.out.println("Erreur dans le septieme test: limite droite");
		}
		
		//test 8
		if (fst.deplacement(10000, Direction.UP)) {
			System.out.println("Erreur dans le huitieme test: limite haute");
		}
		
		//test 9
		if (fst.deplacement(10000, Direction.DOWN)) {
			System.out.println("Erreur dans le neuvieme test: limite basse");
		}
		
		//test les relations entre vaisseau et missile
		
		//test10 lancement du Missile et test de l'ajout dans la liste des entités
		Vaisseau snd = new Vaisseau(3, 3, 20, 20, 2, 2, Entite.getListeEntite());
		Missile missile1 = snd.lanceMissile(snd.getX(), snd.getY()-2, Direction.UP);
		//test le positionnement du missile
		if ((missile1.getX() != 20) || (missile1.getY() != 18) || (Entite.nbEntite() != 3)) {
			System.out.println("Erreur dans le dixieme test: lancement du missile");
		}
		
		// test11 pas de collision 
		Vaisseau third = new Vaisseau(3,3, 20, 10, 2, 2, Entite.getListeEntite());//3,3,2,0
		missile1.deplacement(1);
		if (!Entite.getListeEntite().contains(missile1)) {
			System.out.println("Erreur dans le onzieme test: collision mal gérée, une collision a été détectée sans raison");
		}
		
		//test12 collision !!
		missile1.deplacement(9);
		if (Entite.getListeEntite().contains(missile1) || Entite.getListeEntite().contains(third)) {
			System.out.println("Erreur dans le douxieme test: collision mal gérée, pas de collision détectée");
		}
		
	}
	
}