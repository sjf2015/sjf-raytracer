package rt.testscenes;

import java.io.IOException;

import rt.*;
import rt.accelerators.BSPAccelerator;
import rt.cameras.*;
import rt.films.*;
import rt.integrators.*;
import rt.intersectables.*;
import rt.lightsources.*;
import rt.samplers.*;
import rt.tonemappers.*;
import rt.util.Timer;
import rt.materials.*;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import org.omg.CORBA.TRANSACTION_MODE;

/**
 * Test scene for instancing and rendering triangle meshes.
 */
public class TeapotShadowTest extends Scene {

	public IntersectableList objects;

	/**
	 * Timing: 8.5 sec on 12 core Xeon 2.5GHz, 24 threads
	 */
	public TeapotShadowTest()
	{	
		outputFilename = new String("../output/testscenes/MeshTextureTest");
		
		// Specify integrator to be used
		integratorFactory = new PointLightIntegratorFactory();
		
		// Specify pixel sampler to be used
	//	samplerFactory = new OneSamplerFactory();
		samplerFactory = new UniformSamplerFactory();
		
		SPP = 16;
		
		// Make camera and film
		Vector3f eye = new Vector3f(0.f,0.f,2.f);
		Vector3f lookAt = new Vector3f(0.f,0.f,0.f);
		Vector3f up = new Vector3f(0.f,1.f,0.f);
		float fov = 60.f;
		int width = 256;
		int height = 256;
		float aspect = (float)width/(float)height;
		camera = new MovableCamera(eye, lookAt, up, fov, aspect, width, height);
		film = new BoxFilterFilm(width, height);						
		tonemapper = new ClampTonemapper();
		
		// List of objects
		objects = new IntersectableList();	
				
		// Box
		Plane plane = new Plane(new Vector3f(0.f, 1.f, 0.f), 1.f);
		plane.material = new Diffuse();
		objects.add(plane);		
		
		plane = new Plane(new Vector3f(0.f, 0.f, 1.f), 1.f);
		plane.material = new Diffuse();
		objects.add(plane);
		
		plane = new Plane(new Vector3f(-1.f, 0.f, 0.f), 1.f);
		plane.material = new Diffuse();
		objects.add(plane);
		
		plane = new Plane(new Vector3f(1.f, 0.f, 0.f), 1.f);
		plane.material = new Diffuse();
		objects.add(plane);
		
		plane = new Plane(new Vector3f(0.f, -1.f, 0.f), 1.f);
		plane.material = new Diffuse();
		objects.add(plane);
		
		// Add objects
		Timer timer = new Timer();
		Mesh mesh;
		BSPAccelerator accelerator;
		try
		{
			
			mesh = ObjReader.read("../obj/sphere.obj", 1.f);
			mesh.material = new Textured("../textures/sphere.bmp");
			
			timer.reset();
			accelerator = new BSPAccelerator(mesh);
			System.out.printf("Accelerator computed in %d ms.\n", timer.timeElapsed());
			
			objects.add(accelerator); 	
		} catch(IOException e) 
		{
			System.out.printf("Could not read .obj file\n");
			return;
		}
						
		root = objects;
		
		// List of lights
		lightList = new LightList();
		
		LightGeometry light = new PointLight(new Vector3f(0.f,0.8f,0.8f), new Spectrum(3.f, 3.f, 3.f));
		lightList.add(light);
		
		light = new PointLight(new Vector3f(-0.8f,0.2f,1.f), new Spectrum(1.5f, 1.5f, 1.5f));
		lightList.add(light);		
	}
}
