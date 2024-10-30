import java.util.Arrays;

public class FastFourier {

    // A helper class to represent complex numbers using int
    static class Complex {
        int real;
        int imag;

        Complex(int real, int imag) {
            this.real = real;
            this.imag = imag;
        }

        Complex add(Complex other) {
            return new Complex((int)(this.real + other.real), (int)(this.imag + other.imag));
        }

        Complex subtract(Complex other) {
            return new Complex((int)(this.real - other.real), (int)(this.imag - other.imag));
        }

        Complex multiply(Complex other) {
            int realPart = (int)(this.real * other.real - this.imag * other.imag);
            int imagPart = (int)(this.real * other.imag + this.imag * other.real);
            return new Complex(realPart, imagPart);
        }
    }

    public static void fastFourierTransform(Complex[] inputData) {
        int dataSize = inputData.length;
        if (dataSize == 1) {
            return;
        }

        Complex[] evenData = extractEvenElements(inputData);
        Complex[] oddData = extractOddElements(inputData);

        fastFourierTransform(evenData);
        fastFourierTransform(oddData);

        combineEvenAndOddData(inputData, evenData, oddData);
    }

    private static Complex[] extractEvenElements(Complex[] inputData) {
        int halfSize = inputData.length / 2;
        Complex[] evenData = new Complex[halfSize];

        for (int i = 0; i < halfSize; i++) {
            evenData[i] = inputData[2 * i];
        }

        return evenData;
    }

    private static Complex[] extractOddElements(Complex[] inputData) {
        int halfSize = inputData.length / 2;
        Complex[] oddData = new Complex[halfSize];

        for (int i = 0; i < halfSize; i++) {
            oddData[i] = inputData[2 * i + 1];
        }

        return oddData;
    }

    private static void combineEvenAndOddData(Complex[] inputData, Complex[] evenData, Complex[] oddData) {
        int halfSize = inputData.length / 2;

        for (int i = 0; i < halfSize; i++) {
            double angle = -2 * Math.PI * i / inputData.length;
            int realPart = (int)(Math.cos(angle) * oddData[i].real - Math.sin(angle) * oddData[i].imag);
            int imagPart = (int)(Math.sin(angle) * oddData[i].real + Math.cos(angle) * oddData[i].imag);

            Complex exp = new Complex(realPart, imagPart);

            inputData[i] = evenData[i].add(exp);
            inputData[i + halfSize] = evenData[i].subtract(exp);
        }
    }

    // Manually extract real parts from complex data
    public static int[] realPart(Complex[] complexData) {
        int[] realParts = new int[complexData.length];
        for (int i = 0; i < complexData.length; i++) {
            realParts[i] = complexData[i].real;
        }
        return realParts;
    }

    // Manually extract imaginary parts from complex data
    public static int[] imaginaryPart(Complex[] complexData) {
        int[] imagParts = new int[complexData.length];
        for (int i = 0; i < complexData.length; i++) {
            imagParts[i] = complexData[i].imag;
        }
        return imagParts;
    }


}
