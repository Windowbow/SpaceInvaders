package Logic;

import java.util.LinkedList;
import java.util.Iterator;

public class LigneDeMonstre {
	public static int NB_CASES_A_DESCENDRE = 1;
	
	private LinkedList<Monstre> ligne;
	private Monstre finLigne;
	private Monstre debutLigne;
	private int y;
	
	
	/* 
	 * Constructeurs
	 * a noter que la ligne des monstre sera triée selon l'abscisse du monstre
	 */
	
	public LigneDeMonstre(Monstre monstre) {
		debutLigne = monstre;
		finLigne = monstre;
		y = monstre.getY();
		this.ligne = new LinkedList<Monstre>();
		ligne.add(monstre);
	}
	
	public LigneDeMonstre(LinkedList<Monstre> listeMonstre, int y) {

		for (Iterator<Monstre> itr = listeMonstre.iterator(); itr.hasNext();) {
			if (this.y != y)
				ajouteMonstre(itr.next());
		}
		debutLigne = ligne.getFirst();
		finLigne = ligne.getLast();
	}
	
	/* 
	 *  ajoute un monstre dans la liste
	 *  On suppose que l'on a vérifié lors de la création de l'Entité que 2 Entité ne se chevauchent pas
	 *  la liste étant triée, on recherche d'abord où on devra l'insérer
	 */
	
	private int rechercheIndexInsertion(Monstre mobAAjouter) {
		int xAAjouter = mobAAjouter.getX();
		int gauche = 0;
		if (!ligne.isEmpty()) {
			int droite = ligne.size();
			while (droite -gauche > 1) {
				int milieu = (droite + gauche) /2;
				if (xAAjouter < ligne.get(milieu).getX()) {
					droite = milieu;
				}
				else {
					gauche = milieu;
				}
			}
			return gauche;
		}
		return 0;
	}
	
	public void ajouteMonstre(Monstre mobAAjouter) throws IllegalArgumentException {
		if (mobAAjouter.getY() == y)
			ligne.add(rechercheIndexInsertion(mobAAjouter), mobAAjouter);
		else
			throw new IllegalArgumentException("les monstres doivent se trouver sur la même ordonnée");
	}
	
	/*
	 * on gère le déplacement de la ligne de Monstre
	 */
	
	private void tousDeplacementSaufElem(int nbCasesATraverser, Direction direction, Monstre monstre) {
		for (Iterator<Monstre> itr = ligne.iterator(); itr.hasNext();) {
			Monstre currentMonster = itr.next();
			if (currentMonster != monstre) {
				currentMonster.deplacement(nbCasesATraverser, direction);
			}
		}
	}
	
	boolean deplacement(int nbCasesATraverser, Direction direction) throws IllegalArgumentException {
		
		switch(direction) {
			case LEFT:
				if (debutLigne.deplacement(nbCasesATraverser, direction)) {
					tousDeplacementSaufElem(nbCasesATraverser, direction, debutLigne);
				}
				else {
					tousDeplacementSaufElem(NB_CASES_A_DESCENDRE, Direction.DOWN, null);
				}
				return true;
				
			case RIGHT:
				if (finLigne.deplacement(nbCasesATraverser, direction)) {
					tousDeplacementSaufElem(nbCasesATraverser, direction, finLigne);
				}
				else {
					tousDeplacementSaufElem(NB_CASES_A_DESCENDRE, Direction.DOWN, null);
				}
				return true;
				
			default:
				throw new IllegalArgumentException("la direction du joueur doit être horizontale");
		}
	}
	/*
	 * On vérifie que les tests sont vérifiés
	 */
	
	public static void main(String[] args) {
		
		LinkedList<Entite> targets = Entite.getListeEntite();
		Monstre monstre1 = new Monstre(3, 3, 2, 8, targets);
		Monstre monstre2 = new Monstre(3, 3, 7, 8, targets);
		LigneDeMonstre ligne = new LigneDeMonstre(monstre1);
		ligne.ajouteMonstre(monstre2);
		
		//test1 déplacement normal vers la gauche
		ligne.deplacement(1, Direction.LEFT);
		if (monstre2.getX() != 6 || monstre1.getX() != 1) {
			System.out.println("Erreur dans le premier test, gauche mal géré");
		}
		
		//test2 déplacement normal vers la droite
		ligne.deplacement(4, Direction.RIGHT);
		if ((monstre2.getX() != 10) || (monstre1.getX() != 5)) {
			System.out.println("Erreur dans le deuxieme test, droite mal géré");
		}
		
		//test3 déplacement gauche contre bord
		ligne.deplacement(8, Direction.LEFT);
		if (monstre2.getY() != 8 + LigneDeMonstre.NB_CASES_A_DESCENDRE) {
			System.out.println(monstre1.getY());
			System.out.println(monstre1.getX());
			System.out.println("Erreur dans le troisieme test, limite gauche mal géré, il faut descendre");
		}
		
		//test4 déplacement droit contre bord
		ligne.deplacement(10000, Direction.RIGHT);
		if (monstre2.getY() != 8 + 2*LigneDeMonstre.NB_CASES_A_DESCENDRE) {
			System.out.println(monstre1.getY());
			System.out.println(monstre1.getX());
			System.out.println("Erreur dans le troisieme test, limite gauche mal géré, il faut descendre");
		}
		
	}
}