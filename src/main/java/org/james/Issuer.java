package org.james;


import org.james.model.CustomField;
import org.james.model.InputIssueObject;
import org.james.model.NewIssue;
import org.james.model.ReturnUsersObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import sun.rmi.runtime.NewThreadAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Issuer {


    public static void main(String[] args) {
        Issuer issuer = new Issuer();
        String id = issuer.getUserId("tw024319");
        System.out.println("ID:" + id);
        issuer.issueIssue();
    }

    public String getUserId(String acct) {
//        http://10.0.4.245/redmine/users.json?name=TW009077

        RestTemplate rt = new RestTemplate();


        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic dHcwMDkwNzc6dHcwMDkwNzc=");
        headers.setContentType(MediaType.APPLICATION_JSON);
        @SuppressWarnings({"rawtypes", "unchecked"})
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<org.james.model.ReturnUsersObject> result =
                rt.exchange("http://10.0.4.245/redmine/users.json?name=" + acct.trim(),
                        HttpMethod.GET,
                        request,
                        ReturnUsersObject.class
                );
        System.out.println("Status:" + result.getStatusCode());
        ReturnUsersObject returnObjectUsers = result.getBody();
        if (returnObjectUsers == null) return "-1";
        return returnObjectUsers.getUsers().size() > 0
                ? String.valueOf(returnObjectUsers.getUsers().get(0).getId())
                : "-1";

    }

    public void issueIssue() {

        RestTemplate rt = new RestTemplate();

        NewIssue newIssue = new NewIssue();
        newIssue.setProjectId(145);
        newIssue.setSubject("API Test 中文-" + System.currentTimeMillis());
        newIssue.setTrackerId(4);
        newIssue.setDescription("java.lang.NullPointerException");
        newIssue.setStartDate("2020-09-15");
        newIssue.setDueDate("2020-10-01");
        newIssue.setAssignedToId(41);
        List<CustomField> customFields = new ArrayList<>();
        customFields.add(new CustomField(28, "2020-10-21"));
        newIssue.setCustomFields(customFields);
        InputIssueObject inputIssueObject = new InputIssueObject();
        inputIssueObject.setIssue(newIssue);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic dHcwMDkwNzc6dHcwMDkwNzc=");
        headers.setContentType(MediaType.APPLICATION_JSON);
        @SuppressWarnings({"rawtypes", "unchecked"})
        HttpEntity request = new HttpEntity(inputIssueObject, headers);
        ResponseEntity<String> result = null;
        try {
            result = rt.exchange("http://10.0.4.245/redmine/issues.json",
                    HttpMethod.POST,
                    request,
                    String.class
            );
        } catch (Exception e) {
            System.out.printf(e.getMessage());
        }
        System.out.println("Status:" + result.getStatusCode());
        System.out.println(result);
    }


    public void issueIssue(NewIssue newIssue) {

        RestTemplate rt = new RestTemplate();

//        NewIssue newIssue = new NewIssue();
//        newIssue.setProjectId(145);
//        newIssue.setSubject("API Test 中文-" + System.currentTimeMillis());
//        newIssue.setTrackerId(4);
//        newIssue.setDescription("java.lang.NullPointerException");
//        newIssue.setStartDate("2020-09-15");
//        newIssue.setDueDate("2020-10-01");
//        newIssue.setAssignedToId(41);
//        List<CustomField> customFields = new ArrayList<>();
//        customFields.add(new CustomField(28, "2020-10-21"));
//        newIssue.setCustomFields(customFields);
        InputIssueObject inputIssueObject = new InputIssueObject();
        inputIssueObject.setIssue(newIssue);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic dHcwMDkwNzc6dHcwMDkwNzc=");
        headers.setContentType(MediaType.APPLICATION_JSON);
        @SuppressWarnings({"rawtypes", "unchecked"})
        HttpEntity request = new HttpEntity(inputIssueObject, headers);
        ResponseEntity<String> result = null;
        try {
            result = rt.exchange("http://10.0.4.245/redmine/issues.json",
                    HttpMethod.POST,
                    request,
                    String.class
            );
        } catch (Exception e) {
            System.out.printf(e.getMessage());
        }
        System.out.println("Status:" + result.getStatusCode());
        System.out.println(result);
    }
}