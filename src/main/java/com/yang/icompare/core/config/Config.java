package com.yang.icompare.core.config;

public class Config {

    private String firstCompareCandidateDir;
    private String secondCompareCandidateDir;
    private String outPutDir;
    private String pathOfDataJs;


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
