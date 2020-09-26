package org.james.model;

public class InputIssueObject {

    private NewIssue issue;

    public NewIssue getIssue() {
        return issue;
    }

    public void setIssue(NewIssue issue) {
        this.issue = issue;
    }
}
/*

{
        "issue":{
        "project_id":"145",
        "subject":"API Test 中文4",
        "tracker_id":  4,
        "description": "java.sql.SQLException",
        "start_date": "2020-09-15",
        "due_date": "2020-10-01",
        "assigned_to_id": 51,
        "custom_fields":
        [
            {"value":"2020-10-21","id":28}
        ]
       }
      }
        */
