package curta.function;

import curta.Function;

import java.util.Collections;
import java.util.List;

public class Max extends Function {

    public Max() {
        super("max");
    }

    @Override
    public Object eval(Object... params) {

        super.checkNumberOfParams(2, 2, params);

        return Math.max(super.getNumber(0, params), super.getNumber(1, params));
    }
}
