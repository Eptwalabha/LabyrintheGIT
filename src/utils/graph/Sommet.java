package utils.graph;


public class Sommet {

	private Sommet frere;
	private Sommet fils;
	private boolean passage = false;
	private int valeur = -1;
	private String name = "<no name>";
	
	public Sommet(){
		this.frere = null;
		this.fils = null;
	}
	
	public Sommet(Sommet frere, Sommet fils){
		this.frere = frere;
		this.fils = fils;
	}

	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public Sommet getSommetFrere(){
		return this.frere;
	}
	
	public Sommet getSommetFils(){
		return this.fils;
	}
	
	public int getValeurSommet(){
		return this.valeur;
	}
	
	public void setFils(Sommet nouveau_fils){
		this.fils = nouveau_fils;
	}
	
	public void setFrere(Sommet nouveau_frere){
		this.frere = nouveau_frere;
	}
	
	public void resetPassage(){
		this.passage = false;
		this.valeur = -1;
	}
	
	public boolean isVisited(){
		return this.passage;
	}
	
	public void stack(Sommet sommet){
		
		if(sommet != null){
			if(this.fils == null){
//				System.out.println("new fils!");
				this.fils = sommet;
			}else if(this.frere != null){
//				System.out.println("add!");
				this.frere.stack(sommet);
			}else{
//				System.out.println("new frere!");
				Sommet new_frere = new Sommet(null, sommet);
				this.frere = new_frere;
			}
		}
	}
	
	public Sommet getPlusPetitCheminRecursif(Sommet fin){
		
		return getPlusPetitCheminRecursif(fin, 0, true);
	}
	
	public Sommet getPlusPetitCheminRecursif(Sommet sommet_final, int poid_chemin, boolean ce_sommet_est_un_fils){
		
		if(poid_chemin < this.valeur || this.valeur == -1){
			
			if(ce_sommet_est_un_fils) this.valeur = poid_chemin;
			Sommet chemin = new Sommet();
			
			if(this == sommet_final){
				chemin.setName("créer par " + this.name);
				chemin.setFils(this);
				return chemin;
			}
		
			Sommet chemin_frere = null;
			Sommet chemin_fils = null;
			
			int poid_chemin_frere = -1;
			int poid_chemin_fils = -1;
			
			if(this.frere != null){
//				System.out.println(this.name + " frère : " + this.frere.getName() + " poid = " + poid_chemin);
				chemin_frere = this.frere.getPlusPetitCheminRecursif(sommet_final, poid_chemin, false);
				if(chemin_frere != null) poid_chemin_frere = sommet_final.getValeurSommet();
			}
			
			if(this.fils != null){
//				System.out.println(this.name + " fils : " + this.fils.getName() + " poid = " + (poid_chemin + 1));
				chemin_fils = this.fils.getPlusPetitCheminRecursif(sommet_final, poid_chemin + 1, true);
				if(chemin_fils != null) poid_chemin_fils = sommet_final.getValeurSommet();
			}
			
			if(chemin_fils != null || chemin_frere != null){
				
				if(ce_sommet_est_un_fils){
					
					chemin = new Sommet();
					chemin.setName("créer par " + this.name);
					chemin.setFils(this);
					
//					if(poid_chemin_fils > poid_chemin_frere){
//						chemin.setFrere(chemin_frere);
//					}else{
//						chemin.setFrere(chemin_fils);
//					}
					
					if(chemin_fils != null && chemin_frere == null){
						chemin.setFrere(chemin_fils);
					}else if(chemin_fils == null && chemin_frere != null){
						chemin.setFrere(chemin_frere);
					}else{
//						System.out.println("deux chemins trouvés; poid fils = " + poid_chemin_fils + "; poid frere = " + poid_chemin_frere);
						if(poid_chemin_fils > poid_chemin_frere){
							chemin.setFrere(chemin_frere);
						}else{
							chemin.setFrere(chemin_fils);
						}
					}
					
				}else{
					
//					if(poid_chemin_fils > poid_chemin_frere){
//						chemin = chemin_frere;
//					}else{
//						chemin = chemin_fils;
//					}
					
					if(chemin_fils != null && chemin_frere == null){
						chemin = chemin_fils;
					}else if(chemin_fils == null && chemin_frere != null){
						chemin = chemin_frere;
					}else{
//						System.out.println("deux chemins trouvés; poid fils = " + poid_chemin_fils + "; poid frere = " + poid_chemin_frere);
						if(poid_chemin_fils > poid_chemin_frere){
							chemin = chemin_frere;
						}else{
							chemin = chemin_fils;
						}
					}
				}
				return chemin;
			}
		}else{
//			System.out.println("nom: " + this.name + "; poid rejeté = " + poid_chemin + "; " + this.name + ".valeur = " + this.valeur);
		}
		
		return null;
	}
	
	// type = 0 pour racine; 1 pour fils; 2 pour frere.
	public void printGraph(int profondeur, int type) {
		
		this.passage = true;
		
		String separateur = "";
		for(int i = 0; i < profondeur; i++){
			separateur += "|";
		}
		
		String info = "";
		
		switch (type) {
		case 0:
			info = "ra :" + this.name + " (" + this.valeur + ")";
			break;
		case 1:
			info = separateur + "fi :" + this.name + " (" + this.valeur + ")";
			break;
		case 2:
			info = separateur + "fr :" + this.name + " (" + this.valeur + ")";
			break;
		default:
			break;
		}

		System.out.println(info);
		
		if(this.fils != null){
			if(!this.fils.isVisited()){
				this.fils.printGraph(profondeur + 1, 1);
			}else{
				System.out.println(separateur + "- fi : " + this.fils.getName() + " (loop)");
			}
		}else{
			System.out.println(separateur + "- fi : null");
		}
		
		if(this.frere != null){
			this.frere.printGraph(profondeur + 1, 2);
		}else{
			System.out.println(separateur + "- fr : null");
		}
		

	}
}
