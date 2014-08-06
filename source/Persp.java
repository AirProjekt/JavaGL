
import java.awt.Color;
import java.awt.Graphics;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Winner
 */
public class Persp {
        private Graphics g;
	private double xPrvi,yPrvi;
	private double xDrugi,yDrugi;
	private double sX,sY;
	private double pX,pY;
	private double[][] matrica;
        private double[][] matricaKam;
	private double[] koordinate;
	private double[] rezultat;
        private double[] rezKam;
        private double d;
        private MT3D m;

	Persp(Graphics g, double xmin, double xmax, double ymin, double ymax,double d, int xsize, int ysize){
		this.g = g;
                this.d = d;
		this.sX = xsize/(xmax-xmin);
		this.sY = ysize/(ymax-ymin);
		this.pX = -sX*xmin;
		this.pY = sY*ymax;
		this.koordinate = new double[4];
		this.rezultat = new double[4];
                m = new MT3D();
                this.matrica = m.getMatrica();
                this.rezKam = new double[4];
	}
        
        public void KSK(double x0, double y0, double z0, double x1, double y1, 
                double z1, double Vx, double Vy, double Vz){
            Vektor n = new Vektor(x0-x1, y0-y1, z0-z1);
            n.podjeliJedinicnim();
            Vektor u;
            Vektor v1 = new Vektor(Vx, Vy, Vz);
            u = Vektor.produkt(v1, n);
            u.podjeliJedinicnim();
            Vektor v = Vektor.produkt(n, u);
            MT3D m1 = new MT3D();
            m1.postaviNaPrvi(u, v, n);
            MT3D m2 = new MT3D();
            m2.postaviNaDrugi(x0, y0, z0);
            m1.mult(m2);
            this.matricaKam = m1.getMatrica();
        }
        
        public void stozac(double r, double h,int n){
            double korak = 2*Math.PI/n;
            postaviNa(r*Math.cos(0), r*Math.sin(0), 0);
            for (double kut = 0; kut <= 2*Math.PI; kut+=korak) {
                linijaDo(r*Math.cos(kut), r*Math.sin(kut), 0);
            }
            for (double kut = 0; kut <= 2*Math.PI; kut+=korak) {
                postaviNa(r*Math.cos(kut), r*Math.sin(kut), 0);
                linijaDo(0, 0, h);
            }
        }
        
        public void valjak(double r, double h, int n){
            double korak = 2*Math.PI/n;
            postaviNa(r*Math.cos(0), r*Math.sin(0), 0);
            for (double kut = 0; kut <= 2*Math.PI; kut+=korak) {
                linijaDo(r*Math.cos(kut), r*Math.sin(kut), 0);
            }
            postaviNa(r*Math.cos(0), r*Math.sin(0), h);
            for (double kut = 0; kut <= 2*Math.PI; kut+=korak) {
                linijaDo(r*Math.cos(kut), r*Math.sin(kut), h);
            }
            for (double kut = 0; kut <= 2*Math.PI; kut+=korak) {
                postaviNa(r*Math.cos(kut), r*Math.sin(kut), 0);
                linijaDo(r*Math.cos(kut), r*Math.sin(kut), h);
            }
        }
        
        public void kugla(double r, int m, int n){
            //crtanje paralela
            double korak = 2*Math.PI/m;
            double korakm = Math.PI/n;
            for (double phi = 0; phi <= 2*Math.PI; phi+=korak) {
                postaviNa(r*Math.sin(0)*Math.cos(phi), r*Math.sin(0)*Math.sin(phi), r*Math.cos(0));
                for (double delta = 0; delta <= 2*Math.PI; delta += Math.PI/100) {
                    linijaDo(r*Math.sin(delta)*Math.cos(phi), r*Math.sin(delta)*Math.sin(phi), r*Math.cos(delta));
                }
            }
            //crtanje meridijana
            for (double delta = 0; delta <= Math.PI; delta+=korakm) {
                postaviNa(r*Math.sin(delta)*Math.cos(0), r*Math.sin(delta)*Math.sin(0), r*Math.cos(delta));
                for (double phi = 0; phi <= 2*Math.PI; phi += Math.PI/100) {
                    linijaDo(r*Math.sin(delta)*Math.cos(phi), r*Math.sin(delta)*Math.sin(phi), r*Math.cos(delta));
                }
            }
        }
        
