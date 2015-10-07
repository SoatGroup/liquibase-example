package fr.patouche.soat.service;

import java.util.List;

import fr.patouche.soat.entity.Comment;
import fr.patouche.soat.entity.Post;

/**
 * Comment service interface.
 *
 * @author : patouche - 06/10/15.
 */
public interface CommentService {

    /**
     * Add a comment on the given post.
     *
     * @param postId  the post id
     * @param author  the author comment
     * @param content the content of the comment
     * @return the post related
     */
    Post addComment(Long postId, String author, String content);

    /**
     * Delete a comment.
     *
     * @param commentId the comment id
     * @return the post related to this comment
     */
    Post deleteComment(Long commentId);

    /**
     * The list of comment related to the post id
     *
     * @param postId the post identifier
     * @return the list of comments
     */
    List<Comment> getComments(Long postId);
}
