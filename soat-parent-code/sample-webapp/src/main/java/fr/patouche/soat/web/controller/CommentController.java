package fr.patouche.soat.web.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.patouche.soat.entity.Post;
import fr.patouche.soat.service.CommentService;

/**
 * Comment controller.
 *
 * @author patouche - 06/10/15
 */
@Controller
@RequestMapping("/post/{postId:[0-9]+}")
public class CommentController {

    /** The comment service. */
    @Inject
    private CommentService commentService;

    /**
     * Add a comment.
     *
     * @param postId  the post id
     * @param content the content of the comment
     * @param author  the name of the author
     * @return the post view
     */
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public String add(@PathVariable("postId") Long postId, @RequestParam("content") String content,
            @RequestParam("author") String author) {
        Post post = this.commentService.addComment(postId, content, author);
        return "redirect:/post/" + post.getId();
    }

}