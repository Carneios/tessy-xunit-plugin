/**
 * The MIT License
 * Copyright (c) 2014 Lars Sadau (Carneios GmbH) and all contributors
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
import java.util.HashMap;
import java.util.Map;

import org.jenkinsci.lib.dtkit.model.InputMetric;
import org.junit.Test;

public class TessyXSLTest extends AbstractXUnitXSLTest {

   public TessyXSLTest() {
      super(TessyInputMetric.class);
   }

   @Override
   public void convert(final InputMetric inputMetric, final File inputXMLFile,
      final File outputXMLFile)
   {
      final Map<String, Object> params = new HashMap<>();
      params.put(TessyInputMetric.BASEDIR_XSL_PARAM, "BASEDIR");
      params.put(TessyInputMetric.FILENAME_XSL_PARAM, "FILENAME.xml");
      params.put(TessyInputMetric.FILEDIR_XSL_PARAM, "FILEDIR");

      inputMetric.convert(inputXMLFile, outputXMLFile, params);
   }

   @Test
   public void allPass() throws Exception {
      convertAndValidate("allPass");
   }
}
