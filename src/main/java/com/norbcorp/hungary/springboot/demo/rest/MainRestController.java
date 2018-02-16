package com.norbcorp.hungary.springboot.demo.rest;

import com.norbcorp.hungary.springboot.demo.backend.User;
import com.norbcorp.hungary.springboot.demo.backend.UserRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/user/")
public class MainRestController {
    private static Logger logger = Logger.getLogger(MainRestController.class.getName());

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody Iterable<User> getAll(){
        return userRepository.findAll();
    }

    @GetMapping("/{userid}")
    public @ResponseBody User getSpecificedUser(@PathVariable("userid") Long userid){
        return userRepository.findOne(userid);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String addUser(@RequestBody(required = true) String userName){
        try {
            User user = new User();
            user.setName(userName);
            userRepository.save(user);
            return "[{\"status\": Successful}]";
        } catch(Exception e){
            return "[{\"status\": Unsuccessful}, \"reason\": "+e.getMessage()+"}]";
        }
    }

    @PutMapping
    public User updateUser(@RequestBody User user, @PathVariable Long id){
        User localUser = null;
        if(user!=null && user.getName()!=null && user.getName()!="" && user.getId()!=null && user.getId().equals("")){
            localUser = userRepository.findOne(user.getId());
            localUser.setName(user.getName());
        }
        return localUser;
    }

    /**
     * Excel export of users table.
     *
     * @return excel file as a byte array.
     * @throws Exception
     */
    @RequestMapping(value = "/export", produces = "application/vnd.ms-excel")
    public ResponseEntity<byte[]> getUsersInExcelFormat() throws Exception{
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentDispositionFormData("attachment","UserList.xlsx");
        responseHeaders.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));

        List<User> users = new LinkedList<User>();
        for(Iterator<User> iterator = userRepository.findAll().iterator();iterator.hasNext();)
            users.add(iterator.next());
        //Add users to file
        Workbook workbook = new HSSFWorkbook();
        Sheet userSheet = workbook.createSheet("Users");
        Row row = userSheet.createRow(0);
        row.createCell(0).setCellValue("Name");
        int i = 1;
        for(User user : users) {
            userSheet.createRow(i).createCell(0).setCellValue(user.getName());
            i++;
        }
        //Convert to bytestream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);

        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(),responseHeaders, HttpStatus.OK);
    }
}
