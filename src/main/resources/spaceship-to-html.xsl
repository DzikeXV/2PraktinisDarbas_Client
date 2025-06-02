<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ns="http://spring.io/guides/gs-producing-web-service">

    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Erdvėlaivio ataskaita</title>
                <style>
                    body { font-family: Arial; margin: 2em; }
                    h1 { color: navy; }
                    h2 { margin-top: 1em; }
                    .block { margin-bottom: 1em; }
                </style>
            </head>
            <body>
                <h1>Erdvėlaivio ataskaita</h1>

                <xsl:for-each select="ns:getSpaceshipResponse/ns:spaceship">
                    <div class="block">
                        <b>ID:</b> <xsl:value-of select="ns:id"/> <br/>
                        <b>Pavadinimas:</b> <xsl:value-of select="ns:name"/> <br/>
                        <b>Modelis:</b> <xsl:value-of select="ns:model"/> <br/>
                        <b>Registracija:</b> <xsl:value-of select="ns:registry"/> <br/>
                        <b>Gamintojas:</b> <xsl:value-of select="ns:manufacturer"/> <br/>
                        <b>Metai:</b> <xsl:value-of select="ns:yearBuilt"/> <br/>
                    </div>

                    <h2>Įgulos narys</h2>
                    <xsl:for-each select="ns:crew">
                        <div class="block">
                            <b>Vardas:</b> <xsl:value-of select="ns:firstName"/> <xsl:value-of select="ns:lastName"/> <br/>
                            <b>Pareigos:</b> <xsl:value-of select="ns:position"/> <br/>
                            <b>Laipsnis:</b> <xsl:value-of select="ns:rank"/> <br/>
                            <b>Amžius:</b> <xsl:value-of select="ns:age"/> <br/>
                            <b>Patirtis:</b> <xsl:value-of select="ns:experienceYears"/> metų
                        </div>
                    </xsl:for-each>

                    <h2>Misijos</h2>
                    <xsl:for-each select="ns:missions">
                        <div class="block">
                            <b>ID:</b> <xsl:value-of select="ns:id"/> <br/>
                            <b>Pavadinimas:</b> <xsl:value-of select="ns:name"/> <br/>
                            <b>Data:</b> <xsl:value-of select="ns:startDate"/> → <xsl:value-of select="ns:endDate"/>
                        </div>
                    </xsl:for-each>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
