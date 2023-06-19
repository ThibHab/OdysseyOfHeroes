package info3.game.entity;

public class TransparencyBlock extends DecorElement {
	
	DecorElement target;
	
	TransparencyBlock(int x,int y,DecorElement target){
		super();
		this.location=new Location(x,y);
		this.target=target;
	}
	
}