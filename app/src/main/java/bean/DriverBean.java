package bean;

public class DriverBean {
    public String name;
    public String LicenseId;
    public  String contactnumber;
    public String PROFILE_PIC_PATH;

    public DriverBean()
    {

    }

    public DriverBean(String name, String licenseId, String contactnumber, String PROFILE_PIC_PATH) {
        this.name = name;
        LicenseId = licenseId;
        this.contactnumber = contactnumber;
        this.PROFILE_PIC_PATH = PROFILE_PIC_PATH;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getPROFILE_PIC_PATH() {
        return PROFILE_PIC_PATH;
    }

    public void setPROFILE_PIC_PATH(String PROFILE_PIC_PATH) {
        this.PROFILE_PIC_PATH = PROFILE_PIC_PATH;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicenseId() {
        return LicenseId;
    }

    public void setLicenseId(String licenseId) {
        LicenseId = licenseId;
    }
}
