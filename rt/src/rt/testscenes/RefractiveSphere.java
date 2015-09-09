package rt.testscenes;

import rt.*;
import rt.cameras.*;
import rt.films.BoxFilterFilm;
import rt.integrators.*;
import rt.intersectables.*;
import rt.lightsources.*;
import rt.samplers.*;
import rt.materials.*;
import rt.tonemappers.ClampTonemapper;

import javax.vecmath.*;

/**
 * Test scene for refractive objects, renders a sphere in front of a planar background.
 */
public class RefractiveSphere extends Scene {
		
	public RefractiveSphere()
	{
		// Output file name
		outputFilename = new String("../output/testscenes/RefractiveSphere");
		
		// Image width and height in pixels
		width = 512;
		height = 512;
		
		// Number of samples per pixel
		SPP = 32;
		
		// Specify which camera, film, and tonemapper to use
		Vector3f eye = new Vector3f(0.f, 5.f, 2.f);
		Vector3f lookAt = new Vector3f(0.f, 0.f, 1.f);
		Vector3f up = new Vector3f(0.f, 0.f, 1.f);
		float fov = 60.f;
		float aspect = 1.f;
		camera = new MovableCamera(eye, lookAt, up, fov, aspect, width, height);
		film = new BoxFilterFilm(width, height);
		tonemapper = new ClampTonemapper();
		
		// Specify which integrator and sampler to use
		integratorFactory = new WhittedIntegratorFactory();
		samplerFactory = new RandomSamplerFactory();		
		
		Material refractive = new Diffuse(new Spectrum(1, 0, 0));

		
		// Ground and back plane
		// A grid with red and white lines, line thickness 0.01, zero offset shift, and tile size 0.125, all in world coordinates
		//XYZGrid grid = new XYZGrid(new Spectrum(0.2f, 0.f, 0.f), new Spectrum(1.f, 1.f, 1.f), 0.01f, new Vector3f(0.f, 0.f, 0.f), 0.125f);
		Plane backPlane = new Plane(new Vector3f(0.f, 0.f, 1f), 0f);
		//backPlane.material = new Diffuse(new Spectrum(0F,0.3F,1F));
		backPlane.material = refractive;
		// A sphere for testing
		Sphere sphere = new Sphere(new Point3f(0F,-2F,1.5F),1F);
		sphere.material = new Diffuse(new Spectrum(0, 0, 1));
		
		// Collect objects in intersectable list
		IntersectableList intersectableList = new IntersectableList();

		
		intersectableList.add(sphere);
		intersectableList.add(backPlane);
		
		// Set the root node for the scene
		root = intersectableList;
		
		// Light sources
		Vector3f lightPos = new Vector3f(eye);
		lightPos.add(new Vector3f(5.f, 5.f, 5.f));
		LightGeometry pointLight1 = new PointLight(new Vector3f(0,0,1F), new Spectrum(100.f, 100.f, 100.f));
		//lightPos.add(new Vector3f(2.f, 0.f, 0.f));
		LightGeometry pointLight2 = new PointLight(lightPos, new Spectrum(14.f, 14.f, 14.f));
		LightGeometry pointLight3 = new PointLight(new Vector3f(0.f, 7.f, 0.f), new Spectrum(14.f, 14.f, 14.f));
		lightList = new LightList();
		lightList.add(pointLight1);
		//lightList.add(pointLight2);
		//lightList.add(pointLight3);
	}
	
}
