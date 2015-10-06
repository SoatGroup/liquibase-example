package fr.patouche.soat.service;

import fr.patouche.soat.entity.Post;

/**
 * Comment service interface.
 *
 * @author : patouche - 06/10/15.
 */
public interface CommentService {

    Post addComment(Long postId, String content, String author);

    Post deleteComment(Long commentId);

}
