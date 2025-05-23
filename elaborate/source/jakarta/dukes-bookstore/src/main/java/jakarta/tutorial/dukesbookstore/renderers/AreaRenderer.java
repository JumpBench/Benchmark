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

    public AreaRenderer() {
    }

    // Renderer Methods
    /**
     * <p>
     * No decoding is required.
     * </p>
     *
     * @param context   <code>FacesContext</code>for the current request
     * @param component <code>UIComponent</code> to be decoded
     */
    @Override
    public void decode(FacesContext context, UIComponent component) {
        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }
    }

    /**
     * <p>
     * No begin encoding is required.
     * </p>
     *
     * @param context   <code>FacesContext</code>for the current request
     * @param component <code>UIComponent</code> to be decoded
     */
    @Override
    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }
    }

    /**
     * <p>
     * No children encoding is required.
     * </p>
     *
     * @param context   <code>FacesContext</code>for the current request
     * @param component <code>UIComponent</code> to be decoded
     * @throws java.io.IOException
     */
    @Override
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }
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

    /**
     * <p>
     * Encode this component.
     * </p>
     *
     * @param context   <code>FacesContext</code>for the current request
     * @param component <code>UIComponent</code> to be decoded
     * @throws java.io.IOException
     */
    @Override
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }

        AreaComponent area = (AreaComponent) component;
        UIComponent image = context.getViewRoot().findComponent("bookForm:mapImage");
        if (image == null) {
            throw new IllegalStateException(
                    "Target image component with id '" + area.getTargetImage() + "' not found.");
        }

        String targetImageId = image.getClientId(context);
        ImageArea iarea = (ImageArea) area.getValue();
        ResponseWriter writer = context.getResponseWriter();
        StringBuilder sb;

        writer.startElement("area", area);
        writer.writeAttribute("alt", iarea.getAlt(), "alt");
        writer.writeAttribute("coords", iarea.getCoords(), "coords");
        writer.writeAttribute("shape", iarea.getShape(), "shape");
        sb = new StringBuilder("document.forms[0]['").append(targetImageId)
                .append("'].src='");
        sb.append(
                getURI(context,
                        (String) area.getAttributes().get("onmouseout")));
        sb.append("'");
        writer.writeAttribute("onmouseout", sb.toString(), "onmouseout");
        sb = new StringBuilder("document.forms[0]['").append(targetImageId)
                .append("'].src='");
        sb.append(
                getURI(context,
                        (String) area.getAttributes().get("onmouseover")));
        sb.append("'");
        writer.writeAttribute("onmouseover", sb.toString(), "onmouseover");
        sb = new StringBuilder("document.forms[0]['");
        sb.append(getName(context, area));
        sb.append("'].value='");
        sb.append(iarea.getAlt());
        sb.append("'; document.forms[0].submit()");
        writer.writeAttribute("onclick", sb.toString(), "value");
        writer.endElement("area");
    }

    /**
     * <p>
     * Return the calculated name for the hidden input field.
     * </p>
     *
     * @param context   Context for the current request
     * @param component Component we are rendering
     */
    private String getName(FacesContext context, UIComponent component) {
        while (component != null) {
            if (component instanceof MapComponent) {
                return (component.getId() + "_current");
            }

            component = component.getParent();
        }

        throw new IllegalArgumentException();
    }

    /**
     * <p>
     * Return the path to be passed into JavaScript for the specified value.
     * </p>
     *
     * @param context Context for the current request
     * @param value   Partial path to be (potentially) modified
     */
    private String getURI(FacesContext context, String value) {
        if (value.startsWith("/")) {
            return (context.getExternalContext().getRequestContextPath() + value);
        } else {
            return (value);
        }
    }
}