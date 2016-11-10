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
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Class to parse xml using DOM
 */
public class DomParser implements Parser {

    private static final Logger logger = LoggerFactory.getLogger(DomParser.class);
    private Document document;

    /**
     * Parse the given xml file using DOM
     * @param xml xml file to be parsed
     */
    public void parse(File xml) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            this.document = documentBuilder.parse(xml);
            logger.info(xml.getName() + " is successfully parsed by DOM Parser");

        } catch (ParserConfigurationException e) {
            logger.error("DocumentBuilder cannot be created which satisfies the configuration requested", e);
        } catch (SAXException e) {
            logger.error("Parsing error", e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("IO error reading file: " + xml.getName(), e);
            e.printStackTrace();
        }
    }

    /**
     * Get DOM Document object. Return null if no xml is parsed.
     * @return DOM Document object.
     */
    public Document getDocument() {
        return document;
    }
}
