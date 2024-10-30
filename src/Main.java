
public class Main {
    public static void main(String[] args) {

        Audio audio = new Audio();
        audio.load(args[0]);

        AudioFootprinting audioFootprint = new AudioFootprinting(audio);
        audioFootprint.printData(audio.data.Samples.length);
    }
}