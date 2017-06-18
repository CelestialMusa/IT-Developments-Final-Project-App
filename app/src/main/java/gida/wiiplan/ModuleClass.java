package gida.wiiplan;

/**
 * Created by NWUUser on 2016/12/03.
 */
public class ModuleClass {

    private String moduleName,moduleDescr,moduleLecturer,moduleCredits, moduleCode;
    private int moduleResource;

    public ModuleClass(int moduleResource,String moduleDescr, String moduleLecturer, String moduleCredits, String moduleName, String moduleCode) {
        setModuleDescr(moduleDescr);
        setModuleLecturer(moduleLecturer);
        setModuleCredits(moduleCredits);
        setModuleName(moduleName);
        setModuleCode(moduleCode);
        setModuleResource(moduleResource);
    }

    @Override
    public String toString() {
        return getModuleCode() +" "+getModuleName();
    }

    public int getModuleResource() {
        return moduleResource;
    }

    public void setModuleResource(int moduleResource) {
        this.moduleResource = moduleResource;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleDescr() {
        return moduleDescr;
    }

    public void setModuleDescr(String moduleDescr) {
        this.moduleDescr = moduleDescr;
    }

    public String getModuleLecturer() {
        return moduleLecturer;
    }

    public void setModuleLecturer(String moduleLecturer) {
        this.moduleLecturer = moduleLecturer;
    }

    public String getModuleCredits() {
        return moduleCredits;
    }

    public void setModuleCredits(String moduleCredits) {
        this.moduleCredits = moduleCredits;
    }
}
