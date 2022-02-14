package org.activiti;

import org.activiti.engine.RuntimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
public class HireProcessRestController {

    private final RuntimeService runtimeService;
    private final ApplicantRepository applicantRepository;

    public HireProcessRestController(RuntimeService runtimeService, ApplicantRepository applicantRepository) {
        this.runtimeService = runtimeService;
        this.applicantRepository = applicantRepository;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/start-hire-process",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void startHireProcess(@RequestBody Map<String, String> data) {

        Applicant applicant = new Applicant(data.get("name"), data.get("email"), data.get("phoneNumber"));
        applicantRepository.save(applicant);

        Map<String, Object> vars = Collections.singletonMap("applicant", applicant);
        runtimeService.startProcessInstanceByKey("hireProcessWithJpa", vars);
    }

}