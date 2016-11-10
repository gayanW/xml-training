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

import java.io.File;
import java.util.stream.IntStream;

/**
 * Program to perform xml parsing and validation
 * usage: java App <command> <args>
 *
 *     Commands:
 *          parse (dom|sax) xml_file [xsd_file]
 *          transform xml_file xsf_file
 *
 * sample cases:
 *   $ java App parse dom data/books.xml
 *   $ java App parse sax data/books.xml
 *
 *   To parse and validate
 *   $ java App parse dom data/sample.xml data/sample.xsd
 *
 *   To transform
 *   $ java App transform data/article1.xml data/article1a.xsl
 *
 */
public class App
{
    public static void main( String[] args ) {
        if (args.length == 0) {
            printUsageStatement();
            System.exit(1);
        }

        // Handle program arguments
        String commandArg = args[0];
        switch (commandArg) {
            case "parse":
                parse(args);
                break;
            case "transform":
                transform(args);
                break;
            default:
                System.err.println(commandArg + " is not a valid command");
                printUsageStatement();
                System.exit(1);
        }
    }


    private static void transform(String[] args) {
        if (!isValidCommand(args, 3)) {
            printUsageStatement();
            System.exit(1);
        }

        String xmlFileArg = args[1];
        String xslFileArg = args[2];

        XmlFunctions.transform(new File(xmlFileArg), new File(xslFileArg), System.out);
    }

    private static void parse(String[] args) {
        if (!isValidCommand(args, 3, 4)) {
            printUsageStatement();
            System.exit(1);
        }

        String parserArg = args[1];
        String xmlFileArg = args[2];
        File xmlFile = new File(xmlFileArg);

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

        if (args.length == 4) {
            // Validate
            String xsdFileArg = args[3];
            boolean isValid = XmlFunctions.validateByXsd(xmlFile, new File(xsdFileArg));

            if (!isValid) {
                System.exit(1);
            }
        }

        parser.parse(xmlFile);
    }

    /**
     * Check whether the command is valid and complete
     * @param args supplied command-line arguments as an array of String objects
     * @param validArgCountValues number of arguments a command could take as and array of ints
     *                            ex: parse command could have either one or two more preceding arguments
     * @return 'true' if the supplied command is valid
     */
    public static boolean isValidCommand(String[] args, int... validArgCountValues) {
        return IntStream.of(validArgCountValues).anyMatch(x -> x == args.length);
    }



    /**
     * Prints the usage statement for the application
     */
    private static void printUsageStatement() {
        System.out.println("usage: java " + App.class.getSimpleName() + " <command> <args>\n" +
                "Commands:\n" +
                "\tparse (dom|sax) xml_file [xsd_file]\n" +
                "\ttransform xml_file xsf_file\n"
        );
        System.out.println("Sample cases:\n" +
                "$ java App parse dom data/books.xml\n" +
                "$ java App parse sax data/books.xml\n\n" +

                "To parse and validate\n" +
                "$ java App parse dom data/sample.xml data/sample.xsd\n\n" +

                "To transform\n" +
                "$ java App transform data/article1.xml data/article1a.xsl"
        );
    }
}
