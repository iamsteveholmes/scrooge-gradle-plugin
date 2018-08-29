package se.rosenhorn.scrooge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScroogePluginExtention {
    List<String> opts;
    String sourceDir;
    String targetDir;

    public ScroogePluginExtention() {
        this.opts = new ArrayList<String>(Arrays.asList("--finagle"));
        this.sourceDir = "src/main/thrift/";
        this.targetDir = "target/scala/";
    }

    public List<String> getOpts() {
        return opts;
    }

    public void setOpts(List<String> opts) {
        this.opts = opts;
    }

    public String getSourceDir() {
        return sourceDir;
    }

    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }

    public String getTargetDir() {
        return targetDir;
    }

    public void setTargetDir(String targetDir) {
        this.targetDir = targetDir;
    }
}
