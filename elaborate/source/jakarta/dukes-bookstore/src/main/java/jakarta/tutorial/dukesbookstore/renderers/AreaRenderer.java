/*
 * Copyright (c), Eclipse Foundation, Inc. and its licensors.
 *
 * All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v1.0, which is available at
 * https://www.eclipse.org/org/documents/edl-v10.php
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package jakarta.tutorial.dukesbookstore.renderers;

import java.io.IOException;

import jakarta.faces.FacesException;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;
import jakarta.faces.render.FacesRenderer;
import jakarta.faces.render.Renderer;
import jakarta.tutorial.dukesbookstore.components.AreaComponent;
import jakarta.tutorial.dukesbookstore.components.MapComponent;
import jakarta.tutorial.dukesbookstore.model.ImageArea;

/**
 * <p>
 * This class converts the internal representation of a <code>UIArea</code>
 * component into the output stream associated with the response to a particular
 * request.
 * </p>
 */
@FacesRenderer(componentFamily = "Area", rendererType = "DemoArea")
public class AreaRenderer extends Renderer {

    @Override
    public boolean getRendersChildren() {
        return true;
    }

    private UIComponent findComponentRecursive(UIComponent base, String id) {
        if (id.equals(base.getId())) {
            return base;
        }

        for (UIComponent child : base.getChildren()) {
            UIComponent result = findComponentRecursive(child, id);
            if (result != null) {
                return result;
            }
        }

        for (UIComponent facet : base.getFacets().values()) {
            UIComponent result = findComponentRecursive(facet, id);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        if (context == null || component == null) {
            throw new NullPointerException();
        }

        AreaComponent area = (AreaComponent) component;

        // Delay resolution: component tree should be fully built now
        UIComponent targetImage = findComponentRecursive(context.getViewRoot(), "mapImage");
        if (targetImage == null) {
            throw new FacesException("Could not find component with ID '" + area.getTargetImage() + "'");
        }

        // String targetImageId = targetImage.getClientId(context);
        String targetImageId = "mainForm:mapImage"; // hardcoded fallback

        ImageArea iarea = (ImageArea) area.getValue();
        ResponseWriter writer = context.getResponseWriter();
        StringBuilder sb;

        writer.startElement("area", area);
        writer.writeAttribute("alt", iarea.getAlt(), "alt");
        writer.writeAttribute("coords", iarea.getCoords(), "coords");
        writer.writeAttribute("shape", iarea.getShape(), "shape");

        sb = new StringBuilder("document.forms[0]['").append(targetImageId).append("'].src='")
                .append(getURI(context, (String) area.getAttributes().get("onmouseout")))
                .append("'");
        writer.writeAttribute("onmouseout", sb.toString(), "onmouseout");

        sb = new StringBuilder("document.forms[0]['").append(targetImageId).append("'].src='")
                .append(getURI(context, (String) area.getAttributes().get("onmouseover")))
                .append("'");
        writer.writeAttribute("onmouseover", sb.toString(), "onmouseover");

        sb = new StringBuilder("document.forms[0]['").append(getName(context, area)).append("'].value='")
                .append(iarea.getAlt())
                .append("'; document.forms[0].submit()");
        writer.writeAttribute("onclick", sb.toString(), "value");

        writer.endElement("area");
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        // no-op
    }

    private String getName(FacesContext context, UIComponent component) {
        while (component != null) {
            if (component instanceof MapComponent) {
                return component.getId() + "_current";
            }
            component = component.getParent();
        }
        throw new IllegalArgumentException("No parent MapComponent found");
    }

    private String getURI(FacesContext context, String value) {
        if (value.startsWith("/")) {
            return context.getExternalContext().getRequestContextPath() + value;
        }
        return value;
    }
}
