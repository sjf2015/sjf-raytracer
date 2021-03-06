package rt.testscenes;

import java.io.File;
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
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.omg.CORBA.TRANSACTION_MODE;

/**
 * Test scene for instancing and rendering the space.
 */
public class Skyscraper extends Scene {

	public IntersectableList objects;

	public Skyscraper()
	{	
		outputFilename = new String("../output/testscenes/Skyscraper");
		
		// Specify integrator to be used
		integratorFactory = new PointLightIntegratorFactory();
		
		// Specify pixel sampler to be used
		//samplerFactory = new OneSamplerFactory();
		samplerFactory = new RandomSamplerFactory();
		SPP = 12;
		
		// Make camera and film
		Vector3f eye = new Vector3f(0.f,0.f,5.f);
		Vector3f lookAt = new Vector3f(0.f,0.f,0.f);
		Vector3f up = new Vector3f(0.f,1.f,0.f);
		float fov = 60.f;
		int width = 1280;
		int height = 720;
		float aspect = (float)width/(float)height;
		camera = new MovableCamera(eye, lookAt, up, fov, aspect, width, height);
		film = new BoxFilterFilm(width, height);						
		tonemapper = new ClampTonemapper();
		
		// List of objects
		objects = new IntersectableList();	
		
		// Skyscraper
			Timer timer = new Timer();
			Mesh mesh;
			BSPAccelerator accelerator;
			try
				{
					
					mesh = ObjReader.read("../obj/skyscraper.obj", 1.f);
					mesh.material = new Diffuse();
					
					timer.reset();
					accelerator = new BSPAccelerator(mesh);
					System.out.printf("Accelerator computed in %d ms.\n", timer.timeElapsed());
					
					objects.add(accelerator);
				} 	catch(IOException e) 
				{
					System.out.printf("Could not read .obj file\n");
					return;
				}
			
				
		root = objects;
		
		// List of lights
		lightList = new LightList();
		
		LightGeometry light = new PointLight(new Vector3f(0.f,4f,0.8f), new Spectrum(100.f, 100.f, 100.f));
		lightList.add(light);
		
		light = new PointLight(new Vector3f(-0.8f,0.2f,2.f), new Spectrum(1f, 1f, 1f));
		lightList.add(light);		
	}
}