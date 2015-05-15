package nl.bigo.curta;

import java.util.HashMap;
import nl.bigo.curta.expression.Expression;
import nl.bigo.curta.function.Function;
import java.util.Map;

public class CurtaNode extends SimpleNode implements CurtaParserTreeConstants {

    public final int treeType;
	public final Map<String, Object> options;
	
    public CurtaNode(int type) {
        super(type);
        this.treeType = type;
		this.options = new HashMap<>();
    }

    public Object eval(Map<String, Object> vars, Map<String, Function> functions, Map<Integer, Expression> expressions) {
        return CurtaNode.eval(this, vars, functions, expressions);
    }

    public static CurtaNode child(int index, CurtaNode ast) {
        return (CurtaNode)ast.jjtGetChild(index);
    }

    public static Object eval(CurtaNode ast, Map<String, Object> vars, Map<String, Function> functions, Map<Integer, Expression> expressions) {

        final int tokenType = ast.id;

        switch(tokenType) {

            case JJTNUM:
            case JJTBOOL:
			case JJTSTRING:
            case JJTNULL:
                return ast.value;

            default:
                Expression expression = expressions.get(tokenType);

                if(expression == null) {
                    throw new RuntimeException("not implemented: " + Operator.findByType(tokenType));
                }

                return expression.eval(ast, vars, functions, expressions);
        }
	}
	
	public void addOption(String optionName, Object optionValue){
		options.put(optionName, optionValue);
	}
	
	public Object getOption(String optionName){
		return options.get(optionName);
	}
	
	public CurtaNode getRoot(){
		CurtaNode p = (CurtaNode) this.parent;
		if (p.treeType == JJTROOT ){
			return p;
		}
		return p.getRoot();
	}
}
