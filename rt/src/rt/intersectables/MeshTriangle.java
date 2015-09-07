package rt.intersectables;

import javax.vecmath.Matrix3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import rt.HitRecord;
import rt.Intersectable;
import rt.Ray;
import rt.accelerators.AxisAlignedBox;

/**
 * Defines a triangle by referring back to a {@link Mesh}
 * and its vertex and index arrays. 
 */
public class MeshTriangle implements Intersectable {

	private Mesh mesh;
	private int index;
	
	/**
	 * Make a triangle.
	 * 
	 * @param mesh the mesh storing the vertex and index arrays
	 * @param index the index of the triangle in the mesh
	 */
	public MeshTriangle(Mesh mesh, int index)
	{
		this.mesh = mesh;
		this.index = index;		
	}
	
	public HitRecord intersect(Ray r)
	{
		float vertices[] = mesh.vertices;
		
		// Access the triangle vertices as follows (same for the normals):		
		// 1. Get three vertex indices for triangle
		int v0 = mesh.indices[index*3];
		int v1 = mesh.indices[index*3+1];
		int v2 = mesh.indices[index*3+2];
		
		// 2. Access x,y,z coordinates for each vertex
		float x0 = vertices[v0*3];
		float x1 = vertices[v1*3];
		float x2 = vertices[v2*3];
		float y0 = vertices[v0*3+1];
		float y1 = vertices[v1*3+1];
		float y2 = vertices[v2*3+1];
		float z0 = vertices[v0*3+2];
		float z1 = vertices[v1*3+2];
		float z2 = vertices[v2*3+2];
		
		Point3f a = new Point3f(x0,y0,z0);
		Point3f b = new Point3f(x1,y1,z1);
		Point3f c = new Point3f(x2,y2,z2);
		
		Vector3f e = r.origin;
		Vector3f d = r.direction;
		
		Matrix3f me = new Matrix3f();
		me.m00 = a.x - b.x;
		me.m01 = a.y - b.y;
		me.m02 = a.z - b.z;
		me.m10 = a.x - c.x;
		me.m11 = a.y - c.y;
		me.m12 = a.z - c.z;
		me.m20 = d.x;
		me.m21 = d.y;
		me.m22 = d.z;
		
		Vector3f m = new Vector3f(a.x - e.x, a.y - e.y, a.z - e.z);
		
		me.invert();
		
		me.transform(m);
		
		float t = m.z;
		float beta = m.x;
		float gamma = m.y;
		float alpha = 1f -beta -gamma;
		
		if(beta>0 && gamma>0 && beta+gamma<1){
			Point3f p = r.pointAt(t);
			
			float normals[] = mesh.normals;
			
			float nx0 = normals[v0*3];
			float nx1 = normals[v1*3];
			float nx2 = normals[v2*3];
			float ny0 = normals[v0*3+1];
			float ny1 = normals[v1*3+1];
			float ny2 = normals[v2*3+1];
			float nz0 = normals[v0*3+2];
			float nz1 = normals[v1*3+2];
			float nz2 = normals[v2*3+2];
			
			Vector3f normal = new Vector3f(alpha*nx0 + beta*ny0 + gamma*nz0, alpha*nx1 + beta*ny1 + gamma*nz1, alpha*nx2 + beta*ny2 + gamma*nz2);
			
			
			new HitRecord(t, p, normal, r.direction, this, mesh.material, 0, 0);
		}
		
		return null;
	}

	@Override
	public AxisAlignedBox getBoundingBox() {
		// TODO Generate some useful bounding box
		return null;
	}

	@Override
	public float surfaceArea() {
		// TODO Compute surface area of this triangle.
		return 0;
	}

}
