package de.casestudy.xs2a.filter;

import de.casestudy.xs2a.tpp.TppInfo;
import de.casestudy.xs2a.tpp.TppInfoHolder;
import de.casestudy.xs2a.tpp.TppRole;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class QwacCertificateUrlencFilterTest {
    private static String testFilePath = "nginx/certs/nginx.crt";
    private String urlEncCert;
    private TppInfo expected;

    @Before
    public void setUp() throws Exception {
        String cert = new String(Files.readAllBytes(Paths.get(testFilePath)), StandardCharsets.UTF_8);
        urlEncCert = URLEncoder.encode(cert, "UTF-8");

        expected = new TppInfo();
        expected.setAuthorisationNumber("12345987");
        expected.setTppName("domainName");
        expected.setTppRoles(Arrays.asList(TppRole.ASPSP, TppRole.PISP, TppRole.AISP, TppRole.PIISP));
        expected.setAuthorityId("Germany");
        expected.setAuthorityName("Auth");
        expected.setCountry("Germany");
        expected.setOrganisation("org");
        expected.setOrganisationUnit("ou");
        expected.setCity("Nuremberg");
        expected.setState("Bayern");
    }

    @Test
    public void doFilterInternalTest() throws Exception {
        TppInfoHolder tppInfoHolder = new TppInfoHolder();
        QwacCertificateUrlencFilter filter = new QwacCertificateUrlencFilter(tppInfoHolder);

        MockFilterChain mockChain = new MockFilterChain();
        MockHttpServletRequest req = new MockHttpServletRequest("tppInfo", "/");


        req.addHeader("tpp-qwac-certificate-urlenc", urlEncCert);
        MockHttpServletResponse rsp = new MockHttpServletResponse();

        filter.doFilterInternal(req, rsp, mockChain);

        System.out.println(tppInfoHolder.getTppInfo());
        assertEquals(expected, tppInfoHolder.getTppInfo());
    }
}
