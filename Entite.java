
abstract class Entite {
	private int largeur, longueur, x, y;
	
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
	
	public Entite() {
		
	}
	
	abstract void deplacement();
	public boolean collision(Entite e) {
		//a ecrire
		return true;
	}
}
