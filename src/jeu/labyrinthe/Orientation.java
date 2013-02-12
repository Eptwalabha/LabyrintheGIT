package jeu.labyrinthe;

public enum Orientation {
	
	I_HORIZONTAL(0),
	I_VERTICAL(1),
	
	L_HAUT_DROITE(0),
	L_DROITE_BAS(1),
	L_BAS_GAUCHE(2),
	L_GAUCHE_HAUT(3),
	
	T_VERS_LE_HAUT(0),
	T_VERS_LA_DROITE(1),
	T_VERS_LE_BAS(2),
	T_VERS_LA_GAUCHE(3);

	public int valeur;
	
	private Orientation(int valeur){
		this.valeur = valeur;
	}
	
}
