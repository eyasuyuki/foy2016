package com.example;

import org.scijava.java3d.Alpha;
import org.scijava.java3d.Appearance;
import org.scijava.java3d.BoundingSphere;
import org.scijava.java3d.BranchGroup;
import org.scijava.java3d.DirectionalLight;
import org.scijava.java3d.Geometry;
import org.scijava.java3d.GeometryArray;
import org.scijava.java3d.Material;
import org.scijava.java3d.QuadArray;
import org.scijava.java3d.RotationInterpolator;
import org.scijava.java3d.Shape3D;
import org.scijava.java3d.Texture2D;
import org.scijava.java3d.TextureAttributes;
import org.scijava.java3d.Transform3D;
import org.scijava.java3d.TransformGroup;
import org.scijava.java3d.utils.behaviors.mouse.MouseRotate;
import org.scijava.java3d.utils.image.TextureLoader;
import org.scijava.java3d.utils.universe.SimpleUniverse;
import org.scijava.vecmath.Color3f;
import org.scijava.vecmath.Point3d;
import org.scijava.vecmath.TexCoord2f;
import org.scijava.vecmath.Vector3d;

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

        //BoundingSphere bound = new BoundingSphere(new Point3d(), 100.0);

//        MouseRotate rot = new MouseRotate();
//        rot.setTransformGroup(trans);
//        rot.setSchedulingBounds(bound);
//        trans.addChild(rot);
        group.addChild(trans);

        // lighting
        DirectionalLight light = new DirectionalLight();
        light.setInfluencingBounds(new BoundingSphere(new Point3d(), 100.0));
        trans.addChild(light);

        // texture
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image img = toolkit.getImage("images/1.png");
        Texture2D tex = (Texture2D)new TextureLoader(img, new Container()).getTexture();

        // material
        Material mat = new Material();
        mat.setLightingEnable(true);
        mat.setAmbientColor(new Color3f(0.1f, 0.1f, 0.1f));
        mat.setDiffuseColor(new Color3f(0.2f, 0.2f, 0.2f));
        mat.setSpecularColor(new Color3f(0.5f, 0.5f, 0.5f));
        mat.setShininess(64.0f);

        // appearance
        Appearance ap = new Appearance();
        ap.setMaterial(mat);
        ap.setTexture(tex);
        //ap.setPolygonAttributes(new PolygonAttributes(PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_BACK, 0.0f, false));
        TextureAttributes txatt = new TextureAttributes();
        txatt.setTextureMode(TextureAttributes.COMBINE);
        ap.setTextureAttributes(txatt);

        // shape
        Point3d[] verts = new Point3d[4];
        verts[0] = new Point3d(-0.8, 0.0, 0.0);
        verts[1] = new Point3d(0.8, 0.0, 0.0);
        verts[2] = new Point3d(0.8, 0.1, 0.0);
        verts[3] = new Point3d(-0.8, 0.1, 0.0);

        TexCoord2f[] texcoods = new TexCoord2f[4];
        texcoods[0] = new TexCoord2f(0.0f, 0.0f);
        texcoods[1] = new TexCoord2f(1.0f, 0.0f);
        texcoods[2] = new TexCoord2f(1.0f, 1.0f);
        texcoods[3] = new TexCoord2f(0.0f, 1.0f);

        QuadArray geom = new QuadArray(verts.length, GeometryArray.COORDINATES | GeometryArray.TEXTURE_COORDINATE_2);
        geom.setCapability(Geometry.ALLOW_INTERSECT);
        geom.setCoordinates(0, verts);
        geom.setTextureCoordinates(0, 0, texcoods);
        Shape3D shape = new Shape3D(geom, ap);
        shape.setCapability(Shape3D.ALLOW_GEOMETRY_READ);
        trans.addChild(shape);

        // rotation
        Transform3D axis = new Transform3D();
        axis.rotZ( -(Math.PI / 2.0) );

        Alpha alpha = new Alpha(-1, 200L);

        RotationInterpolator rotationInterpolator =
                new RotationInterpolator(alpha, trans, axis, 0.0f, (float)(Math.PI*2.0));
        rotationInterpolator.setSchedulingBounds(new BoundingSphere(new Point3d(), 100.0));
        trans.addChild(rotationInterpolator);

        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(group);
    }

    public static void main(String[] args) {
        new Hello();
    }
}
