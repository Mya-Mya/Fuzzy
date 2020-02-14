package fuzzy;

/**
 * 1つの入力値 x (スカラー)を表す。
 */
public class InputValue {
    private double value;
    private String description;
    public InputValue(double value){
        this("",value);
    }
    public InputValue(String description,double value){
        setDescription(description);
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
