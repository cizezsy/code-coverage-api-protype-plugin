package io.jenkins.plugins.coverapi.converter;

import io.jenkins.plugins.coverapi.exception.ConversionException;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class XMLConverter {

    private static XMLConverter converter = new XMLConverter();

    public static XMLConverter getInstance() {
        return converter;
    }

    private XMLConverter() {
    }

    /**
     * Converting source xml file to target file according the XSLT file
     *
     * @param xsl    XSLT file
     * @param source Source xml file
     * @param target Target file
     */
    public void convert(File xsl, File source, File target) {
        //TODO checkout if the result has been written into target file
        convert(xsl, source, new StreamResult(target));
    }

    /**
     * Converting source xml file to {@link DOMResult}
     *
     * @param xsl    XSLT file
     * @param source Source xml file
     * @return DOMResult
     */
    public DOMResult convertToDOMResultWithXSL(File xsl, File source) {
        DOMResult result = new DOMResult();
        convert(xsl, source, result);
        return result;
    }

    /**
     * Converting source xml file to {@link SAXResult}
     *
     * @param xsl    XSLT file
     * @param source Source xml file
     * @return SAXResult
     */
    public SAXResult convertToSAXResultWithXSL(File xsl, File source) {
        SAXResult result = new SAXResult();
        convert(xsl, source, result);
        return result;
    }

    /**
     * Converting source xml file to target result according the XSLT file
     *
     * @param xsl    XSLT file
     * @param source Source file
     * @param result Result that want to be written in
     */
    private void convert(File xsl, File source, Result result) {
        if (!xsl.exists()) {
            throw new ConversionException("XSL File not exist!");
        }

        if (!source.exists()) {
            throw new ConversionException("source File not exist!");
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer(new StreamSource(xsl));
            transformer.transform(new StreamSource(source), result);
        } catch (TransformerException e) {
            e.printStackTrace();
            throw new ConversionException(e);
        }
    }

}
