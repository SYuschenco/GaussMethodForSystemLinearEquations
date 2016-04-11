package ua.gostart.goit.gauss;
/**
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 */
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * A sample that demonstrates the use of two different constructors in the Image
 * class.
 *
 * @related graphics/images/ImageProperties
 * @see javafx.scene.image.Image
 * @see javafx.scene.image.ImageView
 * @resource icon-48x48.png
 */
public class InterfaceIO extends Application {
    private static final Image ICON_48 = new Image(InterfaceIO.class.getResourceAsStream("D://JProjects/jv0h.jpg"));
    private void init(Stage primaryStage) {
        Group root = new Group();
        primaryStage.setScene(new Scene(root));
        // load and display a image resource from classpath
        ImageView sample1 = new ImageView(ICON_48);
        // load and display a image resource from url
        ImageView sample2 = new ImageView(
                new Image("http://java.com/images/jv0h.jpg",400,100,true, true));
        //show
        VBox hb = new VBox(10);
        hb.getChildren().addAll(sample1,sample2);
        root.getChildren().add(hb);
    }

    @Override public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
    }
    public static void main(String[] args) { launch(args); }
}