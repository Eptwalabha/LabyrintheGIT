package jeu.labyrinthe;

public enum TypeMur {

	TYPE_I(0),
	TYPE_L(1),
	TYPE_T(2);
	
	public int valeur;
	
	private TypeMur(int valeur) {
		this.valeur = valeur;
	}
	
}
