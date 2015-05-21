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

import hudson.Extension;

import org.jenkinsci.lib.dtkit.descriptor.TestTypeDescriptor;
import org.jenkinsci.lib.dtkit.type.TestType;
import org.kohsuke.stapler.DataBoundConstructor;

public class TessyPluginType extends TestType {

   @DataBoundConstructor
   public TessyPluginType(final String pattern, final boolean faildedIfNotNew, final boolean deleteOutputFiles, final boolean stopProcessingIfError)
   {
      super(pattern, faildedIfNotNew, deleteOutputFiles, stopProcessingIfError);
   }

   @Override
   public TestTypeDescriptor<?> getDescriptor() {
      return new TessyPluginType.DescriptorImpl();
   }

   @Extension
   public static class DescriptorImpl extends TestTypeDescriptor<TessyPluginType> {

      public DescriptorImpl() {
         super(TessyPluginType.class, TessyInputMetric.class);
      }

      @Override
      public String getId() {
         return TessyPluginType.class.getCanonicalName();
      }
   }

}
