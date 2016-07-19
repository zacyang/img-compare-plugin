package com.yang.icompare.core.comparator;

import com.yang.icompare.core.Result;

public class PixelDiffResult implements Result {

    private boolean isIdentical;
    private String diffImgLocation;

    public PixelDiffResult(boolean isIdentical, String diffImgLocation) {
        this.isIdentical = isIdentical;
        this.diffImgLocation = diffImgLocation;
    }

    public boolean isIdentical() {
        return this.isIdentical;
    }

    public String getDiffImgLocation() {
        return this.diffImgLocation;
    }
}
