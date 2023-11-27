package com.cojored.FPHSCompSciTests.utils;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;

public class CheckstyleAuditListener implements AuditListener {
    public String errors = "";

    @Override
    public void auditStarted(AuditEvent event) {
    }

    @Override
    public void auditFinished(AuditEvent event) {
    }

    @Override
    public void fileStarted(AuditEvent event) {
    }

    @Override
    public void fileFinished(AuditEvent event) {
    }

    @Override
    public void addError(AuditEvent event) {
        errors += event.getMessage() + "\n";
    }

    @Override
    public void addException(AuditEvent auditEvent, Throwable throwable) {
    }
}