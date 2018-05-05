package io.jenkins.plugins.coverapi.adapter;

import hudson.FilePath;
import hudson.model.Run;
import io.jenkins.plugins.coverapi.converter.XMLConverter;
import io.jenkins.plugins.coverapi.exception.ConversionException;
import jenkins.model.Jenkins;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public abstract class XMLCoverageReportAdapter extends CoverageReportAdapter {

    public XMLCoverageReportAdapter(String path) {
        super(path);
    }

    public abstract String getXSL();

    public abstract String getXSDSchema();

    @Override
    public void convert(File source, File target) {
        XMLConverter converter = XMLConverter.getInstance();
        File xsl = getXSLFile();
        converter.convert(xsl, source, target);
    }

    @SuppressWarnings("WeakerAccess")
    protected File getXSLFile() {
        try {
            return new File(getClass().getResource(getXSL()).toURI());
        } catch (URISyntaxException e) {
            throw new ConversionException(e);
        }
//        FilePath[] xlsFiles;
//        try {
//            xlsFiles = workspace.list(getXSL());
//
//            if (xlsFiles.length == 0)
//                throw new ConversionException("Unable to find XSLT file to convert source xml");
//
//            //Default return first satisfied XSLT file
//            return new File(xlsFiles[0].toURI());
//
//        } catch (IOException | InterruptedException e) {
//            throw new ConversionException(e);
//        }
    }
}
