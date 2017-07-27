<?xml version="1.0" encoding="UTF-8"?>
<!--
  ~ Copyright (C) 2001-2016 Food and Agriculture Organization of the
  ~ United Nations (FAO-UN), United Nations World Food Programme (WFP)
  ~ and United Nations Environment Programme (UNEP)
  ~
  ~ This program is free software; you can redistribute it and/or modify
  ~ it under the terms of the GNU General Public License as published by
  ~ the Free Software Foundation; either version 2 of the License, or (at
  ~ your option) any later version.
  ~
  ~ This program is distributed in the hope that it will be useful, but
  ~ WITHOUT ANY WARRANTY; without even the implied warranty of
  ~ MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  ~ General Public License for more details.
  ~
  ~ You should have received a copy of the GNU General Public License
  ~ along with this program; if not, write to the Free Software
  ~ Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301, USA
  ~
  ~ Contact: Jeroen Ticheler - FAO - Viale delle Terme di Caracalla 2,
  ~ Rome - Italy. email: geonetwork@osgeo.org
  -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:gco="http://www.isotc211.org/2005/gco"
                xmlns:gmd="http://www.isotc211.org/2005/gmd"
                xmlns:gmx="http://www.isotc211.org/2005/gmx"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                version="2.0" exclude-result-prefixes="#all">

  <xsl:param name="uuid" as="xs:string"/>
  <xsl:param name="description" as="xs:string?"/>
  <xsl:param name="north" as="xs:double"/>
  <xsl:param name="east" as="xs:double"/>
  <xsl:param name="south" as="xs:double"/>
  <xsl:param name="west" as="xs:double"/>
  <xsl:param name="geometry" as="xs:string?"/>
  <xsl:param name="onlyBoundingBox" as="xs:boolean?"/>


  <xsl:template match="/">
    <gmd:EX_Extent>
      <xsl:if test="$description">
        <gmd:description>
          <gco:CharacterString>
            <xsl:value-of select="$description"/>
          </gco:CharacterString>
        </gmd:description>
      </xsl:if>
      <gmd:geographicElement>
        <gmd:EX_GeographicBoundingBox>
          <gmd:westBoundLongitude>
            <gco:Decimal><xsl:value-of select="$west"/></gco:Decimal>
          </gmd:westBoundLongitude>
          <gmd:eastBoundLongitude>
            <gco:Decimal><xsl:value-of select="$east"/></gco:Decimal>
          </gmd:eastBoundLongitude>
          <gmd:southBoundLatitude>
            <gco:Decimal><xsl:value-of select="$south"/></gco:Decimal>
          </gmd:southBoundLatitude>
          <gmd:northBoundLatitude>
            <gco:Decimal><xsl:value-of select="$north"/></gco:Decimal>
          </gmd:northBoundLatitude>
        </gmd:EX_GeographicBoundingBox>
      </gmd:geographicElement>

      <xsl:if test="$geometry and $onlyBoundingBox != true()">
        <gmd:geographicElement>
          <gmd:EX_BoundingPolygon>
            <gmd:polygon>
              <xsl:value-of select="$geometry"/>
            </gmd:polygon>
          </gmd:EX_BoundingPolygon>
        </gmd:geographicElement>
      </xsl:if>
    </gmd:EX_Extent>
  </xsl:template>
</xsl:stylesheet>
