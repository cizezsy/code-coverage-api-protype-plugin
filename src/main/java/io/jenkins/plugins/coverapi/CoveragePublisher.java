package io.jenkins.plugins.coverapi;

import hudson.DescriptorExtensionList;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.remoting.VirtualChannel;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;
import io.jenkins.plugins.coverapi.adapter.CoverageReportAdapter;
import io.jenkins.plugins.coverapi.adapter.CoverageReportAdapterDescriptor;
import jenkins.MasterToSlaveFileCallable;
import jenkins.tasks.SimpleBuildStep;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.StaplerRequest;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

        Map<CoverageReportAdapter, FilePath[]> reports = new HashMap<>();

        for (CoverageReportAdapter adapter : adapters) {
            String path = adapter.getPath();

            FilePath[] r = workspace.act(new ParseReportCallable(path, adapter));
            reports.put(adapter, r);
        }

        if (reports.size() == 0) {
            listener.getLogger().println("No reports were found in this path");
        }

        File runRootDir = run.getRootDir();
        Map<CoverageReportAdapter, File[]> copiedReportFile = new HashMap<>();

        for (Map.Entry<CoverageReportAdapter, FilePath[]> adapterReports : reports.entrySet()) {
            FilePath[] r = adapterReports.getValue();
            File[] copies = new File[r.length];

            for (int i = 0; i < r.length; i++) {
                File copy = new File(runRootDir, r[i].getName() + i);
                r[i].copyTo(new FilePath(copy));
                copies[i] = copy;
            }
            copiedReportFile.put(adapterReports.getKey(), copies);
        }
        reports.clear();

        List<File> targetFiles = new LinkedList<>();
        for (Map.Entry<CoverageReportAdapter, File[]> adapterReports : copiedReportFile.entrySet()) {
            CoverageReportAdapter adapter = adapterReports.getKey();
            for (File s : adapterReports.getValue()) {
                File target = new File(s.getName() + ".universal");
                //TODO Wrong implementation, will fix in future
                adapter.convert(s, target);
                targetFiles.add(target);
                FileUtils.deleteQuietly(s);
            }
        }

        CoverageResultAction action = new CoverageResultAction(targetFiles);
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

    private static class ParseReportCallable extends MasterToSlaveFileCallable<FilePath[]> {

        private final String reportFilePath;
        private final CoverageReportAdapter reportAdapter;

        public ParseReportCallable(String reportFilePath, CoverageReportAdapter reportAdapter) {
            this.reportFilePath = reportFilePath;
            this.reportAdapter = reportAdapter;
        }

        @Override
        public FilePath[] invoke(File f, VirtualChannel channel) throws IOException, InterruptedException {
            FilePath[] r = new FilePath(f).list(reportFilePath);

            for (FilePath filePath : r) {
                //TODO use schema to validate xml file
            }
            return r;
        }
    }
}
