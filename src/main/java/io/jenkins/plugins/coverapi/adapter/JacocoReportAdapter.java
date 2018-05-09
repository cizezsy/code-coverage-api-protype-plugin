package io.jenkins.plugins.coverapi.adapter;

import hudson.Extension;
import org.kohsuke.stapler.DataBoundConstructor;

public class JacocoReportAdapter extends JavaXMLCoverageReportAdapter {

    @DataBoundConstructor
    public JacocoReportAdapter(String path) {
        super(path);
    }

    @Override
    public String getXSL() {
        //TODO Find standard format jacoco report xml file
        return null;
    }

    @Override
    public String getXSDSchema() {
        return null;
    }

    @Override
    public String getToolName() {
        return "Jacoco";
    }

    @Extension
    public static final class JacocoReportAdapterDescriptor extends CoverageReportAdapterDescriptor<CoverageReportAdapter> {

        public JacocoReportAdapterDescriptor() {
            super(JacocoReportAdapter.class, "Jacoco");
        }
    }
}
