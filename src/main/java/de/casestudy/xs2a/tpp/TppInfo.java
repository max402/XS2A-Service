package de.casestudy.xs2a.tpp;
import lombok.Data;

import java.util.List;

@Data
public class TppInfo {
    private String authorisationNumber;
    private String tppName;
    private List<TppRole> tppRoles;
    private String authorityId;
    private String authorityName;
    private String country;
    private String organisation;
    private String organisationUnit;
    private String city;
    private String state;
    private TppRedirectUri tppRedirectUri;
}
