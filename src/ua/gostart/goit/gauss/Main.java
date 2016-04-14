package ua.gostart.goit.gauss;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main {

    static int DEFAULT_EQUATIONS_NUMBER = 3;
    static int DEFAULT_VARIABLES_NUMBER = DEFAULT_EQUATIONS_NUMBER;
    static String methodForGeneratingSystemCoefficients = "Input";
    //static String methodForGeneratingSystemCoefficients = "Random";
    static LinearSystem<Float, MyEquation> list;

    public static void main(String args[]) {

        //= generateSystem(methodForGeneratingSystemCoefficients);
//            printSystem(list);
        if (methodForGeneratingSystemCoefficients.equals("Input")) {
            list = generateSystem(methodForGeneratingSystemCoefficients);
            System.out.println("Application run in \"" + methodForGeneratingSystemCoefficients + "\" mode");
        }if (methodForGeneratingSystemCoefficients.equals("Random")) {
            list = generateSystem(methodForGeneratingSystemCoefficients);
            System.out.println("Application run in \"" + methodForGeneratingSystemCoefficients + "\" mode");
        }

        printSystem(list);

        int i, j;
        Algorithm<Float, MyEquation> alg = new Algorithm<Float, MyEquation>(list);
        try {
            alg.calculate();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        Float[] x = new Float[DEFAULT_EQUATIONS_NUMBER];
        for (i = list.size() - 1; i >= 0; i--) {
            Float sum = 0.0f;
            for (j = list.size() - 1; j > i; j--) {
                sum += list.itemAt(i, j) * x[j];
            }
            x[i] = (list.itemAt(i, list.size()) - sum) / list.itemAt(i, j);
        }
        printSystem(list);
        printVector(x);
    }

    public static LinearSystem<Float, MyEquation> generateSystem(String DEFAULT_GENERETED_SYSTEM) {
        LinearSystem<Float, MyEquation> list = new LinearSystem<Float, MyEquation>();
        for (int i = 0; i < DEFAULT_EQUATIONS_NUMBER; i++) {
            MyEquation eq = new MyEquation();
            eq.generate(DEFAULT_VARIABLES_NUMBER + 1, DEFAULT_GENERETED_SYSTEM);
            list.push(eq);
        }
        return list;
    }

    public static void printSystem(LinearSystem<Float, MyEquation> system) {
        for (int i = 0; i < system.size(); i++) {
            MyEquation temp = system.get(i);
            String s = "";
            for (int j = 0; j < temp.size(); j++) {
                s += String.format("%f; %s", system.itemAt(i, j), "\t");
            }
            System.out.println(s);
        }
        System.out.println("");
    }

    public static void printSystemInScene(LinearSystem<Float, MyEquation> system) {
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
        for (int i = 0; i < system.size(); i++) {
            MyEquation temp = system.get(i);
            //scenetitle2.setText(String.valueOf(system.itemAt(i));
            for (int j = 0; j < temp.size(); j++) {

                scenetitle2.setText(String.valueOf(system.itemAt(i, j)));

                //+= String.format("%f; %s", system.itemAt(i, j), "\t");
            }
            System.out.println(scenetitle2);
        }
        System.out.println("");
        grid.add(scenetitle2, 0, 10, 2, 1);
        primaryStage.setScene(scene);
    }

    public static void printVector(Float[] x) {
        String s = "";
        for (int i = 0; i < x.length; i++) {
            s += String.format("x%d = %f; ", i, x[i]);
        }
        System.out.println(s);
    }
}
