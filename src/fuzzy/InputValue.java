package fuzzy;

/**
 * 1つの入力値を表す。
 */
public class InputValue {
    private double value;
    private String description;
    public InputValue(double value){
        setValue(value);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
