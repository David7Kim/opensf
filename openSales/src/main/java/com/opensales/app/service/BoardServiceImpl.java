package com.opensales.app.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opensales.app.domain.model.Post;
import com.opensales.app.domain.model.PostComments;
import com.opensales.app.repository.PostCommentRepository;
import com.opensales.app.repository.PostRespository;
import com.opensales.app.repository.UserRepository;
import com.opensales.app.utils.DataTypeConverter;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

    private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private PostRespository postRespository;

    @Autowired
    private PostCommentRepository postCommentRepository;
    
    @Autowired 
    private UserRepository userRepository;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Autowired
    DataTypeConverter converter;

    /**
     * @Method Name : addBoard
     * @작성일 : 2022. 6. 21.
     * @작성자 : tykim
     * @변경이력 :
     * @Method 설명 :
     * @param param
     */
    @Override
    public void addBoard(Map<String, Object> param) {

        logger.info("addBoard(): {} ", param);

        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO open_sf_boards (name)");
        sql.append("VALUES(:name)");

        namedParameterJdbcTemplate.update(sql.toString(), param);
    }

    /**
     * @Method Name : getBoardList
     * @작성일 : 2022. 6. 21.
     * @작성자 : tykim
     * @변경이력 :
     * @Method 설명 :
     * @param authoriz
     * @return
     */
    @Override
    public String getBoardList(Boolean authoriz) {
        return null;
    }

    /**
     * @Method Name : addPost
     * @작성일 : 2022. 6. 21.
     * @작성자 : tykim
     * @변경이력 :
     * @Method 설명 :
     * @param param
     * @throws ParseException
     */
    @Override
    @Transactional
    public Boolean addPost(Map<String, Object> param) throws ParseException {
        Boolean msg = false;
        logger.info("addPost() - param : {} ", param);
        Post post = new Post();
        
        if(param.get("board_id")!=null && !param.get("board_id").equals(""))  {
            if(param.get("contents")!=null && !param.get("contents").equals("")) {
                if(param.get("created_date").toString()!= null && !param.get("created_date").equals("")){
                    if(param.get("updated_date").toString()!= null && !param.get("updated_date").equals("")) {
                        if(param.get("writer").toString()!= null && !param.get("writer").equals("")) {
                            post.setBoardId(converter.converterStringToLong(param.get("board_id")));
                            post.setContents(param.get("contents").toString());
                            post.setCreatedDate(converter.converterStringtoDate(param.get("created_date").toString()));
                            post.setUpdatedDate(converter.converterStringtoDate(param.get("updated_date").toString()));
                            post.setWriter(param.get("writer").toString());
                            post.setUserId(userRepository.findByUserAccount(param.get("writer").toString()));
                        }
                    }
                }
            }
        } else {
           logger.info("addPost Error NullPoint");
        }
        try {
            postRespository.save(post);
            msg = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            logger.info("addPost() Exception");
        }
        return msg;
    }

    /**
     * @Method Name : getPost
     * @작성일 : 2022. 8. 25.
     * @작성자 : kimdonghyeon
     * @변경이력 :
     * @Method 설명 : 특정 Post 한개를 가져온다.
     * @param postId
     * @return
     */
    @Override
    public Post getPost(Long postId) {
        Post post = new Post();
        post = postRespository.findById(postId);
       
        return post;
    }

    /**
     * @Method Name : getBoardPostList
     * @작성일 : 2022. 7. 11.
     * @작성자 : tykim
     * @변경이력 :
     * @Method 설명 : 게시글 리스트를 가져옵니다.
     * @param boardId
     * @return
     */
    @Override
    public List<Post> getBoardPostLists(Long boardId) {
        
        List<Post> post = postRespository.findByBoardId(boardId);
        List<Post>newPost = null;
//        for (Post posts : post) {
//            posts.getUserId().setPassword("비공개");
//            newPost.add(post.size(), posts);
//        }
        //postRespository.findAll(sort);
        //board 는 필요없고 그냥 하나의 Chatter로만 사용하기로 함
        // 나중에 게시판 같은것이 필요하면 그룹 기능을 추가할 생각임
        
        return post;
    }

    /**
     * @Method Name : updateBoardPostList
     * @작성일 : 2022. 6. 21.
     * @작성자 : tykim
     * @변경이력 :
     * @Method 설명 :
     * @param boardId
     */
    @Override
    public void updateBoardPostList(Map<String, Object> params) {
        Long postId = Long.parseLong(params.get("postId").toString());
        String postContents = params.get("contents").toString();

        Post post = new Post();
        post = postRespository.findById(postId);
        post.setContents(postContents);
        postRespository.save(post);
    }

    /**
     * @Method Name : deleteBoardPost
     * @작성일 : 2022. 6. 21.
     * @작성자 : tykim
     * @변경이력 :
     * @Method 설명 : 게시글을 삭제합니다.
     * @param postId
     */
    @Override
    public void deleteBoardPost(Long postId) {
        postRespository.delete(postId);
    }

    /**
     * @Method Name : addComment
     * @작성일 : 2022. 6. 21.
     * @작성자 : tykim
     * @변경이력 :
     * @Method 설명 :
     * @param param
     */
    @Override
    public void addComment(Map<String, Object> param) {

        logger.info("addComment(): {} ", param);

        Post post = new Post();
        Long postId = Long.parseLong(param.get("postId").toString());
        post = postRespository.findById(postId);

        String writer = param.get("commentWriter").toString();
        String contents = param.get("contents").toString();
        Long wirterId = Long.parseLong(param.get("commentWriterId").toString());

        PostComments postComments = new PostComments();
        postComments.setCommentPostId(post);
        postComments.setCommentWriter(writer);
        postComments.setCommentWriterId(wirterId);
        postComments.setContents(contents);

        postCommentRepository.save(postComments);
    }

    /**
     * @Method Name : getPostCommentList
     * @작성일 : 2022. 6. 21.
     * @작성자 : tykim
     * @변경이력 :
     * @Method 설명 : 댓글을 가져옵니다.
     * @param postId
     * @return
     */
    @Override
    public Page<PostComments> getPostCommentList(int page, int size, Long postId) {
        return null;
        //        return postCommentRepository.findByCommentPostId(pageRequest, postId);
    }

    /**
     * @Method Name : updatePostCommentList
     * @작성일 : 2022. 6. 21.
     * @작성자 : tykim
     * @변경이력 :
     * @Method 설명 : 댓글 업데이트함
     * @param postId
     */
    @Override
    public void updatePostComment(Map<String, Object> params) {
        Long commentId = Long.parseLong(params.get("commentId").toString());
        String comments = params.get("contents").toString();

        PostComments postComment = new PostComments();
        postComment = postCommentRepository.findOne(commentId);
        postComment.setContents(comments);
        postCommentRepository.save(postComment);

    }

    /**
     * @Method Name : deletePostComment
     * @작성일 : 2022. 6. 21.
     * @작성자 : tykim
     * @변경이력 :
     * @Method 설명 : 댓글을 삭제합니다.
     * @param commentId
     */
    @Override
    public void deletePostComment(Long commentId) {
        postCommentRepository.delete(commentId);
    }

    /**
      * @Method Name : getPostList
      * @작성일 : 2022. 11. 14.
      * @작성자 : kimdonghyeon
      * @변경이력 : 
      * @Method 설명 :
      * @param pageable
      * @return
      */
    @Override
    public Page<Post> getPostList(int page, int size) {
        return postRespository.findAll(new PageRequest(page, size));
    }

}
