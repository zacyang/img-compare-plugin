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
package com.yang.icompare.core.generator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

import com.google.common.collect.Lists;
import com.yang.icompare.core.Result;
import com.yang.icompare.core.comparator.PixelDiffResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OverlapDiffGenerator implements DiffGenerator {
    private static final int RGB = 0x99ff0000;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Result report(final File first,
                         final File second,
                         final String outputDir,
                         final Result previousResult) {
        Result result = null;
        List<Point> diffPoints = getAllDiffPoints(first, second);
        BufferedImage imageOne = null;
        try {
            imageOne = ImageIO.read(first);
            for (Point diffPoint : diffPoints) {
                imageOne.setRGB(diffPoint.x, diffPoint.y, RGB);
            }
            File diffFile = new File(outputDir +  first.getName().trim());
            if(!diffFile.exists()){
                diffFile.createNewFile();
            }
            result = new PixelDiffResult(false, diffFile.getAbsolutePath());
            ImageIO.write(imageOne, "png", diffFile);
        } catch (Exception ex) {
            logger.error("Generate diff image failed.", ex);
        }

        return result;
    }

    private List<Point> getAllDiffPoints(File one, File two) {
        BufferedImage img1 = null;
        BufferedImage img2 = null;
        List<Point> points = Lists.newArrayList();
        try {
            img1 = ImageIO.read(one);
            img2 = ImageIO.read(two);

            int width1 = img1.getWidth(null);
            int width2 = img2.getWidth(null);
            int height1 = img1.getHeight(null);
            int height2 = img2.getHeight(null);
            if ((width1 == width2) && (height1 == height2)) {
                for (int y = 0; y < height1; y++) {
                    for (int x = 0; x < width1; x++) {
                        int rgb1 = img1.getRGB(x, y);
                        int rgb2 = img2.getRGB(x, y);
                        int r1 = (rgb1 >> 16) & 0xff;
                        int g1 = (rgb1 >> 8) & 0xff;
                        int b1 = rgb1 & 0xff;
                        int r2 = (rgb2 >> 16) & 0xff;
                        int g2 = (rgb2 >> 8) & 0xff;
                        int b2 = rgb2 & 0xff;
                        int diff = Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
                        if (diff > 0) {
                            points.add(new Point(x, y));
                        }
                    }
                }
            } else {
                putAllPoint(points, img1);
            }
        } catch (IOException ex) {
            logger.error("Generate diff image failed.", ex);
        }
        return points;
    }

    private void putAllPoint(List<Point> points, BufferedImage img) {
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                points.add(new Point(x, y));
            }
        }
    }

    private class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
