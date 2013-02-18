package launcher;

import java.util.ArrayList;

import utils.graph.Sommet;

public class Test {

	/**
	 * 
	 * A-B-E
	 * | | 
	 * C-D F
	 * 
	 * @param args
	 */
	
	public static void main(String[] args) {

		ArrayList<Sommet> list = new ArrayList<Sommet>();
		
		Sommet a = new Sommet();
		a.setName("A");
		Sommet b = new Sommet();
		b.setName("B");
		Sommet c = new Sommet();
		c.setName("C");
		Sommet d = new Sommet();
		d.setName("D");
		Sommet e = new Sommet();
		e.setName("E");
		Sommet f = new Sommet();
		f.setName("F");
		
		list.add(a);
		list.add(b);
		list.add(c);
		list.add(d);
		list.add(e);
		list.add(f);
		
		Sommet a2 = new Sommet();
		a2.setName("A2");
		
		a.setFils(b);
		a2.setFils(c);
		a.setFrere(a2);

		Sommet b2 = new Sommet();
		b2.setName("B2");
		Sommet b3 = new Sommet();
		b3.setName("B3");
		b.setFils(a);
		b2.setFils(d);
		b3.setFils(e);
		b2.setFrere(b3);
		b.setFrere(b2);

		Sommet c2 = new Sommet();
		c2.setName("C2");
		c.setFils(a);
		c2.setFils(d);
		c.setFrere(c2);
		
		Sommet d2 = new Sommet();
		d2.setName("D2");
		d.setFils(c);
		d2.setFils(b);
		d.setFrere(d2);
		
		e.setFils(b);
		
		System.out.println(a.getSommetFrere().getSommetFils().toString());
		
		System.out.println(a.getSommetFrere().toString());
		System.out.println(a.toString());
		
		a.printGraph(0, 0);
		
		System.out.println("chemin:\n");


		for(int i = 0; i < list.size(); i++){
			list.get(i).resetPassage();
		}
		System.out.println("***********\n* chemin: *\n***********");
		Sommet chemin = a.getPlusPetitCheminRecursif(e);
		
		for(int i = 0; i < list.size(); i++){
			list.get(i).resetPassage();
		}
		if(chemin != null) chemin.printGraph(0, 0);
			
		
		
	}
	
}
