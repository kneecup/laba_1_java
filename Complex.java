public class Complex {
    private final double re;
    private final double im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public Complex add(Complex o) {
        return new Complex(re + o.re, im + o.im);
    }

    public Complex sub(Complex o) {
        return new Complex(re - o.re, im - o.im);
    }

    public Complex mul(Complex o) {
        // (a+bi)(c+di) = ac-bd + (ad+bc)i
        return new Complex(re * o.re - im * o.im, re * o.im + im * o.re);
    }

    public Complex div(Complex o) {
        double denom = o.re * o.re + o.im * o.im;
        if (denom == 0) {
            throw new ArithmeticException("деление на ноль");
        }
        return new Complex(
                (re * o.re + im * o.im) / denom,
                (im * o.re - re * o.im) / denom
        );
    }

    public double mod() {
        return Math.sqrt(re * re + im * im);
    }

    @Override
    public String toString() {
        // чуть округляю, а то double гадит
        double r = Math.round(re * 1000.0) / 1000.0;
        double i = Math.round(im * 1000.0) / 1000.0;

        if (i == 0) return "" + r;
        if (r == 0) return i + "i";
        if (i > 0) return r + "+" + i + "i";
        return r + "" + i + "i";
    }
}