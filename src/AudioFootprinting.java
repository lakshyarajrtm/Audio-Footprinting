import java.util.Arrays;
import java.util.Arrays.*;

public class AudioFootprinting {

    FastFourier.Complex[] data;
    int SamplingRate = 0;

    public AudioFootprinting(Audio audio){

        this.SamplingRate = audio.head.nSamplesPerSec;
        data = new FastFourier.Complex[audio.data.Samples.length];
        for(int i = 0; i < audio.data.Samples.length; i++){
            data[i] = new FastFourier.Complex(audio.data.Samples[i], 0);
        }

        FastFourier.fastFourierTransform(data);
    }

    public void sampledPoints(){

       // min = Arrays.stream(data).sorted().max()
    }

    public void printData(int l){

        for(int i = 0; i < l; i++){
            System.out.println(Math.sqrt(Math.pow(data[i].imag,2)+
                    Math.pow(data[i].real,2)) + " " + "at " +
                    (double)(i * SamplingRate)/data.length + " Hz");
        }
    }
}
