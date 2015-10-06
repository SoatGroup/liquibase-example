package fr.patouche.soat.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import fr.patouche.soat.entity.Post;
import fr.patouche.soat.repository.CommentRepository;
import fr.patouche.soat.service.CommentService;

/**
 * {@link fr.patouche.soat.service.CommentService} implementation.
 *
 * @author : patouche - 06/10/15.
 */
@Service
public class CommentServiceImpl implements CommentService {

    /** The comment repository. */
    private final CommentRepository commentRepository;

    /**
     * Class constructor.
     *
     * @param commentRepository the comment repository
     */
    @Inject
    public CommentServiceImpl(final CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Post addComment(final Long postId, final String content, final String author) {
        return null;
    }

    @Override
    public Post deleteComment(final Long commentId) {
        return null;
    }
}
