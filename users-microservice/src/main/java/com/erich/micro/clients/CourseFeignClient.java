package com.erich.micro.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "courses-mcsv", url = "courses-mcsv:8002/api/v1/course")
public interface CourseFeignClient {

    @DeleteMapping("/deleteUser/{idUser}")
    void deleteUserToCourse(@PathVariable(value = "idUser") Long idUser);
}
