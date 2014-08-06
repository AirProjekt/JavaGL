/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vjezbe
 */
public class Vektor {
    
    private double a;
    private double b;
    private double c;
    
    public Vektor(double a, double b, double c){
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * @return the a
     */
    public double getA() {
        return a;
    }

    /**
     * @param a the a to set
     */
    public void setA(double a) {
        this.a = a;
    }

    /**
     * @return the b
     */
    public double getB() {
        return b;
    }

    /**
     * @param b the b to set
     */
    public void setB(double b) {
        this.b = b;
    }

    /**
     * @return the c
     */
    public double getC() {
        return c;
    }

    /**
     * @param c the c to set
     */
    public void setC(double c) {
        this.c = c;
    }
    
    public void podjeliJedinicnim(){
        double korijen = Math.pow(a, 2) + Math.pow(b, 2) + Math.pow(c, 2);
        double duzina = Math.sqrt(korijen);
        this.a = a / duzina;
        this.b = b / duzina;
        this.c = c / duzina;
    }
    
    public static Vektor produkt(Vektor vektor1, Vektor vektor2){
        double i,j,k;
        i = vektor1.getB()*vektor2.getC() - vektor1.getC()*vektor2.getB();
        j = vektor1.getA()*vektor2.getC() - vektor1.getC()*vektor2.getA();
        k = vektor1.getA()*vektor2.getB() - vektor1.getB()*vektor2.getA();
        return new Vektor(i, -j, k);
    }
}
