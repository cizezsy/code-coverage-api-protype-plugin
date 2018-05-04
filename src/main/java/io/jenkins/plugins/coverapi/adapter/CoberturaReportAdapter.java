package io.jenkins.plugins.coverapi.adapter;


import hudson.Extension;
import org.kohsuke.stapler.DataBoundConstructor;

public class CoberturaReportAdapter extends JavaXMLCoverageReportAdapter {

    @DataBoundConstructor
    public CoberturaReportAdapter(String path) {
        super(path);
    }

    @Override
    public String getXSL() {
        return null;
    }

    @Override
    public String getXSDSchema() {
        return null;
    }

    @Override
    public String getToolName() {
        return "Cobertura";
    }

    @Extension
    public static final class CoverturaDescriptor extends CoverageReportAdapterDescriptor<CoverageReportAdapter> {

        public CoverturaDescriptor() {
            super(CoberturaReportAdapter.class, "Cobertura");
        }
    }
}
