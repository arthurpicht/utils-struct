package de.arthurpicht.utils.struct.dag;

import de.arthurpicht.utils.core.assertion.AssertMethodPrecondition;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static <N> List<N> sublist(List<N> list, int index) {
        AssertMethodPrecondition.parameterNotNull("list", list);
        if (index < 0) throw new IllegalArgumentException("Index out of bounds: index < 0.");

        int lastIndex = list.size() - 1;
        if (lastIndex < index) throw new IllegalArgumentException("Index out of bounds: index is greater than index of last element.");

        List<N> subList = new ArrayList<>();
        for (int i = index; i <= lastIndex; i++) {
            subList.add(list.get(i));
        }
        return subList;
    }

}
