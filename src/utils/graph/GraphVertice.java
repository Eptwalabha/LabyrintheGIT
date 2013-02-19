package utils.graph;

public class GraphVertice {

	private GraphVertice brother;
	private GraphVertice son;
	private boolean visited = false;
	private int weight_value = -1;
	
	private int weight_to_son;
	
	private String name = "<no name>";
	
	public GraphVertice(){
		this.brother = null;
		this.son = null;
		this.weight_to_son = 1;
	}
	
	public GraphVertice(GraphVertice brother, GraphVertice son){
		this.brother = brother;
		this.son = son;
		this.weight_to_son = 1;
	}
	
	public GraphVertice(GraphVertice brother, GraphVertice son, int weight_to_son){
		this.brother = brother;
		this.son = son;
		this.weight_to_son = weight_to_son;
	}	

	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public GraphVertice getVerticeBrother(){
		return this.brother;
	}
	
	public GraphVertice getVerticeSon(){
		return this.son;
	}
	
	public int getVerticeValue(){
		return this.weight_value;
	}
	
	public void setSon(GraphVertice nouveau_fils){
		this.son = nouveau_fils;
	}
	
	public void setBrother(GraphVertice nouveau_frere){
		this.brother = nouveau_frere;
	}
	
	public int getSonEdgeWeight(){
		return this.weight_to_son;
	}
	
	public void setSonEdgeWeight(int new_weight){
		this.weight_to_son = new_weight;
	}
	
	public void resetVerticeWeight(){
		this.visited = false;
		this.weight_value = -1;
	}
	
	public boolean hasBeenVisited(){
		return this.visited;
	}
	
	public void stack(GraphVertice sommet){
		
		if(sommet != null){
			if(this.son == null){
//				System.out.println("new fils!");
				this.son = sommet;
			}else if(this.brother != null){
//				System.out.println("add!");
				this.brother.stack(sommet);
			}else{
//				System.out.println("new frere!");
				GraphVertice new_frere = new GraphVertice(null, sommet);
				this.brother = new_frere;
			}
		}
	}
	
	public GraphVertice getShortestPathRecursive(GraphVertice fin){
		
		return getShortestPathRecursive(fin, 0, true);
	}
	
	public GraphVertice getShortestPathRecursive(GraphVertice sommet_final, int poid_chemin, boolean ce_sommet_est_un_fils){
		
		if(poid_chemin < this.weight_value || this.weight_value == -1){
			
			if(ce_sommet_est_un_fils) this.weight_value = poid_chemin;
			GraphVertice chemin = new GraphVertice();
			
			if(this == sommet_final){
				chemin.setName("créer par " + this.name);
				chemin.setSon(this);
				return chemin;
			}
		
			GraphVertice chemin_frere = null;
			GraphVertice chemin_fils = null;
			
			int poid_chemin_frere = -1;
			int poid_chemin_fils = -1;
			
			if(this.brother != null){
//				System.out.println(this.name + " frère : " + this.frere.getName() + " poid = " + poid_chemin);
				chemin_frere = this.brother.getShortestPathRecursive(sommet_final, poid_chemin, false);
				if(chemin_frere != null) poid_chemin_frere = sommet_final.getVerticeValue();
			}
			
			if(this.son != null){
//				System.out.println(this.name + " fils : " + this.fils.getName() + " poid = " + (poid_chemin + 1));
				chemin_fils = this.son.getShortestPathRecursive(sommet_final, poid_chemin + this.weight_to_son, true);
				if(chemin_fils != null) poid_chemin_fils = sommet_final.getVerticeValue();
			}
			
			if(chemin_fils != null || chemin_frere != null){
				
				if(ce_sommet_est_un_fils){
					
					chemin = new GraphVertice();
					chemin.setName("créer par " + this.name);
					chemin.setSon(this);
					
//					if(poid_chemin_fils > poid_chemin_frere){
//						chemin.setFrere(chemin_frere);
//					}else{
//						chemin.setFrere(chemin_fils);
//					}
					
					if(chemin_fils != null && chemin_frere == null){
						chemin.setBrother(chemin_fils);
					}else if(chemin_fils == null && chemin_frere != null){
						chemin.setBrother(chemin_frere);
					}else{
//						System.out.println("deux chemins trouvés; poid fils = " + poid_chemin_fils + "; poid frere = " + poid_chemin_frere);
						if(poid_chemin_fils > poid_chemin_frere){
							chemin.setBrother(chemin_frere);
						}else{
							chemin.setBrother(chemin_fils);
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
		
		this.visited = true;
		
		String separateur = "";
		for(int i = 0; i < profondeur; i++){
			separateur += "|";
		}
		
		String info = "";
		
		switch (type) {
		case 0:
			info = "ra :" + this.name + " (" + this.weight_value + ")";
			break;
		case 1:
			info = separateur + "fi :" + this.name + " (" + this.weight_value + ")";
			break;
		case 2:
			info = separateur + "fr :" + this.name + " (" + this.weight_value + ")";
			break;
		default:
			break;
		}

		System.out.println(info);
		
		if(this.son != null){
			if(!this.son.hasBeenVisited()){
				this.son.printGraph(profondeur + 1, 1);
			}else{
				System.out.println(separateur + "- fi : " + this.son.getName() + " (loop)");
			}
		}else{
			System.out.println(separateur + "- fi : null");
		}
		
		if(this.brother != null){
			this.brother.printGraph(profondeur + 1, 2);
		}else{
			System.out.println(separateur + "- fr : null");
		}
		

	}
}
