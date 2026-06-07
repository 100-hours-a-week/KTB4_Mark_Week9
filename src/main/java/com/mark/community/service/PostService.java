package com.mark.community.service;

import com.mark.community.dto.PostRequest;
import com.mark.community.dto.PostTempRequest;
import com.mark.community.entity.Post;
import com.mark.community.entity.User;
import com.mark.community.exception.CustomException;
import com.mark.community.messages.ApiResponseErrorMessage;
import com.mark.community.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final FileService fileService;
    private final UserService userService;

    public PostService(PostRepository postRepository, FileService fileService, UserService userService){
        this.postRepository = postRepository;
        this.fileService = fileService;
        this.userService = userService;
    }

    public Post postTemp(PostTempRequest request, User user) {
        Post post = new Post(
                request.getTitle(),
                request.getBody(),
                user.getProfileImage(),
                user.getNickname(),
                user.getUserId());
        postRepository.save(post);

        return post;
    }


    public Post postAutoTemp(String postId, PostTempRequest request, MultipartFile[] images) {
        List<String> tempList = new ArrayList<>();
        if(images != null){
            for(MultipartFile file : images){
                tempList.add(fileService.upload(file));
            }
        }

        Post post = postRepository.findById(postId).
                orElseThrow(() -> new CustomException(ApiResponseErrorMessage.POST_NOT_FOUND));

        post.setFileIds(tempList);
        post.setTitle(request.getTitle());
        post.setBody(request.getBody());

        postRepository.save(post);

        return post;
    }

    public void deletePost(String postId, String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ApiResponseErrorMessage.POST_NOT_FOUND));

        if(!post.getUserId().equals(userId)){
            throw new CustomException(ApiResponseErrorMessage.FORBIDDEN);
        }

        post.setDeleted(true);
        postRepository.save(post);
    }

    public Post getPost(String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ApiResponseErrorMessage.POST_NOT_FOUND));

        post.setViews(post.getViews() + 1);
        postRepository.save(post);

        if(!userService.existUser(post.getUserId())) post.setNickname("알 수 없음"); ;

        return post;
    }

    public Post savePost(String postId, PostRequest postRequest, MultipartFile[] images) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ApiResponseErrorMessage.POST_NOT_FOUND));

            List<String> tempList = new ArrayList<>();
            if(images != null){
                for(MultipartFile file : images){
                    tempList.add(fileService.upload(file));
                }
            }
            if(postRequest.getImages() != null){
                for(String image : postRequest.getImages()){
                    tempList.add(image);
                }
            }

            post.setTitle(postRequest.getTitle());
            post.setBody(postRequest.getBody());
            post.setFileIds(tempList);
            post.setTemp(false);
            post.setPostTime(new Date());

            return postRepository.save(post);
    }

    public List<Post> getPosts(int size, String lastPostId) {
        List<Post> posts = postRepository.findAllOrderByPostTime(size, lastPostId);
        for(Post post : posts){
            if(!userService.existUser(post.getUserId())){
                post.setNickname("알 수 없음");
            }
        }
       return posts;
    }


    public void addLike(String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ApiResponseErrorMessage.POST_NOT_FOUND));

        post.setLikes(post.getLikes() + 1);

        postRepository.save(post);
    }

    public void deleteLike(String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ApiResponseErrorMessage.POST_NOT_FOUND));

        post.setLikes(post.getLikes() - 1);

        postRepository.save(post);
    }

    public Post editPost(String postId, PostTempRequest request, MultipartFile[] images, String userId) {
        List<String> tempList = new ArrayList<>();

        if(images != null){
            for(MultipartFile file : images){
                tempList.add(fileService.upload(file));
            }
        }
        Post post = postRepository.findById(postId).
                orElseThrow(() -> new CustomException(ApiResponseErrorMessage.POST_NOT_FOUND));

        if(!post.getUserId().equals(userId)){
            throw new CustomException(ApiResponseErrorMessage.FORBIDDEN);
        }

        post.setFileIds(tempList);
        post.setEdited(true);

        if(!request.getTitle().isBlank()) post.setTitle(request.getTitle());

        if(!request.getBody().isBlank()) post.setBody(request.getBody());

        postRepository.save(post);

        return post;
    }

    public void addReports(String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ApiResponseErrorMessage.POST_NOT_FOUND));

        post.setReports(post.getReports() + 1);
        postRepository.save(post);
    }
}
