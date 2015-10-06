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

package fr.patouche.soat.service.impl;

import javax.inject.Inject;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.patouche.soat.entity.Post;
import fr.patouche.soat.repository.PostRepository;
import fr.patouche.soat.service.PostService;

/**
 * {@link fr.patouche.soat.service.PostService} implementation.
 *
 * @author patouche - 06/10/15
 */
@Service
public class PostServiceImpl implements PostService {

    /** The post repository. */
    private final PostRepository postRepository;

    /**
     * Class constructor.
     *
     * @param postRepository the post repository
     */
    @Inject
    public PostServiceImpl(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAll() {
        return this.postRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Post create(final String post, final String author) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Post read(final Long id) {
        return null;
    }

}
