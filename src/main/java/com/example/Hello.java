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
import org.scijava.java3d.utils.image.TextureLoader;
import org.scijava.java3d.utils.universe.PlatformGeometry;
import org.scijava.java3d.utils.universe.SimpleUniverse;
import org.scijava.vecmath.Color3f;
import org.scijava.vecmath.Point3d;
import org.scijava.vecmath.TexCoord2f;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;


class Hello {
    private static Logger log = Logger.getLogger(Hello.class.getName());

    private static String[] titles = {"images/10.png", "images/1.png", "images/2.png",
            "images/3.png", "images/4.png", "images/5.png", "images/6.png",
            "images/7.png", "images/8.png", "images/9.png" };

    private static String[] holders = {"images/season.png", "images/nissei.png", "images/one.png",
            "images/vanguard.png", "images/daiwa.png", "images/season.png", "images/leos.png",
            "images/leos.png", "images/smta.png", "images/nissei.png" };

    private static Point3d[] verts = new Point3d[4];
    private static Point3d[] hVerts = new Point3d[4];
    private static Point3d[] bVerts = new Point3d[4];

    private static TexCoord2f[] fCoords = new TexCoord2f[4];
    private static TexCoord2f[] bCoords = new TexCoord2f[4];

    private static BranchGroup root = new BranchGroup();
    private static BranchGroup rotGroup = null;

    private static TransformGroup trans = new TransformGroup();

    static {
        verts[0] = new Point3d(-0.8, 0.0, 0.0);
        verts[1] = new Point3d(0.8, 0.0, 0.0);
        verts[2] = new Point3d(0.8, 0.1, 0.0);
        verts[3] = new Point3d(-0.8, 0.1, 0.0);

        hVerts[0] = new Point3d(-0.8, 0.0, 0.01);
        hVerts[1] = new Point3d(0.8, 0.0, 0.01);
        hVerts[2] = new Point3d(0.8, 0.1, 0.01);
        hVerts[3] = new Point3d(-0.8, 0.1, 0.01);

        bVerts[0] = new Point3d(0.8, 0.0, -0.01);
        bVerts[1] = new Point3d(-0.8, 0.0, -0.01);
        bVerts[2] = new Point3d(-0.8, 0.1, -0.01);
        bVerts[3] = new Point3d(0.8, 0.1, -0.01);

        fCoords[0] = new TexCoord2f(0.0f, 0.0f);
        fCoords[1] = new TexCoord2f(1.0f, 0.0f);
        fCoords[2] = new TexCoord2f(1.0f, 1.0f);
        fCoords[3] = new TexCoord2f(0.0f, 1.0f);

        bCoords[0] = new TexCoord2f(1.0f, 1.0f);
        bCoords[1] = new TexCoord2f(0.0f, 1.0f);
        bCoords[2] = new TexCoord2f(0.0f, 0.0f);
        bCoords[3] = new TexCoord2f(1.0f, 0.0f);
    }

    private Appearance createAppearance(String imageFile) {
        // texture
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image img = toolkit.getImage(imageFile);
        Texture2D tex = (Texture2D)new TextureLoader(img, new Container()).getTexture();

        // material
        Material mat = new Material();
        mat.setLightingEnable(true);
        mat.setAmbientColor(new Color3f(0.1f, 0.1f, 0.1f));
        mat.setDiffuseColor(new Color3f(0.3f, 0.3f, 0.3f));
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

        return ap;
    }

    private Shape3D createShape(String imageFile, Point3d[] verts, TexCoord2f[] texcoords) {
        QuadArray geom = new QuadArray(verts.length, GeometryArray.COORDINATES | GeometryArray.TEXTURE_COORDINATE_2);
        geom.setCapability(Geometry.ALLOW_INTERSECT);
        geom.setCoordinates(0, verts);
        geom.setTextureCoordinates(0, 0, texcoords);
        Shape3D shape = new Shape3D(geom, createAppearance(imageFile));
        shape.setCapability(Shape3D.ALLOW_GEOMETRY_READ);
        return shape;
    }

    private BranchGroup createRotation() {
        BranchGroup group = new BranchGroup();

        group.setCapability(BranchGroup.ALLOW_DETACH);

        // rotation
        Transform3D axis = new Transform3D();
        axis.rotZ( -(Math.PI / 2.0) );

        Alpha alpha = new Alpha(10, 200L);
        alpha.setStartTime(System.currentTimeMillis() + 100L);

        RotationInterpolator rotationInterpolator =
                new RotationInterpolator(alpha, trans, axis, 0.0f, (float)(Math.PI));
        rotationInterpolator.setSchedulingBounds(new BoundingSphere(new Point3d(), 100.0));
        group.addChild(rotationInterpolator);

        return group;
    }

    public Hello() {

        SimpleUniverse universe = new SimpleUniverse();
        log.severe("geometry="+universe.getViewingPlatform().getPlatformGeometry());

        universe.getCanvas().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_0) {
                    if (rotGroup != null && rotGroup.isLive()) {
                        log.severe("keyPress: rotGroup is live.");
                        root.removeChild(rotGroup);
                    }
                    rotGroup = createRotation();
                    root.addChild(rotGroup);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        root.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        root.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        root.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);

        // transform
        trans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        trans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        trans.setCapability(TransformGroup.ENABLE_PICK_REPORTING);

        root.addChild(trans);

        // lighting
        DirectionalLight light = new DirectionalLight();
        light.setInfluencingBounds(new BoundingSphere(new Point3d(), 100.0));
        trans.addChild(light);

        trans.addChild(createShape(holders[1], bVerts, bCoords));

        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(root);

        universe.getViewer().getJFrame(0).setSize(1280, 1024);
    }

    public static void main(String[] args) {
        new Hello();
    }
}
