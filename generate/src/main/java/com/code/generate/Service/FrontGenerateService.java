package com.code.generate.Service;

import com.code.generate.Entity.CodeEntity;

import java.io.File;

public interface FrontGenerateService {
    public File generateIndex(CodeEntity e);
    public File generateHtml(CodeEntity e);
    public File generateScss(CodeEntity e);
    public File generateSpec(CodeEntity e);
    public File generateTs(CodeEntity e);
    public File generateModule(CodeEntity e);
    public File generateRoutes(CodeEntity e);
    public String generateFile(CodeEntity e);
}
