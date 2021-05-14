package queries;

import java.util.Arrays;

public interface StringManager {
     static String[] removeElements(String[] arr, String key) {
        // Move all other elements to beginning
        int index = 0;
        for (int i = 0; i < arr.length; i++)
            if (!arr[i].equals(key))
                arr[index++] = arr[i];

        // Create a copy of arr[]
        return Arrays.copyOf(arr, index);
    }

}
