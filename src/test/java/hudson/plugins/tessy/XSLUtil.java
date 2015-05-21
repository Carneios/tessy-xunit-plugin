/**
 * The MIT License
 * Copyright (c) 2014 Gregory Boissinot and all contributors
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class copied from <a href=
 * "https://github.com/jenkinsci/gallio-plugin/blob/master/src/test/java/hudson/plugins/gallio/XSLUtil.java"
 * >Jenkins Gallio Plugin</a>
 */
public class XSLUtil {

   public static String readXmlAsString(final File input)
      throws IOException
   {
      String xmlString = "";

      if(input == null) {
         throw new IOException("The input stream object is null.");
      }

      final FileInputStream fileInputStream = new FileInputStream(input);
      final InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
      final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      String line = bufferedReader.readLine();
      while(line != null) {
         xmlString += line + "\n";
         line = bufferedReader.readLine();
      }
      fileInputStream.close();
      fileInputStream.close();
      bufferedReader.close();

      return xmlString;
   }

}
