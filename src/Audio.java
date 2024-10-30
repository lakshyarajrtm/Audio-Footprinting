
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Audio {

    int ckId;
    int ckSize;
    Head head;
    Data data;

    final int head_offset = 44;

    public void load(String path){

        try{

            byte[] d = Files.readAllBytes(Paths.get(path));

            head = new Head();

            ckId = getInt(d, 0);
            ckSize = getInt(d, 4);

            head.waveId = getInt(d, 8);
            head.ckId = getInt(d, 12);
            head.ckSize = getInt(d, 16);
            head.wFormatTags = getShort(d, 20);
            head.nChannels = getShort(d, 22);
            head.nSamplesPerSec = getInt(d, 24);
            head.nAvgBytesPerSec = getInt(d, 28);
            head.nBlockAlign = getShort(d, 32);
            head.wBitsPerSample = getShort(d, 34);

            int chunk_size = getInt(d, 40);
            data = new Data(chunk_size);
            data.ckId = getInt(d, 36);
            data.ckSize = chunk_size;

            for (int i = 0, j=0; i < chunk_size; i+=2, j++){
                // taking a short because of 2 channels in each sample
                data.Samples[j] = getShort(d, i+head_offset);
            }

        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    public void printHead(){
        System.out.println("ckId : "+ckId);
        System.out.println("ckSize : "+ckSize);
        System.out.println("Wave ID : "+head.waveId);
        System.out.println("head chunk ckId : "+head.ckId);
        System.out.println("head chunk ckSize : "+head.ckSize);
        System.out.println("wFormatTags : "+head.wFormatTags);
        System.out.println("nChannels : "+head.nChannels);
        System.out.println("nSamplePerSec : "+head.nSamplesPerSec);
        System.out.println("nAvgBytesPerSec : "+head.nAvgBytesPerSec);
        System.out.println("nBlockAlign : "+head.nBlockAlign);
        System.out.println("wBitsPerSample : "+head.wBitsPerSample);

    }
    public void printData(int l){
        System.out.println("Data ckId : "+data.ckId +" Data ckSize : "+ data.ckSize);
        for(int i = 0; i < l; i++){
            if(i % 8 == 0 && i > 0){
                System.out.println();
            }
            System.out.print(data.Samples[i] + " ");
        }
    }

    int getInt(byte[] data, int index){
        int x;
        int ans = 0;
        for(int i=0; i < 4; i++){

                x = data[index + i];
                x = x << i*8;
                ans+=x;
        }
        return ans;
    }
    short getShort(byte[] data, int index){

        short x;
        short ans = 0;
        for(int i=0; i < 2; i++){

            x = data[index + i];
            x = (short) (x << i*8);
            ans+=x;

        }

        return ans;
    }


    static class Head {

        int waveId;                              // 4 bytes
        int ckId;                               // 4 bytes
        int ckSize;                              // 4 bytes
        short wFormatTags;                      // 2 bytes
        short nChannels;                        // 2 bytes
        int nSamplesPerSec;                     // 4 bytes
        int nAvgBytesPerSec;                    // 4 bytes
        short nBlockAlign;                        // 2 bytes
        short wBitsPerSample;                     // 2 bytes

    }

    static class Data{
        int ckId;                                   // 4 bytes
        int ckSize;                                 // 4 bytes
        short[] Samples;                            // M*Nc*Ns

        public Data(int size)   {
            Samples = new short[size];
        }
    }
}

