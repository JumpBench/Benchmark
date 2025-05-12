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
package jakarta.tutorial.dukesbookstore.components;

import java.io.IOException;

import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UICommand;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.tutorial.dukesbookstore.listeners.AreaSelectedEvent;

/**
 * <p>
 * {@link MapComponent} is a JavaServer Faces component that corresponds to a
 * client-side image map. It can have one or more children of type
 * {@link AreaComponent}, each representing hot spots, which a user can click on
 * and mouse over.
 * </p>
 *
 * <p>
 * This component is a source of {@link AreaSelectedEvent} events, which are
 * fired whenever the current area is changed.
 * </p>
 *
 * <p>
 * Use of the javax.faces.component.StateHelper interface allows the use of
 * expressions and also makes it unnecessary to implement saveState() and
 * restoreState().
 * </p>
 */
@FacesComponent("DemoMap")
public class MapComponent extends UICommand {

    private enum PropertyKeys {
        current;
    }

    public MapComponent() {
        super();
    }

    @Override
    public String getFamily() {
        return "Map";
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }

    @Override
    public void encodeChildren(FacesContext context) throws IOException {
        if (context == null) {
            throw new NullPointerException();
        }

        for (UIComponent child : getChildren()) {
            child.encodeAll(context); // Renders the graphic image and area components
        }
    }

    public String getCurrent() {
        return (String) getStateHelper().eval(PropertyKeys.current, null);
    }

    public void setCurrent(String current) {
        if (this.getParent() == null) {
            return;
        }

        String previous = (String) getStateHelper().get(current);
        getStateHelper().put(PropertyKeys.current, current);

        if ((previous == null && current == null) ||
                (previous != null && previous.equals(current))) {
            // no change
        } else {
            this.queueEvent(new AreaSelectedEvent(this));
        }
    }
}
