package me.rainma22.serversentevents;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class SSEChunk {

    private String id = null;
    private String event = null;
    private StringJoiner data = new StringJoiner("\n");
    private Integer retry = 0;
    private List<Exception> warnings = new ArrayList<>(3);

    private SSEChunk() {
    }

    public static SSEChunk fromString(String s) {
        var lines = s.split("\n");
        var res = new SSEChunk();
        for (String line : lines) {
            int splitIdx = line.indexOf(": ");
            if (splitIdx == -1) {
                res.warnings.add(new IllegalArgumentException("Bad line: " + line
                        + "!\n No ': ' split found"));
                continue; // skip bad line
            }
            var type = line.substring(0, splitIdx);
            if (type.isEmpty()) {
                res.warnings.add(new IllegalArgumentException("Bad line: " + line
                        + "!\n Unexpected comment/keep-alive line"));
                continue; // just a comment/keepalive
            }
            var data = line.substring(splitIdx + 2, line.length());
            switch (type) {
                case "id":
                    res.id = data;
                    break;
                case "event":
                    res.event = data;
                    break;
                case "data":
                    res.data.add(data);
                    break;
                case "retry":
                    try {
                        res.retry = Integer.parseInt(data);
                    } catch (NumberFormatException e) {
                        res.warnings.add(e);
                    }
                    break;
                default:
                    break;
            }
        }
        return res;
    }

    public String getData() {
        return data.toString();
    }

    public String getId() {
        return id;
    }

    public String getEvent() {
        return event;
    }

    public Integer getRetry() {
        return retry;
    }

    public List<Exception> getWarnings() {
        return warnings;
    }

}
