/*
 * Copyright (C) 2018 Fraunhofer Institut IOSB, Fraunhoferstr. 1, D 76131
 * Karlsruhe, Germany.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.fraunhofer.iosb.ilt.swe.common.simple;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import de.fraunhofer.iosb.ilt.configurable.annotations.ConfigurableClass;
import de.fraunhofer.iosb.ilt.configurable.annotations.ConfigurableField;
import de.fraunhofer.iosb.ilt.configurable.editor.AbstractEditorMap;
import de.fraunhofer.iosb.ilt.configurable.editor.EditorClass;
import de.fraunhofer.iosb.ilt.configurable.editor.EditorMap;
import de.fraunhofer.iosb.ilt.configurable.editor.EditorString;
import de.fraunhofer.iosb.ilt.swe.common.constraint.AllowedTimes;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Hylke van der Schaaf
 * @author Michael Jacoby
 */
@ConfigurableClass(jsonName = "Time")
public class Time extends AbstractSimpleComponent {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Time.class);

    @ConfigurableField(editor = EditorString.class, optional = true,
            profilesGui = MODE_EXPERT,
            label = "Reference Time",
            description = "The “referenceTime” attribute is used to specify a different time origin than the one\n"
            + "sometimes implied by the “referenceFrame”. This is used to express a time relative to an\n"
            + "arbitrary epoch (i.e. different from the origin of a well known reference frame). The new\n"
            + "time origin specified by “referenceTime” shall be expressed with respect to the reference\n"
            + "frame specified and is of type “DateTime”. This forces the definition of this origin as a\n"
            + "calendar date/time combination.")
    @EditorString.EdOptsString(profilesEdit = MODE_SIMPLE_EXPERT)
    private String referenceTime;

    @ConfigurableField(editor = EditorString.class, optional = true,
            profilesGui = MODE_EXPERT,
            label = "Local Frame",
            description = "The optional “localFrame” attribute allows for the definition of a local temporal frame of\n"
            + "reference through the value of the component (i.e. we are specifying a time origin), as\n"
            + "opposed to the referenceFrame which specifies that the value of the component is in\n"
            + "reference to this frame.")
    @EditorString.EdOptsString(profilesEdit = MODE_SIMPLE_EXPERT)
    private String localFrame;

    @ConfigurableField(editor = EditorString.class, optional = true,
            profilesGui = MODE_EXPERT,
            label = "Unit of Measurement",
            description = "The “uom” attribute is mandatory since time is a continuous property that shall always be\n"
            + "expressed in a well defined scale. The only units allowed are obviously time units.")
    @EditorString.EdOptsString(profilesEdit = MODE_SIMPLE_EXPERT, dflt = "http://www.opengis.net/def/uom/ISO‐8601/0/Gregorian")
    private String uom;

    @ConfigurableField(editor = EditorClass.class, optional = true,
            profilesGui = MODE_SIMPLE_EXPERT,
            label = "Constraint",
            description = "The “constraint” attribute allows further restricting the\n"
            + "range of possible time values.")
    @EditorClass.EdOptsClass(clazz = AllowedTimes.class)
    private AllowedTimes constraint;

    @ConfigurableField(editor = EditorString.class, optional = true,
            profilesGui = MODE_VALUE,
            label = "Value",
            description = "The “value” attribute (or the corresponding value in out-of-band data) is of type\n"
            + "“TimePosition” and must match the constraint.")
    @EditorString.EdOptsString(profilesEdit = MODE_VALUE)
    private String value;

    public String getReferenceTime() {
        return referenceTime;
    }

    public void setReferenceTime(String referenceTime) {
        this.referenceTime = referenceTime;
    }

    public String getLocalFrame() {
        return localFrame;
    }

    public void setLocalFrame(String localFrame) {
        this.localFrame = localFrame;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public AllowedTimes getConstraint() {
        return constraint;
    }

    public void setConstraint(AllowedTimes constraint) {
        this.constraint = constraint;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public JsonElement getValueJson() {
        return new JsonPrimitive(getValue());
    }

    @Override
    public void setValueJson(JsonElement jsonValue) {
        if (!jsonValue.isJsonPrimitive()) {
            LOGGER.warn("Given value is not a JsonPrimitive: {}", jsonValue);
            return;
        }
        try {
            setValue(jsonValue.getAsJsonPrimitive().getAsString());
            EditorMap<?> editor = getConfigEditor(null, null);
            AbstractEditorMap.Item valueEditorItem = editor.getOptions().get("value");
            valueEditorItem.editor.setValue(getValue());
        } catch (NumberFormatException exc) {
            LOGGER.warn("Given value is not Text: {}", jsonValue);
            LOGGER.trace("", exc);
        }
    }

    @Override
    public boolean valueIsValid() {
        if (getValue() == null) {
            return false;
        }
        if (getConstraint() == null) {
            return true;
        }
        return getConstraint().isValid(getValue(), getUom());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.referenceTime);
        hash = 19 * hash + Objects.hashCode(this.localFrame);
        hash = 19 * hash + Objects.hashCode(this.uom);
        hash = 19 * hash + Objects.hashCode(this.constraint);
        hash = 19 * hash + Objects.hashCode(this.value);
        hash = 19 * hash + super.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Time other = (Time) obj;
        if (!Objects.equals(this.referenceTime, other.referenceTime)) {
            return false;
        }
        if (!Objects.equals(this.localFrame, other.localFrame)) {
            return false;
        }
        if (!Objects.equals(this.uom, other.uom)) {
            return false;
        }
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        if (!Objects.equals(this.constraint, other.constraint)) {
            return false;
        }
        return super.equals(obj);
    }

}
