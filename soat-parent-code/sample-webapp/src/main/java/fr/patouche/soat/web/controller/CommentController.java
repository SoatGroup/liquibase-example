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

    @Inject
    private CommentService commentService;

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public String create(@PathVariable("postId") Long postId, @RequestParam("content") String content,
            @RequestParam("author") String author) {
        final Post post = this.commentService.addComment(postId, content, author);
        return "redirect:/post/" + post.getId();
    }

}