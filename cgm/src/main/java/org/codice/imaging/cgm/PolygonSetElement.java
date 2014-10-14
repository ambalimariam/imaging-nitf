/*
 * Copyright (c) 2014, Codice
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.codice.imaging.cgm;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PolygonSetElement extends ElementHelpers {
    List<Point> points = new ArrayList<>();
    List<Integer> edgeOutFlags = new ArrayList<>();
        
    public PolygonSetElement() {
        super(CgmIdentifier.POLYGON_SET);
    }

    @Override
    public void readParameters(CgmInputReader dataReader, int parameterListLength) throws IOException {
        int bytesRead = 0;
        while (bytesRead < parameterListLength) {
            Point point = dataReader.readPoint();
            points.add(point);
            bytesRead += 4;
            int edgeOutFlag = dataReader.readEnumValue();
            bytesRead += 2;
            edgeOutFlags.add(edgeOutFlag);
        }
    }

    @Override
    public void dumpParameters() {
        for (int pointIndex = 0; pointIndex < points.size(); ++pointIndex) {
            System.out.println("\tPoint: " + points.get(pointIndex));
            System.out.println("\tEdge out flag:" + edgeOutFlags.get(pointIndex));
        }
    }

    @Override
    public void render(Graphics2D g2, CgmGraphicState graphicState) {
        System.out.println("figure out how to render edge out flags");
        applyFilledPrimitiveAttributes(g2, graphicState);
        Polygon polygon = new Polygon();
        for (int pointIndex = 0; pointIndex < points.size(); ++pointIndex) {
            Point point = points.get(pointIndex);
            polygon.addPoint(point.x, point.y);
        }
        g2.draw(polygon);
    }
    
}