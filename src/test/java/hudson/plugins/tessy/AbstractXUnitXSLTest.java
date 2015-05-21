/**
 * The MIT License
 * Copyright (c) 2014 Gregory Boissinot, Lars Sadau and all contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package hudson.plugins.tessy;

import java.io.File;
import java.nio.file.Paths;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.jenkinsci.lib.dtkit.model.InputMetric;
import org.jenkinsci.lib.dtkit.model.InputMetricFactory;
import org.jenkinsci.lib.dtkit.util.validator.ValidationError;
import org.junit.Assert;
import org.junit.Before;
	
/**
 * Class copied from <a href=
 * "https://github.com/jenkinsci/gallio-plugin/blob/master/src/test/java/hudson/plugins/gallio/AbstractXUnitXSLTest.java"
 * >Jenkins Gallio Plugin</a>
 */
public class AbstractXUnitXSLTest {

   final Class<? extends InputMetric> metricType;

   public AbstractXUnitXSLTest(final Class<? extends InputMetric> metricType) {
      this.metricType = metricType;
   }

   @Before
   public void prepareXmlUnit() {
      XMLUnit.setIgnoreWhitespace(true);
      XMLUnit.setNormalizeWhitespace(true);
      XMLUnit.setIgnoreComments(true);
   }

   public void convertAndValidate(final String inputXMLPath,
      final String expectedResultPath) throws Exception
   {
      final InputMetric inputMetric = InputMetricFactory.getInstance(metricType);
      final File outputXMLFile = File.createTempFile("result", "xml");
      final File inputXMLFile = new File(this.getClass().getResource(inputXMLPath)
         .toURI());

      // The input file must be valid
      final boolean result = inputMetric.validateInputFile(inputXMLFile);
      for(final ValidationError error : inputMetric.getInputValidationErrors()) {
         System.out.println(error);
      }
      Assert.assertTrue(result);

      convert(inputMetric, inputXMLFile, outputXMLFile);

      final Diff myDiff = new Diff(XSLUtil.readXmlAsString(new File(this.getClass()
         .getResource(expectedResultPath).toURI())),
         XSLUtil.readXmlAsString(outputXMLFile));
      Assert.assertTrue("XSL transformation did not work" + myDiff,
         myDiff.similar());

      // The generated output file must be valid
      Assert.assertTrue(inputMetric.validateOutputFile(outputXMLFile));

      outputXMLFile.deleteOnExit();
   }

   public void convertAndValidate(final String testName) throws Exception {
      convertAndValidate(Paths.get(testName, "input.xml").toString(), Paths
         .get(testName, "junit-result.xml").toString());
   }

   public void convert(final InputMetric inputMetric, final File inputXMLFile,
      final File outputXMLFile)
   {
      inputMetric.convert(inputXMLFile, outputXMLFile);
   }

}
