/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package donnees;

public class Case {
    private int ligne;
    private int colonne;
    private NatureTerrain nature;

    public Case(int lig, int col, NatureTerrain nat){
		this.ligne = lig;
		this.colonne = col;
		this.nature = nat;
    }
    
    public int getLigne(){
		return this.ligne;
    }
    
    public int getColonne(){
		return this.colonne;
    }
    
    public NatureTerrain getNature(){
	return this.nature;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Case == false) {
            return false;
        }
        Case c = (Case) o;
        
        return (this.ligne == c.getLigne()) && (this.colonne == c.getColonne());
    }
}