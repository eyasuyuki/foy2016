package com.example;

import org.apache.commons.lang.StringUtils;
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
import org.scijava.java3d.Switch;
import org.scijava.java3d.SwitchValueInterpolator;
import org.scijava.java3d.Texture2D;
import org.scijava.java3d.TextureAttributes;
import org.scijava.java3d.Transform3D;
import org.scijava.java3d.TransformGroup;
import org.scijava.java3d.utils.image.TextureLoader;
import org.scijava.java3d.utils.universe.SimpleUniverse;
import org.scijava.vecmath.Color3f;
import org.scijava.vecmath.Point3d;
import org.scijava.vecmath.TexCoord2f;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    private static Shape3D[] shapes = new Shape3D[10];
    private static Shape3D[] fShapes = new Shape3D[11];
    private static Shape3D[] bShapes = new Shape3D[10];

    private static BranchGroup root = new BranchGroup();
    private static BranchGroup rotGroup = null;

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

        shapes[0] = createShape(titles[0], verts, fCoords);
        shapes[1] = createShape(titles[1], verts, fCoords);
        shapes[2] = createShape(titles[2], verts, fCoords);
        shapes[3] = createShape(titles[3], verts, fCoords);
        shapes[4] = createShape(titles[4], verts, fCoords);
        shapes[5] = createShape(titles[5], verts, fCoords);
        shapes[6] = createShape(titles[6], verts, fCoords);
        shapes[7] = createShape(titles[7], verts, fCoords);
        shapes[8] = createShape(titles[8], verts, fCoords);
        shapes[9] = createShape(titles[9], verts, fCoords);

        bShapes[0] = createShape(holders[0], bVerts, bCoords);
        fShapes[0] = createShape(titles[0], hVerts, fCoords);
        bShapes[1] = createShape(holders[1], bVerts, bCoords);
        fShapes[1] = createShape(titles[1], hVerts, fCoords);
        bShapes[2] = createShape(holders[2], bVerts, bCoords);
        fShapes[2] = createShape(titles[2], hVerts, fCoords);
        bShapes[3] = createShape(holders[3], bVerts, bCoords);
        fShapes[3] = createShape(titles[3], hVerts, fCoords);
        bShapes[4] = createShape(holders[4], bVerts, bCoords);
        fShapes[4] = createShape(titles[4], hVerts, fCoords);
        bShapes[5] = createShape(holders[5], bVerts, bCoords);
        fShapes[5] = createShape(titles[5], hVerts, fCoords);
        bShapes[6] = createShape(holders[6], bVerts, bCoords);
        fShapes[6] = createShape(titles[6], hVerts, fCoords);
        bShapes[7] = createShape(holders[7], bVerts, bCoords);
        fShapes[7] = createShape(titles[7], hVerts, fCoords);
        bShapes[8] = createShape(holders[8], bVerts, bCoords);
        fShapes[8] = createShape(titles[8], hVerts, fCoords);
        bShapes[9] = createShape(holders[9], bVerts, bCoords);
        fShapes[9] = createShape(titles[9], hVerts, fCoords);
        fShapes[10] = createShape("images/blank.png", hVerts, fCoords);

        for (int i=0; i<10; i++) {
            shapes[i].setCapability(Shape3D.ALLOW_PARENT_READ);
            fShapes[i].setCapability(Shape3D.ALLOW_PARENT_READ);
            bShapes[i].setCapability(Shape3D.ALLOW_PARENT_READ);
        }
        fShapes[10].setCapability(Shape3D.ALLOW_PARENT_READ);

    }

    private static Appearance createAppearance(String imageFile) {

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

        if (StringUtils.isNotEmpty(imageFile)) {
            // texture
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image img = toolkit.getImage(imageFile);
            Texture2D tex = (Texture2D)new TextureLoader(img, new Container()).getTexture();
            ap.setTexture(tex);
            TextureAttributes txatt = new TextureAttributes();
            txatt.setTextureMode(TextureAttributes.MODULATE);
            ap.setTextureAttributes(txatt);
        }

        return ap;
    }

    private static Shape3D createShape(String imageFile, Point3d[] verts, TexCoord2f[] texcoords) {
        QuadArray geom = new QuadArray(verts.length, GeometryArray.COORDINATES | GeometryArray.TEXTURE_COORDINATE_2);
        geom.setCapability(Geometry.ALLOW_INTERSECT);
        geom.setCoordinates(0, verts);
        geom.setTextureCoordinates(0, 0, texcoords);
        Shape3D shape = new Shape3D(geom, createAppearance(imageFile));
        shape.setCapability(Shape3D.ALLOW_GEOMETRY_READ);
        return shape;
    }

    private BranchGroup createRotation(int index) {
        BranchGroup group = new BranchGroup();

        TransformGroup rotTrans = new TransformGroup();

        TransformGroup trans = new TransformGroup();

        // transform
        rotTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        rotTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        rotTrans.setCapability(TransformGroup.ENABLE_PICK_REPORTING);

        group.addChild(trans);
        group.addChild(rotTrans);

        group.setCapability(BranchGroup.ALLOW_DETACH);

        // rotation
        Transform3D axis = new Transform3D();
        axis.rotZ( -(Math.PI / 2.0) );

        Alpha alpha = new Alpha(10, 200L);
        alpha.setStartTime(System.currentTimeMillis() + 100L);

        RotationInterpolator rotationInterpolator =
                new RotationInterpolator(alpha, rotTrans, axis, 0.0f, (float)(Math.PI));
        rotationInterpolator.setSchedulingBounds(new BoundingSphere(new Point3d(), 100.0));
        group.addChild(rotationInterpolator);

        Switch sw = new Switch();
        Switch rSw = new Switch();

        sw.setCapability(Switch.ALLOW_SWITCH_READ);
        sw.setCapability(Switch.ALLOW_SWITCH_WRITE);
        rSw.setCapability(Switch.ALLOW_SWITCH_READ);
        rSw.setCapability(Switch.ALLOW_SWITCH_WRITE);

        List<Integer> posList = new ArrayList<>();
        for (int i=0; i<10; i++) { posList.add(i); }
        posList.remove(index);
        Collections.shuffle(posList);

        for (int i=0; i<9; i++) {
            Shape3D top = shapes[posList.get(i)];
            Shape3D f;
            if (i == 0) {
                log.severe("i="+i+", posList.get("+i+")="+posList.get(i));
                f = fShapes[10];
            } else {
                log.severe("i="+i+", posList.get("+i+")="+posList.get(i)+", posList.get("+i+"-1)="+posList.get(i-1));
                f = fShapes[posList.get(i-1)];
            }
            Shape3D b = bShapes[posList.get(i)];

            sw.addChild(top);

            BranchGroup g = new BranchGroup();
            g.addChild(f);
            g.addChild(b);

            rSw.addChild(g);
        }
        sw.addChild(shapes[index]);
        rSw.addChild(bShapes[index]);

        trans.addChild(sw);
        rotTrans.addChild(rSw);

        Alpha a2 = new Alpha(1, 2000L);
        Alpha a3 = new Alpha(1, 2000L);
        a2.setStartTime(alpha.getStartTime());
        a3.setStartTime(alpha.getStartTime());

        SwitchValueInterpolator si = new SwitchValueInterpolator(a2, sw);
        SwitchValueInterpolator rsi = new SwitchValueInterpolator(a3, rSw);
        si.setSchedulingBounds(new BoundingSphere(new Point3d(), 100.0));
        rsi.setSchedulingBounds(new BoundingSphere(new Point3d(), 100.0));
        si.setLastChildIndex(9);
        rsi.setLastChildIndex(9);

        group.addChild(si);
        group.addChild(rsi);

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
                int k = -1;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_0:
                        k = 0;
                        break;
                    case KeyEvent.VK_1:
                        k = 1;
                        break;
                    case KeyEvent.VK_2:
                        k = 2;
                        break;
                    case KeyEvent.VK_3:
                        k = 3;
                        break;
                    case KeyEvent.VK_4:
                        k = 4;
                        break;
                    case KeyEvent.VK_5:
                        k = 5;
                        break;
                    case KeyEvent.VK_6:
                        k = 6;
                        break;
                    case KeyEvent.VK_7:
                        k = 7;
                        break;
                    case KeyEvent.VK_8:
                        k = 8;
                        break;
                    case KeyEvent.VK_9:
                        k = 9;
                        break;
                    default:
                        return;
                }
                if (rotGroup != null && rotGroup.isLive()) {
                    log.severe("keyPress: rotGroup is live.");
                    root.removeChild(rotGroup);
                }
                rotGroup = createRotation(k);
                root.addChild(rotGroup);
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        root.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        root.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        root.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);


        // lighting
        DirectionalLight light = new DirectionalLight();
        light.setInfluencingBounds(new BoundingSphere(new Point3d(), 100.0));
        root.addChild(light);

        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(root);

        universe.getViewer().getJFrame(0).setSize(640, 480);
    }

    public static void main(String[] args) {
        new Hello();
    }
}
