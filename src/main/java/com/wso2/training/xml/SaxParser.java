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
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

/**
 * SAXParser class to parse xml
 */
public class SaxParser implements Parser {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    /**
     * Parse xml file using default sax handler
     * @param xml xml file to be parsed
     */
    public void parse(File xml) {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setNamespaceAware(true);

        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            saxParser.parse(xml, new DefaultHandler());
            logger.info(xml.getName() + " is successfully parsed by SAX Parser");

        } catch (ParserConfigurationException e) {
            logger.error("Unable to create saxParser due to configuration error", e);
        } catch (SAXException e) {
            logger.error("Unable to create saxParser", e);
        } catch (IOException e) {
            logger.error("IO error reading file: " + xml.getName(), e);
        }

    }
}
