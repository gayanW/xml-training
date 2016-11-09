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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App
{
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main( String[] args )
    {
        if (args.length != 1) {
            System.err.println("usage: java " + App.class.getSimpleName() + " input_file");
            System.exit(1);
        }

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setNamespaceAware(true);

        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            SaxBookHandler saxBookHandler = new SaxBookHandler();
            saxParser.parse(new File(args[0]), saxBookHandler);

            printBookModels(saxBookHandler.getBooks());

        } catch (ParserConfigurationException e) {
            logger.error("Unable to create saxParser due to configuration error", e);
        } catch (SAXException e) {
            logger.error("Unable to create saxParser", e);
        } catch (IOException e) {
            logger.error("IO error reading file: " + args[0], e);
        }

    }

    private static void printBookModels(List<BookModel> bookModels) {
        for (BookModel bookModel : bookModels) {
            System.out.println(bookModel);
        }
    }
}

class SaxBookHandler extends DefaultHandler {

    private List<BookModel> books = new ArrayList<BookModel>();

    private BookModel currentBook;
    private boolean insideBook;

    private StringBuffer accumulator = new StringBuffer();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        // Ready to accumulate new text
        accumulator.setLength(0);

        if (localName.equals("book")) {
            currentBook = BookModel.newInstance();
            insideBook = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if (insideBook) {
            currentBook.putKeyValue(localName, accumulator.toString().trim());
        }

        if (localName.equals("book")) {
            books.add(currentBook);
            insideBook = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        accumulator.append(ch, start, length);
    }

    List<BookModel> getBooks() {
        return books;
    }
}
