import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Winner
 */
public class Vjetromjer extends Applet{
    private double kut;
    
  class Animacija extends Thread {
    long pauza, kraj;

    Animacija(double fps, double trajanje) {
    // fps - broj sličica u sekundi
    // trajanje - trajanje u sekundama

      // pauza između sličica u milisekundama
      pauza = Math.round(1000.0 / fps);

      // poslije koliko koraka animacija završava
      kraj = Math.round(trajanje * fps);
    } // Animacija

    @Override
    public void run() {
      long brojac = 0;
      kut = 0;
      while(brojac++ < kraj) {
        try {
          sleep(pauza); // pauza u milisekundama
        } catch (InterruptedException e) { }
        kut++;
        repaint(); // traži ponovno iscrtavanje
      }
    } // run
  } // class Animacija
    
    @Override
    public void init() {
	setBackground(Color.yellow);
        resize(1000, 1000);
        // 30 sličica u sekundi u trajanju 20 sekundi
        (new Animacija(40.0, 15.0)).start();
    }
    
    @Override
    public void update(Graphics g) { paint(g); }
    
    @Override
    public void paint(Graphics g){
        MT3D m = new MT3D();
        MT3D m1 = new MT3D();
        int w = getWidth();
        int h = getHeight();
        Image slika = createImage(w, h);
        Graphics gs = slika.getGraphics();
        Persp gks = new Persp(gs,-10,10,-10,10,20,w,h);
        gks.KSK(20, 20, 25, 0, 0, 0, 0, 0, 1);
        gks.postaviBoju(Color.red);
        for(int i = -10; i<=10;i++){
            gks.postaviNa(i, -10, 0);
            gks.linijaDo(i, 10, 0);
        }
        for(int i = -10; i<=10;i++){
            gks.postaviNa(-10, i, 0);
            gks.linijaDo(10, i, 0);
        }
        gks.postaviBoju(Color.black);
        //stozac
        gks.stozac(3, 10, 15);
        //siri valjak
        m.pomakni(0, 0, 7);
        m1.rotirajZ(kut);
        m1.mult(m);
        gks.trans(m1);
        gks.valjak(1, 3, 10);
        //prvi tanji valjak
        m.rotirajY(90.0);
        m1.pomakni(1, 0, 9);
        m1.mult(m);
        m.rotirajZ(kut);
        m.mult(m1);
        gks.trans(m);
        gks.valjak(0.5, 5, 6);
        //drugi tanji valjak
        m.rotirajY(90.0);
        m1.pomakni(1, 0, 9);
        m1.mult(m);
        m.rotirajZ(kut+120.0);
        m.mult(m1);
        gks.trans(m);
        gks.valjak(0.5, 5, 6);
        //treci tanji valjak
        m.rotirajY(90.0);
        m1.pomakni(1, 0, 9);
        m1.mult(m);
        m.rotirajZ(kut + 240.0);
        m.mult(m1);
        gks.trans(m);
        gks.valjak(0.5, 5, 6);
        //prva kugla
        m.pomakni(8, -0.4, 9);
        m1.rotirajZ(kut);
        m1.mult(m);
        gks.trans(m1);
        gks.poluKugla(2, 20, 10);
        //druga kugla
        m.pomakni(8, -0.4, 9);
        m1.rotirajZ(kut+120.0);
        m1.mult(m);
        gks.trans(m1);
        gks.poluKugla(2, 20, 10);
        //treca kugla
        m.pomakni(8, -0.4, 9);
        m1.rotirajZ(kut+240.0);
        m1.mult(m);
        gks.trans(m1);
        gks.poluKugla(2, 20, 10);
        g.drawImage(slika, 0, 0, null);
    }
    
    private void kocka(Persp gks, double a){
        gks.postaviNa(0, 0, 0);
        gks.linijaDo(a, 0, 0);
        gks.linijaDo(a, a, 0);
        gks.linijaDo(0, a, 0);
        gks.linijaDo(0, 0, 0);
        gks.linijaDo(0, 0, a);
        gks.linijaDo(a, 0, a);
        gks.linijaDo(a, 0, 0);
        gks.postaviNa(a, 0, a);
        gks.linijaDo(a, a, a);
        gks.linijaDo(a, a, 0);
        gks.postaviNa(a, a, a);
        gks.linijaDo(0, a, a);
        gks.linijaDo(0, a, 0);
        gks.postaviNa(0, a, a);
        gks.linijaDo(0, 0, a);
    }
}
