/*
 * Copyright (C) 2001-2022 Food and Agriculture Organization of the
 * United Nations (FAO-UN), United Nations World Food Programme (WFP)
 * and United Nations Environment Programme (UNEP)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301, USA
 *
 * Contact: Jeroen Ticheler - FAO - Viale delle Terme di Caracalla 2,
 * Rome - Italy. email: geonetwork@osgeo.org
 */

package org.fao.geonet.events.md;

import com.google.common.collect.Multimap;
import org.fao.geonet.domain.AbstractMetadata;
import org.fao.geonet.domain.Metadata;

/**
 * Event launched when the indexation of a metadata record started, but not yet committed,
 * allowing to update the fields for indexing.
 *
 */
public class MetadataIndexStarted extends MetadataEvent {

    private static final long serialVersionUID = 5119421930299384126L;

    private Multimap<String, Object> indexFields;

    public MetadataIndexStarted(AbstractMetadata metadata, Multimap<String, Object> indexFields) {
        super(metadata);
        this.indexFields = indexFields;
    }

    public Multimap<String, Object> getIndexFields() {
        return indexFields;
    }
}
