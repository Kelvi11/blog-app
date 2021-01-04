package com.kelvi.blog.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kelvi.blog.service.managment.AppConstants;
import com.kelvi.blog.service.model.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class RsRepositoryService<T, U> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Class<T> entityClass;

    @Autowired
    EntityManager entityManager;

    protected Class<T> getEntityClass() {
        return entityClass;
    }

    protected EntityManager getEntityManager() { return entityManager; }

    public RsRepositoryService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    //Rest endpoints
    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity fetch(@PathVariable("id") U id){
        log.info("fetch: " + id);

        try {
            T t = find(id);
            if (t == null) {
                return handleObjectNotFoundRequest(id);
            } else {
                try {
                    postFetch(t);
                } catch (Exception e) {
                    log.error("fetch: " + id, e);
                }
                return ResponseEntity.ok(t);
            }
        } catch (NoResultException e) {
            log.error("fetch: " + id, e);
            return jsonMessageResponse(HttpStatus.NOT_FOUND, id);
        } catch (Exception e) {
            log.error("fetch: " + id, e);
            return jsonErrorMessageResponse(e);
        }
    }

    private void postFetch(T object) throws Exception{
    }

    @PostMapping
    @Transactional
    public ResponseEntity persist(T object){
        log.info("persist");
        try {
            prePersist(object);
        }catch (Exception e){
            //TODO: log the error and return a message
            log.error("persist", e);
            return jsonMessageResponse(HttpStatus.BAD_REQUEST, e);
        }

        //TODO: persist the entity

        try {
            postPersist(object);
        }catch (Exception e){
            //TODO: return a message
        }

        return ResponseEntity.ok(object);
    }

    private void prePersist(T object) throws Exception{
    }

    private void postPersist(T object) throws Exception{
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity update(@PathVariable("id") U id, T object){
        log.info("update:" + id);

        try {
            object = preUpdate(object);
        } catch (Exception e) {
            log.error("update:" + id, e);
            return jsonMessageResponse(HttpStatus.BAD_REQUEST, e);
        }
        try {
            entityManager.merge(object);
            return ResponseEntity.ok(object);
        } catch (Exception e) {
            log.error("update:" + id, e);
            return jsonErrorMessageResponse(object);
        } finally {
            try {
                postUpdate(object);
            } catch (Exception e) {
                log.error("update:" + id, e);
            }
        }
    }

    private T preUpdate(T object) throws Exception{
        return object;
    }

    private void postUpdate(T object) throws Exception{
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable("id") U id){
        log.info("delete: " + id);

        try {
            preDelete(id);
        } catch (Exception e) {
            log.error("delete: " + id, e);
            return jsonMessageResponse(HttpStatus.BAD_REQUEST, e);
        }
        T t;
        try {
            t = find(id);
            if (t == null) {
                return handleObjectNotFoundRequest(id);
            }
        } catch (Exception e) {
            return jsonMessageResponse(HttpStatus.BAD_REQUEST, e);
        }
        try {
            toDelete(t);
            postDelete(id);
            return (ResponseEntity) ResponseEntity.noContent();
        } catch (NoResultException e) {
            log.error("delete: " + id, e);
            return jsonMessageResponse(HttpStatus.NOT_FOUND, id);
        } catch (Exception e) {
            log.error("delete: " + id, e);
            return jsonErrorMessageResponse(e);
        }
    }

    private void preDelete(U id) throws Exception{
    }

    private void postDelete(U id) throws Exception{
    }

    @GetMapping("/{id}/exist")
    @Transactional
    public ResponseEntity exist(@PathVariable("id") U id) {
        log.info("exist: " + id);

        try {
            boolean exist = find(id) != null;
            if (!exist) {
                return handleObjectNotFoundRequest(id);
            } else {
                return jsonMessageResponse(HttpStatus.OK, id);
            }
        } catch (Exception e) {
            log.error("exist: " + id, e);
            return jsonErrorMessageResponse(e);
        }
    }

    @GetMapping("/listSize")
    @Transactional
    public ResponseEntity listSize(){
        log.info("getListSize");

//        Map<String, Object> params = new HashMap<>();
//        StringBuilder queryBuilder = new StringBuilder();
//        try {
//            PanacheQuery<T> search = getSearch(null);
//            long listSize = search.count();
//            return Response.status(Status.OK).entity(listSize)
//                    .header("Access-Control-Expose-Headers", "listSize")
//                    .header("listSize", listSize).build();
//        } catch (Exception e) {
//            logger.errorv(e, "getListSize");
//            return jsonErrorMessageResponse(e);
//        }

        long listSize = 10;
        return ResponseEntity.ok(listSize);
    }

    @GetMapping
    public ResponseEntity getList(
            @DefaultValue("0") @RequestParam("startRow") Integer startRow,
            @DefaultValue("10") @RequestParam("pageSize") Integer pageSize,
            @RequestParam("orderBy") String orderBy){

        log.info("getList");

        try {
//            PanacheQuery<T> search = getSearch(orderBy);
//            long listSize = search.count();
//            List<T> list;
//            if (listSize == 0) {
//                list = new ArrayList<>();
//            } else {
//                int currentPage = 0;
//                if (pageSize != 0) {
//                    currentPage = startRow / pageSize;
//                } else {
//                    pageSize = Long.valueOf(listSize).intValue();
//                }
//                list = search.page(Page.of(currentPage, pageSize)).list();
//            }
//            postList(list);

//            return ResponseEntity.ok(list)
//                    .header("Access-Control-Expose-Headers", "startRow, pageSize, listSize")
//                    .header("startRow", startRow)
//                    .header("pageSize", pageSize)
//                    .header("listSize", listSize)
//                    .build();
        } catch (Exception e) {
            log.error("getList", e);
            return jsonErrorMessageResponse(e);
        }

        List<Post> postList = new ArrayList<>();
        return ResponseEntity.ok(postList);
    }

    private void postList(List<T> postList) throws Exception{
    }

    //Crud
    public T find(U id) {
        return getEntityManager().find(getEntityClass(), id);
    }

    public void toDelete(T t) {
        getEntityManager().remove(t);
    }

    //Response
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

    protected ResponseEntity handleObjectNotFoundRequest(U id) {
        String errorMessage = String.format("Object [{0}] with id [{1}] not found",
                entityClass.getCanonicalName(), id);
        return jsonMessageResponse(HttpStatus.NOT_FOUND, errorMessage);
    }

    public static ResponseEntity jsonErrorMessageResponse(Object error) {
        if (error instanceof Throwable) {
            Throwable t = (Throwable) error;
            return jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppConstants.JSON_GENERIC_MESSAGE_KEY, getErrorMessage(t));
        } else {
            return jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppConstants.JSON_GENERIC_MESSAGE_KEY, "" + error);
        }
    }

    public static ResponseEntity jsonMessageResponse(HttpStatus status, Object object) {
        if (object instanceof Throwable) {
            Throwable t = (Throwable) object;
            return jsonResponse(status, AppConstants.JSON_GENERIC_MESSAGE_KEY, getErrorMessage(t));
        } else {
            return jsonResponse(status, AppConstants.JSON_GENERIC_MESSAGE_KEY, "" + object);

        }
    }
}
