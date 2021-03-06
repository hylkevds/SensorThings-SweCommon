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
package de.fraunhofer.iosb.ilt.swe.common;

import de.fraunhofer.iosb.ilt.configurable.AbstractConfigurable;

/**
 *
 * @author Hylke van der Schaaf
 */
public abstract class AbstractSWE extends AbstractConfigurable<Void, Void> {

    public static final String MODE_SIMPLE = "simple";
    public static final String MODE_EXPERT = "expert";
    public static final String MODE_VALUE = "value";
    public static final String MODE_SIMPLE_EXPERT = MODE_SIMPLE + "," + MODE_EXPERT;
    public static final String MODE_SIMPLE_EXPERT_VALUE = MODE_SIMPLE + "," + MODE_EXPERT + "," + MODE_VALUE;

}
