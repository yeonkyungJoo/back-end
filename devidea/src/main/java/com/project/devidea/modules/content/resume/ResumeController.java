package com.project.devidea.modules.content.resume;

import com.project.devidea.modules.content.resume.form.CreateResumeRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.Errors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/resume")
@RequiredArgsConstructor
public class ResumeController {

    @GetMapping("/")
    public ResponseEntity getResumes() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity getResume() {
        return null;
    }

    @PostMapping("/")
    public ResponseEntity newResume(@RequestBody @Valid CreateResumeRequest request, Errors errors) {
        return new ResponseEntity(null, HttpStatus.CREATED);
    }

    @PostMapping("/edit")
    public ResponseEntity editResume() {
        return null;
    }

    @PostMapping("/delete")
    public ResponseEntity deleteResume() {
        return null;
    }

}
