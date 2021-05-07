package main;

import com.lucene.revlo.*;

public class Main {
    public static void main(String[] args) {
        String[] names = {"No"};
        names = new String[3];

        names[0] = "Sebas";
        names[1] = "Fish";
        names[2] = "Terry";

        for (int i = 0; i < names.length; i++)
            System.out.println(names[i]);

        names = new String[10];

        for (int i = 0; i < names.length; i++)
            System.out.println(names[i]);

    }

}
