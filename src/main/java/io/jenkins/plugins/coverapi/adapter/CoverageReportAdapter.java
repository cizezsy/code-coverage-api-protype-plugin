package io.jenkins.plugins.coverapi.adapter;

import hudson.ExtensionPoint;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;

import java.io.File;

public abstract class CoverageReportAdapter implements ExtensionPoint, Describable<CoverageReportAdapter> {

    private String path;

    public CoverageReportAdapter(String path) {
        this.path = path;
    }

    public abstract void convert(File source, File target);

    public abstract String getToolName();

    @SuppressWarnings("unchecked")
    @Override
    public Descriptor<CoverageReportAdapter> getDescriptor() {
        return Jenkins.getInstance().getDescriptorOrDie(getClass());
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
