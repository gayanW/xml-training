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

import javax.xml.XMLConstants;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class XmlFunctions {

    private static final Logger logger = LoggerFactory.getLogger(DomParser.class);

    /**
     * Validate xml file with the given xsd file
     * @param xmlFile xmlFile to be parsed
     * @param xsdFile xsd to be used in validating the xml
     * @return 'true' if validates fine and 'false' if not.
     */
    public static boolean validateByXsd(File xmlFile, File xsdFile) {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        boolean isValid = false;
        try {
            Schema schema = schemaFactory.newSchema(xsdFile);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(xmlFile);
            validator.validate(source);

            isValid = true;
            logger.info("XML source is successfully validated with the given xsd: " + xsdFile.getName());
        } catch (SAXException | IOException e) {
            logger.error("Validation failed with the given xsd: " + xsdFile.getName(), e);
        }
        return isValid;
    }

    /**
     * Transform XML File based on the XSL File, placing the resulting transformed
     * document in a OutputStream.
     * @param xmlFile xml source to be transformed
     * @param xslFile xsl file to be used
     */
    public static void transform(File xmlFile, File xslFile, OutputStream outputStream) {
        StreamSource xslSource = new StreamSource(xslFile);
        StreamSource xmlSource = new StreamSource(xmlFile);

        try {
            transform(xmlSource, xslSource, new StreamResult(outputStream));
            logger.info("XML file " + xmlFile.getName() + " is successfully transformed using the XSL: " + xslFile.getName());
        } catch (TransformerException e) {
            logger.error("Error occurred during transformation. XSL file: " + xslFile.getName(), e);
        }
    }

    /**
     * Transform XML source using XSLT. The resulting transformed
     * document is placed in the passed in Result
     */
    public static void transform(Source xml, Source xsl, Result result) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer(xsl);
            transformer.transform(xml, result);
        } catch (TransformerException e) {
            throw new TransformerException(e.getMessageAndLocation());

        }
    }

}
