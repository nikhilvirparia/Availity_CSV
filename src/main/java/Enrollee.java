import java.util.Comparator;

public class Enrollee{

    private String userId;
    private String name;
    private Integer version;
    private String insuranceCompany;

    // Get UserID
    public String getUserId() {
        return userId;
    }

    // SetUserID
    public void setUserId(String userId) {
        this.userId = userId;
    }

    // GetName
    public String getName() {
        return name;
    }

    // SetName
    public void setName(String name) {
        this.name = name;
    }

    // GetVersion
    public Integer getVersion() {
        return version;
    }

    // SetVersion
    public void setVersion(Integer version) {
        this.version = version;
    }

    // GetInsuranceComapny
    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    // SetInsuranceComapny
    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    // Add the toString method to print the result
    @Override
    public String toString() {
        return "{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", version=" + version +
                ", insuranceCompany='" + insuranceCompany + '\'' +
                '}';

    }
}
