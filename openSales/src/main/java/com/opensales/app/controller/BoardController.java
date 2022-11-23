package com.opensales.app.controller;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.opensales.app.domain.model.Post;
import com.opensales.app.service.BoardService;
import com.opensales.app.utils.DataTypeConverter;


@Controller
public class BoardController {
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
    
    @Autowired
    BoardService boardService;
    
    @Autowired
    DataTypeConverter converter;
    
    @ResponseBody
    @RequestMapping(value = "/post/addPost", method = {RequestMethod.POST})
    public String addBoardPost(@RequestBody HashMap<String,Object> param ) throws ParseException {
        
        logger.info("addBoardPost  boardid : {} " , param );
        if(boardService.addPost(param)) {
            return "SUCCESS";
        }else {
            return "FAIL";
        }
    }
    
    /**
      * @Method Name : getBoardPostList
      * @작성일 : 2022. 6. 21.
      * @작성자 : tykim
      * @변경이력 : 
      * @Method 설명 : 게시판의 게시글을 가져옵니다.
      * @param boardid
      * @return
      */
    @ResponseBody
    @RequestMapping(value = "/post/{boardid}", method = {RequestMethod.GET})
    public List<Post> getBoardPostList(@PathVariable("boardid") String boardId ) {
        
        logger.info("getBoardPostList  boardid : {} " , boardId );
        Long boardIds = Long.parseLong(boardId);

        return boardService.getBoardPostLists(boardIds);
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
    @ResponseBody
    @RequestMapping(value = "/chatter/post", method = {RequestMethod.GET})
    public Page<Post> getPostList(@RequestParam (value ="page" ,required=true,defaultValue="0")int page,
            @RequestParam (value ="size" ,required=true,defaultValue="5")int size,
            @RequestParam (value="direction" , required=false, defaultValue="DESC") String direction,
            @RequestParam(value="sort" ,required=false, defaultValue="id")String sort) {
        
        logger.info("getPostList() - page: {} , size  :{}  "  ,page ,size);
        
        return boardService.getPostList(page, size);
    }
    /**
      * @Method Name : deleteBoardPost
      * @작성일 : 2022. 6. 21.
      * @작성자 : tykim
      * @변경이력 : 
      * @Method 설명 : 게시글을 삭제합니다.
      * @param postId
      */
    @ResponseBody
    @RequestMapping(value ="/post/{postid}", method= {RequestMethod.DELETE})
    public void deleteBoardPost(@PathVariable("postid") Long postId) {
        
        logger.info("deleteBoardPost  boardid : {} " , postId );
        
        boardService.deleteBoardPost(postId);
        
    }
    /**
      * @Method Name : deleteBoardPost
      * @작성일 : 2022. 7. 22.
      * @작성자 : kimdonghyeon
      * @변경이력 : 
      * @Method 설명 :
      * @param postId
      */
    @ResponseBody
    @RequestMapping(value ="/post/detail/view/{postid}", method= {RequestMethod.GET})
    public Post seePostDetail(@PathVariable("postid") Long postId) {
        
        logger.info("seePostDetail  postId : {} " , postId );

        return boardService.getPost(postId); 
    }
    
    
    @ResponseBody
    @RequestMapping(value ="/comment/get", method= {RequestMethod.GET})
    public void getPostComment(@RequestParam (value ="page" ,required=false,defaultValue="0")int page,
            @RequestParam (value ="size" ,required=false,defaultValue="5")int size,
            @RequestParam (value ="postId" ,required=false,defaultValue="")Long postId) {
        
        boardService.getPostCommentList(page,size, postId);
        
    }
    
    @ResponseBody
    @RequestMapping(value ="/comment/add", method= {RequestMethod.POST})
    public void addPostComment(@RequestBody Map<String,Object>param ) {
        boardService.addComment(param);

    }
    @ResponseBody
    @RequestMapping(value ="/comment/delete", method= {RequestMethod.POST})
    public void deletePostComment(@RequestParam (value="commentId" , required= false) Long commentId) {
        boardService.deletePostComment(commentId);

    }
    @ResponseBody
    @RequestMapping(value ="/comment/update", method= {RequestMethod.POST})
    public void updatePostComment(@RequestBody Map<String,Object>param ) {
        boardService.updatePostComment(param);

    }
}
