/*
 *
 *   Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *   WSO2 Inc. licenses this file to you under the Apache License,
 *   Version 2.0 (the "License"); you may not use this file except
 *   in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 * /
 */

package com.wso2.training.xml;

import javax.xml.transform.stream.StreamSource;
import java.io.File;

/**
 * Program to perform xml parsing and validation
 * usage: java App (dom|sax) xml_file [xsd_file]
 *
 * sample cases:
 *   $ java App dom data/books.xml
 *   $ java App sax data/books.xml
 *
 *   To validate and parse
 *   $ java App dom data/sample.xml data/sample.xsd
 *
 */
public class App
{
    public static void main( String[] args )
    {
        if (!(args.length == 2 | args.length == 3)) {
            printUsageStatement();
            System.exit(1);
        }

        // Commandline arguments
        String parserArg = args[0];
        String xmlFileArg = args[1];

        Parser parser = null;
        switch (parserArg) {
            case "sax":
                parser = new SaxParser();
                break;
            case "dom":
                parser = new DomParser();
                break;
            default:
                System.err.println(args[0] + " is not a valid parser");
                printUsageStatement();
                System.exit(1);
        }

        if (args.length == 3) {
            // Validate
            String xsdFileArg = args[2];
            boolean isValid = XmlFunctions.validateByXsd(new File(xmlFileArg), new File(xsdFileArg));

            if (!isValid) {
                System.exit(1);
            }
        }

        File xmlFile = new File(args[1]);
        parser.parse(xmlFile);
        XmlFunctions.transform(new File("data/article1.xml"), new File("data/article1b.xsl"), System.out);
    }


    /**
     * Prints the usage statement for the application
     */
    private static void printUsageStatement() {
        System.out.println("usage: java " + App.class.getSimpleName() + " (sax|dom) input_file [xsd_file]");
        System.out.println("sample cases:\n" +
                "$ java App dom data/books.xml\n" +
                "$ java App sax data/books.xml\n" +
                "To validate and parse\n" +
                "$ java App dom data/sample.xml data/sample.xsd"
        );
    }
}
