package io.jenkins.plugins.coverapi;

import hudson.model.Run;
import jenkins.model.RunAction2;

import javax.annotation.CheckForNull;

public class CoverageResultAction implements RunAction2 {

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
}
