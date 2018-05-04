package io.jenkins.plugins.coverapi;

import io.jenkins.plugins.coverapi.exception.ConversionException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class XSLConverter {

    /**
     * Converting source xml file to target according the XSLT file
     * @param xsl XSLT file
     * @param source source file
     * @param target target file
     */
    public void convert(File xsl, File source, File target) {
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
            transformer.transform(new StreamSource(source), new StreamResult(target));
        } catch (TransformerException e) {
            e.printStackTrace();
            throw new ConversionException(e);
        }

    }

}
