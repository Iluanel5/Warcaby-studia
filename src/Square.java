import java.awt.Color;
import java.awt.Graphics;

public class Square{

	private Color color;
	private int x;
	private int y;
	private int a;

	Square(Color color, int a, int x, int y){
		this.color = color;
		this.x = x;
		this.y = y;
		this.a = a;
	}

	public void paint(Graphics gr){
		gr.setColor(color);
		gr.fillRect(this.x, this.y, this.a, this.a);
	}
}
