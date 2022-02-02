public class View implements Field
{
    double [] field;
    @Override
    public void setField(double[] field) {
        this.field = field;
    }

    @Override
    public double[] getField() {
        return this.field;
    }
}
