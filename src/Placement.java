import java.awt.*;
import java.awt.event.*;

public class Placement implements MouseListener{
	private Window ap;
	private int x0, y0, x1, y1; // miejsce, gdzie klikn¹³ u¿ytkownik
	private int a; // rozmiar boku a planszy
	private int round = 1; // 1 - kolejka gracza 1; 2 - kolejka gracza 2
	private Disc[][] discTable = new Disc[8][8]; // rozstawienie pionków
	private int action[] = {0, 0}; // czy nast¹pi³ atak / czy nast¹pi³ ruch 
	private int err = 0; 
	
	Placement(int a, Window ap) {
		this.a = a;
        this.ap = ap;
        ap.addMouseListener(this);
        setDiscBoard();
    }
	
	public void windowRepaint(int a){
		this.a = a;
		ap.boardRepaint();
		int i, j;
		/* Rysowanie rozstawienia pionków */
		for (j = 0; j < 8; ++j)
			for (i = 0; i < 8; ++i)
				this.discTable[i][j] = new Disc(this.a/8*i, this.a/8*j, this.a/8, this.discTable[i][j].owner, this.discTable[i][j].type);
		
		ap.paintScoreBoard(ap.getGraphics());
		
		if (this.err == 0){
			ap.paintRound(ap.getGraphics(), this.round);
			ap.end(ap.getGraphics());
		}
	}
	
	/* Wype³nienie planszy odpowiednimi elementami Disc na pocz¹tku */
	public void setDiscBoard(){
		int i, j = 0, owner;
		
		for (j = 0; j < 8; ++j){
			if(j > 3) owner = 2; else owner = 1;
			if (j == 3 || j == 4){
				for (i = 0; i < 8; ++i) this.discTable[i][j] = new Disc(this.a/8*i, this.a/8*j, this.a/8, 0, 0);
			}else{
				if (j % 2 == 0){
					for (i = 0; i < 8; ++i){
						if(i % 2 == 0) this.discTable[i][j] = new Disc(this.a/8*i, this.a/8*j, this.a/8, owner, 1);
						else this.discTable[i][j] = new Disc(this.a/8*i, this.a/8*j, this.a/8, 0, 0);
					}
				}else{
					for (i = 0; i < 8; ++i){
						if(i % 2 != 0) this.discTable[i][j] = new Disc(this.a/8*i, this.a/8*j, this.a/8, owner, 1);
						else this.discTable[i][j] = new Disc(this.a/8*i, this.a/8*j, this.a/8, 0, 0);
					}
				}
			}
		}
	}
	
	/* Rysowanie rozstawienia pionków */
	public void paintDiscBoard(Graphics gr){
		int i, j;
		for (j = 0; j < 8; ++j)
			for (i = 0; i < 8; ++i)
				this.discTable[j][i].paint(gr);
	}
	
	/* Zmiana rundy */
	private void changeRound(){
		if (this.round == 2) this.round=1;
		else  this.round=2;
		this.action[0] = 0;
		this.action[1] = 0;
	}
	
	/* "ruch" */
	public void setDisc(){
		Disc tmp = new Disc(this.a/8*this.x1,this.a/8*this.y1, this.a/8, discTable[this.x0][this.y0].owner, discTable[this.x0][this.y0].type);
		this.discTable[this.x1][this.y1] = tmp;
		this.discTable[this.x0][this.y0].owner = 0;
		this.discTable[this.x0][this.y0].type = 0;
		//this.action[0] = 1;
		windowRepaint(this.a);
		paintDiscBoard(ap.getGraphics());
	}
	
	/* Sprawdzenie, czy istnieje w miejscu wybranym pionek */
	private boolean checkIfExist(int klik, int x, int y){
		if (klik == 1){
			if (discTable[x][y].owner != 0 && discTable[x][y].owner <= 2) return true;
			else return false;
		}else return false;
	}
	
