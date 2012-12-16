package curta.function;

public class Sin extends Function {

    public Sin() {
        super("sin");
    }

    @Override
    public Object eval(Object... params) {

        super.checkNumberOfParams(1, 1, params);

        return Math.sin(super.getDouble(0, params));
    }
}
