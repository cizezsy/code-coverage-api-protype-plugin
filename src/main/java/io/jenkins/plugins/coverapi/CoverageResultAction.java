package io.jenkins.plugins.coverapi;

import hudson.model.Action;
import hudson.model.HealthReport;
import hudson.model.HealthReportingAction;
import hudson.model.Run;
import jenkins.model.RunAction2;
import jenkins.tasks.SimpleBuildStep;
import org.kohsuke.stapler.StaplerProxy;

import javax.annotation.CheckForNull;
import java.io.File;
import java.util.Collection;
import java.util.List;

public class CoverageResultAction implements HealthReportingAction, RunAction2, SimpleBuildStep.LastBuildAction, StaplerProxy {

    private List<File> reports;

    public CoverageResultAction(List<File> reports) {
        this.reports = reports;
    }

    private transient Run<?, ?> owner;

    @Override
    public void onAttached(Run<?, ?> r) {
        this.owner = r;
    }

    @Override
    public void onLoad(Run<?, ?> r) {
        this.owner = r;
    }

    @CheckForNull
    @Override
    public String getIconFileName() {
        return null;
    }

    @CheckForNull
    @Override
    public String getDisplayName() {
        return "Coverage Report";
    }

    @CheckForNull
    @Override
    public String getUrlName() {
        return "coverage";
    }

    @Override
    public HealthReport getBuildHealth() {
        return null;
    }

    @Override
    public Collection<? extends Action> getProjectActions() {
        return null;
    }

    @Override
    public Object getTarget() {
        return null;
    }
}
