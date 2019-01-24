package de.casestudy.xs2a.filter;

import de.adorsys.psd2.validator.certificate.util.CertificateExtractorUtil;
import de.adorsys.psd2.validator.certificate.util.TppCertificateData;
import de.casestudy.xs2a.tpp.TppInfo;
import de.casestudy.xs2a.tpp.TppInfoHolder;
import de.casestudy.xs2a.tpp.TppRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.difi.certvalidator.api.CertificateValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class QwacCertificateUrlencFilter extends OncePerRequestFilter {
    private final TppInfoHolder tppInfoHolder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String encodedTppQwacCert = request.getHeader("tpp-qwac-certificate-urlenc");
        String decodedTppQwacCert = URLDecoder.decode(encodedTppQwacCert, "UTF-8");
        log.info("Decoded cert: " + decodedTppQwacCert);

        if (StringUtils.isNotBlank(decodedTppQwacCert)) {
            try {
                TppCertificateData tppCertificateData = CertificateExtractorUtil.extract(decodedTppQwacCert);
                TppInfo tppInfo = new TppInfo();
                tppInfo.setAuthorisationNumber(tppCertificateData.getPspAuthorisationNumber());
                tppInfo.setTppName(tppCertificateData.getName());
                tppInfo.setAuthorityId(tppCertificateData.getPspAuthorityId());
                tppInfo.setAuthorityName(tppCertificateData.getPspAuthorityName());
                tppInfo.setCountry(tppCertificateData.getCountry());
                tppInfo.setOrganisation(tppCertificateData.getOrganisation());
                tppInfo.setOrganisationUnit(tppCertificateData.getOrganisationUnit());
                tppInfo.setCity(tppCertificateData.getCity());
                tppInfo.setState(tppCertificateData.getState());

                List<String> tppRoles = tppCertificateData.getPspRoles();
                List<TppRole> xs2aTppRoles = tppRoles.stream()
                        .map(TppRole::valueOf)
                        .collect(Collectors.toList());
                tppInfo.setTppRoles(xs2aTppRoles);

//                if (!tppRoleValidationService.hasAccess(tppInfo, request)) {
//                    log.error("Access forbidden for TPP with authorisation number: {}", tppCertificateData.getPspAuthorisationNumber());
//                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have access to this resource");
//                    return;
//                }
//
                tppInfoHolder.setTppInfo(tppInfo);
            } catch (CertificateValidationException e) {
                log.debug(e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
