package com.kelvi.blog.service.rs;

import com.kelvi.blog.service.model.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostServiceRs {

    @GetMapping
    public ResponseEntity getList(){
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
            //TODO: return a message
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
}
