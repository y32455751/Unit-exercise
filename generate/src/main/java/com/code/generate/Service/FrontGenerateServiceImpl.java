package com.code.generate.Service;

import com.code.generate.Entity.CodeEntity;
import com.code.generate.Util.CodeUtil;
import com.code.generate.Util.FileUtil;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class FrontGenerateServiceImpl implements FrontGenerateService {

    @Override
    public File generateIndex(CodeEntity e){
        StringBuffer buffer = new StringBuffer();
        buffer.append(CodeEntity.Export);
        buffer.append(" { ");
        buffer.append(e.getModuleName() + CodeUtil.upperCaseFrist(CodeEntity.Module));
        buffer.append(" } ");
        buffer.append(CodeEntity.From);
        buffer.append(" './" + e.getModuleName_Else() + "." + CodeEntity.Module + "';\n");
        return FileUtil.createFile(e.getOutPath(),"index.ts",buffer.toString(),e);
    }

    @Override
    public File generateHtml(CodeEntity e){
        StringBuffer buffer = new StringBuffer();
        buffer.append(CodeEntity.HtmlInitText);
        return FileUtil.createFile(e.getOutPath(),e.getModuleName_Else() + "." + CodeEntity.Component + ".html",buffer.toString(),e);
    }

    @Override
    public File generateScss(CodeEntity e){
        StringBuffer buffer = new StringBuffer();
        return FileUtil.createFile(e.getOutPath(),e.getModuleName_Else() + "." + CodeEntity.Component + ".scss",buffer.toString(),e);
    }

    @Override
    public File generateSpec(CodeEntity e){
        StringBuffer buffer = new StringBuffer();
        String[] imp = { "async", "ComponentFixture", "TestBed" };
        buffer.append(CodeUtil.generateImport(imp,"@angular/core/testing") + "\n");
        buffer.append(CodeUtil.generateImport(e.getModuleName()+CodeUtil.upperCaseFrist(CodeEntity.Component),"./"+ e.getModuleName() +".component") + "\n\n");
        buffer.append("describe('"+e.getModuleName()+"Component'), () => {\n");
        buffer.append("\t" + CodeEntity.Let + " component: "+e.getModuleName()+"Component;\n");
        buffer.append("\t" + CodeEntity.Let + " fixtrue: ComponentFixtrue<"+e.getModuleName()+"Componnent>;\n\n");
        buffer.append("\tbeforeEach(async(() => {\n");
        buffer.append("\t\tTestBed.configureTestingModule({\n");
        buffer.append("\t\t\tdeclarations: [ "+e.getModuleName()+"Component ]\n");
        buffer.append("\t\t}).compileComponents();\n");
        buffer.append("\t}));\n\n");
        buffer.append("\tbeforeEach(() => {\n");
        buffer.append("\t\tfixture = TestBed.createComponent("+e.getModuleName()+"Component);\n");
        buffer.append("\t\tcomponent = fixture.componentInstance;\n");
        buffer.append("\t\tfixture.detectChanges();\n");
        buffer.append("\t});\n\n");
        buffer.append("\tit('should create', () => {\n");
        buffer.append("\t\texpect(component).toBeTruthy();\n");
        buffer.append("\t});\n");
        buffer.append("};");
        return FileUtil.createFile(e.getOutPath(),e.getModuleName_Else() + "." + CodeEntity.Component + ".spec.ts",buffer.toString(),e);
    }

    @Override
    public File generateTs(CodeEntity e){
        StringBuffer buffer = new StringBuffer();
        String[] imp = {"Component", "OnInit"};
        buffer.append(CodeUtil.classNotes(e.getTitleName(),"",e.getTitleName()) + "\n");
        buffer.append(CodeUtil.generateImport(imp,"@angular/core")+"\n\n");
        buffer.append("@Component({\n");
        buffer.append("\tselector: 'app-"+e.getModuleName_Else()+"',\n");
        buffer.append("\ttemplateUrl: './"+e.getModuleName_Else()+"."+CodeEntity.Component+".html',\n");
        buffer.append("\tstyleUrls: ['./"+e.getModuleName_Else()+"."+CodeEntity.Component+".scss'],\n");
        buffer.append("})\n");
        buffer.append("export class "+e.getModuleName()+CodeUtil.upperCaseFrist(CodeEntity.Component)+" implements OnInit {\n\n");
        buffer.append(CodeUtil.classNotes("构造函数","\t") + "\n");
        buffer.append("\t" + CodeEntity.Constructor + "() {\n\n");
        buffer.append("\t" + "}\n\n");
        buffer.append("\t" + CodeEntity.NgOnInit + "() {\n\n");
        buffer.append("\t" + "}\n\n");
        buffer.append("}");
        return FileUtil.createFile(e.getOutPath(),e.getModuleName_Else() + "." + CodeEntity.Component + ".ts",buffer.toString(),e);
    }

    @Override
    public File generateModule(CodeEntity e){
        StringBuffer buffer = new StringBuffer();
        String[] imp = new String[]{"NgModule", "CUSTOM_ELEMENTS_SCHEMA"};
        buffer.append(CodeUtil.generateImport(imp,"@angular/core") + "\n");
        imp = new String[]{"CommonModule", "DatePipe"};
        buffer.append(CodeUtil.generateImport(imp,"@angular/common") + "\n");
        buffer.append(CodeUtil.generateImport("FormsModule","@angular/forms") + "\n");
        buffer.append(CodeUtil.generateImport("RouterModule","@angular/router") + "\n");
        buffer.append(CodeUtil.generateImport("SharedModule","../../../../shared/shared.module") + "\n");
        buffer.append(CodeUtil.generateImport("UI_COMPONENTS","../../../../system-ui") + "\n\n");
        buffer.append(CodeUtil.generateImport("routes","./"+e.getModuleName_Else()+"."+CodeEntity.Routes+".ts") + "\n");
        buffer.append(CodeUtil.generateImport(e.getModuleName()+CodeUtil.upperCaseFrist(CodeEntity.Component),"./"+e.getModuleName_Else()+"."+CodeEntity.Component+".ts") + "\n\n");
        buffer.append("@NgModule({\n");
        buffer.append("\tdeclarations: [\n");
        buffer.append("\t\t"+e.getModuleName()+CodeUtil.upperCaseFrist(CodeEntity.Component)+",\n");
        buffer.append("\t],\n");
        buffer.append("\timports: [\n");
        buffer.append("\t\tUI_COMPONENTS,\n");
        buffer.append("\t\tCommonModule,\n");
        buffer.append("\t\tFormsModule,\n");
        buffer.append("\t\tSharedModule,\n");
        buffer.append("\t\tRouterModule.forChild(routes),\n");
        buffer.append("\t],\n");
        buffer.append("\tschemas: [CUSTOM_ELEMENTS_SCHEMA],\n");
        buffer.append("\tproviders: [DatePipe],\n");
        buffer.append("})\n");
        buffer.append(CodeEntity.Export + " " + CodeEntity.Class + " " + e.getModuleName() + "Module {\n");
        buffer.append("\t"+CodeEntity.Public + " " + CodeEntity.Static + " routes = routes;\n");
        buffer.append("}\n");
        return FileUtil.createFile(e.getOutPath(),e.getModuleName_Else() + "." + CodeEntity.Module + ".ts",buffer.toString(),e);
    }

    @Override
    public File generateRoutes(CodeEntity e){
        StringBuffer buffer = new StringBuffer();
        buffer.append(CodeUtil.generateImport("Routes","@angular/router") + "\n");
        buffer.append(CodeUtil.generateImport(e.getModuleName()+CodeUtil.upperCaseFrist(CodeEntity.Component),"./"+e.getModuleName_Else()+"."+CodeEntity.Component+".ts") + "\n\n");
        buffer.append(CodeEntity.Export + " " + CodeEntity.Const + " routes: Routes = [\n");
        buffer.append("\t{ path: '', " + CodeEntity.Component + ": " + e.getModuleName() + CodeUtil.upperCaseFrist(CodeEntity.Component) + ", pathMatch: 'full', data: {breadcrumb: '"+e.getTitleName()+"'} },\n");
        buffer.append("];");
        return FileUtil.createFile(e.getOutPath(),e.getModuleName_Else() + "." + CodeEntity.Routes + ".ts",buffer.toString(),e);
    }

    @Override
    public String generateFile(CodeEntity e) {
        FileUtil.deleteDirectory(e.getOutPath());
        List<File> filelist = new ArrayList<File>();
        filelist.add(this.generateIndex(e));
        filelist.add(this.generateHtml(e));
        filelist.add(this.generateScss(e));
        filelist.add(this.generateSpec(e));
        filelist.add(this.generateTs(e));
        filelist.add(this.generateModule(e));
        filelist.add(this.generateRoutes(e));
        File[] files = new File[filelist.size()];
        for (int i=0;i<filelist.size();i++) {
            files[i] = filelist.get(i);
        }
        e.setFiles(files);
        e.setRarFilePath(e.getOutPath());
        e.setRarFilename(e.getModuleName_Else()+".zip");
        File zipFile = new File(e.getRarFilePath() + e.getRarFilename());
        // 调用压缩方法
        FileUtil.zipFiles(files, zipFile);
//        FileUtil.downLoadFile(e,zipFile);
        return CodeEntity.OUT_FILE_RELATIVE_PATH + e.getRarFilename() + "#" + e.getRarFilename();
    }


}
