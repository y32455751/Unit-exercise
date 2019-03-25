package com.code.generate.Entity;

public class FileEntity {

    protected StringBuffer index;
    protected StringBuffer html;
    protected StringBuffer scss;
    protected StringBuffer spec;
    protected StringBuffer ts;
    protected StringBuffer module;
    protected StringBuffer routes;

    public StringBuffer getIndex() {
        return index;
    }

    public void setIndex(StringBuffer index) {
        this.index = index;
    }

    public StringBuffer getHtml() {
        return html;
    }

    public void setHtml(StringBuffer html) {
        this.html = html;
    }

    public StringBuffer getScss() {
        return scss;
    }

    public void setScss(StringBuffer scss) {
        this.scss = scss;
    }

    public StringBuffer getSpec() {
        return spec;
    }

    public void setSpec(StringBuffer spec) {
        this.spec = spec;
    }

    public StringBuffer getTs() {
        return ts;
    }

    public void setTs(StringBuffer ts) {
        this.ts = ts;
    }

    public StringBuffer getModule() {
        return module;
    }

    public void setModule(StringBuffer module) {
        this.module = module;
    }

    public StringBuffer getRoutes() {
        return routes;
    }

    public void setRoutes(StringBuffer routes) {
        this.routes = routes;
    }
}
