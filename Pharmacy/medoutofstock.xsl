<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method='html' version='1.0' encoding='UTF-8' indent='yes'/>
<xsl:template match="/">
  <html>
  <body>
  <h2>Medication Out Of Stock</h2>
    <table border="1">
      <tr bgcolor="skyblue">
        <th align="left">Name</th>
        <th align="left">Ingredients</th>
        <th align="left">Manufacturer</th>
        <th align="left">Quantity</th>
        <th align="left">Price</th>
      </tr>
      <xsl:for-each select="medications/medication">
      <tr>
        <td><xsl:value-of select="name"/></td>
        <td><xsl:value-of select="ingredients"/></td>
        <td><xsl:value-of select="manufacturer"/></td>
        <td><xsl:value-of select="quantity"/></td>
        <td><xsl:value-of select="price"/></td>
      </tr>
      </xsl:for-each>
    </table>    
  </body>
  </html>
</xsl:template>
</xsl:stylesheet>