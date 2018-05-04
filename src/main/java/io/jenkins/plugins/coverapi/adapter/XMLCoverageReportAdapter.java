package io.jenkins.plugins.coverapi.adapter;

import io.jenkins.plugins.coverapi.XSLConverter;

import java.io.File;

public abstract class XMLCoverageReportAdapter extends CoverageReportAdapter {

    public XMLCoverageReportAdapter(String path) {
        super(path);
    }

    public void convert(File source, File target) {
        new XSLConverter().convert(new File(getXSL()), source, target);
    }

    public abstract String getXSL();

    public abstract String getXSDSchema();

}
