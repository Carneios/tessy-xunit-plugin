<?xml version="1.0" encoding="UTF-8"?>
<!--
The MIT License (MIT)

Copyright (c) 2015 Lars Sadau (Carneios GmbH) and all contributors

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
-->

<!-- Evaluates a TESSY Details Report file and transform them to jUnit xml -->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:param name="filename" />
  <xsl:param name="filedir" />
  <xsl:param name="basedir" />
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*" />

  <xsl:template match="/report">
    <testsuite>
      <xsl:attribute name="name"><xsl:call-template name="packagename" /></xsl:attribute>
      <xsl:attribute name="tests" ><xsl:value-of select="count(//testcase/teststep)" /></xsl:attribute>
      <xsl:attribute name="failures" ><xsl:value-of select="count(//testcase/teststep[@success='notok'])" /></xsl:attribute>
      <xsl:attribute name="timestamp" ><xsl:value-of select="/report/summary/info/@date" />T<xsl:value-of select="/report/summary/info/@time" /></xsl:attribute>
      <xsl:apply-templates select="//testcase" />
  </testsuite>
  </xsl:template>
  
  <xsl:template match="testcase">
      <xsl:apply-templates select="teststep" />
  </xsl:template>
  
  <xsl:template match="teststep">
    <testcase>
      <xsl:attribute name="name">
        <xsl:if test="1=count(../@name)">
          <xsl:value-of select="../@name" />
          <xsl:value-of select="'_'" />
        </xsl:if>
        <xsl:if test="1=count(@name)">
          <xsl:value-of select="@name" />
          <xsl:value-of select="'_'" />
        </xsl:if>
        <xsl:value-of select="'id'" />
        <xsl:value-of select="@id" />
      </xsl:attribute>
      <xsl:attribute name="classname" ><xsl:call-template name="packagename"/>.<xsl:value-of select="/report/summary/info/@testobject_name"/></xsl:attribute>
      <xsl:attribute name="assertions" ><xsl:value-of select="count(results/result[@expected_value!='*none*'])"/></xsl:attribute>
      <xsl:apply-templates select="results/result" />
      <xsl:call-template name="attachments"/>
      <!-- 
            <xsl:attribute name="time" />
            <xsl:attribute name="status" />
      --> 
    </testcase>
  </xsl:template>
  <xsl:template match="result[@success='notok']">
   <failure>
      <xsl:attribute name="message" >Expected value [<xsl:value-of select="@expected_value"/>], but was [<xsl:value-of select="@actual_value"/>]</xsl:attribute>
      <xsl:attribute name="type" >Assertion_Failed</xsl:attribute>
   </failure>
   </xsl:template>
   <xsl:template match="result[@success='notexecuted']">
   <skipped>Not Executed</skipped>
  </xsl:template>

  <xsl:template name="packagename">
      <xsl:value-of select="/report/summary/info/@project_name"/>.<xsl:value-of select="/report/summary/info/@module_name"/>
  </xsl:template>
  <xsl:template name="attachments">
<system-out>
  [[ATTACHMENT|<xsl:value-of select="$filename" />]]
  [[ATTACHMENT|<xsl:value-of select="substring-before($filename,'.xml')" />.pdf]]
</system-out>
  </xsl:template>
</xsl:stylesheet>
