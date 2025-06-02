<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:ns="http://spring.io/guides/gs-producing-web-service">

    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4"
                                       page-height="29.7cm"
                                       page-width="21cm"
                                       margin="2cm">
                    <fo:region-body/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="A4">
                <fo:flow flow-name="xsl-region-body">

                    <fo:block font-size="18pt" font-weight="bold" space-after="10pt">Erdvėlaivio ataskaita</fo:block>

                    <xsl:for-each select="ns:getSpaceshipResponse/ns:spaceship">
                        <fo:block font-size="12pt" space-after="6pt">
                            <fo:block>ID: <xsl:value-of select="ns:id"/></fo:block>
                            <fo:block>Pavadinimas: <xsl:value-of select="ns:name"/></fo:block>
                            <fo:block>Modelis: <xsl:value-of select="ns:model"/></fo:block>
                            <fo:block>Registracija: <xsl:value-of select="ns:registry"/></fo:block>
                            <fo:block>Gamintojas: <xsl:value-of select="ns:manufacturer"/></fo:block>
                            <fo:block>Pagaminimo metai: <xsl:value-of select="ns:yearBuilt"/></fo:block>
                        </fo:block>

                        <fo:block font-weight="bold" font-size="14pt" space-before="10pt">Įgulos narys</fo:block>
                        <xsl:for-each select="ns:crew">
                            <fo:block>
                                <fo:block>Vardas: <xsl:value-of select="ns:firstName"/> <xsl:value-of select="ns:lastName"/></fo:block>
                                <fo:block>Pareigos: <xsl:value-of select="ns:position"/></fo:block>
                                <fo:block>Laipsnis: <xsl:value-of select="ns:rank"/></fo:block>
                                <fo:block>Amžius: <xsl:value-of select="ns:age"/> m., Patirtis: <xsl:value-of select="ns:experienceYears"/> m.</fo:block>
                            </fo:block>
                        </xsl:for-each>

                        <fo:block font-weight="bold" font-size="14pt" space-before="10pt">Misijos</fo:block>
                        <xsl:for-each select="ns:missions">
                            <fo:block>
                                <fo:block>ID: <xsl:value-of select="ns:id"/></fo:block>
                                <fo:block>Pavadinimas: <xsl:value-of select="ns:name"/></fo:block>
                                <fo:block>Laikas: <xsl:value-of select="ns:startDate"/> → <xsl:value-of select="ns:endDate"/></fo:block>
                            </fo:block>
                        </xsl:for-each>
                    </xsl:for-each>

                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
