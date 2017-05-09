package ge.komaroveli.gipa.Classes;

public class Colour
{
	private int color;
	private String text;
	
	public Colour(int color, String text)
	{
		this.color = color;
		this.text = text;
	}
	
	public int getColor()
	{
		return color;
	}
	
	public void setColor(int color)
	{
		this.color = color;
	}
	
	public String getText()
	{
		return text;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
}
