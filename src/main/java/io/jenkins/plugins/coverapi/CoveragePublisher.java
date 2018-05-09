package io.jenkins.plugins.coverapi;

import hudson.DescriptorExtensionList;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;
import io.jenkins.plugins.coverapi.adapter.CoverageReportAdapter;
import io.jenkins.plugins.coverapi.adapter.CoverageReportAdapterDescriptor;
import io.jenkins.plugins.coverapi.target.CoverageReport;
import jenkins.tasks.SimpleBuildStep;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.StaplerRequest;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;

public class CoveragePublisher extends Recorder implements SimpleBuildStep {

    private CoverageReportAdapter[] adapters;

    @DataBoundConstructor
    public CoveragePublisher(CoverageReportAdapter[] adapters) {
        this.adapters = adapters;
    }

    public CoverageReportAdapter[] getAdapters() {
        return adapters;
    }

    @DataBoundSetter
    public void setAdapters(CoverageReportAdapter[] adapters) {
        this.adapters = adapters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void perform(@Nonnull Run<?, ?> run, @Nonnull FilePath workspace, @Nonnull Launcher launcher, @Nonnull TaskListener listener) throws InterruptedException, IOException {

        CoverageProcessor processor = new CoverageProcessor();
        List<CoverageReport> results = processor.precess(run, workspace, listener, adapters);

        CoverageResultAction action = new CoverageResultAction(results);
        run.addAction(action);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.NONE;
    }

    /**
     * Descriptor for {@link CoveragePublisher}
     */
    @Extension
    public static final class CoveragePublisherDescriptor extends BuildStepDescriptor<Publisher> {

        public CoveragePublisherDescriptor() {
            super(CoveragePublisher.class);
            load();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public String getDisplayName() {
            return "Publish Coverage Report";
        }


        public DescriptorExtensionList<CoverageReportAdapter, CoverageReportAdapterDescriptor<?>> getListCoverageReportAdapterDescriptors() {
            return CoverageReportAdapterDescriptor.all();
        }

        @Override
        public Publisher newInstance(@CheckForNull StaplerRequest req, @Nonnull JSONObject formData) throws FormException {
            if (req == null)
                throw new FormException("req cannot be null", "");

            return req.bindJSON(CoveragePublisher.class, formData);
        }
    }
}
