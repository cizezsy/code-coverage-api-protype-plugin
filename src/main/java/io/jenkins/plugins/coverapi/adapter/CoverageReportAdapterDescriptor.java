package io.jenkins.plugins.coverapi.adapter;

import hudson.DescriptorExtensionList;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;

import javax.annotation.Nonnull;

public class CoverageReportAdapterDescriptor<T extends CoverageReportAdapter> extends Descriptor<CoverageReportAdapter> {

    private String displayName;

    public CoverageReportAdapterDescriptor(Class<? extends CoverageReportAdapter> clazz, String displayName) {
        super(clazz);
        this.displayName = displayName;
    }

    /**
     * @return all extensions which extend CoverageReportAdapter and its Descriptor.
     */
    public static DescriptorExtensionList<CoverageReportAdapter, CoverageReportAdapterDescriptor<?>> all() {
        return Jenkins.getInstance().getDescriptorList(CoverageReportAdapter.class);
    }

    @Nonnull
    @Override
    public String getDisplayName() {
        return displayName;
    }
}
