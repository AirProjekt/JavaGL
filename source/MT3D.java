/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Winner
 */
public class MT3D {
    private double[][] matrica;
	
	public MT3D(){
		this.matrica = new double[4][4];
		this.identitet();
	}

	public void pomakni(double px, double py, double pz){
		this.identitet();
		this.matrica[0][3]= px;
		this.matrica[1][3]= py;
                this.matrica[2][3]= pz;
	}
	
	public void skaliraj(double sx, double sy, double sz){
		this.identitet();
		this.matrica[0][0]= sx;
		this.matrica[1][1]= sy;
                this.matrica[2][2]= sz;
	}
	
	public void rotirajZ(double kut){
		this.identitet();
		kut = Math.toRadians(kut);
		this.matrica[0][0] = Math.cos(kut);
		this.matrica[0][1] = -Math.sin(kut);
		this.matrica[1][0] = Math.sin(kut);
		this.matrica[1][1] = Math.cos(kut);
	}
	
        public void rotirajX(double kut){
		this.identitet();
		kut = Math.toRadians(kut);
		this.matrica[1][1] = Math.cos(kut);
		this.matrica[1][2] = -Math.sin(kut);
		this.matrica[2][1] = Math.sin(kut);
		this.matrica[2][2] = Math.cos(kut);
	}
        
        public void rotirajY(double kut){
		this.identitet();
		kut = Math.toRadians(kut);
		this.matrica[0][0] = Math.cos(kut);
		this.matrica[0][2] = Math.sin(kut);
		this.matrica[2][0] = -Math.sin(kut);
		this.matrica[2][2] = Math.cos(kut);
	}
        
        public void rotiraj(double x1, double y1, double z1, double x2, double y2, double z2, double kut){
            this.identitet();
            double a = (x2 - x1)/Math.sqrt(Math.pow(x2 - x1, 2)+Math.pow(y2 - y1, 2)+Math.pow(z2 - z1, 2));
            double b = (y2 - y1)/Math.sqrt(Math.pow(x2 - x1, 2)+Math.pow(y2 - y1, 2)+Math.pow(z2 - z1, 2));
            double c = (z2 - z1)/Math.sqrt(Math.pow(x2 - x1, 2)+Math.pow(y2 - y1, 2)+Math.pow(z2 - z1, 2));
            double d = Math.sqrt(b*b + c*c);
            MT3D m = new MT3D();
            double kutA = Math.toDegrees(Math.asin(b/d));
            double kutB = Math.toDegrees(Math.asin(a));
            m.pomakni(-x1, -y1, -z1);
            this.rotirajX(kutA);
            this.mult(m);
            m.rotirajY(-kutB);
            m.mult(this);
            this.rotirajZ(kut);
            this.mult(m);
            m.rotirajY(kutB);
            m.mult(this);
            this.rotirajX(-kutA);
            this.mult(m);
            m.pomakni(x1, y1, z1);
            m.mult(this);
            this.matrica = m.getMatrica();
        }
	
	public void identitet(){
		this.matrica[0][0] = 1;
		this.matrica[0][1] = 0;
		this.matrica[0][2] = 0;
                this.matrica[0][3] = 0;
		this.matrica[1][0] = 0;
		this.matrica[1][1] = 1;
		this.matrica[1][2] = 0;
                this.matrica[1][3] = 0;
		this.matrica[2][0] = 0;
		this.matrica[2][1] = 0;
		this.matrica[2][2] = 1;
                this.matrica[2][3] = 0;
                this.matrica[3][0] = 0;
		this.matrica[3][1] = 0;
		this.matrica[3][2] = 0;
                this.matrica[3][3] = 1;
                
	}
	
	public double[][] getMatrica(){
		return this.matrica;
	}
	
	public void mult(MT3D m){
		double[][] matrica2 = m.getMatrica();
		double[][] matrica1 = new double[4][4];
		this.kopirajMatricu(matrica1);
		double rez = 0;
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				for(int k=0;k<4;k++){
					rez += matrica1[i][k]*matrica2[k][j];
				}
				this.matrica[i][j] = rez;
				rez = 0;
			}
		}
	}
        
        public void postaviNaPrvi(Vektor u, Vektor v, Vektor n){
            this.identitet();
            this.matrica[0][0] = u.getA();
            this.matrica[0][1] = u.getB();
            this.matrica[0][2] = u.getC();
            this.matrica[1][0] = v.getA();
            this.matrica[1][1] = v.getB();
            this.matrica[1][2] = v.getC();
            this.matrica[2][0] = n.getA();
            this.matrica[2][1] = n.getB();
            this.matrica[2][2] = n.getC();
        }
        
        public void postaviNaDrugi(double x0, double y0, double z0){
            this.identitet();
            this.matrica[0][3] = -x0;
            this.matrica[1][3] = -y0;
            this.matrica[2][3] = -z0;
        }
	
	private void kopirajMatricu(double[][] matricaTo){
                for(int i = 0; i < 4; i++)
                    for(int j = 0; j < 4; j++)
                        matricaTo[i][j] = this.matrica[i][j];
	}
}
