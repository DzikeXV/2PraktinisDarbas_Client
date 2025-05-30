<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ns="http://spring.io/guides/gs-producing-web-service">

    <xsl:output method="html" indent="yes"/>

    <xsl:template match="/ns:spaceship">
        <html>
            <head>
                <title>Erdvėlaivio informacija</title>
                <style>
                    body { font-family: Arial; }
                    table { border-collapse: collapse; margin-top: 10px; }
                    th, td { border: 1px solid black; padding: 5px; }
                    th { background-color: #f2f2f2; }
                </style>
            </head>
            <body>
                <h1>Erdvėlaivis: <xsl:value-of select="ns:name"/></h1>
                <p><strong>Modelis:</strong> <xsl:value-of select="ns:model"/></p>
                <p><strong>Registracija:</strong> <xsl:value-of select="ns:registry"/></p>
                <p><strong>Gamintojas:</strong> <xsl:value-of select="ns:manufacturer"/></p>
                <p><strong>Pagaminimo metai:</strong> <xsl:value-of select="ns:yearBuilt"/></p>

                <h2>Įgulos nariai</h2>
                <table>
                    <tr>
                        <th>Vardas</th><th>Pavardė</th><th>Pozicija</th><th>Rangas</th><th>Amžius</th><th>Patirtis</th>
                    </tr>
                    <xsl:for-each select="ns:crew">
                        <tr>
                            <td><xsl:value-of select="ns:firstName"/></td>
                            <td><xsl:value-of select="ns:lastName"/></td>
                            <td><xsl:value-of select="ns:position"/></td>
                            <td><xsl:value-of select="ns:rank"/></td>
                            <td><xsl:value-of select="ns:age"/></td>
                            <td><xsl:value-of select="ns:experienceYears"/></td>
                        </tr>
                    </xsl:for-each>
                </table>

                <h2>Misijos</h2>
                <table>
                    <tr>
                        <th>Pavadinimas</th><th>Pradžia</th><th>Pabaiga</th>
                    </tr>
                    <xsl:for-each select="ns:missions">
                        <tr>
                            <td><xsl:value-of select="ns:name"/></td>
                            <td><xsl:value-of select="ns:startDate"/></td>
                            <td><xsl:value-of select="ns:endDate"/></td>
                        </tr>
                    </xsl:for-each>
                </table>

            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