	/*Sprawdzenie, czy mo¿na siê ruszyæ we wskazane miejsce*/
	private boolean checkIfPossible(int x0, int y0, int x1, int y1){	
		// Dla zwyk³ych pionków
		if (this.discTable[x0][y0].type != 2){
			if(this.discTable[x0][y0].owner == 1){
				if (checkIfExist(1, x0, y0) && (x1==x0-1 || x1==x0+1) &&  y1==y0+1  && this.action[0] == 0) {
					if(this.discTable[x1][y1].owner ==  0) {this.action[1]++; return true;}
					else return false;
				} else {
					if(!checkIfAttack(x1, y1, x0, y0))  return false; 
					else {
						this.action[0] = 1;
						attack(x0, y0, x1-x0, y1-y0);
						return true;
					}
				}
				// koniec dla gracza 1
			} else {
				if(this.discTable[x0][y0].owner == 2){
					if (checkIfExist(1, x0, y0) && (x1==x0-1 || x1==x0+1) && y1==y0-1  && this.action[0] == 0) {
						if(this.discTable[x1][y1].owner ==  0) {this.action[1]=1; return true;}
						
						else return false;
					} else {
						
						if(!checkIfAttack(x1, y1, x0, y0))  { return false; }
						else {
							this.action[0] = 1;
							attack(x0, y0, x1-x0, y1-y0);
							return true;
						}
					} // koniec dla gracza 2;
				} else  return false; 
			}
			// dla damki
		} else {
			int tmpx = x1 - x0, tmpy = y1 - y0;
			System.out.println("to jest ruch damki");
			if (checkIfExist(1, x0, y0) && Math.abs(tmpx) == Math.abs(tmpy)){
				
				if(this.discTable[x1][y1].owner ==  0) {
					
					if (checkQueenAction(tmpx, tmpy) > 0 && checkQueenAction(tmpx, tmpy) <= 2){
						if (checkQueenAction(tmpx, tmpy) == 1){ 
							this.action[1]=1; 
							return true;
						} else {
							this.action[0] = 1;
							//attack(x0, y0, x1-x0, y1-y0);
							return true;
						}
					} else return false;
				} else return false;
			} else 
				return false;
		} // koniec dla damki
	}

	private int checkQueenAction(int tmpx, int tmpy){
		int queenAction = 0, tmp = 0, i, j = 0, k = 0;
		for (i = 1; i <= Math.abs(tmpx); i++){
			if (tmpx >= 0 && tmpy >= 0){
				//jeœli nastepny krok ma innego w³aœciciela ni¿ poruszaj¹cy siê 
				if (this.discTable[this.x0+i][this.y0+i].owner != this.discTable[this.x0][this.y0].owner){
					// jesli jest rozny od zera (przeciwnik)
					if (this.discTable[this.x0+i][this.y0+i].owner != 0) {++tmp; j = i; k = i;}
					else queenAction = 1;
				} else { 
					System.out.println("Nie mo¿esz przeskoczyæ w³asnych pionków");
					queenAction = 0;
					i = Math.abs(tmpx)+1;
				}
			}
			if (tmpx < 0 && tmpy >= 0){
				//jeœli nastepny krok ma innego w³aœciciela ni¿ poruszaj¹cy siê 
				if (this.discTable[this.x0-i][this.y0+i].owner != this.discTable[this.x0][this.y0].owner){
					// jesli jest rozny od zera (przeciwnik)
					if (this.discTable[this.x0-i][this.y0+i].owner != 0) {++tmp; j = -i; k = i;}
					else queenAction = 1;
				} else { 
					System.out.println("Nie mo¿esz przeskoczyæ w³asnych pionków");
					queenAction = 0;
					i = Math.abs(tmpx)+1;
				}
			}
			if (tmpx >= 0 && tmpy < 0){
				//jeœli nastepny krok ma innego w³aœciciela ni¿ poruszaj¹cy siê 
				if (this.discTable[this.x0+i][this.y0-i].owner != this.discTable[this.x0][this.y0].owner){
					// jesli jest rozny od zera (przeciwnik)
					if (this.discTable[this.x0+i][this.y0-i].owner != 0) {++tmp; j = i; k = -i;}
					else queenAction = 1;
				} else { 
					System.out.println("Nie mo¿esz przeskoczyæ w³asnych pionków");
					queenAction = 0;
					i = Math.abs(tmpx)+1;
				}
			}
			if (tmpx < 0 && tmpy < 0){
				//jeœli nastepny krok ma innego w³aœciciela ni¿ poruszaj¹cy siê 
				if (this.discTable[this.x0-i][this.y0-i].owner != this.discTable[this.x0][this.y0].owner){
					// jesli jest rozny od zera (przeciwnik)
					if (this.discTable[this.x0-i][this.y0-i].owner != 0) {++tmp; j = -i; k = -i;}
					else queenAction = 1;
				} else { 
					System.out.println("Nie mo¿esz przeskoczyæ w³asnych pionków");
					queenAction = 0;
					i = Math.abs(tmpx)+1;
				}
			}
		}
		if (tmp > 1) {
			queenAction = 0;
			System.out.println("Nie mo¿esz przeskoczyæ wiêcej ni¿ jednego pionka");
		}
		if(tmp == 1) { 
			System.out.println("Atak damki mo¿liwy. Atakuje."); 
			queenAction = 2; 
			attack(x0, y0, j, k);
		}
		return queenAction;
	}
	
