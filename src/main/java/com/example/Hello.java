package com.example;

import org.scijava.java3d.Appearance;
import org.scijava.java3d.BoundingSphere;
import org.scijava.java3d.BranchGroup;
import org.scijava.java3d.Canvas3D;
import org.scijava.java3d.DirectionalLight;
import org.scijava.java3d.Material;
import org.scijava.java3d.Texture2D;
import org.scijava.java3d.TransformGroup;
import org.scijava.java3d.utils.behaviors.mouse.MouseRotate;
import org.scijava.java3d.utils.geometry.Box;
import org.scijava.java3d.utils.geometry.Primitive;
import org.scijava.java3d.utils.image.TextureLoader;
import org.scijava.java3d.utils.universe.SimpleUniverse;
import org.scijava.vecmath.Color3f;
import org.scijava.vecmath.Point3d;

import java.awt.*;

class Hello {
    public Hello() {

        SimpleUniverse universe = new SimpleUniverse();

        BranchGroup group = new BranchGroup();

        // transform
        TransformGroup trans = new TransformGroup();
        trans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        trans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        trans.setCapability(TransformGroup.ENABLE_PICK_REPORTING);

        BoundingSphere bound = new BoundingSphere(new Point3d(), 100.0);

        MouseRotate rot = new MouseRotate();
        rot.setTransformGroup(trans);
        rot.setSchedulingBounds(bound);
        trans.addChild(rot);
        group.addChild(trans);


        // lighting
        DirectionalLight light = new DirectionalLight();
        light.setInfluencingBounds(bound);
        trans.addChild(light);

        // texture
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image img = toolkit.getImage("images/1.png");
        Texture2D tex = (Texture2D)new TextureLoader(img, new Container()).getTexture();

        // material
        Material mat = new Material();
        mat.setDiffuseColor(new Color3f());
        mat.setSpecularColor(new Color3f(0.1f, 0.1f, 0.1f));

        // appearance
        Appearance ap = new Appearance();
        ap.setMaterial(mat);
        ap.setTexture(tex);

        // shape
        Box box = new Box(0.9f, 0.1f, 0.01f,
                Primitive.GENERATE_TEXTURE_COORDS,
                ap);
        trans.addChild(box);

        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(group);
    }

    public static void main(String[] args) {
        new Hello();
    }
}
