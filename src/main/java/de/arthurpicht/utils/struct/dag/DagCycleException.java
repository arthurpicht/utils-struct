package de.arthurpicht.utils.struct.dag;

import de.arthurpicht.utils.core.strings.Strings;

import java.util.ArrayList;
import java.util.List;

public class DagCycleException extends Exception {

    private final List<String> cycleNodeList;

    public static DagCycleException createInstanceForMessageOnly(String message) {
        return new DagCycleException(message);
    }

    public DagCycleException(List<String> cycleNodeList) {
        super("Dag has at least one cycle: "
                + Strings.listing(cycleNodeList, ", ", "{", "}", "[", "]"));
        this.cycleNodeList = cycleNodeList;
    }

    private DagCycleException(String message) {
        super(message);
        this.cycleNodeList = new ArrayList<>();
    }

    public List<String> getCycleNodeList() {
        return this.cycleNodeList;
    }

}
