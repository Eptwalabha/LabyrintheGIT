package com.labyrinth.utils.graph;

public class GraphVertex {

	private GraphVertex brother;
	private GraphVertex son;
	private boolean visited = false;
	private int weight_value = -1;
	
	private int weight_to_son;
	
	private String name = "<no name>";
	
	public GraphVertex(){
		this.brother = null;
		this.son = null;
		this.weight_to_son = 1;
	}
	
	public GraphVertex(GraphVertex brother, GraphVertex son){
		this.brother = brother;
		this.son = son;
		this.weight_to_son = 1;
	}
	
	public GraphVertex(GraphVertex brother, GraphVertex son, int weight_to_son){
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
	
	public GraphVertex getBrother(){
		return this.brother;
	}
	
	public GraphVertex getBrother(int position){
		
		if(position == 0){
			return this;
		}else{
			if(this.brother != null){
				return this.brother.getBrother(position - 1);
			}else{
				return null;
			}
		}
		
	}
	
	public GraphVertex getSon(){
		return this.son;
	}
	
	public int getVertexValue(){
		return this.weight_value;
	}
	
	public void setSon(GraphVertex nouveau_fils){
		this.son = nouveau_fils;
	}
	
	public void setBrother(GraphVertex nouveau_frere){
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
	
	public void stack(GraphVertex vertex){
		
		if(vertex != null){
			if(this.son == null){
//				System.out.println("new fils!");
				this.son = vertex;
			}else if(this.brother != null){
//				System.out.println("add!");
				this.brother.stack(vertex);
			}else{
//				System.out.println("new frere!");
				GraphVertex new_brother = new GraphVertex(null, vertex);
				this.brother = new_brother;
			}
		}
	}
	
	public GraphVertex getShortestPathRecursive(GraphVertex destination){
		
		return getShortestPathRecursive(destination, 0, true);
	}
	
	public GraphVertex getShortestPathRecursive(GraphVertex last_vertex, int path_weight, boolean this_vertex_is_a_son){
		
		if(path_weight < this.weight_value || this.weight_value == -1){
			
			if(this_vertex_is_a_son) this.weight_value = path_weight;
			GraphVertex chemin = new GraphVertex();
			
			if(this == last_vertex){
				chemin.setName("créer par " + this.name);
				chemin.setSon(this);
				return chemin;
			}
		
			GraphVertex path_brother = null;
			GraphVertex chemin_fils = null;
			
			int poid_chemin_frere = -1;
			int poid_chemin_fils = -1;
			
			if(this.brother != null){
				path_brother = this.brother.getShortestPathRecursive(last_vertex, path_weight, false);
				if(path_brother != null) poid_chemin_frere = last_vertex.getVertexValue();
			}
			
			if(this.son != null){
				chemin_fils = this.son.getShortestPathRecursive(last_vertex, path_weight + this.weight_to_son, true);
				if(chemin_fils != null) poid_chemin_fils = last_vertex.getVertexValue();
			}
			
			if(chemin_fils != null || path_brother != null){
				
				if(this_vertex_is_a_son){
					
					chemin = new GraphVertex();
					chemin.setName("créer par " + this.name);
					chemin.setSon(this);
					
					if(chemin_fils != null && path_brother == null){
						chemin.setBrother(chemin_fils);
					}else if(chemin_fils == null && path_brother != null){
						chemin.setBrother(path_brother);
					}else{
						if(poid_chemin_fils > poid_chemin_frere){
							chemin.setBrother(path_brother);
						}else{
							chemin.setBrother(chemin_fils);
						}
					}
					
				}else{
					
					if(chemin_fils != null && path_brother == null){
						chemin = chemin_fils;
					}else if(chemin_fils == null && path_brother != null){
						chemin = path_brother;
					}else{
						if(poid_chemin_fils > poid_chemin_frere){
							chemin = path_brother;
						}else{
							chemin = chemin_fils;
						}
					}
				}
				return chemin;
			}
		}
		
		return null;
	}
	
	public GraphVertex getAllVerticesConnectedTo(){
		
		GraphVertex list = new GraphVertex(null, null);
		
		this.getAllVerticesConnectedTo(list, true);
		
		return list;
	}
	
	
	private void getAllVerticesConnectedTo(GraphVertex list, boolean this_vertex_is_a_son){

		if(!this.visited){
			
			if(this_vertex_is_a_son){
				this.visited = true;
				list.stack(this);
			}
			
			if(this.brother != null) this.brother.getAllVerticesConnectedTo(list, false);
			if(this.son != null) this.son.getAllVerticesConnectedTo(list, true);
		}
	}
	
	public int countBrother(){
		
		if(this.brother == null){
			return 1;
		}else{
			return 1 + this.brother.countBrother();
		}
		
	}
	
	// type = 0 pour racine; 1 pour fils; 2 pour frere.
	public void printGraph(int depth, int type) {
		
		this.visited = true;
		
		String separator = "";
		for(int i = 0; i < depth; i++){
			separator += "|";
		}
		
		String info = "";
		
		switch (type) {
		case 0:
			info = "ra :" + this.name + " (" + this.weight_value + ")";
			break;
		case 1:
			info = separator + "fi :" + this.name + " (" + this.weight_value + ")";
			break;
		case 2:
			info = separator + "fr :" + this.name + " (" + this.weight_value + ")";
			break;
		default:
			break;
		}

		System.out.println(info);
		
		if(this.son != null){
			if(!this.son.hasBeenVisited()){
				this.son.printGraph(depth + 1, 1);
			}else{
				System.out.println(separator + "- fi : " + this.son.getName() + " (loop)");
			}
		}else{
			System.out.println(separator + "- fi : null");
		}
		
		if(this.brother != null){
			this.brother.printGraph(depth + 1, 2);
		}else{
			System.out.println(separator + "- fr : null");
		}
		

	}
}
