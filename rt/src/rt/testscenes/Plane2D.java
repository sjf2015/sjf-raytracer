package rt.testscenes;

import rt.intersectables.Rectangle;

import javax.swing.Box;
import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import rt.LightGeometry;
import rt.LightList;
import rt.Scene;
import rt.Spectrum;
import rt.cameras.MovableCamera;
import rt.films.BoxFilterFilm;
import rt.integrators.PointLightIntegratorFactory;
import rt.integrators.WhittedIntegrator;
import rt.integrators.WhittedIntegratorFactory;
import rt.intersectables.Instance;
import rt.intersectables.IntersectableList;
import rt.intersectables.Plane;
import rt.intersectables.Sphere;
import rt.lightsources.PointLight;
import rt.materials.BlinnPhong;
import rt.materials.Diffuse;
import rt.materials.Fraktal;
import rt.materials.Gitterstruktur;
import rt.materials.PerlinNoisWood;
import rt.materials.PerlinNoise;
import rt.materials.Refractive;
import rt.materials.Schachbrett;
import rt.materials.Textured;
import rt.samplers.OneSamplerFactory;
import rt.samplers.UniformSamplerFactory;
import rt.tonemappers.ClampTonemapper;

public class Plane2D extends Scene{
	
	public Plane2D()
	{
		// Output file name
		outputFilename = new String("../output/testscenes/FusballTest");
		// Image width and height in pixels
		width = 1280;
		height =720;
		
		// Number of samples per pixel
		SPP = 8;
		
		// Specify which camera, film, and tonemapper to use
		Vector3f eye = new Vector3f(0.5f, -0.5f, 5.f);
		Vector3f lookAt = new Vector3f(0.5f, 0.f, 0.f);
		Vector3f up = new Vector3f(0f, 1.f, 0.f);
		float fov = 60.f;
		float aspect = 16.f/9.f;
		camera = new MovableCamera(eye, lookAt, up, fov, aspect, width, height);
		//camera = new PinholeCamera(eye, lookAt, up, fov, aspect, width, height);
		film = new BoxFilterFilm(width, height);
		tonemapper = new ClampTonemapper();
		
		// Specify which integrator and sampler to use
		integratorFactory = new WhittedIntegratorFactory();
		samplerFactory = new UniformSamplerFactory();
		
		IntersectableList iList = new IntersectableList();
		
		// Define some objects to be added to the scene. 
		// 5 planes can be used to define a box (with never ending walls).
		Plane p1 = new Plane(new Vector3f(0.f, 0.f, 1.f), 1.f);
		p1.material = new Schachbrett(1.3f);
		//iList.add(p1);
		
		Plane p2 = new Plane(new Vector3f(0.f, 0.f, 10.f), 1.f);
		p2.material = new Gitterstruktur(new Spectrum(0,0,1), new Spectrum(1,1,1), 0.1f, 2f);
		iList.add(p2);
		
		
		Sphere sphere = new Sphere(new Point3f(0F,0F,1F),1F);
		//sphere.material = refractive;
		//sphere.material = new Fraktal(0.3425f, 0.41f);
		sphere.material = new Schachbrett(1.3f);;
		iList.add(sphere);
		
		Matrix4f t = new Matrix4f();
		t.setIdentity();
		
		// Instance one
		t.setScale(1f);
		t.rotY((float) Math.PI);;
		Instance instance = new Instance(sphere, t);
		//iList.add(instance);	
		
		/*
		Rectangle rect = new Rectangle(new Vector3f(-1, -1, 0), new Vector3f(2,0,0), new Vector3f(0,2,0));
		rect.material = new Textured("../textures/textureKontrolle.jpg");
		iList.add(rect);
		*/
		
		this.root = iList;
		
		// Light sources
		lightList = new LightList();
		LightGeometry l1 = new PointLight(new Vector3f(0f, 0f, 4f), new Spectrum(5f, 5f, 5f));
		lightList.add(l1);
		LightGeometry l2 = new PointLight(new Vector3f(2f, 0f, 4f), new Spectrum(5f, 5f, 5f));
		//lightList.add(l2);
		
		
	}
}
