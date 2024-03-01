package local.htss.apgo.lib;

import java.io.DataInputStream;
import java.io.IOException;

public class ArraySafe {
    public static byte[] readSafeDis(DataInputStream dataInputStream, int step, int total) throws IOException {
        int remaining = total;
        byte[] array = new byte[step];
        while (remaining > 0) {
            if(remaining < step) {
                array = resize(array, remaining);
                dataInputStream.readFully(array, array.length - remaining, remaining);
                remaining = 0;
            } else {
                array = resize(array, step);
                dataInputStream.readFully(array, array.length - step, step);
                remaining -= step;
            }
        }
        return array;
    }
    public static byte[] resize(byte[] original, int delta) {
        byte[] bytes = new byte[original.length + delta];
        System.arraycopy(
                original,
                0,
                bytes,
                0,
                original.length
        );
        return bytes;
    }
}
