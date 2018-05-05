package io.jenkins.plugins.coverapi.adapter;

import hudson.ExtensionPoint;
import hudson.FilePath;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.Run;
import jenkins.model.Jenkins;

import java.io.File;
import java.util.Objects;

public abstract class CoverageReportAdapter implements ExtensionPoint, Describable<CoverageReportAdapter> {

    private String path;

    public CoverageReportAdapter(String path) {
        this.path = path;
    }

    /**
     * Tool name should be unique
     *
     * @return Tool name
     */
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


    public abstract void convert(File source, File target);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoverageReportAdapter that = (CoverageReportAdapter) o;
        return Objects.equals(getToolName(), that.getToolName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getToolName());
    }
}
