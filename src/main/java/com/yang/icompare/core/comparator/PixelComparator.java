/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yang.icompare.core.comparator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.yang.icompare.core.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PixelComparator implements Comparator {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Result compare(File first, File second, final Result previousResult) {
        Boolean isIdentical = true;
        try {
            isIdentical = compare(first, second);
        } catch (Exception e) {
            logger.error("Exception happened when compare two image", e);
        }
        return new PixelDiffResult(isIdentical, "");
    }

    private boolean compare(final File condidatefile,
                            final File compareToFile)
            throws IOException {
        BufferedImage first = ImageIO.read(condidatefile);
        BufferedImage second = ImageIO.read(compareToFile);
        return getResultBySomeArgIdontCare(first, second, first.getWidth(null), second.getWidth(null), first.getHeight(null), second.getHeight(null));
    }

    private boolean getResultBySomeArgIdontCare(BufferedImage first, BufferedImage second, int width1, int width2, int height1, int height2) {
        boolean theSame = true;
        // basic impl of https://en.wikipedia.org/wiki/Scale-invariant_feature_transform
        if ((width1 == width2) && (height1 == height2)) {
            for (int y = 0; y < height1; y++) {
                for (int x = 0; x < width1; x++) {
                    int rgb1 = first.getRGB(x, y);
                    int rgb2 = second.getRGB(x, y);
                    int r1 = (rgb1 >> 16) & 0xff;
                    int g1 = (rgb1 >> 8) & 0xff;
                    int b1 = rgb1 & 0xff;
                    int r2 = (rgb2 >> 16) & 0xff;
                    int g2 = (rgb2 >> 8) & 0xff;
                    int b2 = rgb2 & 0xff;
                    int diff = Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
                    if (diff > 0) {
                        theSame = false;
                        break;
                    }
                }
            }
        } else {
            theSame = false;
        }
        return theSame;
    }
}
