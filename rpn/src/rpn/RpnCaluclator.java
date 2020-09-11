package rpn;

import java.util.*;

public class RpnCaluclator {

    // Commands
    private static final String QUIT = "quit";


    private static Stack<Double> operationStack = new Stack<Double>();
    private static Map<String,Operator> operatorMap;


    public static void main(String[] args) {
        operatorMap= createOperatorMap();
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your Expression::");
        while(scan.hasNextLine()) {
            String s = scan.nextLine();
            evaluate(s);
            System.out.print("% ");
        }
    }

    public static  Map createOperatorMap() {
        Map<String, Operator> map = new HashMap<String, Operator>();
        map.put("+", new PlusOperator());
        map.put("-", new MinusOperator());
        map.put("*", new MultiplyOperator());
        map.put("/", new DivisionOperator());
        return map;
    }


    public static void evaluate(String s) {
        if(s.equals(QUIT)) {
            System.exit(0);
        }else if("Q".equals(s) || "q".equals(s)){
            System.exit(0);
        }
        else {
            calculate(s);
        }
    }
    public static void calculate(String s) {
        ArrayList<String> input = new ArrayList<String>();
        Collections.addAll(input, s.trim().split(" "));
        input.removeAll(Arrays.asList(null, ""));
        if(input.size() == 1) {
            double d = getValue(s);
            if(!Double.isNaN(d)) System.out.println("\t" + d);
            return;
        }
        for(int i = 0; i < input.size(); i++) {
            String n = input.get(i);
            if(isOperator(n)) {
                if(operationStack.size() > 1) {
                    operationStack.push(doOperation(n));
                } else {
                    System.out.println("\tOperation not valid!");
                    return;
                }
            } else {
                double d = getValue(n);
                if(!Double.isNaN(d)) {
                    operationStack.push(d);
                } else {
                    operationStack.clear();
                    return;
                }
            }
        }
        double result = operationStack.pop();
        if(operationStack.size() > 0) {
            System.out.println("\tOperation not valid!");
            operationStack.clear();
            return;
        }


        System.out.println("\t" + result);
    }

    public static double doOperation(String s) {
        char op = s.charAt(0);
        double a = operationStack.pop();
        double b = operationStack.pop();

        Operator operator = operatorMap.get(Character.toString(op));
        if (operator == null)
            throw new IllegalArgumentException("Unknown operator");
        return  operator.compute(b,a);
    }

    public static double getValue(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException ex) {
                System.out.printf("\t%s not found.\n", s);
                return Double.NaN;
            }
    }

    public static boolean isOperator(String s) {
        char c = s.charAt(0);
        return c == '+' || c == '-' || c == '/' || c == '*';
    }
}