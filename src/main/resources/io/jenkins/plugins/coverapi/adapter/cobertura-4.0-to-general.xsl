<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


    <xsl:template match="/">
        <result>
            The source is -
            <xsl:value-of select="/coverage/sources/source"/>
        </result>
    </xsl:template>

</xsl:stylesheet>