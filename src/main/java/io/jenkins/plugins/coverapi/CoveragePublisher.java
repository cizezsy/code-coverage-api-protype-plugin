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
import jenkins.tasks.SimpleBuildStep;
import org.kohsuke.stapler.DataBoundSetter;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CoveragePublisher extends Recorder implements SimpleBuildStep {

    private CoverageReportAdapter[] adapters;

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
        //TODO
        File runRootDir = run.getRootDir();

        List<File> targetFiles = new LinkedList<>();

        for (CoverageReportAdapter adapter : adapters) {
            String path = adapter.getPath();
        }

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
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }

        @Nonnull
        @Override
        public String getDisplayName() {
            return "Publish Coverage Report";
        }


        public DescriptorExtensionList<CoverageReportAdapter, CoverageReportAdapterDescriptor<?>> getListCoverageReportAdapterDescriptors() {
            return CoverageReportAdapterDescriptor.all();
        }
    }
}
