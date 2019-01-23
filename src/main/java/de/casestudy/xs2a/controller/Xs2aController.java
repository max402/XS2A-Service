package de.casestudy.xs2a.controller;

import de.casestudy.xs2a.tpp.TppInfo;
import de.casestudy.xs2a.tpp.TppInfoHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Xs2aController {

    private final TppInfoHolder tppInfoHolder;

    @GetMapping("/tppInfo")
    TppInfo getTppInfo(@RequestHeader("tpp-qwac-certificate-urlenc") String certHeader) {
        return tppInfoHolder.getTppInfo();
    }
}