        public void poluKugla(double r, int m, int n){
            //crtanje paralela
            double korak = 2*Math.PI/m;
            double korakm = Math.PI/n;
            for (double phi = 0; phi <= Math.PI; phi+=korak) {
                postaviNa(r*Math.sin(0)*Math.cos(phi), r*Math.sin(0)*Math.sin(phi), r*Math.cos(0));
                for (double delta = 0; delta <= Math.PI; delta += Math.PI/100) {
                    linijaDo(r*Math.sin(delta)*Math.cos(phi), r*Math.sin(delta)*Math.sin(phi), r*Math.cos(delta));
                }
            }
            //crtanje meridijana
            for (double delta = 0; delta <= Math.PI; delta+=korakm) {
                postaviNa(r*Math.sin(delta)*Math.cos(0), r*Math.sin(delta)*Math.sin(0), r*Math.cos(delta));
                for (double phi = 0; phi <= Math.PI; phi += Math.PI/100) {
                    linijaDo(r*Math.sin(delta)*Math.cos(phi), r*Math.sin(delta)*Math.sin(phi), r*Math.cos(delta));
                }
            }
        }
	
	public void postaviNa(double x, double y, double z){
		//transformacija prvog matricom
		this.postaviKoordinate(x,y,z);
		this.pomnoziMatrice();
                this.pomnoziKamMatrice();
                x = -this.d*this.rezKam[0]/this.rezKam[2];
                y = -this.d*this.rezKam[1]/this.rezKam[2];
		//transformacija prvog
		this.xPrvi = this.sX*x + pX;
		this.yPrvi = -this.sY*y + pY;
	}
	
	public void linijaDo(double x, double y, double z){
		//transormacije matricom
		this.postaviKoordinate(x,y,z);
		this.pomnoziMatrice();
                this.pomnoziKamMatrice();
                x = -this.d*this.rezKam[0]/this.rezKam[2];
                y = -this.d*this.rezKam[1]/this.rezKam[2];
		//transformacija drugog
		this.xDrugi = this.sX*x + pX;
		this.yDrugi = -this.sY*y + pY;
		this.g.drawLine((int)this.xPrvi,(int)this.yPrvi,(int)this.xDrugi,(int)this.yDrugi);
		this.xPrvi = this.xDrugi;
		this.yPrvi = this.yDrugi;
	}
	
	public void postaviBoju(Color c){
		this.g.setColor(c);
	}
	
	public void trans(MT3D m){
		this.matrica = m.getMatrica();
	}
	
	public void pomnoziMatrice(){
		this.postaviRezultat();
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				this.rezultat[i] += this.matrica[i][j] * this.koordinate[j];
			}
		}
	}
        
        public void pomnoziKamMatrice(){
            this.postaviKamRezultat();
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				this.rezKam[i] += this.matricaKam[i][j] * this.rezultat[j];
			}
		}
	}
	
	public void postaviKoordinate(double x, double y, double z){
		this.koordinate[0] = x;
		this.koordinate[1] = y;
                this.koordinate[2] = z;
		this.koordinate[3] = 1;
	}
	
	public void postaviRezultat(){
		this.rezultat[0] = 0;
		this.rezultat[1] = 0;
		this.rezultat[2] = 0;
                this.rezultat[3] = 0;
	}
        
        public void postaviKamRezultat(){
		this.rezKam[0] = 0;
		this.rezKam[1] = 0;
		this.rezKam[2] = 0;
                this.rezKam[3] = 0;
	}
}
