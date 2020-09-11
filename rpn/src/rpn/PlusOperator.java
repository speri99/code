package rpn;

public class PlusOperator implements  Operator{
    @Override
    public Double compute(Double... values) {
        return values[0] + values[1];
    }
}
