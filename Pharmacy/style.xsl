<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" >
<xsl:output method="text" omit-xml-declaration="yes" indent="no"/>
<xsl:template match="/">
Name,Ingredients,Manufacturer,Quantity,Price
<xsl:for-each select="//medication">
<xsl:value-of select="concat(name,',',ingredients,',',manufacturer,',',quantity,',',price,'&#xA;')"/>
</xsl:for-each>
</xsl:template>
</xsl:stylesheet>