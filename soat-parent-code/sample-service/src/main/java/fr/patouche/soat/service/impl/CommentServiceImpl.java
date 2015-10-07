package fr.patouche.soat.service.impl;

import javax.inject.Inject;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.patouche.soat.entity.Comment;
import fr.patouche.soat.entity.Post;
import fr.patouche.soat.repository.CommentRepository;
import fr.patouche.soat.repository.PostRepository;
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

    /** The comment repository. */
    private final PostRepository postRepository;

    /**
     * Class constructor.
     *
     * @param commentRepository the comment repository
     * @param postRepository    the post repository
     */
    @Inject
    public CommentServiceImpl(final CommentRepository commentRepository, final PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Post addComment(final Long postId, final String author, final String content) {
        final Post post = this.postRepository.findOne(postId);
        this.commentRepository.save(new Comment(post, author, content));
        return post;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Post deleteComment(final Long commentId) {
        final Comment comment = this.commentRepository.findOne(commentId);
        Post post = null;
        if (comment != null) {
            post = comment.getPost();
            this.commentRepository.delete(comment);
        }
        return post;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getComments(final Long postId) {
        return this.commentRepository.findAll(CommentRepository.Predicates.byPostId(postId), CommentRepository.ORDER);
    }
}
