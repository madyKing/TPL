/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package donnees;

/**
 *
 * @author macintosh
 */
public class Incendie {
    private Case position;
    private int litreEauNec;

    public Incendie(Case c, int l) {
	this.position = c;
	this.litreEauNec = l;
    }

    public Case getCase() {
        return this.position;
    }

    public int getIntensite() {
        return this.litreEauNec;
    }

    public void setIntensite(int intensite) {
        this.litreEauNec = intensite;
    }

    public void setPosition(Case c) {
    this.position = c;
}

}
