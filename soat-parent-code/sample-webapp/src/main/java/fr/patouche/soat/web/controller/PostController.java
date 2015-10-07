package fr.patouche.soat.web.controller;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.patouche.soat.entity.Post;
import fr.patouche.soat.service.CommentService;
import fr.patouche.soat.service.PostService;
import fr.patouche.soat.web.error.HttpStatusException;

/**
 * Post controller.
 *
 * @author patouche - 06/10/15
 */
@Controller
public class PostController {

    /** The post service. */
    @Inject
    private PostService postService;

    /** The comment service. */
    @Inject
    private CommentService commentService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String all(Model model) {
        model.addAttribute("posts", this.postService.getAll());
        return "post/all";
    }

    @RequestMapping(value = "/post/{id:[0-9]+}", method = RequestMethod.GET)
    public String read(@PathVariable("id") Long id, Model model) {
        final Post post = this.postService.read(id)
                .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        model.addAttribute("post", post);
        model.addAttribute("comments", this.commentService.getComments(id));
        return "post/read";
    }

    @RequestMapping(value = "/post/create", method = RequestMethod.GET)
    public String create() {
        return "post/create";
    }

    @RequestMapping(value = "/post/create", method = RequestMethod.POST)
    public String create(@RequestParam("author") String author, @RequestParam("title") String title,
            @RequestParam("content") String content) {
        final Post post = this.postService.create(author, title, content);
        return "redirect:/post/" + post.getId();
    }

}