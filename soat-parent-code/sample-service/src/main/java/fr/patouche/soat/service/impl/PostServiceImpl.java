package fr.patouche.soat.service.impl;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

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
    public Post create(final String author, String title, String content) {
        return this.postRepository.save(new Post(author, title, content));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Post> read(final Long id) {
        return Optional.ofNullable(this.postRepository.findOne(id));
    }

}
