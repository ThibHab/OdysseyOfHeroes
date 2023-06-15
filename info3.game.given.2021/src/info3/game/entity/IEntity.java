package info3.game.entity;

import java.awt.Graphics;

import info3.game.automata.Category;
import info3.game.automata.Direction;

public interface IEntity {
	public void Move(Direction d);
	
	public void Turn(Direction d);
	
	public void Egg(Direction d, Category c);
	
	public void Hit(Direction d);
	
	public void Jump();
	
	public void Explode();
	
	public void Pick(Direction d);
	
	public void Pop(Direction d, Category c);
	
	public void Wizz(Direction d, Category c);
	
	public void Power();
	
	public void Store(Category c);
	
	public void Throw(Direction d, Category c);
	
	public void Wait();
	
	public void paint(Graphics g, int TileSize);
}
