package com.kelvi.blog.service.rs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kelvi.blog.service.managment.AppConstants;
import com.kelvi.blog.service.model.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/posts")
@Slf4j
public class PostServiceRs {

    @GetMapping
    public ResponseEntity getList() throws Exception {

        List<Post> postList = new ArrayList<>();
        postList.add(new Post("uuid1", "title1", LocalDateTime.now(), "Author1", "Content1"));
        postList.add(new Post("uuid2", "title2", LocalDateTime.now().plusDays(1), "Author2", "Content2"));
        postList.add(new Post("uuid3", "title3", LocalDateTime.now().plusDays(2), "Author3", "Content3"));

        try {
            postList(postList);
        }catch (Exception e){
            //TODO: return a message
        }

        return ResponseEntity.ok(postList);
    }

    @GetMapping("/listSize")
    public ResponseEntity listSize(){

        long listSize = 0L;

        return ResponseEntity.ok(listSize);
    }

    private void postList(List<Post> postList) throws Exception{
    }

    @GetMapping("/{uuid}")
    public ResponseEntity fetch(@PathVariable("uuid") String uuid){
        Post post = new Post("uuid1", "title1", LocalDateTime.now(), "Author1", "Content1");

        try {
            postFetch(post);
        }catch (Exception e){
            //TODO: return a message
        }

        return ResponseEntity.ok(post);
    }

    private void postFetch(Post post) throws Exception{
    }

    @PostMapping
    public ResponseEntity persist(Post post){

        try {
            prePersist(post);
        }catch (Exception e){
            //TODO: log the error and return a message
            log.error("TEST");
            return jsonMessageResponse(HttpStatus.BAD_REQUEST, e);
        }

        //TODO: persist the entity

        try {
            postPersist(post);
        }catch (Exception e){
            //TODO: return a message
        }

        return ResponseEntity.ok(post);
    }

    private void prePersist(Post post) throws Exception{
    }

    private void postPersist(Post post) throws Exception{
    }

    @PutMapping("/{uuid}")
    public ResponseEntity update(@PathVariable("uuid") String uuid){
        Post post = new Post("uuid1", "title1", LocalDateTime.now(), "Author1", "Content1");

        try {
            preUpdate(post);
        }catch (Exception e){
            //TODO: return a message
        }

        //TODO: update the entity

        try {
            postUpdate(post);
        }catch (Exception e){
            //TODO: return a message
        }

        return ResponseEntity.ok(post);
    }

    private void preUpdate(Post post) throws Exception{
    }

    private void postUpdate(Post post) throws Exception{
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity delete(@PathVariable("uuid") String uuid){
        try {
            preDelete(uuid);
        }catch (Exception e){
            //TODO: return a message
        }

        //TODO: update the entity

        try {
            postDelete(uuid);
        }catch (Exception e){
            //TODO: return a message
        }

        return (ResponseEntity) ResponseEntity.noContent();
    }

    private void preDelete(String uuid) throws Exception{
    }

    private void postDelete(String uuid) throws Exception{
    }

    public static ResponseEntity jsonMessageResponse(HttpStatus status, Object object) {
        if (object instanceof Throwable) {
            Throwable t = (Throwable) object;
            return jsonResponse(status, AppConstants.JSON_GENERIC_MESSAGE_KEY, getErrorMessage(t));
        } else {
            return jsonResponse(status, AppConstants.JSON_GENERIC_MESSAGE_KEY, "" + object);

        }
    }

    public static ResponseEntity jsonResponse(HttpStatus status, String key, Object value) {
        Map<String, String> toJson = new HashMap<String, String>();
        toJson.put(key, value.toString());
        return jsonResponse(toJson, status);
    }

    public static ResponseEntity jsonResponse(Map<String, String> toJson, HttpStatus status) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = "";
        try {
            jsonStr = objectMapper.writeValueAsString(toJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(status).body(jsonStr);
    }

    private static String getErrorMessage(Throwable t) {
        String exceptionClass = t.getClass().getCanonicalName();
        return t.getMessage() == null ?
                exceptionClass : MessageFormat.format("{0}: {1}", exceptionClass, t.getMessage());
    }
}
