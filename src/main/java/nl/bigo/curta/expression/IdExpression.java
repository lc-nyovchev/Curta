package nl.bigo.curta.expression;

import nl.bigo.curta.CurtaNode;
import nl.bigo.curta.function.Function;

import java.util.Map;
import static nl.bigo.curta.Curta.OPTION_DEFAULT_FAIL_ON_UKNOWN_VARIABLE;
import static nl.bigo.curta.Curta.OPTION_FAIL_ON_UNKNOWN_VARIABLE;

public class IdExpression extends Expression {
	
    @Override
    public Object eval(CurtaNode ast, Map<String, Object> vars, Map<String, Function> functions, Map<Integer, Expression> expressions) {

        String id = (String)ast.jjtGetValue();

        if(!vars.containsKey(id)) {
			if ( !failOnUnknownProp(ast) ){
				return null;
			}
            throw new RuntimeException("Unknown variable: " + id);
        } 

        return vars.get(id);
    }
	
	private boolean failOnUnknownProp(CurtaNode ast){
		Object fail = ast.getRoot().getOption(OPTION_FAIL_ON_UNKNOWN_VARIABLE);
		if ( fail == null ){
			return OPTION_DEFAULT_FAIL_ON_UKNOWN_VARIABLE;
		}
		try {
			return (Boolean) fail;
		} catch (ClassCastException ex){
			return OPTION_DEFAULT_FAIL_ON_UKNOWN_VARIABLE;
		}
	}
}