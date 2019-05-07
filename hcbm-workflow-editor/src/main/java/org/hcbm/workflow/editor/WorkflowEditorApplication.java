package org.hcbm.workflow.editor;

import org.hzero.autoconfigure.workflow.editor.EnableHZeroWorkflowEditor;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableHZeroWorkflowEditor
@EnableEurekaClient
@SpringBootApplication
public class WorkflowEditorApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkflowEditorApplication.class, args);
    }
}


