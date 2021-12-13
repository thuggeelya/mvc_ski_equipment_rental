package org.example.app.comparators;

import org.example.web.dto.Equipment;

import java.util.Comparator;

public class Comparators {

    public static final Comparator<Equipment> equipmentCostComparator = new Comparator<Equipment>() {
        @Override
        public int compare(Equipment o1, Equipment o2) {
            if (o1 == null || o2 == null) {
                return 0;
            }
            if (Integer.parseInt(o1.getCost()) == Integer.parseInt(o2.getCost())) {
                return 0;
            }
            return Integer.parseInt(o1.getCost()) > Integer.parseInt(o2.getCost()) ? 1 : -1;
        }
    };
}