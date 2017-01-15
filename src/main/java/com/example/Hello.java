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
import org.scijava.java3d.MediaContainer;
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
import sun.applet.Main;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;


class Hello {
    private static Logger log = Logger.getLogger(Hello.class.getName());

    private static String[] musics = {
      "mg.au", "best.au"
    };

    private static String[] points = {
            "images/p0.png", "images/p1.png", "images/p2.png",
            "images/p3.png", "images/p4.png", "images/p5.png",
            "images/p6.png", "images/p7.png", "images/p8.png",
            "images/p9.png"

    };

    private static String[] lanks
            = {"images/l1.png", "images/l2.png", "images/l3.png",
            "images/l4.png", "images/l5.png", "images/l6.png",
            "images/l7.png", "images/l8.png", "images/l9.png",
            "images/l10.png"
    };

    private static String[] titles
            = {"images/1.png", "images/2.png", "images/3.png",
            "images/4.png", "images/5.png", "images/6.png",
            "images/7.png", "images/8.png", "images/9.png",
            "images/10.png",  "images/11a.png", "images/11b.png",
            "images/13.png", "images/14.png", "images/15.png",
            "images/16.png", "images/17.png", "images/18.png",
            "images/19.png", "images/20a.png", "images/20b.png",
            "images/20c.png", "images/20d.png"
    };

    private static String[] holders
            = {"images/nissei.png", "images/one.png", "images/vanguard.png",
            "images/daiwa.png", "images/season.png", "images/leos.png",
            "images/leos.png", "images/smta.png", "images/nissei.png",
            "images/season.png", "images/kamakura.png", "images/smam.png",
            "images/nomura.png", "images/one.png", "images/ufj.png",
            "images/smta.png", "images/state.png", "images/smta.png",
            "images/smam.png", "images/nissei.png", "images/smta.png",
            "images/leos.png", "images/commons.png"
    };

    private static Point3d[] lVerts = new Point3d[4];
    private static Point3d[] nVerts = new Point3d[4];
    private static Point3d[] sVerts = new Point3d[4];

    private static Point3d[] verts = new Point3d[4];
    private static Point3d[] hVerts = new Point3d[4];
    private static Point3d[] bVerts = new Point3d[4];

    private static TexCoord2f[] fCoords = new TexCoord2f[4];
    private static TexCoord2f[] bCoords = new TexCoord2f[4];

    private static TexCoord2f[] nCoords = new TexCoord2f[4];
    private static TexCoord2f[] sCoords = new TexCoord2f[4];


    private static Shape3D[] shapes = new Shape3D[23];
    private static Shape3D[] fShapes = new Shape3D[24];
    private static Shape3D[] bShapes = new Shape3D[23];

    private static BranchGroup root = new BranchGroup();
    private static BranchGroup rotGroup = null;

