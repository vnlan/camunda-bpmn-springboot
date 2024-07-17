package com.demo.camunda.controller;


import com.demo.camunda.model.BpmnFile;
import com.demo.camunda.service.BpmnFileService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bpmn")
public class TestController {

    private final BpmnFileService bpmnFileService;
    private final RepositoryService repositoryService;
    @PostMapping("/upload")
    public ResponseEntity<String> uploadBpmnFile(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("fileName") String fileName,
                                                 @RequestParam("description") String description) {
        try {
            bpmnFileService.saveBpmnFile(fileName, file, description);
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + fileName);
        }
    }

    @PostMapping("/deploy")
    public ResponseEntity<String> deployBpmnFile(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("description") String description) {
        try {
            // Save BPMN file to the database
            BpmnFile savedFile = bpmnFileService.saveBpmnFile(file.getOriginalFilename(), file, description);

            // Deploy BPMN file to Camunda
            Deployment deployment = repositoryService.createDeployment()
                    .addInputStream(file.getOriginalFilename(), file.getInputStream())
                    .deploy();

            return new ResponseEntity<>("File deployed and saved successfully: " + deployment.getId(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to deploy and save file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/test")
    public String TestApi() {

            return "hehe";

    }

}



//
//import org.camunda.bpm.engine.RepositoryService;
//import org.camunda.bpm.engine.RuntimeService;
//import org.camunda.bpm.engine.repository.Deployment;
//import org.camunda.bpm.engine.repository.DeploymentBuilder;
//import org.camunda.bpm.engine.repository.ProcessDefinition;
//import org.camunda.bpm.engine.runtime.ProcessInstance;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//        import org.springframework.web.multipart.MultipartFile;
//import java.io.IOException;
//import java.io.InputStream;

//@RestController
//@RequestMapping("/api/bpmn")
//public class BpmnController {
//
//    @Autowired
//    private RepositoryService repositoryService;
//
//    @Autowired
//    private RuntimeService runtimeService;
//
//    @Autowired
//    private ResourceLoader resourceLoader;
//
//    // API endpoint để lưu trữ một tệp BPMN
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadBpmnFile(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return ResponseEntity.badRequest().body("Vui lòng chọn một tệp BPMN để tải lên.");
//        }
//
//        try {
//            InputStream inputStream = file.getInputStream();
//            Deployment deployment = repositoryService.createDeployment()
//                    .addInputStream(file.getOriginalFilename(), inputStream)
//                    .deploy();
//
//            return ResponseEntity.ok("Tệp BPMN đã được tải lên thành công. ID Triển khai: " + deployment.getId());
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi tải lên tệp BPMN: " + e.getMessage());
//        }
//    }
//
//    // API endpoint để bắt đầu một quy trình dựa trên process definition key
//    @PostMapping("/start-process/{processDefinitionKey}")
//    public ResponseEntity<String> startProcess(@PathVariable String processDefinitionKey) {
//        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
//                .processDefinitionKey(processDefinitionKey)
//                .latestVersion()
//                .singleResult();
//
//        if (processDefinition == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        // Bắt đầu một phiên bản của quy trình
//        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
//
//        return ResponseEntity.ok("Đã bắt đầu quy trình với ID: " + processInstance.getId());
//    }
//
//    // API endpoint để đọc một tệp BPMN theo tên tệp
//    @GetMapping("/download/{fileName}")
//    public ResponseEntity<Resource> downloadBpmnFile(@PathVariable String fileName) {
//        Resource resource = resourceLoader.getResource("classpath:" + fileName);
//        return ResponseEntity.ok()
//                .body(resource);
//    }
//}


//viết api lưu bpmn file và đọc file nếu start process thì ứng dụng tự hiểu và start
