package ua.gostart.goit.gauss;


import com.sun.deploy.util.SyncFileAccess;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application{

    static int DEFAULT_EQUATIONS_NUMBER = 0;
    static int DEFAULT_VARIABLES_NUMBER = 0;

    public static void main(String args[]){
        launch(args);
        LinearSystem<Float, MyEquation> list = generateSystem();
        printSystem(list);
        //printSystemInScene(list);
        int i, j;
        Algorithm<Float, MyEquation> alg = new Algorithm<Float, MyEquation>(list);
        try{
            alg.calculate();
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }catch (ArithmeticException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
        Float [] x = new Float[DEFAULT_EQUATIONS_NUMBER];
        for(i = list.size() - 1; i >= 0; i--) {
            Float sum = 0.0f;
            for(j = list.size() - 1; j > i; j--) {
                sum += list.itemAt(i, j) * x[j];
            }
            x[i] = (list.itemAt(i, list.size()) - sum) / list.itemAt(i, j);
        }
        printSystem(list);
        printVector(x);
    }

    public static LinearSystem<Float, MyEquation> generateSystem(){
        LinearSystem<Float, MyEquation> list = new LinearSystem<Float, MyEquation>();
        for (int i = 0; i < DEFAULT_EQUATIONS_NUMBER; i++){
            MyEquation eq = new MyEquation();
            eq.generate(DEFAULT_VARIABLES_NUMBER + 1);
            list.push(eq);
        }
        return list;
    }

    public static void printSystem(LinearSystem<Float, MyEquation> system){
        for (int i = 0; i < system.size(); i++){
            MyEquation temp = system.get(i);
            String s = "";
            for (int j = 0; j < temp.size(); j++){
                s += String.format("%f; %s", system.itemAt(i, j), "\t");
            }
            System.out.println(s);
        }System.out.println("");
    }

    public static void printSystemInScene(LinearSystem<Float, MyEquation> system){
        Stage primaryStage = new Stage();
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 1300, 975);
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Text scenetitle2 = new Text("");
        scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle2, 0, 5, 2, 1);
        primaryStage.setScene(scene);
        for (int i = 0; i < system.size(); i++){
            MyEquation temp = system.get(i);
            //scenetitle2.setText(String.valueOf(system.itemAt(i));
            for (int j = 0; j < temp.size(); j++){

                scenetitle2.setText(String.valueOf(system.itemAt(i, j)));

                //+= String.format("%f; %s", system.itemAt(i, j), "\t");
            }
            System.out.println(scenetitle2);
        }System.out.println("");
        grid.add(scenetitle2, 0, 10, 2, 1);
        primaryStage.setScene(scene);
    }

    public static void printVector(Float [] x){
        String s = "";
        for (int i = 0; i < x.length; i++){
            s += String.format("x%d = %f; ", i, x[i]);
        }System.out.println(s);
    }

    @Override
    public void start(final Stage primaryStage) {

        primaryStage.setTitle("Application for solving a system of linear equations by Gauss");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid, 1300, 975);
        primaryStage.setScene(scene);
        Text scenetitle = new Text("Welcome to application for solving a system of linear equations by Gauss");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        final Label equationsAndVariablesNumber = new Label("Number of equations and variables:");
        grid.add(equationsAndVariablesNumber, 0, 1);
        final TextField eqAndVarTextField = new TextField();
        grid.add(eqAndVarTextField, 1, 1);
        final String[] text = {eqAndVarTextField.getText()};
        Button btn = new Button("Run with random coefficients");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 3, 1);
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                actiontarget.setFill(Color.FIREBRICK);
                text[0] = eqAndVarTextField.getText();
                int t = Integer.parseInt(text[0]);
                actiontarget.setText(""+t);
                DEFAULT_EQUATIONS_NUMBER = t;
                DEFAULT_VARIABLES_NUMBER = t;
                byRunRandom(primaryStage);
            }

        });
        primaryStage.show();
    }

    private static void byRunRandom(Stage primaryStage) {
        primaryStage.setTitle("Application for solving a system of linear equations by Gauss");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid, 1300, 975);
        primaryStage.setScene(scene);
        Text scenetitle = new Text("Welcome to application for solving a system of linear equations by Gauss");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        final Label equationsAndVariablesNumber = new Label("Number of equations and variables:");
        grid.add(equationsAndVariablesNumber, 0, 1);
        final TextField eqAndVarTextField = new TextField();
        grid.add(eqAndVarTextField, 1, 1);
        final String[] text = {eqAndVarTextField.getText()};
        Button btn = new Button("Run with random coefficients");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 3, 1);
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Text scenetitle1 = new Text("Run Gauss with " + DEFAULT_EQUATIONS_NUMBER + " equations and variables: "
                                     + " (running with random making coefficients) ");
        scenetitle1.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle1, 0, 5, 2, 1);
        primaryStage.setScene(scene);
    }

}
