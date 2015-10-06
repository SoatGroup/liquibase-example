/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.patouche.soat.web.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.patouche.soat.entity.Post;
import fr.patouche.soat.service.PostService;

/**
 * Post controller.
 *
 * @author patouche - 06/10/15
 */
@Controller
public class PostController {

    @Inject
    private PostService postService;

    @RequestMapping(value = "/", method = RequestMethod.GET)

    public String all(Model model) {
        model.addAttribute("posts", this.postService.getAll());
        return "post/all";
    }

    @RequestMapping(value = "/post/{id:[0-9]+}", method = RequestMethod.GET)
    public String read(@PathVariable("id") Long id, Model model) {
        model.addAttribute("post", this.postService.read(id));
        return "post/read";
    }

    @RequestMapping(value = "/post/create", method = RequestMethod.GET)
    public String create() {
        return "post/create";
    }

    @RequestMapping(value = "/post/create", method = RequestMethod.POST)
    public String create(@RequestParam("content") String content, @RequestParam("author") String author) {
        Post post = this.postService.create(content, author);
        return "redirect:/post/" + post.getId();
    }

}