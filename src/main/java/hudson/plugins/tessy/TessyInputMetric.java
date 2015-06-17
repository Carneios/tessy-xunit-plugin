/**
 * The MIT License
 * Copyright (c) 2015 Lars Sadau (Carneios GmbH) and all contributors
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

import org.jenkinsci.lib.dtkit.model.InputMetricXSL;
import org.jenkinsci.lib.dtkit.model.InputType;
import org.jenkinsci.lib.dtkit.model.OutputMetric;
import org.jenkinsci.lib.dtkit.util.converter.ConversionException;
import org.jenkinsci.plugins.xunit.types.model.JUnitModel;

public class TessyInputMetric extends InputMetricXSL {

   public static final String FILENAME_XSL_PARAM = "filename";
   public static final String FILEDIR_XSL_PARAM = "filedir";
   public static final String BASEDIR_XSL_PARAM = "basedir";

   @Override
   public InputType getToolType() {
      return InputType.TEST;
   }

   @Override
   public String getToolName() {
      return "Tessy";
   }

   @Override
   public String getToolVersion() {
      return "3.2";
   }

   @Override
   public String getXslName() {
      return "tessy-3.2-to-junit-1.0.xsl";
   }

   @Override
   public String[] getInputXsdNameList() {
      return null;
   }

   @Override
   public OutputMetric getOutputFormatType() {
      return JUnitModel.LATEST;
   }

   @Override
   public void convert(final File inputFile, final File outFile, final Map<String, Object> params)
      throws ConversionException
   {
      final Map<String, Object> conversionParams = new HashMap<>();
      conversionParams.put(FILENAME_XSL_PARAM, inputFile.getAbsolutePath());

      if(params != null) {
         conversionParams.putAll(params);
      }
      super.convert(inputFile, outFile, conversionParams);
   }
}