    static {
        lVerts[0] = new Point3d(-1.0, -0.1, 0.0);
        lVerts[1] = new Point3d(-0.8, -0.1, 0.0);
        lVerts[2] = new Point3d(-0.8, 0.1, 0.0);
        lVerts[3] = new Point3d(-1.0, 0.1, 0.0);

        nVerts[0] = new Point3d(0.0, 0.0, 0.0);
        nVerts[0] = new Point3d(0.1, 0.0, 0.0);
        nVerts[0] = new Point3d(0.1, 0.1, 0.0);
        nVerts[0] = new Point3d(0.0, 0.1, 0.0);

        sVerts[0] = new Point3d(0.0, -0.1, 0.0);
        sVerts[0] = new Point3d(0.1, -0.1, 0.0);
        sVerts[0] = new Point3d(0.1, 0.0, 0.0);
        sVerts[0] = new Point3d(0.0, 0.0, 0.0);

        verts[0] = new Point3d(-0.8, 0.0, 0.0);
        verts[1] = new Point3d(0.7, 0.0, 0.0);
        verts[2] = new Point3d(0.7, 0.1, 0.0);
        verts[3] = new Point3d(-0.8, 0.1, 0.0);

        hVerts[0] = new Point3d(-0.8, 0.0, 0.01);
        hVerts[1] = new Point3d(0.7, 0.0, 0.01);
        hVerts[2] = new Point3d(0.7, 0.1, 0.01);
        hVerts[3] = new Point3d(-0.8, 0.1, 0.01);

        bVerts[0] = new Point3d(0.7, 0.0, -0.01);
        bVerts[1] = new Point3d(-0.8, 0.0, -0.01);
        bVerts[2] = new Point3d(-0.8, 0.1, -0.01);
        bVerts[3] = new Point3d(0.7, 0.1, -0.01);

        fCoords[0] = new TexCoord2f(0.0f, 0.0f);
        fCoords[1] = new TexCoord2f(1.0f, 0.0f);
        fCoords[2] = new TexCoord2f(1.0f, 1.0f);
        fCoords[3] = new TexCoord2f(0.0f, 1.0f);

        bCoords[0] = new TexCoord2f(1.0f, 1.0f);
        bCoords[1] = new TexCoord2f(0.0f, 1.0f);
        bCoords[2] = new TexCoord2f(0.0f, 0.0f);
        bCoords[3] = new TexCoord2f(1.0f, 0.0f);

        nCoords[0] = new TexCoord2f(0.0f, 0.5f);
        nCoords[1] = new TexCoord2f(1.0f, 0.5f);
        nCoords[2] = new TexCoord2f(1.0f, 1.0f);
        nCoords[3] = new TexCoord2f(0.0f, 1.0f);

        sCoords[0] = new TexCoord2f(0.0f, 0.0f);
        sCoords[1] = new TexCoord2f(1.0f, 0.0f);
        sCoords[2] = new TexCoord2f(1.0f, 0.5f);
        sCoords[3] = new TexCoord2f(0.0f, 0.5f);

        for (int i=0; i<23; i++) {
            shapes[i] = createShape(titles[i], verts, fCoords);
            bShapes[i] = createShape(holders[i], bVerts, bCoords);
            fShapes[i] = createShape(titles[i], hVerts, fCoords);
        }

        fShapes[23] = createShape("images/blank.png", hVerts, fCoords);

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
            txatt.setTextureMode(TextureAttributes.COMBINE);
            txatt.setPerspectiveCorrectionMode(TextureAttributes.NICEST);
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

    private BranchGroup createRotation(int index) throws Exception {
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

        // lank
        trans.addChild(createShape(lanks[index], lVerts, fCoords));

        // rotation
        Transform3D axis = new Transform3D();
        axis.rotZ( -(Math.PI / 2.0) );

        int count = 23;
        long dur = 450L;
        Alpha alpha = new Alpha(count, dur);
        alpha.setStartTime(System.currentTimeMillis() + 400L);

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
        for (int i=0; i<23; i++) { posList.add(i); }
        posList.remove(index);
        Collections.shuffle(posList);

        for (int i=0; i<22; i++) {
            Shape3D top = shapes[posList.get(i)];
            Shape3D f;
            if (i == 0) {
                log.severe("i="+i+", posList.get("+i+")="+posList.get(i));
                f = fShapes[23];
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

        Alpha a2 = new Alpha(1, count * dur);
        Alpha a3 = new Alpha(1, count * dur);
        a2.setStartTime(alpha.getStartTime());
        a3.setStartTime(alpha.getStartTime());

        SwitchValueInterpolator si = new SwitchValueInterpolator(a2, sw);
        SwitchValueInterpolator rsi = new SwitchValueInterpolator(a3, rSw);
        si.setSchedulingBounds(new BoundingSphere(new Point3d(), 100.0));
        rsi.setSchedulingBounds(new BoundingSphere(new Point3d(), 100.0));
        si.setLastChildIndex(22);
        rsi.setLastChildIndex(22);

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
                        k = 9;
                        break;
                    case KeyEvent.VK_1:
                        k = 0;
                        break;
                    case KeyEvent.VK_2:
                        k = 1;
                        break;
                    case KeyEvent.VK_3:
                        k = 2;
                        break;
                    case KeyEvent.VK_4:
                        k = 3;
                        break;
                    case KeyEvent.VK_5:
                        k = 4;
                        break;
                    case KeyEvent.VK_6:
                        k = 5;
                        break;
                    case KeyEvent.VK_7:
                        k = 6;
                        break;
                    case KeyEvent.VK_8:
                        k = 7;
                        break;
                    case KeyEvent.VK_9:
                        k = 8;
                        break;
                    default:
                        return;
                }
                if (rotGroup != null && rotGroup.isLive()) {
                    log.severe("keyPress: rotGroup is live.");
                    root.removeChild(rotGroup);
                }
                try {
                    rotGroup = createRotation(k);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                root.addChild(rotGroup);

                try {
                    String file = musics[0];
                    if (k == 1) file = musics[1];
                    URL url = this.getClass().getClassLoader().getResource(file);

                    AudioInputStream in = AudioSystem
                            .getAudioInputStream(url);
                    AudioFormat format = in.getFormat();
                    DataLine.Info info = new DataLine.Info(Clip.class, format);
                    Clip clip = (Clip)AudioSystem.getLine(info);
                    clip.open(in);
                    clip.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

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

        universe.getViewer().getJFrame(0).setSize(720, 540);

    }

    public static void main(String[] args) {
        new Hello();
    }
}
