package org.byteam.superadapter.demo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Generate random data.
 * </p>
 * Created by Chen on 2016/5/23.
 */

public final class DataUtils {
    public static String[] names = {"John", "Michelle", "Amy", "Kim", "Mary", "David", "Sunny",
            "James", "Maria", "Betty", "Brian", "Candy", "Charles", "Vicky", "James"};

    public static List<MockModel> generateData() {
        List<MockModel> models = new ArrayList<>();
        for (int i = 0; i < getRandom(5, 15); i++) {
            models.add(new MockModel(names[getRandom(0, names.length - 1)], getRandom(1, 30)));
        }
        return models;
    }

    private static int getRandom(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }
}
