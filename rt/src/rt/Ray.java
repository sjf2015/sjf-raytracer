package rt;

import javax.vecmath.*;

/**
 * A ray represented by an origin and a direction.
 */
public class Ray {

	public Vector3f origin;
	public Vector3f direction;
	
	public Ray(Vector3f origin, Vector3f direction)
	{
		this.origin = new Vector3f(origin); 
		this.direction = new Vector3f(direction);
	}
	
	public Point3f pointAt(float t) 
	{
		Point3f p = new Point3f(direction);
		p.scaleAdd(t, origin);
		return p;
	}
}