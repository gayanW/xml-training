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
import java.util.List;

public class App
{
    public static void main( String[] args )
    {
        if (args.length != 2) {
            printUsageStatement();
            System.exit(1);
        }

        Parser parser = null;
        switch (args[0]) {
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

        parser.parse(new File(args[1]));
    }

    private static void printBookModels(List<BookModel> bookModels) {
        for (BookModel bookModel : bookModels) {
            System.out.println(bookModel);
        }
    }

    private static void printUsageStatement() {
        System.err.println("usage: java " + App.class.getSimpleName() + " (sax|dom) input_file");
    }
}
