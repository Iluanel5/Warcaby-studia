import java.awt.Color;
import java.awt.Graphics;

public class Board {
	
	private int a;
	private Square[][] table = new Square[8][8];

	Board(int a){
		this.a = a;
		setMainBoard();
	}
	
	private void setMainBoard(){
		int i,j;
		Color color1, color2;
		
		/* Ustalenie kolorów planszy */
		color1 = new Color(255,0,0);
		color2 = new Color(255,255,255);
		
		/* Wype³nienie planszy odpowiednimi elementami Square */
		for (j = 0; j < 8; ++j){
			if(j % 2 == 0){
				for (i = 0; i < 8; ++i){
					if(i % 2 == 0) this.table[j][i] = new Square(color1, this.a/8, this.a/8*i, this.a/8*j);
					else this.table[j][i] = new Square(color2, this.a/8, this.a/8*i, this.a/8*j);
				}
			}else{
				for (i = 0; i < 8; ++i){
					if(i % 2 == 0) this.table[j][i] = new Square(color2, this.a/8, this.a/8*i, this.a/8*j);
					else this.table[j][i] = new Square(color1, this.a/8, this.a/8*i, this.a/8*j);
				}
			}
		}
	}
	
	/* Rysowanie planszy g³ównej */
	public void paintMainBoard(Graphics gr){
		int i,j;
		
		for (i = 0; i < 8; ++i)
			for (j = 0; j < 8; ++j)
				this.table[j][i].paint(gr);
	}
	
}