	/* Sprawdz, czy mo¿esz atakowaæ */
	private boolean checkIfAttack(int x, int y, int xa, int ya){
		/*
		 * x, y - odpowiednik dla x1, y1
		 * xa, ya - odpowiednik dla x0, y0
		 */
		int tmpx, tmpy;
		//boolean d = false;
		//jeœli istniej pionek w miejscu puszczenia
		if (!checkIfExist(1, x, y)){
			
			tmpx = x - xa; // ró¿nica w x
			tmpy = y - ya; // ró¿nica w y

			//jeœli jest to po ukosie dwa pola dalej
			if (Math.abs(tmpx) == Math.abs(tmpy) && Math.abs(tmpx) == 2){
	
				//jeœli istnieje pionek do przeskoczenia
				if (checkIfExist(1,(xa+(tmpx/Math.abs(tmpx))),(ya+(tmpy/Math.abs(tmpy))))){
					//jeœli pionek do przeskoczenia jest inny ni¿ aktywnego gracza
					if (this.discTable[xa+(tmpx/Math.abs(tmpx))][ya+(tmpy/Math.abs(tmpy))].owner != this.round){
						return true;
					}
				}			
			}
		}
		return false;
	}

	/* 
	 * przymus 
	 * dla 1 przymus po zbiciu dla jednego pionka, dla 2 przymus po rozpoczêciu nowej tury przez gracza
	 */
	private void compulsion(int mode){
		int i, j;
		if(mode == 2){
			for (i = 0; i < 8; ++i){
				for (j = 0; j < 8; ++j){
					if (this.discTable[i][j].type == 1)
					if (this.discTable[i][j].owner == this.round){
						if (i+2 < 8 && j+2 < 8)
							if (checkIfAttack(i+2, j+2, i, j)) paintRect(ap.getGraphics(), i+1, j+1);
						if (i-2 >= 0 && j+2 < 8)
							if (checkIfAttack(i-2, j+2, i, j)) paintRect(ap.getGraphics(), i-1, j+1);
						if (i+2 < 8 && j-2 >= 0)
							if (checkIfAttack(i+2, j-2, i, j)) paintRect(ap.getGraphics(), i+1, j-1);
						if (i-2 >= 0 && j-2 >= 0)
							if (checkIfAttack(i-2, j-2, i, j)) paintRect(ap.getGraphics(), i-1, j-1);
					}
				}
			}
		} else {
			if (this.discTable[x1][y1].type == 1){
			if (this.x1+2 < 8 && this.y1+2 < 8)
				if (checkIfAttack(this.x1+2, this.y1+2, this.x1, this.y1)) paintRect(ap.getGraphics(), this.x1+1, this.y1+1);
			if (this.x1-2 >= 0 && this.y1+2 < 8)
				if (checkIfAttack(this.x1-2, this.y1+2, this.x1, this.y1)) paintRect(ap.getGraphics(), this.x1-1, this.y1+1);
			if (this.x1+2 < 8 && this.y1-2 >= 0)
				if (checkIfAttack(this.x1+2, this.y1-2, this.x1, this.y1)) paintRect(ap.getGraphics(), this.x1+1, this.y1-1);
			if (this.x1-2 >= 0 && this.y1-2 >= 0)
				if (checkIfAttack(this.x1-2, this.y1-2, this.x1, this.y1)) paintRect(ap.getGraphics(), this.x1-1, this.y1-1);
			}
			}
	}

