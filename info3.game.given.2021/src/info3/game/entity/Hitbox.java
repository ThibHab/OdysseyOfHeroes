package info3.game.entity;

public class Hitbox {
	public Entity entity;
	public float width, height;
	public Location location;
	
	public Hitbox(Entity entity, float x, float y) {
		this.width = x;
		this.height = y;
		this.entity = entity;
		this.location = new Location(entity.location.getX() + (1 - this.width) / 2, entity.location.getY() + (1 - this.height) / 2);
	}
	
	public void update() {
		this.location.setX((float) (this.entity.location.getX() + (1 - this.width) / 2));
		this.location.setY((float) (this.entity.location.getY() + (1 - this.height) / 2));
	}
}
