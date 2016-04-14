package ua.gostart.goit.gauss;

import java.util.*;

public class MyEquation implements Gauss<Float, MyEquation> {

    private List<Float> equation = new ArrayList<Float>();

    public List<Float> getEquation(){
        return equation;
    }

    public void generate(int size, String methodForGeneratingSystemCoefficients){
        if (size < 2) size = 2;
        this.equation.clear();
        if (methodForGeneratingSystemCoefficients.equals("Random")) {
            for (int i = 0; i < size; i++){
            Random random = new Random();
            this.equation.add((float) (random.nextInt()%10) + 1);
        }

        } else{
            System.out.println("\nSystem of linear equations size is " + (size-1) +
                    " input in console matrixs coefficients (one line for one coefficient )");
            for (int i = 0; i < size; i++){
                Scanner scanner = new Scanner(System.in);
                String value = scanner.nextLine();
                this.equation.add(Float.parseFloat(value));
            }
        }
    }

    @Override
    public int size(){
        return equation.size();
    }
    @Override
    public void addEquation(MyEquation item){
        ListIterator<Float> i = equation.listIterator();
        ListIterator<Float> j = item.getEquation().listIterator();
        for(; i.hasNext() && j.hasNext();){
            Float a = i.next();
            Float b = j.next();
            i.set(a + b);
        }
    }
    @Override
    public void mul(Float coefficient){
        for(ListIterator<Float> i = equation.listIterator(); i.hasNext();){
            Float next = i.next();
            i.set(next * coefficient);
        }
    }
    @Override
    public Float findCoefficient(Float a, Float b){
        if (a == 0.0f) return 1.0f;
        return -b/a;
    }
    @Override
    public Float at(int index){
        return equation.get(index);
    }
}