package com.yang.icompare;

public class ImgComparePluginExtension {
    //TDOO : add default path
    private String firstCompareCandidateDir = "defaultPath";
    private String secondCompareCandidateDir = "defaultPath";
    private String outPutDir = "defaultPath";
    private String pathOfDataJs = "defaultPath";

    public String getFirstCompareCandidateDir() {
        return firstCompareCandidateDir;
    }

    public void setFirstCompareCandidateDir(String firstCompareCandidateDir) {
        this.firstCompareCandidateDir = firstCompareCandidateDir;
    }

    public String getSecondCompareCandidateDir() {
        return secondCompareCandidateDir;
    }

    public void setSecondCompareCandidateDir(String secondCompareCandidateDir) {
        this.secondCompareCandidateDir = secondCompareCandidateDir;
    }

    public String getOutPutDir() {
        return outPutDir;
    }

    public void setOutPutDir(String outPutDir) {
        this.outPutDir = outPutDir;
    }

    public String getPathOfDataJs() {
        return pathOfDataJs;
    }

    public void setPathOfDataJs(String pathOfDataJs) {
        this.pathOfDataJs = pathOfDataJs;
    }
}
