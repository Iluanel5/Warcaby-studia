/*
* Autor: Karolina Niemira
* H5X1S1
* Warcaby
*/

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Window extends Applet implements ComponentListener{

	private int a;
	private Board board;
	private Placement discBoard;
	protected int[] score = {12, 12}; // 0 - gracz 1, 1 - gracz 2
	
	public void init() {
		setBackground(Color.gray);
		repaint();
		this.board = new Board(this.a);
		this.discBoard = new Placement(this.a,this);
	}
	
	public void paint (Graphics gr){
		repaint();
		this.discBoard.windowRepaint(this.a);
		boardRepaint();
		this.discBoard.paintDiscBoard(gr);	
	}
	
	public void end(Graphics gr){
		if (this.score[0] == 0 || this.score[1] == 0) {
			gr.setFont(new Font("TimesRoman", Font.BOLD+Font.ITALIC,this.a/7));
			gr.setColor(Color.orange);
			gr.drawString("KONIEC GRY", 10, this.a/2+20);
		}
	}
	
	public void boardRepaint(){
		this.board = new Board(this.a);
		this.board.paintMainBoard(this.getGraphics());
	}
	
	public void repaint(){
		int b = getWidth();
		int c = getHeight();
		
		if(c < b)	this.a = c;
		else		this.a = b;
	}
	
	public void paintScoreBoard(Graphics gr){
		gr.setColor(Color.gray);
		gr.fillRect(this.a+2, 0, getWidth(), getHeight());
		gr.setFont(new Font("TimesRoman", Font.BOLD+Font.ITALIC,this.a/24));
		gr.setColor(Color.blue);
		gr.drawString("Gracz 1: "+this.score[0], this.a+20, this.a/10);
		gr.setColor(Color.black);
		gr.drawString("Gracz 2: "+this.score[1], this.a+20, this.a/10*2);
	}

	public void paintRound(Graphics gr, int player){
		gr.setFont(new Font("TimesRoman", Font.BOLD+Font.ITALIC,this.a/24));
		gr.setColor(Color.orange);
		gr.drawString("Teraz kolej Gracza:", this.a+20, this.a/10*4);
		if (player == 1){
			gr.setColor(Color.blue);
			gr.drawString("niebieskiego ", this.a+20, this.a/10*9/2);
		}else {
			gr.setColor(Color.black);
			gr.drawString("czarnego ", this.a+20, this.a/10*9/2);
		}
	}
	public void componentResized(ComponentEvent arg0) {	repaint(); }
	public void componentHidden(ComponentEvent arg0) {}
	public void componentMoved(ComponentEvent arg0) {}
	public void componentShown(ComponentEvent arg0) {}
}
