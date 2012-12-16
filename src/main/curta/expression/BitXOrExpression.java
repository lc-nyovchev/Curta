package curta.expression;

import curta.CurtaNode;
import curta.function.Function;

import java.util.Map;

public class BitXOrExpression extends Expression {

    @Override
    public Object eval(CurtaNode ast, Map<String, Object> vars, Map<String, Function> functions, Map<Integer, Expression> expressions) {

        Long lhs = super.evalChildAsLong(0, ast, vars, functions, expressions);
        Long rhs = super.evalChildAsLong(1, ast, vars, functions, expressions);

        return lhs ^ rhs;
    }
}