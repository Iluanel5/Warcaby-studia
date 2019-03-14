import java.awt.Color;
import java.awt.Graphics;

public class Disc {

	protected int x, y; // umieszczenie w pikselach na ekranie
	private int a; // rozmiar ko³a
	protected int owner; // w³aœciciel (jeœli 0, to brak w³aœciciela)
	protected int type; // typ pionka 1 - zwyk³y; 2 - damka, 0 - brak

	Disc(int x, int y, int a, int owner, int type){
		this.x = x;
		this.y = y;
		this.a = a;
		this.owner = owner;
		this.type = type;
	}
	
	public void paint(Graphics gr){
		Color color2 = new Color(0,0,0);
		Color color1 = new Color(0,0,255);
		
		if(this.owner == 1) gr.setColor(color1);
		else if(this.owner == 2) gr.setColor(color2);
		else {
			// owner == 0
			int i = this.y;
			int j = this.x;
			if(j % 2 == 0){
				if(i % 2 == 0) gr.setColor(Color.red);
				else gr.setColor(Color.white);
			}else{
				if(i % 2 == 0) gr.setColor(Color.white);
				else gr.setColor(Color.red);
			}
		}
		
		if(this.owner != 0) gr.fillOval(this.x, this.y, this.a, this.a);
	
		if (this.type == 2 && this.owner != 0) {
			gr.setColor(Color.yellow);
			gr.fillOval(this.x, this.y, this.a/4, this.a/4);
		}
	}
}
