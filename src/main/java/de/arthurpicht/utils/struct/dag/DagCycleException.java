package de.arthurpicht.utils.struct.dag;

import de.arthurpicht.utils.core.strings.Strings;

import java.util.List;

public class DagCycleException extends Exception {

    private final List<String> cycleNodeList;

    public DagCycleException(List<String> cycleNodeList) {
        super("Dag has at least one cycle: "
                + Strings.listing(cycleNodeList, ", ", "{", "}", "[", "]"));
        this.cycleNodeList = cycleNodeList;
    }

    public List<String> getCycleNodeList() {
        return this.cycleNodeList;
    }

}
