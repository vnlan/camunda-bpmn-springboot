package com.demo.camunda.service;


import com.demo.camunda.model.BpmnFile;
import com.demo.camunda.repository.BpmnFileRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BpmnFileService {


    private final BpmnFileRepository bpmnFileRepository;

    public BpmnFile saveBpmnFile(String fileName, MultipartFile bpmnFile, String description) throws IOException {
        BpmnFile file = new BpmnFile();
        file.setFileName(fileName);
        file.setFileBlob(bpmnFile.getBytes());
        file.setDescription(description);
        return bpmnFileRepository.save(file);
    }
}
