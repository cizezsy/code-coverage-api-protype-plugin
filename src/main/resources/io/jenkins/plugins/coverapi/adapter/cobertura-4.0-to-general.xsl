<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xml"/>

    <xsl:template match="/">
        <report>
            <xsl:attribute name="tool">cobertura</xsl:attribute>
            <node type="report">
                <renders>
                    <render>
                        <xsl:attribute name="render">LineRateRender</xsl:attribute>
                        <xsl:variable name="rate" select="number(coverage/@line-rate)"/>
                        <xsl:variable name="line-number" select="count(coverage//class/lines/line)"/>
                        <rate>
                            <xsl:value-of select="$rate"/>
                        </rate>
                        <part>
                            <xsl:value-of select="$line-number * $rate"/>
                        </part>
                        <sum>
                            <xsl:value-of select="$line-number"/>
                        </sum>
                    </render>
                </renders>
                <nodes>
                    <xsl:apply-templates select="coverage/packages/package"/>
                </nodes>
            </node>
        </report>
    </xsl:template>

    <xsl:template match="coverage/packages/package">
        <node type="package">
            <xsl:attribute name="name">
                <xsl:value-of select="@name"/>
            </xsl:attribute>
            <renders>
                <render>
                    <xsl:attribute name="render">LineRateRender</xsl:attribute>
                    <xsl:variable name="rate" select="number(@line-rate)"/>
                    <xsl:variable name="line-number" select="count(//class/lines/line)"/>
                    <rate>
                        <xsl:value-of select="$rate"/>
                    </rate>
                    <part>
                        <xsl:value-of select="$line-number * $rate"/>
                    </part>
                    <sum>
                        <xsl:value-of select="$line-number"/>
                    </sum>
                </render>
            </renders>
            <nodes>
                <xsl:apply-templates select="classes/class"/>
            </nodes>
        </node>
    </xsl:template>

    <xsl:template match="classes/class">
        <node type="class">
            <xsl:attribute name="name">
                <xsl:value-of select="@name"/>
            </xsl:attribute>
            <renders>
                <render>
                    <xsl:attribute name="render">LineRateRender</xsl:attribute>
                    <xsl:variable name="rate" select="number(@line-rate)"/>
                    <xsl:variable name="line-number" select="count(lines/line)"/>
                    <rate>
                        <xsl:value-of select="$rate"/>
                    </rate>
                    <part>
                        <xsl:value-of select="$line-number * $rate"/>
                    </part>
                    <sum>
                        <xsl:value-of select="$line-number"/>
                    </sum>
                </render>
            </renders>
        </node>
    </xsl:template>
</xsl:stylesheet>