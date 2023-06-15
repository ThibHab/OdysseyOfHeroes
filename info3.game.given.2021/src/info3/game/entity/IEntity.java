package info3.game.entity;

import java.awt.Graphics;

import info3.game.automata.Aut_Category;
import info3.game.automata.Aut_Direction;

public interface IEntity {
	public void Move(Aut_Direction d);
	
	public void Turn(Aut_Direction d);
	
	public void Egg(Aut_Direction d, Aut_Category c);
	
	public void Hit(Aut_Direction d);
	
	public void Jump();
	
	public void Explode();
	
	public void Pick(Aut_Direction d);
	
	public void Pop(Aut_Direction d, Aut_Category c);
	
	public void Wizz(Aut_Direction d, Aut_Category c);
	
	public void Power();
	
	public void Store(Aut_Category c);
	
	public void Throw(Aut_Direction d, Aut_Category c);
	
	public void Wait();
	
	public void paint(Graphics g, int TileSize, float screenPosX, float screenPosY);
}
