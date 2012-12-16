package curta;

import curta.expression.Expression;
import curta.function.Function;

import java.util.Map;

public class Demo {

    public static void customFunction() throws ParseException {

        Curta curta = new Curta();

        curta.addFunction(new Function("isPrime") {

            @Override
            public Object eval(Object... params) {

                // number of parameters must be exactly 1 (min=1, max=1)
                super.checkNumberOfParams(1, 1, params);

                // expecting an integer value (not floating-point!)
                long value = super.getLong(0, params);

                // return whether this number is prime
                return new java.math.BigInteger(String.valueOf(value)).isProbablePrime(20);
            }
        });

        String expression = "isPrime(11)";

        System.out.printf("%s = %s\n", expression, curta.eval(expression));
    }

    public static void changeExpression() throws ParseException {

        Curta curta = new Curta();

        String expression = "2^10";

        System.out.printf("%s = %s\n", expression, curta.eval(expression));

        curta.setExpression(Operator.BitXOr, new Expression() {
            @Override
            public Object eval(CurtaNode ast, Map<String, Object> vars, Map<String, Function> functions, Map<Integer, Expression> expressions) {

                Double lhs = super.evalChildAsDouble(0, ast, vars, functions, expressions);
                Double rhs = super.evalChildAsDouble(1, ast, vars, functions, expressions);

                return Math.pow(lhs, rhs);
            }
        });

        System.out.printf("%s = %s\n", expression, curta.eval(expression));
    }

    public static void changeAssignExpression() throws ParseException {

        Curta curta = new Curta();

        String expression = "PI = 3; PI";

        System.out.printf("%s = %s\n", expression, curta.eval(expression));

        curta.clear();

        curta.setExpression(Operator.Assign, new Expression() {
            @Override
            public Object eval(CurtaNode ast, Map<String, Object> vars, Map<String, Function> functions, Map<Integer, Expression> expressions) {

                String id = super.getTextFromChild(0, ast);
                Object value = super.evalChild(1, ast, vars, functions, expressions);

                if(id.matches("[A-Z_]+") && vars.containsKey(id)) {
                    System.err.println("cannot reassign: " + id);
                }
                else {
                    vars.put(id, value);
                }

                return null;
            }
        });

        System.out.printf("%s = %s\n", expression, curta.eval(expression));

        expression = "PI = 3; NEW_VAR = 42; NEW_VAR = -1; NEW_VAR";

        System.out.printf("%s = %s\n", expression, curta.eval(expression));
    }

    public static void main(String[] args) throws ParseException {
        //changeExpression();
        //customFunction();
        //changeAssignExpression();

        Curta curta = new Curta();

        String expression = "~42";

        System.out.printf("%s = %s\n", expression, curta.eval(expression));

        System.out.printf("%s\n", ~42);
    }
}