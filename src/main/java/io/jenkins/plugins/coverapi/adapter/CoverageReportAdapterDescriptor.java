package io.jenkins.plugins.coverapi.adapter;

import hudson.DescriptorExtensionList;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public class CoverageReportAdapterDescriptor<T extends CoverageReportAdapter> extends Descriptor<CoverageReportAdapter> {

    private String displayName;

    public CoverageReportAdapterDescriptor(Class<? extends CoverageReportAdapter> clazz, String displayName) {
        super(clazz);
        this.displayName = displayName;
    }

    /**
     * @return Extensions that are subclass of {@link CoverageReportAdapter} and have factory class that is subclass of ${@link CoverageReportAdapterDescriptor}
     */
    public static DescriptorExtensionList<CoverageReportAdapter, CoverageReportAdapterDescriptor<?>> all() {
        return Jenkins.getInstance().getDescriptorList(CoverageReportAdapter.class);
    }

    @Nonnull
    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public CoverageReportAdapter newInstance(@CheckForNull StaplerRequest req, @Nonnull JSONObject formData) throws FormException {
        return super.newInstance(req, formData);
    }
}