	/* bij */
	private void attack(int x, int y, int tmpx, int tmpy){
		//++this.go[0];
		if (this.discTable[x][y].type != 2){
			--ap.score[this.discTable[x+(tmpx/Math.abs(tmpx))][y+(tmpy/Math.abs(tmpy))].owner-1];
			this.discTable[x+(tmpx/Math.abs(tmpx))][y+(tmpy/Math.abs(tmpy))].owner = 0;
		} else {
			--ap.score[this.discTable[x+tmpx][y+tmpy].owner-1];
			this.discTable[x+tmpx][y+tmpy].owner = 0;
		}
	}
	
	/* Sprawdz czy królowa */
	private boolean checkIfQueen(){
		if (this.discTable[this.x1][this.y1].owner == 1){
			if (this.y1 == 7) return true;
			else return false;
			
		} else {
			
			if (this.discTable[this.x1][this.y1].owner == 2){
				if (this.y1 == 0) return true;
				else return false;
			} else 
				return false;
		}
	}

	private void paintRect(Graphics g, int i, int j){
		g.setColor(Color.cyan);
		g.drawRect(this.a/8*i, this.a/8*j, this.a/8, this.a/8);
		g.drawRect(this.a/8*i+1, this.a/8*j+1, this.a/8-2, this.a/8-2);
		this.action[0]=2;
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		// Zerowanie b³êdu
		this.err = 0;
		
		this.x0 = arg0.getX() / (this.a/8);
		this.y0 = arg0.getY() / (this.a/8);
		//System.out.println(x0+", "+y0);
		try{
			if (this.x0 >= this.discTable.length || this.y0 >= this.discTable.length) throw new Exceptions(1);
			if (this.discTable[this.x0][this.y0].owner == 0) throw new Exceptions(5);
			if (this.round != this.discTable[this.x0][this.y0].owner) throw new Exceptions(4);
		}catch(Exceptions e){++this.err;}
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
		this.x1 = arg0.getX() / (this.a/8);
		this.y1 = arg0.getY() / (this.a/8);
		
		if (err == 0){
			try{
				if (this.x1 >= this.discTable.length || this.y1 >= this.discTable.length) 
					throw new Exceptions(1);
				
				if (this.discTable[this.x1][this.y1].owner == this.discTable[this.x0][this.y0].owner) 
					throw new Exceptions(3);
				
				if (!checkIfPossible(this.x0, this.y0, this.x1, this.y1)) 
					throw new Exceptions(2);
				
				setDisc();
				
				if (this.action[0] != 0){
					if (this.x1+2 < 8 && this.y1+2 < 8) {
						if (checkIfAttack(this.x1+2, this.y1+2, this.x1, this.y1)) throw new Exceptions(6);
					}
					if (this.x1-2 >= 0 && this.y1+2 < 8) {
						if (checkIfAttack(this.x1-2, this.y1+2, this.x1, this.y1)) throw new Exceptions(6);
					}
					if (this.x1+2 < 8 && this.y1-2 >=0) {
						if (checkIfAttack(this.x1+2, this.y1-2, this.x1, this.y1)) throw new Exceptions(6);
					}
					if (this.x1-2 >= 0 && this.y1-2 >= 0) {
						if (checkIfAttack(this.x1-2, this.y1-2, this.x1, this.y1)) throw new Exceptions(6);
					}
				} 
				
				if(checkIfQueen())
					this.discTable[this.x1][this.y1].type = 2;

			}catch(Exceptions e){ ++this.err;}
			
			if(err == 0){
				if(this.action[0] == 1) compulsion(1);
				else compulsion(2);
				if (this.action[1] != 0 || this.action[0] == 1) changeRound();
				windowRepaint(this.a);
				paintDiscBoard(ap.getGraphics());
				System.out.println(this.round);
			}
			
			if(this.action[0] == 1) compulsion(1);
			else compulsion(2);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}

}