package rt.testscenes;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import rt.LightGeometry;
import rt.LightList;
import rt.Material;
import rt.Scene;
import rt.Spectrum;
import rt.cameras.MovableCamera;
import rt.films.BoxFilterFilm;
import rt.integrators.PointLightIntegratorFactory;
import rt.integrators.WhittedIntegratorFactory;
import rt.intersectables.IntersectableList;
import rt.intersectables.Plane;
import rt.intersectables.Sphere;
import rt.lightsources.PointLight;
import rt.materials.Diffuse;
import rt.materials.Gitterstruktur;
import rt.materials.Refractive;
import rt.materials.Schachbrett;
import rt.samplers.RandomSamplerFactory;
import rt.tonemappers.ClampTonemapper;

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
		Vector3f eye = new Vector3f(0.f, 0.f, 3.f);
		Vector3f lookAt = new Vector3f(0.f, 0.f, 0.f);
		Vector3f up = new Vector3f(1.f, 0.f, 0.f);
		float fov = 60.f;
		float aspect = 1.f;
		camera = new MovableCamera(eye, lookAt, up, fov, aspect, width, height);
		film = new BoxFilterFilm(width, height);
		tonemapper = new ClampTonemapper();
		
		// Specify which integrator and sampler to use
		integratorFactory = new WhittedIntegratorFactory();
		samplerFactory = new RandomSamplerFactory();		
		
		Material chess = new Gitterstruktur(0);

		
		// Ground and back plane
		// A grid with red and white lines, line thickness 0.01, zero offset shift, and tile size 0.125, all in world coordinates
		//XYZGrid grid = new XYZGrid(new Spectrum(0.2f, 0.f, 0.f), new Spectrum(1.f, 1.f, 1.f), 0.01f, new Vector3f(0.f, 0.f, 0.f), 0.125f);
		Plane backPlane = new Plane(new Vector3f(0.f, 0.f, 1f), 1f);
		backPlane.material = chess;
		// A sphere for testing
		Sphere sphere = new Sphere(new Point3f(0F,0F,1F),1F);
		sphere.material = new Refractive(1.3F);
		
		// Collect objects in intersectable list
		IntersectableList intersectableList = new IntersectableList();

		
		intersectableList.add(sphere);
		intersectableList.add(backPlane);
		
		// Set the root node for the scene
		root = intersectableList;
		
		// Light sources
		LightGeometry pointLight1 = new PointLight(new Vector3f(-1,0,3), new Spectrum(1000.f, 1000.f, 1000.f));
		lightList = new LightList();
		lightList.add(pointLight1);
	}
	
}
